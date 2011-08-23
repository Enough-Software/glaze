package de.enough.glaze.style.background;

import net.rim.device.api.ui.Graphics;
import de.enough.glaze.style.Color;

public class SolidBackground extends GzBackground {

	private final int color;
	
	public SolidBackground(Color color) {
		this.color = color.getColor();
	}
	
	/* (non-Javadoc)
	 * @see net.rim.device.api.ui.decor.Background#draw(net.rim.device.api.ui.Graphics, net.rim.device.api.ui.XYRect)
	 */
	public void draw(Graphics graphics, int x, int y, int width, int height) {
		// remember original color
		int originalColor = graphics.getColor();
		
		graphics.setColor(this.color);
		graphics.fillRect(x, y, width, height);
		
		// restore original color
		graphics.setColor(originalColor);
	}

	/* (non-Javadoc)
	 * @see net.rim.device.api.ui.decor.Background#isTransparent()
	 */
	public boolean isTransparent() {
		return true;
	} 
}
