package de.enough.glaze.style.parser;

import de.enough.glaze.style.parser.exception.CssSyntaxError;

public interface CssContentHandler {
	public void onBlockStart(CssParser parser, String blockId, String blockClass, String blockExtends) throws CssSyntaxError;
	
	public void onBlockEnd(CssParser parser) throws CssSyntaxError;
	
	public void onProperty(CssParser parser, String attributeId, String attributeValue) throws CssSyntaxError;
	
}
