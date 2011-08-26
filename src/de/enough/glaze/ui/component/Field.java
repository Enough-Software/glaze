package de.enough.glaze.ui.component;

import net.rim.device.api.ui.Graphics;
import de.enough.glaze.ui.delegate.FieldDelegate;
import de.enough.glaze.ui.delegate.GzField;

public abstract class Field extends net.rim.device.api.ui.Field implements GzField {

	public void gz_setExtent(int width, int height) {
		super.setExtent(width, height);
	}

	protected void layout(int width, int height) {
		FieldDelegate.layout(width, height, this);
	}

	protected void paint(Graphics graphics) {
		FieldDelegate.paint(graphics, this);
	}

}
