package de.enough.glaze.style.border;

import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.XYRect;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;

public class BevelBorder extends GzBorder {

	private final Border border;

	public BevelBorder(XYEdges edges,
            XYEdges colorsOuter,
            XYEdges colorsInner) {
		super(edges, Border.STYLE_SOLID);
		this.border = BorderFactory.createBevelBorder(edges, colorsOuter, colorsInner);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.rim.device.api.ui.decor.Border#paint(net.rim.device.api.ui.Graphics,
	 * net.rim.device.api.ui.XYRect)
	 */
	public void paint(Graphics graphics, XYRect rect) {
		this.border.paint(graphics, rect);
	}
}
