package de.enough.glaze.ui.delegate;

import net.rim.device.api.ui.Graphics;

public interface GzField extends GzExtent {

	/**
	 * Must call super.layout(width,height) in an implementing field
	 * 
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 */
	public void gz_layout(int width, int height);

	/**
	 * Must call super.paint(graphics) in an implementing field
	 * 
	 * @param graphics
	 *            the {@link Graphics} instance
	 */
	public void gz_paint(Graphics graphics);
}
