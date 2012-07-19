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
 
package de.enough.glaze.style.property.background.gradient;

/**
 * A class containing methods for gradient drawing
 * 
 * @author Andre
 * 
 */
public class GradientBackgroundUtils {

	/**
	 * Creates a gradient of colors. This method is highly optimized and only
	 * uses bit-shifting and additions (no multiplication nor devision), but it
	 * will create a new integer array in each call.
	 * 
	 * @param startColor
	 *            the first color
	 * @param endColor
	 *            the last color
	 * @param steps
	 *            the number of colors in the gradient, when 2 is given, the
	 *            first one will be the startColor and the second one will the
	 *            endColor.
	 * @return an int array with the gradient.
	 * @see #getGradient(int, int, int[])
	 * @see #getGradientColor(int, int, int)
	 */
	public static int[] getGradient(int startColor, int endColor, int steps) {
		if (steps <= 0) {
			return new int[0];
		}
		int[] gradient = new int[steps];
		getGradient(startColor, endColor, gradient);
		return gradient;

	}

	/**
	 * Creates a gradient of colors. This method is highly optimized and only
	 * uses bit-shifting and additions (no multiplication nor devision).
	 * 
	 * @param startColor
	 *            the first color
	 * @param endColor
	 *            the last color
	 * @param gradient
	 *            the array in which the gradient colors are stored.
	 * @see #getGradientColor(int, int, int, int)
	 */
	public static void getGradient(int startColor, int endColor, int[] gradient) {
		int steps = gradient.length;
		if (steps == 0) {
			return;
		} else if (steps == 1) {
			gradient[0] = startColor;
			return;
		}
		int startAlpha = startColor >>> 24;
		int startRed = (startColor >>> 16) & 0x00FF;
		int startGreen = (startColor >>> 8) & 0x0000FF;
		int startBlue = startColor & 0x00000FF;

		int endAlpha = endColor >>> 24;
		int endRed = (endColor >>> 16) & 0x00FF;
		int endGreen = (endColor >>> 8) & 0x0000FF;
		int endBlue = endColor & 0x00000FF;

		int stepAlpha = ((endAlpha - startAlpha) << 8) / (steps - 1);
		int stepRed = ((endRed - startRed) << 8) / (steps - 1);
		int stepGreen = ((endGreen - startGreen) << 8) / (steps - 1);
		int stepBlue = ((endBlue - startBlue) << 8) / (steps - 1);

		startAlpha <<= 8;
		startRed <<= 8;
		startGreen <<= 8;
		startBlue <<= 8;

		gradient[0] = startColor;
		for (int i = 1; i < steps; i++) {
			startAlpha += stepAlpha;
			startRed += stepRed;
			startGreen += stepGreen;
			startBlue += stepBlue;

			gradient[i] = ((startAlpha << 16) & 0xFF000000)
					| ((startRed << 8) & 0x00FF0000)
					| (startGreen & 0x0000FF00) | (startBlue >>> 8);
		}
	}
}
