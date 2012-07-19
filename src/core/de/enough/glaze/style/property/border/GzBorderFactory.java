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
 
package de.enough.glaze.style.property.border;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.XYEdges;

public class GzBorderFactory {
	
	public static GzBorder createSimpleBorder(XYEdges borderWidths,
			XYEdges borderColors, XYEdges borderStyles) {
		return new SimpleBorder(borderWidths, borderColors, borderStyles);
	}
	
	public static GzBorder createRoundedBorder(XYEdges padding,
            int color,
            int style) {
		return new RoundedBorder(padding, color, style);
	}
	
	public static GzBorder createBevelBorder(XYEdges edges,
            XYEdges colorsOuter,
            XYEdges colorsInner) {
		return new BevelBorder(edges, colorsOuter, colorsInner);
	}
	
	public static GzBorder createTiledBorder(XYEdges padding,
            XYEdges corners,
            Bitmap bitmap) {
		return new TiledBorder(padding, corners, bitmap);
		
	}
}
