package de.enough.glaze.style.background;

import net.rim.device.api.ui.Graphics;

public class NoBackground extends GzBackground {

	private static NoBackground INSTANCE;

	public static final NoBackground getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new NoBackground();
		}

		return INSTANCE;
	}
	
	private NoBackground() {
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
