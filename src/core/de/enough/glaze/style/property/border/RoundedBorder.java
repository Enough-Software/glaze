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
		
		// Set the desired drawing settings
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
		int height = rect.height;
		int x = rect.x;
		int y = rect.y;
		
		
		// Draw the corners using drawRoundRect() and clipping, to ensure corners are symmetrical	
		
		// Top-left
		graphics.pushContext(new XYRect(x,y,this.widths.left,this.widths.top),0,0);
		graphics.drawRoundRect(x,y, width, height, this.widths.left*2, this.widths.top*2);
		graphics.popContext();
		
		// Top-right
		graphics.pushContext(new XYRect(x+width-this.widths.right,y,this.widths.right,this.widths.top),0,0);
		graphics.drawRoundRect(x,y, width, height, this.widths.right*2, this.widths.top*2);
		graphics.popContext();
		
		// Bottom-right
		graphics.pushContext(new XYRect(x+width-this.widths.right,y+height-this.widths.bottom,this.widths.right,this.widths.bottom),0,0);
		graphics.drawRoundRect(x,y, width, height, this.widths.right*2, this.widths.bottom*2);
		graphics.popContext();
		
		// Bottom-left
		graphics.pushContext(new XYRect(x,y+height-this.widths.bottom,this.widths.left,this.widths.bottom),0,0);
		graphics.drawRoundRect(x,y, width, height, this.widths.left*2, this.widths.bottom*2);
		graphics.popContext();
		
		// Draw the connecting lines
		
		// Top
		graphics.drawLine(x+this.widths.left, y, x+width-this.widths.right, y);
		
		// Bottom
		graphics.drawLine(x+this.widths.left, y+height-1, x+width-this.widths.right, y+height-1);
		
		// Left
		graphics.drawLine(x,y+this.widths.top,x,y+height-this.widths.bottom);
		
		// Right
		graphics.drawLine(x+width-1,y+this.widths.top,x+width-1,y+height-this.widths.bottom);
					
		// Restore default drawing settings
		graphics.setColor(oldColor);
		graphics.setStipple(oldStipple);
		graphics.setDrawingStyle(Graphics.DRAWSTYLE_AALINES, false);
	}
}
