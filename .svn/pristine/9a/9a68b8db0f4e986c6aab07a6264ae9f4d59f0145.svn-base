package ch.pschatzmann.common.tests;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ch.pschatzmann.common.table.FieldModelLastN;
import ch.pschatzmann.common.table.ITable;
import ch.pschatzmann.common.table.TableFormatterCSV;
import ch.pschatzmann.common.table.TableFormatterHtml;
import ch.pschatzmann.common.table.TableFormatterJson;
import ch.pschatzmann.common.table.TablePercent;
import ch.pschatzmann.common.table.TablePercentChange;
import ch.pschatzmann.common.table.TableTopN;
import ch.pschatzmann.common.table.TablePercentChange.PercentDimension;


public class TestTablePercent {
	private ITable table = new TestData();

	@Test
	public void testTable() throws Exception {
		Assert.assertEquals(10, table.getColumnCount());
		Assert.assertEquals(10, table.getRowCount());
		System.out.println(new TableFormatterCSV().format(table));
	}
	
	
	@Test
	public void testPercentColumns() throws Exception {
		ITable t = new TablePercent(table, PercentDimension.OnColumns);
		Assert.assertEquals(10, t.getColumnCount());
		Assert.assertEquals(10, t.getRowCount());
		
		double total = 0.0;
		for (int j=0;j<t.getRowCount();j++) {
			total += ((Double)t.getValue(0, j)).doubleValue();
		}
		System.out.println(new TableFormatterCSV().format(t));
		Assert.assertEquals(100.0, total,0.09);
	}

	@Test
	public void testPercentRows() throws Exception {
		ITable t = new TablePercent(table, PercentDimension.OnRows);
		Assert.assertEquals(10, t.getColumnCount());
		Assert.assertEquals(10, t.getRowCount());
		double total = 0.0;
		for (int j=0;j<t.getColumnCount();j++) {
			total += ((Double)t.getValue(j,4)).doubleValue();
		}
		
		System.out.println(new TableFormatterCSV().format(t));
		Assert.assertEquals(100.0, total,0.09);
	}


}
