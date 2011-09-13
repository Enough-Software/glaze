package de.enough.glaze.style.definition.converter;

import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.Ui;
import de.enough.glaze.style.Color;
import de.enough.glaze.style.Dimension;
import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.ColorPropertyParser;
import de.enough.glaze.style.parser.property.DimensionPropertyParser;
import de.enough.glaze.style.parser.property.Property;
import de.enough.glaze.style.parser.property.ValuePropertyParser;
import de.enough.glaze.style.property.font.GzFont;

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

		Property fontFamilyProp = definition.getProperty("font-family");
		Property fontSizeProp = definition.getProperty("font-size");
		Property fontStyleProp = definition.getProperty("font-style");
		Property fontColorProp = definition.getProperty("font-color");
		Property colorProp = definition.getProperty("color");

		// prepare the default values
		Font defaultFont = Font.getDefault();
		String name = FontFamily.FAMILY_SYSTEM;
		FontFamily family = defaultFont.getFontFamily();
		Dimension size = new Dimension(defaultFont.getHeight(),
				Dimension.UNIT_PX);
		int style = defaultFont.getStyle();
		Color color = new Color(0x000000);

		// convert the font-family property
		if (fontFamilyProp != null) {
			Object result = ValuePropertyParser.getInstance().parse(
					fontFamilyProp);
			if (result instanceof String) {
				name = (String) result;
				try {
					family = FontFamily.forName(name);
				} catch (ClassNotFoundException e) {
					throw new CssSyntaxError("unknown font family",
							fontFamilyProp);
				}
			} else {
				throw new CssSyntaxError("must be a single font name",
						fontFamilyProp);
			}
		}

		// convert the font-size property
		if (fontSizeProp != null) {
			Object result = ValuePropertyParser.getInstance().parse(
					fontSizeProp);
			if (result instanceof String) {
				String fontSizeValue = (String) result;
				size = getFontSize(fontSizeValue, fontSizeProp);
			} else {
				throw new CssSyntaxError("must be a single dimension or value",
						fontFamilyProp);
			}
		}

		// convert the font-style property
		if (fontStyleProp != null) {
			Object result = ValuePropertyParser.getInstance().parse(
					fontStyleProp);
			if (result instanceof String) {
				String fontStyleEnum = (String) result;
				style |= getFontStyle(fontStyleEnum, fontStyleProp);
			} else if (result instanceof String[]) {
				String[] fontStyleEnums = (String[]) result;
				for (int index = 0; index < fontStyleEnums.length; index++) {
					String fontStyleEnum = fontStyleEnums[index];
					style |= getFontStyle(fontStyleEnum, fontStyleProp);
				}
			}
		}

		// convert the font-color property
		if (fontColorProp != null) {
			color = (Color) ColorPropertyParser.getInstance().parse(
					fontColorProp);
		}

		// convert the color property
		if (colorProp != null) {
			color = (Color) ColorPropertyParser.getInstance().parse(colorProp);
		}

		// get the font size in pixels
		int fontHeightPixels = size.getValue(defaultFont.getHeight());

		// create the result font
		Font resultFont = family.getFont(style, fontHeightPixels, Ui.UNITS_px);

		return new GzFont(name, resultFont, color);
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
	 *             if the CSS syntax is wrong
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

	/**
	 * Returns the font size for the given font size value
	 * 
	 * @param fontSizeValue
	 *            the font size value
	 * @param fontSizeProp
	 *            the font size property
	 * @return the font size dimension
	 * @throws CssSyntaxError
	 *             if the CSS syntax is wrong
	 */
	private Dimension getFontSize(String fontSizeValue, Property fontSizeProp)
			throws CssSyntaxError {
		if (GzFont.SIZE_SMALL.equals(fontSizeValue)) {
			return new Dimension(75, Dimension.UNIT_PERCENT);
		} else if (GzFont.SIZE_MEDIUM.equals(fontSizeValue)) {
			return new Dimension(100, Dimension.UNIT_PERCENT);
		} else if (GzFont.SIZE_LARGE.equals(fontSizeValue)) {
			return new Dimension(150, Dimension.UNIT_PERCENT);
		} else {
			return (Dimension) DimensionPropertyParser.getInstance().parse(
					fontSizeProp);
		}
	}
}
