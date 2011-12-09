package de.enough.glaze.ui.delegate;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.Screen;
import de.enough.glaze.log.Log;
import de.enough.glaze.style.Color;
import de.enough.glaze.style.Style;
import de.enough.glaze.style.handler.StyleHandler;
import de.enough.glaze.style.handler.StyleManager;
import de.enough.glaze.style.property.Visibility;
import de.enough.glaze.style.property.font.GzFont;

/**
 * Used by {@link GzField} implementations to extend/override layout and paint
 * behavior for use in Glaze
 * 
 * @author Andre
 * 
 */
public class FieldDelegate {

	/**
	 * Returns the preferred width for the field and its style
	 * 
	 * @param field
	 *            the field
	 * @return the preferred width
	 */
	public static int getPreferredWidth(Field field) {
		if (field instanceof GzExtent) {
			GzExtent gzExtent = (GzExtent) field;
			int preferredWidth = gzExtent.gz_getPreferredWidth();
			// get the style of the field
			Style style = getStyle(field);
			return ExtentDelegate.getPreferredWidth(preferredWidth, field,
					style);
		} else {
			Log.error("field must implement GzExtent", field);
			return 0;
		}
	}

	/**
	 * Returns the preferred height for the field and its style
	 * 
	 * @param field
	 *            the field
	 * @return the preferred height
	 */
	public static int getPreferredHeight(Field field) {
		if (field instanceof GzExtent) {
			GzExtent gzExtent = (GzExtent) field;
			int preferredHeight = gzExtent.gz_getPreferredHeight();
			// get the style of the field
			Style style = getStyle(field);
			return ExtentDelegate.getPreferredHeight(preferredHeight, field,
					style);
		} else {
			Log.error("field must implement GzExtent", field);
			return 0;
		}
	}

	/**
	 * Invalidates the full given field
	 * 
	 * @param x
	 *            the x offset
	 * @param y
	 *            the y offset
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @param field
	 *            the field
	 */
	public static void invalidate(int x, int y, int width, int height,
			Field field) {
		GzField gzField;
		if (field instanceof GzField) {
			gzField = (GzField) field;
			gzField.gz_invalidateAll(0, 0, field.getWidth(), field.getHeight());
		} else {
			Log.error("field must implement GzField", field);
		}
	}

	/**
	 * Layout the given field with the given width and height
	 * 
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @param field
	 *            the field
	 */
	public static void layout(int width, int height, Field field) {
		GzField gzField;
		if (field instanceof GzField) {
			gzField = (GzField) field;
			// get the style of the field
			Style style = getStyle(field);
			// correct the available content width (width)
			width = ExtentDelegate.getAvailableContentWidth(width, field);
			// get the maximum width and height to layout the field
			int layoutWidth = ExtentDelegate
					.getLayoutWidth(width, field, style);
			int layoutHeight = ExtentDelegate.getLayoutHeight(height, field,
					style);
			// layout the field
			gzField.gz_layout(layoutWidth, layoutHeight);
			// adjust the width and height
			ExtentDelegate.setExtentWidth(field, gzField, style);
			ExtentDelegate.setExtentHeight(field, gzField, style);
		} else {
			Log.error("field must implement GzField", field);
		}
	}

	/**
	 * Paints the given field with the given {@link Graphics} instance
	 * 
	 * @param graphics
	 *            the {@link Graphics} instance
	 * @param field
	 *            the field
	 */
	public static void paint(Graphics graphics, Field field) {
		GzField gzField;
		if (field instanceof GzField) {
			gzField = (GzField) field;
			// get the style
			Style style = getStyle(field);
			// set the font color
			int originalColor = setFontColor(graphics, style);
			// if the field is visible ...
			if (isVisible(style)) {
				// paint it
				gzField.gz_paint(graphics);
			}
			// reset the color to the original color
			graphics.setColor(originalColor);
		} else {
			Log.error("field must implement GzField", field);
		}
	}

	/**
	 * Sets the font color for the given field
	 * 
	 * @param graphics
	 *            the {@link Graphics} instance
	 * @param field
	 *            the field
	 * @return the original color from the {@link Graphics} instance
	 */
	private static int setFontColor(Graphics graphics, Style style) {
		// remember the original color
		int originalColor = graphics.getColor();
		if (style != null) {
			GzFont font = style.getFont();
			// if the style has a font ...
			if (font != null) {
				// get the font color
				Color fontColor = font.getColor();
				// set it in the Graphics instance
				graphics.setColor(fontColor.getColor());
			}
		}
		return originalColor;
	}

	/**
	 * Returns true if the style has a visibility of visible
	 * 
	 * @param style
	 *            the style
	 * @return true if the style has a visibility of visible otherwise false
	 */
	public static boolean isVisible(Style style) {
		if (style != null) {
			return style.getVisibility() == Visibility.VISIBLE;
		} else {
			return true;
		}
	}

	/**
	 * Returns the {@link StyleManager} of the given field
	 * 
	 * @param field
	 *            the field
	 * @return the {@link StyleManager}
	 */
	protected static StyleManager getStyleManager(Field field) {
		GzManager gzManager;
		// get the manager of the field
		Manager manager = field.getManager();
		// if the manager is a GzManager ...
		if (manager != null && manager instanceof GzManager) {
			// the field is added to a GzManager
			gzManager = (GzManager) manager;
			// return the style manager
			return gzManager.getStyleManager();
			// otherwise ...
		} else {
			// the field must be added directly to a GzScreen
			Screen screen = field.getScreen();
			if (screen instanceof GzScreen) {
				GzScreen gzScreen = (GzScreen) screen;
				return gzScreen.getStyleManager();
				// otherwise ...
			} else {
				// the field is used outside of a glaze hierachy
				return null;
			}
		}
	}

	/**
	 * Returns the {@link StyleHandler} of the given field
	 * 
	 * @param field
	 *            the field
	 * @return the {@link StyleHandler}
	 */
	public static StyleHandler getStyleHandler(Field field) {
		StyleManager styleManager = getStyleManager(field);
		if (styleManager != null) {
			return styleManager.get(field);
		} else {
			return null;
		}
	}

	/**
	 * Returns the style for the given field
	 * 
	 * @param field
	 *            the field
	 * @return the style
	 */
	public static Style getStyle(Field field) {
		StyleHandler styleHandler = getStyleHandler(field);
		if (styleHandler != null) {
			return styleHandler.getStyle();
		} else {
			return null;
		}
	}
	
	/**
	 * Returns true if the current style of the given field has a background 
	 * @param field
	 * @return
	 */
	public static boolean drawFocusNeeded(Field field) {
		StyleHandler styleHandler = getStyleHandler(field);
		if (styleHandler != null) {
			return !styleHandler.getStyle().hasBackground();
		} else {
			return true;
		}
	}
}
