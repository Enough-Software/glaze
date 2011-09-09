package de.enough.glaze.ui.test;

import net.rim.device.api.ui.Screen;
import net.rim.device.api.ui.UiApplication;
import de.enough.glaze.content.ContentException;
import de.enough.glaze.log.Log;
import de.enough.glaze.style.StyleResources;
import de.enough.glaze.style.StyleSheetSandbox;

/**
 * This class extends the UiApplication class, providing a graphical user
 * interface.
 */
public class MyApp extends UiApplication {

	public static StyleSheetSandbox sandbox;

	/**
	 * Entry point for application
	 * 
	 * @param args
	 *            Command line arguments (not used)
	 */
	public static void main(String[] args) {
		MyApp theApp = new MyApp();
		theApp.enterEventDispatcher();
	}

	/**
	 * Creates a new MyApp object
	 */
	public MyApp() {
		Log.setLevel(Log.INFO);

		try {
			StyleResources.getInstance().clear();
		} catch (ContentException e) {
			e.printStackTrace();
		}

		sandbox = new StyleSheetSandbox(
				"http://pastebin.com/raw.php?i=auTcDFec") {

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
		
		sandbox.update();
	}
}
