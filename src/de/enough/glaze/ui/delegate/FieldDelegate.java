package de.enough.glaze.ui.delegate;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.Screen;
import de.enough.glaze.log.Log;
import de.enough.glaze.style.Color;
import de.enough.glaze.style.Style;
import de.enough.glaze.style.font.GzFont;
import de.enough.glaze.style.handler.StyleHandler;
import de.enough.glaze.style.handler.StyleManager;

/**
 * Used by {@link GzField} implementations to extend/override layout and paint
 * behavior for use in Glaze
 * 
 * @author Andre
 * 
 */
public class FieldDelegate {

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
			// get the maximum width and height to layout the field
			int preprocessedWidth = ExtentDelegate.preprocessWidth(width,
					height, field, style);
			int preprocessedHeight = ExtentDelegate.preprocessHeight(width,
					height, field, style);
			// layout the field
			gzField.gz_layout(preprocessedWidth, preprocessedHeight);
			// adjust the width and height
			ExtentDelegate.postprocessWidth(width, height, field, gzField,
					style);
			ExtentDelegate.postprocessHeight(width, height, field, gzField,
					style);
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
			// set the font color
			int originalColor = setFontColor(graphics, field);
			// paint the field
			gzField.gz_paint(graphics);
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
	private static int setFontColor(Graphics graphics, Field field) {
		// remember the original color
		int originalColor = graphics.getColor();
		Style style = getStyle(field);
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
	protected static StyleHandler getStyleHandler(Field field) {
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
	protected static Style getStyle(Field field) {
		StyleHandler styleHandler = getStyleHandler(field);
		if (styleHandler != null) {
			return styleHandler.getStyle();
		} else {
			return null;
		}
	}
}
