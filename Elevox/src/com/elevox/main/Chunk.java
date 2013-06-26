package com.elevox.main;
import java.awt.Point;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

public class Chunk {
	public static final int CHUNK_WIDTH = 16;
	public static final int CHUNK_HEIGHT = 256;
	public static final int CHUNK_DEPTH = 16;
	
	public BlockStorage[] blockStorage = new BlockStorage[16];
	
	public final Point chunkPos;
	
	private boolean isReadyToDraw = false;
	private boolean refillBuffer = false;
	
	public int vertexBufferId = 0;
	
	private int bufferSize = 0;
	
	public Chunk(Block[][][] blocks, Point chunkPos, ChunkMap chunkMap) {
		this.chunkPos = chunkPos;
		
		for(int i = 0; i < 16; i++) {
			Block[][][] blockArray = new Block[16][16][16];
			
			for(int x = 0; x < 16; x++) {
				for(int y = 0; y < 16; y++) {
					for(int z = 0; z < 16; z++) {
						blockArray[x][y][z] = blocks[x][y+i*16][z];
					}
				}
			}
			
			blockStorage[i] = new BlockStorage(blockArray, i, this, chunkMap);
		}
		
		vertexBufferId = MainWindow.vertexBufferIds.remove(0);
	}
	
	public void calculateVBOData() {
		for(int i = 0; i < 16; i++) {
			blockStorage[i].calculateVBOData();
		}
		
		refillBuffer = true;
		
		isReadyToDraw = true;
	}
	
	public FloatBuffer getVBOData() {
		ArrayList<FloatBuffer> buffers = new ArrayList<FloatBuffer>();
		
		for(int i = 0; i < 16; i++) {
			buffers.add(blockStorage[i].vboData);
			
			blockStorage[i].vboData.rewind();
		}
		
		bufferSize = 0;
		
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
	
	public void render() {
		if(this.isReadyToDraw()) {
			if(refillBuffer) {
				refillBuffer = false;
				GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexBufferId);
			    GL15.glBufferData(GL15.GL_ARRAY_BUFFER, getVBOData(), GL15.GL_DYNAMIC_DRAW);
			}
		    
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexBufferId);
			
		    GL11.glVertexPointer(3, GL11.GL_FLOAT, 40, 0);
		    GL11.glNormalPointer(GL11.GL_FLOAT, 40, 12);
		    GL11.glColorPointer(4, GL11.GL_FLOAT, 40, 24);
		    
		    GL11.glDrawArrays(GL11.GL_QUADS, 0, bufferSize);
	    }
	}
	
	public Block getBlockAt(int x, int y, int z) {
		int blockStorageNum = (int) Math.floor(y / 16.0F);
		
		return blockStorage[blockStorageNum].blocks[x][y % 16][z];
	}
	
	public void setBlockAt(Block block, int x, int y, int z) {
		int blockStorageNum = (int) Math.floor(y / 16.0F);
		
		blockStorage[blockStorageNum].blocks[x][y % 16][z] = block;
	}
	
	public boolean isReadyToDraw() {
		return isReadyToDraw;
	}

	public int getX() {
		return chunkPos.x;
	}

	public int getZ() {
		return chunkPos.y;
	}
}
