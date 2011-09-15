package de.enough.glaze.ui.component;

import net.rim.device.api.ui.Graphics;
import de.enough.glaze.ui.delegate.FieldDelegate;
import de.enough.glaze.ui.delegate.GzField;

public class ActiveAutoTextEditField extends
		net.rim.device.api.ui.component.ActiveAutoTextEditField implements
		GzField {

	public ActiveAutoTextEditField(String label, String initialValue) {
		super(label, initialValue);
	}

	public ActiveAutoTextEditField(String label, String initialValue,
			int maxNumChars) {
		super(label, initialValue, maxNumChars);
	}

	public ActiveAutoTextEditField(String label, String initialValue,
			int maxNumChars, long style) {
		super(label, initialValue, maxNumChars, style);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.component.Field#getPreferredWidth()
	 */
	public int getPreferredWidth() {
		return FieldDelegate.getPreferredWidth(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.component.Field#getPreferredHeight()
	 */
	public int getPreferredHeight() {
		return FieldDelegate.getPreferredHeight(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Field#invalidate(int, int, int, int)
	 */
	public void invalidate(int x, int y, int width, int height) {
		FieldDelegate.invalidate(x, y, width, height, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.component.LabelField#layout(int, int)
	 */
	public void layout(int width, int height) {
		FieldDelegate.layout(width, height, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.rim.device.api.ui.component.LabelField#paint(net.rim.device.api.ui
	 * .Graphics)
	 */
	protected void paint(Graphics graphics) {
		FieldDelegate.paint(graphics, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.ui.delegate.GzField#gz_invalidateAll(int, int, int,
	 * int)
	 */
	public void gz_invalidateAll(int x, int y, int width, int height) {
		super.invalidateAll(x, y, width, height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.ui.component.GzField#gz_layout(int, int)
	 */
	public void gz_layout(int width, int height) {
		super.layout(width, height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.ui.component.GzField#gz_paint(net.rim.device.api.ui.Graphics
	 * )
	 */
	public void gz_paint(Graphics graphics) {
		super.paint(graphics);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.ui.component.GzField#gz_setExtent(int, int)
	 */
	public void gz_setExtent(int width, int height) {
		super.setExtent(width, height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.ui.delegate.GzExtent#gz_getPreferredWidth()
	 */
	public int gz_getPreferredWidth() {
		return super.getPreferredWidth();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.ui.delegate.GzExtent#gz_getPreferredHeight()
	 */
	public int gz_getPreferredHeight() {
		return super.getPreferredHeight();
	}
}
