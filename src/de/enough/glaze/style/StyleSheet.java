package de.enough.glaze.style;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import de.enough.glaze.log.Log;
import de.enough.glaze.style.definition.StyleSheetDefinition;
import de.enough.glaze.style.definition.converter.Converter;
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
 * loaded and parsed through {@link #load(String, StyleSheetListener)} or
 * {@link #load(String)}. Various method provide access to backgrounds, borders,
 * fonts etc..
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
	private void notifyLoaded(String url) {
		for (int index = 0; index < this.listeners.size(); index++) {
			StyleSheetListener listener = (StyleSheetListener) this.listeners
					.elementAt(index);
			listener.onLoaded(url);
		}
	}

	/**
	 * Notify all listeners of the given {@link CssSyntaxError}
	 * 
	 * @param syntaxError
	 *            the {@link CssSyntaxError}
	 */
	private void notifySyntaxError(CssSyntaxError syntaxError) {
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
	private void notifyError(Exception exception) {
		for (int index = 0; index < this.listeners.size(); index++) {
			StyleSheetListener listener = (StyleSheetListener) this.listeners
					.elementAt(index);
			listener.onError(exception);
		}
	}

	/**
	 * Loads and parses the given url asynchronously and notifies the given
	 * listener of the result of the loading process
	 * 
	 * @param url
	 *            the url
	 * @param listener
	 *            the {@link StyleSheetListener}
	 */
	public void load(final String url, StyleSheetListener listener) {
		addListener(listener);

		new Thread() {
			public void run() {
				try {
					InputStream stream = new URL(url).openStream();
					load(stream);
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
	 * Loads and parses the given url
	 * 
	 * @param url
	 *            the url
	 * @throws CssSyntaxError
	 *             if the syntax of the CSS is wrong
	 * @throws Exception
	 *             if an error occurs
	 */
	public void load(final String url) throws CssSyntaxError, Exception {
		synchronized (this) {
			try {
				InputStream stream = new URL(url).openStream();
				load(stream);
				notifyLoaded(url);
			} catch (CssSyntaxError e) {
				notifySyntaxError(e);
				throw e;
			} catch (Exception e) {
				notifyError(e);
				throw e;
			}
		}
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
	private void load(InputStream stream) throws IOException, CssSyntaxError {
		InputStreamReader reader = new InputStreamReader(stream);
		CssParser cssParser = new CssParser(reader);
		CssContentHandlerImpl cssContentHandler = new CssContentHandlerImpl(
				StyleSheet.getInstance());
		cssParser.setContentHandler(cssContentHandler);
		cssParser.parse();
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
	public void addColor(String id, Color color) {
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
	public void addBorder(String id, GzBorder border) {
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
	public void addBackground(String id, GzBackground background) {
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
	public void addFont(String id, GzFont font) {
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
	public void addStyle(String id, Style style) {
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
