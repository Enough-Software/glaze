package de.enough.glaze.style.definition.converter;

import java.util.Vector;

import de.enough.glaze.style.background.GzBackground;
import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.definition.converter.background.GradientBackgroundConverter;
import de.enough.glaze.style.definition.converter.background.ImageBackgroundConverter;
import de.enough.glaze.style.definition.converter.background.RoundedBackgroundConverter;
import de.enough.glaze.style.definition.converter.background.SolidBackgroundConverter;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.Property;
import de.enough.glaze.style.parser.property.ValuePropertyParser;

public class BackgroundConverter implements Converter {

	/**
	 * the instance
	 */
	private static BackgroundConverter INSTANCE;

	/**
	 * Returns the instance
	 * 
	 * @return the instance
	 */
	public static BackgroundConverter getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new BackgroundConverter();
		}

		return INSTANCE;
	}

	/**
	 * the ids
	 */
	private String[] ids;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.style.definition.converter.Converter#getIds()
	 */
	public String[] getIds() {
		if (this.ids == null) {
			Vector idCollection = new Vector();

			addIds(SolidBackgroundConverter.getInstance(), idCollection);
			addIds(ImageBackgroundConverter.getInstance(), idCollection);
			addIds(RoundedBackgroundConverter.getInstance(), idCollection);
			addIds(GradientBackgroundConverter.getInstance(), idCollection);
			addIds(new String[] { "background-type" }, idCollection);

			this.ids = new String[idCollection.size()];
			idCollection.copyInto(this.ids);
		}

		return this.ids;
	}

	private void addIds(Converter converter, Vector idCollection) {
		addIds(converter.getIds(), idCollection);
	}

	private void addIds(String[] ids, Vector idCollection) {
		for (int index = 0; index < ids.length; index++) {
			String id = ids[index];
			idCollection.addElement(id);
		}
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
		Property backgroundColorProp = definition
				.getProperty("background-color");

		if (backgroundTypeProp != null) {
			Object result = ValuePropertyParser.getInstance().parse(
					backgroundTypeProp);
			if (result instanceof String) {
				String backgroundType = (String) result;
				return convertType(backgroundType, definition, backgroundTypeProp);
			} else if (result instanceof String[]) {
				throw new CssSyntaxError("must be a single id",
						backgroundTypeProp);
			}
			return null;
		} else if (backgroundImageProp != null) {
			return ImageBackgroundConverter.getInstance().convert(definition);
		} else if (backgroundColorProp != null) {
			return SolidBackgroundConverter.getInstance().convert(definition);
		} else {
			return null;
		}
	}

	public GzBackground convertType(String backgroundType,
			Definition definition, Property backgroundTypeProperty)
			throws CssSyntaxError {
		if ("image".equals(backgroundType)) {
			return (GzBackground) ImageBackgroundConverter.getInstance()
					.convert(definition);
		} else if ("rounded".equals(backgroundType)) {
			return (GzBackground) RoundedBackgroundConverter.getInstance()
					.convert(definition);
		} else if ("gradient".equals(backgroundType)) {
			return (GzBackground) GradientBackgroundConverter.getInstance()
					.convert(definition);
		} else {
			throw new CssSyntaxError("unknown background type",
					backgroundTypeProperty);
		}
	}

}
