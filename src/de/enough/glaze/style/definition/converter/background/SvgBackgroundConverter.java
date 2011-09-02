package de.enough.glaze.style.definition.converter.background;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.m2g.SVGImage;

import de.enough.glaze.content.ContentException;
import de.enough.glaze.style.StyleResources;
import de.enough.glaze.style.StyleSheet;
import de.enough.glaze.style.URL;
import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.definition.StyleSheetDefinition;
import de.enough.glaze.style.definition.converter.BackgroundConverter;
import de.enough.glaze.style.definition.converter.Converter;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.Property;
import de.enough.glaze.style.parser.property.UrlPropertyParser;
import de.enough.glaze.style.property.background.GzBackground;
import de.enough.glaze.style.property.background.GzBackgroundFactory;

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
			if (result instanceof URL) {
				URL url = (URL) result;
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

	public GzBackground getBackground(String id) throws CssSyntaxError {
		GzBackground background = StyleSheet.getInstance().getBackground(id);

		if (background == null) {
			StyleSheetDefinition styleSheetDefinition = StyleSheet
					.getInstance().getDefinition();
			Definition backgroundDefinition = styleSheetDefinition
					.getBackgroundDefinition(id);
			background = (GzBackground) BackgroundConverter.getInstance()
					.convert(backgroundDefinition);
		}

		return background;
	}
}
