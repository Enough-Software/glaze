package de.enough.glaze.ui.test;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import de.enough.glaze.style.Style;
import de.enough.glaze.ui.component.NullField;
import de.enough.glaze.ui.container.FlowFieldManager;
import de.enough.glaze.ui.container.MainScreen;

/**
 * A class extending the MainScreen class, which provides default standard
 * behavior for BlackBerry GUI applications.
 */
public final class BackgroundTest extends MainScreen {
	/**
	 * Creates a new MyScreen object
	 */
	public BackgroundTest() {
		super();

		MenuItem updateItem = new MenuItem("Update", 0, 0) {
			public void run() {
				MyApp.updateStyle();
			}
		};

		addMenuItem(updateItem);

		FlowFieldManager flow = new FlowFieldManager();

		flow.add(new NullField(), Style.id("solid_test"));
		flow.add(new NullField(), Style.id("rounded_test"));
		flow.add(new NullField(), Style.id("image_test"));
		flow.add(new NullField(), Style.id("gradient_test"));
		flow.add(new NullField(), Style.id("mask_test"));
		flow.add(new NullField(), Style.id("layer_test"));
		flow.add(new NullField(), Style.id("patch_test"));
		flow.add(new NullField(), Style.id("svg_test"));

		add(flow);
	}
}
