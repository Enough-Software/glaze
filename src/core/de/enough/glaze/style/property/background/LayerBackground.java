package de.enough.glaze.style.property.background;

import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.XYRect;

public class LayerBackground extends GzBackground {

	private final GzBackground[] backgrounds;
	private final XYRect drawRect;

	public LayerBackground(GzBackground[] backgrounds) {
		this.backgrounds = backgrounds;
		this.drawRect = new XYRect();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.rim.device.api.ui.decor.Background#draw(net.rim.device.api.ui.Graphics
	 * , net.rim.device.api.ui.XYRect)
	 */
	public void draw(Graphics graphics, int x, int y, int width, int height) {
		for (int index = 0; index < this.backgrounds.length; index++) {
			this.drawRect.x = x;
			this.drawRect.y = y;
			this.drawRect.width = width;
			this.drawRect.height = height;
			this.backgrounds[index].draw(graphics, this.drawRect);
		}
	}
}
