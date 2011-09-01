package mypackage;

import net.rim.device.api.ui.UiApplication;
import de.enough.glaze.log.Log;
import de.enough.glaze.style.StyleSheet;
import de.enough.glaze.style.parser.exception.CssSyntaxError;

/**
 * This class extends the UiApplication class, providing a graphical user
 * interface.
 */
public class MyApp extends UiApplication {
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

		updateStyle();
		pushScreen(new MyFirstScreen());

	}

	public static void updateStyle() {
		try {
			StyleSheet.getInstance().load(
					"http://pastebin.com/raw.php?i=Xmr8We8q");
		} catch (CssSyntaxError e) {
			// do nothing
		} catch (Throwable t) {
			Log.error("error", t);
			t.printStackTrace();
		}
	}
}
