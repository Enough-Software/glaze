/*
 *
 * Copyright: (c) 2012 Enough Software GmbH & Co. KG
 *
 * Licensed under:
 * 1. MIT: http://www.opensource.org/licenses/mit-license.php
 * 2. Apache 2.0: http://opensource.org/licenses/apache2.0
 * 3. GPL with classpath exception: http://www.gnu.org/software/classpath/license.html
 *
 * You may not use this file except in compliance with these licenses.
 *
 */
 
package de.enough.glaze.style.property.background.image;

import de.enough.glaze.style.Dimension;

/**
 * A class describing the position of an image in an image background
 * 
 * @author Andre
 * 
 */
public class ImageBackgroundPosition {

	/**
	 * the relative left position
	 */
	public static Dimension LEFT = new Dimension(0, Dimension.UNIT_PX);

	/**
	 * the relative right position
	 */
	public static Dimension RIGHT = new Dimension(0, Dimension.UNIT_PX);

	/**
	 * the relative top position
	 */
	public static Dimension TOP = new Dimension(0, Dimension.UNIT_PX);

	/**
	 * the relative bottom position
	 */
	public static Dimension BOTTOM = new Dimension(0, Dimension.UNIT_PX);

	/**
	 * the relative center position
	 */
	public static Dimension CENTER = new Dimension(0, Dimension.UNIT_PX);

	static {
		LEFT = new Dimension(0, Dimension.UNIT_PX);
		RIGHT = new Dimension(0, Dimension.UNIT_PX);
		TOP = new Dimension(0, Dimension.UNIT_PX);
		BOTTOM = new Dimension(0, Dimension.UNIT_PX);
		CENTER = new Dimension(0, Dimension.UNIT_PX);
	}

	/**
	 * the horizontal position
	 */
	private final Dimension horizontalPosition;

	/**
	 * the vertical position
	 */
	private final Dimension verticalPosition;

	/**
	 * Returns true if the given position is a valid horizontal position
	 * 
	 * @param dimension
	 *            the dimension
	 * @return true if the given position is a valid horizontal position
	 *         otherwise false
	 */
	public static boolean isValidHorizontalPosition(Dimension dimension) {
		return dimension == LEFT || dimension == RIGHT || dimension == CENTER;
	}

	/**
	 * Returns true if the given position is a valid vertical position
	 * 
	 * @param dimension
	 *            the dimension
	 * @return true if the given position is a valid vertical position otherwise
	 *         false
	 */
	public static boolean isValidVerticalPosition(Dimension dimension) {
		return dimension == TOP || dimension == BOTTOM || dimension == CENTER;
	}

	/**
	 * Constructs a new {@link ImageBackgroundPosition} instance
	 */
	public ImageBackgroundPosition() {
		// use center as the horizontal and vertical position
		this(CENTER, CENTER);
	}

	/**
	 * Constructs a new {@link ImageBackgroundPosition} instance
	 * 
	 * @param horizontalPosition
	 *            the horizontal position
	 */
	public ImageBackgroundPosition(Dimension horizontalPosition) {
		// use the dimension as the horizontal position and center as the
		// vertical position
		this(horizontalPosition, CENTER);
	}

	/**
	 * Constructs a new {@link ImageBackgroundPosition} instance
	 * 
	 * @param horizontalPosition
	 *            the horizontal position
	 * @param verticalPosition
	 *            the vertical position
	 */
	public ImageBackgroundPosition(Dimension horizontalPosition,
			Dimension verticalPosition) {
		this.horizontalPosition = horizontalPosition;
		this.verticalPosition = verticalPosition;
	}

	/**
	 * Returns true if the horizontal position is relative
	 * 
	 * @return true if the horizontal position is relative otherwise false
	 */
	public boolean isHorizontalPositionRelative() {
		return this.horizontalPosition == LEFT
				|| this.horizontalPosition == RIGHT
				|| this.horizontalPosition == CENTER;
	}

	/**
	 * Returns true if the vertical position is relative
	 * 
	 * @return true if the vertical position is relative otherwise false
	 */
	public boolean isVerticalPositionRelative() {
		return this.verticalPosition == TOP || this.verticalPosition == BOTTOM
				|| this.verticalPosition == CENTER;
	}

	/**
	 * Returns the horizontal position
	 * 
	 * @return the horizontal position
	 */
	public Dimension getHorizontalPosition() {
		return this.horizontalPosition;
	}

	/**
	 * Returns the vertical position
	 * 
	 * @return the vertical position
	 */
	public Dimension getVerticalPosition() {
		return this.verticalPosition;
	}
}
