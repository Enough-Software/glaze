package de.enough.glaze.ui.test;

import net.rim.device.api.ui.MenuItem;
import de.enough.glaze.style.Style;
import de.enough.glaze.ui.component.NullField;
import de.enough.glaze.ui.container.FlowFieldManager;
import de.enough.glaze.ui.container.MainScreen;

/**
 * A class extending the MainScreen class, which provides default standard
 * behavior for BlackBerry GUI applications.
 */
public final class BorderTest extends MainScreen {
	/**
	 * Creates a new MyScreen object
	 */
	public BorderTest() {
		super();

		MenuItem updateItem = new MenuItem("Update", 0, 0) {
			public void run() {
				MyApp.updateStyle();
			}
		};

		addMenuItem(updateItem);

		FlowFieldManager flow = new FlowFieldManager();

		flow.add(new NullField(), Style.id("simple_test"));
		flow.add(new NullField(), Style.id("rounded_test"));
		flow.add(new NullField(), Style.id("bevel_test"));
		flow.add(new NullField(), Style.id("bitmap_test"));

		add(flow);
	}
}
