package de.enough.glaze.ui.delegate;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.XYRect;
import net.rim.device.api.ui.decor.Border;
import de.enough.glaze.style.Dimension;
import de.enough.glaze.style.Style;

public class ExtentDelegate {

	protected static int preprocessWidth(int width, int height, Field field,
			Style style) {
		if (style != null) {
			int marginWidth = field.getMarginLeft() + field.getMarginRight();
			
			int borderWidth = 0;
			Border border = field.getBorder();
			if (border != null) {
				borderWidth = border.getLeft() + border.getRight();
			}

			int paddingWidth = field.getPaddingLeft() + field.getPaddingRight();
			
			int fullWidth =  marginWidth + borderWidth + paddingWidth + width;
			
			Dimension widthDimension = style.getWidth();
			Dimension maxWidthDimension = style.getMaxWidth();

			if (widthDimension != null) {
				width = widthDimension.getValue(fullWidth);
				return width;
			} else if (maxWidthDimension != null) {
				width = maxWidthDimension.getValue(fullWidth);
				return width;
			}
		}

		return width;
	}

	protected static void postprocessWidth(int width, int height, Field field,
			GzExtent gzExtent, Style style) {
		if (style != null) {
			Dimension minWidthDimension = style.getMinWidth();
			if (minWidthDimension != null) {
				int contentWidth = minWidthDimension.getValue(width);
				XYRect fieldExtent = field.getExtent();
				int extentWidth = getExtentWidth(field, contentWidth);
				if (fieldExtent.width < extentWidth) {
					gzExtent.gz_setExtent(extentWidth, fieldExtent.height);
				}
			}
		}
	}

	private static int getExtentWidth(Field field, int contentWidth) {
		int borderWidth = 0;
		Border border = field.getBorder();
		if (border != null) {
			borderWidth = border.getLeft() + border.getRight();
		}

		int paddingWidth = field.getPaddingLeft() + field.getPaddingRight();

		return paddingWidth + borderWidth + contentWidth;
	}

	protected static int preprocessHeight(int width, int height, Field field,
			Style style) {
		if (style != null) {
			int borderHeight = 0;
			Border border = field.getBorder();
			if (border != null) {
				borderHeight = border.getTop() + border.getBottom();
			}

			int paddingHeight = field.getPaddingTop()
					+ field.getPaddingBottom();

			Dimension heightDimension = style.getHeight();
			Dimension maxHeightDimension = style.getMaxHeight();

			if (heightDimension != null) {
				height = heightDimension.getValue(width);
				return width + paddingHeight + borderHeight;
			} else if (maxHeightDimension != null) {
				height = maxHeightDimension.getValue(width);
				return height + paddingHeight + borderHeight;
			}
		}

		return height;
	}

	protected static void postprocessHeight(int width, int height, Field field,
			GzExtent gzExtent, Style style) {
		if (style != null) {
			Dimension minHeightDimension = style.getMinHeight();
			if (minHeightDimension != null) {
				int contentHeight = minHeightDimension.getValue(width);
				XYRect fieldExtent = field.getExtent();
				int extentHeight = getExtentHeight(field, contentHeight);
				if (fieldExtent.height < extentHeight) {
					gzExtent.gz_setExtent(fieldExtent.width, extentHeight);
				}
			}
		}
	}

	private static int getExtentHeight(Field field, int contentHeight) {
		int borderHeight = 0;
		Border border = field.getBorder();
		if (border != null) {
			borderHeight = border.getTop() + border.getBottom();
		}

		int paddingHeight = field.getPaddingTop() + field.getPaddingBottom();

		return paddingHeight + borderHeight + contentHeight;
	}
}
