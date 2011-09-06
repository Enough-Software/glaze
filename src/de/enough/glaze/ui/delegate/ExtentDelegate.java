package de.enough.glaze.ui.delegate;

import net.rim.device.api.ui.Field;
import de.enough.glaze.style.Dimension;
import de.enough.glaze.style.Style;
import de.enough.glaze.style.handler.StyleManager;
import de.enough.glaze.style.property.Visibility;

public class ExtentDelegate {

	/**
	 * Returns the preferred height for the given field with respect to
	 * dimensional height properties (e.g. min-height, height)
	 * 
	 * @param field
	 *            the field
	 * @param style
	 *            the style
	 * @return the preferred height
	 */
	protected static int getPreferredWidth(int preferredWidth, Field field,
			Style style) {
		if (style != null) {
			int visibility = style.getVisibility();
			// if the visibility is collapse ...
			if (visibility == Visibility.COLLAPSE) {
				// layout with no height
				return 0;
			}
			// get the min-width, height and max-width dimension
			Dimension minWidthDimension = style.getMinWidth();
			Dimension widthDimension = style.getWidth();
			Dimension maxWidthDimension = style.getMaxWidth();
			if (minWidthDimension != null || widthDimension != null
					|| maxWidthDimension != null) {
				// get the style manager for the field
				StyleManager styleManager = FieldDelegate
						.getStyleManager(field);
				// get the maximum width given to the manager of the field
				int maxWidth = styleManager.getMaxWidth();
				if (widthDimension != null) {
					int widthValue = widthDimension.getValue(maxWidth);
					preferredWidth = widthValue;
				} else if (minWidthDimension != null) {
					int minWidthValue = minWidthDimension.getValue(maxWidth);
					// if the preferred width is smaller than the minimum
					// width ...
					if (preferredWidth < minWidthValue) {
						// adjust the preferred width to the minimum width
						preferredWidth = minWidthValue;
					}
				}

				if (maxWidthDimension != null) {
					int maxWidthValue = maxWidthDimension.getValue(maxWidth);
					// if the preferred width is larger than the maximum width
					// ...
					if (preferredWidth > maxWidthValue) {
						// adjust the preferred width to the maximum width
						preferredWidth = maxWidthValue;
					}
				}

			}
		}

		return preferredWidth;
	}

	/**
	 * Returns the preferred height for the given field with respect to
	 * dimensional height properties (e.g. min-height, height)
	 * 
	 * @param field
	 *            the field
	 * @param style
	 *            the style
	 * @return the preferred height
	 */
	protected static int getPreferredHeight(int preferredHeight, Field field,
			Style style) {
		if (style != null) {
			int visibility = style.getVisibility();
			// if the visibility is collapse ...
			if (visibility == Visibility.COLLAPSE) {
				// layout with no height
				return 0;
			}
			// get the min-height, height and max-height dimension
			Dimension minHeightDimension = style.getMinHeight();
			Dimension heightDimension = style.getHeight();
			Dimension maxHeightDimension = style.getMaxHeight();
			if (minHeightDimension != null || heightDimension != null
					|| maxHeightDimension != null) {
				// get the style manager for the field
				StyleManager styleManager = FieldDelegate
						.getStyleManager(field);
				// get the maximum width given to the manager of the field
				int maxWidth = styleManager.getMaxWidth();

				if (heightDimension != null) {
					int heightValue = heightDimension.getValue(maxWidth);
					preferredHeight = heightValue;
				} else if (minHeightDimension != null) {
					int minHeightValue = minHeightDimension.getValue(maxWidth);
					// if the preferred height is smaller than the minimum
					// height ...
					if (preferredHeight < minHeightValue) {
						// adjust the preferred height to the minimum height
						preferredHeight = minHeightValue;
					}
				}

				if (maxHeightDimension != null) {
					int maxHeightValue = maxHeightDimension.getValue(maxWidth);
					// if the preferred height is larger than the maximum height
					// ...
					if (preferredHeight > maxHeightValue) {
						// adjust the preferred height to the maximum height
						preferredHeight = maxHeightValue;
					}
				}

			}
		}

		return preferredHeight;
	}

	/**
	 * Prepare a fields layout height by using the width and max-width dimension
	 * and returns it
	 * 
	 * @param availableContentWidth
	 *            the available content width
	 * @param field
	 *            the field
	 * @param style
	 *            the style
	 * @return the field layout width
	 */
	protected static int getLayoutWidth(int availableContentWidth, Field field,
			Style style) {
		if (style != null) {
			int visibility = style.getVisibility();
			// if the visibility is collapse ...
			if (visibility == Visibility.COLLAPSE) {
				// layout with no width
				return 0;
			}
			// get the width and max-width dimension
			Dimension widthDimension = style.getWidth();
			Dimension maxWidthDimension = style.getMaxWidth();
			if (widthDimension != null || maxWidthDimension != null) {
				// get the style manager for the field
				StyleManager styleManager = FieldDelegate
						.getStyleManager(field);
				// get the maximum width given to the manager of the field
				int maxWidth = styleManager.getMaxWidth();
				if (widthDimension != null) {
					return widthDimension.getValue(maxWidth);
				} else if (maxWidthDimension != null) {
					return maxWidthDimension.getValue(maxWidth);
				}
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
	protected static int getLayoutHeight(int availableContentHeight,
			Field field, Style style) {
		if (style != null) {
			int visibility = style.getVisibility();
			// if the visibility is collapse ...
			if (visibility == Visibility.COLLAPSE) {
				// layout with no height
				return 0;
			}
			// get the height and max-height dimension
			Dimension heightDimension = style.getHeight();
			Dimension maxHeightDimension = style.getMaxHeight();
			if (heightDimension != null || maxHeightDimension != null) {
				// get the style manager for the field
				StyleManager styleManager = FieldDelegate
						.getStyleManager(field);
				// get the maximum width given to the manager of the field
				int maxWidth = styleManager.getMaxWidth();
				if (heightDimension != null) {
					return heightDimension.getValue(maxWidth);
				} else if (maxHeightDimension != null) {
					return maxHeightDimension.getValue(maxWidth);
				}
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
	protected static void setExtentWidth(Field field, GzExtent gzExtent,
			Style style) {
		if (style != null) {
			int visibility = style.getVisibility();
			// if the visibility is collapse ...
			if (visibility == Visibility.COLLAPSE) {
				// enforce the collapse
				gzExtent.gz_setExtent(0, 0);
				return;
			}
			// get the width and min-width dimension
			Dimension widthDimension = style.getWidth();
			Dimension minWidthDimension = style.getMinWidth();
			if (widthDimension != null || minWidthDimension != null) {
				// get the style manager for the field
				StyleManager styleManager = FieldDelegate
						.getStyleManager(field);
				// get the maximum width given to the manager of the field
				int maxWidth = styleManager.getMaxWidth();
				if (widthDimension != null) {
					int contentWidth = widthDimension.getValue(maxWidth);
					// if the content width of the field is not equal to the
					// given width ...
					if (field.getContentWidth() != contentWidth) {
						// adjust the content width
						gzExtent.gz_setExtent(contentWidth,
								field.getContentHeight());
					}
				} else if (minWidthDimension != null) {
					// if the content width of the field is smaller than the
					// given minimum width ...
					int contentWidth = minWidthDimension.getValue(maxWidth);
					if (field.getContentWidth() < contentWidth) {
						// adjust the content width
						gzExtent.gz_setExtent(contentWidth,
								field.getContentHeight());
					}
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
	protected static void setExtentHeight(Field field, GzExtent gzExtent,
			Style style) {
		if (style != null) {
			int visibility = style.getVisibility();
			// if the visibility is collapse ...
			if (visibility == Visibility.COLLAPSE) {
				// enforce the collapse
				gzExtent.gz_setExtent(0, 0);
				return;
			}
			// get the height and min-height dimension
			Dimension heightDimension = style.getHeight();
			Dimension minHeightDimension = style.getMinHeight();
			if (heightDimension != null || minHeightDimension != null) {
				// get the style manager for the field
				StyleManager styleManager = FieldDelegate
						.getStyleManager(field);
				// get the maximum width given to the manager of the field
				int maxWidth = styleManager.getMaxWidth();
				if (heightDimension != null) {
					int contentHeight = heightDimension.getValue(maxWidth);
					// if the content height of the field is not equal to the
					// given height ...
					if (field.getContentHeight() != contentHeight) {
						// adjust the content height
						gzExtent.gz_setExtent(field.getContentWidth(),
								contentHeight);
					}
				} else if (minHeightDimension != null) {
					// if the content height of the field is smaller than the
					// given minimum height ...
					int contentHeight = minHeightDimension.getValue(maxWidth);
					if (field.getContentHeight() < contentHeight) {
						// adjust the content height
						gzExtent.gz_setExtent(field.getContentWidth(),
								contentHeight);
					}
				}
			}
		}
	}
}
