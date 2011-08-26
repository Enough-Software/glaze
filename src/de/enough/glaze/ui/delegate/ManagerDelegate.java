package de.enough.glaze.ui.delegate;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import de.enough.glaze.log.Log;
import de.enough.glaze.style.Style;
import de.enough.glaze.style.background.GzBackground;
import de.enough.glaze.style.handler.FieldStyleHandler;
import de.enough.glaze.style.handler.FieldStyleManager;

public class ManagerDelegate {

	public static void sublayout(int maxWidth, int maxHeight, Manager manager) {
		GzManager gzManager;
		if (manager instanceof GzManager) {
			gzManager = (GzManager) manager;
			FieldStyleManager styleManager = gzManager.getStyleManager();
			for (int index = 0; index < manager.getFieldCount(); index++) {
				FieldStyleHandler handler = styleManager.get(index);
				if (handler.isVisualStateChanged()) {
					handler.updateStyle(maxWidth);
					handler.updateVisualState();
				}
			}

			Style style = FieldDelegate.getStyle(manager);
			int preprocessedWidth = ExtentDelegate.preprocessWidth(maxWidth,
					maxHeight, manager, style);
			int preprocessedHeight = ExtentDelegate.preprocessHeight(maxWidth,
					maxHeight, manager, style);
			gzManager.gz_sublayout(preprocessedWidth, preprocessedHeight);
			ExtentDelegate.postprocessWidth(maxWidth, maxHeight, manager,
					gzManager, style);
			ExtentDelegate.postprocessHeight(maxWidth, maxHeight, manager,
					gzManager, style);
		} else {
			Log.e("manager must implement GzManager", manager);
		}
	}

	public static void subpaint(Graphics graphics, Manager manager) {
		GzManager gzManager;
		if (manager instanceof GzManager) {
			gzManager = (GzManager) manager;
			FieldStyleManager handlers = gzManager.getStyleManager();
			for (int index = 0; index < manager.getFieldCount(); index++) {
				Field field = manager.getField(index);
				if (field instanceof Manager) {
					FieldStyleHandler handler = handlers.get(index);
					GzBackground background = handler.getStyle()
							.getBackground();
					background.setField(field);
					gzManager.gz_paintChild(graphics, field);
					background.setField(null);
				} else {
					gzManager.gz_paintChild(graphics, field);
				}
			}

			for (int index = 0; index < manager.getFieldCount(); index++) {
				FieldStyleHandler handler = handlers.get(index);
				if (handler.isVisualStateChanged()) {
					gzManager.gz_updateLayout();
					return;
				}
			}
		} else {
			Log.e("manager must implement GzManager", manager);
		}
	}

}
