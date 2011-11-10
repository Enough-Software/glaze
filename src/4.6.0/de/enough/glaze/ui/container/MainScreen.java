package de.enough.glaze.ui.container;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import de.enough.glaze.style.Style;
import de.enough.glaze.style.handler.StyleHandler;
import de.enough.glaze.style.handler.StyleManager;
import de.enough.glaze.ui.delegate.FieldDelegate;
import de.enough.glaze.ui.delegate.GzManager;
import de.enough.glaze.ui.delegate.GzScreen;
import de.enough.glaze.ui.delegate.ManagerDelegate;

public class MainScreen extends net.rim.device.api.ui.container.MainScreen
		implements GzScreen {

	private final StyleManager styleManager;

	private final VerticalFieldManager fieldManager;

	/**
	 * Constructs a new {@link MainScreen} instance
	 */
	public MainScreen() {
		this(null);
	}

	/**
	 * Constructs a new {@link MainScreen} instance
	 * 
	 * @param style
	 *            the style
	 */
	public MainScreen(long style) {
		this(style, null);
	}

	/**
	 * Constructs a new {@link MainScreen} instance
	 * 
	 * @param screenStyle
	 *            the screen style
	 */
	public MainScreen(Style screenStyle) {
		super();
		this.fieldManager = new VerticalFieldManager(Field.USE_ALL_WIDTH
				| Manager.NO_VERTICAL_SCROLL | Manager.NO_VERTICAL_SCROLL);
		super.add(this.fieldManager);
		this.styleManager = new StyleManager(getMainManager());
		this.styleManager.add(getMainManager(), screenStyle);
	}

	/**
	 * Constructs a new {@link MainScreen} instance
	 * 
	 * @param style
	 *            the style
	 * @param screenStyle
	 *            the screen style
	 */
	public MainScreen(long style, Style screenStyle) {
		super(style);
		this.fieldManager = new VerticalFieldManager(Field.USE_ALL_WIDTH
				| Manager.NO_VERTICAL_SCROLL | Manager.NO_VERTICAL_SCROLL);
		super.add(this.fieldManager);
		this.styleManager = new StyleManager(getMainManager());
		this.styleManager.add(getMainManager(), screenStyle);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.ui.delegate.GzScreen#getFieldManager()
	 */
	public GzManager getFieldManager() {
		return this.fieldManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Manager#add(net.rim.device.api.ui.Field)
	 */
	public void add(Field field) {
		this.fieldManager.add(field);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.ui.delegate.GzManager#add(net.rim.device.api.ui.Field,
	 * de.enough.glaze.style.Style)
	 */
	public void add(Field field, Style style) {
		this.fieldManager.add(field, style);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Manager#addAll(net.rim.device.api.ui.Field[])
	 */
	public void addAll(Field[] fields) {
		this.fieldManager.addAll(fields);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Manager#addAll(net.rim.device.api.ui.Field[])
	 */
	public void addAll(Field[] fields, Style style) {
		this.fieldManager.addAll(fields, style);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Manager#insert(net.rim.device.api.ui.Field,
	 * int)
	 */
	public void insert(Field field, int index) {
		this.fieldManager.insert(field, index);
	}

	public void insert(Field field, int index, Style style) {
		this.fieldManager.insert(field, index, style);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Manager#delete(net.rim.device.api.ui.Field)
	 */
	public void delete(Field field) {
		this.fieldManager.delete(field);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Manager#deleteAll()
	 */
	public void deleteAll() {
		this.fieldManager.deleteAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Manager#deleteRange(int, int)
	 */
	public void deleteRange(int start, int count) {
		this.fieldManager.deleteRange(start, count);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Manager#replace(net.rim.device.api.ui.Field,
	 * net.rim.device.api.ui.Field)
	 */
	public void replace(Field oldField, Field newField) {
		this.fieldManager.replace(oldField, newField);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.ui.delegate.GzManager#replace(net.rim.device.api.ui.Field
	 * , net.rim.device.api.ui.Field, de.enough.glaze.style.Style)
	 */
	public void replace(Field oldField, Field newField, Style style) {
		this.fieldManager.replace(oldField, newField, style);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.ui.delegate.GzManager#apply(net.rim.device.api.ui.Field,
	 * de.enough.glaze.style.Style)
	 */
	public void apply(Field field, Style style) {
		StyleHandler styleHandler = this.fieldManager.getStyleManager().get(
				field);
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
	 * @see net.rim.device.api.ui.Manager#invalidate(int, int, int, int)
	 */
	public void invalidate(int x, int y, int width, int height) {
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
	 * @see net.rim.device.api.ui.Screen#paint(net.rim.device.api.ui.Graphics)
	 */
	protected void paint(Graphics graphics) {
		ManagerDelegate.paint(graphics, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.ui.delegate.GzExtent#gz_setExtent(int, int)
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
	 * @see de.enough.glaze.ui.delegate.GzManager#gz_sublayout(int, int)
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
	 * de.enough.glaze.ui.delegate.GzManager#gz_paint(net.rim.device.api.ui.
	 * Graphics)
	 */
	public void gz_paint(Graphics graphics) {
		super.paint(graphics);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.ui.delegate.GzManager#gz_updateLayout()
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
}
