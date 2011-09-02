package de.enough.glaze.style.definition.converter.border;

import net.rim.device.api.ui.XYEdges;
import de.enough.glaze.style.Color;
import de.enough.glaze.style.Dimension;
import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.definition.converter.Converter;
import de.enough.glaze.style.definition.converter.utils.ColorConverterUtils;
import de.enough.glaze.style.definition.converter.utils.DimensionConverterUtils;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.ColorPropertyParser;
import de.enough.glaze.style.parser.property.DimensionPropertyParser;
import de.enough.glaze.style.parser.property.Property;
import de.enough.glaze.style.property.border.GzBorderFactory;

public class BevelBorderConverter implements Converter {

	/**
	 * the instance
	 */
	private static BevelBorderConverter INSTANCE;

	/**
	 * Returns the instance
	 * 
	 * @return the instance
	 */
	public static BevelBorderConverter getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new BevelBorderConverter();
		}

		return INSTANCE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.style.definition.converter.Converter#getIds()
	 */
	public String[] getIds() {
		return new String[] { "border-inner-color", "border-outer-color",
				"border-width" };
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

		Property borderInnerColorProp = definition
				.getProperty("border-inner-color");
		Property borderOuterColorProp = definition
				.getProperty("border-outer-color");
		Property borderWidthProp = definition.getProperty("border-width");

		XYEdges borderInnerColors = ColorConverterUtils
				.toXYEdges(net.rim.device.api.ui.Color.LIGHTGRAY);
		XYEdges borderOuterColors = ColorConverterUtils
				.toXYEdges(net.rim.device.api.ui.Color.DARKGRAY);
		XYEdges borderWidths = DimensionConverterUtils.toXYEdges(1);

		if (borderInnerColorProp != null) {
			Object result = ColorPropertyParser.getInstance().parse(
					borderInnerColorProp);
			if (result instanceof Color) {
				Color color = (Color) result;
				borderInnerColors = ColorConverterUtils.toXYEdges(color);
			} else if (result instanceof Color[]) {
				Color[] colors = (Color[]) result;
				borderInnerColors = ColorConverterUtils.toXYEdges(colors,
						borderInnerColorProp);
			}
		}

		if (borderOuterColorProp != null) {
			Object result = ColorPropertyParser.getInstance().parse(
					borderOuterColorProp);
			if (result instanceof Color) {
				Color color = (Color) result;
				borderOuterColors = ColorConverterUtils.toXYEdges(color);
			} else if (result instanceof Color[]) {
				Color[] colors = (Color[]) result;
				borderOuterColors = ColorConverterUtils.toXYEdges(colors,
						borderOuterColorProp);
			}
		}

		if (borderWidthProp != null) {
			Object result = DimensionPropertyParser.getInstance().parse(
					borderWidthProp);
			if (result instanceof Dimension) {
				Dimension dimension = (Dimension) result;
				borderWidths = DimensionConverterUtils.toXYEdges(dimension);
			} else if (result instanceof Dimension[]) {
				Dimension[] dimensions = (Dimension[]) result;
				borderWidths = DimensionConverterUtils.toXYEdges(dimensions,
						borderWidthProp);
			}
		}

		return GzBorderFactory.createBevelBorder(borderWidths,
				borderOuterColors, borderInnerColors);
	}

}
