package de.enough.glaze.style.definition.converter;

import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.Ui;
import de.enough.glaze.style.Color;
import de.enough.glaze.style.Dimension;
import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.font.GzFont;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.ColorPropertyParser;
import de.enough.glaze.style.parser.property.DimensionPropertyParser;
import de.enough.glaze.style.parser.property.Property;
import de.enough.glaze.style.parser.property.ValuePropertyParser;

public class FontConverter implements Converter {

	/**
	 * the instance
	 */
	private static FontConverter INSTANCE;

	/**
	 * Returns the instance
	 * 
	 * @return the instance
	 */
	public static FontConverter getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new FontConverter();
		}

		return INSTANCE;
	}

	/* (non-Javadoc)
	 * @see de.enough.glaze.style.definition.converter.Converter#getIds()
	 */
	public String[] getIds() {
		return new String[] { "font-family", "font-size", "font-color",
				"font-style", "color" };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.style.definition.converter.Converter#convert(de.enough
	 * .glaze.style.definition.Definition)
	 */
	public Object convert(Definition definition) throws CssSyntaxError {
		if(!definition.hasProperties(this)) {
			return null;
		}
		
		Property fontfamilyAttr = definition.getProperty("font-family");
		Property fontSizeAttr = definition.getProperty("font-size");
		Property fontStyleAttr = definition.getProperty("font-style");
		Property fontColorAttr = definition.getProperty("font-color");
		Property colorAttr = definition.getProperty("color");

		Font defaultFont = Font.getDefault();
		FontFamily fontFamily = defaultFont.getFontFamily();
		Dimension fontSize = new Dimension(defaultFont.getHeight(),
				Dimension.UNIT_PX);
		int fontStyle = defaultFont.getStyle();
		Color fontColor = new Color(0x000000);

		if (fontfamilyAttr != null) {
			String fontName = fontFamily.getName();
			try {
				fontFamily = FontFamily.forName(fontName);
			} catch (ClassNotFoundException e) {
				throw new CssSyntaxError("unknown font family", fontfamilyAttr);
			}
		}

		if (fontSizeAttr != null) {
			fontSize = (Dimension) DimensionPropertyParser.getInstance().parse(
					fontSizeAttr);
		}

		if (fontStyleAttr != null) {
			Object result = ValuePropertyParser.getInstance().parse(
					fontStyleAttr);
			if (result instanceof String) {
				String fontStyleEnum = (String) result;
				fontStyle |= getFontStyle(fontStyleEnum, fontStyleAttr);
			} else if (result instanceof String[]) {
				String[] fontStyleEnums = (String[]) result;
				for (int index = 0; index < fontStyleEnums.length; index++) {
					String fontStyleEnum = fontStyleEnums[index];
					fontStyle |= getFontStyle(fontStyleEnum, fontStyleAttr);
				}
			}
		}

		if (fontColorAttr != null) {
			fontColor = (Color) ColorPropertyParser.getInstance().parse(
					fontColorAttr);
		}

		if (colorAttr != null) {
			fontColor = (Color) ColorPropertyParser.getInstance().parse(
					colorAttr);
		}

		int fontHeight = fontSize.getValue();
		Font resultFont = fontFamily
				.getFont(fontStyle, fontHeight, Ui.UNITS_px);

		return new GzFont(resultFont, fontColor);
	}

	private int getFontStyle(String fontStyleEnum, Property fontStyleAttr)
			throws CssSyntaxError {
		if (GzFont.STYLE_BOLD.equals(fontStyleEnum)) {
			return Font.BOLD;
		} else if (GzFont.STYLE_UNDERLINED.equals(fontStyleEnum)) {
			return Font.UNDERLINED;
		} else if (GzFont.STYLE_ITALIC.equals(fontStyleEnum)) {
			return Font.ITALIC;
		} else {
			throw new CssSyntaxError("unknown font style", fontStyleAttr);
		}
	}
}
