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
import javax.microedition.m2g.ScalableGraphics;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Graphics;

public class SvgBackground extends GzCachedBackground {

	private final SVGImage image;

	public SvgBackground(SVGImage image) {
		this.image = image;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.style.background.GzCachedBackground#create(int, int)
	 */
	public Object create(int width, int height) {
		ScalableGraphics scalableGraphics = ScalableGraphics.createInstance();
		Bitmap bitmap = new Bitmap(width, height);
		Graphics graphics = new Graphics(bitmap);

		// render the image to the graphics instance
		scalableGraphics.bindTarget(graphics);
		this.image.setViewportWidth(width);
		this.image.setViewportHeight(height);
		scalableGraphics.render(0, 0, this.image);
		scalableGraphics.releaseTarget();
		
		return bitmap;
	}

	/* (non-Javadoc)
	 * @see de.enough.glaze.style.property.background.GzCachedBackground#draw(net.rim.device.api.ui.Graphics, int, int, int, int, java.lang.Object)
	 */
	public void draw(Graphics graphics, int x, int y, int width, int height,
			Object data) {
		Bitmap bitmap = (Bitmap)data;
		graphics.drawBitmap(x, y, width, height, bitmap, 0, 0);
	}
}
