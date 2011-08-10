package de.enough.glaze.ui.container;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import de.enough.glaze.style.handler.FieldStyleHandler;
import de.enough.glaze.style.handler.FieldStyleHandlerList;

public class GzVerticalFieldManager extends net.rim.device.api.ui.container.VerticalFieldManager {

	private FieldStyleHandlerList handlers;

	public GzVerticalFieldManager() {
		super();
		this.handlers = new FieldStyleHandlerList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Manager#add(net.rim.device.api.ui.Field)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Manager#add(net.rim.device.api.ui.Field)
	 */
	public void add(Field field) {
		super.add(field);
		this.handlers.add(field);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Manager#addAll(net.rim.device.api.ui.Field[])
	 */
	public void addAll(Field[] fields) {
		super.addAll(fields);
		this.handlers.addAll(fields);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Manager#insert(net.rim.device.api.ui.Field,
	 * int)
	 */
	public void insert(Field field, int index) {
		super.insert(field, index);
		this.handlers.insert(field, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Manager#delete(net.rim.device.api.ui.Field)
	 */
	public void delete(Field field) {
		super.delete(field);
		this.handlers.delete(field);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Manager#deleteAll()
	 */
	public void deleteAll() {
		super.deleteAll();
		this.handlers.deleteAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Manager#deleteRange(int, int)
	 */
	public void deleteRange(int start, int count) {
		super.deleteRange(start, count);
		this.handlers.deleteRange(start, count);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Manager#replace(net.rim.device.api.ui.Field,
	 * net.rim.device.api.ui.Field)
	 */
	public void replace(Field oldField, Field newField) {
		super.replace(oldField, newField);
		this.handlers.replace(oldField, newField);
	}
	
	/* (non-Javadoc)
	 * @see net.rim.device.api.ui.container.VerticalFieldManager#sublayout(int, int)
	 */
	protected void sublayout(int maxWidth, int maxHeight) {
		for (int index = 0; index < getFieldCount(); index++) {
			Field field = getField(index);
			FieldStyleHandler handler = this.handlers.get(index);
			if(handler.isVisualStateChanged()) {
				handler.updateVisualState();
			} 
		}
		
		super.sublayout(maxWidth, maxHeight);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.rim.device.api.ui.container.VerticalFieldManager#subpaint(net.rim
	 * .device.api.ui.Graphics)
	 */
	protected void subpaint(Graphics graphics) {
		for (int index = 0; index < getFieldCount(); index++) {
			Field field = getField(index);
			FieldStyleHandler handler = this.handlers.get(index);
			if(handler.isVisualStateChanged()) {
				updateLayout();
			}

			paintChild(graphics, field);
		}
	}
}
