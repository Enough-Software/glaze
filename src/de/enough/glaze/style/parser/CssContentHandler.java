package de.enough.glaze.style.parser;

import de.enough.glaze.style.parser.exception.CssSyntaxError;

public interface CssContentHandler {
	public void onBlockStart(String blockId, String blockClass, String blockExtends) throws CssSyntaxError;
	
	public void onBlockEnd() throws CssSyntaxError;
	
	public void onAttribute(String attributeId, String attributeValue) throws CssSyntaxError;
	
}
