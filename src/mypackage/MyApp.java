package mypackage;

import java.io.IOException;

import net.rim.device.api.ui.UiApplication;
import de.enough.glaze.log.Log;
import de.enough.glaze.style.StyleSheet;
import de.enough.glaze.style.StyleSheetListener;
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
		Log.setLevel(Log.DEBUG);

		try {
			StyleSheet.getInstance().load("/test.css");
			StyleSheet.getInstance().finalize();
			pushScreen(new MyScreen());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CssSyntaxError e) {
			e.printStackTrace();
		}
	}
}
