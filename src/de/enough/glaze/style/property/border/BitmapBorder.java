package de.enough.glaze.style.property.border;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.XYRect;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;

public class BitmapBorder extends GzBorder {

	private final Border border;

	public BitmapBorder(XYEdges padding,
            XYEdges corners,
            Bitmap bitmap) {
		super(padding, Border.STYLE_SOLID);
		this.border = BorderFactory.createBitmapBorder(padding, corners, bitmap);
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
