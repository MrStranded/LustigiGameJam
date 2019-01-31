package Graphics.Gui;

import java.util.ArrayList;
import java.util.List;

public class UIComponent {

	private Rect position = new Rect(0,0,0,0);
	private List<UIComponent> subComponentList = new ArrayList<UIComponent>();

	public UIComponent(Rect position) {
		this.position = position;
	}

	public void addSubComponent(UIComponent uiComponent) {
		subComponentList.add(uiComponent);
	}

	public Rect getPosition() {
		return position;
	}

	public List<UIComponent> getSubComponentList() {
		return subComponentList;
	}

	public boolean inside(int[] mousePosition) {
		return position.inside(mousePosition[0], mousePosition[1]);
	}

}
