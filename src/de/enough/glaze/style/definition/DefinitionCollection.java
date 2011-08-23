package de.enough.glaze.style.definition;

import java.util.Vector;

public class DefinitionCollection {

	private final Vector definitions;

	public DefinitionCollection() {
		this.definitions = new Vector();
	}

	public void addDefinition(Definition definition) {
		String id = definition.getId();
		Definition originalDefinition = getDefinition(id);
		if (originalDefinition != null) {
			int index = this.definitions.indexOf(originalDefinition);
			this.definitions.setElementAt(definition, index);
		} else {
			this.definitions.addElement(definition);
		}
	}

	public Definition getDefinition(int index) {
		return (Definition) this.definitions.elementAt(index);
	}

	public int size() {
		return this.definitions.size();
	}

	public Definition getDefinition(String id) {
		for (int index = 0; index < this.definitions.size(); index++) {
			Definition definition = getDefinition(index);
			if (id.equals(definition.getId())) {
				return definition;
			}
		}

		return null;
	}
}
