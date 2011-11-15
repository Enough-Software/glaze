package de.enough.glaze.style.parser.property.url;

import de.enough.glaze.style.StyleSheet;
import de.enough.glaze.style.Url;
import de.enough.glaze.style.parser.property.UrlPropertyParser;

/**
 * The default implementation of {@link UrlBuilder} used by
 * {@link UrlPropertyParser}.
 * 
 * @author Andre
 * 
 */
public class DefaultUrlBuilder implements UrlBuilder {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.style.parser.property.url.UrlBuilder#getResourceUrl(java
	 * .lang.String)
	 */
	public Url getResourceUrl(String url) {
		// use the stylesheet url to use as a context for the given url
		Url stylesheetUrl = StyleSheet.getInstance().getUrl();
		return new Url(url.toString(), stylesheetUrl);
	}

}
