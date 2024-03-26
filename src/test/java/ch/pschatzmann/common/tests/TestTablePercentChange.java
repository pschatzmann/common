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
import ch.pschatzmann.common.table.TablePercentChange;
import ch.pschatzmann.common.table.TableTopN;
import ch.pschatzmann.common.table.TablePercentChange.PercentDimension;


public class TestTablePercentChange {
	private ITable table = new TestData();

	@Test
	public void testTable() throws Exception {
		Assert.assertEquals(10, table.getColumnCount());
		Assert.assertEquals(10, table.getRowCount());
		System.out.println(new TableFormatterCSV().format(table));
	}
	
	
	@Test
	public void testPerzentHorizontal() throws Exception {
		ITable t = new TablePercentChange(table, PercentDimension.OnColumns);
		Assert.assertEquals(9, t.getColumnCount());
		Assert.assertEquals(10, t.getRowCount());
		System.out.println(new TableFormatterCSV().format(t));
	}

	@Test
	public void testPercentVertical() throws Exception {
		ITable t = new TablePercentChange(table, PercentDimension.OnRows);
		Assert.assertEquals(10, t.getColumnCount());
		Assert.assertEquals(9, t.getRowCount());
		System.out.println(new TableFormatterCSV().format(t));
	}


}
