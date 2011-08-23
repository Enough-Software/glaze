package de.enough.glaze.style.background;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.XYRect;
import net.rim.device.api.ui.decor.Background;

public abstract class GzBackground extends Background {
	private Field field;

	public void setField(Field field) {
		this.field = field;
	}

	public Field getField() {
		return this.field;
	}

	public void draw(Graphics graphics, XYRect rect) {
		if (field != null) {
			int width = this.field.getPaddingLeft()
					+ this.field.getContentWidth()
					+ this.field.getPaddingRight();
			int height = this.field.getPaddingTop()
					+ this.field.getContentHeight()
					+ this.field.getPaddingBottom();
			draw(graphics, rect.x, rect.y, width, height);
		} else {
			draw(graphics, rect.x, rect.y, rect.width, rect.height);
		}
	}

	public abstract void draw(Graphics graphics, int x, int y, int width,
			int height);

	public boolean isTransparent() {
		return true;
	}
}
