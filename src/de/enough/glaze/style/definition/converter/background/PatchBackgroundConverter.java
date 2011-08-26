package de.enough.glaze.style.definition.converter.background;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.XYEdges;
import de.enough.glaze.content.ContentException;
import de.enough.glaze.style.Color;
import de.enough.glaze.style.Dimension;
import de.enough.glaze.style.StyleResources;
import de.enough.glaze.style.URL;
import de.enough.glaze.style.background.GzBackgroundFactory;
import de.enough.glaze.style.border.GzBorderFactory;
import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.definition.converter.Converter;
import de.enough.glaze.style.definition.converter.utils.DimensionConverterUtils;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.ColorPropertyParser;
import de.enough.glaze.style.parser.property.DimensionPropertyParser;
import de.enough.glaze.style.parser.property.Property;
import de.enough.glaze.style.parser.property.UrlPropertyParser;
import de.enough.glaze.style.parser.property.ValuePropertyParser;

public class PatchBackgroundConverter implements Converter {

	/**
	 * the instance
	 */
	private static PatchBackgroundConverter INSTANCE;

	/**
	 * Returns the instance
	 * 
	 * @return the instance
	 */
	public static PatchBackgroundConverter getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PatchBackgroundConverter();
		}

		return INSTANCE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.style.definition.converter.Converter#getIds()
	 */
	public String[] getIds() {
		return new String[] { "background-image", "background-width", "background-margin" };
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

		Property backgroundImageProp = definition.getProperty("background-image");
		Property backgroundWidthProp = definition.getProperty("background-width");
		Property backgroundMarginProp = definition.getProperty("background-margin");
		
		Bitmap imageBitmap = null;		
		Dimension[] dimensions = new Dimension[] {Dimension.ZERO, Dimension.ZERO, Dimension.ZERO, Dimension.ZERO};
		Dimension[] margins = new Dimension[] {Dimension.ZERO, Dimension.ZERO, Dimension.ZERO, Dimension.ZERO};
		
		if (backgroundImageProp != null) {
			Object result = UrlPropertyParser.getInstance().parse(
					backgroundImageProp);
			if (result instanceof URL) {
				URL url = (URL) result;
				try {
					imageBitmap = StyleResources.getInstance().loadBitmap(url);
				} catch (ContentException e) {
					throw new CssSyntaxError("unable to load image",
							backgroundImageProp);
				}
			} else {
				throw new CssSyntaxError("must be a single url",
						backgroundImageProp);
			}
		}
		
		if (backgroundMarginProp != null) {
			Object result = DimensionPropertyParser.getInstance().parse(
					backgroundMarginProp);
			if (result instanceof Dimension) {
				Dimension dimension = (Dimension) result;
				margins = new Dimension [] {dimension, dimension, dimension, dimension};
			} else if (result instanceof Dimension[]) {
				margins = (Dimension[]) result;
			}
		}
		
		if (backgroundWidthProp != null) {
			Object result = DimensionPropertyParser.getInstance().parse(
					backgroundWidthProp);
			if (result instanceof Dimension) {
				Dimension dimension = (Dimension) result;
				dimensions = new Dimension [] {dimension, dimension, dimension, dimension};
			} else if (result instanceof Dimension[]) {
				dimensions = (Dimension[]) result;
			}
		}
		
		return GzBackgroundFactory.createPatchBackground(imageBitmap, margins, dimensions);
	}
}
