import java.awt.Point;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

public class GuiSelectField extends GuiElement {
	private String text = "";
	private String action = "";		// Buttonaktionstext der beim Doppelklick übergeben wird
	
	private int drawNum = 0;
	
	private long doubleClickDelay = -1L;
	
	private long mouseStep = 0;
	
	// Texturen
	private static Texture frameInactive = Gui.createImage("frameActive.png");
	private static Texture frameActive = Gui.createImage("frameInactive.png");
	
	private Texture actualFrame = frameInactive;
	
	public GuiSelectField(MainWindow win, Point bounds, String text, String action) {
		super(win);
		this.setPosition(bounds);
		this.setText(text);
		this.setAction(action);
	}
	
	public void run() {
		int mouseX = win.mouseX;
		int mouseY = Math.abs(win.mouseY - Display.getDisplayMode().getHeight()) + 2;
		
		if((doubleClickDelay > MainWindow.getSystemTime() || doubleClickDelay == -1) && mouseX >= getX() && mouseY >= getY() && mouseX < getWidth() + getX() && mouseY < getHeight() + getY()) {
			if(Mouse.isButtonDown(0) && mouseStep == 0) {
				System.out.println("click1");
				doubleClickDelay = MainWindow.getSystemTime() + 150L;
				mouseStep = 1;
			}
			if(!Mouse.isButtonDown(0) && mouseStep == 1) {
				System.out.println("click2");
				doubleClickDelay = MainWindow.getSystemTime() + 150L;
				mouseStep = 2;
			}
			if(Mouse.isButtonDown(0) && mouseStep == 2) {
				System.out.println("click3");
				doubleClickDelay = -1L;
				MainWindow.gui.getActualGui().buttonClicked(getAction());
				mouseStep = 0;
			}
		}
		else if(doubleClickDelay > 0) {
			doubleClickDelay = -1;
		}
		
		// Den Status des Auswahlfeldes festlegen
		if (mouseX >= getX() && mouseY >= getY() && mouseX < getWidth() + getX() && mouseY < getHeight() + getY()) {
			if(win.leftMouseDown) setDrawNum(1);
		} else {
			if(win.leftMouseDown) setDrawNum(0);
		}
		
		if(drawNum == 0) actualFrame = frameInactive;
		if(drawNum == 1) actualFrame = frameActive;
		if(actualFrame == null) return;
		
		// Auswahlfeld korrekt ausrichten (je nach festgelegter Ausrichtung)
		super.run();
		
		// Breite/Höhe des aktuellen Bildes zur aktuellen Breite/Höhe des Buttons machen und dabei neu positionieren
		this.setWidth(actualFrame.getImageWidth());
		this.setHeight(actualFrame.getImageHeight());
		this.setX(getUnmodifiedX() - (actualFrame.getImageWidth() - frameInactive.getImageWidth()) / 2);
		this.setY(getUnmodifiedY() - (actualFrame.getImageHeight() - frameInactive.getImageHeight()) / 2);
	}
	
	public void paint() {
		Color.white.bind();
		actualFrame.bind(); // or GL11.glBind(texture.getTextureID());
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0,0);
			GL11.glVertex2f(getX(),getY());
			GL11.glTexCoord2f(1,0);
			GL11.glVertex2f(getX()+actualFrame.getImageWidth(),getY());
			GL11.glTexCoord2f(1,1);
			GL11.glVertex2f(getX()+actualFrame.getImageWidth(),getY()+actualFrame.getImageHeight());
			GL11.glTexCoord2f(0,1);
			GL11.glVertex2f(getX(),getY()+actualFrame.getImageHeight());
		GL11.glEnd();
		
		int stringWidth = getFont().getWidth(getText());
		int stringHeight = getFont().getHeight();
		
		int stringX = getX() + getWidth() / 2 - stringWidth / 2;
		int stringY = getY() + getHeight() / 2 + stringHeight / 2 - 17;

		FontRenderer.drawString(stringX, stringY, getText(), getFont(), getColor());
	}

	// getter-Methoden //
	public String getText() {
		return text;
	}
	public String getAction() {
		return action;
	}
	public Texture getFrameInactive() {
		return frameInactive;
	}
	public Texture getFrameActive() {
		return frameActive;
	}
	public Texture getActualFrame() {
		return actualFrame;
	}
	public int getDrawNum() {
		return drawNum;
	}

	// setter-Methoden //
	public void setText(String text) {
		this.text = text;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public void setFrameInactive(Texture frameInactive) {
		GuiSelectField.frameInactive = frameInactive;
	}
	public void setFrameActive(Texture frameActive) {
		GuiSelectField.frameActive = frameActive;
	}
	public void setActualFrame(Texture actualFrame) {
		this.actualFrame = actualFrame;
	}
	public void setDrawNum(int drawNum) {
		this.drawNum = drawNum;
	}
}
