package com.elevox.main;
import java.awt.Point;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

public class GuiCheckbox extends GuiElement {
	// globale Variablen
	// Checkboxbilder
	private Texture normalBox;
	private Texture activeBox;
	private Texture hoverBox;
	private Texture normalBoxChecked;
	private Texture activeBoxChecked;
	private Texture hoverBoxChecked;
	
	private Texture actualBox;
	
	private boolean isChecked = true;
	
	// Nummer des aktuellen Bildes
	private int drawNum = 0;
	
	public GuiCheckbox(MainWindow win, Point pos, boolean checked) {
		super(win);
		
		this.setChecked(checked);
		
		this.setPosition(pos);
		
		this.setNormalBox(Gui.createImage("boxNormal.png"));
		this.setActiveBox(Gui.createImage("boxActive.png"));
		this.setHoverBox(Gui.createImage("boxHover.png"));
		this.setNormalBoxChecked(Gui.createImage("boxNormalChecked.png"));
		this.setActiveBoxChecked(Gui.createImage("boxActiveChecked.png"));
		this.setHoverBoxChecked(Gui.createImage("boxHoverChecked.png"));
	}

	// run()-Methode die den Button je nach Ausrichtung platziert und den Button-Status verändert
	// z.B. wenn die Maus über dem Button ist oder die Maus auf die Checkbox klickt.
	public void run() {
		if((drawNum == 1 || drawNum == 4) && !win.leftMouseDown) {
			setChecked(!isChecked());
			MainWindow.gui.getActualGui().onChecked(this);
		}
		
		// Den Status des Buttons festlegen
		if (win.mouseX >= getX() && win.mouseY >= getY() && win.mouseX < getWidth() + getX() && win.mouseY < getHeight() + getY()) {
			if (win.leftMouseDown) {
				setDrawNum(1);
			} else {
				setDrawNum(2);
			}
		} else {
			setDrawNum(0);
		}
		if(isChecked()) setDrawNum(getDrawNum() + 3);
		
		if(drawNum == 0) actualBox = normalBox;
		if(drawNum == 1) actualBox = activeBox;
		if(drawNum == 2) actualBox = hoverBox;
		if(drawNum == 3) actualBox = normalBoxChecked;
		if(drawNum == 4) actualBox = activeBoxChecked;
		if(drawNum == 5) actualBox = hoverBoxChecked;
		if(actualBox == null) return;
		
		// Button wird korrekt ausgerichtet
		super.run();
		
		// Breite/Höhe des aktuellen Bildes zur aktuellen Breite/Höhe des Buttons machen und dabei neu positionieren
		this.setWidth(actualBox.getImageWidth());
		this.setHeight(actualBox.getImageHeight());
		if(getDrawNum() != 0) {
			this.setX(getUnmodifiedX() - (actualBox.getImageWidth() - actualBox.getImageWidth()) / 2);
			this.setY(getUnmodifiedY() - (actualBox.getImageHeight() - actualBox.getImageHeight()) / 2);
		}
	}
	
	// Hier wird die Checkbox gemalt
	public void paint() {
		Color.white.bind();
		actualBox.bind(); // or GL11.glBind(texture.getTextureID());
 
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0,0);
			GL11.glVertex2f(100,100);
			GL11.glTexCoord2f(1,0);
			GL11.glVertex2f(100+actualBox.getTextureWidth(),100);
			GL11.glTexCoord2f(1,1);
			GL11.glVertex2f(100+actualBox.getTextureWidth(),100+actualBox.getTextureHeight());
			GL11.glTexCoord2f(0,1);
			GL11.glVertex2f(100,100+actualBox.getTextureHeight());
		GL11.glEnd();
	}

	// getter/is-Methoden //
	public int getDrawNum() {
		return drawNum;
	}
	public boolean isChecked() {
		return isChecked;
	}
	public Texture getNormalBox() {
		return normalBox;
	}
	public Texture getActiveBox() {
		return activeBox;
	}
	public Texture getHoverBox() {
		return hoverBox;
	}
	public Texture getNormalBoxChecked() {
		return normalBoxChecked;
	}
	public Texture getActiveBoxChecked() {
		return activeBoxChecked;
	}
	public Texture getHoverBoxChecked() {
		return hoverBoxChecked;
	}

	// setter-Methoden //
	public void setDrawNum(int drawNum) {
		this.drawNum = drawNum;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	public void setNormalBox(Texture normalBox) {
		this.normalBox = normalBox;
	}
	public void setActiveBox(Texture activeBox) {
		this.activeBox = activeBox;
	}
	public void setHoverBox(Texture hoverBox) {
		this.hoverBox = hoverBox;
	}
	public void setNormalBoxChecked(Texture normalBoxChecked) {
		this.normalBoxChecked = normalBoxChecked;
	}
	public void setActiveBoxChecked(Texture activeBoxChecked) {
		this.activeBoxChecked = activeBoxChecked;
	}
	public void setHoverBoxChecked(Texture hoverBoxChecked) {
		this.hoverBoxChecked = hoverBoxChecked;
	}
}
