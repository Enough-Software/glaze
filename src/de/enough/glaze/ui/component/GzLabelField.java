package de.enough.glaze.ui.component;

import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.component.LabelField;

public class GzLabelField extends LabelField implements GzField {
	
	public GzLabelField(String text) {
		super(text);
	}
	
	public GzLabelField(String text, long style) {
		super(text, style);
	}
	
	protected void paint(Graphics graphics) {
		graphics.setColor(0x00FF00);
		super.paint(graphics);
	}

	/* (non-Javadoc)
	 * @see net.rim.device.api.ui.Field#drawFocus(net.rim.device.api.ui.Graphics, boolean)
	 */
	protected void drawFocus(Graphics graphics, boolean on) {
		graphics.setColor(0x00FF00);
		// don't draw the focus, the style should handle the focus highlighting 
	}
	
	
}
