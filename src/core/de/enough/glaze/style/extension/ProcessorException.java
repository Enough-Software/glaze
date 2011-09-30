package de.enough.glaze.style.extension;

import de.enough.glaze.ui.component.Field;

/**
 * A processor exception to be thrown if the given data of {@link Processor} is
 * not applicable to a {@link Field}.
 * 
 * @author Andre
 * 
 */
public class ProcessorException extends Exception {

	/**
	 * Constructs a new {@link ProcessorException} instance
	 * 
	 * @param message
	 *            the message
	 */
	public ProcessorException(String message) {
		super(message);
	}
}
