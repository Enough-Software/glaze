package de.enough.glaze.ui.container;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import de.enough.glaze.style.Style;
import de.enough.glaze.style.handler.StyleHandler;
import de.enough.glaze.style.handler.StyleManager;
import de.enough.glaze.ui.delegate.FieldDelegate;
import de.enough.glaze.ui.delegate.GzManager;
import de.enough.glaze.ui.delegate.ManagerDelegate;

public class FlowFieldManager extends
		net.rim.device.api.ui.container.FlowFieldManager implements GzManager {

	private final StyleManager styleManager;

	public FlowFieldManager() {
		this(0);
	}

	public FlowFieldManager(long style) {
		super(style);
		this.styleManager = new StyleManager(this);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.ui.delegate.GzManager#add(net.rim.device.api.ui.Field,
	 * de.enough.glaze.style.Style)
	 */
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
		for (int index = 0; index < fields.length; index++) {
			Field field = fields[index];
			add(field);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Manager#addAll(net.rim.device.api.ui.Field[])
	 */
	public void addAll(Field[] fields, Style style) {
		for (int index = 0; index < fields.length; index++) {
			Field field = fields[index];
			add(field, style);
		}
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.ui.delegate.GzManager#replace(net.rim.device.api.ui.Field
	 * , net.rim.device.api.ui.Field, de.enough.glaze.style.Style)
	 */
	public void replace(Field oldField, Field newField, Style style) {
		super.replace(oldField, newField);
		this.styleManager.replace(oldField, newField, style);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.ui.delegate.GzManager#apply(net.rim.device.api.ui.Field,
	 * de.enough.glaze.style.Style)
	 */
	public void apply(Field field, Style style) {
		StyleHandler styleHandler = this.styleManager.get(field);
		if (styleHandler != null) {
			styleHandler.setStyle(style);
			invalidate();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.ui.container.GzManager#getHandlers()
	 */
	public StyleManager getStyleManager() {
		return this.styleManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Manager#invalidate(int, int, int, int)
	 */
	protected void invalidate(int x, int y, int width, int height) {
		ManagerDelegate.invalidate(x, y, width, height, this);
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
	 * net.rim.device.api.ui.Field#paintBackground(net.rim.device.api.ui.Graphics
	 * )
	 */
	protected void paintBackground(Graphics graphics) {
		ManagerDelegate.paintBackground(graphics, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.rim.device.api.ui.container.VerticalFieldManager#subpaint(net.rim
	 * .device.api.ui.Graphics)
	 */
	protected void paint(Graphics graphics) {
		ManagerDelegate.paint(graphics, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.component.Field#getPreferredWidth()
	 */
	public int getPreferredWidth() {
		return FieldDelegate.getPreferredWidth(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.component.Field#getPreferredHeight()
	 */
	public int getPreferredHeight() {
		return FieldDelegate.getPreferredHeight(this);
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
	 * @see de.enough.glaze.ui.delegate.GzManager#gz_invalidate(int, int, int,
	 * int)
	 */
	public void gz_invalidate(int x, int y, int width, int height) {
		super.invalidate(x, y, width, height);
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
	 * de.enough.glaze.ui.delegate.GzManager#gz_paintBackground(net.rim.device
	 * .api.ui.Graphics)
	 */
	public void gz_paintBackground(Graphics graphics) {
		super.paintBackground(graphics);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.ui.container.GzManager#gz_paintChild(net.rim.device.api
	 * .ui.Graphics, net.rim.device.api.ui.Field)
	 */
	public void gz_paint(Graphics graphics) {
		super.paint(graphics);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.ui.container.GzManager#gz_updateLayout()
	 */
	public void gz_updateLayout() {
		super.updateLayout();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.ui.delegate.GzExtent#gz_getPreferredWidth()
	 */
	public int gz_getPreferredWidth() {
		return super.getPreferredWidth();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.ui.delegate.GzExtent#gz_getPreferredHeight()
	 */
	public int gz_getPreferredHeight() {
		return super.getPreferredHeight();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.ScrollView#onDisplay()
	 */
	protected void onDisplay() {
		super.onDisplay();
		this.styleManager.onDisplay();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.ScrollView#onUndisplay()
	 */
	protected void onUndisplay() {
		super.onUndisplay();
		this.styleManager.onUndisplay();
	}

	/*
	 * 5.0 workarounds & fixes
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Manager#onFocus(int)
	 */
	protected void onFocus(int direction) {
		super.onFocus(direction);
		invalidate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Manager#onUnfocus()
	 */
	protected void onUnfocus() {
		super.onUnfocus();
		invalidate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Manager#moveFocus(int, int, int)
	 */
	protected int moveFocus(int amount, int status, int time) {
		Field previousFocusedField = getFieldWithFocus();
		int result = super.moveFocus(amount, status, time);
		if (previousFocusedField != getFieldWithFocus()) {
			// invalidate to update the field styles
			invalidate();
		}
		return result;
	}
}
