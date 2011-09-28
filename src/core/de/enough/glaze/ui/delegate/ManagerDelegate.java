package de.enough.glaze.ui.delegate;

import net.rim.device.api.system.DeviceInfo;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.decor.Background;
import de.enough.glaze.log.Log;
import de.enough.glaze.style.Style;
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
	 * Invalidates the given {@link Manager} with the given offsets and
	 * dimension and triggers an update if needed
	 * 
	 * @param x
	 *            the x offset
	 * @param y
	 *            the y offset
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @param manager
	 *            the {@link Manager}
	 */
	public static void invalidate(int x, int y, int width, int height,
			Manager manager) {
		GzManager gzManager;
		if (manager instanceof GzManager) {
			gzManager = (GzManager) manager;
			// get the style manager
			StyleManager styleManager = gzManager.getStyleManager();
			synchronized (UiApplication.getEventLock()) {
				// if the manager is not layouting and a one of the fields needs
				// a layout update ...
				if (!styleManager.isLayouting()) {
					boolean layoutUpdate = styleManager.applyStyles();
					if (layoutUpdate) {
						System.out.println("update layout");
						// update the layout
						gzManager.gz_updateLayout();
					}
				}
			}

			// if the device is a simulator ...
			if (DeviceInfo.isSimulator()) {
				// invalidate all to workaround simulator bug
				gzManager.gz_invalidate(0, 0, manager.getContentWidth(),
						manager.getContentHeight());
			} else {
				// invalidate with the given offsets and dimension
				gzManager.gz_invalidate(x, y, width, height);
			}

		} else {
			Log.error("manager must implement GzManager", manager);
		}
	}

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
			// indicate that the manager is layouting
			styleManager.setLayouting(true);
			// set the maximum width in the style manager for percentual field
			// dimensions
			styleManager.setMaxWidth(maxWidth);
			// apply the margin and padding for all fields
			styleManager.applyLayouts(maxWidth);
			// get the style of the manager
			Style style = FieldDelegate.getStyle(manager);
			// get the available width and height to layout the manager
			int preprocessedWidth = ExtentDelegate.getLayoutWidth(maxWidth,
					manager, style);
			int preprocessedHeight = ExtentDelegate.getLayoutHeight(maxHeight,
					manager, style);
			// layout the manager
			gzManager.gz_sublayout(preprocessedWidth, preprocessedHeight);
			// adjust the width and height
			ExtentDelegate.setExtentWidth(manager, gzManager, style);
			ExtentDelegate.setExtentHeight(manager, gzManager, style);
			// reset the layouting flag
			styleManager.setLayouting(false);
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
