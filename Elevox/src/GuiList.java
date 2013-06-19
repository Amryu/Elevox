/*
 * Diese Klasse leitet sich von der gleichnamigen Klasse ArrayList ab.
 * Sie wird hier nur wiederholt um das Protokoll zu erweitern während
 * sie direkt in eine Liste nur für Gui-Elemente umgewandelt wird.
 */

import java.util.Collection;

public class GuiList extends java.util.ArrayList<GuiElement> {
	private static final long serialVersionUID = -8770760877205966941L;
	
	public GuiList() {
		super();
	}
	
	public GuiList(int num) {
		super(num);
	}
	
	public GuiList(Collection<GuiElement> collection) {
		super(collection);
	}
	
	public boolean add(GuiElement obj) {
		return super.add(obj);
	}
	
	public void add(int num, GuiElement obj) {
		super.add(num, obj);
	}
	
	public boolean addAll(Collection<? extends GuiElement> collection) {
		return super.addAll((Collection<? extends GuiElement>) collection);
	}
	
	public boolean addAll(int num, Collection<? extends GuiElement> collection) {
		return super.addAll(num, (Collection<? extends GuiElement>) collection);
	}
	
	public boolean remove(Object obj) {
		return super.remove(obj);
	}
	
	public GuiElement remove(int num) {
		return super.remove(num);
	}
	
	public boolean removeAll(Collection<?> collection) {
		return super.remove(collection);
	}
	
	public void removeRange(int start, int end) {
		if(end > start) return;
		super.removeRange(start, end);
	}
	
	public void clear() {
		super.clear();
	}
}
