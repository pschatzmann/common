package ch.pschatzmann.common.table;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

/**
 * Table where we support the filtering of rows
 * 
 * @author pschatzmann
 *
 */
public class TableFilteredOnRow implements ITableEx<Number> {
	private static final long serialVersionUID = 1L;
	private ITableEx<Number> table;
	private List<Integer> positions = new ArrayList();
	
	public TableFilteredOnRow(ITableEx<Number> table, BiFunction<ITable, Integer, Boolean> filter){
		this.table = table;
		IntStream.range(0,table.getRowCount())
			.filter(pos -> filter.apply(table, pos))
			.forEach(pos -> positions.add(pos));
	}

	@Override
	public List<String> getRowFieldNames() {
		return table.getRowFieldNames();
	}

	@Override
	public int getColumnCount() {
		return table.getColumnCount();
	}

	@Override
	public String getColumnTitle(int col) {
		return table.getColumnTitle(col);
	}

	@Override
	public int getRowCount() {
		return positions.size();
	}

	@Override
	public List<String> getRowValue(int row) {
		return table.getRowValue(positions.get(row));
	}

	@Override
	public Number getValue(int col, int row) {
		return table.getValue(col, positions.get(row));
	}

	@Override
	public TableFilteredOnRow addColumnKey(String parameterName) {
		table.addColumnKey(parameterName);
		return this;
	}

	@Override
	public ITable getBaseTable() {
		return this.table;
	}


}
