package de.enough.glaze.style.border;

import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.decor.Border;
import de.enough.glaze.style.definition.Definable;
import de.enough.glaze.style.definition.Definition;

public abstract class GzBorder extends Border implements Definable {
		
	public static final String STYLE_SOLID = "solid";
	
	public static final String STYLE_DOTTED = "dotted";
	
	public static final String STYLE_FILLED = "filled";
	
	public static final String STYLE_DASHED = "dashed";

	private Definition definition;
	
	public GzBorder(XYEdges borderWidths, int style) {
		super(borderWidths, style);
	}
	
	public void setDefinition(Definition definition) {
		this.definition = definition;
	}

	public Definition getDefinition() {
		return this.definition;
	}

}
