package mypackage;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;
import de.enough.glaze.style.Style;
import de.enough.glaze.ui.component.LabelField;
import de.enough.glaze.ui.container.MainScreen;

/**
 * A class extending the MainScreen class, which provides default standard
 * behavior for BlackBerry GUI applications.
 */
public final class MySecondScreen extends MainScreen implements
		FieldChangeListener {
	/**
	 * Creates a new MyScreen object
	 */
	public MySecondScreen() {
		super(Style.id("screen"));
		// Set the displayed title of the screen
		setTitle("My Second Screen");

		LabelField label = new LabelField("test");
		add(label, Style.id("test"));
	}

	public void fieldChanged(Field field, int context) {
		UiApplication.getUiApplication().popScreen();
	}
}
