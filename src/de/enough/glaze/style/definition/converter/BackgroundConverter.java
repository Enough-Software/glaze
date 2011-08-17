package de.enough.glaze.style.definition.converter;

import java.util.Vector;

import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.definition.converter.background.SolidBackgroundConverter;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.Property;

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
			addIds(new String[] { "background-type" },
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
		
		if(backgroundTypeProp != null) {
			// handle background type
		} else {
			return SolidBackgroundConverter.getInstance().convert(definition);
		}

		return null;
	}

}
