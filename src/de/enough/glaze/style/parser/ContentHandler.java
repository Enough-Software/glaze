package de.enough.glaze.style.parser;

import de.enough.glaze.style.parser.exception.CssSyntaxException;

public interface ContentHandler {
	public void onBlockStart(String blockId, String blockClass, String blockExtends) throws CssSyntaxException;
	
	public void onBlockEnd() throws CssSyntaxException;
	
	public void onAttribute(String attributeId, String attributeValue) throws CssSyntaxException;
	
}
