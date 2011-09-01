package mypackage;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import de.enough.glaze.style.Style;
import de.enough.glaze.ui.component.ButtonField;
import de.enough.glaze.ui.component.LabelField;
import de.enough.glaze.ui.component.TextField;
import de.enough.glaze.ui.container.MainScreen;
import de.enough.glaze.ui.container.VerticalFieldManager;

/**
 * A class extending the MainScreen class, which provides default standard
 * behavior for BlackBerry GUI applications.
 */
public final class MyFirstScreen extends MainScreen implements
		FieldChangeListener {
	/**
	 * Creates a new MyScreen object
	 */
	public MyFirstScreen() {
		super(Style.id("screen"));
		// Set the displayed title of the screen
		setTitle("Welcome!");

		VerticalFieldManager content = new VerticalFieldManager(
				Field.USE_ALL_WIDTH);

		LabelField usernameLabel = new LabelField("Username");
		content.add(usernameLabel, Style.id("label"));
		
		TextField usernameField = new TextField(Field.USE_ALL_WIDTH);
		content.add(usernameField, Style.id("field"));
		
		LabelField passwordLabel = new LabelField("Password");
		content.add(passwordLabel, Style.id("label"));

		TextField passwordField = new TextField(Field.USE_ALL_WIDTH);
		content.add(passwordField, Style.id("field"));

		ButtonField loginButton = new ButtonField("Login", Field.USE_ALL_WIDTH);
		loginButton.setChangeListener(this);
		content.add(loginButton, Style.id("button"));

		add(content, Style.id("content"));

		MenuItem updateItem = new MenuItem("Update", 0, 0) {
			public void run() {
				MyApp.updateStyle();
			}
		};

		addMenuItem(updateItem);
	}

	public void fieldChanged(Field field, int context) {
		UiApplication.getUiApplication().pushScreen(new MySecondScreen());
	}
}
