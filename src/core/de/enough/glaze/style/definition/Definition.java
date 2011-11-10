package de.enough.glaze.style.definition;

import java.util.Enumeration;
import java.util.Hashtable;

import de.enough.glaze.style.StyleSheet;
import de.enough.glaze.style.definition.converter.Converter;
import de.enough.glaze.style.parser.property.Property;

/**
 * A collection of properties. Definitions are used in the
 * {@link StyleSheetDefinition} of a {@link StyleSheet} instance to create
 * styles, backgrounds etc..
 * 
 * @author Andre
 * 
 */
public class Definition {

	/**
	 * the id
	 */
	private final String id;

	/**
	 * the class id
	 */
	private String classId;

	/**
	 * the properties
	 */
	private final Hashtable properties;

	/**
	 * the parent definition
	 */
	private Definition parent;

	/**
	 * the parent definition id
	 */
	private String parentId;

	/**
	 * Creates a new {@link Definition} instance
	 */
	public Definition() {
		this(null);
	}

	/**
	 * Creates a new {@link Definition} instance
	 * 
	 * @param id
	 *            the id
	 * @param classId
	 *            the class id
	 */
	public Definition(String id, String classId) {
		this.id = id + ":" + classId;
		this.classId = classId;
		this.properties = new Hashtable();
	}

	/**
	 * Creates a new {@link Definition} instance
	 * 
	 * @param id
	 *            the id
	 */
	public Definition(String id) {
		this.id = id;
		this.properties = new Hashtable();
	}

	/**
	 * Returns the id
	 * 
	 * @return the id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Returns the class id
	 * 
	 * @return the class id
	 */
	public String getClassId() {
		return this.classId;
	}

	/**
	 * Sets the parent definition id
	 * 
	 * @param parentId
	 *            the parent definition id
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * Returns the parent definition id
	 * 
	 * @return
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * Sets the parent definition
	 * 
	 * @param parent
	 *            the parent definition
	 */
	public void setParent(Definition parent) {
		this.parent = parent;
	}

	/**
	 * Returns the parent definition
	 * 
	 * @return the parent definition
	 */
	public Definition getParent() {
		return this.parent;
	}

	/**
	 * Adds a property
	 * 
	 * @param blockId
	 *            the block id
	 * @param propertyId
	 *            the property id
	 * @param propertyValue
	 *            the property value
	 * @param propertyLine
	 *            the property line
	 */
	public void addProperty(String blockId, String propertyId,
			String propertyValue, int propertyLine) {
		addProperty(blockId + "-" + propertyId, propertyValue, propertyLine);
	}

	/**
	 * Adds a property
	 * 
	 * @param propertyId
	 *            the property id
	 * @param propertyValue
	 *            the property value
	 * @param propertyLine
	 *            the property line
	 */
	public void addProperty(String propertyId, String propertyValue,
			int propertyLine) {
		Property attribute = new Property(propertyId, propertyValue,
				propertyLine);
		addProperty(attribute);
	}

	/**
	 * Adds a property
	 * 
	 * @param property
	 *            the property
	 */
	private void addProperty(Property property) {
		this.properties.put(property.getId(), property);
	}

	/**
	 * Adds all properties of the given definition
	 * 
	 * @param definition
	 *            the definition
	 */
	public void addProperties(Definition definition) {
		Enumeration properties = definition.getProperties();
		while (properties.hasMoreElements()) {
			Property property = (Property) properties.nextElement();
			addProperty(property);
		}
	}

	/**
	 * Returns the property found for first found id of the given property ids
	 * 
	 * @param propertyIds
	 *            the property ids
	 * @return the property
	 */
	public Property getProperty(String[] propertyIds) {
		for (int index = 0; index < propertyIds.length; index++) {
			String propertyId = propertyIds[index];
			Property property = getProperty(propertyId);
			if (property != null) {
				return (Property) property;
			}
		}

		return null;
	}

	/**
	 * Returns the property for the given property id
	 * 
	 * @param propertyId
	 *            the property id
	 * @return the property
	 */
	public Property getProperty(String propertyId) {
		Property property = (Property) this.properties.get(propertyId);

		// if no property with the given id is stored in this definition ...
		if (property == null && this.parent != null) {
			// try with the parent definition
			return this.parent.getProperty(propertyId);
		} else {
			return property;
		}
	}

	/**
	 * Returns the properties
	 * 
	 * @return the properties
	 */
	public Enumeration getProperties() {
		return this.properties.elements();
	}

	/**
	 * Returns true if this definition has properties which are accepted by the
	 * given converter
	 * 
	 * @param converter
	 *            the converter
	 * @return true if this definition has properties which are accepted by the
	 *         given converter otherwise false
	 */
	public boolean hasProperties(Converter converter) {
		String[] ids = converter.getIds();
		for (int index = 0; index < ids.length; index++) {
			String id = ids[index];
			if (getProperty(id) != null) {
				return true;
			}
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String result = "Definition [\n";
		Enumeration properties = this.properties.elements();
		while (properties.hasMoreElements()) {
			Property property = (Property) properties.nextElement();
			result += property + "\n";
		}
		result += "]\n";
		return result;
	}
}