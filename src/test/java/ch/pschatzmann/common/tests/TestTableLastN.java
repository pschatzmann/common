package ch.pschatzmann.common.tests;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ch.pschatzmann.common.table.FieldModelLastN;
import ch.pschatzmann.common.table.ITable;
import ch.pschatzmann.common.table.TableFormatterJson;
import ch.pschatzmann.common.table.TableTopN;

public class TestTableLastN {
	private ITable table = new TestData();
	
	
	@Test
	public void testLastN() throws Exception {
		ITable t = new TableTopN(table,5, new FieldModelLastN(), new FieldModelLastN());
		System.out.println(new TableFormatterJson().format(t));
		Assert.assertEquals(5, t.getColumnCount());
		Assert.assertEquals(5, t.getRowCount());
	}
	
	@Test
	public void testLastN10() throws Exception {
		ITable t = new TableTopN(table,10, new FieldModelLastN(), new FieldModelLastN());
		System.out.println(new TableFormatterJson().format(t));
		Assert.assertEquals(10, t.getColumnCount());
		Assert.assertEquals(10, t.getRowCount());
	}
	
	@Test
	public void testLastN11() throws Exception {
		ITable t = new TableTopN(table,11, new FieldModelLastN(), new FieldModelLastN());
		System.out.println(new TableFormatterJson().format(t));
		Assert.assertEquals(10, t.getColumnCount());
		Assert.assertEquals(10, t.getRowCount());
	}
	
	
}
