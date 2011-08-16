package de.enough.glaze.style;

import net.rim.device.api.ui.Font;

public class GzFont {
	
	private final Font font;
	
	private final Color color;
	
	public GzFont(Font font, Color color) {
		this.font = font;
		this.color = color;
	}
	
	public Font getFont() {
		return this.font;
	}
	
	public Color getColor() {
		return this.color;
	}
}
