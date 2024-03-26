package ch.pschatzmann.common.table;

import ch.pschatzmann.common.utils.Utils;

/**
 * Output as CSV
 * @author pschatzmann
 *
 */
public class TableFormatterCSV<T> implements ITableFormatter<T> {
	private String delim=";";
	boolean withHeaderRecord=true;

	public TableFormatterCSV() {		
	}

	public TableFormatterCSV(String delim, boolean withHeaderRecord) {
		this.delim = delim;
		this.withHeaderRecord = withHeaderRecord;
	}
	/**
	 * Renders the table as CSV
	 * 
	 * 
	 */
	public String format(ITable<T> table ) throws FormatException {
		StringBuffer sb = new StringBuffer();
		if (withHeaderRecord) {
			boolean first = true;
			for (String title : table.getRowFieldNames()) {
				if (!first)
					sb.append(delim);
				first = false;
				sb.append(title);
			}

			for (int col = 0; col < table.getColumnCount(); col++) {
				if (!first)
					sb.append(delim);
				first = false;
				sb.append(table.getColumnTitle(col));
			}

			sb.append(Utils.NL);
		}

		for (int row = 0; row < table.getRowCount(); row++) {
			boolean first = true;
			for (String rv : table.getRowValue(row)) {
				if (!first)
					sb.append(delim);
				first = false;
				sb.append(rv);
			}

			for (int col = 0; col < table.getColumnCount(); col++) {
				if (!first)
					sb.append(delim);
				first = false;
				sb.append(quote(Utils.notNull(table.getValue(col, row),"")));
			}
			sb.append(Utils.NL);
		}
		return sb.toString();
	}
	
	private String quote(String str) {
		String result = str.replaceAll("\"", "'");
		if (result.contains(delim)) {
			result = "\""+result+"\"";
		}
		return result;
	}

}
