package ch.pschatzmann.common.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ch.pschatzmann.common.utils.Tuple;

/**
 * Table where we can hide columns
 * 
 * @author pschatzmann
 *
 */

public class TableFilteredOnCol implements ITableEx<Number> {
	private static final long serialVersionUID = 1L;
	private ITableEx<Number> table;
	private List<Tuple<String, Integer>> fields = new ArrayList();

	/**
	 * Default Constructor
	 * 
	 * @param table
	 */
	public TableFilteredOnCol(ITableEx table) {
		this.table = table;
		this.fields = IntStream.range(0, table.getColumnCount())
				.mapToObj(col -> new Tuple<String,Integer>(table.getColumnTitle(col), col))
				.collect(Collectors.toList());
	}

	/**
	 * Defines the parameters which need to be removed
	 * @param parameterNames
	 * @return
	 */
	public TableFilteredOnCol removeParameterNames(String... parameterNames) {
		List<String> parametersList = Arrays.asList(parameterNames);
		fields = fields.stream().filter(tuple -> !parametersList.contains(tuple.x)).collect(Collectors.toList());
		return this;
	}

	/**
	 * Defines the parameters which need to be removed
	 * @param parameterNames
	 * @return
	 */
	public TableFilteredOnCol removeParameterNames(List<String> parameterNames) {
		List<String> parametersList = parameterNames;
		fields = fields.stream().filter(tuple -> !parametersList.contains(tuple.x)).collect(Collectors.toList());
		return this;
	}

	@Override
	public List<String> getRowFieldNames() {
		return this.table.getRowFieldNames();
	}

	@Override
	public int getColumnCount() {
		return fields.size();
	}

	@Override
	public String getColumnTitle(int col) {
		return fields.get(col).x;
	}

	@Override
	public int getRowCount() {
		return table.getRowCount();
	}

	@Override
	public List<String> getRowValue(int row) {
		return table.getRowValue(row);
	}

	@Override
	public ITable getBaseTable() {
		return this.table;
	}

	@Override
	public TableFilteredOnCol addColumnKey(String parameterName) {
		 this.table.addColumnKey(parameterName);
		 return this;
	}

	@Override
	public Number getValue(int col, int row) {
		int tableCol = fields.get(col).y;
		return table.getValue(tableCol, row);
	}

}
