package de.enough.glaze.style.definition.converter.border;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.XYEdges;
import de.enough.glaze.content.ContentException;
import de.enough.glaze.style.Dimension;
import de.enough.glaze.style.StyleResources;
import de.enough.glaze.style.URL;
import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.definition.converter.Converter;
import de.enough.glaze.style.definition.converter.utils.DimensionConverterUtils;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.DimensionPropertyParser;
import de.enough.glaze.style.parser.property.Property;
import de.enough.glaze.style.parser.property.UrlPropertyParser;
import de.enough.glaze.style.property.border.GzBorderFactory;

public class BitmapBorderConverter implements Converter {
	/**
	 * the instance
	 */
	private static BitmapBorderConverter INSTANCE;

	/**
	 * Returns the instance
	 * 
	 * @return the instance
	 */
	public static BitmapBorderConverter getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new BitmapBorderConverter();
		}

		return INSTANCE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.style.definition.converter.Converter#getIds()
	 */
	public String[] getIds() {
		return new String[] { "border-image", "border-width" };
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

		Property borderImageProp = definition.getProperty("border-image");
		Property borderWidthProp = definition.getProperty("border-width");

		Bitmap imageBitmap = null;
		XYEdges borderWidths = DimensionConverterUtils.toXYEdges(1);

		if (borderImageProp != null) {
			Object result = UrlPropertyParser.getInstance().parse(
					borderImageProp);
			if (result instanceof URL) {
				URL url = (URL) result;
				try {
					imageBitmap = StyleResources.getInstance().loadBitmap(url);
				} catch (ContentException e) {
					throw new CssSyntaxError("unable to load image",
							borderImageProp);
				}
			} else {
				throw new CssSyntaxError("must be a single url",
						borderImageProp);
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
		
		return GzBorderFactory.createImageBorder(borderWidths, borderWidths, imageBitmap);
	}

}
