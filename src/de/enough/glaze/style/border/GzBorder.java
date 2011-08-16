package de.enough.glaze.style.border;

import de.enough.glaze.style.definition.Definable;
import de.enough.glaze.style.definition.Definition;

public class GzBorder implements Definable {

	private Definition definition;
	
	public void setDefinition(Definition definition) {
		this.definition = definition;
	}

	public Definition getDefinition() {
		return this.definition;
	}

}
