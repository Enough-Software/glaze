package de.enough.glaze.ui.delegate;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import de.enough.glaze.log.Log;
import de.enough.glaze.style.Color;
import de.enough.glaze.style.Style;
import de.enough.glaze.style.font.GzFont;
import de.enough.glaze.style.handler.FieldStyleHandler;
import de.enough.glaze.style.handler.FieldStyleManager;

public class FieldDelegate {
	public static void layout(int width, int height, Field field) {
		GzField gzField;
		if (field instanceof GzField) {
			gzField = (GzField) field;
			Style style = getStyle(field);
			int preprocessedWidth = ExtentDelegate.preprocessWidth(width, height, field, style);
			int preprocessedHeight = ExtentDelegate.preprocessHeight(width, height, field, style);
			gzField.gz_layout(preprocessedWidth, preprocessedHeight);
			ExtentDelegate.postprocessWidth(width, height, field, gzField, style);
			ExtentDelegate.postprocessHeight(width, height, field, gzField, style);
		} else {
			Log.e("field must implement GzField", field);
		}
	}


	public static void paint(Graphics graphics, Field field) {
		GzField gzField;
		if (field instanceof GzField) {
			gzField = (GzField) field;
			int originalColor = applyFontColor(graphics, field);
			gzField.gz_paint(graphics);
			graphics.setColor(originalColor);
		} else {
			Log.e("field must implement GzField", field);
		}
	}
	
	private static int applyFontColor(Graphics graphics, Field field) {
		int originalColor =  graphics.getColor();
		Style style = getStyle(field);
		if(style != null) {
			GzFont font = style.getFont();
			if(font != null) {
				Color fontColor = font.getColor();
				graphics.setColor(fontColor.getColor());
			}
		} 
		return originalColor;
	}

	public static void drawFocus(Graphics graphics, boolean on) {
		// simply do nothing
	}

	protected static Style getStyle(Field field) {
		Manager manager = field.getManager();
		if (manager instanceof GzManager) {
			GzManager gzManager = (GzManager) manager;
			FieldStyleManager styleManager = gzManager.getStyleManager();
			FieldStyleHandler styleHandler = styleManager.get(field);
			return styleHandler.getStyle();
		} 
		return null;
	}
}
