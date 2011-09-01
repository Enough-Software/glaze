package de.enough.glaze.style;

import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.util.MathUtilities;

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
	 * Calculates and returns the pixels
	 * 
	 * @return the calculated pixels
	 */
	public int getValue() {
		int deviceWidth = Display.getWidth();
		int deviceHeight = Display.getHeight();
		return getValue(deviceWidth, deviceWidth, deviceHeight);
	}

	/**
	 * Calculates and returns the pixels
	 * 
	 * @param availableWidth
	 *            the available width
	 * @return the calculated pixels
	 */
	public int getValue(int availableWidth) {
		int deviceWidth = Display.getWidth();
		int deviceHeight = Display.getHeight();
		return getValue(availableWidth, deviceWidth, deviceHeight);
	}

	/**
	 * Calculates and returns the pixels
	 * 
	 * @param availableWidth
	 *            the available width (for percentual calculations)
	 * @param deviceWidth
	 *            the device width (for percentual calculations)
	 * @return the calculated pixels
	 */
	private int getValue(int availableWidth, int deviceWidth, int deviceHeight) {
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
			float pxValue = ((float) deviceWidth / 100) * this.value;
			pixels = MathUtilities.round(pxValue);
		} else if (UNIT_HP.equals(this.unit)) {
			float pxValue = ((float) deviceHeight / 100) * this.value;
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
