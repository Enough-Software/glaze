package de.enough.glaze.style;

import java.util.Enumeration;
import java.util.Hashtable;

import net.rim.device.api.ui.Field;
import de.enough.glaze.style.extension.Extension;
import de.enough.glaze.style.property.Visibility;
import de.enough.glaze.style.property.background.GzBackground;
import de.enough.glaze.style.property.border.GzBorder;
import de.enough.glaze.style.property.font.GzFont;

/**
 * A class representing a style. A style consists of a margin, a padding,
 * dimensional properties (e.g. min-width, height), a background, a border, a
 * font and the visibility. Each style has substyles representing style classes
 * for the visual states of a field (e.g. normal, focus).
 * 
 * @author Andre
 * 
 */
public class Style {

	/**
	 * Returns the style with the given id from the stylesheet instance
	 * 
	 * @param id
	 *            the id
	 * @return the style
	 */
	public static Style id(String id) {
		return StyleSheet.getInstance().getStyle(id);
	}

	/**
	 * the class name for a focused visual state
	 */
	private static final String FOCUS = "focus";

	/**
	 * the class name for an active visual state
	 */
	private static final String ACTIVE = "active";

	/**
	 * the class name for a disabled visual state
	 */
	private static final String DISABLED = "disabled";

	/**
	 * the class name for a disabled focused visual state
	 */
	private static final String DISABLED_FOCUSED = "disabled_focused";

	/**
	 * the id
	 */
	private final String id;

	/**
	 * the margin
	 */
	private Margin margin;

	/**
	 * the padding
	 */
	private Padding padding;

	/**
	 * the background
	 */
	private GzBackground background;

	/**
	 * the font
	 */
	private GzFont font;

	/**
	 * the border
	 */
	private GzBorder border;

	/**
	 * the minimum width dimension
	 */
	private Dimension minWidth;

	/**
	 * the width dimension
	 */
	private Dimension width;

	/**
	 * the maximum width dimension
	 */
	private Dimension maxWidth;

	/**
	 * the minimum height dimension
	 */
	private Dimension minHeight;

	/**
	 * the height dimension
	 */
	private Dimension height;

	/**
	 * the maximum height dimension
	 */
	private Dimension maxHeight;

	/**
	 * the visibility
	 */
	private int visibility;

	/**
	 * the extensions map
	 */
	private final Hashtable extensions;

	/**
	 * the parent style
	 */
	private Style parentStyle;

	/**
	 * the focused style
	 */
	private Style focusStyle;

	/**
	 * the active style
	 */
	private Style activeStyle;

	/**
	 * the disabled style
	 */
	private Style disabledStyle;

	/**
	 * the disabled focused style
	 */
	private Style disabledFocusedStyle;

	/**
	 * flag indicating if this style and its classes need a layout update
	 */
	private boolean layoutUpdate;

	public Style(String id) {
		this.id = id;
		this.margin = Margin.ZERO;
		this.padding = Padding.ZERO;
		this.background = null;
		this.border = null;
		this.visibility = Visibility.VISIBLE;
		this.extensions = new Hashtable();
		this.layoutUpdate = false;
	}

	/**
	 * Returns the id
	 * 
	 * @return the id
	 */
	public String getId() {
		// if this style is a style class and thus the parent style is set ...
		if (this.parentStyle != null) {
			// return the id of the parent style
			return this.parentStyle.getId();
			// otherwise ...
		} else {
			// return the id
			return this.id;
		}
	}

	/**
	 * Returns true if the given class is is valid
	 * 
	 * @param id
	 *            the class id
	 * @return true if the given class is is valid otherwise false
	 */
	public static boolean isValidClass(String id) {
		return (FOCUS.equals(id) || ACTIVE.equals(id) || DISABLED.equals(id) || DISABLED_FOCUSED
				.equals(id));
	}

	/**
	 * Sets the class of the given id with the given style
	 * 
	 * @param id
	 *            the class id
	 * @param style
	 *            the style
	 */
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

		// test if the layout needs an update when a change from a style class
		// to the
		// normal style is performed
		boolean layoutUpdate = LayoutUpdateTest.getInstance().test(this, style);
		// only set the layout update flag if its true
		if (layoutUpdate) {
			this.layoutUpdate = layoutUpdate;
		}

		// set the parent style
		style.parentStyle = this;
	}

	/**
	 * Returns true if the style and its classes need a layout update
	 * 
	 * @return true if the style and its classes need a layout update otherwise
	 *         false
	 */
	public boolean layoutUpdate() {
		// if this style is a style class and thus the parent style is set ...
		if (this.parentStyle != null) {
			// return the layout update flag of the parent style
			return this.parentStyle.layoutUpdate;
			// otherwise ...
		} else {
			// return the layout update flag
			return this.layoutUpdate;
		}
	}

	/**
	 * Returns the style for the given visual state
	 * 
	 * @param visualState
	 *            the visual state
	 * @return the style
	 */
	public Style getStyle(int visualState) {
		// if this style is a style class and thus the parent style is set ...
		if (this.parentStyle != null) {
			// return the style for the visual state
			return getStyle(this.parentStyle, visualState);
		} else {
			return getStyle(this, visualState);
		}
	}

	private Style getStyle(Style style, int visualState) {
		// if the visual state is normal ...
		if (visualState == Field.VISUAL_STATE_NORMAL) {
			// return the style
			return style;
			// if the visual state is focus ...
		} else if (visualState == Field.VISUAL_STATE_FOCUS) {
			// if the focus style is set ...
			if (style.focusStyle != null) {
				// return the focus style
				return style.focusStyle;
				// otherwise ...
			} else {
				// return the normal style
				return style;
			}
			// if the visual state is active ...
		} else if (visualState == Field.VISUAL_STATE_ACTIVE) {
			// if the active style is set ...
			if (style.activeStyle != null) {
				// return the active style
				return style.activeStyle;
				// otherwise ...
			} else {
				// if the focus style is set ...
				if (style.focusStyle != null) {
					// return the active style
					return style.focusStyle;
					// otherwise ...
				} else {
					// return the normal style
					return style;
				}
			}
			// if the visual state is disabled ...
		} else if (visualState == Field.VISUAL_STATE_DISABLED) {
			// if the disabled style is set ...
			if (style.disabledStyle != null) {
				// return the disabled style
				return style.disabledStyle;
				// otherwise ...
			} else {
				// return the normal style
				return style;
			}
			// if the visual state is focused/disabled ...
		} else if (visualState == Field.VISUAL_STATE_DISABLED_FOCUS) {
			// if focused/disabled style is set ...
			if (style.disabledFocusedStyle != null) {
				// return the focused/disabled style
				return style.disabledFocusedStyle;
				// otherwise ...
			} else {
				// return the normal style
				return style;
			}
		} else {
			throw new IllegalArgumentException(visualState
					+ " is not a valid visual state");
		}
	}

	/**
	 * Returns the margin
	 * 
	 * @return the margin
	 */
	public Margin getMargin() {
		return margin;
	}

	/**
	 * Sets the margin
	 * 
	 * @param margin
	 *            the margin
	 */
	public void setMargin(Margin margin) {
		this.margin = margin;
	}

	/**
	 * Returns the padding
	 * 
	 * @return the padding
	 */
	public Padding getPadding() {
		return padding;
	}

	/**
	 * Sets the padding
	 * 
	 * @param padding
	 *            the padding
	 */
	public void setPadding(Padding padding) {
		this.padding = padding;
	}

	/**
	 * Returns the background
	 * 
	 * @return the background
	 */
	public GzBackground getBackground() {
		return background;
	}

	/**
	 * Sets the background
	 * 
	 * @param background
	 *            the background
	 */
	public void setBackground(GzBackground background) {
		this.background = background;
	}

	/**
	 * Returns the font
	 * 
	 * @return the font
	 */
	public GzFont getFont() {
		return font;
	}

	/**
	 * Sets the font
	 * 
	 * @param font
	 *            the font
	 */
	public void setFont(GzFont font) {
		this.font = font;
	}

	/**
	 * Returns the border
	 * 
	 * @return the border
	 */
	public GzBorder getBorder() {
		return border;
	}

	/**
	 * Sets the border
	 * 
	 * @param border
	 *            the border
	 */
	public void setBorder(GzBorder border) {
		this.border = border;
	}

	/**
	 * Returns the minimum width dimension
	 * 
	 * @return the minimum width dimension
	 */
	public Dimension getMinWidth() {
		return minWidth;
	}

	/**
	 * Sets the minimum width dimension
	 * 
	 * @param minWidth
	 *            the minimum width dimension
	 */
	public void setMinWidth(Dimension minWidth) {
		this.minWidth = minWidth;
	}

	/**
	 * Returns the width dimension
	 * 
	 * @return the width dimension
	 */
	public Dimension getWidth() {
		return width;
	}

	/**
	 * Sets the width dimension
	 * 
	 * @param width
	 *            the width dimension
	 */
	public void setWidth(Dimension width) {
		this.width = width;
	}

	/**
	 * Returns the maximum width dimension
	 * 
	 * @return the maximum width dimension
	 */
	public Dimension getMaxWidth() {
		return maxWidth;
	}

	/**
	 * Sets the maximum width dimension
	 * 
	 * @param maxWidth
	 *            the maximum width dimension
	 */
	public void setMaxWidth(Dimension maxWidth) {
		this.maxWidth = maxWidth;
	}

	/**
	 * Returns the minimum height dimension
	 * 
	 * @return the minimum height dimension
	 */
	public Dimension getMinHeight() {
		return minHeight;
	}

	/**
	 * Sets the minimum height dimension
	 * 
	 * @param minHeight
	 *            the minimum height dimension
	 */
	public void setMinHeight(Dimension minHeight) {
		this.minHeight = minHeight;
	}

	/**
	 * Returns the height dimension
	 * 
	 * @return the height dimension
	 */
	public Dimension getHeight() {
		return height;
	}

	/**
	 * Sets the height dimension
	 * 
	 * @param height
	 *            the height dimension
	 */
	public void setHeight(Dimension height) {
		this.height = height;
	}

	/**
	 * Returns the maximum height dimension
	 * 
	 * @return the maximum height dimension
	 */
	public Dimension getMaxHeight() {
		return maxHeight;
	}

	/**
	 * Sets the maximum height dimension
	 * 
	 * @param maxWidth
	 *            the maximum height dimension
	 */
	public void setMaxHeight(Dimension maxHeight) {
		this.maxHeight = maxHeight;
	}

	/**
	 * Returns the visibilty
	 * 
	 * @return the visibilty
	 */
	public int getVisibility() {
		return this.visibility;
	}

	/**
	 * Sets the visibility
	 * 
	 * @param visibility
	 *            the visibility
	 */
	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

	/**
	 * Adds an extension with its associated date
	 * 
	 * @param extension
	 *            the extension
	 * @param data
	 *            the data
	 */
	public void addExtension(Extension extension, Object data) {
		this.extensions.put(extension, data);
	}

	/**
	 * Returns the extensions
	 * 
	 * @return the extensions
	 */
	public Enumeration getExtensions() {
		return this.extensions.keys();
	}

	/**
	 * Returns true if this style uses extensions
	 * 
	 * @return true if this style uses extensions otherwise
	 */
	public boolean usesExtensions() {
		return this.extensions.size() > 0;
	}

	/**
	 * Returns the data for the given extension
	 * 
	 * @param extension
	 *            the extension
	 * @return the data
	 */
	public Object getExtensionData(Extension extension) {
		return this.extensions.get(extension);
	}

	/**
	 * Releases the style and its classes
	 */
	public void release() {
		release(Field.VISUAL_STATE_NORMAL);
		release(Field.VISUAL_STATE_FOCUS);
		release(Field.VISUAL_STATE_ACTIVE);
		release(Field.VISUAL_STATE_DISABLED);
		release(Field.VISUAL_STATE_DISABLED_FOCUS);
	}

	/**
	 * Releases the style class for the given visual state
	 * 
	 * @param visualState
	 *            the visual state
	 */
	private void release(int visualState) {
		// if a style is given for the visual state ...
		Style style = getStyle(visualState);
		if (style != null) {
			// release its border
			GzBorder border = style.getBorder();
			if (border != null) {
				border.release();
			}

			// release its background
			GzBackground background = style.getBackground();
			if (background != null) {
				background.release();
			}
		}
	}
}
