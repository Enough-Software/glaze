package de.enough.glaze.style.background;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.XYRect;
import net.rim.device.api.ui.image.Image;
import de.enough.glaze.style.Color;

public class ImageBackground extends GzBackground {

	public static final int POSITION_TOP = 2;
	public static final int POSITION_BOTTOM = 4;
	public static final int POSITION_LEFT = 8;
	public static final int POSITION_RIGHT = 16;
	public static final int POSITION_CENTER = 32;
	
	public static final int REPEAT_X = 2;
	public static final int REPEAT_Y = 4;
	
	private final Bitmap image;
	private final int positionFlag, repeatFlag;
	
	public ImageBackground(Bitmap image, int positionFlag, int repeatFlag) {
		this.image = image;
		this.positionFlag = positionFlag;
		this.repeatFlag = repeatFlag;
	}
	
	/* (non-Javadoc)
	 * @see net.rim.device.api.ui.decor.Background#draw(net.rim.device.api.ui.Graphics, net.rim.device.api.ui.XYRect)
	 */
	public void draw(Graphics graphics, XYRect rect) {
		int width = rect.width;
		int height = rect.height;
		int imageWidth = this.image.getWidth();		
		int imageHeight = this.image.getHeight();
		
		// Calculate startX and startY for drawing the image, based on the position flag
		int startX = 0, startY =0;
		if ( (this.positionFlag & POSITION_TOP) != 0 ) {
			startY = 0;
		}
		if ( (this.positionFlag & POSITION_BOTTOM) != 0 ) {
			startY = height - imageHeight;
		}
		if ( (this.positionFlag & POSITION_LEFT) != 0 ) {
			startX = 0;
		}
		if ( (this.positionFlag & POSITION_RIGHT) != 0 ) {
			startX = width - imageWidth;
		}
		if ( (this.positionFlag & POSITION_CENTER) != 0 ) {
			startX = ( width - imageWidth ) / 2;
			startY = ( height - imageHeight ) / 2;
		}
		
		// Calculate endX and endY for drawing the image, based on the repeat flag
		int endX = startX;
		int endY = startY;
		if ( (this.repeatFlag & REPEAT_X) != 0 ) {
			endX = width;
			startX = 0;
			if ( (this.positionFlag & POSITION_RIGHT)  != 0) {
				int offset = (rect.width % this.image.getWidth()) - image.getWidth();
				startX += offset;
			}
		}
		if ( (this.repeatFlag & REPEAT_Y) != 0 ) {
			endY = height;
			startY = 0;
			if ( (this.positionFlag & POSITION_BOTTOM)  != 0) {
				int offset = (rect.height % this.image.getHeight()) - image.getHeight();
				startY += offset;
			}
		}
		
		// Draw the image
		int x = startX, y = startY;
		graphics.pushContext(rect.x, rect.y, rect.width, rect.height, 0, 0);
		while ( x <=  endX ) {
			while ( y <= endY ) {
				graphics.drawBitmap(rect.x + x, rect.y + y, imageWidth, imageHeight, this.image,0,0);
				y+= imageHeight;
			}
			x+= imageWidth;
			y=startY;
		}
		graphics.popContext();
	
	}

	/* (non-Javadoc)
	 * @see net.rim.device.api.ui.decor.Background#isTransparent()
	 */
	public boolean isTransparent() {
		return true;
	} 
}
