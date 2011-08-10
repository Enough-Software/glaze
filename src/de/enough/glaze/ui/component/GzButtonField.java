package de.enough.glaze.ui.component;

import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.component.ButtonField;

public class GzButtonField extends ButtonField implements GzField {

	public GzButtonField(String label) {
		super(label);
	}

	protected void paint(Graphics graphics) {
		graphics.setColor(0x00FF00);
		super.paint(graphics);
	}

	protected void drawFocus(Graphics graphics, boolean on) {
		// don't
	}	
	
	
}
