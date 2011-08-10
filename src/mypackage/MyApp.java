package mypackage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.rim.device.api.ui.UiApplication;
import de.enough.glaze.style.parser.CssContentHandler;
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
    	String css = "colors { red: #ff0000" +
    			"}" +
    			"block:focus {\n " +
    			"firstkey: firstvalue;\n " +
    			"secondkey: secondstyle;\n " +
    			"innerblock {\n " +
    			"innerfirstkey: innerfirstvalue; \n" +
    			"innersecondkey: test\n" +
    			"} \n" +
    			"/*comment*/\n" +
    			"thirdkey: thirdstyle;\n " +
    			"fourthkey: fourthstyle\n " +
    			"}\n";
    	
    	InputStream stream = new ByteArrayInputStream(css.getBytes());
    	InputStreamReader reader = new InputStreamReader(stream);
    	CssParser cssParser = new CssParser(reader);
    	CssContentHandler cssContentHandler = new CssContentHandler();
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
