package de.enough.glaze.style.definition.converter;

import java.util.Vector;

import de.enough.glaze.style.background.GzBackground;
import de.enough.glaze.style.background.NoBackground;
import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.definition.converter.background.ImageBackgroundConverter;
import de.enough.glaze.style.definition.converter.background.LayerBackgroundConverter;
import de.enough.glaze.style.definition.converter.background.MaskBackgroundConverter;
import de.enough.glaze.style.definition.converter.background.GradientBackgroundConverter;
import de.enough.glaze.style.definition.converter.background.PatchBackgroundConverter;
import de.enough.glaze.style.definition.converter.background.RoundedBackgroundConverter;
import de.enough.glaze.style.definition.converter.background.SolidBackgroundConverter;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.Property;
import de.enough.glaze.style.parser.property.ValuePropertyParser;

/**
 * Converts a given definition to a background
 * 
 * @author Andre
 * 
 */
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
		// if the ids are not already set ...
		if (this.ids == null) {
			// collect all ids from the available converters
			Vector idCollection = new Vector();

			addIds(SolidBackgroundConverter.getInstance(), idCollection);
			addIds(ImageBackgroundConverter.getInstance(), idCollection);
			addIds(RoundedBackgroundConverter.getInstance(), idCollection);
			addIds(GradientBackgroundConverter.getInstance(), idCollection);
			addIds(PatchBackgroundConverter.getInstance(), idCollection);
			addIds(MaskBackgroundConverter.getInstance(), idCollection);
			addIds(LayerBackgroundConverter.getInstance(), idCollection);
			addIds(new String[] { "background-type" }, idCollection);

			// store the ids
			this.ids = new String[idCollection.size()];
			idCollection.copyInto(this.ids);
		}

		return this.ids;
	}

	/**
	 * Adds the ids of the given converter to the given id collection
	 * 
	 * @param converter
	 *            the converter
	 * @param idCollection
	 *            the id collection
	 */
	private void addIds(Converter converter, Vector idCollection) {
		addIds(converter.getIds(), idCollection);
	}

	/**
	 * Adds the given ids to the given id collection
	 * 
	 * @param ids
	 *            the ids
	 * @param idCollection
	 *            the id collection
	 */
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
		// if the definition has no properties handled by this converter ...
		if (!definition.hasProperties(this)) {
			// return null
			return NoBackground.getInstance();
		} 

		Property backgroundTypeProp = definition.getProperty("background-type");
		Property backgroundImageProp = definition
				.getProperty("background-image");
		Property backgroundColorProp = definition
				.getProperty("background-color");

		// if a background type is given ...
		if (backgroundTypeProp != null) {
			Object result = ValuePropertyParser.getInstance().parse(
					backgroundTypeProp);
			if (result instanceof String) {
				String backgroundType = (String) result;
				// convert the background by its type
				return convertType(backgroundType, definition,
						backgroundTypeProp);
			} else if (result instanceof String[]) {
				throw new CssSyntaxError("must be a single id",
						backgroundTypeProp);
			}
			return null;
			// if a background image is given ...
		} else if (backgroundImageProp != null) {
			// convert to an image background
			return ImageBackgroundConverter.getInstance().convert(definition);
			// if a background color is given ...
		} else if (backgroundColorProp != null) {
			// convert to a solid background
			return SolidBackgroundConverter.getInstance().convert(definition);
		} else {
			return NoBackground.getInstance();
		}
	}

	/**
	 * Converts the given definition by the given background type
	 * 
	 * @param backgroundType
	 *            the background type
	 * @param definition
	 *            the definition
	 * @param backgroundTypeProperty
	 *            the background type property
	 * @return the converted background
	 * @throws CssSyntaxError
	 *             if the css syntax is wrong
	 */
	public GzBackground convertType(String backgroundType,
			Definition definition, Property backgroundTypeProperty)
			throws CssSyntaxError {
		if ("solid".equals(backgroundType)) {
			return (GzBackground) SolidBackgroundConverter.getInstance()
					.convert(definition);
		} else if ("image".equals(backgroundType)) {
			return (GzBackground) ImageBackgroundConverter.getInstance()
					.convert(definition);
		} else if ("rounded".equals(backgroundType)) {
			return (GzBackground) RoundedBackgroundConverter.getInstance()
					.convert(definition);
		} else if ("gradient".equals(backgroundType)) {
			return (GzBackground) GradientBackgroundConverter.getInstance()
					.convert(definition);
		} else if ("mask".equals(backgroundType)) {
			return (GzBackground) MaskBackgroundConverter.getInstance()
					.convert(definition);
		} else if ("layer".equals(backgroundType)) {
			return (GzBackground) LayerBackgroundConverter.getInstance()
					.convert(definition);
		} else if ("patch".equals(backgroundType)) {
			return (GzBackground) PatchBackgroundConverter.getInstance()
					.convert(definition);
		} else {
			throw new CssSyntaxError("unknown background type",
					backgroundTypeProperty);
		}
	}
}
