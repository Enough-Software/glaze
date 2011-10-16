package de.enough.glaze.showcase;

import net.rim.device.api.ui.FontManager;
import net.rim.device.api.ui.Screen;
import net.rim.device.api.ui.UiApplication;
import de.enough.glaze.style.StyleSheetSandbox;

/*
 *  This class extends the UiApplication class, providing a graphical user
 *  interface.
 */
public class Showcase extends UiApplication {

	/**
	 * the unstyled sandbox
	 */
	public static StyleSheetSandbox simple;

	/**
	 * the bank sandbox
	 */
	public static StyleSheetSandbox bank;

	/**
	 * the social sandbox
	 */
	public static StyleSheetSandbox social;

	/**
	 * the game sandbox
	 */
	public static StyleSheetSandbox game;

	/*
	 * Entry point for application
	 * 
	 * @param args Command line arguments (not used)
	 */
	public static void main(String[] args) {
		Showcase showcase = new Showcase();
		showcase.enterEventDispatcher();
	}

	/**
	 * Constructs a new {@link Showcase} instance.
	 */
	public Showcase() {
		// install the Amplitude and ComicSans Font
		FontManager.getInstance().load("social/Amplitude-Bold.ttf",
				"Amplitude", 0);

		// create a sandbox for the unstyled example
		simple = new StyleSheetSandbox("res://simple/styles.css") {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * de.enough.glaze.style.StyleSheetSandbox#getUpdateMenuTextItem()
			 */
			protected String getUpdateMenuTextItem() {
				return "Simple Example";
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * de.enough.glaze.style.StyleSheetSandbox#createSandboxScreen()
			 */
			public Screen createSandboxScreen() {
				return new LoginScreen();
			}
		};

		// create a sandbox for the bank example
		bank = new StyleSheetSandbox("res://bank/styles.css") {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * de.enough.glaze.style.StyleSheetSandbox#getUpdateMenuTextItem()
			 */
			protected String getUpdateMenuTextItem() {
				return "Bank Example";
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * de.enough.glaze.style.StyleSheetSandbox#createSandboxScreen()
			 */
			public Screen createSandboxScreen() {
				return new LoginScreen();
			}
		};

		// create a sandbox for the social example
		social = new StyleSheetSandbox("res://social/styles.css") {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * de.enough.glaze.style.StyleSheetSandbox#getUpdateMenuTextItem()
			 */
			protected String getUpdateMenuTextItem() {
				return "Social Example";
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * de.enough.glaze.style.StyleSheetSandbox#createSandboxScreen()
			 */
			public Screen createSandboxScreen() {
				return new LoginScreen();
			}
		};

		// create a sandbox for the game example
		game = new StyleSheetSandbox("res://game/styles.css") {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * de.enough.glaze.style.StyleSheetSandbox#getUpdateMenuTextItem()
			 */
			protected String getUpdateMenuTextItem() {
				return "Game Example";
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * de.enough.glaze.style.StyleSheetSandbox#createSandboxScreen()
			 */
			public Screen createSandboxScreen() {
				return new LoginScreen();
			}
		};

		bank.update();
	}

}
