package de.enough.glaze.style.parser;

import java.util.Stack;

import de.enough.glaze.log.Log;
import de.enough.glaze.style.Color;
import de.enough.glaze.style.Style;
import de.enough.glaze.style.StyleSheet;
import de.enough.glaze.style.background.GzBackground;
import de.enough.glaze.style.border.GzBorder;
import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.definition.DefinitionCollection;
import de.enough.glaze.style.definition.StyleSheetDefinition;
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

	private Definition styleDefinition;

	private Definition blockDefinition;

	private final StyleSheet stylesheet;

	private final StyleSheetDefinition styleSheetDefinition;

	public CssContentHandlerImpl(StyleSheet stylesheet) {
		this.blockStack = new Stack();
		this.stylesheet = stylesheet;
		this.styleSheetDefinition = stylesheet.getDefinition();
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
	 * de.enough.glaze.style.parser.CssContentHandler#onDocumentStart(de.enough
	 * .glaze.style.parser.CssParser)
	 */
	public void onDocumentStart(CssParser parser) {
		// TODO Auto-generated method stub

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

				Log.d("starting style : " + blockId);

				DefinitionCollection styleDefinitions = this.styleSheetDefinition
						.getStyleDefinitions();
				Definition parentDefinition;
				if (blockClass != null) {
					if (Style.isClass(blockClass)) {
						parentDefinition = styleDefinitions
								.getDefinition(blockId);
						if (parentDefinition != null) {
							this.styleDefinition = new Definition(blockId,
									blockClass);
							this.styleDefinition.setParent(parentDefinition);
						} else {
							throw new CssSyntaxError("unable to resolve style",
									blockId, lineNumber);
						}
					} else {
						throw new CssSyntaxError("invalid style class",
								blockClass, lineNumber);
					}
				} else if (blockExtends != null) {
					parentDefinition = styleDefinitions
							.getDefinition(blockExtends);
					if (parentDefinition != null) {
						this.styleDefinition = new Definition(blockId);
						this.styleDefinition.setParent(parentDefinition);
					} else {
						throw new CssSyntaxError("unable to resolve style",
								blockExtends, lineNumber);
					}
				} else {
					this.styleDefinition = new Definition(blockId);
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
					DefinitionCollection backgroundDefinitions = this.styleSheetDefinition
							.getBackgroundDefinitions();
					parentDefinition = backgroundDefinitions
							.getDefinition(blockExtends);
					if (parentDefinition != null) {
						throw new CssSyntaxError(
								"could not resolve background", blockId,
								lineNumber);
					}
				}
			} else if (BLOCK_SECTION_BORDERS.equals(blockType)) {
				Log.d("starting border : " + blockId);
				pushBlockType(BLOCK_BORDER);
				if (blockExtends != null) {
					DefinitionCollection borderDefinitions = this.styleSheetDefinition
							.getBorderDefinitions();
					parentDefinition = borderDefinitions
							.getDefinition(blockExtends);
					if (parentDefinition != null) {
						throw new CssSyntaxError("could not resolve border",
								blockId, lineNumber);
					}
				}
			} else if (BLOCK_SECTION_FONTS.equals(blockType)) {
				Log.d("starting font : " + blockId);
				pushBlockType(BLOCK_FONT);
				if (blockExtends != null) {
					DefinitionCollection fontDefinitions = this.styleSheetDefinition
							.getFontDefinitions();
					parentDefinition = fontDefinitions
							.getDefinition(blockExtends);
					if (parentDefinition != null) {
						throw new CssSyntaxError("could not resolve font",
								blockId, lineNumber);
					}
				}
			}
			this.blockDefinition = new Definition(blockId);
			this.blockDefinition.setParent(parentDefinition);
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
				DefinitionCollection styleDefinitions = this.styleSheetDefinition
						.getStyleDefinitions();
				styleDefinitions.addDefinition(this.styleDefinition);
			} else if (BLOCK_BACKGROUND.equals(blockType)) {
				DefinitionCollection backgroundDefinitions = this.styleSheetDefinition
						.getBackgroundDefinitions();
				backgroundDefinitions.addDefinition(this.blockDefinition);
				Log.d("added background definition : "
						+ this.blockDefinition.getId());
			} else if (BLOCK_BORDER.equals(blockType)) {
				DefinitionCollection borderDefinitions = this.styleSheetDefinition
						.getBorderDefinitions();
				borderDefinitions.addDefinition(this.blockDefinition);
				Log.d("added border definition : "
						+ this.blockDefinition.getId());
			} else if (BLOCK_FONT.equals(blockType)) {
				DefinitionCollection fontDefinitions = this.styleSheetDefinition
						.getFontDefinitions();
				fontDefinitions.addDefinition(this.blockDefinition);
				Log.d("added font definition : " + this.blockId);
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
		// clear the whole stylesheet 
		this.stylesheet.clear();

		DefinitionCollection backgroundDefinitions = this.styleSheetDefinition
				.getBackgroundDefinitions();
		for (int index = 0; index < backgroundDefinitions.size(); index++) {
			Definition definition = backgroundDefinitions.getDefinition(index);
			if (this.stylesheet.getBackground(definition.getId()) == null) {
				GzBackground background = (GzBackground) BackgroundConverter
						.getInstance().convert(definition);
				this.stylesheet.addBackground(definition.getId(), background);
			}
		}

		DefinitionCollection borderDefinitions = this.styleSheetDefinition
				.getBorderDefinitions();
		for (int index = 0; index < borderDefinitions.size(); index++) {
			Definition definition = borderDefinitions.getDefinition(index);
			if (this.stylesheet.getBorder(definition.getId()) == null) {
				GzBorder border = (GzBorder) BorderConverter.getInstance()
						.convert(definition);
				this.stylesheet.addBorder(definition.getId(), border);
			}
		}

		DefinitionCollection fontDefinitions = this.styleSheetDefinition
				.getFontDefinitions();
		for (int index = 0; index < fontDefinitions.size(); index++) {
			Definition definition = fontDefinitions.getDefinition(index);
			if (this.stylesheet.getFont(definition.getId()) == null) {
				GzFont font = (GzFont) FontConverter.getInstance().convert(
						definition);
				this.stylesheet.addFont(definition.getId(), font);
			}
		}

		DefinitionCollection styleDefinitions = this.styleSheetDefinition
				.getStyleDefinitions();
		for (int index = 0; index < styleDefinitions.size(); index++) {
			Definition definition = styleDefinitions.getDefinition(index);
			Style style = (Style) StyleConverter.getInstance().convert(
					definition);

			String classId = definition.getClassId();
			if (classId != null) {
				Definition parentDefinition = definition.getParent();
				String parentId = parentDefinition.getId();
				Style parentStyle = this.stylesheet.getStyle(parentId);
				parentStyle.setClass(classId, style);
				style.setParentStyle(parentStyle);
			} else {
				this.stylesheet.addStyle(definition.getId(), style);
			}
		}

	}
}