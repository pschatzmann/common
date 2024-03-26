package ch.pschatzmann.common.table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Table model 
 * 
 * @author pschatzmann
 *
 */
public interface ITable<I> extends  Serializable  {

	/**
	 * Returns the field names of the row key
	 * @return
	 */
	List<String> getRowFieldNames();

	/**
	 * Returns the number of parameter columns
	 * @return
	 */
	int getColumnCount();

	/**
	 * Returns the title of a parameter column
	 * @param col
	 * @return
	 */
	String getColumnTitle(int col);

	/**
	 * Returns the number of rows in the table
	 * @return
	 */
	int getRowCount();

	/**
	 * Returns the key values of the indicated row
	 * @param row
	 * @return
	 */
	List<String> getRowValue(int row);

	/**
	 * Returns the parameter value of the indicated row and column
	 * @param col
	 * @param row
	 * @return
	 */
	I getValue(int col, int row);
	
	/**
	 * Returns the parent table if it was created from a composition. Otherwise we return a reference to itself.
	 * @return
	 */
	ITable getBaseTable();

	/**
	 * Converts the table to a list of Maps which contains the key values and parameter values
	 * @return
	 */
	default List<Map<String,I>> toList() {
		return new TableBackedListOfMap<I>(this);
	}
	
	/**
	 * Returns the hierary of base tables
	 * @return
	 */
	default List<ITable> getBaseTables() {
		List<ITable> result = new ArrayList();
		ITable current = this;
		while(current !=null && !result.contains(current)) {
			result.add(current);
			current = current.getBaseTable();
		}
		return result;
	}
	
	/**
	 * Determines the first base table in the hierarchy which contains the indicated
	 * row (key) field.
	 * @param fieldName
	 * @return
	 */
	default ITable getBaseTableWithFields(String fieldName) {
		for (ITable table : getBaseTables()) {
			if (table.getRowFieldNames().indexOf(fieldName)>=0) {
				return table;
			}
		}
		return null;
	}
	
	/**
	 * Determines the first base table in the hierarchy with the indicated (simple)
	 * class name
	 * @param className
	 * @return
	 */
	default ITable getBaseTableOfClass(String className) {
		for (ITable table : getBaseTables()) {
			if (table.getClass().getSimpleName().equals(className)) {
				return table;
			}
		}
		return null;
	}
	
	/**
	 * Returns true if the table does not contain any records
	 * @return
	 */
	default boolean isEmpty() {
		return this.getRowCount()==0;
	}
	
	/**
	 * Determines the index of the indicated field. If the requested field is not available we return -1
	 * @param fieldName
	 * @return
	 */
	default int getColumnTitleIndex(String fieldName) {
		for (int j=0;j<this.getColumnCount();j++) {
			if (this.getColumnTitle(j).equals(fieldName)){
				return j;
			}
		}
		return -1;
	}

	/**
	 * Returns the index of the key field name
	 * @param fieldName
	 * @return
	 */
	default int getColumnKeyIndex(String fieldName) {
		return this.getRowFieldNames().indexOf(fieldName);
	}

	
	/**
	 * Returns the values of the specified column
	 * @param fieldName
	 * @return
	 */
	default List<I> getColumnValues(String fieldName) {
		List<I> result = Collections.emptyList();
		int colIndex = this.getColumnTitleIndex(fieldName);
		if (colIndex>=0) {
			result = IntStream.range(0, this.getRowCount())
				.mapToObj(row -> this.getValue(colIndex, row))
				.collect(Collectors.toList());
		}
		return result;		
	}
	
	/**
	 * Returns the values of the specified column
	 * @param fieldName
	 * @return
	 */
	default List<String> getColumnKeyValues(String fieldName) {
		List<String> result = Collections.emptyList();
		int colIndex = this.getColumnKeyIndex(fieldName);
		if (colIndex>=0) {
			result = IntStream.range(0, this.getRowCount())
				.mapToObj(row -> this.getRowValue(row).get(colIndex))
				.collect(Collectors.toList());
		}
		return result;		
	}
	

}
