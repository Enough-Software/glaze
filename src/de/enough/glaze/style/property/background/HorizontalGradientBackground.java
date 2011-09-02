package de.enough.glaze.style.property.background;

import net.rim.device.api.ui.Graphics;
import de.enough.glaze.drawing.DrawUtils;
import de.enough.glaze.style.Color;
import de.enough.glaze.style.Dimension;

public class HorizontalGradientBackground extends GzBackground {

	/**
	 * Start and end colors for the gradient
	 */
	private final Color startColor;

	/**
	 * End color for the gradient
	 */
	private final Color endColor;

	/**
	 * Start position for the gradient
	 */
	private final Dimension startPosition;

	/**
	 * End position for the gradient
	 */
	private final Dimension endPosition;

	public HorizontalGradientBackground(Color[] colors, Dimension[] offsets) {
		this.startColor = colors[0];
		this.endColor = colors[1];
		this.startPosition = offsets[0];
		this.endPosition = offsets[1];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.rim.device.api.ui.decor.Background#draw(net.rim.device.api.ui.Graphics
	 * , net.rim.device.api.ui.XYRect)
	 */
	public void draw(Graphics graphics, int x, int y, int width, int height) {
		int startGradientPixels = this.startPosition.getValue(width);
		int endGradientPixels = this.endPosition.getValue(width);

		// remember original color
		int originalColor = graphics.getColor();

		// Draw the pre-gradient area
		graphics.setColor(this.startColor.getColor());
		graphics.fillRect(x, y, startGradientPixels, height);

		// Draw the gradient
		int[] gradientColors = DrawUtils.getGradient(startColor.getColor(),
				endColor.getColor(),
				Math.abs(endGradientPixels - startGradientPixels));
		int pos = startGradientPixels;
		int i = 0;
		while (pos < endGradientPixels) {
			graphics.setColor(gradientColors[i]);
			graphics.drawLine(x+pos, y, x+pos, y+height);
			i++;
			pos++;
		}

		// Draw the post-gradient area
		graphics.setColor(this.endColor.getColor());
		graphics.fillRect(x + endGradientPixels, y, width - endGradientPixels, height);
		// restore original color
		graphics.setColor(originalColor);
	}
}
