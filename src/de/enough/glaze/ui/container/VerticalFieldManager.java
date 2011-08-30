package de.enough.glaze.ui.container;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import de.enough.glaze.log.Log;
import de.enough.glaze.style.Style;
import de.enough.glaze.style.handler.FieldStyleManager;
import de.enough.glaze.ui.delegate.GzManager;
import de.enough.glaze.ui.delegate.ManagerDelegate;

public class VerticalFieldManager extends
		net.rim.device.api.ui.container.VerticalFieldManager implements
		GzManager {

	private final FieldStyleManager styleManager;

	public VerticalFieldManager() {
		this(0);
	}

	public VerticalFieldManager(long style) {
		super(style);
		this.styleManager = new FieldStyleManager(this);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.container.VerticalFieldManager#sublayout(int,
	 * int)
	 */
	protected void sublayout(int maxWidth, int maxHeight) {
		ManagerDelegate.sublayout(maxWidth, maxHeight, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.rim.device.api.ui.container.VerticalFieldManager#subpaint(net.rim
	 * .device.api.ui.Graphics)
	 */
	protected void subpaint(Graphics graphics) {
		ManagerDelegate.subpaint(graphics, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.ui.container.GzManager#gz_setExtent(int, int)
	 */
	public void gz_setExtent(int width, int height) {
		super.setExtent(width, height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.ui.container.GzManager#gz_sublayout(int, int)
	 */
	public void gz_sublayout(int maxWidth, int maxHeight) {
		super.sublayout(maxWidth, maxHeight);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.ui.container.GzManager#gz_paintChild(net.rim.device.api
	 * .ui.Graphics, net.rim.device.api.ui.Field)
	 */
	public void gz_paintChild(Graphics graphics, Field field) {
		super.paintChild(graphics, field);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.ui.container.GzManager#gz_updateLayout()
	 */
	public void gz_updateLayout() {
		super.updateLayout();
	}
	
	/* (non-Javadoc)
	 * @see net.rim.device.api.ui.ScrollView#onDisplay()
	 */
	protected void onDisplay() {
		super.onDisplay();
		this.styleManager.onDisplay();
	}

	/* (non-Javadoc)
	 * @see net.rim.device.api.ui.ScrollView#onUndisplay()
	 */
	protected void onUndisplay() {
		super.onUndisplay();
		this.styleManager.onUndisplay();
	}
}
