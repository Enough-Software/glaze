package de.enough.glaze.style.property.background.image;

import de.enough.glaze.style.Dimension;

public class ImageBackgroundPosition {
	public static Dimension LEFT = new Dimension(Float.MAX_VALUE, Dimension.UNIT_PX);
	
	public static Dimension RIGHT = new Dimension(Float.MAX_VALUE, Dimension.UNIT_PX);
	
	public static Dimension TOP = new Dimension(Float.MAX_VALUE, Dimension.UNIT_PX);
	
	public static Dimension BOTTOM = new Dimension(Float.MAX_VALUE, Dimension.UNIT_PX);
	
	public static Dimension CENTER = new Dimension(Float.MAX_VALUE, Dimension.UNIT_PX);
	
	static {
		LEFT = new Dimension(Float.MAX_VALUE, Dimension.UNIT_PX);
		RIGHT = new Dimension(Float.MAX_VALUE, Dimension.UNIT_PX);
		TOP = new Dimension(Float.MAX_VALUE, Dimension.UNIT_PX);
		BOTTOM = new Dimension(Float.MAX_VALUE, Dimension.UNIT_PX);
		CENTER = new Dimension(Float.MAX_VALUE, Dimension.UNIT_PX);
	}
	
	private final Dimension horizontalPosition;
	
	private final Dimension verticalPosition;
	
	public static boolean isValidHorizontalPosition(Dimension dimension) {
		return dimension == LEFT || dimension == RIGHT || dimension == CENTER;
	}
	
	public static boolean isValidVerticalPosition(Dimension dimension) {
		return dimension == TOP || dimension == BOTTOM || dimension == CENTER;
	}
	
	public ImageBackgroundPosition() {
		this(CENTER, CENTER);
	}
	
	public ImageBackgroundPosition(Dimension horizontalPosition) {
		this(horizontalPosition, CENTER);
	}
	
	public ImageBackgroundPosition(Dimension horizontalPosition, Dimension verticalPosition) {
		this.horizontalPosition = horizontalPosition;
		this.verticalPosition = verticalPosition;
	}
	
	public boolean isHorizontalPositionRelative() {
		return this.horizontalPosition == LEFT || this.horizontalPosition == RIGHT || this.horizontalPosition == CENTER;
	}
	
	public boolean isVerticalPositionRelative() {
		return this.verticalPosition == TOP || this.verticalPosition == BOTTOM || this.verticalPosition == CENTER;
	}
	
	public Dimension getHorizontalPosition() {
		return this.horizontalPosition;
	}
	
	public Dimension getVerticalPosition() {
		return this.verticalPosition;
	}
}
