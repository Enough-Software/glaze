package de.enough.glaze.style.parser.block;

import java.util.Hashtable;

import de.enough.glaze.style.parser.exception.CssSyntaxError;

public abstract class BlockParser {

	public String[] getNames() {
		return null;
	}

	/**
	 * Parses the given value into an array or single value
	 * 
	 * @param value
	 *            the value
	 * @return the result
	 * @throws CssSyntaxError
	 *             if the syntax is wrong
	 */
	public abstract Object parseBlock(Hashtable attributes) throws CssSyntaxError;

}
