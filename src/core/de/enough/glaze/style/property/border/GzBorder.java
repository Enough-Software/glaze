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
 
package de.enough.glaze.style.property.border;

import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.decor.Border;

public abstract class GzBorder extends Border {

	public static final String STYLE_SOLID = "solid";

	public static final String STYLE_DOTTED = "dotted";

	public static final String STYLE_FILLED = "filled";

	public static final String STYLE_DASHED = "dashed";

	public GzBorder(XYEdges borderWidths, int style) {
		super(borderWidths, style);
	}

	/**
	 * Releases this border
	 */
	public void release() {
		// do nothing
	}
}
