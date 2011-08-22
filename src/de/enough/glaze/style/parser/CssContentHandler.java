package de.enough.glaze.style.parser;

import de.enough.glaze.style.parser.exception.CssSyntaxError;

public interface CssContentHandler {

	public void onDocumentStart(CssParser parser) throws CssSyntaxError;

	public void onBlockStart(CssParser parser, String blockId,
			String blockClass, String blockExtends) throws CssSyntaxError;

	public void onBlockEnd(CssParser parser) throws CssSyntaxError;

	public void onProperty(CssParser parser, String propertyId,
			String propertyValue) throws CssSyntaxError;

	public void onDocumentEnd(CssParser parser) throws CssSyntaxError;

}
