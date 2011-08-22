package de.enough.glaze.style.definition;

import java.util.Enumeration;
import java.util.Hashtable;

import de.enough.glaze.style.definition.converter.Converter;
import de.enough.glaze.style.parser.property.Property;

public class Definition {

	private final String id;

	private String classId;

	private final Hashtable properties;

	private Definition parent;

	public Definition() {
		this(null);
	}

	public Definition(String id, String classId) {
		this.id = id + ":" + classId;
		this.classId = classId;
		this.properties = new Hashtable();
	}

	public Definition(String id) {
		this.id = id;
		this.properties = new Hashtable();
	}

	public String getId() {
		return this.id;
	}

	public String getClassId() {
		return this.classId;
	}

	public void setParent(Definition parent) {
		this.parent = parent;
	}

	public Definition getParent() {
		return this.parent;
	}

	public void addProperty(String blockId, String propertyId,
			String propertyValue, int propertyLine) {
		addProperty(blockId + "-" + propertyId, propertyValue, propertyLine);
	}

	public void addProperty(String propertyId, String propertyValue,
			int propertyLine) {
		Property attribute = new Property(propertyId, propertyValue,
				propertyLine);
		addProperty(attribute);
	}

	private void addProperty(Property property) {
		this.properties.put(property.getId(), property);
	}

	public void addProperties(Definition definition) {
		Enumeration properties = definition.getProperties();
		while (properties.hasMoreElements()) {
			Property property = (Property) properties.nextElement();
			addProperty(property);
		}
	}

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

	public Property getProperty(String propertyId) {
		Property property = (Property) this.properties.get(propertyId);

		if (property == null && this.parent != null) {
			return this.parent.getProperty(propertyId);
		} else {
			return property;
		}
	}

	public Enumeration getProperties() {
		return this.properties.elements();
	}

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