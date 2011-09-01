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

/**
 * Converts a given definition to a font
 * 
 * @author Andre
 * 
 */
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

	/*
	 * (non-Javadoc)
	 * 
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
		if (!definition.hasProperties(this)) {
			return null;
		} 

		Property fontfamilyAttr = definition.getProperty("font-family");
		Property fontSizeAttr = definition.getProperty("font-size");
		Property fontStyleAttr = definition.getProperty("font-style");
		Property fontColorAttr = definition.getProperty("font-color");
		Property colorAttr = definition.getProperty("color");

		// prepare the default values
		Font defaultFont = Font.getDefault();
		FontFamily fontFamily = defaultFont.getFontFamily();
		Dimension fontSize = new Dimension(defaultFont.getHeight(),
				Dimension.UNIT_PX);
		int fontStyle = defaultFont.getStyle();
		Color fontColor = new Color(0x000000);

		// convert the font-family property
		if (fontfamilyAttr != null) {
			String fontName = fontFamily.getName();
			try {
				fontFamily = FontFamily.forName(fontName);
			} catch (ClassNotFoundException e) {
				throw new CssSyntaxError("unknown font family", fontfamilyAttr);
			}
		}

		// convert the font-size property
		if (fontSizeAttr != null) {
			fontSize = (Dimension) DimensionPropertyParser.getInstance().parse(
					fontSizeAttr);
		}

		// convert the font-style property
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

		// convert the font-color property
		if (fontColorAttr != null) {
			fontColor = (Color) ColorPropertyParser.getInstance().parse(
					fontColorAttr);
		}

		// convert the color property
		if (colorAttr != null) {
			fontColor = (Color) ColorPropertyParser.getInstance().parse(
					colorAttr);
		}

		// get the font size in pixels
		int fontHeightPixels = fontSize.getValue(defaultFont.getHeight());

		// create the result font
		Font resultFont = fontFamily.getFont(fontStyle, fontHeightPixels,
				Ui.UNITS_px);

		return new GzFont(resultFont, fontColor);
	}

	/**
	 * Returns the font style bit for the given font style value
	 * 
	 * @param fontStyleValue
	 *            the font style value
	 * @param fontStyleProp
	 *            the font style property
	 * @return the font style bit
	 * @throws CssSyntaxError
	 *             if the css syntax is wrong
	 */
	private int getFontStyle(String fontStyleValue, Property fontStyleProp)
			throws CssSyntaxError {
		if (GzFont.STYLE_BOLD.equals(fontStyleValue)) {
			return Font.BOLD;
		} else if (GzFont.STYLE_UNDERLINED.equals(fontStyleValue)) {
			return Font.UNDERLINED;
		} else if (GzFont.STYLE_ITALIC.equals(fontStyleValue)) {
			return Font.ITALIC;
		} else {
			throw new CssSyntaxError("unknown font style", fontStyleProp);
		}
	}
}
