package de.enough.glaze.style.parser.attribute;

public class MarginAttributeParser extends DimensionAttributeParser {

	/* (non-Javadoc)
	 * @see de.enough.glaze.style.parser.attribute.AttributeParser#getValidCounts()
	 */
	protected int[] getValidCounts() {
		return new int[]{1,2,3,4};
	}
	
}
