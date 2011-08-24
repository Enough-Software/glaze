package de.enough.glaze.ui.container;

import de.enough.glaze.log.Log;
import net.rim.device.api.ui.Field;

public class VerticalFieldManager extends GzFieldManager {

	public VerticalFieldManager() {
		super();
	}

	public VerticalFieldManager(long style) {
		super(style);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.ui.container.GzFieldManager#sublayout(int, int, int)
	 */
	protected void sublayout(int maxWidth, int maxHeight, int fieldCount) {
		int x = 0;
		int y = 0;

		for (int index = 0; index < fieldCount; index++) {
			Field field = getField(index);
			Log.d("field : " + field);
			Log.d("preferred width : " + field.getPreferredWidth());
			Log.d("preferred height : " + field.getPreferredHeight());
			layoutChild(field, maxWidth, maxHeight);
			Log.d("field width : " + field.getExtent().width);
			Log.d("field height : " + field.getExtent().height);
			setPositionChild(field, x, y);
			y += field.getExtent().height;
		}
		
		setExtent(maxWidth, y);
	}

}
