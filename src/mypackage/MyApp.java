package mypackage;

import java.io.IOException;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.UiApplication;
import de.enough.glaze.content.ContentException;
import de.enough.glaze.log.Log;
import de.enough.glaze.style.Dimension;
import de.enough.glaze.style.StyleResources;
import de.enough.glaze.style.StyleSheet;
import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.definition.converter.Converter;
import de.enough.glaze.style.extension.Processor;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.DimensionPropertyParser;
import de.enough.glaze.style.parser.property.Property;

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
    	
    	Converter myConverter = new Converter() {

			public String[] getIds() {
				return new String[]{"my-dimension"};
			}

			public Object convert(Definition definition) throws CssSyntaxError {
				if(!definition.hasProperties(this)) {
					return null;
				}
				
				Property myDimensionProp = definition.getProperty("my-dimension");
				
				if(myDimensionProp != null) {
					Object result = DimensionPropertyParser.getInstance().parse(myDimensionProp);
					if(result instanceof Dimension) {
						return (Dimension)result;
					} else {
						throw new CssSyntaxError("must be a single dimension", myDimensionProp);
					}
				}
				
				return null;
			}
    			
    	};
    	
    	Processor myProcessor = new Processor() {

			public void process(Field field, Object object) {
				Log.d("processing field", field);
				Dimension dimension = (Dimension)object;
				Log.d("with dimension", new Integer(dimension.getValue()));
			}
    	};
    	
    	StyleSheet.getInstance().addExtension(myConverter, myProcessor);
    	
        try {
			StyleSheet.getInstance().load("/test.css");
		} catch (IOException e) {
			System.out.println(e);
		} catch (CssSyntaxError e) {
			System.out.println(e);
		}
		
        pushScreen(new MyScreen());
    }    
}
