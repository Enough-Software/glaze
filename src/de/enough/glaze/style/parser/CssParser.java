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

	private int invalidBlockDepth;

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
		this.invalidBlockDepth = Integer.MIN_VALUE;
	}

	public void setContentHandler(CssContentHandler handler) {
		this.handler = handler;
	}

	public CssContentHandler getContentHandler() {
		return this.handler;
	}

	public void parse() throws IOException {
		StringBuffer parseBuffer = new StringBuffer();
		while (hasNextToken()) {
			parseNextToken(parseBuffer);
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

	private boolean isInInvalidBlock() {
		return this.invalidBlockDepth != Integer.MIN_VALUE;
	}

	private void setInvalidBlock(boolean invalid) {
		if (invalid) {
			this.invalidBlockDepth = this.blockDepth;
		} else {
			this.invalidBlockDepth = Integer.MIN_VALUE;
		}
	}

	private void enterBlock() {
		this.blockDepth++;
	}

	private void leaveBlock() {
		this.blockDepth--;
	}

	private int getBlockDepth() {
		return this.blockDepth;
	}

	private int getInvalidBlockDepth() {
		return this.invalidBlockDepth;
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
	private void parseNextToken(StringBuffer buffer) throws IOException {
		if (this.bufferStartIndex >= this.bufferEndIndex) {
			int read = this.reader.read(this.readBuffer);
			if (read == -1) {
				if(getBlockDepth() > 0) {
					Log.s(new CssSyntaxError(this.lineNumber, "block not closed"));
				}
				return;
			}
			this.bufferStartIndex = 0;
			this.bufferEndIndex = read;
			this.hasNextToken = (read == BUFFER_SIZE);
		}
		boolean comment = this.isInComment;
		boolean checkNextCharForEndComment = false;
		boolean checkNextCharForStartComment = false;
		for (int index = this.bufferStartIndex; index < this.bufferEndIndex; index++) {
			char c = this.readBuffer[index];
			if (comment && c == '*') {
				checkNextCharForEndComment = true;
			} else if (checkNextCharForEndComment && c == '/') {
				comment = false;
				this.isInComment = false;
			} else if (comment) {
				// ignore comment char
				checkNextCharForEndComment = false;
			} else if (c == '/') {
				// this _could_ be the start of a new comment:
				checkNextCharForStartComment = true;
				buffer.append(c);
			} else if (checkNextCharForStartComment && c == '*') {
				checkNextCharForStartComment = false;
				comment = true;
				buffer.deleteCharAt(buffer.length() - 1);
			} else {
				checkNextCharForStartComment = false;
				if (c == ATTRIBUTE_END || c == BLOCK_START || c == BLOCK_END) {
					if (c == BLOCK_START) {
						enterBlock();
					}

					if (!isInInvalidBlock()) {
						String value = buffer.toString().trim();
						if (c == ATTRIBUTE_END) {
							try {
								handleAttribute(value);
							} catch (CssSyntaxError e) {
								Log.s(new CssSyntaxError(this.lineNumber, e));
							}
						} else if (c == BLOCK_START) {
							try {
								handleBlockStart(value);
							} catch (CssSyntaxError e) {
								Log.s(new CssSyntaxError(this.lineNumber, e));
								setInvalidBlock(true);
							}
						} else if (c == BLOCK_END) {
							if (value.length() > 0) {
								try {
									handleAttribute(value);
								} catch (CssSyntaxError e) {
									Log.s(new CssSyntaxError(this.lineNumber, e));
								}
							}
							try {
								handleBlockEnd();
							} catch (CssSyntaxError e) {
								Log.s(new CssSyntaxError(this.lineNumber, e));
							}
						}
					} else {
						if (c == BLOCK_END
								&& getBlockDepth() == getInvalidBlockDepth()) {
							setInvalidBlock(false);
						}
					}

					if (c == BLOCK_END) {
						leaveBlock();
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
					ParserUtils.validate(attributeId);
					String attributeValue = ParserUtils
							.normalize(attributeArray[1]);
					if (this.handler != null) {
						this.handler.onAttribute(attributeId, attributeValue);
					}
					return;
				}
			}

			throw new CssSyntaxError(value, "invalid attribute declaration");
		}
	}

	public void handleBlockStart(String value) throws CssSyntaxError {
		if (ParserUtils.hasDelimiter(value, BLOCK_EXTENDS_DELIMITER)) {
			String[] attributeArray = ParserUtils.toArray(value,
					BLOCK_EXTENDS_DELIMITER);

			if (attributeArray.length == 2) {
				String blockId = ParserUtils.normalize(attributeArray[0]);
				ParserUtils.validate(blockId);
				String blockExtends = ParserUtils.normalize(attributeArray[1]);
				handleBlockStart(value, blockId, blockExtends);
				return;
			}
		} else {
			handleBlockStart(value, value, null);
			return;
		}

		throw new CssSyntaxError(value, "invalid block declaration");
	}

	public void handleBlockStart(String value, String blockIdValue,
			String blockExtends) throws CssSyntaxError {
		if (ParserUtils.hasDelimiter(blockIdValue, BLOCK_CLASS_DELIMITER)) {
			String[] attributeArray = ParserUtils.toArray(blockIdValue,
					BLOCK_CLASS_DELIMITER);

			if (attributeArray.length == 2) {
				String blockId = ParserUtils.normalize(attributeArray[0]);
				ParserUtils.validate(blockId);
				String blockClass = ParserUtils.normalize(attributeArray[1]);
				ParserUtils.validate(blockClass);

				if (blockExtends != null) {
					throw new CssSyntaxError(value,
							"pseudoclasses are not allowed to be extended");
				}

				if (this.handler != null) {
					this.handler
							.onBlockStart(blockId, blockClass, blockExtends);
				}
				return;
			}
		} else {
			String blockId = ParserUtils.normalize(blockIdValue);
			ParserUtils.validate(blockId);
			if (this.handler != null) {
				this.handler.onBlockStart(blockId, null, blockExtends);
			}
			return;
		}

		throw new CssSyntaxError(value, "invalid block declaration");
	}

	public void handleBlockEnd() throws CssSyntaxError {
		if (this.handler != null) {
			this.handler.onBlockEnd();
		}
	}
}
