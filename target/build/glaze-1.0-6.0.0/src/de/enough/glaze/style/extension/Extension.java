package de.enough.glaze.style.extension;

import de.enough.glaze.style.definition.converter.Converter;

/**
 * An extension holding a {@link Converter} and a {@link Processor}
 * 
 * @author Andre
 * 
 */
public class Extension {

	/**
	 * the converter
	 */
	private final Converter converter;

	/**
	 * the processor
	 */
	private final Processor processor;

	/**
	 * Constructs a new {@link Extension} instance
	 * 
	 * @param converter
	 *            the converter
	 * @param processor
	 *            the processor
	 */
	public Extension(Converter converter, Processor processor) {
		this.converter = converter;
		this.processor = processor;
	}

	/**
	 * Returns the converter
	 * 
	 * @return the converter
	 */
	public Converter getConverter() {
		return converter;
	}

	/**
	 * Returns the processor
	 * 
	 * @return the processor
	 */
	public Processor getProcessor() {
		return processor;
	}

}
