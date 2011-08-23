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
		Log.setLevel(Log.ERROR);

		updateStyle();
		pushScreen(new MyScreen());

	}

	public static void updateStyle() {
		try {
			StyleSheet.getInstance().load(
					"http://pastebin.com/raw.php?i=XAVz9Lds");
		} catch (CssSyntaxError e) {
			// do nothing
		} catch (Throwable t) {
			Log.e("error", t);
			t.printStackTrace();
		}
	}
}
