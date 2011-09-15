package de.enough.glaze.style.definition.converter.background;

import java.io.IOException;

import javax.microedition.m2g.SVGImage;

import de.enough.glaze.content.ContentException;
import de.enough.glaze.style.StyleResources;
import de.enough.glaze.style.Url;
import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.definition.converter.Converter;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.Property;
import de.enough.glaze.style.parser.property.UrlPropertyParser;
import de.enough.glaze.style.property.background.GzBackgroundFactory;

/**
 * A {@link Converter} implementation to convert a definition to a SVG
 * background
 * 
 * @author Andre
 * 
 */
public class SvgBackgroundConverter implements Converter {

	/**
	 * the instance
	 */
	private static SvgBackgroundConverter INSTANCE;

	/**
	 * Returns the instance
	 * 
	 * @return the instance
	 */
	public static SvgBackgroundConverter getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SvgBackgroundConverter();
		}

		return INSTANCE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.style.definition.converter.Converter#getIds()
	 */
	public String[] getIds() {
		return new String[] { "background-file" };
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
		Property backgroundFileProp = definition.getProperty("background-file");

		SVGImage svgImage = null;

		if (backgroundFileProp != null) {
			Object result = UrlPropertyParser.getInstance().parse(
					backgroundFileProp);
			if (result instanceof Url) {
				Url url = (Url) result;
				try {
					svgImage = StyleResources.getInstance().loadSVG(url);
				} catch (IOException e) {
					throw new CssSyntaxError("unable to load svg file",
							backgroundFileProp);
				} catch (ContentException e) {
					throw new CssSyntaxError("unable to load svg file",
							backgroundFileProp);
				}
			} else {
				throw new CssSyntaxError("must be a single file url",
						backgroundFileProp);
			}
		}

		if (svgImage != null) {
			return GzBackgroundFactory.createSvgBackground(svgImage);
		} else {
			throw new CssSyntaxError(
					"unable to create mask background, properties are missing",
					backgroundTypeProp);
		}
	}
}
