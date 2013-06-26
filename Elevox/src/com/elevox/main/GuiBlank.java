package com.elevox.main;
import java.awt.Graphics;

public class GuiBlank extends Gui {
	private GuiList guiElements = new GuiList();
	
	public GuiBlank(MainWindow win) {
		super(win);
		
		// Buttons und Texte hinzufügen
	}
	
	// Alle Bilder o.ä. die hinter allem anderen stehen sollen malen
	public void drawBackground(Graphics g) {
		
	}
	
	// Alle Bilder o.ä. die vor allem anderen stehen sollen malen
	public void drawForeground(Graphics g) {
		
	}
	
	// Schleife nutzen um wiederholte Aufgaben auszuführen. (Wird z.B. benutzt in GuiButton oder GuiText)
	public void run() {
		
	}
	
	// getter-Methoden
	public GuiList getElements() {
		return guiElements;
	}
	
	// Buttondrücke werden hier bearbeitet
	public void buttonClicked(String action) {
		
	}
	
	// Wird beim verändern des Zustandes von einer Checkbox ausgelöst
	public void onChecked(GuiCheckbox guiCheckbox) {
		
	}
}
