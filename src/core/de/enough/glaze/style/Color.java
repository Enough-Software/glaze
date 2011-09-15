package de.enough.glaze.style;

import java.util.Hashtable;

import net.rim.device.api.ui.Graphics;

/**
 * A class representing a color with its alpha and rgb value.
 * 
 * @author Andre
 * 
 */
public class Color {

	/**
	 * the default colors map
	 */
	public static Hashtable DEFAULT_COLORS = new Hashtable();

	static {
		// initialize common colors with their name and value
		DEFAULT_COLORS.put("aqua", new Color(0x00ffff));
		DEFAULT_COLORS.put("black", new Color(0x000000));
		DEFAULT_COLORS.put("blue", new Color(0x0000ff));
		DEFAULT_COLORS.put("fuchsia", new Color(0xff00ff));
		DEFAULT_COLORS.put("gray", new Color(0x808080));
		DEFAULT_COLORS.put("green", new Color(0x008000));
		DEFAULT_COLORS.put("lime", new Color(0x00ff00));
		DEFAULT_COLORS.put("maroon", new Color(0x800000));
		DEFAULT_COLORS.put("navy", new Color(0x000080));
		DEFAULT_COLORS.put("olive", new Color(0x808000));
		DEFAULT_COLORS.put("orange", new Color(0xffa500));
		DEFAULT_COLORS.put("purple", new Color(0x800080));
		DEFAULT_COLORS.put("red", new Color(0xff0000));
		DEFAULT_COLORS.put("silver", new Color(0xc0c0c0));
		DEFAULT_COLORS.put("teal", new Color(0x008080));
		DEFAULT_COLORS.put("white", new Color(0xffffff));
		DEFAULT_COLORS.put("yellow", new Color(0xffff00));
	}

	/**
	 * the mask to extract the rgb value of a color
	 */
	private static final int RGB_MASK = 0x00FFFFFF;

	/**
	 * the mask to extract the alpha value of a color
	 */
	private static final int ALPHA_MASK = 0xFF;

	private final int argb;

	/**
	 * the rgb value
	 */
	private final int rgb;

	/**
	 * the alpha value
	 */
	private final int alpha;

	private int storedColor;

	private int storedGlobalAlpha;

	/**
	 * Creates a new {@link Color} instance
	 * 
	 * @param argb
	 *            the argb value of the color
	 */
	public Color(int argb) {
		this.argb = argb;
		this.rgb = argb & RGB_MASK;
		int alpha = (argb >> 24) & ALPHA_MASK;
		// if no alpha is set ...
		if (alpha == 0) {
			// use full opacity
			this.alpha = 0xFF;
			// otherwise ...
		} else {
			// use the alpha value
			this.alpha = alpha;
		}
	}

	/**
	 * Returns the alpha value of the argb color
	 * 
	 * @return the alpha value of the argb color
	 */
	public int getAlpha() {
		return this.alpha;
	}

	/**
	 * Returns the rgb color
	 * 
	 * @return the rgb color
	 */
	public int getColor() {
		return this.rgb;
	}

	/**
	 * Sets the color in the given {@link Graphics} instance
	 * 
	 * @param graphics
	 *            the {@link Graphics} instance
	 */
	public void set(Graphics graphics) {
		// store the original color and global alpha
		this.storedGlobalAlpha = graphics.getGlobalAlpha();
		this.storedColor = graphics.getColor();

		// set the global alpha and color
		graphics.setGlobalAlpha(this.alpha);
		graphics.setColor(this.rgb);
	}

	/**
	 * Resets the color and alpha in the given {@link Graphics} instance
	 * 
	 * @param graphics
	 *            the {@link Graphics} instance
	 */
	public void reset(Graphics graphics) {
		// reset the global alpha and color
		graphics.setGlobalAlpha(this.storedGlobalAlpha);
		graphics.setColor(this.storedColor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Color [ #" + Integer.toHexString(this.argb) + " ]";
	}
}
