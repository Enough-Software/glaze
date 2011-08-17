package mypackage;

import java.io.IOException;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.UiApplication;
import de.enough.glaze.content.ContentException;
import de.enough.glaze.log.Log;
import de.enough.glaze.style.StyleResources;
import de.enough.glaze.style.StyleSheet;
import de.enough.glaze.style.parser.exception.CssSyntaxError;

/**
 * This class extends the UiApplication class, providing a
 * graphical user interface.
 */
public class MyApp extends UiApplication
{
    /**
     * Entry point for application
     * @param args Command line arguments (not used)
     */ 
    public static void main(String[] args)
    {
    	MyApp theApp = new MyApp();       
        theApp.enterEventDispatcher();
    }
    

    /**
     * Creates a new MyApp object
     */
    public MyApp()
    {        
    	Log.setLevel(Log.DEBUG);
        try {
			StyleSheet.getInstance().load("/test.css");
		} catch (IOException e) {
			System.out.println(e);
		} catch (CssSyntaxError e) {
			System.out.println(e);
		}
		
        pushScreen(new MyScreen());
        
        new Thread() {
        	public void run() {
        		try {
					Bitmap iconBitmap = StyleResources.getInstance().loadBitmap("/img/icon.png");
					System.out.println(iconBitmap.getWidth() + "x" + iconBitmap.getHeight());
					
					Bitmap logoBitmap = StyleResources.getInstance().loadBitmap("http://www.spiegel.de/static/sys/v9/spiegelonline_logo.png;deviceside=true");
					System.out.println(logoBitmap.getWidth() + "x" + logoBitmap.getHeight());
				} catch (ContentException e) {
					System.out.println(e);
					e.printStackTrace();
				}
        	}
        }.start();
    }    
}
