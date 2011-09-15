package de.enough.glaze.style.property.background;

import net.rim.device.api.ui.Graphics;

public class ZeroBackground extends GzBackground {

	private static ZeroBackground INSTANCE;

	public static final ZeroBackground getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ZeroBackground();
		}

		return INSTANCE;
	}
	
	private ZeroBackground() {
		// no instantiation allowed
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.rim.device.api.ui.decor.Background#draw(net.rim.device.api.ui.Graphics
	 * , net.rim.device.api.ui.XYRect)
	 */
	public void draw(Graphics graphics, int x, int y, int width, int height) {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.decor.Background#isTransparent()
	 */
	public boolean isTransparent() {
		return false;
	}
}
