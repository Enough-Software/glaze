package de.enough.glaze.ui.delegate;

import de.enough.glaze.style.handler.FieldStyleManager;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;

public interface GzManager extends GzExtent {
	public void add(Field field);
	
	public void add(Field field, String name);
	
	public void addAll(Field[] fields);
	
	public void insert(Field field, int index);
	
	public void delete(Field field);
	
	public void deleteAll();
	
	public void deleteRange(int start, int count);
	
	public void replace(Field oldField, Field newField);
	
	public void gz_sublayout(int maxWidth, int maxHeight);

	public void gz_paintChild(Graphics graphics, Field field);
	
	public void gz_updateLayout();
	
	public FieldStyleManager getStyleManager();
}
