package de.enough.glaze.style;

import net.rim.device.api.ui.XYEdges;

/**
 * A class representing a padding in the {@link http
 * ://www.w3.org/TR/CSS2/box.html CSS box model}.
 * 
 * @author Andre
 * 
 */
public class Padding {

	/**
	 * a zero padding
	 */
	public static final Padding ZERO = new Padding();

	/**
	 * the top padding
	 */
	private Dimension top;

	/**
	 * the right padding
	 */
	private Dimension right;

	/**
	 * the bottom padding
	 */
	private Dimension bottom;

	/**
	 * the left padding
	 */
	private Dimension left;

	/**
	 * Creates a new {@link Padding} instance
	 */
	public Padding() {
		// set all padding edges to 0
		set(Dimension.ZERO);
	}

	/**
	 * Returns the top padding
	 * 
	 * @return the top padding
	 */
	public Dimension getTop() {
		return top;
	}

	public void setTop(Dimension top) {
		this.top = top;
	}

	/**
	 * Returns the left padding
	 * 
	 * @return the left padding
	 */
	public Dimension getLeft() {
		return left;
	}

	/**
	 * Sets the left padding
	 * 
	 * @param left
	 *            the left padding
	 */
	public void setLeft(Dimension left) {
		this.left = left;
	}

	/**
	 * Returns the bottom padding
	 * 
	 * @return the bottom padding
	 */
	public Dimension getBottom() {
		return bottom;
	}

	/**
	 * Sets the bottom margin
	 * 
	 * @param bottom
	 *            the bottom margin
	 */
	public void setBottom(Dimension bottom) {
		this.bottom = bottom;
	}

	/**
	 * Returns the right padding
	 * 
	 * @return the right padding
	 */
	public Dimension getRight() {
		return right;
	}

	/**
	 * Sets the right margin
	 * 
	 * @param right
	 *            the right margin
	 */
	public void setRight(Dimension right) {
		this.right = right;
	}

	/**
	 * Sets the padding edges to the given dimension
	 * 
	 * @param dimension
	 *            the dimension
	 */
	public void set(Dimension dimension) {
		setTop(dimension);
		setLeft(dimension);
		setBottom(dimension);
		setRight(dimension);
	}

	/**
	 * Sets the padding edges to the given dimensions according to the {@see
	 * http://www.w3.org/TR/CSS2/box.html#value-def-margin-width W3C
	 * specifications}
	 * 
	 * @param topBottom
	 *            the top/bottom margin
	 * @param leftRight
	 *            the left/right margin
	 */
	public void set(Dimension topBottom, Dimension leftRight) {
		setTop(topBottom);
		setLeft(leftRight);
		setBottom(topBottom);
		setRight(leftRight);
	}

	/**
	 * Sets the padding edges to the given dimensions according to the {@see
	 * http://www.w3.org/TR/CSS2/box.html#value-def-margin-width W3C
	 * specifications}
	 * 
	 * @param top
	 *            the top margin
	 * @param leftRight
	 *            the left/right margin
	 * @param bottom
	 *            the bottom margin
	 */
	public void set(Dimension top, Dimension leftRight, Dimension bottom) {
		setTop(top);
		setLeft(leftRight);
		setBottom(bottom);
		setRight(leftRight);
	}

	/**
	 * Sets the padding edges to the given dimensions according to the {@see
	 * http://www.w3.org/TR/CSS2/box.html#value-def-margin-width W3C
	 * specifications}
	 * 
	 * @param top
	 *            the top margin
	 * @param right
	 *            the right margin
	 * @param bottom
	 *            the bottom margin
	 * @param left
	 *            the left margin
	 */
	public void set(Dimension top, Dimension right, Dimension bottom,
			Dimension left) {
		setTop(top);
		setLeft(left);
		setBottom(bottom);
		setRight(right);
	}

	/**
	 * Converts and sets the padding in a given {@link XYEdges} instance by
	 * using the given available width
	 * 
	 * @param xyEdges
	 *            the {@link XYEdges} instance
	 * @param availableWidth
	 *            the available width
	 */
	public void setEdges(XYEdges xyEdges, int availableWidth) {
		int leftPixels = this.left.getValue(availableWidth);
		int topPixels = this.top.getValue(availableWidth);
		int rightPixels = this.right.getValue(availableWidth);
		int bottomPixels = this.bottom.getValue(availableWidth);
		xyEdges.set(topPixels, rightPixels, bottomPixels, leftPixels);
	}

	/**
	 * Converts and returns the padding as a {@link XYEdges} instance
	 * 
	 * @param availableWidth
	 *            the available width
	 * @return the created {@link XYEdges} instances
	 */
	public XYEdges toEdges(int availableWidth) {
		XYEdges edges = new XYEdges();
		setEdges(edges, availableWidth);
		return edges;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Padding [left:" + this.left + ",top:" + this.top + ",right:"
				+ this.right + ",bottom:" + this.bottom + "]";
	}
}
