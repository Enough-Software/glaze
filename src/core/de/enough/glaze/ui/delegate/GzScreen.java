package de.enough.glaze.ui.delegate;

/**
 * An interface to extend {@link Screen} instances for the use in Glaze
 * 
 * @author Andre
 * 
 */
public interface GzScreen extends GzManager {

	/**
	 * Return the field manager of an implementing screen.
	 * 
	 * @return the field manager
	 */
	public GzManager getFieldManager();
}
