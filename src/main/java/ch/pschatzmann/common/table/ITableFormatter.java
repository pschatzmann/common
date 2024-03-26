package ch.pschatzmann.common.table;

import java.io.Serializable;

/**
 * Formats the content of the table as String
 * 
 * @author pschatzmann
 *
 */
public interface ITableFormatter<T> extends  Serializable {
	public String format(ITable<T> table ) throws FormatException;

}
