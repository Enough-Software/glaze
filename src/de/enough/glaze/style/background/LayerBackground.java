package de.enough.glaze.style.background;

import net.rim.device.api.ui.Graphics;
import de.enough.glaze.style.Dimension;

public class LayerBackground extends GzBackground {

	private final GzBackground[] backgrounds;
	private final Dimension[] margins;

	public LayerBackground(GzBackground[] backgrounds, Dimension[] margins) {
		this.backgrounds = backgrounds;
		this.margins = margins;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.rim.device.api.ui.decor.Background#draw(net.rim.device.api.ui.Graphics
	 * , net.rim.device.api.ui.XYRect)
	 */
	public void draw(Graphics graphics, int x, int y, int width, int height) {
		// remember original color
		int originalColor = graphics.getColor();

		for (int index = 0; index < this.backgrounds.length; index++) {
			Dimension childMargin = this.margins[index];
			int marginValue = childMargin.getValue();
			int childX = x + marginValue;
			int childY = y + marginValue;
			int childWidth = width - (marginValue * 2);
			int childHeight = height - (marginValue * 2);
			this.backgrounds[index].draw(graphics, childX, childY, childWidth,
					childHeight);

		}

		// restore original color
		graphics.setColor(originalColor);
	}
}
