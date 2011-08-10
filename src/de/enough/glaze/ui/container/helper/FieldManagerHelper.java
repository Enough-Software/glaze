package de.enough.glaze.ui.container.helper;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Manager;
import de.enough.glaze.style.handler.FieldStyleHandlerList;

public class FieldManagerHelper {
	
	public static void add(Manager manager, FieldStyleHandlerList handlers, Field field) {
		manager.add(field);
		handlers.add(field);
	}
	
	public static void addAll(Manager manager, FieldStyleHandlerList handlers, Field[] fields) {
		manager.addAll(fields);
		handlers.addAll(fields);
	}
	
	public static void insert(Manager manager, FieldStyleHandlerList handlers, Field field, int index) {
		manager.insert(field, index);
		handlers.insert(field, index);
	}
	
	public static void delete(Manager manager, FieldStyleHandlerList handlers, Field field) {
		manager.delete(field);
		handlers.delete(field);
	}
	
	public static void deleteRange(Manager manager, FieldStyleHandlerList handlers, int start, int count) {
		manager.deleteRange(start, count);
		handlers.deleteRange(start, count);
	}
	
	public static void deleteAll(Manager manager, FieldStyleHandlerList handlers) {
		manager.deleteAll();
		handlers.deleteAll();
	}
	
	public static void replace(Manager manager, FieldStyleHandlerList handlers, Field oldField, Field newField) {
		manager.replace(oldField, newField);
		handlers.replace(oldField, newField);
	}
}
