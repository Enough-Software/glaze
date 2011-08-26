package de.enough.glaze.ui.container.utils;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.XYRect;
import net.rim.device.api.ui.decor.Border;
import de.enough.glaze.style.Dimension;
import de.enough.glaze.style.Style;

public class ManagerUtils {
	public static int getCollapsedVerticalMargin(Field field, Field nextField) {
		return Math.max(field.getMarginBottom(), nextField.getMarginTop());
	}

	public static int getCollapsedHorizontalMargin(Field field, Field nextField) {
		return Math.max(field.getMarginRight(), nextField.getMarginLeft());
	}

	public static int getLayoutX(XYRect bounds, Field field, long layout) {
		if (layout == Field.FIELD_RIGHT) {
			return (bounds.x + bounds.width) - field.getWidth();
		} else if (layout == Field.FIELD_HCENTER) {
			return bounds.x + ((bounds.width - field.getWidth()) / 2);
		} else {
			return bounds.x;
		}
	}

	public static void layoutField(XYRect rect, Field field, long hAlign, long vAlign) {
		if (hAlign == Field.FIELD_RIGHT) {
			rect.x = (rect.x + rect.width) - field.getWidth();
		} else if (hAlign == Field.FIELD_HCENTER) {
			rect.x = rect.x + ((rect.width - field.getWidth()) / 2);
		}
		
		if (vAlign == Field.FIELD_BOTTOM) {
			rect.y =  rect.y + (rect.height - field.getHeight());
		} else if (vAlign == Field.FIELD_VCENTER) {
			rect.y = rect.y + ((rect.height - field.getHeight()) / 2);
		} 
		
		rect.width = field.getWidth();
		rect.height = field.getHeight();
	}

	private static boolean hasLayout(Field field, long mask, long layout) {
		long fieldLayout = field.getStyle() & mask;
		return fieldLayout == layout;
	}

	public static int getChildLayoutWidth(int maxWidth, int maxHeight,
			Field field, Style style) {
		int borderWidth = 0;
		Border border = field.getBorder();
		if (border != null) {
			borderWidth = border.getLeft() + border.getRight();
		}

		int paddingWidth = field.getPaddingLeft() + field.getPaddingRight();

		if (style != null) {
			Dimension minWidthDimension = style.getMinWidth();
			Dimension widthDimension = style.getWidth();
			Dimension maxWidthDimension = style.getMaxWidth();

			if (minWidthDimension != null) {
				maxWidth = minWidthDimension.getValue(maxWidth);
				return maxWidth + paddingWidth + borderWidth;
			} else if (widthDimension != null) {
				maxWidth = widthDimension.getValue(maxWidth);
				return maxWidth + paddingWidth + borderWidth;
			} else if (maxWidthDimension != null) {
				maxWidth = maxWidthDimension.getValue(maxWidth);
				return maxWidth + paddingWidth + borderWidth;
			}
		}

		return maxWidth;
	}

	public static int getChildLayoutHeight(int maxWidth, int maxHeight,
			Field field, Style style) {
		int borderHeight = 0;
		Border border = field.getBorder();
		if (border != null) {
			borderHeight = border.getTop() + border.getBottom();
		}

		int paddingHeight = field.getPaddingTop() + field.getPaddingBottom();

		if (style != null) {
			Dimension minHeightDimension = style.getMinHeight();
			Dimension heightDimension = style.getHeight();
			Dimension maxHeightDimension = style.getMaxHeight();

			if (minHeightDimension != null) {
				maxHeight = minHeightDimension.getValue(maxWidth);
				return maxHeight + paddingHeight + borderHeight;
			} else if (heightDimension != null) {
				maxHeight = heightDimension.getValue(maxWidth);
				return maxHeight + paddingHeight + borderHeight;
			} else if (maxHeightDimension != null) {
				maxHeight = maxHeightDimension.getValue(maxWidth);
				return maxHeight + paddingHeight + borderHeight;
			}
		}
		return maxHeight;
	}

	/*
	 * public static int getManagerWidth(int width, int height, int maxWidth,
	 * int maxHeight, Manager manager, Style style) { if (style != null) {
	 * Dimension minWidthDimension = style.getMinWidth(); Dimension
	 * widthDimension = style.getWidth(); Dimension maxWidthDimension =
	 * style.getMaxWidth();
	 * 
	 * if (widthDimension != null) { int widthValue =
	 * widthDimension.getValue(maxWidth); return widthValue; }
	 * 
	 * if (minWidthDimension != null) { int minWidthValue =
	 * minWidthDimension.getValue(maxWidth); if (minWidthValue > width) { return
	 * minWidthValue; } }
	 * 
	 * if (maxWidthDimension != null) { int maxWidthValue =
	 * maxWidthDimension.getValue(maxWidth); if (maxWidthValue < width) { return
	 * maxWidthValue; } } }
	 * 
	 * return width; }
	 * 
	 * public static int getManagerHeight(int width, int height, int maxWidth,
	 * int maxHeight, Manager manager, Style style) { if (style != null) {
	 * Dimension minHeightDimension = style.getMinHeight(); Dimension
	 * heightDimension = style.getHeight(); Dimension maxHeightDimension =
	 * style.getMaxHeight();
	 * 
	 * if (heightDimension != null) { int heightValue =
	 * minHeightDimension.getValue(maxWidth); return heightValue; }
	 * 
	 * if (minHeightDimension != null) { int minHeightValue =
	 * minHeightDimension.getValue(maxWidth); if (minHeightValue > height) {
	 * return minHeightValue; } }
	 * 
	 * if (maxHeightDimension != null) { int maxHeightValue =
	 * minHeightDimension.getValue(maxWidth); if (maxHeightValue < height) {
	 * return maxHeightValue; } } }
	 * 
	 * return height; }
	 */
}
