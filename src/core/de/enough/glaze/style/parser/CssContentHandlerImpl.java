package de.enough.glaze.style.parser;

import java.util.Stack;

import de.enough.glaze.log.Log;
import de.enough.glaze.style.Color;
import de.enough.glaze.style.Style;
import de.enough.glaze.style.StyleSheet;
import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.definition.DefinitionCollection;
import de.enough.glaze.style.definition.StyleSheetDefinition;
import de.enough.glaze.style.definition.converter.BackgroundConverter;
import de.enough.glaze.style.definition.converter.BorderConverter;
import de.enough.glaze.style.definition.converter.FontConverter;
import de.enough.glaze.style.definition.converter.StyleConverter;
import de.enough.glaze.style.definition.converter.utils.ConverterUtils;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.ColorPropertyParser;
import de.enough.glaze.style.parser.property.Property;
import de.enough.glaze.style.property.background.GzBackground;
import de.enough.glaze.style.property.border.GzBorder;
import de.enough.glaze.style.property.font.GzFont;

/**
 * The {@link CssContentHandler} implementation to be used with
 * {@link CssParser}
 * 
 * @author Andre
 * 
 */
public class CssContentHandlerImpl implements CssContentHandler {

	/**
	 * the block id for the colors section
	 */
	private final static String BLOCK_TYPE_SECTION_COLORS = "colors";

	/**
	 * the block id for the backgrounds section
	 */
	private final static String BLOCK_TYPE_SECTION_BACKGROUNDS = "backgrounds";

	/**
	 * the block type for the border section
	 */
	private final static String BLOCK_TYPE_SECTION_BORDERS = "borders";

	/**
	 * the block type for the fonts section
	 */
	private final static String BLOCK_TYPE_SECTION_FONTS = "fonts";

	/**
	 * the block type for a style
	 */
	private final static String BLOCK_TYPE_STYLE = "style";

	/**
	 * the block type for the local block inside a style
	 */
	private final static String BLOCK_TYPE_LOCAL = "local";

	/**
	 * the key for a background block
	 */
	private final static String BLOCK_ID_BACKGROUND = "background";

	/**
	 * the key for a border block
	 */
	private final static String BLOCK_ID_BORDER = "border";

	/**
	 * the key for a font block
	 */
	private final static String BLOCK_ID_FONT = "font";

	/**
	 * the block id stack
	 */
	private Stack blockStack;

	/**
	 * the current block id
	 */
	private String blockId;

	/**
	 * the current style definition
	 */
	private Definition styleDefinition;

	/**
	 * the current block definition
	 */
	private Definition blockDefinition;

	/**
	 * the stylesheet
	 */
	private final StyleSheet stylesheet;

	/**
	 * the stylesheet definition
	 */
	private final StyleSheetDefinition styleSheetDefinition;

	/**
	 * Creates a new {@link CssContentHandlerImpl} instance
	 * 
	 * @param stylesheet
	 *            the stylesheet
	 */
	public CssContentHandlerImpl(StyleSheet stylesheet) {
		this.blockStack = new Stack();
		this.stylesheet = stylesheet;
		this.styleSheetDefinition = stylesheet.getDefinition();
	}

	/**
	 * Pushes the given block type onto the block stack
	 * 
	 * @param type
	 *            the block type
	 */
	private void pushBlockType(String type) {
		this.blockStack.push(type);
	}

	/**
	 * Removes the current block type from the block stack and returns it
	 * 
	 * @return the current block type
	 */
	private String popBlockType() {
		if (!this.blockStack.isEmpty()) {
			String block = (String) this.blockStack.pop();
			return block;
		} else {
			return null;
		}
	}

	/**
	 * Returns the current block type
	 * 
	 * @return the current block type
	 */
	private String getBlockType() {
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
	 * de.enough.glaze.style.parser.CssContentHandler#onDocumentStart(de.enough
	 * .glaze.style.parser.CssParser)
	 */
	public void onDocumentStart(CssParser parser) {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.style.parser.ContentHandler#onBlockStart(java.lang.String
	 * , java.lang.String, java.lang.String)
	 */
	public void onBlockStart(CssParser parser, String blockId,
			String blockClass, String blockExtends) throws CssSyntaxError {
		int lineNumber = parser.getLineNumber();
		String blockType = getBlockType();
		// if the block starts in the root level ...
		if (blockType == null) {
			// if the block is the colors section ...
			if (BLOCK_TYPE_SECTION_COLORS.equals(blockId)) {
				// set the current block type to the colors section
				pushBlockType(BLOCK_TYPE_SECTION_COLORS);
				Log.debug("starting block : colors");
				// if the block is the backgrounds section ...
			} else if (BLOCK_TYPE_SECTION_BACKGROUNDS.equals(blockId)) {
				// set the current block type to the backgrounds section
				pushBlockType(BLOCK_TYPE_SECTION_BACKGROUNDS);
				Log.debug("starting block : backgrounds");
				// if the block is the borders section ...
			} else if (BLOCK_TYPE_SECTION_BORDERS.equals(blockId)) {
				// set the current block type to the borders section
				pushBlockType(BLOCK_TYPE_SECTION_BORDERS);
				Log.debug("starting block : borders");
				// if the block is the fonts section ...
			} else if (BLOCK_TYPE_SECTION_FONTS.equals(blockId)) {
				// set the current block type to the fonts section
				pushBlockType(BLOCK_TYPE_SECTION_FONTS);
				Log.debug("starting block : fonts");
				// otherwise ...
			} else {
				// set the current block type to style
				pushBlockType(BLOCK_TYPE_STYLE);

				Log.debug("starting style : " + blockId);

				// get the style definitions
				DefinitionCollection styleDefinitions = this.styleSheetDefinition
						.getStyleDefinitions();
				Definition parentDefinition;
				// if a class is given ...
				if (blockClass != null) {
					// if the block class is valid ...
					if (Style.isValidClass(blockClass)) {
						// get the parent definition for the style id
						parentDefinition = styleDefinitions
								.getDefinition(blockId);
						if (parentDefinition != null) {
							// create a new style definition with the id and
							// class
							this.styleDefinition = new Definition(blockId,
									blockClass);
							// set the parent of the style definition
							this.styleDefinition.setParentId(parentDefinition.getId());
						} else {
							throw new CssSyntaxError("unable to resolve style",
									blockId, lineNumber);
						}
					} else {
						throw new CssSyntaxError("invalid style class",
								blockClass, lineNumber);
					}
					// if the style extends another style ...
				} else if (blockExtends != null) {
					// get the parent definition for the style to extend
					parentDefinition = styleDefinitions
							.getDefinition(blockExtends);
					if (parentDefinition != null) {
						// create a new style definition with the id
						this.styleDefinition = new Definition(blockId);
						// set the parent of the style definition
						this.styleDefinition.setParentId(parentDefinition.getId());
					} else {
						throw new CssSyntaxError("unable to resolve style",
								blockExtends, lineNumber);
					}
					// otherwise ...
				} else {
					// create a new style definition with the id
					this.styleDefinition = new Definition(blockId);
				}
			}
		} else if (BLOCK_TYPE_SECTION_BACKGROUNDS.equals(blockType)
				|| BLOCK_TYPE_SECTION_BORDERS.equals(blockType)
				|| BLOCK_TYPE_SECTION_FONTS.equals(blockType)) {
			Definition parentDefinition = null;
			// if the current block is the backgrounds section ...
			if (BLOCK_TYPE_SECTION_BACKGROUNDS.equals(blockType)) {
				Log.debug("starting background : " + blockId);
				pushBlockType(BLOCK_ID_BACKGROUND);
				// if the background extends another background ...
				if (blockExtends != null) {
					DefinitionCollection backgroundDefinitions = this.styleSheetDefinition
							.getBackgroundDefinitions();
					// get the definition for the background to extend
					parentDefinition = backgroundDefinitions
							.getDefinition(blockExtends);
					if (parentDefinition == null) {
						throw new CssSyntaxError(
								"could not resolve background", blockId,
								lineNumber);
					}
				}
				// if the current block is the borders section ...
			} else if (BLOCK_TYPE_SECTION_BORDERS.equals(blockType)) {
				Log.debug("starting border : " + blockId);
				pushBlockType(BLOCK_ID_BORDER);
				// if the border extends another border ...
				if (blockExtends != null) {
					DefinitionCollection borderDefinitions = this.styleSheetDefinition
							.getBorderDefinitions();
					// get the definition for the border to extend
					parentDefinition = borderDefinitions
							.getDefinition(blockExtends);
					if (parentDefinition == null) {
						throw new CssSyntaxError("could not resolve border",
								blockId, lineNumber);
					}
				}
				// if the current block is the fonts section ...
			} else if (BLOCK_TYPE_SECTION_FONTS.equals(blockType)) {
				Log.debug("starting font : " + blockId);
				pushBlockType(BLOCK_ID_FONT);
				// if the font extends another font ...
				if (blockExtends != null) {
					DefinitionCollection fontDefinitions = this.styleSheetDefinition
							.getFontDefinitions();
					// get the definition for the font to extend
					parentDefinition = fontDefinitions
							.getDefinition(blockExtends);
					if (parentDefinition == null) {
						throw new CssSyntaxError("could not resolve font",
								blockId, lineNumber);
					}
				}
			}
			// create a new definition with the block id
			this.blockDefinition = new Definition(blockId);
			if (parentDefinition != null) {
				// set the parent id of the definition
				this.blockDefinition.setParentId(parentDefinition.getId());
			}
			// if the current block type is the colors section ...
		} else if (BLOCK_TYPE_SECTION_COLORS.equals(blockType)) {
			throw new CssSyntaxError(
					"block declarations are not allowed in the colors section",
					blockId, lineNumber);
			// if the current block is a style ...
		} else if (BLOCK_TYPE_STYLE.equals(blockType)) {
			Log.debug("starting local block");
			pushBlockType(BLOCK_TYPE_LOCAL);
			this.blockId = blockId;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.style.parser.CssContentHandler#onProperty(de.enough.glaze
	 * .style.parser.CssParser, java.lang.String, java.lang.String)
	 */
	public void onProperty(CssParser parser, String propertyId,
			String propertyValue) throws CssSyntaxError {
		int lineNumber = parser.getLineNumber();
		String blockType = getBlockType();
		// if the current block is a style ...
		if (BLOCK_TYPE_STYLE.equals(blockType)) {
			// add the property to the style definition
			this.styleDefinition.addProperty(propertyId, propertyValue,
					lineNumber);
			Log.debug("added style property : " + propertyId + ":"
					+ propertyValue);
			// if the current block id a local block definition ...
		} else if (BLOCK_TYPE_LOCAL.equals(blockType)) {
			// add the property to the style definition
			this.styleDefinition.addProperty(this.blockId, propertyId,
					propertyValue, lineNumber);
			Log.debug("added style property : " + this.blockId + "-"
					+ propertyId + ":" + propertyValue);
			// if the block is a background, border or font ...
		} else if (BLOCK_ID_BACKGROUND.equals(blockType)
				|| BLOCK_ID_BORDER.equals(blockType)
				|| BLOCK_ID_FONT.equals(blockType)) {
			// add the property to the block definition ...
			this.blockDefinition.addProperty(blockType, propertyId,
					propertyValue, lineNumber);
			Log.debug("added block property : " + blockType + "-" + propertyId
					+ ":" + propertyValue);
			// if the block is the colors section ...
		} else if (BLOCK_TYPE_SECTION_COLORS.equals(blockType)) {
			// parse and add the color to the stylesheet
			Property colorAttr = new Property(propertyId, propertyValue,
					lineNumber);
			Color color = (Color) ColorPropertyParser.getInstance().parse(
					colorAttr);
			this.stylesheet.setColor(propertyId, color);
			Log.debug("added color property : " + propertyId + ":" + color);
			// if the block is the backgrounds, borders or fonts section ...
		} else if (blockType == null
				|| BLOCK_TYPE_SECTION_BACKGROUNDS.equals(blockType)
				|| BLOCK_TYPE_SECTION_BORDERS.equals(blockType)
				|| BLOCK_TYPE_SECTION_FONTS.equals(blockType)) {
			throw new CssSyntaxError(
					"property declarations are not allowed here", propertyId,
					lineNumber);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.style.parser.CssContentHandler#onBlockEnd(de.enough.glaze
	 * .style.parser.CssParser)
	 */
	public void onBlockEnd(CssParser parser) throws CssSyntaxError {
		int lineNumber = parser.getLineNumber();
		String blockType = popBlockType();
		if (blockType != null) {
			if (BLOCK_TYPE_STYLE.equals(blockType)) {
				DefinitionCollection styleDefinitions = this.styleSheetDefinition
						.getStyleDefinitions();
				styleDefinitions.addDefinition(this.styleDefinition);
			} else if (BLOCK_ID_BACKGROUND.equals(blockType)) {
				DefinitionCollection backgroundDefinitions = this.styleSheetDefinition
						.getBackgroundDefinitions();
				backgroundDefinitions.addDefinition(this.blockDefinition);
				Log.debug("added background definition : "
						+ this.blockDefinition.getId());
			} else if (BLOCK_ID_BORDER.equals(blockType)) {
				DefinitionCollection borderDefinitions = this.styleSheetDefinition
						.getBorderDefinitions();
				borderDefinitions.addDefinition(this.blockDefinition);
				Log.debug("added border definition : "
						+ this.blockDefinition.getId());
			} else if (BLOCK_ID_FONT.equals(blockType)) {
				DefinitionCollection fontDefinitions = this.styleSheetDefinition
						.getFontDefinitions();
				fontDefinitions.addDefinition(this.blockDefinition);
				Log.debug("added font definition : " + this.blockId);
			}
		} else {
			throw new CssSyntaxError("invalid block", lineNumber);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.style.parser.CssContentHandler#onDocumentEnd(de.enough
	 * .glaze.style.parser.CssParser)
	 */
	public void onDocumentEnd(CssParser parser) throws CssSyntaxError {
		// do nothing
	}
}