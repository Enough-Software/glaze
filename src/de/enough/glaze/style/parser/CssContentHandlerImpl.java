package de.enough.glaze.style.parser;

import java.util.Stack;

import de.enough.glaze.log.Log;
import de.enough.glaze.style.Color;
import de.enough.glaze.style.Style;
import de.enough.glaze.style.StyleSheet;
import de.enough.glaze.style.background.GzBackground;
import de.enough.glaze.style.border.GzBorder;
import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.definition.converter.BackgroundConverter;
import de.enough.glaze.style.definition.converter.BorderConverter;
import de.enough.glaze.style.definition.converter.FontConverter;
import de.enough.glaze.style.definition.converter.StyleConverter;
import de.enough.glaze.style.font.GzFont;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.ColorPropertyParser;
import de.enough.glaze.style.parser.property.Property;

public class CssContentHandlerImpl implements CssContentHandler {

	private final static String BLOCK_SECTION_COLORS = "colors";

	private final static String BLOCK_SECTION_BACKGROUNDS = "backgrounds";

	private final static String BLOCK_SECTION_BORDERS = "borders";

	private final static String BLOCK_SECTION_FONTS = "fonts";

	private final static String BLOCK_STYLE = "style";

	private final static String BLOCK_LOCAL = "local";

	private final static String BLOCK_BACKGROUND = "background";

	private final static String BLOCK_BORDER = "border";

	private final static String BLOCK_FONT = "font";

	private Stack blockStack;

	private String blockId;

	private String styleId;

	private Style styleBase;

	private String styleClass;

	private Definition styleDefinition;

	private Definition blockDefinition;

	private final StyleSheet stylesheet;

	public CssContentHandlerImpl(StyleSheet stylesheet) {
		this.blockStack = new Stack();
		this.stylesheet = stylesheet;
	}

	private void pushBlockType(String id) {
		this.blockStack.push(id);
	}

	private String popBlockType() {
		if (!this.blockStack.isEmpty()) {
			String block = (String) this.blockStack.pop();
			return block;
		} else {
			return null;
		}
	}

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
	 * de.enough.glaze.style.parser.ContentHandler#onBlockStart(java.lang.String
	 * , java.lang.String, java.lang.String)
	 */
	public void onBlockStart(CssParser parser, String blockId,
			String blockClass, String blockExtends) throws CssSyntaxError {
		int lineNumber = parser.getLineNumber();
		String blockType = getBlockType();
		if (blockType == null) {
			if (BLOCK_SECTION_COLORS.equals(blockId)) {
				pushBlockType(BLOCK_SECTION_COLORS);
				Log.d("starting block : colors");
			} else if (BLOCK_SECTION_BACKGROUNDS.equals(blockId)) {
				pushBlockType(BLOCK_SECTION_BACKGROUNDS);
				Log.d("starting block : backgrounds");
			} else if (BLOCK_SECTION_BORDERS.equals(blockId)) {
				pushBlockType(BLOCK_SECTION_BORDERS);
				Log.d("starting block : borders");
			} else if (BLOCK_SECTION_FONTS.equals(blockId)) {
				pushBlockType(BLOCK_SECTION_FONTS);
				Log.d("starting block : fonts");
			} else {
				pushBlockType(BLOCK_STYLE);

				this.styleId = blockId;
				this.styleClass = blockClass;
				this.styleBase = null;

				Log.d("starting style : " + blockId);

				if (blockExtends == null && blockClass == null) {
					this.styleDefinition = new Definition();
				} else {
					if (this.styleClass != null) {
						if (Style.isValidClass(this.styleClass)) {
							this.styleBase = this.stylesheet
									.getStyle(this.styleId);
							Log.d("style class : " + blockId + " : "
									+ this.styleClass);
						} else {
							throw new CssSyntaxError("invalid style class", this.styleClass, lineNumber);
						}
					} else if (blockExtends != null) {
						this.styleBase = this.stylesheet.getStyle(blockExtends);
						Log.d("style extends : " + blockExtends);
					}

					if (this.styleBase != null) {
						Definition baseDefinition = this.styleBase
								.getDefinition();
						this.styleDefinition = new Definition(baseDefinition);
					} else {
						if (this.styleClass != null) {
							throw new CssSyntaxError("unable to resolve style",
									blockId, lineNumber);
						} else if (blockExtends != null) {
							throw new CssSyntaxError("unable to resolve style",
									blockExtends, lineNumber);
						}
					}
				}
			}
		} else if (BLOCK_SECTION_BACKGROUNDS.equals(blockType)
				|| BLOCK_SECTION_BORDERS.equals(blockType)
				|| BLOCK_SECTION_FONTS.equals(blockType)) {
			Definition parentDefinition = null;
			if (BLOCK_SECTION_BACKGROUNDS.equals(blockType)) {
				Log.d("starting background : " + blockId);
				pushBlockType(BLOCK_BACKGROUND);
				if (blockExtends != null) {
					GzBackground backgroundExtends = this.stylesheet
							.getBackground(blockExtends);
					if (backgroundExtends != null) {
						parentDefinition = backgroundExtends.getDefinition();
					} else {
						throw new CssSyntaxError(
								"could not resolve background", blockId,
								lineNumber);
					}
				}
			} else if (BLOCK_SECTION_BORDERS.equals(blockType)) {
				Log.d("starting border : " + blockId);
				pushBlockType(BLOCK_BORDER);
				if (blockExtends != null) {
					GzBorder borderExtends = this.stylesheet
							.getBorder(blockExtends);
					if (borderExtends != null) {
						parentDefinition = borderExtends.getDefinition();
					} else {
						throw new CssSyntaxError("could not resolve border",
								blockId, lineNumber);
					}
				}
			} else if (BLOCK_SECTION_FONTS.equals(blockType)) {
				Log.d("starting font : " + blockId);
				pushBlockType(BLOCK_FONT);
				if (blockExtends != null) {
					GzFont fontExtends = this.stylesheet.getFont(blockExtends);
					if (fontExtends != null) {
						parentDefinition = fontExtends.getDefinition();
					} else {
						throw new CssSyntaxError("could not resolve font",
								blockId, lineNumber);
					}
				}
			}
			this.blockId = blockId;
			this.blockDefinition = new Definition(parentDefinition);
		} else if (BLOCK_SECTION_COLORS.equals(blockType)) {
			throw new CssSyntaxError(
					"block declarations are not allowed in the colors section",
					blockId, lineNumber);
		} else if (BLOCK_STYLE.equals(blockType)) {
			Log.d("starting local block");
			pushBlockType(BLOCK_LOCAL);
			this.blockId = blockId;
		}
	}

	public void onProperty(CssParser parser, String propertyId,
			String propertyValue) throws CssSyntaxError {
		int lineNumber = parser.getLineNumber();
		String block = getBlockType();
		if (BLOCK_STYLE.equals(block)) {
			this.styleDefinition.addProperty(propertyId, propertyValue,
					lineNumber);

			Log.d("added style property : " + propertyId + ":" + propertyValue);
		} else if (BLOCK_LOCAL.equals(block)) {
			this.styleDefinition.addProperty(this.blockId, propertyId,
					propertyValue, lineNumber);
			Log.d("added style property : " + this.blockId + "-" + propertyId
					+ ":" + propertyValue);
		} else if (BLOCK_BACKGROUND.equals(block) || BLOCK_BORDER.equals(block)
				|| BLOCK_FONT.equals(block)) {
			this.blockDefinition.addProperty(block, propertyId, propertyValue,
					lineNumber);
			Log.d("added block property : " + block + "-" + propertyId + ":"
					+ propertyValue);
		} else if (BLOCK_SECTION_COLORS.equals(block)) {
			Property colorAttr = new Property(propertyId, propertyValue,
					lineNumber);
			Color color = (Color) ColorPropertyParser.getInstance().parse(
					colorAttr);
			this.stylesheet.addColor(propertyId, color);
			Log.d("added color property : " + propertyId + ":" + color);
		} else if (block == null || BLOCK_SECTION_BACKGROUNDS.equals(block)
				|| BLOCK_SECTION_BORDERS.equals(block)
				|| BLOCK_SECTION_FONTS.equals(block)) {
			throw new CssSyntaxError(
					"property declarations are not allowed here", propertyId,
					lineNumber);
		}
	}

	public void onBlockEnd(CssParser parser) throws CssSyntaxError {
		int lineNumber = parser.getLineNumber();
		String blockType = popBlockType();
		if (blockType != null) {
			if (BLOCK_STYLE.equals(blockType)) {
				Style style = (Style) StyleConverter.getInstance().convert(
						this.styleDefinition);
				style.setDefinition(this.styleDefinition);

				if (this.styleClass != null) {
					Log.d("setting style class : " + this.styleId + ":"
							+ this.styleClass);
					style.setBaseStyle(this.styleBase);
					this.styleBase.setClass(this.styleClass, style);
				} else {
					Log.d("adding style : " + this.styleId);
					this.stylesheet.addStyle(this.styleId, style);
				}
			} else if (BLOCK_BACKGROUND.equals(blockType)) {
				GzBackground background = (GzBackground) BackgroundConverter
						.getInstance().convert(this.blockDefinition);
				background.setDefinition(this.blockDefinition);
				this.stylesheet.addBackground(this.blockId, background);
				Log.d("added background : " + this.blockId);
			} else if (BLOCK_BORDER.equals(blockType)) {
				GzBorder border = (GzBorder) BorderConverter.getInstance()
						.convert(this.blockDefinition);
				border.setDefinition(this.blockDefinition);
				this.stylesheet.addBorder(this.blockId, border);
				Log.d("added border : " + this.blockId);
			} else if (BLOCK_FONT.equals(blockType)) {
				GzFont font = (GzFont) FontConverter.getInstance().convert(
						this.blockDefinition);
				font.setDefinition(this.blockDefinition);
				this.stylesheet.addFont(this.blockId, font);
				Log.d("added font : " + this.blockId);
			}
		} else {
			throw new CssSyntaxError("invalid block", lineNumber);
		}
	}
}