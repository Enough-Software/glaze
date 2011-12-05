package de.enough.glaze.util;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Utility class for working with strings
 * @author Ovidiu
 *
 */
public class StringUtil {

	/**
	 * Replaces all occurences of a pattern within a string with another string
	 * @param source the source string
	 * @param pattern the pattern to look for
	 * @param replacement the replacement string
	 * @return the resulting string
	 */
	public static String replaceAll(String source, String pattern, String replacement)
	{    
	    //If source is null then Stop
	    //and retutn empty String.
	    if (source == null)
	    {
	        return "";
	    }

	    StringBuffer sb = new StringBuffer();
	    //Intialize Index to -1
	    //to check agaist it later
	    int idx = -1;
	    //Search source from 0 to first occurrence of pattern
	    //Set Idx equal to index at which pattern is found.

	    String workingSource = source;

	    //Iterate for the Pattern till idx is not be -1.
	    while ((idx = workingSource.indexOf(pattern)) != -1)
	    {
	        //append all the string in source till the pattern starts.
	        sb.append(workingSource.substring(0, idx));
	        //append replacement of the pattern.
	        sb.append(replacement);
	        //Append remaining string to the String Buffer.
	        sb.append(workingSource.substring(idx + pattern.length()));

	        //Store the updated String and check again.
	        workingSource = sb.toString();

	        //Reset the StringBuffer.
	        sb.delete(0, sb.length());
	    }

	    return workingSource;
	}
	
	/**
	 * Replaces all patterns of the form %<pattern_name>% in the original string with the ones in the replacement hashtable
	 * @param sourceString the source string
	 * @param patterns a hashtable of the form (<pattern_name>,<replacement_string>)
	 * @return the resulting string
	 */
	public static String replacePatterns(String sourceString, Hashtable patterns) {
		String result = sourceString;
		Enumeration keys = patterns.keys();
		Enumeration values = patterns.elements();
		while ( keys.hasMoreElements() ) {
			String key = (String) keys.nextElement();
			String value = (String) values.nextElement();
			result = replaceAll(result, "%" + key + "%", value);
		}
		return result;
	}

	/**
	 * Tokenizes a string according to a given delimiter
	 * @param strString the source string
	 * @param strDelimiter the delimiter
	 * @return
	 */
	public static String[] split(String strString, String strDelimiter) {
	    String[] strArray;
	    int iOccurrences = 0;
	    int iIndexOfInnerString = 0;
	    int iIndexOfDelimiter = 0;
	    int iCounter = 0;

	    //Check for null input strings.
	    if (strString == null) {
	        throw new IllegalArgumentException("Input string cannot be null.");
	    }
	    //Check for null or empty delimiter strings.
	    if (strDelimiter.length() <= 0 || strDelimiter == null) {
	        throw new IllegalArgumentException("Delimeter cannot be null or empty.");
	    }

	    //strString must be in this format: (without {} )
	    //"{str[0]}{delimiter}str[1]}{delimiter} ...
	    // {str[n-1]}{delimiter}{str[n]}{delimiter}"

	    //If strString begins with delimiter then remove it in order
	    //to comply with the desired format.

	    if (strString.startsWith(strDelimiter)) {
	        strString = strString.substring(strDelimiter.length());
	    }

	    //If strString does not end with the delimiter then add it
	    //to the string in order to comply with the desired format.
	    if (!strString.endsWith(strDelimiter)) {
	        strString += strDelimiter;
	    }

	    //Count occurrences of the delimiter in the string.
	    //Occurrences should be the same amount of inner strings.
	    while((iIndexOfDelimiter = strString.indexOf(strDelimiter,
	           iIndexOfInnerString)) != -1) {
	        iOccurrences += 1;
	        iIndexOfInnerString = iIndexOfDelimiter +
	            strDelimiter.length();
	    }

	    //Declare the array with the correct size.
	    strArray = new String[iOccurrences];

	    //Reset the indices.
	    iIndexOfInnerString = 0;
	    iIndexOfDelimiter = 0;

	    //Walk across the string again and this time add the
	    //strings to the array.
	    while((iIndexOfDelimiter = strString.indexOf(strDelimiter,
	           iIndexOfInnerString)) != -1) {

	        //Add string to array.
	        strArray[iCounter] = strString.substring(iIndexOfInnerString,iIndexOfDelimiter);

	        //Increment the index to the next character after
	        //the next delimiter.
	        iIndexOfInnerString = iIndexOfDelimiter +
	            strDelimiter.length();

	        //Inc the counter.
	        iCounter += 1;
	    }

	    return strArray;
	}
}
