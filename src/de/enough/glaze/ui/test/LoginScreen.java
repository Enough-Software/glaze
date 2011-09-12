package de.enough.glaze.ui.test;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Manager;
import de.enough.glaze.style.Style;
import de.enough.glaze.ui.component.ButtonField;
import de.enough.glaze.ui.component.LabelField;
import de.enough.glaze.ui.component.TextField;
import de.enough.glaze.ui.container.HorizontalFieldManager;
import de.enough.glaze.ui.container.MainScreen;
import de.enough.glaze.ui.container.VerticalFieldManager;

/**
 * A class extending the MainScreen class, which provides default standard
 * behavior for BlackBerry GUI applications.
 */
public final class LoginScreen extends MainScreen {
	/**
	 * Creates a new MyScreen object
	 */
	public LoginScreen() {
		super(Manager.NO_VERTICAL_SCROLL | Manager.NO_HORIZONTAL_SCROLL, Style
				.id("screen"));

		HorizontalFieldManager base = new HorizontalFieldManager(
				Field.USE_ALL_WIDTH | Field.USE_ALL_HEIGHT
						| Manager.NO_VERTICAL_SCROLL
						| Manager.NO_HORIZONTAL_SCROLL);

		VerticalFieldManager content = new VerticalFieldManager(
				Field.USE_ALL_WIDTH | Field.FIELD_VCENTER);

		LabelField usernameLabel = new LabelField("Username");
		content.add(usernameLabel, Style.id("label"));

		TextField usernameField = new TextField(Field.USE_ALL_WIDTH);
		content.add(usernameField, Style.id("field"));

		LabelField passwordLabel = new LabelField("Password");
		content.add(passwordLabel, Style.id("label"));

		TextField passwordField = new TextField(Field.USE_ALL_WIDTH);
		content.add(passwordField, Style.id("field"));

		ButtonField loginButton = new ButtonField("Login", Field.USE_ALL_WIDTH);
		content.add(loginButton, Style.id("button"));

		base.add(content, Style.id("content"));

		add(base);

		addMenuItem(MyApp.sandbox.createUpdateMenuItem());
		addMenuItem(MyApp.complex.createUpdateMenuItem());
	}
}
