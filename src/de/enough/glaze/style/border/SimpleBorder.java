package de.enough.glaze.style.border;

import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.XYRect;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;

public class SimpleBorder extends GzBorder {

	private final Border border;

	public SimpleBorder(XYEdges borderWidths, XYEdges borderColors,
			XYEdges borderStyles) {
		super(borderWidths, 0);
		this.border = BorderFactory.createSimpleBorder(borderWidths,
				borderColors, borderStyles);
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
