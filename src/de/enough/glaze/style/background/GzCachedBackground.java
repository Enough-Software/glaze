package de.enough.glaze.style.background;

import java.util.Hashtable;

import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.XYRect;
import de.enough.glaze.log.Log;

public abstract class GzCachedBackground extends GzBackground {

	private final Hashtable rectBufferMap;

	public GzCachedBackground() {
		this.rectBufferMap = new Hashtable();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.style.background.GzBackground#draw(net.rim.device.api
	 * .ui.Graphics, net.rim.device.api.ui.XYRect)
	 */
	public void draw(Graphics graphics, XYRect rect) {
		adjustRect(rect);

		int[] buffer = (int[]) this.rectBufferMap.get(rect);

		if (buffer == null) {
			Log.d("create mask");
			buffer = create(rect.width, rect.height);
			this.rectBufferMap.put(rect, buffer);
		}

		draw(graphics, rect.x, rect.y, rect.width, rect.height, buffer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.style.background.GzBackground#draw(net.rim.device.api
	 * .ui.Graphics, int, int, int, int)
	 */
	public void draw(Graphics graphics, int x, int y, int width, int height) {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see de.enough.glaze.style.background.GzBackground#release()
	 */
	public void release() {
		this.rectBufferMap.clear();
	}
	
	/**
	 * Creates the buffer for the background to draw
	 * @param width the width
	 * @param height the height
	 * @return the created buffer
	 */
	public abstract int[] create(int width, int height);

	public abstract void draw(Graphics graphics, int x, int y, int width,
			int height, int[] buffer);
	

}
