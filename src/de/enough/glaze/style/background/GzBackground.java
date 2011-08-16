package de.enough.glaze.style.background;

import de.enough.glaze.style.definition.Definable;
import de.enough.glaze.style.definition.Definition;

public class GzBackground implements Definable {

	private Definition definition;
	
	public void setDefinition(Definition definition) {
		this.definition = definition;
	}

	public Definition getDefinition() {
		return this.definition;
	}

}
