package de.enough.glaze.style.font;

import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import de.enough.glaze.style.Color;
import de.enough.glaze.style.Dimension;
import de.enough.glaze.style.GzFont;

public class GzFontFactory {
	
	private static final Color DEFAULT_COLOR = new Color(0x000000);
	
	public static GzFont createFont(FontFamily fontFamily, Dimension size, Color color, String[] style) {
		return null;
	}
	
	private static GzFont createFont(Font font, Color color) {
		return new GzFont(font, color);
	}
}
