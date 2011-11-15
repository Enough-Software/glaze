package de.enough.glaze.style.parser.property.url;

import de.enough.glaze.style.Url;
import de.enough.glaze.style.parser.property.UrlPropertyParser;

/**
 * An interface to handle relative urls in stylesheets. Used by
 * {@link UrlPropertyParser} to resolve urls.
 * 
 * @author Andre
 * 
 */
public interface UrlBuilder {

	/**
	 * Returns an {@link Url} instance with the given base url.
	 * 
	 * @param url
	 *            the url
	 * @return the {@link Url} instance
	 */
	public Url getResourceUrl(String url);
}
