package de.enough.glaze.style.property.background;

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

	protected void adjustRect(XYRect rect) {
		if (this.field != null) {
			rect.width = this.field.getPaddingLeft()
					+ this.field.getContentWidth()
					+ this.field.getPaddingRight();
			rect.height = this.field.getPaddingTop()
					+ this.field.getContentHeight()
					+ this.field.getPaddingBottom();
		}
	}

	public void draw(Graphics graphics, XYRect rect) {
		adjustRect(rect);
		draw(graphics, rect.x, rect.y, rect.width, rect.height);
	}

	public abstract void draw(Graphics graphics, int x, int y, int width,
			int height);

	public boolean isTransparent() {
		return true;
	}

	/**
	 * Releases this background
	 */
	public void release() {
		// do nothing
	}
}
