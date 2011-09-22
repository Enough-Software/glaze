package de.enough.glaze.style.property.background;

import net.rim.device.api.ui.Graphics;
import de.enough.glaze.style.Color;

public class SolidBackground extends GzBackground {

	private final Color color;
	
	public SolidBackground(Color color) {
		this.color = color;
	}
	
	/* (non-Javadoc)
	 * @see net.rim.device.api.ui.decor.Background#draw(net.rim.device.api.ui.Graphics, net.rim.device.api.ui.XYRect)
	 */
	public void draw(Graphics graphics, int x, int y, int width, int height) {
		this.color.set(graphics);
		graphics.fillRect(x, y, width, height);
	}
}
