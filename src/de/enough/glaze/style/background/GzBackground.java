package de.enough.glaze.style.background;

import net.rim.device.api.ui.decor.Background;
import de.enough.glaze.style.definition.Definable;
import de.enough.glaze.style.definition.Definition;

public abstract class GzBackground extends Background implements Definable {

	private Definition definition;
	
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
