package ch.pschatzmann.common.tests;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ch.pschatzmann.common.table.ITable;
import ch.pschatzmann.common.table.TableFormatterCSV;
import ch.pschatzmann.common.table.TablePositive;


public class TestTablePositive {
	
	@Test
	public void testPositive() throws Exception {
		ITable table = new TablePositive(new TestData(1.0),true);
		double v = (double) table.getValue(2, 3);
		System.out.println(new TableFormatterCSV().format(table));
		Assert.assertEquals(5, v, 0.01);
	}

	@Test
	public void testNegative() throws Exception {
		ITable table = new TablePositive(new TestData(-1.0),false);
		double v = (double) table.getValue(2, 3);
		System.out.println(new TableFormatterCSV().format(table));
		Assert.assertEquals(5, v, 0.01);
	}

}
