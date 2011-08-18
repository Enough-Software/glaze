package de.enough.glaze.style.definition.converter;

import java.util.Enumeration;
import java.util.Vector;

import de.enough.glaze.style.Margin;
import de.enough.glaze.style.Padding;
import de.enough.glaze.style.Style;
import de.enough.glaze.style.StyleSheet;
import de.enough.glaze.style.background.GzBackground;
import de.enough.glaze.style.border.GzBorder;
import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.extension.Extension;
import de.enough.glaze.style.font.GzFont;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.Property;
import de.enough.glaze.style.parser.property.ValuePropertyParser;

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

	public String[] getIds() {
		if (this.ids == null) {
			Vector idCollection = new Vector();

			addIds(MarginConverter.getInstance(), idCollection);
			addIds(PaddingConverter.getInstance(), idCollection);
			addIds(BorderConverter.getInstance(), idCollection);
			addIds(BackgroundConverter.getInstance(), idCollection);
			addIds(FontConverter.getInstance(), idCollection);
			
			Enumeration extensions = StyleSheet.getInstance().getExtensions();
			while(extensions.hasMoreElements()) {
				Extension extension = (Extension)extensions.nextElement();
				Converter extensionConverter = extension.getConverter();
				addIds(extensionConverter, idCollection);
			}
			
			addIds(new String[] { "background", "border", "font" },
					idCollection);
			
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

	public Object convert(Definition definition) throws CssSyntaxError {
		validate(definition);
		
		Style style = new Style();
		style.setDefinition(definition);

		Margin margin = (Margin) MarginConverter.getInstance().convert(
				definition);
		style.setMargin(margin);

		Padding padding = (Padding) PaddingConverter.getInstance().convert(
				definition);
		style.setPadding(padding);

		GzBackground background = convertBackground(definition);
		style.setBackground(background);

		GzBorder border = convertBorder(definition);
		style.setBorder(border);

		GzFont font = convertFont(definition);
		style.setFont(font);
		
		Enumeration extensions = StyleSheet.getInstance().getExtensions();
		while(extensions.hasMoreElements()) {
			Extension extension = (Extension)extensions.nextElement();
			Converter extensionConverter = extension.getConverter();
			Object result = extensionConverter.convert(definition);
			if(result != null) {
				style.addExtension(extension, result);
			}
		}

		return style;
	}

	public GzBackground convertBackground(Definition definition)
			throws CssSyntaxError {
		Property backgroundProp = definition.getProperty("background");
		if (backgroundProp != null) {
			Object result = ValuePropertyParser.getInstance().parse(
					backgroundProp);
			if (result instanceof String) {
				String backgroundId = (String) result;
				GzBackground background = StyleSheet.getInstance()
						.getBackground(backgroundId);
				if (background != null) {
					if (definition.hasProperties(BackgroundConverter.getInstance())) {
						Definition backgroundDefinition = background
								.getDefinition();
						definition.addDefinition(backgroundDefinition);
					} else {
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

		return (GzBackground) BackgroundConverter.getInstance().convert(
				definition);
	}

	public GzBorder convertBorder(Definition definition) throws CssSyntaxError {
		Property borderProp = definition.getProperty("border");
		if (borderProp != null) {
			Object result = ValuePropertyParser.getInstance().parse(borderProp);
			if (result instanceof String) {
				String borderId = (String) result;
				GzBorder border = StyleSheet.getInstance().getBorder(borderId);
				if (border != null) {
					if (definition.hasProperties(BorderConverter.getInstance())) {
						Definition borderDefinition = border.getDefinition();
						definition.addDefinition(borderDefinition);
					} else {
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

		return (GzBorder) BorderConverter.getInstance().convert(definition);
	}

	public GzFont convertFont(Definition definition) throws CssSyntaxError {
		Property fontProp = definition.getProperty("font");
		if (fontProp != null) {
			Object result = ValuePropertyParser.getInstance().parse(fontProp);
			if (result instanceof String) {
				String fontId = (String) result;
				GzFont font = StyleSheet.getInstance().getFont(fontId);
				if (font != null) {
					if (definition.hasProperties(FontConverter.getInstance())) {
						Definition fontDefinition = font.getDefinition();
						definition.addDefinition(fontDefinition);
					} else {
						return font;
					}
				} else {
					throw new CssSyntaxError("unable to resolve font", fontProp);
				}
			} else {
				throw new CssSyntaxError("must be a single id", fontProp);
			}
		}

		return (GzFont) FontConverter.getInstance().convert(definition);
	}
	
	public void validate(Definition definition) throws CssSyntaxError {
		Enumeration properties = definition.getProperties();
		while(properties.hasMoreElements()) {
			Property property = (Property)properties.nextElement();
			validate(property);
		}
	}
	
	private void validate(Property property) throws CssSyntaxError {
		String[] ids = getIds();
		String propertyId = property.getId();
		for (int index = 0; index < ids.length; index++) {
			String id = ids[index];
			if (id.equals(propertyId)) {
				return;
			}
		}

		throw new CssSyntaxError("unknown property", property.getId(), property.getLine());
	}

}
