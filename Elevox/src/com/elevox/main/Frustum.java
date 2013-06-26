package com.elevox.main;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;

public class Frustum {
	private static final int RIGHT =  0;
	private static final int LEFT =   1;
	private static final int BOTTOM = 2;
	private static final int TOP =    3;
	private static final int BACK =   4;
	private static final int FRONT =  5;
	
	private static final int A = 0;
	private static final int B = 1;
	private static final int C = 2;
	private static final int D = 3;
	
	float[] proj = new float[16];
	float[] modl = new float[16];
	float[] clip = new float[16];

	FloatBuffer projBuf;
	FloatBuffer modlBuf;
	
	private float[][] frustum = new float[6][4];
	
	public Frustum() {
		projBuf = ByteBuffer.allocateDirect(64).order(ByteOrder.nativeOrder()).asFloatBuffer();
		projBuf.put(proj);
		modlBuf = ByteBuffer.allocateDirect(64).order(ByteOrder.nativeOrder()).asFloatBuffer();
		modlBuf.put(modl);
	}
	
	private void normalizePlane(float[][] frustum, int plane) {
		float magnitude = (float) Math.sqrt(Math.sqrt(frustum[plane][A]) + Math.sqrt(frustum[plane][B]) + Math.sqrt(frustum[plane][C]));
		
		frustum[plane][A] = frustum[plane][A] / magnitude;
		frustum[plane][B] = frustum[plane][B] / magnitude;
		frustum[plane][C] = frustum[plane][C] / magnitude;
		frustum[plane][D] = frustum[plane][D] / magnitude;	
	}
	
	public void calculateFrustum() {
		GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projBuf);
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modlBuf);

		for (int i=0; i < 16; i++) {
			proj[i] = projBuf.get(i);
			modl[i] = modlBuf.get(i);
		}
		
		clip[ 0] = modl[ 0] * proj[ 0] + modl[ 1] * proj[ 4] + modl[ 2] * proj[ 8] + modl[ 3] * proj[12];
		clip[ 1] = modl[ 0] * proj[ 1] + modl[ 1] * proj[ 5] + modl[ 2] * proj[ 9] + modl[ 3] * proj[13];
		clip[ 2] = modl[ 0] * proj[ 2] + modl[ 1] * proj[ 6] + modl[ 2] * proj[10] + modl[ 3] * proj[14];
		clip[ 3] = modl[ 0] * proj[ 3] + modl[ 1] * proj[ 7] + modl[ 2] * proj[11] + modl[ 3] * proj[15];

		clip[ 4] = modl[ 4] * proj[ 0] + modl[ 5] * proj[ 4] + modl[ 6] * proj[ 8] + modl[ 7] * proj[12];
		clip[ 5] = modl[ 4] * proj[ 1] + modl[ 5] * proj[ 5] + modl[ 6] * proj[ 9] + modl[ 7] * proj[13];
		clip[ 6] = modl[ 4] * proj[ 2] + modl[ 5] * proj[ 6] + modl[ 6] * proj[10] + modl[ 7] * proj[14];
		clip[ 7] = modl[ 4] * proj[ 3] + modl[ 5] * proj[ 7] + modl[ 6] * proj[11] + modl[ 7] * proj[15];
		
		clip[ 8] = modl[ 8] * proj[ 0] + modl[ 9] * proj[ 4] + modl[10] * proj[ 8] + modl[11] * proj[12];
		clip[ 9] = modl[ 8] * proj[ 1] + modl[ 9] * proj[ 5] + modl[10] * proj[ 9] + modl[11] * proj[13];
		clip[10] = modl[ 8] * proj[ 2] + modl[ 9] * proj[ 6] + modl[10] * proj[10] + modl[11] * proj[14];
		clip[11] = modl[ 8] * proj[ 3] + modl[ 9] * proj[ 7] + modl[10] * proj[11] + modl[11] * proj[15];
		
		clip[12] = modl[12] * proj[ 0] + modl[13] * proj[ 4] + modl[14] * proj[ 8] + modl[15] * proj[12];
		clip[13] = modl[12] * proj[ 1] + modl[13] * proj[ 5] + modl[14] * proj[ 9] + modl[15] * proj[13];
		clip[14] = modl[12] * proj[ 2] + modl[13] * proj[ 6] + modl[14] * proj[10] + modl[15] * proj[14];
		clip[15] = modl[12] * proj[ 3] + modl[13] * proj[ 7] + modl[14] * proj[11] + modl[15] * proj[15];
	
		frustum[RIGHT][A] = clip[ 3] - clip[ 0];
		frustum[RIGHT][B] = clip[ 7] - clip[ 4];
		frustum[RIGHT][C] = clip[11] - clip[ 8];
		frustum[RIGHT][D] = clip[15] - clip[12];
		normalizePlane(frustum, RIGHT);
		 
		frustum[LEFT][A] = clip[ 3] + clip[ 0];
		frustum[LEFT][B] = clip[ 7] + clip[ 4];
		frustum[LEFT][C] = clip[11] + clip[ 8];
		frustum[LEFT][D] = clip[15] + clip[12];
		normalizePlane(frustum, LEFT);
		 
		frustum[BOTTOM][A] = clip[ 3] + clip[ 1];
		frustum[BOTTOM][B] = clip[ 7] + clip[ 5];
		frustum[BOTTOM][C] = clip[11] + clip[ 9];
		frustum[BOTTOM][D] = clip[15] + clip[13];
		normalizePlane(frustum, BOTTOM);
		 
		frustum[TOP][A] = clip[ 3] - clip[ 1];
		frustum[TOP][B] = clip[ 7] - clip[ 5];
		frustum[TOP][C] = clip[11] - clip[ 9];
		frustum[TOP][D] = clip[15] - clip[13];
		normalizePlane(frustum, TOP);
		 
		frustum[BACK][A] = clip[ 3] - clip[ 2];
		frustum[BACK][B] = clip[ 7] - clip[ 6];
		frustum[BACK][C] = clip[11] - clip[10];
		frustum[BACK][D] = clip[15] - clip[14];
		normalizePlane(frustum, BACK);
		 
		frustum[FRONT][A] = clip[ 3] + clip[ 2];
		frustum[FRONT][B] = clip[ 7] + clip[ 6];
		frustum[FRONT][C] = clip[11] + clip[10];
		frustum[FRONT][D] = clip[15] + clip[14];
		normalizePlane(frustum, FRONT);
	}
	
	public boolean isPointInFrustum(float x, float y, float z) {
		for(int i = 0; i < 6; i++) {
			if((frustum[i][A]*x + frustum[i][B]*y + frustum[i][C]*z + frustum[i][D]) <= 0) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean isCubeInFrustum(float x, float y, float z, float width, float height, float depth) {
		for(int i = 0; i < 6; i++) {
			if((frustum[i][A]*(x-width) + frustum[i][B]*(y-height) + frustum[i][C]*(z-depth) + frustum[i][D]) > 0) continue;
			if((frustum[i][A]*(x+width) + frustum[i][B]*(y-height) + frustum[i][C]*(z-depth) + frustum[i][D]) > 0) continue;
			if((frustum[i][A]*(x-width) + frustum[i][B]*(y+height) + frustum[i][C]*(z-depth) + frustum[i][D]) > 0) continue;
			if((frustum[i][A]*(x+width) + frustum[i][B]*(y+height) + frustum[i][C]*(z-depth) + frustum[i][D]) > 0) continue;
			if((frustum[i][A]*(x-width) + frustum[i][B]*(y-height) + frustum[i][C]*(z+depth) + frustum[i][D]) > 0) continue;
			if((frustum[i][A]*(x+width) + frustum[i][B]*(y-height) + frustum[i][C]*(z+depth) + frustum[i][D]) > 0) continue;
			if((frustum[i][A]*(x+width) + frustum[i][B]*(y+height) + frustum[i][C]*(z+depth) + frustum[i][D]) > 0) continue;
			if(i == 5) return false;
		}
		
		return true;
	}
}
