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

import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.XYEdges;
import de.enough.glaze.style.Color;

public class RoundedBackground extends GzBackground {

	private final Color color;
	private final XYEdges widths;

	public RoundedBackground(Color color, XYEdges widths) {
		this.color = color;
		this.widths = widths;
	}

	private static final byte[] PATH_POINT_TYPES = {
			Graphics.CURVEDPATH_END_POINT,
			Graphics.CURVEDPATH_QUADRATIC_BEZIER_CONTROL_POINT,
			Graphics.CURVEDPATH_END_POINT, Graphics.CURVEDPATH_END_POINT,
			Graphics.CURVEDPATH_QUADRATIC_BEZIER_CONTROL_POINT,
			Graphics.CURVEDPATH_END_POINT, Graphics.CURVEDPATH_END_POINT,
			Graphics.CURVEDPATH_QUADRATIC_BEZIER_CONTROL_POINT,
			Graphics.CURVEDPATH_END_POINT, Graphics.CURVEDPATH_END_POINT,
			Graphics.CURVEDPATH_QUADRATIC_BEZIER_CONTROL_POINT,
			Graphics.CURVEDPATH_END_POINT, };

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.rim.device.api.ui.decor.Background#draw(net.rim.device.api.ui.Graphics
	 * , net.rim.device.api.ui.XYRect)
	 */
	public void draw(Graphics graphics, int x, int y, int width, int height) {
		this.color.set(graphics);
		graphics.setDrawingStyle(Graphics.DRAWSTYLE_AAPOLYGONS, true);

		int marginTop = this.widths.top;
		int marginRight = this.widths.right;
		int marginBottom = this.widths.bottom;
		int marginLeft = this.widths.left;

		int xPts[] = new int[] { x, x, x + marginLeft, x + width - marginRight,
				x + width, x + width, x + width, x + width,
				x + width - marginRight, x + marginLeft, x, x };

		int yPts[] = new int[] { y + marginTop, y, y, y, y, y + marginTop,
				y + height - marginBottom, y + height, y + height, y + height,
				y + height, y + height - marginBottom };

		graphics.drawFilledPath(xPts, yPts, PATH_POINT_TYPES, null);
		graphics.setDrawingStyle(Graphics.DRAWSTYLE_AAPOLYGONS, false);
	}
}
