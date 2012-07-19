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
 
package de.enough.glaze.style;

import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.util.MathUtilities;

/**
 * A class representing a dimension. A dimension consists of a floating point
 * value and a unit (e.g. pixels, points). The methods {@link #getValue()} and
 * {@link #getValue(int)} convert and return the dimension to its value in
 * pixels. Relative dimensions like percent use the given available width for
 * the conversion.
 * 
 * @author Andre
 * 
 */
public class Dimension {

	/**
	 * the conversion factor from inch to cm
	 */
	private final static float CONVERT_INCH_CM = (float) 0.393700787;

	/**
	 * the px unit
	 */
	public final static String UNIT_PX = "px";

	/**
	 * the cm unit
	 */
	public final static String UNIT_CM = "cm";

	/**
	 * the mm unit
	 */
	public final static String UNIT_MM = "mm";

	/**
	 * the inch unit
	 */
	public final static String UNIT_INCH = "in";

	/**
	 * the pt unit
	 */
	public final static String UNIT_PT = "pt";

	/**
	 * the percent unit
	 */
	public final static String UNIT_PERCENT = "%";

	/**
	 * the screen width percent unit
	 */
	public final static String UNIT_WP = "wp";

	/**
	 * the screen width percent unit
	 */
	public final static String UNIT_HP = "hp";

	/**
	 * a zero dimension
	 */
	public final static Dimension ZERO = new Dimension(0, UNIT_PX);

	/**
	 * the value
	 */
	private final float value;

	/**
	 * the unit
	 */
	private final String unit;

	/**
	 * Creates a new {@link Dimension} instance
	 * 
	 * @param value
	 *            the value
	 * @param unit
	 *            the unit
	 */
	public Dimension(float value, String unit) {
		this.value = value;
		this.unit = unit;
	}

	/**
	 * Calculates and returns the pixel value. The base for relative dimensions
	 * (like percent) is the screen width.
	 * 
	 * @return the calculated pixels
	 */
	public int getValue() {
		return getValue(Display.getWidth());
	}

	/**
	 * Calculates and returns the pixel value.
	 * 
	 * @param availableWidth
	 *            the available width (for percentual calculations)
	 * @return the calculated pixels
	 */
	public int getValue(int availableWidth) {
		int pixels = 0;
		if (UNIT_PX.equals(this.unit)) {
			pixels = MathUtilities.round(this.value);
		} else if (UNIT_CM.equals(this.unit)) {
			float mmFloatValue = this.value * 10;
			int mmValue = MathUtilities.round(mmFloatValue);
			pixels = Ui.convertSize(mmValue, Ui.UNITS_mm, Ui.UNITS_px);
		} else if (UNIT_MM.equals(this.unit)) {
			int mmValue = MathUtilities.round(this.value);
			pixels = Ui.convertSize(mmValue, Ui.UNITS_mm, Ui.UNITS_px);
		} else if (UNIT_INCH.equals(this.unit)) {
			float cmValue = (float) this.value / (float) CONVERT_INCH_CM;
			float mmFloatValue = cmValue * 10;
			int mmValue = MathUtilities.round(mmFloatValue);
			pixels = Ui.convertSize(mmValue, Ui.UNITS_mm, Ui.UNITS_px);
		} else if (UNIT_PT.equals(this.unit)) {
			int ptValue = MathUtilities.round(this.value);
			pixels = Ui.convertSize(ptValue, Ui.UNITS_pt, Ui.UNITS_px);
		} else if (UNIT_PERCENT.equals(this.unit)) {
			float pxValue = ((float) availableWidth / 100) * this.value;
			pixels = MathUtilities.round(pxValue);
		} else if (UNIT_WP.equals(this.unit)) {
			int screenWidth = Display.getWidth();
			float pxValue = ((float) screenWidth / 100) * this.value;
			pixels = MathUtilities.round(pxValue);
		} else if (UNIT_HP.equals(this.unit)) {
			int screenHeight = Display.getHeight();
			float pxValue = ((float) screenHeight / 100) * this.value;
			pixels = MathUtilities.round(pxValue);
		} else {
			throw new IllegalArgumentException("unknown dimension unit : "
					+ this.unit);
		}

		return pixels;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Dimension [ value:" + this.value + ", unit:" + this.unit + " ]";
	}
}
