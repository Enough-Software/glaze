/*
 *
 * Copyright: (c) 2012 Enough Software GmbH & Co. KG
 *
 * Licensed under:
 * 1. MIT: http://www.opensource.org/licenses/mit-license.php
 * 2. Apache 2.0: http://opensource.org/licenses/apache2.0
 * 3. GPL with classpath exception: http://www.gnu.org/software/classpath/license.html
 *
 * You may not use this file except in compliance with these licenses.
 *
 */
 
package de.enough.glaze.content.io;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

import de.enough.glaze.style.Url;

/**
 * Provides a <code>HttpConnection</code> that supports HTTP redirects. This
 * class is compatible to <code>javax.microedition.io.HttpConnection</code>.
 * 
 * <p>
 * When connecting to an URL and a HTTP redirect is return this class follows
 * the redirect and uses the following HTTP connection. This works over multiple
 * levels. By default five redirects are supported. The number of supported
 * redirects can be tuned by setting the preprocessing variable
 * <code>polish.Browser.MaxRedirects</code> to some integer value.
 * </p>
 * 
 * @see HttpConnection
 */
public class RedirectHttpConnection implements HttpConnection {
	private static final int MAX_REDIRECTS = 5;

	private final String originalUrl;
	private String requestMethod = HttpConnection.GET;
	private Hashtable requestProperties;
	HttpConnection httpConnection;
	private ByteArrayOutputStream byteArrayOutputStream;
	private InputStream inputStream;
	private HttpConnection currentHttpConnection;
	private boolean limitContentLengthParams;

	/**
	 * Creates a new http connection that understands redirects.
	 * 
	 * @param url
	 *            the url to connect to
	 * @throws IOException
	 *             when Connector.open() fails
	 */
	public RedirectHttpConnection(String url) throws IOException {
		this(url, null);
	}

	/**
	 * Creates a new http connection that understands redirects.
	 * 
	 * @param url
	 *            the url to connect to
	 * @param requestProperties
	 *            the request properties to be set for each http request
	 * @throws IOException
	 *             when Connector.open() fails
	 */
	public RedirectHttpConnection(String url, Hashtable requestProperties)
			throws IOException {
		this.originalUrl = url;
		this.requestProperties = new Hashtable();

		if (requestProperties != null) {
			Enumeration keys = requestProperties.keys();
			while (keys.hasMoreElements()) {
				String key = (String) keys.nextElement();
				String value = (String) requestProperties.get(key);
				setRequestProperty(key, value);
			}
		}
		this.currentHttpConnection = (HttpConnection) Connector.open(url,
				Connector.READ_WRITE, true);
	}

	/**
	 * Makes sure that the http connect got created. This method redirects until
	 * the final connection is created.
	 * 
	 * @throws IOException
	 *             when the connection failed
	 */
	protected synchronized void ensureConnectionCreated() throws IOException {
		if (this.httpConnection != null) {
			return;
		}

		HttpConnection tmpHttpConnection = this.currentHttpConnection;
		InputStream tmpIn = null;

		int redirects = 0;
		String url = this.originalUrl;

		while (true) {
			url += Url.getConnectionSuffix();
			
			if (tmpHttpConnection == null) {
				tmpHttpConnection = (HttpConnection) Connector.open(url,
						Connector.READ_WRITE, true);
			}
			tmpHttpConnection.setRequestMethod(this.requestMethod);
			if (this.requestProperties != null) {
				Enumeration keys = requestProperties.keys();

				if (keys != null) {
					while (keys.hasMoreElements()) {
						String key = (String) keys.nextElement();
						tmpHttpConnection.setRequestProperty(key,
								(String) this.requestProperties.get(key));
					}
				}
			}

			// Send POST data if exists.
			if (this.byteArrayOutputStream != null) {
				byte[] postData = this.byteArrayOutputStream.toByteArray();

				if (postData != null && postData.length > 0) {

					tmpHttpConnection.setRequestProperty("Content-Length",
							Integer.toString(postData.length));
					if (!this.limitContentLengthParams) {
						tmpHttpConnection.setRequestProperty("Content-length",
								Integer.toString(postData.length));
					}
					OutputStream out = tmpHttpConnection.openOutputStream();
					out.write(postData);
					out.close();
				}
			}

			// Opens the connection.
			tmpIn = tmpHttpConnection.openInputStream();
			int resultCode = tmpHttpConnection.getResponseCode();

			if (resultCode == HttpConnection.HTTP_MOVED_TEMP
					|| resultCode == HttpConnection.HTTP_MOVED_PERM
					|| resultCode == HttpConnection.HTTP_SEE_OTHER
					|| resultCode == HttpConnection.HTTP_TEMP_REDIRECT) {
				String tmpUrl = tmpHttpConnection.getHeaderField("Location");

				// Check if url is relative.
				if (!tmpUrl.startsWith("http://")
						&& !tmpUrl.startsWith("https://")) {
					url += tmpUrl;
				} else {
					url = tmpUrl;
				}

				tmpIn.close(); // close input stream - needed for moto devices,
								// for example
				tmpHttpConnection.close();
				tmpHttpConnection = null; // setting to null is needed for
											// some series 40 devices
				if (++redirects > MAX_REDIRECTS) {
					throw new IOException("too many redirects");
				}

				continue;
			}
			// no redirect, we are at the final connection:
			break;
		}

		this.httpConnection = tmpHttpConnection;
		this.currentHttpConnection = tmpHttpConnection;
		this.inputStream = tmpIn;
	}

	/**
	 * Allows to disable sending of both "Content-Length" and "Content-length"
	 * parameters.
	 * 
	 * @param limit
	 *            false, when only the "Content-Length" header should be set,
	 *            not the "Content-length" request header.
	 */
	public void setLimitContentLengthParams(boolean limit) {
		this.limitContentLengthParams = limit;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.io.HttpConnection#getDate()
	 */
	public long getDate() throws IOException {
		ensureConnectionCreated();
		return this.httpConnection.getDate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.io.HttpConnection#getExpiration()
	 */
	public long getExpiration() throws IOException {
		ensureConnectionCreated();
		return this.httpConnection.getExpiration();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.io.HttpConnection#getFile()
	 */
	public String getFile() {
		try {
			ensureConnectionCreated();
			return this.httpConnection.getFile();
		} catch (IOException e) {
			// #debug error
			System.out.println("Unable to open connection" + e);
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.microedition.io.HttpConnection#getHeaderField(java.lang.String)
	 */
	public String getHeaderField(String name) throws IOException {
		ensureConnectionCreated();
		return this.httpConnection.getHeaderField(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.io.HttpConnection#getHeaderField(int)
	 */
	public String getHeaderField(int n) throws IOException {
		ensureConnectionCreated();
		return this.httpConnection.getHeaderField(n);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.microedition.io.HttpConnection#getHeaderFieldDate(java.lang.String,
	 * long)
	 */
	public long getHeaderFieldDate(String name, long def) throws IOException {
		ensureConnectionCreated();
		return this.httpConnection.getHeaderFieldDate(name, def);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.microedition.io.HttpConnection#getHeaderFieldInt(java.lang.String,
	 * int)
	 */
	public int getHeaderFieldInt(String name, int def) throws IOException {
		ensureConnectionCreated();
		return this.httpConnection.getHeaderFieldInt(name, def);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.io.HttpConnection#getHeaderFieldKey(int)
	 */
	public String getHeaderFieldKey(int n) throws IOException {
		ensureConnectionCreated();
		return this.httpConnection.getHeaderFieldKey(n);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.io.HttpConnection#getHost()
	 */
	public String getHost() {
		return this.currentHttpConnection.getHost();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.io.HttpConnection#getLastModified()
	 */
	public long getLastModified() throws IOException {
		ensureConnectionCreated();
		return this.httpConnection.getLastModified();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.io.HttpConnection#getPort()
	 */
	public int getPort() {
		return this.currentHttpConnection.getPort();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.io.HttpConnection#getProtocol()
	 */
	public String getProtocol() {
		return this.currentHttpConnection.getProtocol();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.io.HttpConnection#getQuery()
	 */
	public String getQuery() {
		return this.currentHttpConnection.getQuery();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.io.HttpConnection#getRef()
	 */
	public String getRef() {
		return this.currentHttpConnection.getRef();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.io.HttpConnection#getRequestMethod()
	 */
	public String getRequestMethod() {
		return this.requestMethod;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.microedition.io.HttpConnection#getRequestProperty(java.lang.String)
	 */
	public String getRequestProperty(String key) {
		return (String) this.requestProperties.get(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.io.HttpConnection#getResponseCode()
	 */
	public int getResponseCode() throws IOException {
		ensureConnectionCreated();
		return this.httpConnection.getResponseCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.io.HttpConnection#getResponseMessage()
	 */
	public String getResponseMessage() throws IOException {
		ensureConnectionCreated();
		return this.httpConnection.getResponseMessage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.io.HttpConnection#getURL()
	 */
	public String getURL() {
		return this.originalUrl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.microedition.io.HttpConnection#setRequestMethod(java.lang.String)
	 */
	public void setRequestMethod(String requestMethod) throws IOException {
		this.requestMethod = requestMethod;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.microedition.io.HttpConnection#setRequestProperty(java.lang.String,
	 * java.lang.String)
	 */
	public void setRequestProperty(String key, String value) throws IOException {
		if (this.requestProperties == null) {
			this.requestProperties = new Hashtable();
		}
		// #if polish.Bugs.HttpIfModifiedSince
		if ("if-modified-since".equals(key.toLowerCase())) {
			Date d = new Date();
			this.requestProperties.put("IF-Modified-Since", d.toString());
		}
		// #endif

		this.requestProperties.put(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.io.ContentConnection#getEncoding()
	 */
	public String getEncoding() {
		try {
			ensureConnectionCreated();
			return this.httpConnection.getEncoding();
		} catch (IOException e) {
			// #debug error
			System.out.println("Unable to establish connection" + e);
			return this.currentHttpConnection.getEncoding();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.io.ContentConnection#getLength()
	 */
	public long getLength() {
		try {
			ensureConnectionCreated();
			return this.httpConnection.getLength();
		} catch (IOException e) {
			// #debug error
			System.out.println("Unable to establish connection" + e);
			return this.currentHttpConnection.getLength();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.io.ContentConnection#getType()
	 */
	public String getType() {
		try {
			ensureConnectionCreated();
			return this.httpConnection.getType();
		} catch (IOException e) {
			// #debug error
			System.out.println("Unable to establish connection" + e);
			return this.currentHttpConnection.getType();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.io.InputConnection#openDataInputStream()
	 */
	public DataInputStream openDataInputStream() throws IOException {
		// TODO: Needs to be synnchronized and the DataInputStream should only
		// be created once.
		return new DataInputStream(openInputStream());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.io.InputConnection#openInputStream()
	 */
	public InputStream openInputStream() throws IOException {
		ensureConnectionCreated();
		return this.inputStream;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.io.Connection#close()
	 */
	public void close() throws IOException {
		// Check if there is a connection to actually close.
		if (this.httpConnection != null) {
			if (this.inputStream != null) {
				try {
					this.inputStream.close();
				} catch (Exception e) {
					// #debug error
					System.out.println("Error while closing input stream" + e);
				}
				this.inputStream = null;
			}
			if (this.byteArrayOutputStream != null) {
				try {
					this.byteArrayOutputStream.close();
				} catch (Exception e) {
					// #debug error
					System.out.println("Error while closing output stream" + e);
				}
				this.byteArrayOutputStream = null;
			}
			this.httpConnection.close();
			this.httpConnection = null;
			this.currentHttpConnection = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.io.OutputConnection#openDataOutputStream()
	 */
	public DataOutputStream openDataOutputStream() throws IOException {
		// TODO: Needs to be synchronized and the DataOutputStream should only
		// be
		// created once.
		return new DataOutputStream(openOutputStream());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.io.OutputConnection#openOutputStream()
	 */
	public synchronized OutputStream openOutputStream() throws IOException {
		if (this.httpConnection != null) {
			return this.httpConnection.openOutputStream();
		}
		if (this.byteArrayOutputStream == null) {
			this.byteArrayOutputStream = new ByteArrayOutputStream();
		}

		return this.byteArrayOutputStream;
	}
}
