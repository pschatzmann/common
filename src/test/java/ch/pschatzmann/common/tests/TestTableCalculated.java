package ch.pschatzmann.common.tests;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ch.pschatzmann.common.table.Formulas;
import ch.pschatzmann.common.table.TableCalculated;
import ch.pschatzmann.common.table.TableFormatterCSV;
import ch.pschatzmann.common.utils.Tuple;

public class TestTableCalculated {

	@Test
	public void test() throws Exception {
		List<Tuple<String,String>> calc = Arrays.asList(new Tuple("calc1", "col1 + 1000.0"));
		TableCalculated table = new TableCalculated(new TestData(), new Formulas(), calc);

		System.out.println(new TableFormatterCSV().format(table));
		System.out.println("---");
		table.toList().stream()
		.forEach(map -> System.out.println(map));
		
		
		table.toList().stream().map(map-> (java.util.Map<String,java.lang.Number>) map)
		.forEach(map -> Assert.assertEquals(map.get("col1").doubleValue() + 1000.0, map.get("calc1").doubleValue(),0.01));
		
	}
}
