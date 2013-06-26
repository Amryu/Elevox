package com.elevox.main;
/*
 * Diese Klasse stellt einen einzeiligen String dar den man einen
 * beliebigen Font zuweisen kann, eine beliebige Farbe zuweisen kann
 * sowie beliebig ausrichten (mittels setHorizontal/VerticalAlignment()).
 * (z.B. zentriert und am Boden des Fensters)
 */

import java.awt.Point;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

public class GuiText extends GuiElement {
	// Anzuzeigender Text
	String text = "";
	
	// Kostruktor-Methoden - Hier gibt es mehrere Kosntruktor-Methoden bei denen man
	// optional Font und Farbe verändern kann. Ansonsten wird hier die Position festgelegt
	// sowie der entsprechende Text
	public GuiText(MainWindow win, String text, Point pos) {
		super(win);
		
		this.text = text;

		this.setPosition(pos);
	}
	
	public GuiText(MainWindow win, String text, Point pos, TrueTypeFont font) {
		super(win);
		
		this.text = text;
		
		this.setPosition(pos);
		
		this.font = font;
	}
	
	public GuiText(MainWindow win, String text, Point pos, Color color) {
		super(win);
		
		this.text = text;

		this.setPosition(pos);
		
		this.color = color;
	}
	
	public GuiText(MainWindow win, String text, Point pos, TrueTypeFont font, Color color) {
		super(win);
		
		this.text = text;
		
		this.setPosition(pos);
		
		this.font = font;
		this.color = color;
	}

	// Methode die den Text mit entsprechendem Font und entsprechender Farbe zeichnet
	// Ausserdem wird hier die Größe des Textes ermittelt
	public void paint() {
		setWidth(getFont().getWidth(text));
		setHeight(getFont().getHeight());
		
		FontRenderer.drawString(getX(), getY(), getText(), getFont(), getColor());
	}
	
	// setter-Methoden //
	public void setText(String text) {
		this.text = text;
	}
	
	// getter-Methoden //
	public String getText() {
		return text;
	}
}
