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
	public void draw(Graphics graphics, int x, int y, int width, int height) {
		// remember original color
		int originalColor = graphics.getColor();
		
		Bitmap maskBitmap = new Bitmap(width,height);
		Graphics maskBuffer = Graphics.create(maskBitmap);		
			
		Bitmap backgroundBitmap = new Bitmap(width,height);
		Graphics backgroundBuffer = Graphics.create(backgroundBitmap);
		this.background.draw(backgroundBuffer, new XYRect(0,0,width,height));
		
		int bgColorBlack = 0x0;
		maskBuffer.setColor(bgColorBlack);
		maskBuffer.fillRect(0, 0, width, height );
		int[] transparentColorRgb = new int[1];
		maskBitmap.getARGB(transparentColorRgb, 0, 1, 0, 0, 1, 1 );
		bgColorBlack = transparentColorRgb[0];
		this.mask.draw(maskBuffer, new XYRect(0,0,width,height));
		int[] dataBlack = new int[  width * height ];
		maskBitmap.getARGB(dataBlack, 0, width, 0, 0, width, height );
		int bgColorWhite = 0xffffff;
		maskBuffer.setColor(bgColorWhite);
		maskBuffer.fillRect(0, 0, width, height );
		maskBitmap.getARGB(transparentColorRgb, 0, 1, 0, 0, 1, 1 );
		bgColorWhite = transparentColorRgb[0];
		this.mask.draw(maskBuffer, new XYRect(0,0,width,height));
		int[] dataWhite = new int[  width * height ];
		maskBitmap.getARGB(dataWhite, 0, width, 0, 0, width, height );

		int rgbOpacity = (255 << 24);
		int [] resultMask = new int[ dataBlack.length ];
		int lastPixelWhite = 0;
		int lastPixelBlack = 0;
		int lastPixelResult = 0;
		// ensure transparent parts are indeed transparent
		for (int i = 0; i < dataBlack.length; i++) {
			int pixelBlack = dataBlack[i];
			int pixelWhite = dataWhite[i];
			if (pixelBlack == pixelWhite) {
				resultMask[i] = pixelBlack & rgbOpacity;
			} else if (pixelBlack != bgColorBlack || pixelWhite != bgColorWhite ) {
				if (pixelBlack == lastPixelBlack && pixelWhite == lastPixelWhite) {
					resultMask[i] = lastPixelResult;
				} else {
					// this pixel contains translucency:
					int redBlack = (pixelBlack & 0xff0000) >> 16;
					int greenBlack = (pixelBlack & 0xff00) >> 8;
					int blueBlack = (pixelBlack & 0xff);
					int redWhite = (pixelWhite & 0xff0000) >> 16;
					int greenWhite = (pixelWhite & 0xff00) >> 8;
					int blueWhite = (pixelWhite & 0xff);
					
					
					int originalAlpha = 0;
					int originalRed;
					if (redBlack == 0 && redWhite == 255) {
						originalRed = 0;
					} else {
						if (redBlack == 0) {
							redBlack = 1;
						} else if (redWhite == 255) {
							redWhite = 254;
						}
						originalRed = (255 * redBlack) / (redBlack - redWhite + 255);
						originalAlpha = redBlack - redWhite + 255;
					}
					int originalGreen;
					if (greenBlack == 0 && greenWhite == 255) {
						originalGreen = 0;
					} else {
						if (greenBlack == 0) {
							greenBlack = 1;
						} else if (greenWhite == 255) {
							greenWhite = 254;
						}
						originalGreen = (255 * greenBlack) / (greenBlack - greenWhite + 255);
						originalAlpha = greenBlack - greenWhite + 255;
					}
					int originalBlue;
					if (blueBlack == 0 && blueWhite == 255) {
						originalBlue = 0;
					} else {
						if (blueBlack == 0) {
							blueBlack = 1;
						} else if (blueWhite == 255) {
							blueWhite = 254;
						}
						originalBlue = (255 * blueBlack) / (blueBlack - blueWhite + 255);
						originalAlpha = blueBlack - blueWhite + 255;
					}
					lastPixelWhite = pixelWhite;
					lastPixelBlack = pixelBlack;
					lastPixelResult = ((originalAlpha << 24) | (originalRed << 16) | (originalGreen << 8) | originalBlue) & rgbOpacity; 
					resultMask[i] = lastPixelResult; 
				}
			} 
		}					
			
		int [] backgroundData = new int[width*height];
		
		backgroundBitmap.getARGB(backgroundData, 0, width, 0, 0, width, height);
		
		for (int i=0;i<resultMask.length;i++) {
			backgroundData[i] = (backgroundData[i] & 0x00FFFFFF) | ( resultMask[i]  & 0xFF000000);			
		}
		
		Bitmap result = new Bitmap(width, height);
		result.setARGB(backgroundData, 0, width, 0, 0, width, height);		
		graphics.drawBitmap(x, y, width, height, result, 0, 0);
		
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
