package ch.pschatzmann.common.tests;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

import org.junit.Assert;
import org.junit.Test;

import ch.pschatzmann.common.table.ITable;
import ch.pschatzmann.common.table.TableCalculated;
import ch.pschatzmann.common.table.TableFilteredOnCol;
import ch.pschatzmann.common.table.TableFilteredOnRow;
import ch.pschatzmann.common.table.TableFormatterCSV;
import ch.pschatzmann.common.utils.Tuple;

public class TestTableFilteredOnRow implements BiFunction<ITable, Integer, Boolean> {

	@Test
	public void test() throws Exception {
		List<Tuple<String,String>> calc = Arrays.asList(new Tuple("calc1","row1 / 2.0"));
		ITable table = new TableFilteredOnRow(new TestData(),this);
		
		System.out.println(new TableFormatterCSV().format(table));

		Assert.assertEquals(5, table.getRowCount());
				
	}

	@Override
	public Boolean apply(ITable t, Integer u) {
		return u<5;
	}
}
