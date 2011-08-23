package de.enough.glaze.style.border;

import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.decor.Border;

public abstract class GzBorder extends Border {

	public static final String STYLE_SOLID = "solid";

	public static final String STYLE_DOTTED = "dotted";

	public static final String STYLE_FILLED = "filled";

	public static final String STYLE_DASHED = "dashed";

	public GzBorder(XYEdges borderWidths, int style) {
		super(borderWidths, style);
	}
}
