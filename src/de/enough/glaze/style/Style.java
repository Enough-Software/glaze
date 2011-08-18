package de.enough.glaze.style;

import java.util.Enumeration;
import java.util.Hashtable;

import net.rim.device.api.ui.Field;
import de.enough.glaze.style.background.GzBackground;
import de.enough.glaze.style.border.GzBorder;
import de.enough.glaze.style.definition.Definable;
import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.extension.Extension;
import de.enough.glaze.style.font.GzFont;

public class Style implements Definable {

	private static final String FOCUSED = "focused";

	private static final String ACTIVE = "active";

	private static final String DISABLED = "disabled";

	private static final String DISABLED_FOCUSED = "disabled_focused";
	
	private Margin margin;

	private Padding padding;

	private GzBackground background;

	private GzFont font;

	private GzBorder border;
	
	private final Hashtable extensions;

	private Style baseStyle;

	private Definition definition;

	private Style focusedStyle;

	private Style activeStyle;

	private Style disabledStyle;

	private Style disabledFocusedStyle;
	
	public Style() {
		this.extensions = new Hashtable();
	}
	
	public void setBaseStyle(Style baseStyle) {
		this.baseStyle = baseStyle;
	}

	public Style getBaseStyle() {
		return this.baseStyle;
	}

	public void setClass(String id, Style style) {
		if (FOCUSED.equals(id)) {
			this.focusedStyle = style;
		} else if (ACTIVE.equals(id)) {
			this.activeStyle = style;
		} else if (DISABLED.equals(id)) {
			this.disabledStyle = style;
		} else if (DISABLED_FOCUSED.equals(id)) {
			this.activeStyle = style;
		} else {
			throw new IllegalArgumentException(id
					+ " is not a valid style class");
		}
	}

	public static boolean isClass(String id) {
		return (FOCUSED.equals(id) || ACTIVE.equals(id)
				|| DISABLED.equals(id) || DISABLED_FOCUSED
				.equals(id));
	}

	public Style getStyle(int visualState) {
		if(this.baseStyle != null) {
			return getStyle(this.baseStyle, visualState);
		} else {
			return getStyle(this, visualState);
		}
	}
	
	private Style getStyle(Style style, int visualState) {
		if (visualState == Field.VISUAL_STATE_NORMAL) {
			return style;
		} else if (visualState == Field.VISUAL_STATE_FOCUS) {
			if(style.focusedStyle != null) {
				return style.focusedStyle;
			} else {
				return style;
			}
		} else if (visualState == Field.VISUAL_STATE_ACTIVE) {
			if(style.activeStyle != null) {
				return style.activeStyle;
			} else {
				if(style.focusedStyle != null) {
					return style.focusedStyle;
				} else {
					return style;
				}
			}
		} else if (visualState == Field.VISUAL_STATE_DISABLED) {
			if(style.disabledStyle != null) {
				return style.disabledStyle;
			} else {
				return style;
			}
		} else if (visualState == Field.VISUAL_STATE_DISABLED_FOCUS) {
			if(style.disabledFocusedStyle != null) {
				return style.disabledFocusedStyle;
			} else {
				return style;
			}
		} else {
			throw new IllegalArgumentException(visualState
					+ " is not a valid visual state");
		}
	}

	public Margin getMargin() {
		return margin;
	}

	public void setMargin(Margin margin) {
		this.margin = margin;
	}

	public Padding getPadding() {
		return padding;
	}

	public void setPadding(Padding padding) {
		this.padding = padding;
	}

	public GzBackground getBackground() {
		return background;
	}

	public void setBackground(GzBackground background) {
		this.background = background;
	}

	public GzFont getFont() {
		return font;
	}

	public void setFont(GzFont font) {
		this.font = font;
	}

	public GzBorder getBorder() {
		return border;
	}

	public void setBorder(GzBorder border) {
		this.border = border;
	}
	
	public void addExtension(Extension extension, Object object) {
		this.extensions.put(extension, object);
	}
	
	public Enumeration getExtensions() {
		return this.extensions.keys();
	}
	
	public Object getExtensionData(Extension extension) {
		return this.extensions.get(extension);
	}
	
	public void setDefinition(Definition definition) {
		this.definition = definition;
	}

	public Definition getDefinition() {
		return this.definition;
	}
	
	public void finalize() {
		this.definition = null;
	}
}
