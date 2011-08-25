package de.enough.glaze.style.background;

import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.decor.Border;
import de.enough.glaze.style.Color;
import de.enough.glaze.style.Dimension;

public class RoundedBackground extends GzBackground {

	private final int color;
	private final Dimension [] dimensions;
	
	public RoundedBackground(Color color, Dimension [] dimensions) {
		this.color = color.getColor();
		this.dimensions = dimensions;
	}
	
	private static final byte[] PATH_POINT_TYPES = {
	    Graphics.CURVEDPATH_END_POINT, 
	    Graphics.CURVEDPATH_QUADRATIC_BEZIER_CONTROL_POINT,
	    Graphics.CURVEDPATH_END_POINT, 
	    Graphics.CURVEDPATH_END_POINT, 
	    Graphics.CURVEDPATH_QUADRATIC_BEZIER_CONTROL_POINT,
	    Graphics.CURVEDPATH_END_POINT, 			
	    Graphics.CURVEDPATH_END_POINT, 
	    Graphics.CURVEDPATH_QUADRATIC_BEZIER_CONTROL_POINT,
	    Graphics.CURVEDPATH_END_POINT, 
	    Graphics.CURVEDPATH_END_POINT, 
	    Graphics.CURVEDPATH_QUADRATIC_BEZIER_CONTROL_POINT,
	    Graphics.CURVEDPATH_END_POINT, 
	  };
	
	/* (non-Javadoc)
	 * @see net.rim.device.api.ui.decor.Background#draw(net.rim.device.api.ui.Graphics, net.rim.device.api.ui.XYRect)
	 */
	public void draw(Graphics graphics, int x, int y, int width, int height) {
		
		int oldColor = graphics.getColor();
		graphics.setColor(this.color);
		graphics.setDrawingStyle(Graphics.DRAWSTYLE_AAPOLYGONS, true);
		
		int marginLeft = this.dimensions[3].getValue();
		int marginRight = this.dimensions[1].getValue();
		int marginTop = this.dimensions[0].getValue();
		int marginBottom = this.dimensions[2].getValue();
		
		int xPts [] = new int[] { x, x, x+marginLeft, x+width - marginRight, x+width, x+width,
			      x+width, x+width, x+width - marginRight, x+marginLeft, x, x
		};
		
		int yPts [] = new int[] { y+marginTop, y, y, y, y, y+marginTop,
			      y + height - marginBottom, y+height, y+height, y+height, y+height, y+height-marginBottom
		};
		
		graphics.drawFilledPath(xPts, yPts, PATH_POINT_TYPES, null);
		graphics.setColor(oldColor);
		graphics.setDrawingStyle(Graphics.DRAWSTYLE_AAPOLYGONS, false);
	}

	/* (non-Javadoc)
	 * @see net.rim.device.api.ui.decor.Background#isTransparent()
	 */
	public boolean isTransparent() {
		return true;
	} 
}
