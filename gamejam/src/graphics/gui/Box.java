package graphics.gui;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Box {

	private Rect position = new Rect(0,0,0,0);
	private List<Button> buttonList = new ArrayList<Button>();

	public Box(Rect position) {
		this.position = position;
	}

	public void addButton(Button button) {
		buttonList.add(button);
	}

	public Rect getPosition() {
		return position;
	}

	public List<Button> getButtonList() {
		return buttonList;
	}

	public boolean inside(int[] mousePosition) {
		return position.inside(mousePosition[0], mousePosition[1]);
	}
}
