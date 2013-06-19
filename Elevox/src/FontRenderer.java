import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import java.awt.Font;

public class FontRenderer {
	private static TrueTypeFont font = new TrueTypeFont(new Font("Arial", Font.PLAIN, 24), true);
	
	public static void drawString(int x, int y, String text) {
		org.newdawn.slick.opengl.TextureImpl.unbind();
		font.drawString(x, y, text);
	}
	
	public static void drawString(int x, int y, String text, TrueTypeFont font) {
		org.newdawn.slick.opengl.TextureImpl.unbind();
		font.drawString(x, y, text);
	}

	public static void drawString(int x, int y, String text, Color color) {
		org.newdawn.slick.opengl.TextureImpl.unbind();
		font.drawString(x, y, text, color);
	}

	public static void drawString(int x, int y, String text, TrueTypeFont font, Color color) {
		org.newdawn.slick.opengl.TextureImpl.unbind();
		font.drawString(x, y, text, color);
	}
}
