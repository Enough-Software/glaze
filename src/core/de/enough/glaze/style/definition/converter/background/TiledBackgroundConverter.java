package de.enough.glaze.style.definition.converter.background;

import net.rim.device.api.system.Bitmap;
import de.enough.glaze.content.ContentException;
import de.enough.glaze.style.Dimension;
import de.enough.glaze.style.Url;
import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.definition.converter.Converter;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.DimensionPropertyParser;
import de.enough.glaze.style.parser.property.Property;
import de.enough.glaze.style.parser.property.UrlPropertyParser;
import de.enough.glaze.style.property.background.GzBackgroundFactory;
import de.enough.glaze.style.resources.StyleResources;

/**
 * A {@link Converter} implementation to convert a definition to a patch/bitmap
 * background
 * 
 * @author Andre
 * 
 */
public class TiledBackgroundConverter implements Converter {

	/**
	 * the instance
	 */
	private static TiledBackgroundConverter INSTANCE;

	/**
	 * Returns the instance
	 * 
	 * @return the instance
	 */
	public static TiledBackgroundConverter getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new TiledBackgroundConverter();
		}

		return INSTANCE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.style.definition.converter.Converter#getIds()
	 */
	public String[] getIds() {
		return new String[] { "background-image", "background-width" };
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

		Property backgroundTypeProp = definition.getProperty("background-type");
		Property backgroundImageProp = definition
				.getProperty("background-image");
		Property backgroundWidthProp = definition
				.getProperty("background-width");

		Bitmap bitmap = null;
		Dimension[] width = null;

		if (backgroundImageProp != null) {
			Object result = UrlPropertyParser.getInstance().parse(
					backgroundImageProp);
			if (result instanceof Url) {
				Url url = (Url) result;
				try {
					bitmap = StyleResources.getInstance().loadBitmap(url);
				} catch (ContentException e) {
					throw new CssSyntaxError("unable to load image",
							backgroundImageProp);
				}
			} else {
				throw new CssSyntaxError("must be a single url",
						backgroundImageProp);
			}
		}

		if (backgroundWidthProp != null) {
			Object result = DimensionPropertyParser.getInstance().parse(
					backgroundWidthProp);
			if (result instanceof Dimension) {
				Dimension dimension = (Dimension) result;
				width = new Dimension[] { dimension, dimension, dimension,
						dimension };
			} else if (result instanceof Dimension[]) {
				width = (Dimension[]) result;
			}
		}

		if (bitmap != null && width != null) {
			return GzBackgroundFactory.createTiledBackground(bitmap,
					width);
		} else {
			throw new CssSyntaxError(
					"unable to create patch background, properties are missing",
					backgroundTypeProp);
		}
	}
}
