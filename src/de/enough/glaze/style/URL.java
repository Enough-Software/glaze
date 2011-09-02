package de.enough.glaze.style;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

import net.rim.device.api.servicebook.ServiceBook;
import net.rim.device.api.servicebook.ServiceRecord;
import net.rim.device.api.system.CoverageInfo;
import net.rim.device.api.system.DeviceInfo;
import net.rim.device.api.system.RadioInfo;
import net.rim.device.api.system.WLANInfo;
import de.enough.glaze.content.io.RedirectHttpConnection;

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
		url = url + getConnectionSuffix();
		RedirectHttpConnection connection = new RedirectHttpConnection(url);
		InputStream stream = connection.openInputStream();
		return stream;
	}
	
	public static String getConnectionSuffix()
	{
    	// the final fallback, an unidentified direct TCP connection
    	String connSuffixStr = ";ConnectionTimeout=70000;deviceside=true";
    	
		if (DeviceInfo.isSimulator()) {
			// do nothing
		} else if ((CoverageInfo.getCoverageStatus() & CoverageInfo.COVERAGE_MDS) == CoverageInfo.COVERAGE_MDS) {
			connSuffixStr = ";ConnectionTimeout=70000;deviceside=false";
		} else if (	RadioInfo.isDataServiceOperational() &&
					(CoverageInfo.getCoverageStatus() & CoverageInfo.COVERAGE_DIRECT) == CoverageInfo.COVERAGE_DIRECT) {
			ServiceRecord record = getServiceRecord();
			
			if (record != null) {
				connSuffixStr = ";ConnectionTimeout=70000;deviceside=true;ConnectionUID=" + record.getUid();// WAP2
			} 
		} else if (WLANInfo.getWLANState() == WLANInfo.WLAN_STATE_CONNECTED) { 
			connSuffixStr = ";ConnectionTimeout=70000;interface=wifi";
        }
		
		return connSuffixStr;
	}
	
	 private static ServiceRecord getServiceRecord() {
	    	ServiceRecord[] serviceRecords = ServiceBook.getSB().findRecordsByCid("WPTCP");

			for (int i = 0; i < serviceRecords.length; i++) {
				if (serviceRecords[i] != null && serviceRecords[i].isValid() && !serviceRecords[i].isDisabled()) {
					//#debug info
					System.out.println("Found Service " + serviceRecords[i].getName() + " " + serviceRecords[i].getUid() + "...");
					
					if (serviceRecords[i].getUid() != null && serviceRecords[i].getUid().length() != 0) {
						if ((serviceRecords[i].getUid().toLowerCase().indexOf("wifi") == -1)
							&& (serviceRecords[i].getUid().toLowerCase().indexOf("mms") == -1)) {
							return serviceRecords[i];
						}
					}
				}
			}
			
			return null;
	    }
	
	private InputStream openResourceStream(String url) throws IOException {
		InputStream stream = getClass().getResourceAsStream(url);
		if(stream == null) {
			throw new IOException("resources stream could not be opened : " + url);
		} else {
			return stream;
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.url;
	}
}
