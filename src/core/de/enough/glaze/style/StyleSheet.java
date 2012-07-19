/*
 *
 * Copyright: (c) 2012 Enough Software GmbH & Co. KG
 *
 * Licensed under:
 * 1. MIT: http://www.opensource.org/licenses/mit-license.php
 * 2. Apache 2.0: http://opensource.org/licenses/apache2.0
 * 3. GPL with classpath exception: http://www.gnu.org/software/classpath/license.html
 *
 * You may not use this file except in compliance with these licenses.
 *
 */
 
package de.enough.glaze.style;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import net.rim.device.api.ui.UiApplication;
import de.enough.glaze.log.Log;
import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.definition.DefinitionCollection;
import de.enough.glaze.style.definition.StyleSheetDefinition;
import de.enough.glaze.style.definition.converter.BackgroundConverter;
import de.enough.glaze.style.definition.converter.BorderConverter;
import de.enough.glaze.style.definition.converter.Converter;
import de.enough.glaze.style.definition.converter.FontConverter;
import de.enough.glaze.style.definition.converter.StyleConverter;
import de.enough.glaze.style.definition.converter.utils.ConverterUtils;
import de.enough.glaze.style.extension.Extension;
import de.enough.glaze.style.extension.Processor;
import de.enough.glaze.style.parser.CssContentHandlerImpl;
import de.enough.glaze.style.parser.CssParser;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.property.background.GzBackground;
import de.enough.glaze.style.property.border.GzBorder;
import de.enough.glaze.style.property.font.GzFont;

/**
 * A class representing an abstraction of a CSS stylesheet. A stylesheet is
 * loaded and parsed through {@link #update(String, StyleSheetListener)} or
 * {@link #update(String)}. Various method provide access to backgrounds,
 * borders, fonts etc..
 * 
 * @author Andre
 * 
 */
public class StyleSheet {

	/**
	 * the instance
	 */
	private static StyleSheet INSTANCE;

	/**
	 * the url
	 */
	private Url url;

	/**
	 * the colors
	 */
	private final Hashtable colors;

	/**
	 * the backgrounds
	 */
	private final Hashtable backgrounds;

	/**
	 * the borders
	 */
	private final Hashtable borders;

	/**
	 * the fonts
	 */
	private final Hashtable fonts;

	/**
	 * the styles
	 */
	private final Hashtable styles;

	/**
	 * the extensions
	 */
	private final Vector extensions;

	/**
	 * the stylesheet listeners
	 */
	private final Vector listeners;

	/**
	 * the stylesheet definition
	 */
	private StyleSheetDefinition definition;

	/**
	 * Returns the instance
	 * 
	 * @return the instance
	 */
	public static StyleSheet getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new StyleSheet();
		}

		return INSTANCE;
	}

	/**
	 * Creates a new {@link StyleSheet} instance
	 */
	private StyleSheet() {
		this.colors = new Hashtable();
		this.backgrounds = new Hashtable();
		this.borders = new Hashtable();
		this.fonts = new Hashtable();
		this.styles = new Hashtable();
		this.extensions = new Vector();
		this.listeners = new Vector();
		this.definition = new StyleSheetDefinition();
	}

	/**
	 * Adds a {@link StyleSheetListener} instance to listen to loading events
	 * 
	 * @param listener
	 *            the {@link StyleSheetListener} instance
	 */
	public void addListener(StyleSheetListener listener) {
		if (!this.listeners.contains(listener)) {
			this.listeners.addElement(listener);
		}
	}

	/**
	 * Removes a {@link StyleSheetListener}
	 * 
	 * @param listener
	 *            the {@link StyleSheetListener} instance
	 */
	public void removeListener(StyleSheetListener listener) {
		if (this.listeners.contains(listener)) {
			this.listeners.removeElement(listener);
		}
	}

	/**
	 * Notify all listeners that the given url has been loaded
	 * 
	 * @param url
	 *            the url
	 */
	protected void notifyLoaded(String url) {
		synchronized (UiApplication.getEventLock()) {
			for (int index = 0; index < this.listeners.size(); index++) {
				StyleSheetListener listener = (StyleSheetListener) this.listeners
						.elementAt(index);
				listener.onLoaded(url);
			}
		}
	}

	/**
	 * Notify all listeners of the given {@link CssSyntaxError}
	 * 
	 * @param syntaxError
	 *            the {@link CssSyntaxError}
	 */
	protected void notifySyntaxError(CssSyntaxError syntaxError) {
		for (int index = 0; index < this.listeners.size(); index++) {
			StyleSheetListener listener = (StyleSheetListener) this.listeners
					.elementAt(index);
			listener.onSyntaxError(syntaxError);
		}
	}

	/**
	 * Notify all listeners of the given {@link Exception}
	 * 
	 * @param exception
	 *            the exception
	 */
	protected void notifyError(Exception exception) {
		for (int index = 0; index < this.listeners.size(); index++) {
			StyleSheetListener listener = (StyleSheetListener) this.listeners
					.elementAt(index);
			listener.onError(exception);
		}
	}

	/**
	 * Updates the stylesheet asynchronously with the given url and notifies the
	 * given listener of the result of the loading process
	 * 
	 * @param url
	 *            the url
	 * @param listener
	 *            the {@link StyleSheetListener}
	 */
	public void update(final String url, StyleSheetListener listener) {
		addListener(listener);

		new Thread() {
			public void run() {
				try {
					update(url);
					notifyLoaded(url);
				} catch (CssSyntaxError e) {
					notifySyntaxError(e);
				} catch (Exception e) {
					notifyError(e);
				}
			}
		}.start();
	}

	/**
	 * Updates the stylesheet by clearing the definitions and loading and
	 * parsing the given url.
	 * 
	 * @param url
	 *            the url
	 * @throws CssSyntaxError
	 *             if the syntax of the CSS is wrong
	 * @throws Exception
	 *             if an error occurs
	 */
	public synchronized void update(String url) throws CssSyntaxError,
			Exception {
		update(url, true);
	}

	/**
	 * Updates the stylesheet by clearing the definitions and loading and
	 * parsing the given url.
	 * 
	 * @param url
	 *            the url
	 * @param build
	 *            true if an the stylesheet should be build after the loading is
	 *            done otherwise false
	 * @throws CssSyntaxError
	 *             if the syntax of the CSS is wrong
	 * @throws Exception
	 *             if an error occurs
	 */
	public synchronized void update(String url, boolean build)
			throws CssSyntaxError, Exception {
		// load and parse the url
		this.url = new Url(url);
		// clear all definitions for an update
		this.definition.clear();
		// load and parse the url
		load(this.url.openStream(), build);
	}

	/**
	 * Extends the stylesheet asynchronously with the given url and notifies the
	 * given listener of the result of the loading process
	 * 
	 * @param url
	 *            the url
	 * @param listener
	 *            the {@link StyleSheetListener}
	 */
	public void extend(final String url, StyleSheetListener listener) {
		addListener(listener);

		new Thread() {
			public void run() {
				try {
					extend(url);
					notifyLoaded(url);
				} catch (CssSyntaxError e) {
					notifySyntaxError(e);
				} catch (Exception e) {
					notifyError(e);
				}
			}
		}.start();
	}

	/**
	 * Extends the stylesheet by loading and parsing the given url
	 * 
	 * @param url
	 *            the url
	 * @throws CssSyntaxError
	 *             if the syntax of the CSS is wrong
	 * @throws Exception
	 *             if an error occurs
	 */
	public synchronized void extend(String url) throws CssSyntaxError,
			Exception {
		extend(url, true);
	}

	/**
	 * Extends the stylesheet by loading and parsing the given url
	 * 
	 * @param url
	 *            the url
	 * @param build
	 *            true if an the stylesheet should be build after the loading is
	 *            done otherwise false
	 * @throws CssSyntaxError
	 *             if the syntax of the CSS is wrong
	 * @throws Exception
	 *             if an error occurs
	 */
	public synchronized void extend(String url, boolean build)
			throws CssSyntaxError, Exception {
		// load and parse the url
		this.url = new Url(url);
		// load and parse the url
		load(new Url(url).openStream(), build);
	}

	/**
	 * Loads and parses the data provided by the given {@link InputStream}
	 * instance.
	 * 
	 * @param stream
	 *            the stream
	 * @throws IOException
	 *             if the connection is interrupted
	 * @throws CssSyntaxError
	 *             if the syntax of the CSS is wrong
	 */
	protected void load(InputStream stream, boolean build) throws IOException,
			CssSyntaxError {
		// parse the css from the stream
		InputStreamReader reader = new InputStreamReader(stream);
		CssParser cssParser = new CssParser(reader);
		CssContentHandlerImpl cssContentHandler = new CssContentHandlerImpl(
				this);
		cssParser.setContentHandler(cssContentHandler);
		cssParser.parse();
		// clear the whole stylesheet
		clear();
		// build the stylesheet
		if (build) {
			build();
		}
	}

	/**
	 * Builds the stylesheet elements (styles, backgrounds etc.) from the
	 * current stylesheet definition. update() or extends should be run first.
	 * 
	 * @throws IOException
	 *             if the connection is interrupted
	 * @throws CssSyntaxError
	 *             if the syntax in the css is wrong
	 */
	public void build() throws IOException, CssSyntaxError {
		try {
			// convert and add all backgrounds to the stylesheet
			DefinitionCollection backgroundDefinitions = this.definition
					.getBackgroundDefinitions();
			for (int index = 0; index < backgroundDefinitions.size(); index++) {
				Definition definition = backgroundDefinitions
						.getDefinition(index);
				String definitionParentId = definition.getParentId();
				if (definitionParentId != null) {
					Definition parentDefinition = backgroundDefinitions
							.getDefinition(definitionParentId);
					definition.setParent(parentDefinition);
				}
				ConverterUtils.validate(definition, BackgroundConverter
						.getInstance().getIds());
				GzBackground background = (GzBackground) BackgroundConverter
						.getInstance().convert(definition);
				setBackground(definition.getId(), background);
			}

			// convert and add all borders to the stylesheet
			DefinitionCollection borderDefinitions = this.definition
					.getBorderDefinitions();
			for (int index = 0; index < borderDefinitions.size(); index++) {
				Definition definition = borderDefinitions.getDefinition(index);
				String definitionParentId = definition.getParentId();
				if (definitionParentId != null) {
					Definition parentDefinition = borderDefinitions
							.getDefinition(definitionParentId);
					definition.setParent(parentDefinition);
				}
				ConverterUtils.validate(definition, BorderConverter
						.getInstance().getIds());
				GzBorder border = (GzBorder) BorderConverter.getInstance()
						.convert(definition);
				setBorder(definition.getId(), border);
			}

			// convert and add all fonts to the stylesheet
			DefinitionCollection fontDefinitions = this.definition
					.getFontDefinitions();
			for (int index = 0; index < fontDefinitions.size(); index++) {
				Definition definition = fontDefinitions.getDefinition(index);
				String definitionParentId = definition.getParentId();
				if (definitionParentId != null) {
					Definition parentDefinition = fontDefinitions
							.getDefinition(definitionParentId);
					definition.setParent(parentDefinition);
				}
				ConverterUtils.validate(definition, FontConverter.getInstance()
						.getIds());
				GzFont font = (GzFont) FontConverter.getInstance().convert(
						definition);
				setFont(definition.getId(), font);
			}

			// convert and add all styles to the stylesheet
			DefinitionCollection styleDefinitions = this.definition
					.getStyleDefinitions();
			for (int index = 0; index < styleDefinitions.size(); index++) {
				Definition definition = styleDefinitions.getDefinition(index);
				String definitionParentId = definition.getParentId();
				if (definitionParentId != null) {
					Definition parentDefinition = styleDefinitions
							.getDefinition(definitionParentId);
					definition.setParent(parentDefinition);
				}
				Style style = (Style) StyleConverter.getInstance().convert(
						definition);

				String classId = definition.getClassId();
				// if the style is a style class ...
				if (classId != null) {
					Definition parentDefinition = definition.getParent();
					String parentId = parentDefinition.getId();
					// get the parent style and set the style as a class of it
					Style parentStyle = getStyle(parentId);
					parentStyle.setClass(classId, style);
					// otherwise ...
				} else {
					// simply add the style
					setStyle(definition.getId(), style);
				}
			}
		} catch (CssSyntaxError e) {
			Log.syntaxError(e);
			throw e;
		}
	}

	/**
	 * Finalizes the stylesheet by clearing the definition. This method should
	 * only be called if the stylesheet is in its final state for the given
	 * application.
	 */
	public void finalize() {
		this.definition.clear();
	}

	/**
	 * Sets the url.
	 * 
	 * @param url
	 *            the url
	 */
	protected void setUrl(Url url) {
		this.url = url;
	}

	/**
	 * Returns the url
	 * 
	 * @return the url
	 */
	public Url getUrl() {
		return this.url;
	}

	/**
	 * Returns the color with the given id
	 * 
	 * @param id
	 *            the id
	 * @return the color with the given id
	 */
	public Color getColor(String id) {
		return (Color) this.colors.get(id);
	}

	/**
	 * Stores the given color with the given id
	 * 
	 * @param id
	 *            the id
	 * @param color
	 *            the color
	 */
	public void setColor(String id, Color color) {
		this.colors.put(id, color);
	}

	/**
	 * Returns the border with the given id
	 * 
	 * @param id
	 *            the id
	 * @return the border with the given id
	 */
	public GzBorder getBorder(String id) {
		return (GzBorder) this.borders.get(id);
	}

	/**
	 * Stores the given border with the given id
	 * 
	 * @param id
	 *            the id
	 * @param border
	 *            the border
	 */
	public void setBorder(String id, GzBorder border) {
		this.borders.put(id, border);
	}

	/**
	 * Returns the background with the given id
	 * 
	 * @param id
	 *            the id
	 * @return the background with the given id
	 */
	public GzBackground getBackground(String id) {
		return (GzBackground) this.backgrounds.get(id);
	}

	/**
	 * Stores the given background with the given id
	 * 
	 * @param id
	 *            the id
	 * @param background
	 *            the background
	 */
	public void setBackground(String id, GzBackground background) {
		this.backgrounds.put(id, background);
	}

	/**
	 * Returns the font with the given id
	 * 
	 * @param id
	 *            the id
	 * @return the font with the given id
	 */
	public GzFont getFont(String id) {
		return (GzFont) this.fonts.get(id);
	}

	/**
	 * Stores the given font with the given id
	 * 
	 * @param id
	 *            the id
	 * @param font
	 *            the font
	 */
	public void setFont(String id, GzFont font) {
		this.fonts.put(id, font);
	}

	/**
	 * Returns the style with the given id
	 * 
	 * @param id
	 *            the id
	 * @return the style with the given id
	 */
	public Style getStyle(String id) {
		id = id.toLowerCase().trim();
		Style style = (Style) this.styles.get(id);
		if (style != null) {
			return style;
		} else {
			Log.warn("the style with '" + id
					+ "' could not be found, using empty style");
			return new Style(id);
		}
	}

	/**
	 * Stores the given style with the given id
	 * 
	 * @param id
	 *            the id
	 * @param style
	 *            the style
	 */
	public void setStyle(String id, Style style) {
		this.styles.put(id, style);
	}

	/**
	 * Creates and adds an extension with the given converter and processor
	 * 
	 * @param converter
	 *            the converter
	 * @param processor
	 *            the processor
	 */
	public void addExtension(Converter converter, Processor processor) {
		Extension extension = new Extension(converter, processor);
		addExtension(extension);
	}

	/**
	 * Adds the given extension
	 * 
	 * @param extension
	 *            the extension
	 */
	public void addExtension(Extension extension) {
		this.extensions.addElement(extension);
	}

	/**
	 * Returns all extensions as an enumeration
	 * 
	 * @return the extensions
	 */
	public Enumeration getExtensions() {
		return this.extensions.elements();
	}

	/**
	 * Returns the {@link StyleSheetDefinition} instance of this stylesheet
	 * 
	 * @return the {@link StyleSheetDefinition} instance
	 */
	public StyleSheetDefinition getDefinition() {
		return this.definition;
	}

	/**
	 * Clears the stylesheet
	 */
	public void clear() {
		this.backgrounds.clear();
		this.borders.clear();
		this.fonts.clear();
		this.styles.clear();
	}
}
