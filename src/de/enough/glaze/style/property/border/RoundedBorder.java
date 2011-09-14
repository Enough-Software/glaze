package de.enough.glaze.style.property.border;

import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.XYRect;
import net.rim.device.api.ui.decor.Border;

public class RoundedBorder extends GzBorder {

	private final int STIPPLE_DASHES = 0xff00ff00;
	private final int STIPPLE_DOTS = 0xAAAAAAAA;
	private final int STIPPLE_SOLID = 0xFFFFFFFF;
	
	private final XYEdges widths;
	private final int color;
	private final int style;

	public RoundedBorder(XYEdges widths,
            int color,
            int style) {
		super(widths, style);
		this.widths = widths;
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
		graphics.setDrawingStyle(Graphics.DRAWSTYLE_AALINES, true);
		
		int width = rect.width;
		int x = rect.x;
		int xPts [] = new int[] { x, x, x+this.widths.left-1, x+width - this.widths.right, x+width-1, x+width-1,
			      x+width-1, x+width-1, x+width - this.widths.right, x+this.widths.left-1, x, x
		};
		
		int height = rect.height;
		int y = rect.y;
		int yPts [] = new int[] { y+this.widths.top-1, y, y, y, y, y+this.widths.top-1,
			      y + height - this.widths.bottom, y+height-1, y+height-1, y+height-1, y+height-1, y+height-this.widths.bottom
		};
		
		graphics.drawPathOutline(xPts, yPts, PATH_POINT_TYPES, null,true);
		for (int i=0;i <yPts.length;i++) {
			graphics.drawPoint(xPts[i], yPts[i]);
		}
		graphics.setColor(0xAA00FF00);
		graphics.drawRoundRect(x, y, width, height, this.widths.right*2, this.widths.right*2);
		graphics.setColor(oldColor);
		graphics.setStipple(oldStipple);
		graphics.setDrawingStyle(Graphics.DRAWSTYLE_AALINES, false);
	}
}
