package ch.pschatzmann.common.table;

/**
 * Exceptions relate to the formatting for tables
 * @author pschatzmann
 *
 */
public class FormatException extends Exception {
	private static final long serialVersionUID = -3778877260445346853L;

	public FormatException(Exception ex){
		super(ex);
	}

	public FormatException(String msg) {
		super(msg);
	}
}
