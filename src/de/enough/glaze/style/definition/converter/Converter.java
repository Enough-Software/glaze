package de.enough.glaze.style.definition.converter;

import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.parser.exception.CssSyntaxError;

public interface Converter {

	/**
	 * Returns the ids handled by this converter
	 * 
	 * @return the ids
	 */
	public String[] getIds();

	/**
	 * Convert the given definition to a stylesheet object
	 * 
	 * @param definition
	 *            the definition
	 * @return the created stylesheet object
	 * @throws CssSyntaxError
	 */
	public Object convert(Definition definition) throws CssSyntaxError;
}
