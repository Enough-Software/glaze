package mypackage;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.TextField;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;
import de.enough.glaze.ui.component.GzButtonField;
import de.enough.glaze.ui.component.GzTextField;
import de.enough.glaze.ui.container.GzVerticalFieldManager;
import de.enough.glaze.ui.decor.GzSolidBackground;

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
        // Set the displayed title of the screen       
        setTitle("MyTitle"); 
        
        GzVerticalFieldManager manager = new GzVerticalFieldManager();
        GzTextField labelField = new GzTextField(Field.USE_ALL_WIDTH);
        labelField.setBorder(VISUAL_STATE_NORMAL, BorderFactory.createSimpleBorder(new XYEdges(1, 1, 1, 1),new XYEdges(0x000000, 0x000000, 0x000000, 0x000000), Border.STYLE_SOLID));
		labelField.setBorder(VISUAL_STATE_FOCUS, BorderFactory.createSimpleBorder(new XYEdges(1, 1, 1, 1), new XYEdges(0x000000, 0x000000, 0x000000, 0x000000), Border.STYLE_SOLID));
		labelField.setBorder(VISUAL_STATE_ACTIVE, BorderFactory.createSimpleBorder(new XYEdges(1, 1, 1, 1), new XYEdges(0x000000, 0x000000, 0x000000, 0x000000), Border.STYLE_SOLID));
        labelField.setBackground(VISUAL_STATE_NORMAL, new GzSolidBackground(0xFF0000));
        labelField.setBackground(VISUAL_STATE_FOCUS, new GzSolidBackground(0x0000FF));
        labelField.setBackground(VISUAL_STATE_ACTIVE, new GzSolidBackground(0x00FFFF));
        manager.add(labelField);
        
        labelField = new GzTextField(Field.USE_ALL_WIDTH);
		labelField.setBorder(VISUAL_STATE_NORMAL, BorderFactory.createBevelBorder(new XYEdges(2, 2, 2, 2), new XYEdges(0x000000, 0x000000, 0x000000, 0x000000), new XYEdges(0x000000, 0x000000, 0x000000, 0x000000)));
		labelField.setBorder(VISUAL_STATE_FOCUS, BorderFactory.createBevelBorder(new XYEdges(2, 2, 2, 2), new XYEdges(0x000000, 0x000000, 0x000000, 0x000000), new XYEdges(0xFF0000, 0xFF0000, 0xFF0000, 0xFF0000)));
		labelField.setBorder(VISUAL_STATE_ACTIVE, BorderFactory.createBevelBorder(new XYEdges(2, 2, 2, 2), new XYEdges(0x000000, 0x000000, 0x000000, 0x000000), new XYEdges(0x00FF00, 0x00FF00, 0x00FF00, 0x00FF00)));
        labelField.setBackground(VISUAL_STATE_NORMAL, new GzSolidBackground(0xFF0000));
        labelField.setBackground(VISUAL_STATE_FOCUS, new GzSolidBackground(0x0000FF));
        labelField.setBackground(VISUAL_STATE_ACTIVE, new GzSolidBackground(0x00FFFF));
        Font myFont = Font.getDefault().derive(Font.BOLD | Font.UNDERLINED | Font.ITALIC, 50, Ui.UNITS_px);
        labelField.setFont(myFont);
        manager.add(labelField);
        
        add(manager);
    }
}
