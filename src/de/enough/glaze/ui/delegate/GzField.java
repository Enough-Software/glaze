package de.enough.glaze.ui.delegate;

import net.rim.device.api.ui.Graphics;

public interface GzField extends GzExtent {
	public void gz_layout(int width, int height);
	
	public void gz_paint(Graphics graphics);
}
