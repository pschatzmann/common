package ch.pschatzmann.common.table;

import java.io.Serializable;

/**
 * Factory method which returns a formatter for the requested 
 * format
 * @author pschatzmann
 *
 */
public class TableFormatterFactory implements Serializable {
	
	public static ITableFormatter getFormatter(String format) throws FormatException {
		if ("csv".equalsIgnoreCase(format)) {
			return new TableFormatterCSV();
		}
		if ("html".equalsIgnoreCase(format)) {
			return new TableFormatterHtml();
		}
		if ("json".equalsIgnoreCase(format)) {
			return new TableFormatterJson();
		}
		throw new FormatException("The format '"+format+"' is not supported");
		
	}
}
