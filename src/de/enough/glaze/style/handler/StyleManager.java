package de.enough.glaze.style.handler;

import java.util.Vector;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.XYRect;
import de.enough.glaze.log.Log;
import de.enough.glaze.style.Style;
import de.enough.glaze.style.StyleSheet;
import de.enough.glaze.style.StyleSheetListener;
import de.enough.glaze.style.parser.exception.CssSyntaxError;

/**
 * A management class managing {@link StyleHandler} instances for all fields of
 * a {@link Manager}. A {@link StyleManager} instance is used in a Glaze
 * implementation of {@link Manager} to provide {@link StyleHandler} instances
 * to handle all fields in a {@link Manager}. Methods used to add, remove,
 * insert and replace {@link StyleHandler} instances are similar to the methods
 * used in {@link Manager} instances to allow easy usage in Glaze
 * {@link Manager} implementations.
 * 
 * @author Andre
 * 
 */
public class StyleManager implements StyleSheetListener {

	/**
	 * the list of {@link StyleHandler} instances
	 */
	private final Vector list;

	/**
	 * the manager parenting this {@link StyleManager} instance
	 */
	private final Manager manager;

	/**
	 * the maximum width the parenting {@link Manager} uses to layout
	 */
	private int maxWidth;

	/**
	 * Constructs a new {@link StyleManager} instance
	 * 
	 * @param manager
	 *            the parenting {@link Manager}
	 */
	public StyleManager(Manager manager) {
		this.manager = manager;
		this.list = new Vector();
	}

	/**
	 * Adds a new {@link StyleHandler} instance for the given field
	 * 
	 * @param field
	 *            the field
	 */
	public void add(Field field) {
		add(new StyleHandler(field, null));
	}

	/**
	 * Adds the given {@link StyleHandler} instance
	 * 
	 * @param styleHandler
	 *            the {@link StyleHandler} instance
	 */
	private void add(StyleHandler styleHandler) {
		this.list.addElement(styleHandler);
	}

	/**
	 * Adds a new {@link StyleHandler} instance for the given field and style
	 * 
	 * @param field
	 *            the field
	 * @param style
	 *            the style
	 */
	public void add(Field field, Style style) {
		add(new StyleHandler(field, style));
	}

	/**
	 * Adds all the given fields. Uses {@link #add(Field)} internally.
	 * 
	 * @param fields
	 *            the fields
	 */
	public void addAll(Field[] fields) {
		for (int index = 0; index < fields.length; index++) {
			Field field = fields[index];
			add(field);
		}
	}

	/**
	 * Adds all the given fields. Uses {@link #add(Field,Style)} internally.
	 * 
	 * @param fields
	 *            the fields
	 * @param style
	 *            the style
	 */
	public void addAll(Field[] fields, Style style) {
		for (int index = 0; index < fields.length; index++) {
			Field field = fields[index];
			add(field, style);
		}
	}

	/**
	 * Insert a new {@link StyleHandler} instance for the given field at the
	 * given index
	 * 
	 * @param field
	 *            the field
	 * @param index
	 *            the index
	 */
	public void insert(Field field, int index) {
		insert(new StyleHandler(field, null), index);
	}

	/**
	 * Insert a new {@link StyleHandler} instance for the given field and style
	 * at the given index
	 * 
	 * @param field
	 *            the field
	 * @param index
	 *            the index
	 * @param style
	 *            the style
	 */
	public void insert(Field field, int index, Style style) {
		insert(new StyleHandler(field, style), index);
	}

	/**
	 * Inserts the given {@link StyleHandler} instance at the given index
	 * 
	 * @param styleHandler
	 *            the {@link StyleHandler} instance
	 * @param index
	 *            the index
	 */
	private void insert(StyleHandler styleHandler, int index) {
		this.list.insertElementAt(styleHandler, index);
	}

	/**
	 * Removes the {@link StyleHandler} instance at the given index
	 * 
	 * @param index
	 *            the index
	 */
	private void delete(int index) {
		this.list.removeElementAt(index);
	}

	/**
	 * Removes the {@link StyleHandler} instance for the given field
	 * 
	 * @param field
	 *            the field
	 */
	public void delete(Field field) {
		for (int index = 0; index < this.list.size(); index++) {
			StyleHandler fieldStyleHandler = (StyleHandler) this.list
					.elementAt(index);
			if (fieldStyleHandler.getField().equals(field)) {
				delete(index);
			}
		}

		throw new IllegalArgumentException(
				"no Stylehandler is already registered for field " + field);
	}

	/**
	 * Removes all {@link StyleHandler} instances
	 */
	public void deleteAll() {
		this.list.removeAllElements();
	}

	/**
	 * Removes all {@link StyleHandler} instances of the given range
	 * 
	 * @param start
	 *            the start
	 * @param count
	 *            the count
	 */
	public void deleteRange(int start, int count) {
		for (int index = start; index < start + count; index++) {
			delete(index);
		}
	}

	/**
	 * Deletes the {@link StyleHandler} for the given old field and inserts the
	 * given new field
	 * 
	 * @param oldField
	 *            the old field
	 * @param newField
	 *            the new field
	 */
	public void replace(Field oldField, Field newField) {
		int index = this.list.indexOf(oldField);
		delete(index);
		insert(newField, index);
	}

	/**
	 * Deletes the {@link StyleHandler} for the given old field and inserts the
	 * given new field with the given style
	 * 
	 * @param oldField
	 *            the old field
	 * @param newField
	 *            the new field
	 * @param style
	 *            the style
	 */
	public void replace(Field oldField, Field newField, Style style) {
		int index = this.list.indexOf(oldField);
		delete(index);
		insert(newField, index, style);
	}

	/**
	 * Returns the {@link StyleHandler} instance at the given index
	 * 
	 * @param index
	 *            the index
	 * @return the {@link StyleHandler} instance
	 */
	public StyleHandler get(int index) {
		return (StyleHandler) this.list.elementAt(index);
	}

	/**
	 * Returns the {@link StyleHandler} instance for the given field
	 * 
	 * @param field
	 *            the fied
	 * @return the {@link StyleHandler} instance
	 */
	public StyleHandler get(Field field) {
		for (int index = 0; index < this.list.size(); index++) {
			StyleHandler fieldStyleHandler = (StyleHandler) this.list
					.elementAt(index);
			if (fieldStyleHandler.getField().equals(field)) {
				return fieldStyleHandler;
			}
		}

		return null;
	}

	/**
	 * Returns the number of {@link StyleHandler} instances
	 * 
	 * @return the number of {@link StyleHandler} instances
	 */
	public int size() {
		return this.list.size();
	}

	/**
	 * Returns the maximum width the parenting {@link Manager} uses to layout.
	 * 
	 * @return the maximum width
	 */
	public int getMaxWidth() {
		return this.maxWidth;
	}

	public boolean layoutUpdate() {
		for (int index = 0; index < size(); index++) {
			StyleHandler styleHandler = get(index);
			if (styleHandler.isVisualStateChanged()) {
				styleHandler.updateVisualState();
				if (styleHandler.layoutUpdate()) {
					return true;
				}
			} 
		}
		
		return false;
	}
	

	boolean layouting;
	
	public boolean isLayouting() {
		return layouting;
	}
	
	public void setLayouting(boolean layouting) {
		this.layouting = layouting;
	}

	/**
	 * Sets the maximum width the parenting {@link Manager} uses to layout.
	 * 
	 * @param maxWidth
	 *            the maximum width
	 */
	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}

	/**
	 * Called by onDisplay() of the parenting {@link Manager}
	 */
	public void onDisplay() {
		Log.debug("display", this);
		// add this style manager as a listener
		StyleSheet.getInstance().addListener(this);
	}

	/**
	 * Called by onUndisplay() of the parenting {@link Manager}
	 */
	public void onUndisplay() {
		Log.debug("undisplay", this);
		// removes this style manager
		StyleSheet.getInstance().removeListener(this);
		// release all style handlers
		release();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.style.StyleSheetListener#onLoaded(java.lang.String)
	 */
	public void onLoaded(String url) {
		synchronized (UiApplication.getEventLock()) {
			// for all style handlers ...
			for (int index = 0; index < this.list.size(); index++) {
				StyleHandler styleHandler = (StyleHandler) this.list
						.elementAt(index);
				// get the current style
				Style style = styleHandler.getStyle();
				if (style != null) {
					// get the new style from the stylesheet
					Style newStyle = StyleSheet.getInstance().getStyle(
							style.getId());
					// set the new style in the style handler
					styleHandler.setStyle(newStyle);
				}
			}
		}

		// request a layout update on the parenting manager
		this.manager.setDirty(true);
		this.manager.invalidate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.style.StyleSheetListener#onSyntaxError(de.enough.glaze
	 * .style.parser.exception.CssSyntaxError)
	 */
	public void onSyntaxError(CssSyntaxError syntaxError) {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.style.StyleSheetListener#onError(java.lang.Exception)
	 */
	public void onError(Exception e) {
		// do nothing
	}

	/**
	 * Releases all {@link StyleHandler} instances
	 */
	private void release() {
		for (int index = 0; index < this.list.size(); index++) {
			StyleHandler handler = get(index);
			handler.release();
		}
	}
}
