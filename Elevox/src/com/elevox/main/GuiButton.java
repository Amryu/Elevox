package com.elevox.main;
/*
 * Diese Klasse stellt einen Button dar der 3 Bilder aufnehmen kann
 * sowie individuell beschriftet werden kann (inkl. verschiedene Fonts)
 * Man kann ihn auch mittels setHorizontal/VerticalAlignment() ausrichten
 * (z.B. zentriert und am Boden des Fensters.
 * Diese Klasse benutzt die drei Bilder für einen normalen Zustand, den Zustand
 * während der Mauszeiger über dem Button schwebt und den Zustand während die
 * Maustaste gedrückt ist während sie über dem Button schwebt.
 */

import java.awt.Point;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.Display;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

public class GuiButton extends GuiElement {
	// globale Variablen
	// Buttonbilder
	private static Texture normButton = Gui.createImage("buttonNormal.png");
	private static Texture activeButton = Gui.createImage("buttonActive.png");
	private static Texture hoverButton = Gui.createImage("buttonHover.png");
	
	private Texture actualButton = normButton;
	
	// Nummer des aktuellen Bildes
	private int drawNum = 0;

	String action; 		// String für den ActionListener
	String buttonText; 	// Text der auf dem Button steht
	
	// Konstruktor-Methode - Hier wird der Button erstellt, d.h. die Position, die Bilder,
	// der Text auf dem Button und der Aktionstext der an buttonClicked() in der Gui gesendet wird
	public GuiButton(MainWindow win, Point bounds, Texture normButton, Texture activeButton, Texture hoverButton, String buttonText, String action) {
		super(win);
		
		setPosition(bounds);
		
		this.setNormalButton(normButton);
		this.setActiveButton(activeButton);
		this.setHoverButton(hoverButton);
		
		setAction(action);
		setButtonText(buttonText);
	}
	// Konstruktor-Methode (Statt eigener Bilder Standardbilder laden die immer im Projekt enthalten sind/sein sollten (Ich habe ein "leeres" Template))
	public GuiButton(MainWindow win, Point bounds, String buttonText, String action) {
		super(win);
		
		setPosition(bounds);
		
		setAction(action);
		setButtonText(buttonText);
	}
	
	// run()-Methode die den Button je nach Ausrichtung platziert und den Button-Status verändert
	// z.B. wenn die Maus über dem Button ist oder die Maus auf den Button klickt. Auch das
	// buttonClicked-Event wird hier ausgelöst
	public void run() {
		try {
			if(drawNum == 1 && !win.leftMouseDown) {
				MainWindow.gui.getActualGui().buttonClicked(getAction());
			}
			
			// Den Status des Buttons festlegen
			int mouseX = win.mouseX;
			int mouseY = Math.abs(win.mouseY - Display.getDisplayMode().getHeight()) + 2;
			if (mouseX >= getX() && mouseY >= getY() && mouseX < getWidth() + getX() && mouseY < getHeight() + getY()) {
				if (win.leftMouseDown) {
					setDrawNum(1);
				} else {
					setDrawNum(2);
				}
			} else {
				setDrawNum(0);
			}
			
			if(drawNum == 0) actualButton = normButton;
			if(drawNum == 1) actualButton = activeButton;
			if(drawNum == 2) actualButton = hoverButton;
			if(actualButton == null) return;
			
			// Button korrekt ausrichten (je nach festgelegter Ausrichtung)
			super.run();
			
			// Breite/Höhe des aktuellen Bildes zur aktuellen Breite/Höhe des Buttons machen und dabei neu positionieren
			this.setWidth(actualButton.getImageWidth());
			this.setHeight(actualButton.getImageHeight());
			this.setX(getUnmodifiedX() - (actualButton.getImageWidth() - normButton.getImageWidth()) / 2);
			this.setY(getUnmodifiedY() - (actualButton.getImageHeight() - normButton.getImageHeight()) / 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Hier wird der Button gemalt und die Position des Textes errechnet und gemalt
	// (Das errechnen der Textposition ist nur hier möglich da man die Graphics-Variable benötigt)
	public void paint() {
		Color.white.bind();
		actualButton.bind(); // or GL11.glBind(texture.getTextureID());
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0,0);
			GL11.glVertex2f(getX(),getY());
			GL11.glTexCoord2f(1,0);
			GL11.glVertex2f(getX()+actualButton.getImageWidth(),getY());
			GL11.glTexCoord2f(1,1);
			GL11.glVertex2f(getX()+actualButton.getImageWidth(),getY()+actualButton.getImageHeight());
			GL11.glTexCoord2f(0,1);
			GL11.glVertex2f(getX(),getY()+actualButton.getImageHeight());
		GL11.glEnd();
		
		int stringWidth = getFont().getWidth(buttonText);
		int stringHeight = getFont().getHeight();
		
		int stringX = getX() + getWidth() / 2 - stringWidth / 2 - 5;
		int stringY = getY() + getHeight() / 2 + stringHeight / 2 - 17;

		FontRenderer.drawString(stringX, stringY, buttonText, getFont(), getColor());
	}

	// setter-Methoden //
	public void setNormalButton(Texture texture) {
		GuiButton.normButton = texture;
	}
	public void setActiveButton(Texture texture) {
		GuiButton.activeButton = texture;
	}
	public void setHoverButton(Texture texture) {
		GuiButton.hoverButton = texture;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public void setDrawNum(int drawNum) {
		this.drawNum = drawNum;
	}	
	private void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}

	// getter-Methoden //

	public String getAction() {
		return action;
	}
	public int getDrawNum() {
		return drawNum;
	}
}
