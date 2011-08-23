package de.enough.glaze.style.extension;

import de.enough.glaze.style.definition.converter.Converter;

/**
 * An stylesheet extension
 * @author Andre
 *
 */
public class Extension {
	
	private final Converter converter;

	private final Processor processor;
	
	public Extension(Converter converter, Processor processor) {
		this.converter = converter;
		this.processor = processor;
	}

	public Converter getConverter() {
		return converter;
	}

	public Processor getProcessor() {
		return processor;
	}

}
