package de.enough.glaze.ui.component;

import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.component.TextField;

public class GzTextField extends TextField implements GzField {
	
	public GzTextField(long style) {
		super(style);
	}
	
	protected void paint(Graphics graphics) {
		graphics.setColor(0x00FF00);
		super.paint(graphics);
	}
}
