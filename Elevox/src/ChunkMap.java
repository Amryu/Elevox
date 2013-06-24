import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;

public class ChunkMap implements Runnable {
	public Point chunkPos = new Point(0, 0);
	
	public ConcurrentHashMap<Point,Chunk> chunkMap = new ConcurrentHashMap<Point,Chunk>();
	
	private World world;
	
	// Wenn alle Chunks erstmals generiert/geladen sind wird dies auf true gesetzt
	public boolean isLoaded = false;
	
	public boolean wasModified = false;
	
	// Um Chunks erst zentral zu laden langsam hochzählen
	private int viewDistance = 1;
	
	public ChunkMap(World world) {
		this.world = world;
		
		Thread thread = new Thread(this);
		thread.start();
	}
	
	// Alle Chunks speichern und entfernen
	public void saveAndClose() {
		for(Chunk chunk : chunkMap.values()) {
			if(chunk == null) continue;
			
			this.save(chunk);
		}
		
		chunkMap = null;
	}
	
	public Block getBlockAt(int x, int y, int z) {
		if(y < 0 || y >= Chunk.CHUNK_HEIGHT) return null;
		
		int chunkPosX = (int) Math.floor(x / 16.0F);
		int chunkPosZ = (int) Math.floor(z / 16.0F);
		
		if(isChunkLoaded(chunkPosX, chunkPosZ)) {
			int blockPosX = Math.abs(x & 15);
			int blockPosZ = Math.abs(z & 15);
			
//			if(blockPosX < 0) blockPosX = 16 - blockPosX;
//			if(blockPosZ < 0) blockPosZ = 16 - blockPosZ;
			
//			System.out.println(x+"/"+blockPosX);
			
//			System.out.println(blockPosZ);

			return getChunkAt(chunkPosX, chunkPosZ).getBlockAt(blockPosX, y, blockPosZ);
		}
		else {
			return null;
		}
	}

	public Chunk getChunkAt(int posX, int posZ) {
		return chunkMap.get(new Point(posX, posZ));
	}
	
	public boolean isChunkLoaded(int posX, int posZ) {
		return chunkMap.containsKey(new Point(posX, posZ));
	}

	private void generateChunk(int posX, int posZ, long seed) {
		Block[][][] blocks = new Block[Chunk.CHUNK_WIDTH][Chunk.CHUNK_HEIGHT][Chunk.CHUNK_DEPTH];
		
		for(int x = 0; x < Chunk.CHUNK_WIDTH; x++) {
			for(int y = 0; y < Chunk.CHUNK_HEIGHT; y++) {
				for(int z = 0; z < Chunk.CHUNK_DEPTH; z++) {
					if(y < 128) {
						blocks[x][y][z] = Block.GRASS;
					}
					else {
						blocks[x][y][z] = Block.AIR;
					}
				}
			}
		}

		for(int x = 0; x < Chunk.CHUNK_WIDTH; x++) {
			for(int z = 0; z < Chunk.CHUNK_DEPTH; z++) {
				if(new Random().nextInt(2) == 0) blocks[x][128][z] = Block.GRASS;
			}
		}
		
		Chunk chunk = new Chunk(blocks, new Point(posX, posZ), this);
		
		addToMap(chunk);
		this.save(chunk);
		
		chunk.calculateVBOData();
		
		alertNeighbours(posX, posZ);
	}
	
	private void addToMap(Chunk chunk) {
		chunkMap.put(chunk.chunkPos, chunk);
	}

	private void loadChunk(int posX, int posZ) {
		try {
			DataInputStream dis = new DataInputStream(new GZIPInputStream(new FileInputStream("worlds/"+world.name+"/region/"+posX+"."+posZ+".chk")));
			
			byte[][] blockBytes = new byte[Chunk.CHUNK_WIDTH][Chunk.CHUNK_HEIGHT*Chunk.CHUNK_DEPTH];
			
			for(int i = 0; i < Chunk.CHUNK_WIDTH; i++) {
				dis.read(blockBytes[i], 0, Chunk.CHUNK_HEIGHT*Chunk.CHUNK_DEPTH);
			}
			
			dis.close();

			Block[][][] blocks = new Block[Chunk.CHUNK_WIDTH][Chunk.CHUNK_HEIGHT][Chunk.CHUNK_DEPTH];
			
			for(int x = 0; x < Chunk.CHUNK_WIDTH; x++) {
				int i = 0;
				for(int y = 0; y < Chunk.CHUNK_HEIGHT; y++) {
					for(int z = 0; z < Chunk.CHUNK_DEPTH; z++) {
						blocks[x][y][z] = Block.blockList[blockBytes[x][i]];
						i++;
					}
				}
			}

			Chunk chunk = new Chunk(blocks, new Point(posX, posZ), this);

			addToMap(chunk);
			
			chunk.calculateVBOData();
			
			alertNeighbours(posX, posZ);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void alertNeighbours(int posX, int posZ) {
		if(isChunkLoaded(posX + 1, posZ)) getChunkAt(posX + 1, posZ).calculateVBOData();
		if(isChunkLoaded(posX - 1, posZ)) getChunkAt(posX - 1, posZ).calculateVBOData();
		if(isChunkLoaded(posX, posZ + 1)) getChunkAt(posX, posZ + 1).calculateVBOData();
		if(isChunkLoaded(posX, posZ - 1)) getChunkAt(posX, posZ - 1).calculateVBOData();
	}

	private void save(Chunk chunk) {
		byte[][] data = new byte[Chunk.CHUNK_WIDTH][Chunk.CHUNK_HEIGHT*Chunk.CHUNK_DEPTH];
		
		for(int x = 0; x < Chunk.CHUNK_WIDTH; x++) {
			int i = 0;
			for(int y = 0; y < Chunk.CHUNK_HEIGHT; y++) {
				for(int z = 0; z < Chunk.CHUNK_DEPTH; z++) {
					data[x][i] = (byte) chunk.getBlockAt(x, y, z).getID();
					i++;
				}
			}
		}
		
		try {
			new File("worlds/"+world.name+"/region").mkdirs();
			
			DataOutputStream dos = new DataOutputStream(new GZIPOutputStream(new FileOutputStream("worlds/"+world.name+"/region/"+chunk.chunkPos.x+"."+chunk.chunkPos.y+".chk")));
			
			for(int i = 0; i < Chunk.CHUNK_WIDTH; i++) {
				dos.write(data[i]);
			}
			
			dos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while(Display.isCreated()) {
			float playerX = MainWindow.camera.x;
			float playerY = MainWindow.camera.z;
			
			this.chunkPos.x = (int) Math.floor(playerX / 16.0F);
			this.chunkPos.y = (int) Math.floor(playerY / 16.0F);
			
			int loadedChunks = 0;
			
			ArrayList<Chunk> unloadChunks = new ArrayList<Chunk>();

			for(Chunk chunk : chunkMap.values()) {
				if(chunk == null) continue;
				
				if(Math.abs(this.chunkPos.x - chunk.chunkPos.x) > Options.viewDistance || Math.abs(this.chunkPos.y - chunk.chunkPos.y) > Options.viewDistance) {
					unloadChunks.add(chunk);
				}
			}
			
			for(Chunk chunk : unloadChunks) {
				this.save(chunk);
				chunkMap.remove(chunk.chunkPos);
			}
			
			for(int x = -viewDistance + chunkPos.x; x <= viewDistance + chunkPos.x; x++) {
				for(int z = -viewDistance + chunkPos.y; z <= viewDistance + chunkPos.y; z++) {
					if(!isChunkLoaded(x, z)) {
						if(new File("worlds/"+world.name+"/region/"+x+"."+z+".chk").exists()) {
							loadChunk(x, z);
						}
						else {
							generateChunk(x, z, world.seed);
						}
						wasModified = true;
					}
					else if(getChunkAt(x, z).isReadyToDraw()) {
						loadedChunks++;
					}
				}
			}
			
			isLoaded = true;
			
			if(loadedChunks == (int) Math.pow(this.viewDistance*2+1, 2) && viewDistance < Options.viewDistance) {
				viewDistance++;
			}
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public FloatBuffer getVBOData() {
		Chunk[] chunks = chunkMap.values().toArray(new Chunk[0]);
		
		ArrayList<FloatBuffer> buffers = new ArrayList<FloatBuffer>();
		
		for(Chunk chunk : chunks) {
			if(chunk == null) continue;
				
			if(chunk.isReadyToDraw()) {
				buffers.add(chunk.getVBOData());
			}
		}
		
		int bufferSize = 0;
		
		for(FloatBuffer buffer : buffers) {
			bufferSize += buffer.capacity();
		}

		FloatBuffer vboData = BufferUtils.createFloatBuffer(bufferSize);

		for(FloatBuffer buffer : buffers) {
			vboData.put(buffer);
		}
		
		vboData.rewind();

		return vboData;
	}
}
