package Graphics.Gui.GraphicalComponents;

import Graphics.Gui.Rect;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UIComponent {

	private Rect position = new Rect(0,0,0,0);
	private List<UIComponent> uiComponentList = new ArrayList<UIComponent>();
	private Color color = new Color(180,180,180);

	public void kill() {

	}

	public UIComponent(Rect position) {
		this.position = position;
	}

	public void addUIComponent(UIComponent uiComponent) {
		uiComponentList.add(uiComponent);
	}

	public Rect getPosition() {
		return position;
	}

	public List<UIComponent> getUIComponentList() {
		return uiComponentList;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean inside(int[] mousePosition) {
		return position.inside(mousePosition[0], mousePosition[1]);
	}

}
