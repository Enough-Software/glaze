package de.enough.glaze.style.background;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.XYRect;
import de.enough.glaze.style.Color;
import de.enough.glaze.style.Dimension;

public class PatchBackground extends GzBackground {

	
	private final static int POS_TOP = 0;
	private final static int POS_RIGHT = 1;
	private static final int POS_BOTTOM = 2;
	private static final int POS_LEFT = 3;
	
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
		
		paintHorizontalTiles(rect.x + this.margins[POS_LEFT].getValue(), rect.y + this.margins[POS_TOP].getValue(), rect.width-this.margins[POS_RIGHT].getValue()-this.margins[POS_LEFT].getValue(), rect.height-this.margins[POS_TOP].getValue()-this.margins[POS_BOTTOM].getValue(), graphics);
		paintVerticalTiles(rect.x + this.margins[POS_LEFT].getValue(), rect.y + this.margins[POS_TOP].getValue(), rect.width-this.margins[POS_RIGHT].getValue()-this.margins[POS_LEFT].getValue(), rect.height-this.margins[POS_TOP].getValue()-this.margins[POS_BOTTOM].getValue(), graphics);
		paintCorners(rect.x + this.margins[POS_LEFT].getValue(), rect.y + this.margins[POS_TOP].getValue(), rect.width-this.margins[POS_RIGHT].getValue()-this.margins[POS_LEFT].getValue(), rect.height-this.margins[POS_TOP].getValue()-this.margins[POS_BOTTOM].getValue(), graphics);
		paintCenterTiles(rect.x + this.margins[POS_LEFT].getValue(), rect.y + this.margins[POS_TOP].getValue(), rect.width-this.margins[POS_RIGHT].getValue()-this.margins[POS_LEFT].getValue(), rect.height-this.margins[POS_TOP].getValue()-this.margins[POS_BOTTOM].getValue(), graphics);
		
		// restore original color
		graphics.setColor(originalColor);
	}
	
	private void paintHorizontalTiles(int x, int y, int width, int height, Graphics graphics) {
				
		int tilingTop = this.tiling[POS_TOP].getValue();
		int tilingRight = this.tiling[POS_RIGHT].getValue();
		int tilingBottom = this.tiling[POS_BOTTOM].getValue();
		int tilingLeft = this.tiling[POS_LEFT].getValue();
		
		int srcX = tilingLeft;
		int srcY;
		
		int srcWidth = Math.max(1,this.image.getWidth() - (tilingLeft + tilingRight));
		int srcHeight = tilingTop;
		
		int dstX = x + tilingLeft;
		int dstY;
		
		int fillWidth = Math.max(1,width - (tilingLeft + tilingRight));
		
		srcY = 0;
		dstY = y;
		
		graphics.pushContext(new XYRect(dstX,dstY,fillWidth,srcHeight), 0, 0);
		for (int xOffset = 0; xOffset < fillWidth; xOffset = xOffset + srcWidth) {
			graphics.drawBitmap(dstX+xOffset, dstY, srcWidth, srcHeight, image, srcX, 0);
		}
		graphics.popContext();
		
		dstY = y + (height - tilingBottom);
		srcY = image.getHeight() - tilingBottom;
		srcHeight = tilingBottom;
		
		graphics.pushContext(new XYRect(dstX,dstY,fillWidth,srcHeight), 0, 0);
		for (int xOffset = 0; xOffset < fillWidth; xOffset = xOffset + srcWidth) {
			graphics.drawBitmap(dstX+xOffset, dstY, srcWidth, srcHeight, image, srcX, srcY);
		}
		graphics.popContext();
	}
	
	private void paintVerticalTiles(int x, int y, int width, int height, Graphics graphics) {
		
		int tilingTop = this.tiling[POS_TOP].getValue();
		int tilingRight = this.tiling[POS_RIGHT].getValue();
		int tilingBottom = this.tiling[POS_BOTTOM].getValue();
		int tilingLeft = this.tiling[POS_LEFT].getValue();
		
		int srcX = 0;
		int srcY = tilingTop;
		
		int srcWidth = tilingLeft;
		int srcHeight = Math.max(1,this.image.getHeight() - (tilingTop+tilingBottom));
		
		int dstX = x;
		int dstY = y + tilingTop;
		
		int fillHeight = Math.max(1,height - (tilingTop + tilingBottom));
				
		graphics.pushContext(new XYRect(dstX,dstY,srcWidth,fillHeight), 0, 0);
		for (int yOffset = 0; yOffset < fillHeight; yOffset = yOffset + srcHeight) {
			graphics.drawBitmap(dstX, dstY+yOffset, srcWidth, srcHeight, image, srcX, srcY);
		}
		graphics.popContext();
		
		srcX = this.image.getWidth() - tilingRight;
		dstX = x + width - tilingRight;
		
		graphics.pushContext(new XYRect(dstX,dstY,srcWidth,fillHeight), 0, 0);
		for (int yOffset = 0; yOffset < fillHeight; yOffset = yOffset + srcHeight) {
			graphics.drawBitmap(dstX, dstY+yOffset, srcWidth, srcHeight, image, srcX, srcY);
		}
		graphics.popContext();
	}
	
	private void paintCenterTiles(int x, int y, int width, int height, Graphics graphics) {
		
		int tilingTop = this.tiling[POS_TOP].getValue();
		int tilingRight = this.tiling[POS_RIGHT].getValue();
		int tilingBottom = this.tiling[POS_BOTTOM].getValue();
		int tilingLeft = this.tiling[POS_LEFT].getValue();
		
		int dstX = x + tilingLeft;
		int dstY = y + tilingTop;
		
		int fillWidth = Math.max(1,width - ( tilingRight + tilingLeft));
		int fillHeight = Math.max(1,height - ( tilingTop + tilingBottom));
		
		int srcX = tilingLeft;
		int srcY = tilingTop;
		
		int srcWidth = Math.max(1,this.image.getWidth() - ( tilingRight + tilingLeft));
		int srcHeight = Math.max(1,this.image.getHeight() - (tilingTop+tilingBottom));
		
				
		graphics.pushContext(new XYRect(dstX,dstY,fillWidth,fillHeight), 0, 0);
		for (int yOffset = 0; yOffset < fillHeight; yOffset = yOffset + srcHeight) {
			for (int xOffset = 0; xOffset < fillWidth; xOffset = xOffset + srcWidth) {
				graphics.drawBitmap(dstX+xOffset, dstY+yOffset, srcWidth, srcHeight, this.image, srcX, srcY);
			}
		}
		graphics.popContext();
		
	}
	
	private void paintCorners(int x, int y, int width, int height, Graphics graphics) {
		
		int tilingTop = this.tiling[POS_TOP].getValue();
		int tilingRight = this.tiling[POS_RIGHT].getValue();
		int tilingBottom = this.tiling[POS_BOTTOM].getValue();
		int tilingLeft = this.tiling[POS_LEFT].getValue();
		
		graphics.drawBitmap(x, y, tilingLeft, tilingTop, this.image, 0, 0);
		graphics.drawBitmap(x+width-tilingRight, y, tilingRight, tilingTop, this.image, image.getWidth()-tilingRight, 0);
		graphics.drawBitmap(x,y+height-tilingBottom, tilingLeft,tilingBottom,this.image, 0, this.image.getHeight()-tilingBottom);
		graphics.drawBitmap(x+width-tilingRight,y+height-tilingBottom, tilingRight,tilingBottom,this.image, image.getWidth()-tilingRight, this.image.getHeight()-tilingBottom);
	}
	

	/* (non-Javadoc)
	 * @see net.rim.device.api.ui.decor.Background#isTransparent()
	 */
	public boolean isTransparent() {
		return true;
	} 
}
