package com.elevox.main;
/*
 * Diese Klasse stellt ein Textfeld mit oder ohne Passwortmodus dar.
 * Man Kann dieses beliebig ausrichten (z.B. zentriert und am Boden des Fensters)
 * und seine eigenen Bilder benutzen.
 */

import java.awt.Point;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

public class GuiTextField extends GuiElement {
	//globale Variablen
	
	// maximale Anzahl Zeichen im Textfeld
	private int maxlength = 14;
	
	// erlaubte Zeichen werden hier gespeichert
	private ArrayList<Character> allowedChars = new ArrayList<Character>();
	
	// Enthält zuletzt getipptes Zeichen
	private Character ch = ' ';
	
	// Bilder für das Textfeld
	private Texture normalField;
	private Texture activeField;
	
	private Texture actualField;
	
	// Nummer des aktuellen Bildes
	private int drawNum = 0;
	
	private long delay = 0;
	
	// Farbe
	protected Color color = Color.black;
	
	// Textfeldoptionen
	private boolean password = false;
	private boolean selected = false;
	
	// Text der momentan im Textfeld enthalten ist
	private String text = "";
	
	// Text der Taste die gedrückt wurde
	private String keyText = "";
	
	// Konstruktor-Methoden - Hier wird das Textfeld erstellt. Dabei wird die Position festgelegt
	// sowie die Bilder für das Textfeld. Optional kann auch ein Starttext festgelegt werden.
	public GuiTextField(MainWindow win, Point pos, Texture normalField, Texture activeField) {
		super(win);
		
		this.setX(pos.x);
		this.setY(pos.y);
		
		this.setNormalField(normalField);
		this.setActiveField(activeField);
	}

	public GuiTextField(MainWindow win, Point pos, Texture normalField, Texture activeField, String text) {
		super(win);
		
		this.setX(pos.x);
		this.setY(pos.y);
		
		this.setNormalField(normalField);
		this.setActiveField(activeField);

		this.text = text;
	}
	// Konstruktor-Methoden (Statt eigener Bilder Standardbilder laden die immer im Projekt enthalten sind/sein sollten (Ich habe ein "leeres" Template))
	public GuiTextField(MainWindow win, Point pos) {
		super(win);
		
		this.setX(pos.x);
		this.setY(pos.y);
		
		this.setNormalField(Gui.createImage("fieldNormal.png"));
		this.setActiveField(Gui.createImage("fieldActive.png"));
	}

	public GuiTextField(MainWindow win, Point pos, String text) {
		super(win);
		
		this.setPosition(pos);
		
		this.setNormalField(Gui.createImage("fieldNormal.png"));
		this.setActiveField(Gui.createImage("fieldActive.png"));

		this.text = text;
	}
	
	// run()-Methode die für die Ausrichtung des Textfeldes zuständig ist
	// sowie die Größe des Textfeldes an das entsprechende Bild anpasst
	public void run() {
		super.run();
		
		// Tastatureingaben prüfen
		while(Keyboard.next()) {
			if(Keyboard.getEventNanoseconds() <= delay || Keyboard.getEventCharacter() == ch) break;
			keyText = Keyboard.getKeyName(Keyboard.getEventKey());
			ch = Keyboard.getEventCharacter();
			System.out.println(ch);
			
			try {
				if (keyText.equals("BACK") && selected && text.length() > 0) {
					char[] chars = text.toCharArray();
					text = "";
					chars[chars.length - 1] = ' ';
					for (int i = 0; i <= chars.length - 1; i++) {
						text += chars[i];
					}
					text = text.trim();
				} else if (ch != null && selected && text.length() < maxlength && !keyText.equals("BACK")) {
					if(allowedChars.size() > 0) {
						if(allowedChars.contains(ch)) {
							text += ch;
						}
					}
					else {
						text += ch;
					}
				}
			} catch (NullPointerException e) {e.printStackTrace();}
			delay = Keyboard.getEventNanoseconds() + 200000000L;
		}
		
		
		// Falls die Bilder unterschiedliche Größen haben Werte anpassen
		if(getDrawNum() == 0) {
			this.setWidth(normalField.getImageWidth());
			this.setHeight(normalField.getImageHeight());
		}
		else if(getDrawNum() == 1) {
			this.setX(getUnmodifiedX() - (activeField.getImageWidth() - normalField.getImageWidth()) / 2);
			this.setY(getUnmodifiedY() - (activeField.getImageHeight() - normalField.getImageHeight()) / 2);
			this.setWidth(activeField.getImageWidth());
			this.setHeight(activeField.getImageHeight());
		}
	}
	
	// Alle Objekte des Textfeldes zeichnen also aktuelles Bild und Text. Ausserdem prüfen ob
	// auf das Textfeld gedrückt wurde also ausgewählt wurde.
	public void paint() {
		try {
			if (!selected) {
				actualField = normalField;
				setDrawNum(0);
			}
			else {
				actualField = activeField;
				setDrawNum(1);
			}

			int mouseX = win.mouseX;
			int mouseY = Math.abs(win.mouseY - Display.getDisplayMode().getHeight());
			if (mouseX >= getX() && mouseY >= getY() && mouseX < getWidth() + getX() && mouseY < getHeight() + getY() && drawNum == 0 && win.leftMouseDown) {
				selected = true;
			}
			else if(!(mouseX >= getX()) || !(mouseY >= getY()) || !(mouseX < getWidth() + getX()) || !(mouseY < getHeight() + getY())) {
				if(drawNum == 1 && win.leftMouseDown) {
					selected = false;
				}
			}

			Color.white.bind();
			actualField.bind(); // or GL11.glBind(texture.getTextureID());
	 
			GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0,0);
				GL11.glVertex2f(getX(),getY());
				GL11.glTexCoord2f(1,0);
				GL11.glVertex2f(getX()+actualField.getTextureWidth(),getY());
				GL11.glTexCoord2f(1,1);
				GL11.glVertex2f(getX()+actualField.getTextureWidth(),getY()+actualField.getTextureHeight());
				GL11.glTexCoord2f(0,1);
				GL11.glVertex2f(getX(),getY()+actualField.getTextureHeight());
			GL11.glEnd();
			
			int stringHeight = getFont().getHeight();
			
			int stringY = getY() + getHeight() / 2 + stringHeight / 2 - 3 - 13;
			
			if (password) {
				String passwordText = "";
				for (int i = 0; i < text.length(); i++) {
					passwordText += "•";
				}
				FontRenderer.drawString(getX() + 5, stringY, passwordText, getFont(), getColor());
			} else {
				FontRenderer.drawString(getX() + 5, stringY, text, getFont(), getColor());
			}
		} catch (NullPointerException e) {
			
		}
	}

	// getter/is-Methoden //
	public String getText() {
		return text.toString();
	}
	public boolean isSelected() {
		return selected;
	}
	public Texture getNormalField() {
		return normalField;
	}
	public Texture getActiveField() {
		return activeField;
	}
	public int getDrawNum() {
		return drawNum;
	}
	public boolean getPasswordField() {
		return password;
	}
	public int getMaxlength() {
		return maxlength;
	}
	public ArrayList<Character> getAllowedCharacters() {
		return allowedChars;
	}
	public Color getColor() {
		return color;
	}

	// setter-Methoden //
	public void setText(String text) {
		this.text = text;
	}
	public void setNormalField(Texture normalField) {
		this.normalField = normalField;
	}
	public void setActiveField(Texture activeField) {
		this.activeField = activeField;
	}
	public void setDrawNum(int drawNum) {
		this.drawNum = drawNum;
	}
	public void setPasswordField(boolean password) {
		this.password = password;
	}
	public void setMaxlength(int maxlength) {
		this.maxlength = maxlength;
	}
	public void setAllowedCharacters(ArrayList<Character> allowedChars) {
		this.allowedChars = allowedChars;
	}
}
