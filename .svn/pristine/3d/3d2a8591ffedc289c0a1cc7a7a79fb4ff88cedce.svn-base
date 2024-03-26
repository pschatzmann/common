package ch.pschatzmann.common.table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import ch.pschatzmann.common.utils.Utils;

/**
 * ITable where we remove some key fields and consolidate the values with the help of the 
 * ConsolidationOperation. The operation can be defined per Parameter.
 * 
 * @author pschatzmann
 *
 */
public class TableConsolidated implements ITableEx<Value> {
	private static final long serialVersionUID = 1L;

	public enum ConsolidationOperation {
		Min, Max, Replace, Sum
	}

	private ITableEx<Value> baseTable;
	private List<String> rowFieldNames;
	private List<RowKey> rows = new ArrayList();
	private Map<ValueKey, Value> values = new TreeMap<ValueKey, Value>();
	private List<String> messages = new ArrayList();
	private Map<String,ConsolidationOperation> operationForParameters = new TreeMap();
	private ConsolidationOperation consolidationOperation = ConsolidationOperation.Replace;

	/**
	 * Constructor for internal user
	 * @param operation
	 */
	protected TableConsolidated(ConsolidationOperation operation) {
		this.consolidationOperation = operation;
	}


	
	/**
	 * Minimum Constructor
	 * 
	 * @param baseTable
	 * @param keysToRemove
	 */
	public TableConsolidated(ITableEx<Value> baseTable, List<String> keysToRemove) {
		this(baseTable, keysToRemove, ConsolidationOperation.Replace);
	}

	/**
	 * Maximum Constructor
	 * 
	 * @param baseTable
	 * @param keysToRemove
	 * @param defaultOperation
	 */
	public TableConsolidated(ITableEx<Value> baseTable, List<String> keysToRemove, ConsolidationOperation defaultOperation) {
		this.baseTable = baseTable;
		this.consolidationOperation = defaultOperation;

		rowFieldNames = new ArrayList<String>(baseTable.getRowFieldNames());
		rowFieldNames.removeAll(keysToRemove);

		// get rows
		List<Integer> posToRemove = keysToRemove.stream().map(v -> baseTable.getRowFieldNames().indexOf(v))
				.sorted()
				.collect(Collectors.toList());
		Collections.reverse(posToRemove);

		for (int row = 0; row < baseTable.getRowCount(); row++) {
			RowKey rowKey = getRowKey(baseTable, posToRemove, row);
			if (!rows.contains(rowKey)) {
				rows.add(rowKey);
			}
			for (int col = 0; col < baseTable.getColumnCount(); col++) {
				Value value =  new Value(baseTable.getValue(col, row));
				addValue(rowKey, col, value);
			}
		}
	}

	protected RowKey getRowKey(ITable<Value> baseTable, List<Integer> posToRemove, int row) {
		List<String> keyValues = new ArrayList<String>(baseTable.getRowValue(row));
		posToRemove.forEach(pos -> keyValues.remove((int) pos));
		RowKey rowKey = new RowKey(keyValues);
		return rowKey;
	}

	protected void addValue(RowKey row, int col, Value value) {
		if (value!=null && value.doubleValue()!=0.0) {
			ValueKey key = new ValueKey(row, col);
			Value existingValue = values.get(key);
			if (existingValue == null) {
				values.put(key, value);
			} else {
				switch (getConsolidationOperation(this.baseTable.getColumnTitle(col))) {
				case Replace:
					values.put(key, value);
					if (!existingValue.equals(value)) {
						messages.add("Replaced " + row + ": " + existingValue + " -> " + value);
					}
					break;
				case Min:
					values.put(key, new Value(Math.min(doubleValue(existingValue), doubleValue(value))));
					break;
				case Max:
					values.put(key, new Value(Math.max(doubleValue(existingValue), doubleValue(value))));
					break;
				case Sum:
					values.put(key, new Value(doubleValue(existingValue) + doubleValue(value)));
					break;
				}
			}
		}
	}

	protected double doubleValue(Object value) {
		if (value == null)
			return 0;
		if (value instanceof Number) {
			return ((Number) value).doubleValue();
		}
		return Double.valueOf(value.toString());
	}

	@Override
	public List<String> getRowFieldNames() {
		return rowFieldNames;
	}

	@Override
	public List<String> getRowValue(int row) {
		return rows.get(row).values;
	}

	@Override
	public int getColumnCount() {
		return baseTable.getColumnCount();
	}

	@Override
	public String getColumnTitle(int col) {
		return baseTable.getColumnTitle(col);
	}

	@Override
	public int getRowCount() {
		return rows.size();
	}

	@Override
	public Value getValue(int col, int row) {
		RowKey rowKey = rows.get(row);
		return values.get(new ValueKey(rowKey, col));
	}

	public class ValueKey implements Comparable<ValueKey> {
		private RowKey row;
		private int col;

		public ValueKey(RowKey row, int col) {
			this.row = row;
			this.col = col;
		}

		@Override
		public int compareTo(ValueKey o) {
			int result = this.row.compareTo(o.row);
			if (result != 0) {
				return result;
			}
			result = col - o.col;
			return result;
		}
		
		@Override
		public boolean equals(final Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final ValueKey other = (ValueKey) obj;
			return compareTo(other) == 0;
		}
	}

	public static class RowKey implements Comparable<RowKey> {
		public List<String> values;

		public RowKey(List<String> keyValues) {
			this.values = keyValues;
		}

		@Override
		public int compareTo(RowKey o) {
			int result = 0;
			for (int j = 0; j < values.size(); j++) {
				result = Utils.str(values.get(j)).compareTo(Utils.str(o.values.get(j)));
				if (result != 0) {
					return result;
				}
			}
			return result;
		}

		@Override
		public String toString() {
			return values == null ? "" : values.toString();
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final RowKey other = (RowKey) obj;
			return compareTo(other) == 0;
		}

	}

	/**
	 * Provides replacement messages where the value has been corrected by another entry
	 * @return
	 */
	public List<String> getMessages() {
		return this.messages;
	}
	
	/**
	 * Provides the underlying base table
	 * @return
	 */
	public ITable getTable() {
		return this.baseTable;
	}

	/**
	 * Provides the the parameter specific consolidation information
	 * @return
	 */
	public Map<String, ConsolidationOperation> getOperationForParameters() {
		return operationForParameters;
	}

	/**
	 * Defines the parameter specific consolidation information
	 * @param operationForParameters
	 */
	public void addOperationForParameter(Map<String, ConsolidationOperation> operationForParameters) {
		this.operationForParameters.putAll(operationForParameters);
	}

	/**
	 * Defines the parameter specific consolidation information
	 * @param parameter
	 * @param operationForParameters
	 */
	public void addOperationForParameter(String parameter, ConsolidationOperation operationForParameters) {
		this.operationForParameters.put(parameter, operationForParameters);
	}

	/**
	 * Determines the consolidation operation for a parameter
	 * @param parameterName
	 * @return
	 */
	protected ConsolidationOperation getConsolidationOperation(String parameterName) {
		ConsolidationOperation result = this.operationForParameters.get(parameterName);
		return result!=null ? result : this.consolidationOperation;
	}

	@Override
	public TableConsolidated addColumnKey(String parameterName) {
		baseTable.addColumnKey(parameterName);
		return this;
	}


	@Override
	public ITable getBaseTable() {
		return this.baseTable;
	}
	
	public void setRowFieldNames(List<String> names) {
		this.rowFieldNames = names;
	}


}
