package de.enough.glaze.style.property.background;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.XYRect;
import net.rim.device.api.ui.decor.Background;
import de.enough.glaze.style.Margin;

public abstract class GzBackground extends Background {

	private Field field;

	private Margin margin;

	public void setField(Field field) {
		this.field = field;
	}

	public void setMargin(Margin margin) {
		this.margin = margin;
	}

	/**
	 * Adjusts the rectangle to the current field. Introduced due to a bug in
	 * the background drawing of {@link Manager} instances.
	 * 
	 * @param rect
	 *            the rectangle
	 */
	protected void applyField(XYRect rect) {
		if (this.field != null) {
			rect.width = this.field.getPaddingLeft()
					+ this.field.getContentWidth()
					+ this.field.getPaddingRight();
			rect.height = this.field.getPaddingTop()
					+ this.field.getContentHeight()
					+ this.field.getPaddingBottom();
		}
	}

	/**
	 * Adjust the rectangle by applying the margin.
	 * 
	 * @param rect
	 *            the rectangle
	 */
	protected void applyMargin(XYRect rect) {
		if (this.margin != null) {
			rect.x = rect.x + this.margin.getLeft().getValue();
			rect.width = rect.width - this.margin.getRight().getValue()
					- this.margin.getLeft().getValue();
			rect.y = rect.y + this.margin.getTop().getValue();
			rect.height = rect.height - this.margin.getTop().getValue()
					- this.margin.getBottom().getValue();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.rim.device.api.ui.decor.Background#draw(net.rim.device.api.ui.Graphics
	 * , net.rim.device.api.ui.XYRect)
	 */
	public void draw(Graphics graphics, XYRect rect) {
		applyField(rect);
		applyMargin(rect);
		draw(graphics, rect.x, rect.y, rect.width, rect.height);
	}

	/**
	 * Draws this background to the given dimensions.
	 * 
	 * @param graphics
	 *            the {@link Graphics} instance
	 * @param x
	 *            the x offset
	 * @param y
	 *            the y offset
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 */
	public abstract void draw(Graphics graphics, int x, int y, int width,
			int height);

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.decor.Background#isTransparent()
	 */
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
