package de.enough.glaze.ui.delegate;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.decor.Background;
import de.enough.glaze.log.Log;
import de.enough.glaze.style.Style;
import de.enough.glaze.style.handler.StyleHandler;
import de.enough.glaze.style.handler.StyleManager;
import de.enough.glaze.style.property.background.GzBackground;

/**
 * Used by {@link GzManager} implementations to extend/override layout and paint
 * behavior for use in Glaze
 * 
 * @author Andre
 * 
 */
public class ManagerDelegate {

	/**
	 * Layouts the given {@link Manager} with the given maximum width and height
	 * 
	 * @param maxWidth
	 *            the maximum width
	 * @param maxHeight
	 *            the maximum height
	 * @param manager
	 *            the {@link Manager}
	 */
	public static void sublayout(int maxWidth, int maxHeight, Manager manager) {
		GzManager gzManager;
		if (manager instanceof GzManager) {
			gzManager = (GzManager) manager;
			// get the style manager
			StyleManager styleManager = gzManager.getStyleManager();
			// for each style handler ...
			for (int index = 0; index < styleManager.size(); index++) {
				StyleHandler handler = styleManager.get(index);
				// if the visual state has changed for the field of the style
				// handler ...
				if (handler.isVisualStateChanged()) {
					// update the style with the available width
					handler.updateStyle();
					handler.applyStyle(maxWidth);
					// update the visual style
					handler.updateVisualState();
				}
			}

			// set the maximum width in the style manager for percentual field
			// dimensions
			styleManager.setMaxWidth(maxWidth);

			// get the style of the manager
			Style style = FieldDelegate.getStyle(manager);
			// get the available width and height to layout the manager
			int preprocessedWidth = ExtentDelegate.preprocessWidth(maxWidth,
					maxHeight, manager, style);
			int preprocessedHeight = ExtentDelegate.preprocessHeight(maxWidth,
					maxHeight, manager, style);
			// layout the manager
			gzManager.gz_sublayout(preprocessedWidth, preprocessedHeight);
			// adjust the width and height
			ExtentDelegate.postprocessWidth(maxWidth, maxHeight, manager,
					gzManager, style);
			ExtentDelegate.postprocessHeight(maxWidth, maxHeight, manager,
					gzManager, style);
		} else {
			Log.error("manager must implement GzManager", manager);
		}
	}

	/**
	 * Paints the given {@link Manager} with the given {@link Graphics} instance
	 * 
	 * @param graphics
	 *            the {@link Graphics} instance
	 * @param manager
	 *            the manager
	 */
	public static void paint(Graphics graphics, Manager manager) {
		GzManager gzManager;
		if (manager instanceof GzManager) {
			gzManager = (GzManager) manager;
			Style style = FieldDelegate.getStyle(manager);
			// if the field is visible ...
			if (FieldDelegate.isVisible(style)) {
				// paint it
				gzManager.gz_paint(graphics);
			}

			boolean updateLayout = false;
			boolean visualStateChanged = false;
			// get the handlers
			StyleManager styleManager = gzManager.getStyleManager();
			// for each style handler ...
			for (int index = 0; index < styleManager.size(); index++) {
				StyleHandler handler = styleManager.get(index);
				// if the visual state of a field has changed ...
				if (handler.isVisualStateChanged()) {
					visualStateChanged = true;
					// if the style change requires a layout update ...
					if (handler.layoutUpdate()) {
						// indicate that an update is needed
						updateLayout = true;
						// otherwise ...
					} else {
						// get the updated style
						Field field = handler.getField();
						int visualState = field.getVisualState();
						if (style != null) {
							Style updatedStyle = style.getStyle(visualState);
							// if the updated style has extensions ...
							if (updatedStyle.usesExtensions()) {
								// update the layout
								updateLayout = true;
								// otherwise ...
							} else {
								// update the visual state and style
								handler.updateVisualState();
								handler.updateStyle();
								// apply the font
								handler.applyFont();
							}
						} else {
							// update the visual state and style
							handler.updateVisualState();
							handler.updateStyle();
							// apply the font
							handler.applyFont();
						}
					}
				}
			}

			if (visualStateChanged) {
				// if a layout update is needed ...
				if (updateLayout) {
					// update the layout
					gzManager.gz_updateLayout();
					// otherwise ...
				} else {
					// invalidate to fully repaint
					manager.invalidate();
				}
			}
		} else {
			Log.error("manager must implement GzManager", manager);
		}
	}

	/**
	 * Paints the background of the given {@link Manager} with the given
	 * {@link Graphics} instance
	 * 
	 * @param graphics
	 *            the {@link Graphics} instance
	 * @param manager
	 *            the {@link Manager}
	 */
	public static void paintBackground(Graphics graphics, Manager manager) {
		GzManager gzManager;
		if (manager instanceof GzManager) {
			gzManager = (GzManager) manager;
			// get the background for the current visual state of the manager
			int visualState = manager.getVisualState();
			Background background = manager.getBackground(visualState);
			// if the background is a GzBackground ...
			if (background != null && background instanceof GzBackground) {
				// set the field in the background to adjust the rectangle to
				// draw the background into
				GzBackground gzBackground = (GzBackground) background;
				gzBackground.setField(manager);
				gzManager.gz_paintBackground(graphics);
				// reset the field in the background
				gzBackground.setField(null);
				// otherwise ...
			} else {
				// just draw the background
				gzManager.gz_paintBackground(graphics);
			}
		}
	}

}
