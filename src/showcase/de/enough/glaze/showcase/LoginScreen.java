package de.enough.glaze.showcase;

import net.rim.device.api.ui.Field;
import de.enough.glaze.style.Style;
import de.enough.glaze.ui.component.ButtonField;
import de.enough.glaze.ui.component.LabelField;
import de.enough.glaze.ui.component.TextField;
import de.enough.glaze.ui.container.MainScreen;
import de.enough.glaze.ui.container.VerticalFieldManager;

public final class LoginScreen extends MainScreen {

	/**
	 * Constructs a new {@link LoginScreen} instance.
	 */
	public LoginScreen() {
		super(Style.id("screen"));

		// create a vertical manager for the content (username field, password
		// field and login button)
		VerticalFieldManager content = new VerticalFieldManager(
				Field.USE_ALL_WIDTH);

		// create and add the label for the username with the style 'label'
		LabelField usernameLabel = new LabelField("Username");
		content.add(usernameLabel, Style.id("label"));

		// create and add the username field with the style 'field'
		TextField usernameField = new TextField(Field.USE_ALL_WIDTH);
		content.add(usernameField, Style.id("field"));

		// create and add the label for the password with the style 'label'
		LabelField passwordLabel = new LabelField("Password");
		content.add(passwordLabel, Style.id("label"));

		// create and add the password field with the style 'field'
		TextField passwordField = new TextField(Field.USE_ALL_WIDTH);
		content.add(passwordField, Style.id("field"));

		// create and add the login button with the style 'button'
		ButtonField loginButton = new ButtonField("Login", Field.USE_ALL_WIDTH);
		content.add(loginButton, Style.id("button"));

		// add the content to the screen with the style 'content'
		add(content, Style.id("content"));

		// add the sandbox menu items to update the styles 
		addMenuItem(Showcase.bank.createUpdateMenuItem());
		addMenuItem(Showcase.social.createUpdateMenuItem());
		addMenuItem(Showcase.game.createUpdateMenuItem());
		addMenuItem(Showcase.simple.createUpdateMenuItem());
	}
}
