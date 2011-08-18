package de.enough.glaze.style;

import java.util.Hashtable;

import net.rim.device.api.ui.Graphics;

public class Color {
	
	public static Hashtable DEFAULT_COLORS = new Hashtable();
	
	static {
		DEFAULT_COLORS.put("aqua",   new Color(0x00ffff) );
		DEFAULT_COLORS.put("black",  new Color(0x000000) );
		DEFAULT_COLORS.put("blue",   new Color(0x0000ff) );
		DEFAULT_COLORS.put("fuchsia",new Color(0xff00ff) );
		DEFAULT_COLORS.put("gray",   new Color(0x808080) );
		DEFAULT_COLORS.put("green",  new Color(0x008000) );
		DEFAULT_COLORS.put("lime",   new Color(0x00ff00) );
		DEFAULT_COLORS.put("maroon", new Color(0x800000) );
		DEFAULT_COLORS.put("navy",   new Color(0x000080) );
		DEFAULT_COLORS.put("olive",  new Color(0x808000) );
		DEFAULT_COLORS.put("orange", new Color(0xffa500) );
		DEFAULT_COLORS.put("purple", new Color(0x800080) );
		DEFAULT_COLORS.put("red",    new Color(0xff0000) );
		DEFAULT_COLORS.put("silver", new Color(0xc0c0c0) );
		DEFAULT_COLORS.put("teal",   new Color(0x008080) );
		DEFAULT_COLORS.put("white",  new Color(0xffffff) );
		DEFAULT_COLORS.put("yellow", new Color(0xffff00) );
	}
	
	private static final int RGB_MASK = 0x00FFFFFF;
	
	private static final int ALPHA_MASK = 0xFF000000;
	
	private final int color;
	
	private final int rgb;
	
	private final int alpha;
	
	public Color(int argb) {
		this.color = argb;
		this.rgb = argb & RGB_MASK;
		System.out.println(Integer.toHexString(this.rgb));
		this.alpha = (argb & ALPHA_MASK) >> 32;
		System.out.println(Integer.toHexString(this.alpha));
	}
	
	public void set(Graphics graphics) {
		graphics.setGlobalAlpha(this.alpha);
		graphics.setColor(this.rgb);
	}
	
	public int getColor() {
		return this.color;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Color [ #" + Integer.toHexString(this.color) + " ]";
	}
}
