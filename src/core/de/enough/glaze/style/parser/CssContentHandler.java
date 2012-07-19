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
 
package de.enough.glaze.style.parser;

import de.enough.glaze.style.parser.exception.CssSyntaxError;

/**
 * An interface for handling css parsing events like block starts and end,
 * properties and the document start and end
 * 
 * @author Andre
 * 
 */
public interface CssContentHandler {

	/**
	 * Handles the start of a document
	 * 
	 * @param parser
	 *            the parser
	 * @throws CssSyntaxError
	 *             if the syntax is wrong
	 */
	public void onDocumentStart(CssParser parser) throws CssSyntaxError;

	/**
	 * Handles the start of a block
	 * 
	 * @param parser
	 *            the parser
	 * @param blockId
	 *            the block id
	 * @param blockClass
	 *            the block class
	 * @param blockExtends
	 *            the block id to extend
	 * @throws CssSyntaxError
	 *             if the syntax is wrong
	 */
	public void onBlockStart(CssParser parser, String blockId,
			String blockClass, String blockExtends) throws CssSyntaxError;

	/**
	 * Handles the end of a block
	 * 
	 * @param parser
	 *            the parser
	 * @throws CssSyntaxError
	 *             if the syntax is wrong
	 */
	public void onBlockEnd(CssParser parser) throws CssSyntaxError;

	/**
	 * Handles a property
	 * 
	 * @param parser
	 *            the parser
	 * @param propertyId
	 *            the property id
	 * @param propertyValue
	 *            the property value
	 * @throws CssSyntaxError
	 *             if the syntax is wrong
	 */
	public void onProperty(CssParser parser, String propertyId,
			String propertyValue) throws CssSyntaxError;

	/**
	 * Handles the end of a document
	 * 
	 * @param parser
	 *            the parser
	 * @throws CssSyntaxError
	 *             if the syntax is wrong
	 */
	public void onDocumentEnd(CssParser parser) throws CssSyntaxError;

}
