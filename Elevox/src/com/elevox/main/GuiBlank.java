package com.elevox.main;
import java.awt.Graphics;

public class GuiBlank extends Gui {
	private GuiList guiElements = new GuiList();
	
	public GuiBlank(MainWindow win) {
		super(win);
		
		// Buttons und Texte hinzuf�gen
	}
	
	// Alle Bilder o.�. die hinter allem anderen stehen sollen malen
	public void drawBackground(Graphics g) {
		
	}
	
	// Alle Bilder o.�. die vor allem anderen stehen sollen malen
	public void drawForeground(Graphics g) {
		
	}
	
	// Schleife nutzen um wiederholte Aufgaben auszuf�hren. (Wird z.B. benutzt in GuiButton oder GuiText)
	public void run() {
		
	}
	
	// getter-Methoden
	public GuiList getElements() {
		return guiElements;
	}
	
	// Buttondr�cke werden hier bearbeitet
	public void buttonClicked(String action) {
		
	}
	
	// Wird beim ver�ndern des Zustandes von einer Checkbox ausgel�st
	public void onChecked(GuiCheckbox guiCheckbox) {
		
	}
}
