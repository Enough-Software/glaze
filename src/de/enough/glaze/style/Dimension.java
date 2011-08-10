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
	 * the screen percent unit
	 */
	public final static String UNIT_SP = "sp";

	/**
	 * the value
	 */
	private final int value;

	/**
	 * the unit
	 */
	private final String unit;

	/**
	 * the available width
	 */
	private int availableWidth = Integer.MIN_VALUE;

	/**
	 * the device width
	 */
	private int deviceWidth = Integer.MIN_VALUE;

	/**
	 * the calculated pixels
	 */
	private int pixels;

	/**
	 * Creates a new {@link Dimension} instance
	 * 
	 * @param value
	 *            the value
	 * @param unit
	 *            the unit
	 */
	public Dimension(int value, String unit) {
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
		return getValue(deviceWidth, deviceWidth);
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
	private int getValue(int availableWidth, int deviceWidth) {
		if (this.unit == UNIT_PX) {
			this.pixels = this.value;
		} else if (this.unit == UNIT_CM) {
			this.pixels = Ui.convertSize(this.value, Ui.UNITS_cm, Ui.UNITS_px);
		} else if (this.unit == UNIT_MM) {
			this.pixels = Ui.convertSize(this.value, Ui.UNITS_mm, Ui.UNITS_px);
		} else if (this.unit == UNIT_INCH) {
			float cmValue = (float) this.value / (float) CONVERT_INCH_CM;
			int mmValue = MathUtilities.round(cmValue / 10);
			this.pixels = Ui.convertSize(mmValue, Ui.UNITS_mm, Ui.UNITS_px);
		} else if (this.unit == UNIT_PT) {
			this.pixels = Ui.convertSize(this.value, Ui.UNITS_pt, Ui.UNITS_px);
		} else if (this.unit == UNIT_PERCENT) {
			if (availableWidth != this.availableWidth) {
				float pxValue = ((float) availableWidth / 100) * this.value;
				this.pixels = MathUtilities.round(pxValue);
				this.availableWidth = availableWidth;
			}
		} else if (this.unit == UNIT_SP) {
			if (deviceWidth != this.deviceWidth) {
				float pxValue = ((float) deviceWidth / 100) * this.value;
				this.pixels = MathUtilities.round(pxValue);
				this.deviceWidth = deviceWidth;
			}
		} else {
			throw new IllegalArgumentException("unknown dimension unit : "
					+ this.unit);
		}

		return this.pixels;
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
