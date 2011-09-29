package de.enough.glaze.style.property.background.clipping;

import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.XYPoint;
import net.rim.device.api.ui.XYRect;

/**
 * Provides methods to test an offset and area if its in the current clipping
 * area.
 * 
 * @author Andre
 * 
 */
public class ClippingTest {

	/**
	 * the stored draw offset
	 */
	private final XYPoint drawOffset;

	/**
	 * the absolute clipping rect
	 */
	private final XYRect absoluteClipping;

	public ClippingTest() {
		this.drawOffset = new XYPoint();
		this.absoluteClipping = new XYRect();
	}

	/**
	 * Sets the absolute clipping and drawing offset from the given
	 * {@link Graphics} instance.
	 * 
	 * @param graphics
	 *            the {@link Graphics} instance
	 */
	public void setProperties(Graphics graphics) {
		graphics.getDrawingOffset(this.drawOffset);
		graphics.getAbsoluteClippingRect(this.absoluteClipping);
	}

	/**
	 * Returns true if the given relative offset and area is inside the set
	 * clipping area. setProperties() must be called before to set the drawing
	 * offset and clipping area.
	 * 
	 * @param x
	 *            the x offset
	 * @param y
	 *            the y offset
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @return true if the relative offset and area is inside the set clipping
	 *         area
	 */
	public boolean isInClippingArea(int x, int y, int width, int height) {
		int absoluteX = x + this.drawOffset.x;
		int absoluteY = y + this.drawOffset.y;
		// test if the area is outside the set clipping area
		boolean outsideClipping = ((absoluteX > this.absoluteClipping.x
				+ this.absoluteClipping.width || absoluteX + width < this.absoluteClipping.x) || (absoluteY > this.absoluteClipping.y
				+ this.absoluteClipping.height || absoluteY + height < this.absoluteClipping.y));
		// return the negated value
		return !outsideClipping;
	}
}
