package de.enough.glaze.style.parser;

import java.util.Hashtable;
import java.util.Stack;

import de.enough.glaze.style.Color;
import de.enough.glaze.style.Style;
import de.enough.glaze.style.StyleSheet;
import de.enough.glaze.style.parser.attribute.ColorAttributeParser;
import de.enough.glaze.style.parser.exception.CssSyntaxError;

public class CssContentHandlerImpl implements CssContentHandler {

	private final static String BLOCK_TYPE_STYLE = "style";

	private final static String BLOCK_TYPE_COLORS = "colors";

	private final static String BLOCK_TYPE_BACKGROUNDS = "backgrounds";

	private final static String BLOCK_TYPE_BORDERS = "borders";

	private final static String BLOCK_TYPE_FONTS = "fonts";

	private final static String BLOCK_TYPE_DECLARATION = "declaration";

	private Stack blockTypeStack;

	private String blockId;

	private Hashtable block;

	private String styleId;

	private Style style;

	private ColorAttributeParser colorParser;

	private final StyleSheet stylesheet;

	public CssContentHandlerImpl(StyleSheet stylesheet) {
		this.blockTypeStack = new Stack();
		this.stylesheet = stylesheet;
		this.colorParser = new ColorAttributeParser();
	}

	private void pushBlockType(String id) {
		this.blockTypeStack.push(id);
	}

	private String popBlockType() {
		if(!this.blockTypeStack.isEmpty()) {
		String block = (String) this.blockTypeStack.pop();
		return block;
		} else {
			return null;
		}
	}

	private String getBlockType() {
		if (!this.blockTypeStack.isEmpty()) {
			return (String) this.blockTypeStack.peek();
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
			String blockExtends) throws CssSyntaxError {
		String blockType = getBlockType();
		if (blockType == null) {
			if (BLOCK_TYPE_COLORS.equals(blockId)) {
				pushBlockType(BLOCK_TYPE_COLORS);
			} else if (BLOCK_TYPE_BACKGROUNDS.equals(blockId)) {
				pushBlockType(BLOCK_TYPE_BACKGROUNDS);
			} else if (BLOCK_TYPE_BORDERS.equals(blockId)) {
				pushBlockType(BLOCK_TYPE_BORDERS);
			} else if (BLOCK_TYPE_FONTS.equals(blockId)) {
				pushBlockType(BLOCK_TYPE_FONTS);
			} else {
				pushBlockType(BLOCK_TYPE_STYLE);
				this.styleId = blockId;
				this.style = new Style();
				System.out.println("add style " + blockId);
			}
		} else if (BLOCK_TYPE_COLORS.equals(blockType)) {
			throw new CssSyntaxError(blockId,
					"block declarations are not allowed in the colors section");
		} else if (BLOCK_TYPE_BACKGROUNDS.equals(blockType)
				|| BLOCK_TYPE_BORDERS.equals(blockType)
				|| BLOCK_TYPE_FONTS.equals(blockType)
				|| BLOCK_TYPE_STYLE.equals(blockType)) {
			pushBlockType(BLOCK_TYPE_DECLARATION);
			this.blockId = blockId;
			this.block = new Hashtable();
			System.out.println("add block " + blockId);
		}
	}

	public void onBlockEnd() throws CssSyntaxError {
		String blockType = popBlockType();
		if(blockType != null) {
			if (blockType.equals(BLOCK_TYPE_STYLE)) {
				// TODO add style to stylesheet
			} else if (blockType.equals(BLOCK_TYPE_DECLARATION)) {
				// TODO parse block and add result to current style
			}
		} else {
			throw new CssSyntaxError("invalid block");
		}
	}

	public void onAttribute(String attributeId, String attributeValue)
			throws CssSyntaxError {
		String blockType = getBlockType();
		if (BLOCK_TYPE_STYLE.equals(blockType)) {
			// TODO add to style
			System.out.println("add to style : " + attributeId + ":"
					+ attributeValue);
		} else if (BLOCK_TYPE_DECLARATION.equals(blockType)) {
			this.block.put(attributeId, attributeValue);
			System.out.println("add to block : " + attributeId + ":"
					+ attributeValue);
			// TODO parse value and add result to current block
		} else if (BLOCK_TYPE_COLORS.equals(blockType)) {
			// TODO add color
			System.out.println("add color : " + attributeId + ":"
					+ attributeValue);
			Color color = (Color) this.colorParser.parseValue(attributeId,
					attributeValue, null, stylesheet);
			this.stylesheet.addColor(attributeId, color);
		} else if (blockType == null
				|| BLOCK_TYPE_BACKGROUNDS.equals(blockType)
				|| BLOCK_TYPE_BORDERS.equals(blockType)
				|| BLOCK_TYPE_FONTS.equals(blockType)) {
			throw new CssSyntaxError(attributeId,
					"attribute declarations are not allowed here");
		}
	}
}
