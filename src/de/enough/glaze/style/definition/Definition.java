package de.enough.glaze.style.definition;

import java.util.Enumeration;
import java.util.Hashtable;

import de.enough.glaze.style.definition.converter.Converter;
import de.enough.glaze.style.parser.property.Property;

public class Definition {

	protected Hashtable properties;

	public Definition() {
		this(null);
	}

	public Definition(Definition base) {
		this.properties = new Hashtable();
		if (base != null) {
			addDefinition(base, false);
		}
	}

	public void addDefinition(Definition definition) {
		addDefinition(definition, true);
	}

	private void addDefinition(Definition definition, boolean preserveValues) {
		Enumeration properties = definition.getProperties();
		while (properties.hasMoreElements()) {
			Property property = (Property) properties.nextElement();
			if (preserveValues) {
				if (this.properties.containsKey(property.getId())) {
					continue;
				}
			}
			addProperty(property);
		}
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

	public Property getProperty(String propertyId) {
		return (Property) this.properties.get(propertyId);
	}
	
	public Property getProperty(String[] propertyIds) {
		for (int index = 0; index < propertyIds.length; index++) {
			String propertyId = propertyIds[index];
			Object property = this.properties.get(propertyId);
			if(property != null) {
				return (Property)property;
			}
		}
		
		return null;
	}

	public Enumeration getProperties() {
		return this.properties.elements();
	}

	public boolean hasProperties(Converter converter) {
		String[] ids = converter.getIds();
		Enumeration properties = getProperties();
		while (properties.hasMoreElements()) {
			Property property = (Property) properties.nextElement();
			if (hasProperty(ids, property)) {
				return true;
			}
		}

		return false;
	}

	private boolean hasProperty(String[] ids, Property property) {
		for (int index = 0; index < ids.length; index++) {
			String id = ids[index];
			String propertyId = property.getId();
			if (id.equals(propertyId)) {
				return true;
			}
		}

		return false;
	}

	public void finalize() {
		this.properties.clear();
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