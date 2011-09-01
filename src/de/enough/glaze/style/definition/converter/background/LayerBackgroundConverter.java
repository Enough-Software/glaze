package de.enough.glaze.style.definition.converter.background;

import de.enough.glaze.style.Dimension;
import de.enough.glaze.style.StyleSheet;
import de.enough.glaze.style.background.GzBackground;
import de.enough.glaze.style.background.GzBackgroundFactory;
import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.definition.StyleSheetDefinition;
import de.enough.glaze.style.definition.converter.BackgroundConverter;
import de.enough.glaze.style.definition.converter.Converter;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.DimensionPropertyParser;
import de.enough.glaze.style.parser.property.Property;
import de.enough.glaze.style.parser.property.ValuePropertyParser;

public class LayerBackgroundConverter implements Converter {

	/**
	 * the instance
	 */
	private static LayerBackgroundConverter INSTANCE;

	/**
	 * Returns the instance
	 * 
	 * @return the instance
	 */
	public static LayerBackgroundConverter getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new LayerBackgroundConverter();
		}

		return INSTANCE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.style.definition.converter.Converter#getIds()
	 */
	public String[] getIds() {
		return new String[] { "background-backgrounds", "background-margins" };
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
		Property backgroundsProp = definition
				.getProperty("background-backgrounds");
		Property backgroundMarginsProp = definition
				.getProperty("background-margins");

		GzBackground[] backgrounds = null;
		Dimension[] margins = null;

		if (backgroundsProp != null) {
			Object result = ValuePropertyParser.getInstance().parse(
					backgroundsProp);
			if (result instanceof String) {
				String backgroundId = (String) result;
				GzBackground background = getBackground(backgroundId);
				if (background != null) {
					backgrounds = new GzBackground[] { background };
				} else {
					throw new CssSyntaxError("unable to resolve background",
							backgroundId, backgroundsProp.getLine());
				}
			} else if (result instanceof String[]) {
				String[] backgroundIds = (String[]) result;
				backgrounds = new GzBackground[backgroundIds.length];
				for (int index = 0; index < backgroundIds.length; index++) {
					String backgroundId = backgroundIds[index];
					GzBackground background = getBackground(backgroundId);
					if (background != null) {
						backgrounds[index] = background;
					} else {
						throw new CssSyntaxError(
								"unable to resolve background", backgroundId,
								backgroundsProp.getLine());
					}
				}
			}
		}

		if (backgroundMarginsProp != null) {
			Object result = DimensionPropertyParser.getInstance().parse(
					backgroundMarginsProp);
			if (result instanceof Dimension) {
				Dimension dimension = (Dimension) result;
				if (backgrounds.length == 1) {
					margins = new Dimension[] { dimension };
				} else {
					throw new CssSyntaxError(
							"the number of margins must match the number of backgrounds",
							backgroundMarginsProp);
				}
			} else if (result instanceof Dimension[]) {
				Dimension[] dimensions = (Dimension[]) result;
				if (backgrounds.length == dimensions.length) {
					margins = dimensions;
				} else {
					throw new CssSyntaxError(
							"the number of margins must match the number of backgrounds",
							backgroundMarginsProp);
				}
			}
		}

		if (backgrounds != null && margins != null) {
			return GzBackgroundFactory.createLayerBackground(backgrounds,
					margins);
		} else {
			throw new CssSyntaxError(
					"unable to create layer background, properties are missing",
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
