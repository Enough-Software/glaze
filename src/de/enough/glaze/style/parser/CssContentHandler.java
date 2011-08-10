package de.enough.glaze.style.parser;

import java.util.Stack;

import de.enough.glaze.style.parser.exception.CssSyntaxException;

public class CssContentHandler implements ContentHandler {

	private final static String BLOCK_STYLE = "style";

	private final static String BLOCK_COLORS = "colors";

	private final static String BLOCK_BACKGROUNDS = "backgrounds";

	private final static String BLOCK_BORDERS = "borders";

	private final static String BLOCK_FONTS = "fonts";

	private final static String BLOCK_DECLARATION = "declaration";

	private Stack blockStack;

	public CssContentHandler() {
		this.blockStack = new Stack();
	}

	private void pushBlock(String id) {
		this.blockStack.push(id);
	}

	private String popBlock() {
		String block = (String) this.blockStack.pop();
		return block;
	}

	private String getActiveBlock() {
		if (!this.blockStack.isEmpty()) {
			return (String) this.blockStack.peek();
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.style.parser.ContentHandler#onBlockStart(java.lang.String
	 * , java.lang.String, java.lang.String)
	 */
	public void onBlockStart(String blockId, String blockClass,
			String blockExtends) throws CssSyntaxException {
		String activeBlock = getActiveBlock();
		if (activeBlock == null) {
			if (BLOCK_COLORS.equals(blockId)) {
				pushBlock(BLOCK_COLORS);
			} else if (BLOCK_BACKGROUNDS.equals(blockId)) {
				pushBlock(BLOCK_BACKGROUNDS);
			} else if (BLOCK_BORDERS.equals(blockId)) {
				pushBlock(BLOCK_BORDERS);
			} else if (BLOCK_FONTS.equals(blockId)) {
				pushBlock(BLOCK_FONTS);
			} else {
				pushBlock(BLOCK_STYLE);
				System.out.println("STYLE BLOCK START");
				System.out.println("name : " + blockId);
				System.out.println("class : " + blockClass);
				System.out.println("extends : " + blockExtends);
			}
		} else if (BLOCK_COLORS.equals(activeBlock)) {
			throw new CssSyntaxException(blockId,
					"block declarations are not allowed in the colors section");
		} else if (BLOCK_BACKGROUNDS.equals(activeBlock)) {
			pushBlock(BLOCK_DECLARATION);
			System.out.println("BACKGROUND BLOCK START");
			System.out.println("name : " + blockId);
		} else if (BLOCK_BORDERS.equals(activeBlock)) {
			pushBlock(BLOCK_DECLARATION);
			System.out.println("BORDER BLOCK START");
			System.out.println("name : " + blockId);
		} else if (BLOCK_FONTS.equals(activeBlock)) {
			pushBlock(BLOCK_DECLARATION);
			System.out.println("FONT BLOCK START");
			System.out.println("name : " + blockId);
		} else if (BLOCK_STYLE.equals(activeBlock)) {
			pushBlock(BLOCK_DECLARATION);
			System.out.println("DECLARATION BLOCK START");
			System.out.println("name : " + blockId);
		}
	}

	public void onBlockEnd() throws CssSyntaxException {
		System.out.println("BLOCK END");
		popBlock();
	}

	public void onAttribute(String attributeId, String attributeValue)
			throws CssSyntaxException {
		System.out.println("ATTRIBUTE");
		System.out.println("name : " + attributeId);
		System.out.println("value  : " + attributeValue);
	}

}
