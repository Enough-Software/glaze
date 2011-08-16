package de.enough.glaze.style;

import java.util.Hashtable;

public class Style {
	
	private Hashtable attributes;
	
	public Style() {
		this.attributes = new Hashtable();
	}
	
	public void addAttribute(String id, Object value) {
		this.attributes.put(id, value);
	}
	
	public Object getAttribute(String id) {
		return this.attributes.get(id);
	}
}
