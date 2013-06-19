/*
 * Diese Klasse stellt die Grundlage eines Gui-Elementes dar. Es ist von Objekt abgeleitet
 * und enthält Variablen wie z.B. die Ausrichtung, Position und Größe.
 */

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

public class GuiElement extends Object {
	// globale Variablen
	
	// Unbearbeiteter x/y-Wert
	private int giveX = 0;
	private int giveY = 0;
	
	// Bearbeiteter x/y-Wert
	private int x = 0;
	private int y = 0;
	
	// Größe
	private int width = 0;
	private int height = 0;
	
	// Ausrichtung
	private int horizontalAlignment = 0;
	private int verticalAlignment = 0;

	// Klassenvariablen um Ausrichtung zu erleichtern
	public static final int LEFT = 0;
	public static final int CENTER = 1;
	public static final int RIGHT = 2;
	
	public static final int TOP = 0;
	public static final int MIDDLE = 1;
	public static final int BOTTOM = 2;
	
	// Verknüpfung zum Fenster
	protected MainWindow win = null;
	
	// Font-Optionen (nicht immer benötigt)
	// gewünschter Font
	protected static TrueTypeFont standardFont = new TrueTypeFont(new Font("Arial", Font.BOLD, 14), false);
	protected TrueTypeFont font = standardFont;
	
	// Gewünschte Farbe
	protected Color color = Color.white;
	
	// Sichtbarkeit
	protected boolean visible = true;
	
	public GuiElement(MainWindow win) {
		this.win = win;
	}
	
	// run()-Methode die das Objekt je nach Ausrichtung platziert
	public void run() {
		// Horizontale Ausrichtung (halign)
		if (getHorizontalAlignment() == 0) {
			setModifiedX(getUnmodifiedX());
		} else if (getHorizontalAlignment() == 1) {
			setModifiedX((int) (win.width / 2 - getWidth() / 2) + getUnmodifiedX());
		} else if (getHorizontalAlignment() == 2) {
			setModifiedX((int) (win.width - getWidth()) + getUnmodifiedX());
		}
		// Vertikale Ausrichtung (valign)
		if (getVerticalAlignment() == 0) {
			setModifiedY(getUnmodifiedY());
		} else if (getVerticalAlignment() == 1) {
			setModifiedY((int) (win.height / 2 - getHeight() / 2) + getUnmodifiedY());
		} else if (getVerticalAlignment() == 2) {
			setModifiedY((int) (win.height - getHeight()) + getUnmodifiedY());
		}
	}
	
	// drawObjects-Methode -  Diese wird vererbt und je nach Elementtyp anders benutzt
	public void paint() {
		
	}
	
	// getter/is-Methoden //
    public int getX() {
		return x;
    }
    public int getY() {
		return y;
	}
	public int getUnmodifiedX() {
		return giveX;
	}
	public int getUnmodifiedY() {
		return giveY;
	}
    public Point getPosition() {
    	return new Point(x, y);
    }
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public Dimension getSize() {
		return new Dimension(width, height);
	}
	public int getHorizontalAlignment() {
		return horizontalAlignment;
	}
	public int getVerticalAlignment() {
		return verticalAlignment;
	}
	public TrueTypeFont getFont() {
		return font;
	}
	public Color getColor() {
		return color;
	}
	public boolean isVisible() {
		return visible;
	}
	
	// setter-Methoden //
	public void setX(int x) {
		this.giveX = x;
	}
	public void setY(int y) {
		this.giveY = y;
	}
	public void setModifiedX(int x) {
		this.x = x;
	}
	public void setModifiedY(int y) {
		this.y = y;
	}
	public void setPosition(int x, int y) {
		this.setX(x);
		this.setY(y);
	}
	public void setPosition(Point point) {
		this.setX(point.x);
		this.setY(point.y);
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	public void setSize(Dimension dimension) {
		this.width = dimension.width;
		this.height = dimension.height;
	}
	public void setBounds(int x, int y, int width, int height) {
		this.setX(x);
		this.setY(y);
		this.width = width;
		this.height = height;
	}
	public void setHorizontalAlignment(int horizontalAlignment) {
		this.horizontalAlignment = horizontalAlignment;
	}
	public void setVerticalAlignment(int verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
	}
	public void setAlignment(int horizontalAlignment, int verticalAlignment) {
		this.horizontalAlignment = horizontalAlignment;
		this.verticalAlignment = verticalAlignment;
	}
	public void setFont(TrueTypeFont font) {
		this.font = font;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
