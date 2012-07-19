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
 
package de.enough.glaze.style.definition;

import java.util.Vector;

/**
 * A collection of definitions used in {@link StyleSheetDefinition}
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
		Definition storedDefinition = getDefinition(id);
		if (storedDefinition != null) {
			storedDefinition.setProperties(definition);
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

	/**
	 * Removes all definitions.
	 */
	public void clear() {
		this.definitions.removeAllElements();
	}
}
