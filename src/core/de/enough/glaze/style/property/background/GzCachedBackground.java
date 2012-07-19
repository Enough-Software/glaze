/*
 *
 * Copyright: (c) 2012 Enough Software GmbH & Co. KG
 *
 * Licensed under:
 * 1. MIT: http://www.opensource.org/licenses/mit-license.php
 * 2. Apache 2.0: http://opensource.org/licenses/apache2.0
 * 3. GPL with classpath exception: http://www.gnu.org/software/classpath/license.html
 *
 * You may not use this file except in compliance with these licenses.
 *
 */
 
package de.enough.glaze.style.property.background;

import java.util.Hashtable;

import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.XYRect;

public abstract class GzCachedBackground extends GzBackground {

	private final Hashtable rectBufferMap;

	public GzCachedBackground() {
		this.rectBufferMap = new Hashtable();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.style.background.GzBackground#draw(net.rim.device.api
	 * .ui.Graphics, net.rim.device.api.ui.XYRect)
	 */
	public void draw(Graphics graphics, XYRect rect) {
		applyField(rect);
		applyMargin(rect);

		Object data = this.rectBufferMap.get(rect);

		if (data == null) {
			data = create(rect.width, rect.height);
			this.rectBufferMap.put(rect, data);
		}

		// remember original color
		int originalColor = graphics.getColor();

		draw(graphics, rect.x, rect.y, rect.width, rect.height, data);

		// restore original color
		graphics.setColor(originalColor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.style.background.GzBackground#draw(net.rim.device.api
	 * .ui.Graphics, int, int, int, int)
	 */
	public void draw(Graphics graphics, int x, int y, int width, int height) {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.style.background.GzBackground#release()
	 */
	public void release() {
		this.rectBufferMap.clear();
	}

	/**
	 * Creates the cached data for the background to draw
	 * 
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @return the created cached data
	 */
	public abstract Object create(int width, int height);

	/**
	 * Draws the given cached data for the given offset and area using the given
	 * {@link Graphics} instance
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
	 * @param data
	 *            the cached data
	 */
	public abstract void draw(Graphics graphics, int x, int y, int width,
			int height, Object data);

}
