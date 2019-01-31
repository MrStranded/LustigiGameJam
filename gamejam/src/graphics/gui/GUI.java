package graphics.gui;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class GUI {

	List<Box> boxList = new ArrayList<Box>();

	public GUI () {
		Box bBox = new Box();
		boxList.add(bBox);
	}

	public void click(MouseEvent e) {
		// first we check whether we are in a box
		for (Box box : boxList) {
			if (box.inside(e)) {

				// then we see whether we hit a button
				for (Button button : box.getButtonList()) {
					if (button.inside(e)) {

					}
				}
			}
		}

		// then we check if we can shoot
	}

}
