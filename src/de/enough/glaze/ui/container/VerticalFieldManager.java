package de.enough.glaze.ui.container;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.XYRect;
import de.enough.glaze.style.Style;
import de.enough.glaze.ui.container.utils.ManagerUtils;

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
	protected void sublayout(int maxWidth, int maxHeight, XYRect fieldBounds) {
		int x = 0;
		int y = 0;

		for (int index = 0; index < getFieldCount(); index++) {
			Field field = getField(index);
			Style style = getStyle(index);

			x = field.getMarginLeft();
			// if the field is the first one ...
			if (index == 0) {
				y += field.getMarginTop();
			}

			int fieldMaxWidth = ManagerUtils
					.getMaxWidth(maxWidth, field, style);
			int fieldMaxHeight = ManagerUtils.getMaxHeight(maxHeight, field,
					style);
			layoutChild(field, fieldMaxWidth, fieldMaxHeight);

			// calculate the bounds for the field layout
			fieldBounds.x = x;
			fieldBounds.y = y;
			fieldBounds.width = fieldMaxWidth;
			fieldBounds.height = fieldMaxHeight;
			// get the layouted position
			x = ManagerUtils.getLayoutX(fieldBounds, field);
			y = ManagerUtils.getLayoutY(fieldBounds, field);
			setPositionChild(field, x, y);

			// add the field height
			y += field.getHeight();

			// if the current field is not the last field ...
			if (index < getFieldCount() - 1) {
				// add the collapsed margin
				Field nextField = getField(index + 1);
				y += ManagerUtils.getCollapsedVerticalMargin(field, nextField);
				// otherwise ...
			} else {
				// add the bottom margin
				y += field.getMarginBottom();
			}
		}

		setExtent(maxWidth, y);
	}

}
