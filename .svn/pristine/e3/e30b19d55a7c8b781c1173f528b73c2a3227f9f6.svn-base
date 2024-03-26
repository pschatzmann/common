package ch.pschatzmann.common.tests;

import org.junit.Test;

import ch.pschatzmann.common.table.ITable;
import ch.pschatzmann.common.table.TableFormatterCSV;
import ch.pschatzmann.common.table.TableFormatterHtml;
import ch.pschatzmann.common.table.TableFormatterJson;

public class TestTable {
	private ITable table = new TestData();
	
	@Test
	public void testCSV() throws Exception {
		System.out.println(new TableFormatterCSV().format(table));
	}

	@Test
	public void testHtml() throws Exception {
		System.out.println(new TableFormatterHtml().format(table));
	}
	@Test
	public void testJson() throws Exception {
		System.out.println(new TableFormatterJson().format(table));
	}
	
	
	

	
}
