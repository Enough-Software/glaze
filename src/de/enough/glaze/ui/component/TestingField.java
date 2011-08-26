package de.enough.glaze.ui.component;

import net.rim.device.api.ui.Graphics;

public class TestingField extends Field {

	public void gz_layout(int width, int height) {
		setExtent(50,50);
	}

	public void gz_paint(Graphics graphics) {
		graphics.setColor(0xAA00FF);
		graphics.fillRect(0, 0, getExtent().width, getExtent().height);
	}

}
