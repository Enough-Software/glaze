package de.enough.glaze.style.background;

import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.XYRect;
import de.enough.glaze.style.Color;
import de.enough.glaze.style.Dimension;

public class RoundrectBackground extends GzBackground {

	private final int color;
	private final Dimension [] dimensions;
	
	public RoundrectBackground(Color color, Dimension [] dimensions) {
		this.color = color.getColor();
		this.dimensions = dimensions;
	}
	
	/* (non-Javadoc)
	 * @see net.rim.device.api.ui.decor.Background#draw(net.rim.device.api.ui.Graphics, net.rim.device.api.ui.XYRect)
	 */
	public void draw(Graphics graphics, XYRect rect) {
		
		int width = rect.width;
		int height = rect.height;
		
		// remember original color
		int originalColor = graphics.getColor();
		
		int startX=0, endX = width;
		int startY=0, endY = height;
		
		graphics.setColor(this.color);
		
		if ( this.dimensions[0] != null ) {
			int arcSize = dimensions[0].getValue();
			graphics.fillArc(0, 0, arcSize*2, arcSize*2, 180, -90);
			graphics.fillRect(arcSize, 0, width/2-arcSize, height/2);
			graphics.fillRect(0, arcSize, width/2, height/2-arcSize);
		} else {
			graphics.fillRect(0, 0, width/2, height/2);
		}
		
		if ( this.dimensions[1] != null ) {
			int arcSize = dimensions[1].getValue();
			graphics.fillArc(width-arcSize*2, 0, arcSize*2, arcSize*2, 90, -90);
			graphics.fillRect(width/2, 0, width/2-arcSize, height/2);
			graphics.fillRect(width/2, arcSize, width/2, height/2-arcSize);
		} else {
			graphics.fillRect(width/2, 0, width/2,height/2);
		}
		
		if ( this.dimensions[2] != null ) {
			int arcSize = dimensions[2].getValue();
			graphics.fillArc(width-arcSize*2, height-arcSize*2, arcSize*2, arcSize*2, 0, -90);
			graphics.fillRect(width/2, height/2, width/2, height/2-arcSize);
			graphics.fillRect(width/2, height/2, width/2-arcSize, height/2);
		} else {
			graphics.fillRect(width/2, height/2,  width/2,height/2);
		}
		
		if ( this.dimensions[3] != null ) {
			int arcSize = dimensions[3].getValue();
			graphics.fillArc(0, height-arcSize*2, arcSize*2, arcSize*2, 180, 90);
			graphics.fillRect(0, height/2, width/2, height/2-arcSize);
			graphics.fillRect(arcSize, height/2, width/2-arcSize, height/2);
		} else {
			graphics.fillRect(0, height/2,  width/2,height/2);
		}
		
		// restore original color
		graphics.setColor(originalColor);
	}

	/* (non-Javadoc)
	 * @see net.rim.device.api.ui.decor.Background#isTransparent()
	 */
	public boolean isTransparent() {
		return false;
	} 
}
