package de.enough.glaze.style.parser.attribute;

import de.enough.glaze.style.Color;
import de.enough.glaze.style.Style;
import de.enough.glaze.style.StyleSheet;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.utils.ParserUtils;

public class ColorAttributeParser extends AttributeParser {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.style.parser.ValueParser#parseValue(java.lang.String)
	 */
	public Object parseValue(String id, String value, Style style,
			StyleSheet stylesheet) throws CssSyntaxError {
		if (value.startsWith("#")) {
			return parseHexColor(value);
		} else if (value.startsWith("rgb") || value.startsWith("argb")) {
			return parseRgbColor(value);
		} else {
			return resolveColor(value, stylesheet);
		}
	}

	/**
	 * Parses a color in hex format from the given value
	 * 
	 * @param value
	 *            the value
	 * @return the color
	 * @throws CssSyntaxError
	 *             if the syntax is wrong
	 */
	private Color parseHexColor(String value) throws CssSyntaxError {
		try {
			int valueLength = value.length();
			if (valueLength <= 4) {
				// this is either #rgb or #argb:
				StringBuffer buffer = new StringBuffer((valueLength - 1) * 2);
				for (int i = 1; i < valueLength; i++) {
					char c = value.charAt(i);
					buffer.append(c).append(c);
				}
				long color = Long.parseLong(buffer.toString(), 16);
				return new Color((int) color);

			}
			long color = Long.parseLong(value.substring(1), 16);
			return new Color((int) color);
		} catch (NumberFormatException e) {
			throw new CssSyntaxError(value, "invalid color");
		}

	}

	/**
	 * Parses a color in rgb/argb format from the given value
	 * 
	 * @param value
	 *            the value
	 * @return the color
	 * @throws CssSyntaxError
	 *             if the syntax is wrong
	 */
	private Color parseRgbColor(String value) throws CssSyntaxError {
		int startPos = value.indexOf('(');
		int endPos = value.indexOf(')');
		if (startPos != -1 && endPos != -1) {
			String[] chunks = ParserUtils.toArray(
					value.substring(startPos + 1, endPos), ',');
			if (chunks.length >= 3 && chunks.length <= 4) {
				int color = 0;
				for (int i = 0; i < chunks.length; i++) {
					String chunk = chunks[i];
					int chunkLength = chunk.length();
					int chunkColor;
					if (chunk.charAt(chunkLength - 1) == '%') {
						int percent = Integer.parseInt(chunk.substring(0,
								chunkLength - 1).trim());
						chunkColor = (percent * 255) / 100;
					} else {
						chunkColor = Integer.parseInt(chunk);
					}
					color |= chunkColor << ((chunks.length - i - 1) * 8);
				}
				return new Color(color);
			}
		}

		throw new CssSyntaxError(value, "invalid color");
	}

	/**
	 * Resolves a default color or previously declared color
	 * 
	 * @param value
	 *            the value
	 * @return the color
	 */
	private Color resolveColor(String value, StyleSheet stylesheet)
			throws CssSyntaxError {
		Color color = (Color) Color.DEFAULT_COLORS.get(value);
		if (color != null) {
			return color;
		}

		color = stylesheet.getColor(value);
		if (color != null) {
			return color;
		}

		throw new CssSyntaxError(value, "color could not be resolved");
	}
}
