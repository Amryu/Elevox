import java.util.Random;


public class Color {
	public float r, g, b, a;
	
	public Color(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public float[] getColorWithRandomChange(Random ran) {
		float r = this.r;
		float g = this.g;
		float b = this.b;
		
		float ranModifier = (float) ran.nextInt(21) / 100.0f - 0.1f;
		
		r += ranModifier;
		g += ranModifier;
		b += ranModifier;
		
		if(r > 1.0f) r = 1.0f;
		if(g > 1.0f) g = 1.0f;
		if(b > 1.0f) b = 1.0f;
		
		if(r < 0.0f) r = 0.0f;
		if(g < 0.0f) g = 0.0f;
		if(b < 0.0f) b = 0.0f;
		
		return new float[] {r,g,b,a};
	}
}
