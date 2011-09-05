package de.enough.glaze.style.definition.converter.background;

import de.enough.glaze.style.StyleSheet;
import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.definition.StyleSheetDefinition;
import de.enough.glaze.style.definition.converter.BackgroundConverter;
import de.enough.glaze.style.definition.converter.Converter;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.Property;
import de.enough.glaze.style.parser.property.ValuePropertyParser;
import de.enough.glaze.style.property.background.GzBackground;
import de.enough.glaze.style.property.background.GzBackgroundFactory;

/**
 * A {@link Converter} implementation to convert a definition to a mask
 * background
 * 
 * @author Andre
 * 
 */
public class MaskBackgroundConverter implements Converter {

	/**
	 * the instance
	 */
	private static MaskBackgroundConverter INSTANCE;

	/**
	 * Returns the instance
	 * 
	 * @return the instance
	 */
	public static MaskBackgroundConverter getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MaskBackgroundConverter();
		}

		return INSTANCE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.style.definition.converter.Converter#getIds()
	 */
	public String[] getIds() {
		return new String[] { "background-mask", "background-background" };
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
		Property backgroundMaskProp = definition.getProperty("background-mask");
		Property backgroundProp = definition
				.getProperty("background-background");

		GzBackground maskBackground = null;
		GzBackground background = null;

		if (backgroundMaskProp != null) {
			Object result = ValuePropertyParser.getInstance().parse(
					backgroundMaskProp);
			if (result instanceof String) {
				String backgroundId = (String) result;
				maskBackground = getBackground(backgroundId);
				if (maskBackground == null) {
					throw new CssSyntaxError("unable to resolve background",
							backgroundMaskProp);
				}
			} else {
				throw new CssSyntaxError("must be a single background id",
						backgroundMaskProp);
			}
		}

		if (backgroundProp != null) {
			Object result = ValuePropertyParser.getInstance().parse(
					backgroundProp);
			if (result instanceof String) {
				String backgroundId = (String) result;
				background = getBackground(backgroundId);
				if (background == null) {
					throw new CssSyntaxError("unable to resolve background",
							backgroundMaskProp);
				}
			} else {
				throw new CssSyntaxError("must be a single background id",
						backgroundMaskProp);
			}
		}

		if (maskBackground != null && background != null) {
			return GzBackgroundFactory.createMaskBackground(maskBackground,
					background);
		} else {
			throw new CssSyntaxError(
					"unable to create mask background, properties are missing",
					backgroundTypeProp);
		}
	}

	private GzBackground getBackground(String id) throws CssSyntaxError {
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
