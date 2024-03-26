package ch.pschatzmann.common.tests;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ch.pschatzmann.common.table.ITable;
import ch.pschatzmann.common.table.TableCalculated;
import ch.pschatzmann.common.table.TableFilteredOnCol;
import ch.pschatzmann.common.table.TableFormatterCSV;
import ch.pschatzmann.common.utils.Tuple;

public class TestTableFilteredOnCol {

	@Test
	public void test() throws Exception {
		List<Tuple<String,String>> calc = Arrays.asList(new Tuple("calc1","row1 / 2.0"));
		ITable table = new TableFilteredOnCol(new TestData()).removeParameterNames("col1","col2");
		
		System.out.println(table.getColumnTitle(1));
		
		Assert.assertEquals(8, table.getColumnCount());
		
		System.out.println(new TableFormatterCSV().format(table));

		
		
	}
}
