package mypackage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.rim.device.api.ui.UiApplication;
import de.enough.glaze.style.StyleSheet;
import de.enough.glaze.style.parser.CssContentHandlerImpl;
import de.enough.glaze.style.parser.CssParser;

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
    	String css = "block:focus{\n " +
    			"innerblock {\n " +
    			"innerfirstkey: innerfirstvalue; \n" +
    			"}}} \n";
    	
    	InputStream stream = new ByteArrayInputStream(css.getBytes());
    	InputStreamReader reader = new InputStreamReader(stream);
    	CssParser cssParser = new CssParser(reader);
    	CssContentHandlerImpl cssContentHandler = new CssContentHandlerImpl(StyleSheet.getInstance());
    	cssParser.setContentHandler(cssContentHandler);
    	try {
			cssParser.parse();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
    	/*try {
			Object parsed = new DimensionValueParser().parse("8in      12 16% 18px");
			if(parsed instanceof Object[]) {
				Object[] array = (Object[])parsed;
				for (int index = 0; index < array.length; index++) {
					System.out.println(array[index]);
				}
			} else {
				System.out.println(parsed);
			}
		} catch (CssSyntaxException e) {
			System.out.println(e.toString());
		}
		
		try {
			Object parsed = new ColorValueParser().parse("                    \n    rgb(255,0,0) #FFFFFF blue ");
			if(parsed instanceof Object[]) {
				Object[] array = (Object[])parsed;
				for (int index = 0; index < array.length; index++) {
					System.out.println(array[index]);
				}
			} else {
				System.out.println(parsed);
			}
		} catch (CssSyntaxException e) {
			System.out.println(e.toString());
		}*/
    	
        // Create a new instance of the application and make the currently
        // running thread the application's event dispatch thread.
        MyApp theApp = new MyApp();       
        theApp.enterEventDispatcher();
    }
    

    /**
     * Creates a new MyApp object
     */
    public MyApp()
    {        
        // Push a screen onto the UI stack for rendering.
        pushScreen(new MyScreen());
    }    
}
