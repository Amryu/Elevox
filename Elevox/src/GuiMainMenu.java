import java.awt.Graphics;
import java.awt.Point;

public class GuiMainMenu extends Gui {
	private GuiList guiElements = new GuiList();
	
	public GuiMainMenu(MainWindow win) {
		super(win);
		
		GuiText text = new GuiText(win, "TEST", new Point(20, 20));
		guiElements.add(text);
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
