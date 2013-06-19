import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import java.awt.Font;

public class FontRenderer {
	private static TrueTypeFont font = new TrueTypeFont(new Font("Comic Sans MS", Font.PLAIN, 50), true);
	
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
