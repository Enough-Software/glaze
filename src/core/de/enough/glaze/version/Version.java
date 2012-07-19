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
 
package de.enough.glaze.version;

import java.util.Hashtable;

import net.rim.device.api.system.DeviceInfo;
import de.enough.glaze.util.StringUtil;

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
	private static final String[] VERSION;

	static {
		VERSION = expandVersion(DeviceInfo.getSoftwareVersion());
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
			String[] expandedVersion = expandVersion(version);
			result = new Boolean(isGreaterThan(VERSION, expandedVersion));
			VERSION_MAP.put(version, result);
		}
		return result.booleanValue();
	}

	/**
	 * Returns true if the given version to compare is greater than the software version of
	 * the running device.
	 * 
	 * @param softwareVersion
	 * @param compareVersion
	 * @return
	 */
	private static boolean isGreaterThan(String[] softwareVersion,
			String[] compareVersion) {
		for (int index = 0; index < softwareVersion.length; index++) {
			int softwareVersionNumber = Integer.valueOf(softwareVersion[index]).intValue();
			int compareVersionNumber = Integer.valueOf(
					compareVersion[index]).intValue();
			if (softwareVersionNumber < compareVersionNumber) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Expands the version to a String array.
	 * 
	 * @param version
	 *            the version
	 * @return the String array
	 */
	private static String[] expandVersion(String version) {
		String[] result = new String[10];
		String[] expanded = StringUtil.split(version, ".");

		for (int index = 0; index < result.length; index++) {
			if (index < expanded.length) {
				result[index] = expanded[index];
			} else {
				result[index] = "0";
			}
		}

		return result;
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
