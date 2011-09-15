package de.enough.glaze.content.filter.impl;

import de.enough.glaze.content.ContentDescriptor;
import de.enough.glaze.content.filter.ContentFilter;

public class HttpContentFilter implements ContentFilter {

	public boolean filter(ContentDescriptor descriptor) {
		return descriptor.getUrl().startsWith("http://");
	}

}
