package com.elevox.main;
import java.nio.FloatBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.lwjgl.BufferUtils;


public class BlockStorage {
	private static int DIMENSION = 16;
	
	public Block[][][] blocks = new Block[DIMENSION][DIMENSION][DIMENSION];
	
	private Chunk parent;
	
	private int storageNum;
	
	private ChunkMap chunkMap;
	
	public FloatBuffer vboData;
	
	public BlockStorage(Block[][][] blocks, int storageNum, Chunk parent, ChunkMap chunkMap) {
		this.blocks = blocks;
		this.parent = parent;
		this.storageNum = storageNum;
		this.chunkMap = chunkMap;
	}
	
	private Random getRandom(int mod) {
		Random random = new Random();
		
		random.setSeed((chunkMap.getWorld().seed * parent.chunkPos.x * parent.chunkPos.y * storageNum) + 10 * mod);
		
		return random;
	}
	
	public void calculateVBOData() {
		int cx = parent.chunkPos.x * DIMENSION;
		int cy = storageNum * 16;
		int cz = parent.chunkPos.y * DIMENSION;
		Color green = new Color(0.0f, 1.0f, 0.0f, 1.0f);
		 
		List<float[]> blocksToPut = new LinkedList<float[]>();

		for(int x = 0; x < DIMENSION; x++) {
		    for(int y = 0; y < DIMENSION; y++) {
		        for(int z = 0; z < DIMENSION; z++) {
		            if(blocks[x][y][z] == Block.AIR) continue;
		            
		            if(chunkMap.getBlockAt(x+cx+1, y+cy, z+cz) == Block.AIR) blocksToPut.add(BlockVBO.rightVBO(x+cx, y+cy, z+cz, green, getRandom(x*16+y*16+z*16*65)));
		            if(chunkMap.getBlockAt(x+cx-1, y+cy, z+cz) == Block.AIR) blocksToPut.add(BlockVBO.leftVBO(x+cx, y+cy, z+cz, green, getRandom(x*16+y*16+z*16*23)));
		            if(chunkMap.getBlockAt(x+cx, y+cy+1, z+cz) == Block.AIR) blocksToPut.add(BlockVBO.topVBO(x+cx, y+cy, z+cz, green, getRandom(x*16+y*16+z*16*98)));
		            if(chunkMap.getBlockAt(x+cx, y+cy-1, z+cz) == Block.AIR) blocksToPut.add(BlockVBO.bottomVBO(x+cx, y+cy, z+cz, green, getRandom(x*16+y*16+z*16*28)));
		            if(chunkMap.getBlockAt(x+cx, y+cy, z+cz+1) == Block.AIR) blocksToPut.add(BlockVBO.frontVBO(x+cx, y+cy, z+cz, green, getRandom(x*16+y*16+z*16*37)));
		            if(chunkMap.getBlockAt(x+cx, y+cy, z+cz-1) == Block.AIR) blocksToPut.add(BlockVBO.backVBO(x+cx, y+cy, z+cz, green, getRandom(x*16+y*16+z*16*78)));
		        }
		    }
		}
		 
		vboData = BufferUtils.createFloatBuffer(blocksToPut.size()*40);
		
		for(float[] b : blocksToPut) {
		    vboData.put(b);
		}
		
		blocksToPut.clear();
		
		vboData.rewind();
	}
}
