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
 
package de.enough.glaze.style.property.font;

import net.rim.device.api.ui.Font;
import de.enough.glaze.style.Color;

public class GzFont {

	public static final String SIZE_SMALL = "small";

	public static final String SIZE_MEDIUM = "medium";

	public static final String SIZE_LARGE = "large";

	public static final String STYLE_BOLD = "bold";

	public static final String STYLE_ITALIC = "italic";

	public static final String STYLE_UNDERLINED = "underlined";

	private final String name;

	private final Font font;

	private final Color color;

	public GzFont(String name, Font font, Color color) {
		this.name = name;
		this.font = font;
		this.color = color;
	}

	public String getName() {
		return this.name;
	}

	public Font getFont() {
		return this.font;
	}

	public Color getColor() {
		return this.color;
	}
}
