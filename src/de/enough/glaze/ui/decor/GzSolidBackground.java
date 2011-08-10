package de.enough.glaze.ui.decor;

import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.XYRect;
import net.rim.device.api.ui.decor.Background;

public class GzSolidBackground extends Background {

	private final int color;
	
	public GzSolidBackground(int color) {
		this.color = color;
	}
	
	/* (non-Javadoc)
	 * @see net.rim.device.api.ui.decor.Background#draw(net.rim.device.api.ui.Graphics, net.rim.device.api.ui.XYRect)
	 */
	public void draw(Graphics graphics, XYRect rect) {
		// remember original color
		int originalColor = graphics.getColor();
		
		graphics.setColor(this.color);
		graphics.fillRect(rect.x, rect.y, rect.width, rect.height);
		
		// restore original color
		graphics.setColor(originalColor);
	}

	/* (non-Javadoc)
	 * @see net.rim.device.api.ui.decor.Background#isTransparent()
	 */
	public boolean isTransparent() {
		return false;
	} 
}
