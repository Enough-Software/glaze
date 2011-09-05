package de.enough.glaze.style;

import net.rim.device.api.ui.XYEdges;
import de.enough.glaze.style.property.border.GzBorder;
import de.enough.glaze.style.property.font.GzFont;

/**
 * Tests the given styles if a layout update in the is needed when a field with
 * the given first style is assigned the given second style. A layout update is
 * needed if the area that a field occupies changes. The area is influenced by
 * the style's margin, padding, border width, dimensional values (e.g.
 * min-width, height) and font extent.
 * 
 * @author Andre
 * 
 */
public class LayoutUpdateTest {

	/**
	 * The available width used for the tests
	 */
	private static final int AVAILABLE_TEST_WIDTH = 1000;

	/**
	 * the instance
	 */
	private static LayoutUpdateTest INSTANCE;

	/**
	 * Returns the instance
	 * 
	 * @return the instance
	 */
	public static LayoutUpdateTest getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new LayoutUpdateTest();
		}

		return INSTANCE;
	}

	/**
	 * Creates a new {@link LayoutUpdateTest} instance
	 */
	private LayoutUpdateTest() {
		// no instantiation allowed
	}

	/**
	 * Compares the given styles to determine if the area of the field styled
	 * with the first style might change if the second style is applied and thus
	 * a layout update is needed
	 * 
	 * @param firstStyle
	 *            the first style
	 * @param secondStyle
	 *            the second style
	 * @return true if the layout needs to be updated otherwise false
	 */
	public boolean test(Style firstStyle, Style secondStyle) {
		// return true if one of the relevant style properties changes
		if (marginChanges(firstStyle, secondStyle)
				|| paddingChanges(firstStyle, secondStyle)
				|| borderChanges(firstStyle, secondStyle)
				|| dimensionChanges(firstStyle, secondStyle)
				|| fontChanges(firstStyle, secondStyle)
				|| visibilityChanges(firstStyle, secondStyle)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns true if the margin edges of the first style differ from the
	 * margin edges of the second style
	 * 
	 * @param firstStyle
	 *            the first style
	 * @param secondStyle
	 *            the second style
	 * @return true if the margin edges differ otherwise false
	 */
	private boolean marginChanges(Style firstStyle, Style secondStyle) {
		Margin firstMargin = firstStyle.getMargin();
		Margin secondMargin = secondStyle.getMargin();

		XYEdges firstEdges = firstMargin.toEdges(AVAILABLE_TEST_WIDTH);
		XYEdges secondEdges = secondMargin.toEdges(AVAILABLE_TEST_WIDTH);

		return edgesChange(firstEdges, secondEdges);
	}

	/**
	 * Returns true if the padding edges of the first style differ from the
	 * padding edges of the second style
	 * 
	 * @param firstStyle
	 *            the first style
	 * @param secondStyle
	 *            the second style
	 * @return true if the padding edges differ otherwise false
	 */
	private boolean paddingChanges(Style firstStyle, Style secondStyle) {
		Padding firstPadding = firstStyle.getPadding();
		Padding secondPadding = secondStyle.getPadding();

		XYEdges firstEdges = firstPadding.toEdges(AVAILABLE_TEST_WIDTH);
		XYEdges secondEdges = secondPadding.toEdges(AVAILABLE_TEST_WIDTH);

		return edgesChange(firstEdges, secondEdges);
	}

	/**
	 * Returns true if the first edges differ from the second edges
	 * 
	 * @param firstEdges
	 *            the first edges
	 * @param secondEdges
	 *            the second edges
	 * @return true if the edges differ otherwise false
	 */
	private boolean edgesChange(XYEdges firstEdges, XYEdges secondEdges) {
		return firstEdges.top != secondEdges.top
				|| firstEdges.right != secondEdges.right
				|| firstEdges.bottom != secondEdges.bottom
				|| firstEdges.left != secondEdges.left;
	}

	/**
	 * Returns true if the border edges of the first style differs from the
	 * border edges of the second style
	 * 
	 * @param firstStyle
	 *            the first style
	 * @param secondStyle
	 *            the second style
	 * @return true if the edges differ otherwise false
	 */
	private boolean borderChanges(Style firstStyle, Style secondStyle) {
		GzBorder firstBorder = firstStyle.getBorder();
		GzBorder secondBorder = secondStyle.getBorder();

		XYEdges firstEdges = firstBorder.getEdges();
		XYEdges secondEdges = secondBorder.getEdges();

		return edgesChange(firstEdges, secondEdges);
	}

	/**
	 * Returns true if the dimensional properties of the given first style
	 * differ from the dimensional properties of the given second style
	 * 
	 * @param firstStyle
	 *            the first style
	 * @param secondStyle
	 *            the second style
	 * @return true if the dimensional properties differ otherwise false
	 */
	private boolean dimensionChanges(Style firstStyle, Style secondStyle) {
		// compare the height properties
		Dimension firstMinHeightDimension = firstStyle.getMinHeight();
		Dimension secondMinHeightDimension = secondStyle.getMinHeight();
		boolean minHeightChanges = dimensionChanges(firstMinHeightDimension,
				secondMinHeightDimension);

		Dimension firstHeightDimension = firstStyle.getHeight();
		Dimension secondHeightDimension = secondStyle.getHeight();
		boolean heightChanges = dimensionChanges(firstHeightDimension,
				secondHeightDimension);

		Dimension firstMaxHeightDimension = firstStyle.getMaxHeight();
		Dimension secondMaxHeightDimension = secondStyle.getMaxHeight();
		boolean maxHeightChanges = dimensionChanges(firstMaxHeightDimension,
				secondMaxHeightDimension);

		// compare the width properties
		Dimension firstMinWidthDimension = firstStyle.getMinWidth();
		Dimension secondMinWidthDimension = secondStyle.getMinWidth();
		boolean minWidthChanges = dimensionChanges(firstMinWidthDimension,
				secondMinWidthDimension);

		Dimension firstWidthDimension = firstStyle.getWidth();
		Dimension secondWidthDimension = secondStyle.getWidth();
		boolean widthChanges = dimensionChanges(firstWidthDimension,
				secondWidthDimension);

		Dimension firstMaxWidthDimension = firstStyle.getMaxWidth();
		Dimension secondMaxWidthDimension = secondStyle.getMaxWidth();
		boolean maxWidthChanges = dimensionChanges(firstMaxWidthDimension,
				secondMaxWidthDimension);

		return minHeightChanges || heightChanges || maxHeightChanges
				|| minWidthChanges || widthChanges || maxWidthChanges;
	}

	/**
	 * Returns true if the first dimension differs from the second dimension
	 * 
	 * @param firstDimension
	 *            the first dimension
	 * @param secondDimension
	 *            the second dimension
	 * @return true if dimensions differ otherwise false
	 */
	private boolean dimensionChanges(Dimension firstDimension,
			Dimension secondDimension) {
		if (firstDimension != null && secondDimension != null) {
			return firstDimension.getValue() != secondDimension.getValue();
		} else if (firstDimension != null) {
			return true;
		} else if (secondDimension != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns true if the font extent of the first style differs from the font
	 * extent of the second style
	 * 
	 * @param firstStyle
	 *            the first style
	 * @param secondStyle
	 *            the second style
	 * @return true if the extents differ otherwise false
	 */
	private boolean fontChanges(Style firstStyle, Style secondStyle) {
		GzFont firstFont = firstStyle.getFont();
		GzFont secondFont = secondStyle.getFont();

		boolean nameChanges = !firstFont.getName().equals(secondFont.getName());
		boolean heightChanges = firstFont.getFont().getHeight() != secondFont
				.getFont().getHeight();
		boolean styleChanges = firstFont.getFont().getStyle() != secondFont
				.getFont().getStyle();

		return nameChanges || heightChanges || styleChanges;
	}

	/**
	 * Returns true if the visibility of the first style differs from the
	 * visibility of the second style
	 * 
	 * @param firstStyle
	 *            the first style
	 * @param secondStyle
	 *            the second style
	 * @return true if the visibility differs otherwise false
	 */
	private boolean visibilityChanges(Style firstStyle, Style secondStyle) {
		return firstStyle.getVisibility() != secondStyle.getVisibility();
	}
}
