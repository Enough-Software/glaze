package de.enough.glaze.style;

import net.rim.device.api.ui.XYEdges;

public class Margin {
	
	public static final Margin ZERO = new Margin();

	private Dimension top;

	private Dimension left;

	private Dimension bottom;

	private Dimension right;

	public Margin() {
		set(Dimension.ZERO);
	}

	public Dimension getTop() {
		return top;
	}

	public void setTop(Dimension top) {
		this.top = top;
	}

	public Dimension getLeft() {
		return left;
	}

	public void setLeft(Dimension left) {
		this.left = left;
	}

	public Dimension getBottom() {
		return bottom;
	}

	public void setBottom(Dimension bottom) {
		this.bottom = bottom;
	}

	public Dimension getRight() {
		return right;
	}

	public void setRight(Dimension right) {
		this.right = right;
	}
	
	public void set(Dimension dimension) {
		setTop(dimension);
		setLeft(dimension);
		setBottom(dimension);
		setRight(dimension);
	}

	public void set(Dimension topBottom, Dimension leftRight) {
		setTop(topBottom);
		setLeft(leftRight);
		setBottom(topBottom);
		setRight(leftRight);
	}

	public void set(Dimension top, Dimension leftRight, Dimension bottom) {
		setTop(top);
		setLeft(leftRight);
		setBottom(bottom);
		setRight(leftRight);
	}

	public void set(Dimension top, Dimension left, Dimension bottom,
			Dimension right) {
		setTop(top);
		setLeft(left);
		setBottom(bottom);
		setRight(right);
	}

	public void setXYEdges(XYEdges xyEdges, int availableWidth) {
		int leftPixels = this.left.getValue(availableWidth);
		int topPixels = this.top.getValue(availableWidth);
		int rightPixels = this.right.getValue(availableWidth);
		int bottomPixels = this.bottom.getValue(availableWidth);
		xyEdges.set(topPixels, rightPixels, bottomPixels, leftPixels);
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
