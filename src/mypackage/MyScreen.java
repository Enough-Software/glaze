package mypackage;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.TextField;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;
import de.enough.glaze.style.Color;
import de.enough.glaze.style.Dimension;
import de.enough.glaze.style.background.HorizontalGradientBackground;
import de.enough.glaze.style.background.LayerBackground;
import de.enough.glaze.style.background.MaskBackground;
import de.enough.glaze.style.background.PatchBackground;
import de.enough.glaze.style.background.RoundrectBackground;
import de.enough.glaze.style.background.VerticalGradientBackground;
import de.enough.glaze.style.background.ImageBackground;
import de.enough.glaze.style.background.SolidBackground;
import de.enough.glaze.style.definition.converter.BackgroundConverter;
import de.enough.glaze.ui.component.GzButtonField;
import de.enough.glaze.ui.component.GzTextField;
import de.enough.glaze.ui.container.GzVerticalFieldManager;

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
        labelField.setBackground(VISUAL_STATE_NORMAL, new SolidBackground(new Color(0xFF0000)));
        labelField.setBackground(VISUAL_STATE_FOCUS, new SolidBackground(new Color(0x0000FF)));
        labelField.setBackground(VISUAL_STATE_ACTIVE, new SolidBackground(new Color(0x00FFFF)));
        manager.add(labelField);
        
        labelField = new GzTextField(Field.USE_ALL_WIDTH);
		labelField.setBorder(VISUAL_STATE_NORMAL, BorderFactory.createBevelBorder(new XYEdges(2, 2, 2, 2), new XYEdges(0x000000, 0x000000, 0x000000, 0x000000), new XYEdges(0x000000, 0x000000, 0x000000, 0x000000)));
		labelField.setBorder(VISUAL_STATE_FOCUS, BorderFactory.createBevelBorder(new XYEdges(2, 2, 2, 2), new XYEdges(0x000000, 0x000000, 0x000000, 0x000000), new XYEdges(0xFF0000, 0xFF0000, 0xFF0000, 0xFF0000)));
		labelField.setBorder(VISUAL_STATE_ACTIVE, BorderFactory.createBevelBorder(new XYEdges(2, 2, 2, 2), new XYEdges(0x000000, 0x000000, 0x000000, 0x000000), new XYEdges(0x00FF00, 0x00FF00, 0x00FF00, 0x00FF00)));
        labelField.setBackground(VISUAL_STATE_NORMAL, new SolidBackground(new Color(0xFF0000)));
        labelField.setBackground(VISUAL_STATE_FOCUS, new SolidBackground(new Color(0x0000FF)));
        labelField.setBackground(VISUAL_STATE_ACTIVE, new SolidBackground(new Color(0x00FFFF)));
        Font myFont = Font.getDefault().derive(Font.BOLD | Font.UNDERLINED | Font.ITALIC, 50, Ui.UNITS_px);
        labelField.setFont(myFont);
        manager.add(labelField);
        

        
        Bitmap image = EncodedImage.getEncodedImageResource("img/icon.png").getBitmap();
        
        
        RoundrectBackground roundRectBg = new RoundrectBackground(new Color(0xAA00FF00), new Dimension[] {
        	new Dimension(20,Dimension.UNIT_PX), new Dimension(15,Dimension.UNIT_PERCENT), null, new Dimension(2,Dimension.UNIT_PERCENT) });
        VerticalGradientBackground gradientBg = new VerticalGradientBackground(new Color[] { new Color(0xAA00FF00), new Color(0xAAFF0000) },
        		new Dimension[] { new Dimension(20,Dimension.UNIT_PERCENT), new Dimension(80,Dimension.UNIT_PERCENT)});
        ImageBackground imageBg = new ImageBackground(image, ImageBackground.POSITION_BOTTOM | ImageBackground.POSITION_RIGHT, ImageBackground.REPEAT_X);
        
        labelField = new GzTextField(Field.USE_ALL_WIDTH);
        myFont = Font.getDefault().derive(Font.BOLD | Font.UNDERLINED | Font.ITALIC, 150, Ui.UNITS_px);
        labelField.setFont(myFont);
        labelField.setBackground(new MaskBackground(new Color(0xAA00FF00), roundRectBg, gradientBg));
        manager.add(labelField);        
        
        labelField = new GzTextField(Field.USE_ALL_WIDTH);
        myFont = Font.getDefault().derive(Font.BOLD | Font.UNDERLINED | Font.ITALIC, 150, Ui.UNITS_px);
        labelField.setFont(myFont);
        labelField.setBackground(new LayerBackground(new Background[] { gradientBg, imageBg }, new Dimension[] {
        		new Dimension(10, Dimension.UNIT_PX), new Dimension(20, Dimension.UNIT_PX), new Dimension(15, Dimension.UNIT_PX), new Dimension(20, Dimension.UNIT_PX)
        }));
        manager.add(labelField);
        labelField = new GzTextField(Field.USE_ALL_WIDTH);
        myFont = Font.getDefault().derive(Font.BOLD | Font.UNDERLINED | Font.ITALIC, 150, Ui.UNITS_px);
        labelField.setFont(myFont);
        labelField.setBackground(VISUAL_STATE_NORMAL, new ImageBackground(image, ImageBackground.POSITION_BOTTOM | ImageBackground.POSITION_RIGHT, ImageBackground.REPEAT_X));
        labelField.setBackground(VISUAL_STATE_FOCUS, new ImageBackground(image, ImageBackground.POSITION_BOTTOM | ImageBackground.POSITION_RIGHT, ImageBackground.REPEAT_Y | ImageBackground.REPEAT_X ));
        labelField.setBackground(VISUAL_STATE_ACTIVE, new ImageBackground(image, ImageBackground.POSITION_BOTTOM | ImageBackground.POSITION_RIGHT, 0));
        manager.add(labelField);
        
        image = EncodedImage.getEncodedImageResource("img/patch.png").getBitmap();
        labelField = new GzTextField(Field.USE_ALL_WIDTH);
        myFont = Font.getDefault().derive(Font.BOLD | Font.UNDERLINED | Font.ITALIC, 150, Ui.UNITS_px);
        labelField.setFont(myFont);
        PatchBackground patchBg = new PatchBackground(image, new Dimension [] { new Dimension(10, Dimension.UNIT_PX), new Dimension(10, Dimension.UNIT_PX), new Dimension(30, Dimension.UNIT_PX), new Dimension(10, Dimension.UNIT_PX) },
        		new Dimension [] { new Dimension(10, Dimension.UNIT_PX), new Dimension(20, Dimension.UNIT_PX), new Dimension(15, Dimension.UNIT_PX), new Dimension(20, Dimension.UNIT_PX) });
        labelField.setBackground(patchBg);
        manager.add(labelField);
        
        labelField = new GzTextField(Field.USE_ALL_WIDTH);
        myFont = Font.getDefault().derive(Font.BOLD | Font.UNDERLINED | Font.ITALIC, 150, Ui.UNITS_px);
        labelField.setFont(myFont);
        labelField.setBackground(new VerticalGradientBackground(new Color[] { new Color(0xAA00FF00), new Color(0xAAFF0000) },
        		new Dimension[] { new Dimension(20,Dimension.UNIT_PERCENT), new Dimension(80,Dimension.UNIT_PERCENT)}));
        labelField.setBackground(VISUAL_STATE_FOCUS, new HorizontalGradientBackground(new Color[] { new Color(0xAA00FF00), new Color(0xAAFF0000) },
        		new Dimension[] { new Dimension(20,Dimension.UNIT_PERCENT), new Dimension(80,Dimension.UNIT_PERCENT)}));
        manager.add(labelField);
        
        labelField = new GzTextField(Field.USE_ALL_WIDTH);
        myFont = Font.getDefault().derive(Font.BOLD | Font.UNDERLINED | Font.ITALIC, 150, Ui.UNITS_px);
        labelField.setFont(myFont);        
        labelField.setBackground(new RoundrectBackground(new Color(0xAA00FF00), new Dimension[] {
        	new Dimension(20,Dimension.UNIT_PX), new Dimension(15,Dimension.UNIT_PERCENT), null, new Dimension(2,Dimension.UNIT_PERCENT) }));
        manager.add(labelField);
        
        add(manager);
    }
}
