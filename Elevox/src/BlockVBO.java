import java.util.Random;

public class BlockVBO {
	public static float[] topVBO(float x, float y, float z, Color color, Random ran) {
		float[] ranColors = color.getColorWithRandomChange(ran);
		
		return new float[] {
				0.0f + x, 1.0f + y, 0.0f + z, 0.0f, 1.0f, 0.0f, ranColors[0], ranColors[1], ranColors[2], ranColors[3],
			    0.0f + x, 1.0f + y, 1.0f + z, 0.0f, 1.0f, 0.0f, ranColors[0], ranColors[1], ranColors[2], ranColors[3],
			    1.0f + x, 1.0f + y, 1.0f + z, 0.0f, 1.0f, 0.0f, ranColors[0], ranColors[1], ranColors[2], ranColors[3],
			    1.0f + x, 1.0f + y, 0.0f + z, 0.0f, 1.0f, 0.0f, ranColors[0], ranColors[1], ranColors[2], ranColors[3],
		};
	}
	
	public static float[] bottomVBO(float x, float y, float z, Color color, Random ran) {
		float[] ranColors = color.getColorWithRandomChange(ran);
		
		return new float[] {
				0.0f + x, 0.0f + y, 0.0f + z, 0.0f, 0.0f, 0.0f, ranColors[0], ranColors[1], ranColors[2], ranColors[3],
			    0.0f + x, 0.0f + y, 1.0f + z, 0.0f, 0.0f, 0.0f, ranColors[0], ranColors[1], ranColors[2], ranColors[3],
			    1.0f + x, 0.0f + y, 1.0f + z, 0.0f, 0.0f, 0.0f, ranColors[0], ranColors[1], ranColors[2], ranColors[3],
			    1.0f + x, 0.0f + y, 0.0f + z, 0.0f, 0.0f, 0.0f, ranColors[0], ranColors[1], ranColors[2], ranColors[3],
		};
	}
	
	public static float[] leftVBO(float x, float y, float z, Color color, Random ran) {
		float[] ranColors = color.getColorWithRandomChange(ran);
		
		return new float[] {
				0.0f + x, 1.0f + y, 0.0f + z, 0.0f, 0.0f, 0.0f, ranColors[0], ranColors[1], ranColors[2], ranColors[3],
			    0.0f + x, 1.0f + y, 1.0f + z, 0.0f, 0.0f, 0.0f, ranColors[0], ranColors[1], ranColors[2], ranColors[3],
			    0.0f + x, 0.0f + y, 1.0f + z, 0.0f, 0.0f, 0.0f, ranColors[0], ranColors[1], ranColors[2], ranColors[3],
			    0.0f + x, 0.0f + y, 0.0f + z, 0.0f, 0.0f, 0.0f, ranColors[0], ranColors[1], ranColors[2], ranColors[3],
		};
	}
	
	public static float[] rightVBO(float x, float y, float z, Color color, Random ran) {
		float[] ranColors = color.getColorWithRandomChange(ran);
		
		return new float[] {
				1.0f + x, 1.0f + y, 0.0f + z, 1.0f, 0.0f, 0.0f, ranColors[0], ranColors[1], ranColors[2], ranColors[3],
			    1.0f + x, 1.0f + y, 1.0f + z, 1.0f, 0.0f, 0.0f, ranColors[0], ranColors[1], ranColors[2], ranColors[3],
			    1.0f + x, 0.0f + y, 1.0f + z, 1.0f, 0.0f, 0.0f, ranColors[0], ranColors[1], ranColors[2], ranColors[3],
			    1.0f + x, 0.0f + y, 0.0f + z, 1.0f, 0.0f, 0.0f, ranColors[0], ranColors[1], ranColors[2], ranColors[3],
		};
	}
	
	public static float[] frontVBO(float x, float y, float z, Color color, Random ran) {
		float[] ranColors = color.getColorWithRandomChange(ran);
		
		return new float[] {
				1.0f + x, 1.0f + y, 0.0f + z, 0.0f, 0.0f, 0.0f, ranColors[0], ranColors[1], ranColors[2], ranColors[3],
			    0.0f + x, 1.0f + y, 0.0f + z, 0.0f, 0.0f, 0.0f, ranColors[0], ranColors[1], ranColors[2], ranColors[3],
			    0.0f + x, 0.0f + y, 0.0f + z, 0.0f, 0.0f, 0.0f, ranColors[0], ranColors[1], ranColors[2], ranColors[3],
			    1.0f + x, 0.0f + y, 0.0f + z, 0.0f, 0.0f, 0.0f, ranColors[0], ranColors[1], ranColors[2], ranColors[3],
		};
	}
	
	public static float[] backVBO(float x, float y, float z, Color color, Random ran) {
		float[] ranColors = color.getColorWithRandomChange(ran);
		
		return new float[] {
				1.0f + x, 1.0f + y, 1.0f + z, 0.0f, 0.0f, 1.0f, ranColors[0], ranColors[1], ranColors[2], ranColors[3],
			    0.0f + x, 1.0f + y, 1.0f + z, 0.0f, 0.0f, 1.0f, ranColors[0], ranColors[1], ranColors[2], ranColors[3],
			    0.0f + x, 0.0f + y, 1.0f + z, 0.0f, 0.0f, 1.0f, ranColors[0], ranColors[1], ranColors[2], ranColors[3],
			    1.0f + x, 0.0f + y, 1.0f + z, 0.0f, 0.0f, 1.0f, ranColors[0], ranColors[1], ranColors[2], ranColors[3],
		};
	}
}
