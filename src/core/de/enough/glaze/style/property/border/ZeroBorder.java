package de.enough.glaze.style.property.border;

import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.XYRect;
import net.rim.device.api.ui.decor.Border;

public class ZeroBorder extends GzBorder {
	
	private static ZeroBorder INSTANCE;
	
	public static final ZeroBorder getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new ZeroBorder();
		}
		
		return INSTANCE;
	}

	private ZeroBorder() {
		// no instantiation allowed
		super(new XYEdges(0, 0, 0, 0),Border.STYLE_TRANSPARENT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.rim.device.api.ui.decor.Border#paint(net.rim.device.api.ui.Graphics,
	 * net.rim.device.api.ui.XYRect)
	 */
	public void paint(Graphics graphics, XYRect rect) {
		// no painting 
	}
}
