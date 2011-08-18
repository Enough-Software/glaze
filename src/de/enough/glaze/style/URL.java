package de.enough.glaze.style;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

public class URL {
	
	private static final String HTTP_PREFIX = "http://"; 
	
	private final String url;
	
	public URL(String url) {
		this.url = url;
	}
	
	public InputStream openStream() throws IOException {
		if(this.url.startsWith(HTTP_PREFIX)) {
			return openHttpStream(url);
		} else {
			return openResourceStream(url);
		}	
	}
	
	private InputStream openHttpStream(String url) throws IOException {
		HttpConnection httpConnection = (HttpConnection)Connector.open(url);
		InputStream stream = httpConnection.openInputStream();
		return stream;
	}
	
	private InputStream openResourceStream(String url) throws IOException {
		InputStream stream = getClass().getResourceAsStream(url);
		if(stream == null) {
			throw new IOException("resources stream could not be opened : " + url);
		} else {
			return stream;
		}
	}
}
