package de.enough.glaze.ui.container;

import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import de.enough.glaze.style.Style;
import de.enough.glaze.style.handler.FieldStyleHandler;
import de.enough.glaze.style.handler.FieldStyleManager;
import de.enough.glaze.ui.delegate.GzManager;

public class MainScreen extends net.rim.device.api.ui.container.MainScreen
		implements GzManager {

	private final FieldStyleManager styleManager;

	private final FieldStyleHandler handler;

	public MainScreen() {
		this(null);
	}

	public MainScreen(Style style) {
		this.styleManager = new FieldStyleManager(this);
		if (style != null) {
			this.handler = new FieldStyleHandler(getMainManager(), style);
			this.handler.applyPadding(Display.getWidth());
			this.handler.applyBorders();
			this.handler.applyBackgrounds();
		} else {
			this.handler = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Manager#add(net.rim.device.api.ui.Field)
	 */
	public void add(Field field) {
		super.add(field);
		this.styleManager.add(field);
	}

	public void add(Field field, Style style) {
		super.add(field);
		this.styleManager.add(field, style);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Manager#addAll(net.rim.device.api.ui.Field[])
	 */
	public void addAll(Field[] fields) {
		super.addAll(fields);
		this.styleManager.addAll(fields);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Manager#addAll(net.rim.device.api.ui.Field[])
	 */
	public void addAll(Field[] fields, Style style) {
		super.addAll(fields);
		this.styleManager.addAll(fields, style);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Manager#insert(net.rim.device.api.ui.Field,
	 * int)
	 */
	public void insert(Field field, int index) {
		super.insert(field, index);
		this.styleManager.insert(field, index);
	}

	public void insert(Field field, int index, Style style) {
		super.insert(field, index);
		this.styleManager.insert(field, index, style);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Manager#delete(net.rim.device.api.ui.Field)
	 */
	public void delete(Field field) {
		super.delete(field);
		this.styleManager.delete(field);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Manager#deleteAll()
	 */
	public void deleteAll() {
		super.deleteAll();
		this.styleManager.deleteAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Manager#deleteRange(int, int)
	 */
	public void deleteRange(int start, int count) {
		super.deleteRange(start, count);
		this.styleManager.deleteRange(start, count);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Manager#replace(net.rim.device.api.ui.Field,
	 * net.rim.device.api.ui.Field)
	 */
	public void replace(Field oldField, Field newField) {
		super.replace(oldField, newField);
		this.styleManager.replace(oldField, newField);
	}

	public void replace(Field oldField, Field newField, Style style) {
		super.replace(oldField, newField);
		this.styleManager.replace(oldField, newField, style);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.ui.container.GzManager#getHandlers()
	 */
	public FieldStyleManager getStyleManager() {
		return this.styleManager;
	}

	public void gz_setExtent(int width, int height) {
		setExtent(width, height);
	}

	public void gz_sublayout(int maxWidth, int maxHeight) {
		sublayout(maxWidth, maxHeight);
	}

	public void gz_paintChild(Graphics graphics, Field field) {
		paintChild(graphics, field);
	}

	public void gz_updateLayout() {
		updateLayout();
	}

}
