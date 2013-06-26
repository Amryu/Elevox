package com.elevox.main;
import java.awt.Graphics;
import java.awt.Point;

public class GuiDebug extends Gui {
	private GuiList guiElements = new GuiList();
	
	private GuiText x, y, z, pitch, yaw, chunkPos;
	
	private FPCameraController camera;
	private ChunkMap chunkMap;
	
	public GuiDebug(MainWindow win, FPCameraController camera, ChunkMap chunkMap) {
		super(win);

		this.camera = camera;
		this.chunkMap = chunkMap;
		
		x = new GuiText(win, "X: "+camera.x, new Point(5, 5));
		guiElements.add(x);
		
		y = new GuiText(win, "Y: "+camera.y, new Point(5, 20));
		guiElements.add(y);
		
		z = new GuiText(win, "Z: "+camera.z, new Point(5, 35));
		guiElements.add(z);
		
		pitch = new GuiText(win, "Pitch: "+camera.pitch, new Point(5, 50));
		guiElements.add(pitch);
		
		yaw = new GuiText(win, "Yaw: "+camera.yaw, new Point(5, 65));
		guiElements.add(yaw);
		
		chunkPos = new GuiText(win, "Chunk: "+chunkMap.chunkPos.x+"/"+chunkMap.chunkPos.y, new Point(5, 80));
		guiElements.add(chunkPos);
	}
	
	// Alle Bilder o.ä. die hinter allem anderen stehen sollen malen
	public void drawBackground(Graphics g) {
		
	}
	
	// Alle Bilder o.ä. die vor allem anderen stehen sollen malen
	public void drawForeground(Graphics g) {
		
	}
	
	// Schleife nutzen um wiederholte Aufgaben auszuführen. (Wird z.B. benutzt in GuiButton oder GuiText)
	public void run() {
		x.setText("X: "+camera.x);
		y.setText("Y: "+camera.y);
		z.setText("Z: "+camera.z);
		pitch.setText("Pitch: "+camera.pitch);
		yaw.setText("Yaw: "+camera.yaw);
		chunkPos.setText("Chunk: "+chunkMap.chunkPos.x+"/"+chunkMap.chunkPos.y);
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
