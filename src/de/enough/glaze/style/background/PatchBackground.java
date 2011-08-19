package de.enough.glaze.style.background;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.XYRect;
import de.enough.glaze.style.Color;
import de.enough.glaze.style.Dimension;

public class PatchBackground extends GzBackground {

	private final Bitmap image;
	private final Dimension[] margins;
	private final Dimension[] tiling;
	
	
	public PatchBackground(Bitmap image, Dimension[] margins, Dimension[] tiling) {
		this.image = image;
		this.margins = margins;
		this.tiling = tiling;
	}
	
	/* (non-Javadoc)
	 * @see net.rim.device.api.ui.decor.Background#draw(net.rim.device.api.ui.Graphics, net.rim.device.api.ui.XYRect)
	 */
	public void draw(Graphics graphics, XYRect rect) {
		// remember original color
		int originalColor = graphics.getColor();
		
		paintHorizontalTiles(0+this.margins[3].getValue(), this.margins[0].getValue(), rect.width-this.margins[1].getValue()-this.margins[3].getValue(), rect.height-this.margins[0].getValue()-this.margins[2].getValue(), graphics);
		
		// restore original color
		graphics.setColor(originalColor);
	}
	
	private void paintHorizontalTiles(int x, int y, int width, int height, Graphics graphics) {
		int srcX = this.tiling[3].getValue();
		int srcY;
		
		int srcWidth = this.image.getWidth() - (this.tiling[3].getValue() + this.tiling[1].getValue());
		int srcHeight = this.tiling[0].getValue();
		
		int dstX = x + this.tiling[3].getValue();
		int dstY;
		
		int fillWidth = width - (this.tiling[3].getValue() + this.tiling[1].getValue());
		
		// draw horizontal top tiles:
		srcY = 0;
		dstY = y;
		
		for (int xOffset = 0; xOffset < fillWidth; xOffset = xOffset + srcWidth) {
			graphics.drawBitmap(dstX+xOffset, dstY, srcWidth, srcHeight, image, srcX, 0);
		}
		
		dstY = y + (height - this.tiling[2].getValue());
		srcY = image.getHeight() - this.tiling[2].getValue();
		srcHeight = this.tiling[2].getValue();
		for (int xOffset = 0; xOffset < fillWidth; xOffset = xOffset + srcWidth) {
			graphics.drawBitmap(dstX+xOffset, dstY, srcWidth, srcHeight, image, srcX, srcY);
		}
	}
	

	/* (non-Javadoc)
	 * @see net.rim.device.api.ui.decor.Background#isTransparent()
	 */
	public boolean isTransparent() {
		return false;
	} 
}
