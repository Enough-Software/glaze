package de.enough.glaze.style.property.background;

import net.rim.device.api.ui.Graphics;
import de.enough.glaze.style.Color;
import de.enough.glaze.style.Dimension;
import de.enough.glaze.style.property.background.gradient.GradientBackgroundUtils;

public class VerticalGradientBackground extends GzCachedBackground {

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

	public VerticalGradientBackground(Color[] colors, Dimension[] offsets) {
		this.startColor = colors[0];
		this.endColor = colors[1];
		this.startPosition = offsets[0];
		this.endPosition = offsets[1];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.style.property.background.GzCachedBackground#create(int,
	 * int)
	 */
	public Object create(int width, int height) {
		int startGradientPixels = this.startPosition.getValue(height);
		int endGradientPixels = this.endPosition.getValue(height);
		int[] gradientColors = GradientBackgroundUtils.getGradient(
				this.startColor.getColor(), this.endColor.getColor(),
				Math.abs(endGradientPixels - startGradientPixels));
		return gradientColors;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.style.property.background.GzCachedBackground#draw(net
	 * .rim.device.api.ui.Graphics, int, int, int, int, int[])
	 */
	public void draw(Graphics graphics, int x, int y, int width, int height,
			Object data) {
		int startGradientPixels = this.startPosition.getValue(height);
		int endGradientPixels = this.endPosition.getValue(height);

		// Draw the pre-gradient area
		graphics.setColor(this.startColor.getColor());
		graphics.fillRect(x, y, width, startGradientPixels);


		int pos = startGradientPixels;
		int index = 0;
		int[] gradientColors = (int[])data; 
		while (pos < endGradientPixels) {
			graphics.setColor(gradientColors[index]);
			graphics.drawLine(x, y + pos, x + width - 1, y + pos);
			index++;
			pos++;
		}

		// Draw the post-gradient area
		graphics.setColor(this.endColor.getColor());
		graphics.fillRect(x, y + endGradientPixels, width, height
				- endGradientPixels);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.decor.Background#isTransparent()
	 */
	public boolean isTransparent() {
		return false;
	}
}
