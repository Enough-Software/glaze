package de.enough.glaze.style.background;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.XYRect;
import de.enough.glaze.style.Color;

public class MaskBackground extends GzBackground {

	private final int color;
	private final GzBackground mask, background;
	
	public MaskBackground(Color color, GzBackground mask, GzBackground background) {
		this.color = color.getColor();
		this.mask = mask;
		this.background = background;
	}
	
	/* (non-Javadoc)
	 * @see net.rim.device.api.ui.decor.Background#draw(net.rim.device.api.ui.Graphics, net.rim.device.api.ui.XYRect)
	 */
	public void draw(Graphics graphics, XYRect rect) {
		// remember original color
		int originalColor = graphics.getColor();
			
		Bitmap maskBitmap = new Bitmap(rect.width,rect.height);
		Graphics maskBuffer = Graphics.create(maskBitmap);
		this.mask.draw(maskBuffer, new XYRect(0,0,rect.width,rect.height));
		
		Bitmap backgroundBitmap = new Bitmap(rect.width,rect.height);
		Graphics backgroundBuffer = Graphics.create(backgroundBitmap);
		this.background.draw(backgroundBuffer, new XYRect(0,0,rect.width,rect.height));
			
		int [] maskData = new int[rect.width*rect.height];
		int [] backgroundData = new int[rect.width*rect.height];
		
		maskBitmap.getARGB(maskData, 0, rect.width, 0, 0, rect.width, rect.height);
		backgroundBitmap.getARGB(backgroundData, 0, rect.width, 0, 0, rect.width, rect.height);
		
		int bitMaskedColor = this.color & 0x00FFFFFF;
		for (int i=0;i<maskData.length;i++) {
			if ( (maskData[i] & 0x00FFFFFF) != bitMaskedColor ) {
				backgroundData[i] = 0x00000000;
			}
		}
		
		Bitmap result = new Bitmap(rect.width, rect.height);
		result.setARGB(backgroundData, 0, rect.width, 0, 0, rect.width, rect.height);		
		graphics.drawBitmap(rect.x, rect.y, rect.width, rect.height, result, 0, 0);
		
		// restore original color
		graphics.setColor(originalColor);
	}

	/* (non-Javadoc)
	 * @see net.rim.device.api.ui.decor.Background#isTransparent()
	 */
	public boolean isTransparent() {
		return true;
	} 
}
