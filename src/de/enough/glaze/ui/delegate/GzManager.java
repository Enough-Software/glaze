package de.enough.glaze.ui.delegate;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import de.enough.glaze.style.Style;
import de.enough.glaze.style.handler.StyleManager;

/**
 * An interface to extend {@link Manager} instances for the use in Glaze
 * 
 * @author Andre
 * 
 */
public interface GzManager extends GzExtent {

	/**
	 * Must call super.add(field) in an implementing manager and add(field) in
	 * the style manager of the manager
	 * 
	 * @param field
	 *            the field to add
	 */
	public void add(Field field);

	/**
	 * Must call super.add(field) in an implementing manager and add(field,
	 * style) on the style manager of the manager
	 * 
	 * @param field
	 *            the field to add
	 * @param style
	 *            the style for the field
	 */
	public void add(Field field, Style style);

	/**
	 * Must call super.addAll(fields) in an implementing manager and
	 * addAll(fields) on the style manager of the manager
	 * 
	 * @param field
	 *            the field to add
	 * @param style
	 *            the style for the field
	 */
	public void addAll(Field[] fields);

	/**
	 * Must call super.addAll(fields) in an implementing manager and
	 * addAll(fields, style) on the style manager of the manager
	 * 
	 * @param fields
	 *            the fields to add
	 * @param style
	 *            the style for the field
	 */
	public void addAll(Field[] fields, Style style);

	/**
	 * Must call super.insert(field, index) in an implementing manager and
	 * insert(field, index) on the style manager of the manager
	 * 
	 * @param field
	 *            the field to insert
	 * @param index
	 *            the index of the field
	 */
	public void insert(Field field, int index);

	/**
	 * Must call super.insert(field, index) in an implementing manager and
	 * insert(field, index, style) on the style manager of the manager
	 * 
	 * @param field
	 *            the field to insert
	 * @param index
	 *            the index of the field
	 * @param style
	 *            the style
	 */
	public void insert(Field field, int index, Style style);

	/**
	 * Must call super.delete(field) in an implementing manager and
	 * delete(field) on the style manager of the manager
	 * 
	 * @param field
	 *            the field to delete
	 */
	public void delete(Field field);

	/**
	 * Must call super.deleteAll() in an implementing manager and delete(field)
	 * on the style manager of the manager
	 * 
	 * @param field
	 *            the field to delete
	 * @param style
	 *            the style
	 */
	public void deleteAll();

	/**
	 * Must call super.deleteRange(start, count) in an implementing manager and
	 * deleteRange(start, count) on the style manager of the manager
	 * 
	 * @param start
	 *            the start index
	 * @param count
	 *            the count
	 */
	public void deleteRange(int start, int count);

	/**
	 * Must call super.replace(oldField, newField) in an implementing manager
	 * and replace(oldField, newField) on the style manager of the manager
	 * 
	 * @param oldField
	 *            the field to replace
	 * @param newField
	 *            the field to insert
	 */
	public void replace(Field oldField, Field newField);

	/**
	 * Must call super.replace(oldField, newField) in an implementing manager
	 * and replace(oldField, newField, style) on the style manager of the
	 * manager
	 * 
	 * @param oldField
	 *            the field to replace
	 * @param newField
	 *            the field to insert
	 * @param style
	 *            the style for the field to insert
	 */
	public void replace(Field oldField, Field newField, Style style);

	/**
	 * Must call super.sublayout(maxWidth, maxHeight) in an implementing manager
	 * 
	 * @param maxWidth
	 *            the maximum width
	 * @param maxHeight
	 *            the maximum height
	 */
	public void gz_sublayout(int maxWidth, int maxHeight);

	/**
	 * Must call super.paint(graphics) in an implementing manager
	 * 
	 * @param graphics
	 *            the {@link Graphics} instance
	 */
	public void gz_paint(Graphics graphics);

	/**
	 * Must call super.paintBackground(graphics) in an implementing manager
	 * 
	 * @param graphics
	 *            the {@link Graphics} instance
	 */
	public void gz_paintBackground(Graphics graphics);

	/**
	 * Must call super.updateLayout() in an implementing manager
	 */
	public void gz_updateLayout();

	/**
	 * Return the style manager of the implementing manager
	 * 
	 * @return the style manager
	 */
	public StyleManager getStyleManager();
}
