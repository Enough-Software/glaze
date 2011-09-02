package de.enough.glaze.ui.component;

import de.enough.glaze.ui.delegate.FieldDelegate;
import de.enough.glaze.ui.delegate.GzField;
import net.rim.device.api.ui.Graphics;

public class ButtonField extends net.rim.device.api.ui.component.ButtonField
		implements GzField {

	public ButtonField() {
		super(0);
	}

	public ButtonField(long style) {
		super(style);
	}

	public ButtonField(String label) {
		super(label);
	}

	public ButtonField(String label, long style) {
		super(label, style);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.component.ButtonField#getPreferredWidth()
	 */
	public int getPreferredWidth() {
		// if fiels style contains USE_ALL_WIDTH ...
		if ((getStyle() & Field.USE_ALL_WIDTH) == Field.USE_ALL_WIDTH) {
			// return the maximum width
			return Integer.MAX_VALUE;
			// otherwise ...
		} else {
			// return the preferred width
			return super.getPreferredWidth();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.component.LabelField#layout(int, int)
	 */
	protected void layout(int width, int height) {
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
}
