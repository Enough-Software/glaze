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
		
		// Set the vertical position
		if ( this.position.isVerticalPositionRelative() ) {
			if (this.position.getVerticalPosition() == ImageBackgroundPosition.TOP) {
				startY = y;
			} else if (this.position.getVerticalPosition() == ImageBackgroundPosition.BOTTOM) {
				startY = y + height - imageHeight;
			} else if (this.position.getVerticalPosition() == ImageBackgroundPosition.CENTER) {
				startY = y + (height - imageHeight) / 2;
			}
		} else {
			startY = this.position.getVerticalPosition().getValue(width);
		}
		
		// Set the horizontal position
		if ( this.position.isHorizontalPositionRelative() ) {
			if (this.position.getHorizontalPosition() == ImageBackgroundPosition.LEFT) {
				startX = x;
			} else if (this.position.getHorizontalPosition() == ImageBackgroundPosition.RIGHT) {
				startX = x + width - imageWidth;
			} else if (this.position.getHorizontalPosition() == ImageBackgroundPosition.CENTER) {
				startX = x + (width - imageWidth) / 2;
			}
		} else {
			startX = this.position.getHorizontalPosition().getValue(width);
		}

		// Adjust the background startX, startY, endX and endY based on the repeat flag
		int endX = startX;
		int endY = startY;
		if ((this.repeatFlag & REPEAT_X) != 0) {
			endX = width;
			if ( this.position.isHorizontalPositionRelative() ) {
				if (this.position.getHorizontalPosition() == ImageBackgroundPosition.RIGHT) {
					startX = (width % imageWidth) - imageWidth;
				} else if ( this.position.getHorizontalPosition() == ImageBackgroundPosition.CENTER ) {
					startX = (((width-imageWidth) / 2) % imageWidth) - imageWidth; ;
				} else {
					startX = 0;
				}
			} else {
				startX = (startX % imageWidth) - imageWidth; 
			}
		}
		if ((this.repeatFlag & REPEAT_Y) != 0) {
			endY = height;
			if ( this.position.isVerticalPositionRelative() ) {
				if (this.position.getVerticalPosition() == ImageBackgroundPosition.BOTTOM) {
					startY = (height % imageHeight) - imageHeight;
				} else if ( this.position.getVerticalPosition() == ImageBackgroundPosition.CENTER ) {
					startY = (((height-imageHeight) / 2) % imageHeight) - imageHeight; ;
				} else {
					startY = 0;
				}
			} else {
				startY = (startY % imageHeight) - imageHeight; 
			}
		}

		// Draw the image
		int drawX = startX;
		int drawY = startY;
		graphics.pushContext(x, y, width, height, 0, 0);
		while (drawX <= endX) {
			while (drawY <= endY) {
				graphics.drawBitmap(drawX, drawY, imageWidth, imageHeight, this.image,
						0, 0);
				drawY += imageHeight;
			}
			drawX += imageWidth;
			drawY = startY;
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
