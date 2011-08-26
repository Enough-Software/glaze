package de.enough.glaze.style.border;

import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.XYRect;
import net.rim.device.api.ui.decor.Border;

public class NoBorder extends GzBorder {
	
	private static NoBorder INSTANCE;
	
	public static final NoBorder getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new NoBorder();
		}
		
		return INSTANCE;
	}

	private NoBorder() {
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
