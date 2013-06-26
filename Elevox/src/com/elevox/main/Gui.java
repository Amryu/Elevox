package com.elevox.main;
/*
 * Diese Klasse stellt eine Grundlage für alle Gui´s da die in
 * dem Programm erscheinen können. So kann jede Gui seine eigene
 * Klasse haben die ein Erbe dieser Klasse darstellt. Die gewünschte
 * Gui wird einfach mittels setActual(Gui gui) festgelegt.
 */

import java.awt.Font;
import java.io.IOException;
import java.util.Iterator;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Gui {
	protected GuiList guiElements = new GuiList();
	
	// Statische Verknüpfung zu dem entsprechendem Fenster (Da es nur eine Gui-Instanz geben kann/sollte)
	protected MainWindow win;
	
	// Aktuelle Gui
	private Gui actualGui;
	
	// Überschriften Font
	protected static TrueTypeFont font = new TrueTypeFont(new Font("Comic Sans", Font.BOLD, 40), true);
	protected static TrueTypeFont h1 = new TrueTypeFont(new Font("Arial", Font.PLAIN, 16), true);
	
	// Gui starten und initialisieren
	public Gui(MainWindow win) {
		this.win = win;
		
		initGui();
	}
	
	public void render() {
		drawObjects();
	}
	
	// Hier werden alle Elemente der Gui gezeichnet
	private void drawObjects() {
		try {
			actualGui.drawBackground();
			
			guiElements = actualGui.getElements();
			
			Iterator<GuiElement> it = (Iterator<GuiElement>) (guiElements.iterator());
			while(it.hasNext()) {
				GuiElement element = it.next();
				if(element.isVisible()) element.paint();
			}
			
			actualGui.drawForeground();
		} catch (NullPointerException e) {/* Fehlermeldungen beim Start abfangen */}
	}
	
	public void drawForeground() {
		
	}
	
	public void drawBackground() {
		
	}
	
	public void run() {
		Iterator<GuiElement> it = guiElements.iterator();
		while(it.hasNext()) it.next().run();
		
		actualGui.run();
	}
	
	protected void initGui() {
		guiElements = new GuiList();
	}
	
	// getter-Methoden
	public GuiList getElements() {
		return guiElements;
	}
	public Gui getActualGui() {
		return actualGui;
	}
	
	// Wird bei Buttondruck ausgelöst
	public void buttonClicked(String action) {
		
	}
	
	// Wird beim verändern des Zustandes von einer Checkbox ausgelöst
	public void onChecked(GuiCheckbox guiCheckbox) {
		
	}
	
	// setter-Methoden
	public void setActualGui(Gui gui) {
		actualGui = gui;
	}
	
	// Hilfsfunktion zum Bilder laden
	public static Texture createImage(String pfad) {
		try {
			if(pfad.endsWith(".png")) 
				return TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(pfad));
			if(pfad.endsWith(".jpg"))
				return TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream(pfad));
			if(pfad.endsWith(".gif"))
				return TextureLoader.getTexture("GIF", ResourceLoader.getResourceAsStream(pfad));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	

	public static void enterOrtho() {
		// store the current state of the renderer
		GL11.glPushAttrib(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_ENABLE_BIT);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_PROJECTION); 
		GL11.glPushMatrix();
			
		// now enter orthographic projection
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 800, 600, 0, -1, 1);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);
	}

	public static void leaveOrtho() {
		// restore the state of the renderer
		GL11.glPopMatrix();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPopMatrix();
		GL11.glPopAttrib();
	}
}
