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
	
	public static GzBorder createImageBorder(XYEdges padding,
            XYEdges corners,
            Bitmap bitmap) {
		return new BitmapBorder(padding, corners, bitmap);
		
	}
}
