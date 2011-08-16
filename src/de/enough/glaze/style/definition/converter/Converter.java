package de.enough.glaze.style.definition.converter;

import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.parser.exception.CssSyntaxError;

public interface Converter {

	public String[] getIds();

	public Object convert(Definition definition) throws CssSyntaxError;
}
