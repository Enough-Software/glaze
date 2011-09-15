package de.enough.glaze.style.property.background;

import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.XYRect;
import de.enough.glaze.style.Dimension;

public class LayerBackground extends GzBackground {

	private final GzBackground[] backgrounds;
	private final Dimension[] margins;
	private final XYRect drawRect;

	public LayerBackground(GzBackground[] backgrounds, Dimension[] margins) {
		this.backgrounds = backgrounds;
		this.margins = margins;
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
		// remember original color
		int originalColor = graphics.getColor();

		for (int index = 0; index < this.backgrounds.length; index++) {
			Dimension childMargin = this.margins[index];
			int marginValue = childMargin.getValue();
			this.drawRect.x = x + marginValue;
			this.drawRect.y = y + marginValue;
			this.drawRect.width = width - (marginValue * 2);
			this.drawRect.height = height - (marginValue * 2);
			this.backgrounds[index].draw(graphics, this.drawRect);

		}

		// restore original color
		graphics.setColor(originalColor);
	}
}
