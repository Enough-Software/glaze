package de.enough.glaze.style.border;

import net.rim.device.api.ui.XYEdges;

public class GzBorderFactory {
	public static GzBorder createSimpleBorder(XYEdges borderWidths,
			XYEdges borderColors, XYEdges borderStyles) {
		return new SimpleBorder(borderWidths, borderColors, borderStyles);
	}
}
