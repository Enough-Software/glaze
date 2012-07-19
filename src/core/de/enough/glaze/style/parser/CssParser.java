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

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import de.enough.glaze.log.Log;
import de.enough.glaze.style.StyleSheet;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.utils.ParserUtils;

/**
 * The CSS parser used in {@link StyleSheet} to parse CSS stylesheet by using a
 * content handler to handle block starts and ends, properties and the document
 * start and end.
 * 
 * @author Andre
 * 
 */
public class CssParser {

	/**
	 * the property delimiter character
	 */
	private static final char PROPERTY_DELIMITER = ':';

	/**
	 * the property end character
	 */
	private static final char PROPERTY_END = ';';

	/**
	 * the block start character
	 */
	private static final char BLOCK_START = '{';

	/**
	 * the block end character
	 */
	private static final char BLOCK_END = '}';

	/**
	 * the block class delimiter character
	 */
	private static final char BLOCK_CLASS_DELIMITER = ':';

	/**
	 * the block extension delimiter string
	 */
	private static final String BLOCK_EXTENDS_DELIMITER = "extends";

	/**
	 * the linebreak character
	 */
	private static final char LINEBREAK = '\n';

	/**
	 * the buffer size
	 */
	private static final int BUFFER_SIZE = 1024;

	/**
	 * the reader
	 */
	private final Reader reader;

	/**
	 * the reader buffer
	 */
	private final char[] readBuffer;

	/**
	 * the buffer start index
	 */
	private int bufferStartIndex;

	/**
	 * the buffer end index
	 */
	private int bufferEndIndex;

	/**
	 * flag indicating that the parser is inside a comment
	 */
	private boolean isInComment;
	
	private boolean checkNextCharForStartComment = false;
	
	private boolean checkNextCharForEndComment = false;

	/**
	 * flaf indicating that the parser has more tokens
	 */
	private boolean hasNextToken;

	/**
	 * the css content handler
	 */
	private CssContentHandler handler;

	/**
	 * the block depth
	 */
	private int blockDepth;

	/**
	 * the line number
	 */
	private int lineNumber;

	/**
	 * Creates a new CSS Reader
	 * 
	 * @param cssCode
	 *            the CSS code
	 */
	public CssParser(InputStreamReader reader) {
		this.reader = reader;
		this.readBuffer = new char[BUFFER_SIZE];
		this.bufferStartIndex = BUFFER_SIZE;
		this.hasNextToken = true;
		this.lineNumber = 1;
	}

	/**
	 * Sets the content handler to use
	 * 
	 * @param handler
	 *            the content handler
	 */
	public void setContentHandler(CssContentHandler handler) {
		this.handler = handler;
	}

	/**
	 * Returns the content handler
	 * 
	 * @return the content handler
	 */
	public CssContentHandler getContentHandler() {
		return this.handler;
	}

	/**
	 * Parses the document
	 * 
	 * @throws IOException
	 *             if a reader error occurs
	 * @throws CssSyntaxError
	 *             if the syntax is wrong
	 */
	public void parse() throws IOException, CssSyntaxError {
		StringBuffer parseBuffer = new StringBuffer();
		try {
			this.handler.onDocumentStart(this);
			while (hasNextToken()) {
				parseNextToken(parseBuffer);
			}
			this.handler.onDocumentEnd(this);
		} catch (CssSyntaxError e) {
			Log.syntaxError(e);
			throw e;
		}
	}

	/**
	 * Checks if there are more tokens available
	 * 
	 * @return true when there are more tokens otherwise false
	 */
	private boolean hasNextToken() {
		return this.hasNextToken || this.bufferStartIndex < this.bufferEndIndex;
	}

	/**
	 * Increases the block depth on a block start
	 */
	private void startBlock() {
		this.blockDepth++;
	}

	/**
	 * Decreases the block depth on a block end
	 */
	private void endBlock() {
		this.blockDepth--;
	}

	/**
	 * Returns the block depth
	 * 
	 * @return the block depth
	 */
	private int getBlockDepth() {
		return this.blockDepth;
	}

	/**
	 * Returns the current line number
	 * 
	 * @return the current line number
	 */
	public int getLineNumber() {
		return this.lineNumber;
	}

	/**
	 * Retrieves the next token
	 * 
	 * @param buffer
	 *            the buffer into which tokens are written
	 * @return the trimmed next token, an empty string when a block is closed
	 * @throws IOException
	 *             when reading fails
	 */
	private void parseNextToken(StringBuffer buffer) throws IOException,
			CssSyntaxError {
		if (this.bufferStartIndex >= this.bufferEndIndex) {
			int read = this.reader.read(this.readBuffer);
			this.bufferStartIndex = 0;
			this.bufferEndIndex = read;
			this.hasNextToken = (read == BUFFER_SIZE);
			if (read == -1) {
				if (getBlockDepth() > 0) {
					Log.syntaxError(new CssSyntaxError("block not closed",
							this.lineNumber));
				}
				return;
			}
		}
		for (int index = this.bufferStartIndex; index < this.bufferEndIndex; index++ ) {
			char c = this.readBuffer[index];
			if (this.isInComment && c == '*') {
				this.checkNextCharForEndComment = true;
			} else if (checkNextCharForEndComment && c == '/') {
				this.isInComment = false;
				this.checkNextCharForEndComment = false;
			} else if (this.isInComment) {
				// ignore comment char
				this.checkNextCharForEndComment = false;
			} else if (c == '/'){
				// this _could_ be the start of a new comment:
				this.checkNextCharForStartComment = true;
				buffer.append(c);
			} else if (checkNextCharForStartComment && c == '*') {
				this.checkNextCharForStartComment = false;
				this.isInComment = true;
				buffer.deleteCharAt(buffer.length() - 1);
			} else {
				this.checkNextCharForStartComment = false;
				if (c == PROPERTY_END || c == BLOCK_START || c == BLOCK_END) {
					String value = buffer.toString().trim();
					if (c == PROPERTY_END) {
						handleProperty(value);
					} else if (c == BLOCK_START) {
						startBlock();
						handleBlockStart(value);
					} else if (c == BLOCK_END) {
						if (value.length() > 0) {
							handleProperty(value);
						}
						handleBlockEnd();
						endBlock();
					}

					this.bufferStartIndex = index + 1;
					buffer.setLength(0);
					return;
				} else if (c != LINEBREAK) {
					buffer.append(c);
				}
			}

			if (c == LINEBREAK) {
				this.lineNumber++;
			}
		}
		this.bufferStartIndex = this.bufferEndIndex;
		parseNextToken(buffer);
		return;
	}

	/**
	 * Handles the given value as a property
	 * 
	 * @param value
	 *            the value
	 * @throws CssSyntaxError
	 *             if the syntax is wrong
	 */
	public void handleProperty(String value) throws CssSyntaxError {
		String[] propertyArray = ParserUtils.toPropertyArray(value,
				PROPERTY_DELIMITER);
		if (propertyArray != null) {
			String propertyId = ParserUtils.normalize(propertyArray[0]);
			ParserUtils.validate(this, propertyId);
			String propertyValue = ParserUtils.normalize(propertyArray[1]);
			if (this.handler != null) {
				this.handler.onProperty(this, propertyId, propertyValue);
			}
		} else {
			throw new CssSyntaxError("invalid property", value, this.lineNumber);
		}
	}

	/**
	 * Handles the given value as a block start
	 * 
	 * @param value
	 *            the value
	 * @throws CssSyntaxError
	 *             if the syntax is wrong
	 */
	public void handleBlockStart(String value) throws CssSyntaxError {
		if (ParserUtils.hasDelimiter(value, BLOCK_EXTENDS_DELIMITER)) {
			String[] attributeArray = ParserUtils.toArray(value,
					BLOCK_EXTENDS_DELIMITER);

			if (attributeArray.length == 2) {
				String blockId = ParserUtils.normalize(attributeArray[0]);
				String blockExtends = ParserUtils.normalize(attributeArray[1]);
				handleBlockStart(value, blockId, blockExtends);
				return;
			}
		} else {
			value = ParserUtils.normalize(value);
			handleBlockStart(value, value, null);
			return;
		}

		throw new CssSyntaxError("invalid block declaration", value,
				this.lineNumber);
	}

	/**
	 * Handles the given value as a block start
	 * 
	 * @param value
	 *            the value
	 * @throws CssSyntaxError
	 *             if the syntax is wrong
	 */
	public void handleBlockStart(String value, String blockIdValue,
			String blockExtends) throws CssSyntaxError {
		if (ParserUtils.hasDelimiter(blockIdValue, BLOCK_CLASS_DELIMITER)) {
			if (blockExtends != null) {
				throw new CssSyntaxError(
						"classes are not allowed to be extended", value,
						this.lineNumber);
			}

			String[] attributeArray = ParserUtils.toArray(blockIdValue,
					BLOCK_CLASS_DELIMITER);

			if (attributeArray.length == 2) {
				String blockId = ParserUtils.normalize(attributeArray[0]);
				ParserUtils.validate(this, blockId);
				String blockClass = ParserUtils.normalize(attributeArray[1]);
				ParserUtils.validate(this, blockClass);

				if (this.handler != null) {
					this.handler.onBlockStart(this, blockId, blockClass,
							blockExtends);
				}
				return;
			}
		} else {
			String blockId = ParserUtils.normalize(blockIdValue);
			ParserUtils.validate(this, blockId);
			if (this.handler != null) {
				this.handler.onBlockStart(this, blockId, null, blockExtends);
			}
			return;
		}

		throw new CssSyntaxError("invalid block declaration", value,
				this.lineNumber);
	}

	/**
	 * Handles a block end
	 * 
	 * @throws CssSyntaxError
	 *             if the syntax is wrong
	 */
	public void handleBlockEnd() throws CssSyntaxError {
		if (this.handler != null) {
			this.handler.onBlockEnd(this);
		}
	}
}
