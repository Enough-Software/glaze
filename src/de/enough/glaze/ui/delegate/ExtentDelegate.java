package de.enough.glaze.ui.delegate;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.decor.Border;
import de.enough.glaze.style.Dimension;
import de.enough.glaze.style.Style;

public class ExtentDelegate {

	/**
	 * Returns the full width that is available to a field (including padding,
	 * borders and margin)
	 * 
	 * @param width
	 *            the available content width
	 * @param field
	 *            the field
	 * @return the full width
	 */
	private static int getAvailableWidth(int availableContentWidth, Field field) {
		// get the horizontal margin extent
		int marginWidth = field.getMarginLeft() + field.getMarginRight();

		// get the horizontal border extent
		int borderWidth = 0;
		Border border = field.getBorder();
		if (border != null) {
			borderWidth = border.getLeft() + border.getRight();
		}

		// get the horizontal padding extent
		int paddingWidth = field.getPaddingLeft() + field.getPaddingRight();

		// add the extents to the available content width to get the full
		// available width
		return marginWidth + borderWidth + paddingWidth + availableContentWidth;
	}

	/**
	 * Prepare a fields layout height by using the width and max-width dimension
	 * and returns it
	 * 
	 * @param availableContentWidth
	 *            the available content width
	 * @param availableContentHeight
	 *            the available content height
	 * @param field
	 *            the field
	 * @param style
	 *            the style
	 * @return the field layout width
	 */
	protected static int preprocessWidth(int availableContentWidth,
			int availableContentHeight, Field field, Style style) {
		if (style != null) {
			// retrieve the full width available to a field
			int availableWidth = getAvailableWidth(availableContentWidth, field);

			// get the width and max-width dimension
			Dimension widthDimension = style.getWidth();
			Dimension maxWidthDimension = style.getMaxWidth();

			if (widthDimension != null) {
				// calculate the available content width based on the full
				// available width
				availableContentWidth = widthDimension.getValue(availableWidth);
				return availableContentWidth;
			} else if (maxWidthDimension != null) {
				availableContentWidth = maxWidthDimension
						.getValue(availableWidth);
				return availableContentWidth;
			}
		}

		return availableContentWidth;
	}

	/**
	 * Prepare a fields layout height by using the height and max-height
	 * dimension and returns it
	 * 
	 * @param availableContentWidth
	 *            the available content width
	 * @param availableContentHeight
	 *            the available content height
	 * @param field
	 *            the field
	 * @param style
	 *            the style
	 * @return the field layout height
	 */
	protected static int preprocessHeight(int availableContentWidth,
			int availableContentHeight, Field field, Style style) {
		if (style != null) {
			// retrieve the full width available to a field
			int availableWidth = getAvailableWidth(availableContentWidth, field);

			Dimension heightDimension = style.getHeight();
			Dimension maxHeightDimension = style.getMaxHeight();

			if (heightDimension != null) {
				// calculate the available content width based on the full
				// available width
				availableContentWidth = heightDimension
						.getValue(availableWidth);
				return availableContentWidth;
			} else if (maxHeightDimension != null) {
				availableContentWidth = maxHeightDimension
						.getValue(availableWidth);
				return availableContentWidth;
			}
		}

		return availableContentHeight;
	}

	/**
	 * Adjusts a fields width using the min-width and width dimension
	 * 
	 * @param availableContentWidth
	 *            the available content width
	 * @param availableContentHeight
	 *            the available content height
	 * @param field
	 *            the field
	 * @param gzExtent
	 *            the fields extent interface
	 * @param style
	 *            the style
	 */
	protected static void postprocessWidth(int availableContentWidth,
			int availableContentHeight, Field field, GzExtent gzExtent,
			Style style) {
		if (style != null) {
			int availableWidth = getAvailableWidth(availableContentWidth, field);
			Dimension widthDimension = style.getWidth();
			Dimension minWidthDimension = style.getMinWidth();
			if (widthDimension != null) {
				int contentWidth = widthDimension.getValue(availableWidth);
				if (field.getContentWidth() != contentWidth) {
					gzExtent.gz_setExtent(contentWidth,
							field.getContentHeight());
				}
			} else if (minWidthDimension != null) {
				int contentWidth = minWidthDimension.getValue(availableWidth);
				if (field.getContentWidth() < contentWidth) {
					gzExtent.gz_setExtent(contentWidth,
							field.getContentHeight());
				}
			}
		}
	}

	/**
	 * Adjusts a fields height using the min-height and height dimension
	 * 
	 * @param availableContentWidth
	 *            the available content width
	 * @param availableContentHeight
	 *            the available content height
	 * @param field
	 *            the field
	 * @param gzExtent
	 *            the fields extent interface
	 * @param style
	 *            the style
	 */
	protected static void postprocessHeight(int availableContentWidth,
			int availableContentHeight, Field field, GzExtent gzExtent,
			Style style) {
		if (style != null) {
			int availableWidth = getAvailableWidth(availableContentWidth, field);
			Dimension heightDimension = style.getHeight();
			Dimension minHeightDimension = style.getMinHeight();
			if (heightDimension != null) {
				int contentHeight = heightDimension.getValue(availableWidth);
				if (field.getContentHeight() != contentHeight) {
					gzExtent.gz_setExtent(field.getContentWidth(),
							contentHeight);
				}
			} else if (minHeightDimension != null) {
				int contentHeight = minHeightDimension.getValue(availableWidth);
				if (field.getContentHeight() < contentHeight) {
					gzExtent.gz_setExtent(field.getContentWidth(),
							contentHeight);
				}
			}
		}
	}
}
