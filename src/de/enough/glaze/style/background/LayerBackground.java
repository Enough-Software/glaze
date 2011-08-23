package de.enough.glaze.style.background;

import net.rim.device.api.ui.Graphics;
import de.enough.glaze.style.Dimension;

public class LayerBackground extends GzBackground {

	private final GzBackground[] backgrounds;
	private final Dimension[] margins;
	
	public LayerBackground(GzBackground[] backgrounds,
			Dimension[] margins) {
		this.backgrounds = backgrounds;
		this.margins = margins;
	}
	
	/* (non-Javadoc)
	 * @see net.rim.device.api.ui.decor.Background#draw(net.rim.device.api.ui.Graphics, net.rim.device.api.ui.XYRect)
	 */
	public void draw(Graphics graphics, int x, int y, int width, int height) {
		// remember original color
		int originalColor = graphics.getColor();
		
		for (int i=0;i<this.backgrounds.length;i++) {
			int childX = x + this.margins[3].getValue();
			int childY = x + this.margins[0].getValue();
			int childWidth = x + width - this.margins[3].getValue() - this.margins[1].getValue();
			int childHeight =  height - this.margins[0].getValue() - this.margins[2].getValue();
			backgrounds[i].draw(graphics, childX, childY, childWidth, childHeight);

		}
		
		// restore original color
		graphics.setColor(originalColor);
	}
}
