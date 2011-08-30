package de.enough.glaze.style.handler;

import java.util.Vector;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.UiApplication;
import de.enough.glaze.log.Log;
import de.enough.glaze.style.Style;
import de.enough.glaze.style.StyleSheet;
import de.enough.glaze.style.StyleSheetListener;
import de.enough.glaze.style.parser.exception.CssSyntaxError;

public class FieldStyleManager implements StyleSheetListener {

	private final Vector list;

	private final Manager manager;

	public FieldStyleManager(Manager manager) {
		this.manager = manager;
		this.list = new Vector();
	}

	public void add(Field field) {
		add(new FieldStyleHandler(field, null));
	}

	private void add(FieldStyleHandler fieldStyleHandler) {
		this.list.addElement(fieldStyleHandler);
	}

	public void add(Field field, Style style) {
		add(new FieldStyleHandler(field, style));
	}

	public void addAll(Field[] fields) {
		for (int index = 0; index < fields.length; index++) {
			Field field = fields[index];
			add(field);
		}
	}

	public void addAll(Field[] fields, Style style) {
		for (int index = 0; index < fields.length; index++) {
			Field field = fields[index];
			add(field, style);
		}
	}

	public void insert(Field field, int index) {
		insert(new FieldStyleHandler(field, null), index);
	}

	public void insert(Field field, int index, Style style) {
		insert(new FieldStyleHandler(field, style), index);
	}

	private void insert(FieldStyleHandler fieldStyleHandler, int index) {
		this.list.insertElementAt(fieldStyleHandler, index);
	}

	private void delete(int index) {
		this.list.removeElementAt(index);
	}

	public void delete(Field field) {
		for (int index = 0; index < this.list.size(); index++) {
			FieldStyleHandler fieldStyleHandler = (FieldStyleHandler) this.list
					.elementAt(index);
			if (fieldStyleHandler.getField().equals(field)) {
				delete(index);
			}
		}

		throw new IllegalArgumentException("a field style handler for field "
				+ field + " is already registered");
	}

	public void deleteAll() {
		this.list.removeAllElements();
	}

	public void deleteRange(int start, int count) {
		for (int index = start; index < start + count; index++) {
			delete(index);
		}
	}

	public void replace(Field oldField, Field newField) {
		int index = this.list.indexOf(oldField);
		delete(index);
		insert(newField, index);
	}

	public void replace(Field oldField, Field newField, Style style) {
		int index = this.list.indexOf(oldField);
		delete(index);
		insert(newField, index, style);
	}

	public FieldStyleHandler get(int index) {
		return (FieldStyleHandler) this.list.elementAt(index);
	}

	public FieldStyleHandler get(Field field) {
		for (int index = 0; index < this.list.size(); index++) {
			FieldStyleHandler fieldStyleHandler = (FieldStyleHandler) this.list
					.elementAt(index);
			if (fieldStyleHandler.getField().equals(field)) {
				return fieldStyleHandler;
			}
		}

		return null;
	}

	public void onDisplay() {
		Log.d("display", this);
		StyleSheet.getInstance().addListener(this);
	}

	public void onUndisplay() {
		Log.d("undisplay", this);
		StyleSheet.getInstance().removeListener(this);
		release();
	}

	public void onLoaded(String url) {
		synchronized (UiApplication.getEventLock()) {
			for (int index = 0; index < this.list.size(); index++) {
				FieldStyleHandler fieldStyleHandler = (FieldStyleHandler) this.list
						.elementAt(index);
				Style style = fieldStyleHandler.getStyle();
				if (style != null) {
					Style newStyle = StyleSheet.getInstance().getStyle(
							style.getId());
					fieldStyleHandler.setStyle(newStyle);
				}
			}
		}

		this.manager.setDirty(true);
		this.manager.invalidate();
	}

	public void onSyntaxError(CssSyntaxError syntaxError) {
		// do nothing
	}

	public void onError(Exception e) {
		// do nothing
	}

	private void release() {
		for (int index = 0; index < this.list.size(); index++) {
			FieldStyleHandler handler = get(index);
			handler.release();
		}
	}
}
