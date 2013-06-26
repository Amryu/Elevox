package com.elevox.main;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.Random;

import org.lwjgl.BufferUtils;

public class World {
	public final String name;
	
	public long seed = 0L;
	
	public ChunkMap chunkMap;
	
	public Point playerPos = new Point(0,0);

	public World(String name) {
		this(name, new Random().nextLong());
	}
	
	public World(String name, long seed) {
		this.name = name;
		this.seed = seed;
		
		chunkMap = new ChunkMap(this);
	}
	
	private FloatBuffer getVBOData() {
		if(!chunkMap.isLoaded) return BufferUtils.createFloatBuffer(0);
		else				   return chunkMap.getVBOData();
	}
	
	public static World createWorld(String name, long seed) {
		if(new File("./worlds/"+name+"/world.info").exists()) return null;
		
		World world = new World(name, seed);
		world.save();
		
		return world;
	}
	
	public void save() {
		chunkMap.saveAndClose();
		
		try {
			new File("./worlds/"+this.name).mkdirs();
			new File("./worlds/"+this.name+"/world.info").createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("./worlds/"+this.name+"/world.info")));
			
			bw.append("seed="+this.seed+"\n");
			bw.append("pos="+this.playerPos.x+";"+this.playerPos.y+"\n");
			
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static World load(String name) {
		World world = null;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("./worlds/"+name+"/world.info")));
			
			long seed = 0L;
			Point pos = new Point();
			
			String line = "";
			
			while((line = br.readLine()) != null) {
				if(line.startsWith("seed")) {
					seed = Long.valueOf(line.split("=")[1]);
				}
				if(line.startsWith("pos")) {
					pos.x = Integer.valueOf(line.split("=")[1].split(";")[0]);
					pos.y = Integer.valueOf(line.split("=")[1].split(";")[1]);
				}
			}
			
			world = new World(name, seed);
			world.playerPos = pos;
			
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return world;
	}

	public long getSeed() {
		return seed;
	}

	public int getHighestBlockYAt(int x, int z) {
		for(int i = Chunk.CHUNK_HEIGHT - 1; i >= 0; i--) {
			if(chunkMap.getBlockAt(x, i, z) != Block.AIR) return i;
		}
		
		return 0;
	}

	public String getName() {
		return name;
	}
}
