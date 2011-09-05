package de.enough.glaze.style.definition.converter;

import java.util.Enumeration;
import java.util.Vector;

import de.enough.glaze.style.Dimension;
import de.enough.glaze.style.Margin;
import de.enough.glaze.style.Padding;
import de.enough.glaze.style.Style;
import de.enough.glaze.style.StyleSheet;
import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.definition.DefinitionCollection;
import de.enough.glaze.style.definition.StyleSheetDefinition;
import de.enough.glaze.style.definition.converter.utils.ConverterUtils;
import de.enough.glaze.style.definition.converter.utils.DimensionConverterUtils;
import de.enough.glaze.style.extension.Extension;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.Property;
import de.enough.glaze.style.parser.property.ValuePropertyParser;
import de.enough.glaze.style.property.Visibility;
import de.enough.glaze.style.property.background.GzBackground;
import de.enough.glaze.style.property.border.GzBorder;
import de.enough.glaze.style.property.font.GzFont;

/**
 * A converter implementation to convert a definition to a style.
 * 
 * @author Andre
 * 
 */
public class StyleConverter implements Converter {

	/**
	 * the instance
	 */
	private static StyleConverter INSTANCE;

	/**
	 * Returns the instance
	 * 
	 * @return the instance
	 */
	public static StyleConverter getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new StyleConverter();
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
			// collect all ids from all available converters
			Vector idCollection = new Vector();

			addIds(MarginConverter.getInstance(), idCollection);
			addIds(PaddingConverter.getInstance(), idCollection);
			addIds(BorderConverter.getInstance(), idCollection);
			addIds(BackgroundConverter.getInstance(), idCollection);
			addIds(FontConverter.getInstance(), idCollection);

			// collect the ids from the registered extensions
			Enumeration extensions = StyleSheet.getInstance().getExtensions();
			while (extensions.hasMoreElements()) {
				Extension extension = (Extension) extensions.nextElement();
				Converter extensionConverter = extension.getConverter();
				addIds(extensionConverter, idCollection);
			}

			addIds(new String[] { "background", "border", "font" },
					idCollection);

			addIds(new String[] { "min-width", "width", "max-width" },
					idCollection);

			addIds(new String[] { "min-height", "height", "max-height" },
					idCollection);

			addIds(new String[] { "visibility" }, idCollection);

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
		// validate the definition
		ConverterUtils.validate(definition, getIds());

		// create a new style
		Style style = new Style(definition.getId());

		// convert the margin
		Margin margin = (Margin) MarginConverter.getInstance().convert(
				definition);
		style.setMargin(margin);

		// convert the padding
		Padding padding = (Padding) PaddingConverter.getInstance().convert(
				definition);
		style.setPadding(padding);

		// convert the background
		GzBackground background = convertBackground(definition);
		style.setBackground(background);

		// convert the border
		GzBorder border = convertBorder(definition);
		style.setBorder(border);

		// convert the font
		GzFont font = convertFont(definition);
		style.setFont(font);

		// convert dimensional properties
		convertWidthDimensions(definition, style);
		convertHeightDimensions(definition, style);

		// convert visibility
		convertVisibility(definition, style);

		// convert all extensions
		Enumeration extensions = StyleSheet.getInstance().getExtensions();
		while (extensions.hasMoreElements()) {
			Extension extension = (Extension) extensions.nextElement();
			Converter extensionConverter = extension.getConverter();
			Object result = extensionConverter.convert(definition);
			if (result != null) {
				style.addExtension(extension, result);
			}
		}

		return style;
	}

	/**
	 * Converts the background properties of the given definition
	 * 
	 * @param definition
	 *            the definition
	 * @return the created border
	 * @throws CssSyntaxError
	 *             if a CSS syntax error is found
	 */
	public GzBackground convertBackground(Definition definition)
			throws CssSyntaxError {
		// get the backgrounds definitions
		StyleSheetDefinition definitions = StyleSheet.getInstance()
				.getDefinition();
		DefinitionCollection backgroundDefinitions = definitions
				.getBackgroundDefinitions();
		// get the background property
		Property backgroundProp = definition.getProperty("background");
		if (backgroundProp != null) {
			Object result = ValuePropertyParser.getInstance().parse(
					backgroundProp);
			if (result instanceof String) {
				String backgroundId = (String) result;
				GzBackground background = StyleSheet.getInstance()
						.getBackground(backgroundId);
				// if the background referenced through the background property
				// is valid ...
				if (background != null) {
					// and the definition has background-related properties ...
					if (definition.hasProperties(BackgroundConverter
							.getInstance())) {
						// add the definition of the referenced background to
						// the style definition
						Definition backgroundDefinition = backgroundDefinitions
								.getDefinition(backgroundId);
						definition.addProperties(backgroundDefinition);
						// otherwise ...
					} else {
						// just return the referenced background
						return background;
					}
				} else {
					throw new CssSyntaxError("unable to resolve background",
							backgroundProp);
				}
			} else {
				throw new CssSyntaxError("must be a single id", backgroundProp);
			}
		}

		// create a background from the definition
		return (GzBackground) BackgroundConverter.getInstance().convert(
				definition);
	}

	/**
	 * Converts the border properties of the given definition
	 * 
	 * @param definition
	 *            the definition
	 * @return the created border
	 * @throws CssSyntaxError
	 *             if a CSS syntax error is found
	 */
	public GzBorder convertBorder(Definition definition) throws CssSyntaxError {
		// get the border definitions
		StyleSheetDefinition definitions = StyleSheet.getInstance()
				.getDefinition();
		DefinitionCollection borderDefinitions = definitions
				.getBorderDefinitions();
		// get the border property
		Property borderProp = definition.getProperty("border");
		if (borderProp != null) {
			Object result = ValuePropertyParser.getInstance().parse(borderProp);
			if (result instanceof String) {
				String borderId = (String) result;
				GzBorder border = StyleSheet.getInstance().getBorder(borderId);
				// if the border referenced through the border property
				// is valid ...
				if (border != null) {
					if (definition.hasProperties(BorderConverter.getInstance())) {
						// add the definition of the referenced border to
						// the style definition
						Definition borderDefinition = borderDefinitions
								.getDefinition(borderId);
						definition.addProperties(borderDefinition);
						// otherwise ...
					} else {
						// just return the referenced border
						return border;
					}
				} else {
					throw new CssSyntaxError("unable to resolve border",
							borderProp);
				}
			} else {
				throw new CssSyntaxError("must be a single id", borderProp);
			}
		}

		// create a border from the definition
		return (GzBorder) BorderConverter.getInstance().convert(definition);
	}

	/**
	 * Converts the font properties of the given definition
	 * 
	 * @param definition
	 *            the definition
	 * @return the created font
	 * @throws CssSyntaxError
	 *             if a CSS syntax error is found
	 */
	public GzFont convertFont(Definition definition) throws CssSyntaxError {
		// get the font definitions
		StyleSheetDefinition definitions = StyleSheet.getInstance()
				.getDefinition();
		DefinitionCollection fontDefinitions = definitions.getFontDefinitions();
		// get the font property
		Property fontProp = definition.getProperty("font");
		if (fontProp != null) {
			Object result = ValuePropertyParser.getInstance().parse(fontProp);
			if (result instanceof String) {
				String fontId = (String) result;
				GzFont font = StyleSheet.getInstance().getFont(fontId);
				// if the font referenced through the font property
				// is valid ...
				if (font != null) {
					if (definition.hasProperties(FontConverter.getInstance())) {
						// add the definition of the referenced font to
						// the style definition
						Definition fontDefinition = fontDefinitions
								.getDefinition(fontId);
						definition.addProperties(fontDefinition);
						// otherwise ...
					} else {
						// just return the referenced font
						return font;
					}
				} else {
					throw new CssSyntaxError("unable to resolve font", fontProp);
				}
			} else {
				throw new CssSyntaxError("must be a single id", fontProp);
			}
		}

		// create a font from the definition
		return (GzFont) FontConverter.getInstance().convert(definition);
	}

	/**
	 * Converts the width properties of the given definition and sets it in the
	 * given style
	 * 
	 * @param definition
	 *            the definition
	 * @param style
	 *            the style
	 * @throws CssSyntaxError
	 *             if a CSS syntax error is found
	 */
	public void convertWidthDimensions(Definition definition, Style style)
			throws CssSyntaxError {
		Property minWidthProp = definition.getProperty("min-width");
		Property widthProp = definition.getProperty("width");
		Property maxWidthProp = definition.getProperty("max-width");

		if (minWidthProp != null) {
			Dimension minWidthDimension = DimensionConverterUtils
					.toDimension(minWidthProp);
			style.setMinWidth(minWidthDimension);
		} else {
			style.setMinWidth(null);
		}

		if (widthProp != null) {
			Dimension widthDimension = DimensionConverterUtils
					.toDimension(widthProp);
			style.setWidth(widthDimension);
		} else {
			style.setWidth(null);
		}

		if (maxWidthProp != null) {
			Dimension maxWidthDimension = DimensionConverterUtils
					.toDimension(maxWidthProp);
			style.setMaxWidth(maxWidthDimension);
		} else {
			style.setMaxWidth(null);
		}
	}

	/**
	 * Converts the height properties of the given definition and sets it in the
	 * given style
	 * 
	 * @param definition
	 *            the definition
	 * @param style
	 *            the style
	 * @throws CssSyntaxError
	 *             if a CSS syntax error is found
	 */
	public void convertHeightDimensions(Definition definition, Style style)
			throws CssSyntaxError {
		Property minHeightProp = definition.getProperty("min-height");
		Property heightProp = definition.getProperty("height");
		Property maxHeightProp = definition.getProperty("max-height");

		if (minHeightProp != null) {
			Dimension minHeightDimension = DimensionConverterUtils
					.toDimension(minHeightProp);
			style.setMinHeight(minHeightDimension);
		} else {
			style.setMinHeight(null);
		}

		if (heightProp != null) {
			Dimension heightDimension = DimensionConverterUtils
					.toDimension(heightProp);
			style.setHeight(heightDimension);
		} else {
			style.setHeight(null);
		}

		if (maxHeightProp != null) {
			Dimension maxHeightDimension = DimensionConverterUtils
					.toDimension(maxHeightProp);
			style.setMaxHeight(maxHeightDimension);
		} else {
			style.setMaxHeight(null);
		}
	}

	/**
	 * Converts the visibility property of the given definition and sets it in
	 * the given style
	 * 
	 * @param definition
	 *            the definition
	 * @param style
	 *            the style
	 * @throws CssSyntaxError
	 *             if a CSS syntax error is found
	 */
	public void convertVisibility(Definition definition, Style style)
			throws CssSyntaxError {
		Property visibilityProp = definition.getProperty("visibility");
		if (visibilityProp != null) {
			String visibilityValue = visibilityProp.getValue();
			int visibility = Visibility.getVisibility(visibilityValue,
					visibilityProp);
			style.setVisibility(visibility);
		} else {
			style.setVisibility(Visibility.VISIBLE);
		}
	}
}
