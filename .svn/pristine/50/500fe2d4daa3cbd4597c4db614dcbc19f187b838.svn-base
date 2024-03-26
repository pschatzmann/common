package ch.pschatzmann.common.table;
import ch.pschatzmann.common.utils.Utils;


/**
 * 
 * Creates JSON using the syntax used by Google Charts
 * {
     cols: [{id: \"task\", label: \"Employee Name\", type: \"string\"},
            {id: \"startDate\", label: \"Start Date\", type: \"date\"}],
     rows: [{c:[{v: \"Mike\"}, {v: new Date(2008, 1, 28), f:\"February 28, 2008\"}]},
            {c:[{v: \"Bob\"}, {v: new Date(2007, 5, 1)}]},
            {c:[{v: \"Alice\"}, {v: new Date(2006, 7, 16)}]},
            {c:[{v: \"Frank\"}, {v: new Date(2007, 11, 28)}]},
            {c:[{v: \"Floyd\"}, {v: new Date(2005, 3, 13)}]},
            {c:[{v: \"Fritz\"}, {v: new Date(2011, 6, 1)}]}
           ]
   }
 */

public class TableFormatterJson<T> implements ITableFormatter<T> {
	String quote = "\"";

	/**
	 * Renders the table as a html table
	 * 
	 * @return
	 * @throws FormatException
	 */
	public String format(ITable<T> table) throws FormatException {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"cols\": [");

		sb.append(Utils.NL);
		boolean first = true;
		for (String title : table.getRowFieldNames()) {
			if (!first)
				sb.append(",");
			first = false;
			sb.append("{");
			sb.append("\"id\": ");
			sb.append(this.quote);
			sb.append(title);
			sb.append(this.quote);
			sb.append(", \"label\": \"");
			sb.append(title);
			sb.append("\", \"type\": \"string\" ");
			sb.append("}");
		}
		for (int col = 0; col < table.getColumnCount(); col++) {
			if (!first)
				sb.append(",");
			first = false;
			sb.append("{");
			sb.append("\"id\": ");
			sb.append(this.quote);
			sb.append("ID"+table.getColumnTitle(col));
			sb.append(this.quote);
			sb.append(", \"label\": ");
			sb.append(this.quote);
			sb.append(table.getColumnTitle(col));
			sb.append(this.quote);
			sb.append(", \"type\": \"number\" ");
			sb.append("}");
		}
		sb.append("],");
		sb.append(Utils.NL);
		sb.append("\"rows\": [");
		sb.append(Utils.NL);

		first = true;
		for (int row = 0; row < table.getRowCount(); row++) {
			sb.append("{\"c\":[");
			for (String rv : table.getRowValue(row)) {
				sb.append("{\"v\":");
				sb.append(this.quote);
				sb.append(rv);
				sb.append(this.quote);
				sb.append("}");
				first = false;
			}

			for (int col = 0; col < table.getColumnCount(); col++) {
				if (!first) {
					sb.append(",");
				}
				first = false;				
				sb.append("{\"v\":");
				sb.append(Utils.notNull(table.getValue(col, row),"0"));
				sb.append("}");
			}

			sb.append("]}");
			if (row<table.getRowCount()-1){
				sb.append(",");
			}
			sb.append(Utils.NL);
		}
		sb.append("]");
		sb.append("}");

		return sb.toString();
	}

}
