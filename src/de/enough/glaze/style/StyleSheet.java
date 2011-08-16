package de.enough.glaze.style;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;

import de.enough.glaze.style.background.GzBackground;
import de.enough.glaze.style.border.GzBorder;
import de.enough.glaze.style.font.GzFont;
import de.enough.glaze.style.parser.CssContentHandlerImpl;
import de.enough.glaze.style.parser.CssParser;
import de.enough.glaze.style.parser.exception.CssSyntaxError;

public class StyleSheet {

	private static StyleSheet INSTANCE;

	private Hashtable colors;

	private Hashtable backgrounds;

	private Hashtable borders;

	private Hashtable fonts;

	private Hashtable styles;

	public static StyleSheet getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new StyleSheet();
		}

		return INSTANCE;
	}

	private StyleSheet() {
		this.colors = new Hashtable();
		this.backgrounds = new Hashtable();
		this.borders = new Hashtable();
		this.fonts = new Hashtable();
		this.styles = new Hashtable();
	}

	public void load(String url) throws IOException, CssSyntaxError {
		InputStream stream = getClass().getResourceAsStream(url);
		load(stream);
	}

	public void load(InputStream stream) throws IOException, CssSyntaxError {
		InputStreamReader reader = new InputStreamReader(stream);
		CssParser cssParser = new CssParser(reader);
		CssContentHandlerImpl cssContentHandler = new CssContentHandlerImpl(
				StyleSheet.getInstance());
		cssParser.setContentHandler(cssContentHandler);
		cssParser.parse();
	}

	public Color getColor(String id) {
		return (Color) this.colors.get(id);
	}

	public void addColor(String id, Color color) {
		this.colors.put(id, color);
	}

	public GzBorder getBorder(String id) {
		return (GzBorder) this.borders.get(id);
	}

	public void addBorder(String id, GzBorder border) {
		this.borders.put(id, border);
	}

	public GzBackground getBackground(String id) {
		return (GzBackground) this.backgrounds.get(id);
	}

	public void addBackground(String id, GzBackground background) {
		this.backgrounds.put(id, background);
	}

	public GzFont getFont(String id) {
		return (GzFont) this.fonts.get(id);
	}

	public void addFont(String id, GzFont font) {
		this.fonts.put(id, font);
	}

	public void addStyle(String id, Style style) {
		this.styles.put(id, style);
	}

	public Style getStyle(String id) {
		return (Style) this.styles.get(id);
	}
}
