package de.enough.glaze.style.parser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import de.enough.glaze.log.Log;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.utils.ParserUtils;

/**
 * Allows to interpret simple CSS constructs.
 * 
 * @author Robert Virkus
 * 
 */
public class CssParser {

	private static final char ATTRIBUTE_DELIMITER = ':';

	private static final char ATTRIBUTE_END = ';';

	private static final char BLOCK_START = '{';

	private static final char BLOCK_END = '}';

	private static final char BLOCK_CLASS_DELIMITER = ':';

	private static final String BLOCK_EXTENDS_DELIMITER = "extends";

	private static final char LINEBREAK = '\n';

	private static final int BUFFER_SIZE = 1024;

	private final Reader reader;

	private final char[] readBuffer;

	private int bufferStartIndex;

	private int bufferEndIndex;

	private boolean isInComment;

	private boolean hasNextToken;

	private CssContentHandler handler;

	private int blockDepth;

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

	public void setContentHandler(CssContentHandler handler) {
		this.handler = handler;
	}

	public CssContentHandler getContentHandler() {
		return this.handler;
	}

	public void parse() throws IOException, CssSyntaxError {
		StringBuffer parseBuffer = new StringBuffer();
		try {
			while (hasNextToken()) {
				parseNextToken(parseBuffer);
			}
		} catch (CssSyntaxError e) {
			Log.s(e);
			throw e;
		}
	}

	/**
	 * Checks if there are more tokens available
	 * 
	 * @return true when there are more tokens
	 */
	private boolean hasNextToken() {
		return this.hasNextToken || this.bufferStartIndex < this.bufferEndIndex;
	}

	private void startBlock() {
		this.blockDepth++;
	}

	private void endBlock() {
		this.blockDepth--;
	}

	private int getBlockDepth() {
		return this.blockDepth;
	}

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
			if (read == -1) {
				if (getBlockDepth() > 0) {
					Log.s(new CssSyntaxError("block not closed",
							this.lineNumber));
				}
				return;
			}
			this.bufferStartIndex = 0;
			this.bufferEndIndex = read;
			this.hasNextToken = (read == BUFFER_SIZE);
		}
		boolean checkNextCharForEndComment = false;
		boolean checkNextCharForStartComment = false;
		for (int index = this.bufferStartIndex; index < this.bufferEndIndex; index++) {
			char c = this.readBuffer[index];
			if (this.isInComment && c == '*') {
				checkNextCharForEndComment = true;
			} else if (checkNextCharForEndComment && c == '/') {
				this.isInComment = false;
			} else if (this.isInComment) {
				// ignore comment char
				checkNextCharForEndComment = false;
			} else if (c == '/') {
				// this _could_ be the start of a new comment:
				checkNextCharForStartComment = true;
				buffer.append(c);
			} else if (checkNextCharForStartComment && c == '*') {
				checkNextCharForStartComment = false;
				this.isInComment = true;
				buffer.deleteCharAt(buffer.length() - 1);
			} else {
				checkNextCharForStartComment = false;
				if (c == ATTRIBUTE_END || c == BLOCK_START || c == BLOCK_END) {
					String value = buffer.toString().trim();
					if (c == ATTRIBUTE_END) {
						handleAttribute(value);
					} else if (c == BLOCK_START) {
						startBlock();
						handleBlockStart(value);
					} else if (c == BLOCK_END) {
						if (value.length() > 0) {
							handleAttribute(value);
						}
						handleBlockEnd();
						endBlock();
					}

					this.bufferStartIndex = index + 1;
					buffer.setLength(0);
					return;
				} else if (c == LINEBREAK) {
					this.lineNumber++;
				} else {
					buffer.append(c);
				}
			}
		}
		this.bufferStartIndex = this.bufferEndIndex;
		parseNextToken(buffer);
		return;
	}

	public void handleAttribute(String value) throws CssSyntaxError {
		value = ParserUtils.normalize(value);
		if (value.length() > 0) {
			if (ParserUtils.hasDelimiter(value, ATTRIBUTE_DELIMITER)) {
				String[] attributeArray = ParserUtils.toArray(value,
						ATTRIBUTE_DELIMITER);
				if (attributeArray.length == 2) {
					String attributeId = ParserUtils
							.normalize(attributeArray[0]);
					ParserUtils.validate(this, attributeId);
					String attributeValue = ParserUtils
							.normalize(attributeArray[1]);
					if (this.handler != null) {
						this.handler.onProperty(this, attributeId,
								attributeValue);
					}
					return;
				}
			}

			throw new CssSyntaxError("invalid attribute declaration", value,
					this.lineNumber);
		}
	}

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

	public void handleBlockEnd() throws CssSyntaxError {
		if (this.handler != null) {
			this.handler.onBlockEnd(this);
		}
	}
}