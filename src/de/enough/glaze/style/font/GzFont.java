package de.enough.glaze.style.font;

import net.rim.device.api.ui.Font;
import de.enough.glaze.style.Color;
import de.enough.glaze.style.definition.Definable;
import de.enough.glaze.style.definition.Definition;

public class GzFont implements Definable {
	
	public static final String STYLE_BOLD = "bold";
	
	public static final String STYLE_ITALIC = "italic";
	
	public static final String STYLE_UNDERLINED = "underlined";
	
	private final Font font;
	
	private final Color color;
	
	private Definition definition;
	
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

	public void setDefinition(Definition definition) {
		this.definition = definition;
	}

	public Definition getDefinition() {
		return this.definition;
	}

	public void finalize() {
		this.definition = null;
	}
}
