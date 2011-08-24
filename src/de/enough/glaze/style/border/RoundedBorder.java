package de.enough.glaze.style.border;

import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.XYRect;
import net.rim.device.api.ui.decor.Border;

public class RoundedBorder extends GzBorder {

	private final int STIPPLE_DASHES = 0xff00ff00;
	private final int STIPPLE_DOTS = 0xAAAAAAAA;
	private final int STIPPLE_SOLID = 0xFFFFFFFF;
	
	private final XYEdges padding;
	private final int color;
	private final int style;

	public RoundedBorder(XYEdges padding,
            int color,
            int style) {
		super(padding, style);
		this.padding = padding;
		this.color = color;
		this.style = style;
		
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.rim.device.api.ui.decor.Border#paint(net.rim.device.api.ui.Graphics,
	 * net.rim.device.api.ui.XYRect)
	 */
	public void paint(Graphics graphics, XYRect rect) {
		
		int oldColor = graphics.getColor();
		int oldStipple = graphics.getStipple();
		
		int stipple = STIPPLE_SOLID;
		switch ( this.style ) {
			case Border.STYLE_DASHED:
				stipple = STIPPLE_DASHES;
			break;
			
			case Border.STYLE_DOTTED:
				stipple = STIPPLE_DOTS;
			break;
		}
		
		graphics.setStipple(stipple);
		graphics.setColor(this.color);
		
		int width = rect.width;
		int x = rect.x;
		int xPts [] = new int[] { x, x, x+this.padding.left, x+width - this.padding.right, x+width, x+width,
			      x+width, x+width, x+width - this.padding.right, x+this.padding.left, x, x
		};
		
		int height = rect.height;
		int y = rect.y;
		int yPts [] = new int[] { y+this.padding.top, y, y, y, y, y+this.padding.top,
			      y + height - this.padding.bottom, y+height, y+height, y+height, y+height, y+height-this.padding.bottom
		};
		
		graphics.drawPathOutline(xPts, yPts, PATH_POINT_TYPES, null, true);
		graphics.setColor(oldColor);
		graphics.setStipple(oldStipple);
	}
}
