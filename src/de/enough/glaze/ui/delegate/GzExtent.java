package de.enough.glaze.ui.delegate;

public interface GzExtent {
	/**
	 * Must call super.setExtent(width,height) in an implementing field
	 * 
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 */
	public void gz_setExtent(int width, int height);
}
