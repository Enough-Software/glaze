package de.enough.glaze.style.parser.property;

/**
 * A class representing a property with an id and a value. The corresponding
 * line number of the property in the CSS stylesheet is added for error
 * handling.
 * 
 * @author Andre
 * 
 */
public class Property {

	/**
	 * the id
	 */
	private final String id;

	/**
	 * the value
	 */
	private final String value;

	/**
	 * the line number
	 */
	private final int line;

	/**
	 * Creates a new {@link Property} instance
	 * 
	 * @param id
	 *            the id
	 * @param value
	 *            the value
	 * @param line
	 *            the line number
	 */
	public Property(String id, String value, int line) {
		this.id = id;
		this.value = value;
		this.line = line;
	}

	/**
	 * Returns the id
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns the value
	 * 
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Returns the line number
	 * 
	 * @return the line number
	 */
	public int getLine() {
		return line;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Property [" + this.id + ":" + this.value + "]";
	}
}
