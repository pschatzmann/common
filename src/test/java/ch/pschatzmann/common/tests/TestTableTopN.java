package ch.pschatzmann.common.tests;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ch.pschatzmann.common.table.ITable;
import ch.pschatzmann.common.table.TableFormatterCSV;
import ch.pschatzmann.common.table.TableFormatterJson;
import ch.pschatzmann.common.table.TableTopN;

public class TestTableTopN {
	private ITable table = new TestData();

	@Test
	public void testTopN() throws Exception {
		ITable t = new TableTopN(table, 5);
		Assert.assertEquals(5, t.getColumnCount());
		Assert.assertEquals(5, t.getRowCount());
		System.out.println(new TableFormatterCSV().format(t));
		System.out.println(new TableFormatterJson().format(t));
	}

	@Test
	public void testTopN9() throws Exception {
		ITable t = new TableTopN(table, 9);
		Assert.assertEquals(9, t.getColumnCount());
		Assert.assertEquals(9, t.getRowCount());
		System.out.println(new TableFormatterCSV().format(t));
	}

	@Test
	public void testTopN10() throws Exception {
		ITable t = new TableTopN(table, 10);
		Assert.assertEquals(10, t.getColumnCount());
		Assert.assertEquals(10, t.getRowCount());
		System.out.println(new TableFormatterCSV().format(t));
	}

	@Test
	public void testTopN11() throws Exception {
		ITable t = new TableTopN(table, 11);
		Assert.assertEquals(10, t.getColumnCount());
		Assert.assertEquals(10, t.getRowCount());
		System.out.println(new TableFormatterCSV().format(t));
	}

	@Test
	public void testTopN20() throws Exception {
		ITable t = new TableTopN(table, 20);
		Assert.assertEquals(10, t.getColumnCount());
		Assert.assertEquals(10, t.getRowCount());
		System.out.println(new TableFormatterCSV().format(t));
	}

}
