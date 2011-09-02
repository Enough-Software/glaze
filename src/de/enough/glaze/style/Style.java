package de.enough.glaze.style;

import java.util.Enumeration;
import java.util.Hashtable;

import net.rim.device.api.ui.Field;
import de.enough.glaze.style.extension.Extension;
import de.enough.glaze.style.property.Visibility;
import de.enough.glaze.style.property.background.GzBackground;
import de.enough.glaze.style.property.border.GzBorder;
import de.enough.glaze.style.property.font.GzFont;

public class Style {

	public static Style id(String id) {
		return StyleSheet.getInstance().getStyle(id);
	}

	private static final String FOCUS = "focus";

	private static final String ACTIVE = "active";

	private static final String DISABLED = "disabled";

	private static final String DISABLED_FOCUSED = "disabled_focused";

	private final String id;

	private Margin margin;

	private Padding padding;

	private GzBackground background;

	private GzFont font;

	private GzBorder border;

	private Dimension minWidth;

	private Dimension width;

	private Dimension maxWidth;

	private Dimension minHeight;

	private Dimension height;

	private Dimension maxHeight;

	private int visibility;

	private final Hashtable extensions;

	private Style parentStyle;

	private Style focusStyle;

	private Style activeStyle;

	private Style disabledStyle;

	private Style disabledFocusedStyle;

	public Style(String id) {
		this.id = id;
		this.visibility = Visibility.VISIBLE;
		this.extensions = new Hashtable();
	}

	public String getId() {
		if (this.parentStyle != null) {
			return this.parentStyle.getId();
		} else {
			return this.id;
		}
	}

	public void setParentStyle(Style parentStyle) {
		this.parentStyle = parentStyle;
	}

	public Style getParentStyle() {
		return this.parentStyle;
	}

	public void setClass(String id, Style style) {
		if (FOCUS.equals(id)) {
			this.focusStyle = style;
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

	public static boolean isValidClass(String id) {
		return (FOCUS.equals(id) || ACTIVE.equals(id) || DISABLED.equals(id) || DISABLED_FOCUSED
				.equals(id));
	}

	public Style getStyle(int visualState) {
		if (this.parentStyle != null) {
			return getStyle(this.parentStyle, visualState);
		} else {
			return getStyle(this, visualState);
		}
	}

	private Style getStyle(Style style, int visualState) {
		if (visualState == Field.VISUAL_STATE_NORMAL) {
			return style;
		} else if (visualState == Field.VISUAL_STATE_FOCUS) {
			if (style.focusStyle != null) {
				return style.focusStyle;
			} else {
				return style;
			}
		} else if (visualState == Field.VISUAL_STATE_ACTIVE) {
			if (style.activeStyle != null) {
				return style.activeStyle;
			} else {
				if (style.focusStyle != null) {
					return style.focusStyle;
				} else {
					return style;
				}
			}
		} else if (visualState == Field.VISUAL_STATE_DISABLED) {
			if (style.disabledStyle != null) {
				return style.disabledStyle;
			} else {
				return style;
			}
		} else if (visualState == Field.VISUAL_STATE_DISABLED_FOCUS) {
			if (style.disabledFocusedStyle != null) {
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

	public Dimension getMinWidth() {
		return minWidth;
	}

	public void setMinWidth(Dimension minWidth) {
		this.minWidth = minWidth;
	}

	public Dimension getWidth() {
		return width;
	}

	public void setWidth(Dimension width) {
		this.width = width;
	}

	public Dimension getMaxWidth() {
		return maxWidth;
	}

	public void setMaxWidth(Dimension maxWidth) {
		this.maxWidth = maxWidth;
	}

	public Dimension getMinHeight() {
		return minHeight;
	}

	public void setMinHeight(Dimension minHeight) {
		this.minHeight = minHeight;
	}

	public Dimension getHeight() {
		return height;
	}

	public void setHeight(Dimension height) {
		this.height = height;
	}

	public Dimension getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(Dimension maxHeight) {
		this.maxHeight = maxHeight;
	}

	public int getVisibility() {
		return this.visibility;
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
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

	public void release() {
		release(Field.VISUAL_STATE_NORMAL);
		release(Field.VISUAL_STATE_FOCUS);
		release(Field.VISUAL_STATE_ACTIVE);
		release(Field.VISUAL_STATE_DISABLED);
		release(Field.VISUAL_STATE_DISABLED_FOCUS);
	}

	public void release(int visualState) {
		// if a style is given for the visual state ...
		Style style = getStyle(visualState);
		if (style != null) {
			// release its border
			GzBorder border = style.getBorder();
			border.release();

			// release its background
			GzBackground background = style.getBackground();
			background.release();
		}
	}
}
