package de.enough.glaze.style;

import java.util.Hashtable;

import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.Border;

public class StyleSheet {
	
	private static StyleSheet INSTANCE;
	
	private Hashtable colors;
	
	private Hashtable backgrounds;
	
	private Hashtable borders;
	
	private Hashtable fonts;
	
	private Hashtable styles;
	
	public static StyleSheet getInstance() {
		if(INSTANCE == null) {
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
	
	public Color getColor(String id) {
		return (Color)this.colors.get(id);
	}
	
	public void addColor(String id, Color color) {
		this.colors.put(id, color);
	}
	
	public Border getBorder(String id) {
		return (Border)this.borders.get(id);
	}
	
	public Background getBackground(String id) {
		return (Background)this.backgrounds.get(id);
	}
	
	public Font getFont(String id) {
		return (Font)this.fonts.get(id);
	}
	
	public void addStyle(String id, Style style) {
		this.styles.put(id, style);
	}
	
	public Style getStyle(String id) {
		return (Style)this.styles.get(id);
	}
}
