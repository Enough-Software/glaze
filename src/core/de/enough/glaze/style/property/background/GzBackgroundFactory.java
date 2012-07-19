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

import javax.microedition.m2g.SVGImage;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.XYEdges;
import de.enough.glaze.style.Color;
import de.enough.glaze.style.Dimension;
import de.enough.glaze.style.property.background.image.ImageBackgroundPosition;

public class GzBackgroundFactory {

	/**
	 * Creates a solid background with the given color
	 * 
	 * @param color
	 *            the color
	 * @return the created background
	 */
	public static GzBackground createSolidBackground(Color color) {
		// a simple solid background with a color
		return new SolidBackground(color);
	}

	/**
	 * Creates a solid background with the given color
	 * 
	 * @param color
	 *            the color
	 * @return the created background
	 */
	public static GzBackground createRoundedBackground(Color color,
			XYEdges widths) {
		return new RoundedBackground(color, widths);
	}

	/**
	 * Creates a bitmap background with the given bitmap, position and repeat
	 * 
	 * @param bitmap
	 *            the bitmap
	 * @param position
	 *            the position
	 * @param repeat
	 *            the repeat
	 * @return the created background
	 */
	public static GzBackground createImageBackground(Bitmap bitmap,
			ImageBackgroundPosition position, int repeat) {
		return new ImageBackground(bitmap, position, repeat);
	}

	/**
	 * Creates a gradient background with the given orientation, colors and
	 * offsets
	 * 
	 * @param orientation
	 *            the orientation
	 * @param colors
	 *            the colors
	 * @param offsets
	 *            the offsets
	 * @return the created background
	 */
	public static GzBackground createGradientBackground(String orientation,
			Color[] colors, Dimension[] offsets) {
		if ("horizontal".equals(orientation)) {
			return new HorizontalGradientBackground(colors, offsets);
		} else {
			return new VerticalGradientBackground(colors, offsets);
		}
	}

	/**
	 * Creates a new patch background with the given bitmap, padding, tiling and
	 * color
	 * 
	 * @param bitmap
	 *            the bitmap
	 * @param padding
	 *            the padding
	 * @param tiling
	 *            the tiling
	 * @param color
	 *            the color
	 * @return the created background
	 */
	public static GzBackground createTiledBackground(Bitmap bitmap, Dimension[] tiling) {
		return new TiledBackground(bitmap, tiling);
	}

	/**
	 * Creates a new mask background with the given mask color, mask background
	 * and background
	 * 
	 * @param maskColor
	 *            the mask color
	 * @param maskBackground
	 *            the mask background
	 * @param background
	 *            the background
	 * @return the created background
	 */
	public static GzBackground createMaskBackground(
			GzBackground maskBackground, GzBackground background) {
		return new MaskBackground(maskBackground, background);
	}

	/**
	 * Creates a new layer background with the given backgrounds and margins
	 * 
	 * @param backgrounds
	 *            the backgrounds
	 * @param margins
	 *            the margins
	 * @return the created background
	 */
	public static GzBackground createLayerBackground(GzBackground[] backgrounds) {
		return new LayerBackground(backgrounds);
	}

	public static GzBackground createSvgBackground(SVGImage svgImage) {
		return new SvgBackground(svgImage);
	}
}
