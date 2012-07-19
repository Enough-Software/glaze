/*
 *
 * Copyright: (c) 2012 Enough Software GmbH & Co. KG
 *
 * Licensed under:
 * 1. MIT: http://www.opensource.org/licenses/mit-license.php
 * 2. Apache 2.0: http://opensource.org/licenses/apache2.0
 * 3. GPL with classpath exception: http://www.gnu.org/software/classpath/license.html
 *
 * You may not use this file except in compliance with these licenses.
 *
 */
 
package de.enough.glaze.style;

import net.rim.device.api.ui.XYEdges;

/**
 * A class representing a margin in the {@link http
 * ://www.w3.org/TR/CSS2/box.html CSS box model}.
 * 
 * @author Andre
 * 
 */
public class Margin {

	/**
	 * a zero margin
	 */
	public static final Margin ZERO = new Margin();

	/**
	 * the top margin
	 */
	private Dimension top;

	/**
	 * the left margin
	 */
	private Dimension left;

	/**
	 * the bottom margin
	 */
	private Dimension bottom;

	/**
	 * the right margin
	 */
	private Dimension right;

	/**
	 * Creates a new {@link Margin} instance
	 */
	public Margin() {
		// set all margin edges to 0 
		set(Dimension.ZERO);
	}

	/**
	 * Returns the top margin
	 * 
	 * @return the top margin
	 */
	public Dimension getTop() {
		return top;
	}

	public void setTop(Dimension top) {
		this.top = top;
	}

	/**
	 * Returns the top margin
	 * 
	 * @return the top margin
	 */
	public Dimension getLeft() {
		return left;
	}

	/**
	 * Sets the left margin
	 * 
	 * @param left
	 *            the left margin
	 */
	public void setLeft(Dimension left) {
		this.left = left;
	}

	/**
	 * Returns the bottom margin
	 * 
	 * @return the bottom margin
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
	 * Returns the right margin
	 * 
	 * @return the right margin
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
	 * Sets the margin edges to the given dimension
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
	 * Sets the margin edges to the given dimensions according to the {@see
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
	 * Sets the margin edges to the given dimensions according to the {@see
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
	 * Sets the margin edges to the given dimensions according to the {@see
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
	 * Converts and sets the margin in a given {@link XYEdges} instance by using
	 * the given available width
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
	 * Converts and returns the margin as a {@link XYEdges} instance
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
		return "Margin [left:" + this.left + ",top:" + this.top + ",right:"
				+ this.right + ",bottom:" + this.bottom + "]";
	}
}
