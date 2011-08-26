package mypackage;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.MenuItem;
import de.enough.glaze.style.StyleSheet;
import de.enough.glaze.ui.component.ButtonField;
import de.enough.glaze.ui.component.TextField;
import de.enough.glaze.ui.container.MainScreen;
import de.enough.glaze.ui.container.VerticalFieldManager;

/**
 * A class extending the MainScreen class, which provides default standard
 * behavior for BlackBerry GUI applications.
 */
public final class MyScreen extends MainScreen
{
    /**
     * Creates a new MyScreen object
     */
    public MyScreen()
    {        
    	super(StyleSheet.id("screen"));
        // Set the displayed title of the screen       
        setTitle("MyTitle");
        
        VerticalFieldManager root = new VerticalFieldManager();
        
        VerticalFieldManager content = new VerticalFieldManager(Field.FIELD_RIGHT);
        
        TextField usernameField = new TextField(Field.USE_ALL_WIDTH);
        content.add(usernameField,StyleSheet.id("textfield"));
        
        usernameField = new TextField(Field.USE_ALL_WIDTH);
        content.add(usernameField, StyleSheet.id("textfield"));
        
        ButtonField loginButton = new ButtonField("Login", Field.FIELD_RIGHT);
        content.add(loginButton, StyleSheet.id("button"));
        
        root.add(content, StyleSheet.id("content"));
        add(root);
        
        MenuItem updateItem = new MenuItem("Update", 0, 0) {
        	public void run() {
        		MyApp.updateStyle();
        	}
        };
        
        addMenuItem(updateItem);
    }
}
