package de.enough.glaze.style.handler;

import net.rim.device.api.ui.Field;

public class FieldStyleHandler {
	
	private Field field;
	
	private int visualState;
	
	public FieldStyleHandler(Field field) {
		this.field = field;
		updateVisualState();
	}
	
	public boolean isVisualStateChanged() {
		return field.getVisualState() != this.visualState;
	}
	
	public void updateVisualState() {
		this.visualState = field.getVisualState();
	}
	
	public Field getField() {
		return this.field;
	}
}
