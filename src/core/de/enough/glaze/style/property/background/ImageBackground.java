package de.enough.glaze.style.property.background;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Graphics;
import de.enough.glaze.style.property.background.image.ImageBackgroundPosition;

public class ImageBackground extends GzBackground {

	public static final int POSITION_TOP = 2;
	public static final int POSITION_BOTTOM = 4;
	public static final int POSITION_LEFT = 8;
	public static final int POSITION_RIGHT = 16;
	public static final int POSITION_CENTER = 32;
	public static final int POSITION_H_CENTER = 64;
	public static final int POSITION_V_CENTER = 128;

	public static final int REPEAT_NONE = 0;
	public static final int REPEAT_X = 2;
	public static final int REPEAT_Y = 4;

	private final Bitmap image;
	private final ImageBackgroundPosition position;
	private final int repeatFlag;

	public ImageBackground(Bitmap image, ImageBackgroundPosition position, int repeatFlag) {
		this.image = image;
		this.position = position;
		this.repeatFlag = repeatFlag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.rim.device.api.ui.decor.Background#draw(net.rim.device.api.ui.Graphics
	 * , net.rim.device.api.ui.XYRect)
	 */
	public void draw(Graphics graphics, int x, int y, int width, int height) {
		int imageWidth = this.image.getWidth();
		int imageHeight = this.image.getHeight();		

		// Calculate startX and startY for drawing the image, based on the
		// position flag
		int startX = 0, startY = 0;
		// TODO use ImageBackgroundPosition 
		/*if ((this.positionFlag & POSITION_TOP) != 0) {
			startY = y;
		}
		if ((this.positionFlag & POSITION_BOTTOM) != 0) {
			startY = y + height - imageHeight;
		}
		if ((this.positionFlag & POSITION_V_CENTER) != 0) {
			startY = y + (height - imageHeight) / 2;
		}
		if ((this.positionFlag & POSITION_LEFT) != 0) {
			startX = x;
		}
		if ((this.positionFlag & POSITION_RIGHT) != 0) {
			startX = x + width - imageWidth;
		}
		if ((this.positionFlag & POSITION_H_CENTER) != 0) {
			startX = x + (width - imageWidth) / 2;
		}
		if ((this.positionFlag & POSITION_CENTER) != 0) {
			startX = x + (width - imageWidth) / 2;
			startY = y + (height - imageHeight) / 2;
		}*/

		// Calculate endX and endY for drawing the image, based on the repeat
		// flag
		int endX = startX;
		int endY = startY;
		// TODO use ImageBackgroundPosition 
		/*if ((this.repeatFlag & REPEAT_X) != 0) {
			endX = width;
			startX = 0;
			if ( (this.positionFlag & POSITION_RIGHT)  != 0) {
				int offset = (width % this.image.getWidth()) - image.getWidth();
				startX += offset;
			}
		}
		if ((this.repeatFlag & REPEAT_Y) != 0) {
			endY = height;
			startY = 0;
			if ( (this.positionFlag & POSITION_BOTTOM)  != 0) {
				int offset = (height % this.image.getHeight()) - image.getHeight();
				startY += offset;
			}
		}*/

		// Draw the image
		x = startX;
		y = startY;
		graphics.pushContext(x, y, width, height, 0, 0);
		while (x <= endX) {
			while (y <= endY) {
				graphics.drawBitmap(x, y, imageWidth, imageHeight, this.image,
						0, 0);
				y += imageHeight;
			}
			x += imageWidth;
			y = startY;
		}
		graphics.popContext();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.decor.Background#isTransparent()
	 */
	public boolean isTransparent() {
		return true;
	} 
}
