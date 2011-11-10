package de.enough.glaze.style;

import java.io.IOException;
import java.io.InputStream;

import net.rim.device.api.servicebook.ServiceBook;
import net.rim.device.api.servicebook.ServiceRecord;
import net.rim.device.api.system.CoverageInfo;
import net.rim.device.api.system.DeviceInfo;
import net.rim.device.api.system.RadioInfo;
import net.rim.device.api.system.WLANInfo;
import de.enough.glaze.content.io.RedirectHttpConnection;

/**
 * A class representing a URL. {@link #openStream()} interprets and opens an
 * {@link InputStream} to the URL location. Supported URLs are the application
 * resources (res://) and web-based resources (http://) and relatives.
 * 
 * @author Andre
 * 
 */
public class Url {

	/**
	 * the resources protocol
	 */
	private static final String PROTOCOL_RESOURCE = "res";

	/**
	 * the http protocol
	 */
	private static final String PROTOCOL_HTTP = "http";

	/**
	 * the https protocol
	 */
	private static final String PROTOCOL_HTTPS = "https";

	private static final int PORT_HTTP = 80;

	private String protocol;

	private String host;

	private int port;

	private String path;

	private String file;

	private String query;

	private String url;

	private long lastModified;

	private boolean modified;

	/**
	 * Constructs a new {@link Url} instance
	 * 
	 * @param url
	 *            the url
	 */
	public Url(String url) {
		this(url, null);
	}

	/**
	 * Constructs a new {@link Url} instance
	 * 
	 * @param url
	 *            the url
	 * @param context
	 *            the context for url
	 */
	public Url(String url, Url context) {
		this.protocol = "";
		this.host = "";
		this.port = PORT_HTTP;
		this.path = "";
		this.file = "";
		this.lastModified = 0;
		this.modified = true;
		parse(url, context);
	}

	/**
	 * Parses the url with the context
	 */
	private void parse(String url, Url context) {
		String result = url;

		int index = result.indexOf("://");
		// if a protocol could be found ...
		if (index != -1) {
			// parse the protocol
			this.protocol = result.substring(0, index);
			result = result.substring(index + "://".length());
			if (!PROTOCOL_RESOURCE.equals(this.protocol)) {
				// parse the host and port
				index = result.indexOf('/');
				if (index < 0) {
					index = result.indexOf('?');
				}
				String server;
				if (index >= 0) {
					server = result.substring(0, index);
				} else {
					server = result;
				}

				if (index >= 0) {
					result = result.substring(index);
				} else {
					result = "";
				}

				index = server.indexOf(':');
				if (index >= 0) {
					this.host = server.substring(0, index);
					String port = server.substring(index + 1);
					if (port.length() > 0) {
						this.port = Integer.parseInt(port);
					} else {
						this.port = PORT_HTTP;
					}
				} else {
					this.host = server;
					this.port = PORT_HTTP;
				}
			}
			// otherwise ...
		} else if (context != null) {
			// use the context data to fill the url
			this.protocol = context.getProtocol();
			this.host = context.getHost();
			this.port = context.getPort();
			this.path = context.getPath();
			this.file = context.getFile();
		} else {
			this.protocol = PROTOCOL_RESOURCE;
		}

		// if a file and path is given ...
		if (result.length() > 0) {
			// find the path end
			int pathEnd = result.lastIndexOf('/');
			// if there is a valid path ...
			if (pathEnd > 0) {
				String path = result.substring(0, pathEnd);
				// if this path is absolute ...
				if (path.startsWith("/")) {
					// store the path
					this.path = path;
					// otherwise ...
				} else {
					// join the context path and the path and store it
					this.path = this.path + '/' + path;
				}

				// if a file is present ...
				if (pathEnd != result.length() - 1) {
					// store the file
					this.file = result.substring(pathEnd);
				}
				// if no path is given ...
			} else if (result.startsWith("/")) {
				// store the file
				this.file = result;
			} else {
				this.file = '/' + result;
			}
		}
	}

	/**
	 * Opens an {@link InputStream} to the url
	 * 
	 * @return the created {@link InputStream}
	 * @throws IOException
	 *             if an error occurs
	 */
	public InputStream openStream() throws IOException {
		if (PROTOCOL_HTTPS.equals(this.protocol)
				|| PROTOCOL_HTTP.equals(this.protocol)) {
			return openHttpStream();
		} else {
			return openResourceStream();
		}
	}

	/**
	 * Opens an {@link InputStream} to the given web-based url
	 * 
	 * @param url
	 *            the url
	 * @return the created {@link InputStream}
	 * @throws IOException
	 *             if an error occurs
	 */
	private InputStream openHttpStream() throws IOException {
		String url = getUrl();
		// add the connection suffix
		url = url + getConnectionSuffix();
		RedirectHttpConnection connection = new RedirectHttpConnection(url);
		long lastModified = connection.getLastModified();
		this.modified = lastModified != this.lastModified;
		this.lastModified = lastModified;
		InputStream stream = connection.openInputStream();
		return stream;
	}

	/**
	 * Returns the BlackBerry connection suffix used to open connections to
	 * web-based resources
	 * 
	 * @return the BlackBerry connection suffix
	 */
	public static String getConnectionSuffix() {
		// the final fallback, an unidentified direct TCP connection
		String connSuffixStr = ";ConnectionTimeout=70000;deviceside=true";

		if (DeviceInfo.isSimulator()) {
			// do nothing
		} else if ((CoverageInfo.getCoverageStatus() & CoverageInfo.COVERAGE_MDS) == CoverageInfo.COVERAGE_MDS) {
			connSuffixStr = ";ConnectionTimeout=70000;deviceside=false";
		} else if (RadioInfo.isDataServiceOperational()
				&& (CoverageInfo.getCoverageStatus() & CoverageInfo.COVERAGE_DIRECT) == CoverageInfo.COVERAGE_DIRECT) {
			ServiceRecord record = getServiceRecord();

			if (record != null) {
				connSuffixStr = ";ConnectionTimeout=70000;deviceside=true;ConnectionUID="
						+ record.getUid();// WAP2
			}
		} else if (WLANInfo.getWLANState() == WLANInfo.WLAN_STATE_CONNECTED) {
			connSuffixStr = ";ConnectionTimeout=70000;interface=wifi";
		}

		return connSuffixStr;
	}

	/**
	 * Returns the {@link ServiceRecord} instance for the current connection
	 * type
	 * 
	 * @return the {@link ServiceRecord} instance
	 */
	private static ServiceRecord getServiceRecord() {
		ServiceRecord[] serviceRecords = ServiceBook.getSB().findRecordsByCid(
				"WPTCP");

		for (int i = 0; i < serviceRecords.length; i++) {
			if (serviceRecords[i] != null && serviceRecords[i].isValid()
					&& !serviceRecords[i].isDisabled()) {
				// #debug info
				System.out.println("Found Service "
						+ serviceRecords[i].getName() + " "
						+ serviceRecords[i].getUid() + "...");

				if (serviceRecords[i].getUid() != null
						&& serviceRecords[i].getUid().length() != 0) {
					if ((serviceRecords[i].getUid().toLowerCase()
							.indexOf("wifi") == -1)
							&& (serviceRecords[i].getUid().toLowerCase()
									.indexOf("mms") == -1)) {
						return serviceRecords[i];
					}
				}
			}
		}

		return null;
	}

	/**
	 * Opens an {@link InputStream} to the given resource url
	 * 
	 * @param url
	 *            the url
	 * @return the created {@link InputStream}
	 * @throws IOException
	 *             if an error occurs
	 */
	private InputStream openResourceStream() throws IOException {
		try {
			return getClass().getResourceAsStream(getUrl());
		} catch (IllegalArgumentException e) {
			throw new IOException("unable to load " + url);
		}
	}

	/**
	 * Returns the protocol
	 * 
	 * @return the protocol
	 */
	public String getProtocol() {
		return this.protocol;
	}

	/**
	 * Returns the host
	 * 
	 * @return the host
	 */
	public String getHost() {
		return this.host;
	}

	/**
	 * Returns the port
	 * 
	 * @return the port
	 */
	public int getPort() {
		return this.port;
	}

	/**
	 * Returns the path
	 * 
	 * @return the path
	 */
	public String getPath() {
		return this.path;
	}

	/**
	 * Returns the file
	 * 
	 * @return the file
	 */
	public String getFile() {
		return this.file;
	}

	/**
	 * Returns the query
	 * 
	 * @return the query
	 */
	public String getQuery() {
		return this.query;
	}

	/**
	 * Returns the time the contents of the url have been modified.
	 * 
	 * @return the time the contents of the url have been modified.
	 */
	public long getLastModified() {
		synchronized (this) {
			return this.lastModified;
		}
	}

	/**
	 * Returns true if the contents of this Url has been modified
	 * 
	 * @return true if the contents of this Url has been modified otherwise
	 *         false
	 */
	public boolean isModified() {
		return this.modified;
	}

	/**
	 * Returns the url
	 * 
	 * @return the url
	 */
	private String getUrl() {
		// if the url has not been build yet ...
		if (this.url == null) {
			// build it
			StringBuffer urlBuffer = new StringBuffer();
			if (PROTOCOL_HTTPS.equals(this.protocol)
					|| PROTOCOL_HTTP.equals(this.protocol)) {
				urlBuffer.append(this.protocol).append("://").append(this.host);
				if (this.port != PORT_HTTP) {
					urlBuffer.append(":").append(this.port);
				}
				urlBuffer.append(this.path);
				urlBuffer.append(this.file);
			} else {
				urlBuffer.append(this.path);
				urlBuffer.append(this.file);
			}
			this.url = urlBuffer.toString();
		}

		return this.url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getUrl();
	}
}
