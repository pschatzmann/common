package ch.pschatzmann.common.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.pschatzmann.common.table.ITable;
import ch.pschatzmann.common.table.ITableEx;

/**
 * 
 * @author pschatzmann
 *
 */
public class TestData implements ITableEx {
	double factor=1.0;
	List<String> colTitles = new ArrayList(Arrays.asList("col0","col1","col2","col3","col4","col5","col6","col7","col8","col9" ));
	
	public TestData() {}
	
	public TestData(double factor){
		this.factor = factor;
	}

	@Override
	public List<String> getRowFieldNames() {
		return Arrays.asList("rowKey1","rowKey2");
	}

	@Override
	public int getColumnCount() {
		return colTitles.size();
	}

	@Override
	public String getColumnTitle(int col) {
		return colTitles.get(col);
	}

	@Override
	public int getRowCount() {
		return 10;
	}

	@Override
	public List<String> getRowValue(int row) {
		return Arrays.asList(getRowFieldNames().get(0)+"-"+row, getRowFieldNames().get(1)+"-"+row);
	}

	@Override
	public Number getValue(int col, int row) {
		return (double)( col + row) * factor;
	}

	@Override
	public ITable getBaseTable() {
		return this;
	}

	@Override
	public TestData addColumnKey(String parameterName) {
		colTitles.add(parameterName);
		return this;
	}


}
