package ch.pschatzmann.common.table;

import ch.pschatzmann.common.utils.Utils;

/**
 * Output as Html table
 * 
 * @author pschatzmann
 *
 */
public class TableFormatterHtml<T> implements ITableFormatter<T> {
	private static final long serialVersionUID = 1L;

	/**
	 * Renders the table as a html table
	 * 
	 * @return
	 * @throws FormatException
	 */
	public String format(ITable<T> table) throws FormatException {
		StringBuffer sb = new StringBuffer();
		sb.append(Utils.NL);
		sb.append("<table>");
		sb.append(Utils.NL);
		sb.append("<tr>");
		sb.append(Utils.NL);
		for (String title : table.getRowFieldNames()) {
			sb.append("<th>");
			sb.append(title);
			sb.append("</th>");
		}
		for (int col = 0; col < table.getColumnCount(); col++) {
			sb.append("<th>");
			sb.append(table.getColumnTitle(col));
			sb.append("</th>");
		}
		sb.append("<tr>");
		sb.append(Utils.NL);

		for (int row = 0; row < table.getRowCount(); row++) {
			sb.append("<tr>");
			if (!table.getRowFieldNames().isEmpty()) {
				for (String rv : table.getRowValue(row)) {
					sb.append("<th>");
					sb.append(rv);
					sb.append("</th>");
				}
			}

			for (int col = 0; col < table.getColumnCount(); col++) {
				sb.append("<td>");
				sb.append(Utils.notNull(table.getValue(col, row), ""));
				sb.append("</td>");
			}

			sb.append("</tr>");
			sb.append(Utils.NL);
		}
		sb.append("</table>");

		return sb.toString();
	}

}
