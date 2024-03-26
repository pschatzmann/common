package ch.pschatzmann.common.utils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;
import java.util.Collection;

/**
 * Basic utility function
 * 
 * @author pschatzmann
 *
 */
public class Utils implements Serializable {
	private static DecimalFormatSymbols currentLocaleSymbols = DecimalFormatSymbols.getInstance();
	public static final String NL = "\r\n";
	public static final Object DEL = ";";

	/**
	 * Checks if the string is empty (empty string or null)
	 * 
	 * @param str
	 *            String with or without value
	 * @return true if empty
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}

	/**
	 * Returns the indicated environment variable or if it does not exist the
	 * system property.
	 * 
	 * @param property
	 *            property name
	 * @param defaultValue
	 *            value if no property is defined
	 * @return
	 */
	public static String getProperty(String property, String defaultValue) {
		String value = System.getenv(property);
		return value == null ? System.getProperty(property, defaultValue) : value;
	}

	/**
	 * Returns the last part of a string after any of the following delimiting
	 * characters (_,#,/,:)
	 * 
	 * @param url
	 * @return
	 */

	public static String lastPath(String url) {
		String result = url;
		if (url != null) {
			if (url.contains("_")) {
				result = url.substring(url.lastIndexOf("_") + 1, url.length());
			} else if (url.contains("#")) {
				result = url.substring(url.lastIndexOf("#") + 1, url.length());
			} else if (url.contains("/")) {
				result = url.substring(url.lastIndexOf("/") + 1, url.length());
			} else if (url.contains(":")) {
				result = url.substring(url.lastIndexOf(":") + 1, url.length());
			}
		} else {
			result = "";
		}
		return result;
	}

	/**
	 * Converts a null string to an empty spaces - otherwise the input is
	 * returned
	 * 
	 * @param value
	 * @return
	 */
	public static String notNull(String value) {
		return value == null ? "" : value;
	}

	/**
	 * Converts a null string or empty string to an defaultValue - otherwise the input is
	 * returned
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static String notNull(String value, String defaultValue) {
		return isEmpty(value) ? defaultValue : value.toString();
	}
	/**
	 * Converts a null string or empty string to an defaultValue - otherwise the input is
	 * returned
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static String notNull(Object value, String defaultValue) {
		return value == null || value.toString().isEmpty() ? defaultValue : value.toString();
	}


	/**
	 * Creates a string by repeating the input string n times
	 * 
	 * @param s
	 *            input string
	 * @param n
	 * @return
	 */
	public static String repeat(String s, int n) {
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < n; j++) {
			sb.append(s);
		}
		return sb.toString();
	}

	/**
	 * Executes repeated thread sleep for 1 minutue
	 * 
	 * @throws InterruptedException
	 */
	public static void waitForever() throws InterruptedException {
		while (true) {
			Thread.sleep(1000 * 60);
		}
	}

	/**
	 * Returns true if at least 1 field in any is contained in fields
	 * 
	 * @param fields
	 * @param any
	 * @return
	 */
	public static boolean containsAny(Collection fields, Collection any) {
		for (Object fld : fields) {
			for (Object fld1 : any) {
				if (fld.equals(fld1)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Convert collection to String
	 * 
	 * @param filterValues
	 * @param stringDel
	 * @param delimiter
	 * @return
	 */
	public static String toString(Collection<String> filterValues, String stringDel, String delimiter) {
		StringBuffer sb = new StringBuffer();
		boolean first = true;
		for (String str : filterValues) {
			if (!first)
				sb.append(delimiter);
			first = false;
			sb.append(stringDel);
			sb.append(str);
			sb.append(stringDel);
		}
		return sb.toString();
	}

	/**
	 * Count the occurrences of the findString
	 * 
	 * @param str
	 * @param findStr
	 * @return
	 */
	public static long wordCount(String str, String findStr) {
		int lastIndex = 0;
		int count = 0;

		while (lastIndex != -1) {
			lastIndex = str.indexOf(findStr, lastIndex);

			if (lastIndex != -1) {
				count++;
				lastIndex += findStr.length();
			}
		}

		return count;
	}

	/**
	 * Capitalize the first characters to upper case
	 * 
	 * @param string
	 * @return
	 */
	public static String capitalize(String string) {
		String[] arr = string.split(" ");
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < arr.length; i++) {
			sb.append(Character.toUpperCase(arr[i].charAt(0))).append(arr[i].substring(1)).append(" ");
		}
		return sb.toString().trim();

	}

	/**
	 * Collect the runtime
	 * 
	 * @param context
	 * @param start
	 * @return
	 */
	public static String runtime(String context, long start) {
		StringBuffer sb = new StringBuffer();
		sb.append(context);
		sb.append(" completed in ");
		sb.append((System.currentTimeMillis() - start) / 1000.0);
		sb.append(" sec");
		return sb.toString();
	}

	/**
	 * Return true if the str is a number
	 * 
	 * @param str
	 * @param resultIfEmpty
	 * @return
	 */
	public static Boolean isNumber(String str, Boolean resultIfEmpty) {
		char localeMinusSign = currentLocaleSymbols.getMinusSign();
		if (Utils.isEmpty(str))
			return resultIfEmpty;
		if (!Character.isDigit(str.charAt(0)) && str.charAt(0) != localeMinusSign)
			return false;

		boolean isDecimalSeparatorFound = false;
		char localeDecimalSeparator = currentLocaleSymbols.getDecimalSeparator();

		for (char c : str.substring(1).toCharArray()) {
			if (!Character.isDigit(c)) {
				if (c == localeDecimalSeparator && !isDecimalSeparatorFound) {
					isDecimalSeparatorFound = true;
					continue;
				}
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Converts an object to a number
	 * @param obj
	 * @return
	 */
	public static Number number(Object obj) {
		Number result = null;
		if (obj!=null) {
			if (obj instanceof Number) {
				result = (Number) obj;
			} else {
				String str = obj.toString();
				if (Utils.isNumber(str, false)) {
					result = new BigDecimal(obj.toString());
				}
			}
		}
		return result;
	}

	/**
	 * Converts an object to a Double
	 * @param object
	 * @return
	 */
	public static Double toDouble(Object object) {
		Number number = number(object);
		return number !=null ? number.doubleValue() : null;
	}
	
	/**
	 * Makes sure that we get a string w/o nulls
	 * @param string
	 * @return
	 */
	public static String str(String string) {
		return string==null? "" : string.toString();
	}


}
