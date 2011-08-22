package de.enough.glaze.style.definition;

import java.util.Vector;

/**
 * A collection of definitions
 * 
 * @author Andre
 * 
 */
public class DefinitionCollection {

	/**
	 * the definitions
	 */
	private final Vector definitions;

	/**
	 * Creates a new {@link DefinitionCollection} instance
	 */
	public DefinitionCollection() {
		this.definitions = new Vector();
	}

	/**
	 * Adds the given definition
	 * 
	 * @param definition
	 *            the definition
	 */
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

	/**
	 * Returns the definition at the given index
	 * 
	 * @param index
	 *            the index
	 * @return the definition
	 */
	public Definition getDefinition(int index) {
		return (Definition) this.definitions.elementAt(index);
	}

	/**
	 * Returns the size of this collection
	 * 
	 * @return the size
	 */
	public int size() {
		return this.definitions.size();
	}

	/**
	 * Returns the definition with the given id
	 * 
	 * @param id
	 *            the id
	 * @return the definition
	 */
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
