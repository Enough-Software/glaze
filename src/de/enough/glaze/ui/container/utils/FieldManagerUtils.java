package de.enough.glaze.ui.container.utils;

import net.rim.device.api.ui.Field;

public class FieldManagerUtils {
	public int getCollapsedVerticalMargin(Field field, Field nextField) {
		return Math.max(field.getMarginBottom(), nextField.getMarginTop());
	}
	
	public int getCollapsedHorizontalMargin(Field field, Field nextField) {
		return Math.max(field.getMarginRight(), nextField.getMarginLeft());
	}
}
