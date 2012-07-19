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
import de.enough.glaze.style.Color;
import de.enough.glaze.style.Dimension;
import de.enough.glaze.style.property.background.gradient.GradientBackgroundUtils;

public class HorizontalGradientBackground extends GzCachedBackground {

	/**
	 * Start and end colors for the gradient
	 */
	private final Color startColor;

	/**
	 * End color for the gradient
	 */
	private final Color endColor;

	/**
	 * Start position for the gradient
	 */
	private final Dimension startPosition;

	/**
	 * End position for the gradient
	 */
	private final Dimension endPosition;

	public HorizontalGradientBackground(Color[] colors, Dimension[] offsets) {
		this.startColor = colors[0];
		this.endColor = colors[1];
		this.startPosition = offsets[0];
		this.endPosition = offsets[1];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.style.property.background.GzCachedBackground#create(int,
	 * int)
	 */
	public Object create(int width, int height) {
		int startGradientPixels = this.startPosition.getValue(width);
		int endGradientPixels = this.endPosition.getValue(width);
		// Draw the gradient
		int[] gradientColors = GradientBackgroundUtils.getGradient(
				startColor.getColor(), endColor.getColor(),
				Math.abs(endGradientPixels - startGradientPixels));
		return gradientColors;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.style.property.background.GzCachedBackground#draw(net
	 * .rim.device.api.ui.Graphics, int, int, int, int, int[])
	 */
	public void draw(Graphics graphics, int x, int y, int width, int height,
			Object data) {
		int startGradientPixels = this.startPosition.getValue(width);
		int endGradientPixels = this.endPosition.getValue(width);

		// Draw the pre-gradient area
		graphics.setColor(this.startColor.getColor());
		graphics.fillRect(x, y, startGradientPixels, height);

		int[] gradientColors = (int[])data; 
		int pos = startGradientPixels;
		int index = 0;
		while (pos < endGradientPixels) {
			graphics.setColor(gradientColors[index]);
			graphics.drawLine(x + pos, y, x + pos, y + height);
			index++;
			pos++;
		}

		// Draw the post-gradient area
		graphics.setColor(this.endColor.getColor());
		graphics.fillRect(x + endGradientPixels, y, width - endGradientPixels,
				height);
	}
}
