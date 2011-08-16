package de.enough.glaze.style.parser.property;

public class Property {
	private final String id;
	
	private final String value;
	
	private final int line;
	
	public Property(String id, String value, int line) {
		this.id = id;
		this.value = value;
		this.line = line;
	}

	public String getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

	public int getLine() {
		return line;
	}
}
