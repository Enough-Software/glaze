package de.enough.glaze.style.definition.converter;

import de.enough.glaze.style.Dimension;
import de.enough.glaze.style.Margin;
import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.DimensionPropertyParser;
import de.enough.glaze.style.parser.property.Property;

/**
 * Converts a given definition to a margin
 * 
 * @author Andre
 * 
 */
public class MarginConverter implements Converter {

	/**
	 * the instance
	 */
	private static MarginConverter INSTANCE;

	/**
	 * Returns the instance
	 * 
	 * @return the instance
	 */
	public static MarginConverter getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MarginConverter();
		}

		return INSTANCE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.style.definition.converter.Converter#getIds()
	 */
	public String[] getIds() {
		return new String[] { "margin", "margin-left", "margin-top",
				"margin-right", "margin-bottom" };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.style.definition.converter.Converter#convert(de.enough
	 * .glaze.style.definition.Definition)
	 */
	public Object convert(Definition definition) throws CssSyntaxError {
		Margin margin = new Margin();
		
		if (!definition.hasProperties(this)) {
			return margin;
		}

		Property marginProp = definition.getProperty("margin");
		Property marginLeftProp = definition.getProperty("margin-left");
		Property marginTopProp = definition.getProperty("margin-top");
		Property marginRightProp = definition.getProperty("margin-right");
		Property marginBottomProp = definition.getProperty("margin-bottom");

		if (marginProp != null) {
			Object result = DimensionPropertyParser.getInstance().parse(
					marginProp);
			if (result instanceof Dimension) {
				Dimension dimension = (Dimension) result;
				margin.set(dimension);
			} else if (result instanceof Dimension[]) {
				Dimension[] dimensions = (Dimension[]) result;
				switch (dimensions.length) {
				case 2:
					margin.set(dimensions[0], dimensions[1]);
					break;
				case 3:
					margin.set(dimensions[0], dimensions[1], dimensions[2]);
					break;
				case 4:
					margin.set(dimensions[0], dimensions[1], dimensions[2],
							dimensions[3]);
					break;
				default:
					throw new CssSyntaxError("must be 1,2,3 or 4 dimensions",
							marginProp);
				}
			}
		}

		if (marginLeftProp != null) {
			Object result = (Dimension) DimensionPropertyParser.getInstance()
					.parse(marginLeftProp);
			if (result instanceof Dimension) {
				Dimension dimension = (Dimension) result;
				margin.setLeft(dimension);
			} else {
				throw new CssSyntaxError("must be a single dimension",
						marginLeftProp);
			}
		}

		if (marginTopProp != null) {
			Object result = (Dimension) DimensionPropertyParser.getInstance()
					.parse(marginTopProp);
			if (result instanceof Dimension) {
				Dimension dimension = (Dimension) result;
				margin.setTop(dimension);
			} else {
				throw new CssSyntaxError("must be a single dimension",
						marginTopProp);
			}
		}

		if (marginRightProp != null) {
			Object result = (Dimension) DimensionPropertyParser.getInstance()
					.parse(marginRightProp);
			if (result instanceof Dimension) {
				Dimension dimension = (Dimension) result;
				margin.setRight(dimension);
			} else {
				throw new CssSyntaxError("must be a single dimension",
						marginRightProp);
			}
		}

		if (marginBottomProp != null) {
			Object result = (Dimension) DimensionPropertyParser.getInstance()
					.parse(marginBottomProp);
			if (result instanceof Dimension) {
				Dimension dimension = (Dimension) result;
				margin.setBottom(dimension);
			} else {
				throw new CssSyntaxError("must be a single dimension",
						marginBottomProp);
			}
		}

		return margin;
	}

}