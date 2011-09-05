package de.enough.glaze.style.extension;

import de.enough.glaze.style.definition.converter.Converter;
import net.rim.device.api.ui.Field;

/**
 * A Processor interface. Processor instances are used to apply a data object
 * created through a {@link Converter} to a field. Used in stylesheet
 * extensions.
 * 
 * @author Andre
 * 
 */
public interface Processor {
	/**
	 * Applies the given data to the given field
	 * 
	 * @param field
	 *            the field
	 * @param data
	 *            the data
	 */
	public void apply(Field field, Object data);
}
