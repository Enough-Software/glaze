package de.enough.glaze.version;

import java.util.Hashtable;

import net.rim.device.api.system.DeviceInfo;

/**
 * Helper class for version-related tasks.
 * 
 * @author Andre
 * 
 */
public class Version {

	/**
	 * The version comparison map.
	 */
	private static Hashtable VERSION_MAP;

	/**
	 * The software version of the running device.
	 */
	private static final String VERSION = DeviceInfo.getSoftwareVersion();

	static {
		VERSION_MAP = new Hashtable();
	}

	/**
	 * Return true if the given version is greater than the software version of
	 * the running device.
	 * 
	 * @param version
	 *            the version
	 * @return true if the given version is greater than the software version
	 *         otherwise false
	 */
	public static boolean isGreaterThan(String version) {
		Boolean result = (Boolean) VERSION_MAP.get(version);
		if (result == null) {
			result = new Boolean(VERSION.compareTo(version) >= 0);
			VERSION_MAP.put(version, result);
		}
		return result.booleanValue();
	}

	/**
	 * Return true if the given version is less than the software version of the
	 * running device.
	 * 
	 * @param version
	 *            the version
	 * @return true if the given version is less than the software version
	 *         otherwise false
	 */
	public static boolean isLessThan(String version) {
		return !isGreaterThan(version);
	}
}
