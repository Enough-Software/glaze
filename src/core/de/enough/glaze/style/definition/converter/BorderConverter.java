/*
 *
 * Copyright: (c) 2012 Enough Software GmbH & Co. KG
 *
 * Licensed under:
 * 1. MIT: http://www.opensource.org/licenses/mit-license.php
 * 2. Apache 2.0: http://opensource.org/licenses/apache2.0
 * 3. GPL with classpath exception: http://www.gnu.org/software/classpath/license.html
 *
 * You may not use this file except in compliance with these licenses.
 *
 */
 
package de.enough.glaze.style.definition.converter;

import java.util.Vector;

import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.definition.converter.border.BevelBorderConverter;
import de.enough.glaze.style.definition.converter.border.TiledBorderConverter;
import de.enough.glaze.style.definition.converter.border.RoundedBorderConverter;
import de.enough.glaze.style.definition.converter.border.SimpleBorderConverter;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.Property;
import de.enough.glaze.style.parser.property.ValuePropertyParser;
import de.enough.glaze.style.property.border.GzBorder;

/**
 * Converts a given definition to a border
 * 
 * @author Andre
 * 
 */
public class BorderConverter implements Converter {

	/**
	 * the instance
	 */
	private static BorderConverter INSTANCE;

	/**
	 * Returns the instance
	 * 
	 * @return the instance
	 */
	public static BorderConverter getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new BorderConverter();
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

			addIds(SimpleBorderConverter.getInstance(), idCollection);
			addIds(RoundedBorderConverter.getInstance(), idCollection);
			addIds(BevelBorderConverter.getInstance(), idCollection);
			addIds(TiledBorderConverter.getInstance(), idCollection);
			addIds(new String[] { "border-type" }, idCollection);

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
			return null;
		}

		Property borderTypeProp = definition.getProperty("border-type");

		if (borderTypeProp != null) {
			Object result = ValuePropertyParser.getInstance().parse(
					borderTypeProp);
			if (result instanceof String) {
				String backgroundType = (String) result;
				return convertType(backgroundType, definition, borderTypeProp);
			} else if (result instanceof String[]) {
				throw new CssSyntaxError("must be a single id", borderTypeProp);
			}
			return null;
		} else {
			return SimpleBorderConverter.getInstance().convert(definition);
		}
	}

	/**
	 * Converts the given definition by the given border type
	 * 
	 * @param borderType
	 *            the border type
	 * @param definition
	 *            the definition
	 * @param borderTypeProperty
	 *            the border type property
	 * @return the converted border
	 * @throws CssSyntaxError
	 *             if the css syntax is wrong
	 */
	public GzBorder convertType(String borderType, Definition definition,
			Property borderTypeProperty) throws CssSyntaxError {
		if ("simple".equals(borderType)) {
			return (GzBorder) SimpleBorderConverter.getInstance().convert(
					definition);
		} else if ("bevel".equals(borderType)) {
			return (GzBorder) BevelBorderConverter.getInstance().convert(
					definition);
		} else if ("rounded".equals(borderType)) {
			return (GzBorder) RoundedBorderConverter.getInstance().convert(
					definition);
		} else if ("tiled".equals(borderType) || "patch".equals(borderType) || "bitmap".equals(borderType)) {
			return (GzBorder) TiledBorderConverter.getInstance().convert(
					definition);
		} else {
			throw new CssSyntaxError("unknown border type", borderTypeProperty);
		}
	}

}
