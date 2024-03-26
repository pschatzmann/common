package ch.pschatzmann.common.table;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import ch.pschatzmann.common.table.IFormulas;
import ch.pschatzmann.common.table.TableCalculated;

/**
 * Implementation of Calculation Formulas which are used in TableCalculated
 * 
 * @author pschatzmann
 *
 */
public class Formulas implements IFormulas {
	private int row;
	private TableCalculated table;
	private IForecast forecast;

	public Formulas() {
	}

	protected Formulas(TableCalculated table, int row) {
		this.row = row;
		this.table = table;
	}

	@Override
	public Formulas create(TableCalculated table, int row) {
		return new Formulas(table, row);
	}

	public Number coalesce(Object... ts) {
		for (Object t : ts) {
			Object v = eval(row, t.toString());
			Number n = this.getTable().toNumber(v);
			if (n != null)
				return n;
		}
		return null;
	}

	public Number min(Object... ts) {
		return values(ts).min().getAsDouble();
	}

	public Number max(Object... ts) {
		return values(ts).max().getAsDouble();
	}

	public Number sum(Object... ts) {
		return values(ts).sum();
	}

	public DoubleStream values(Object... ts) {
		List<Number> result = new ArrayList();
		for (Object t : ts) {
			Object v = eval(row, t.toString());
			if (v != null)
				result.add(this.getTable().toNumber(v));
		}
		return result.stream().mapToDouble(f -> f.doubleValue());
	}

	public int row() {
		return this.row;
	}

	public int count() {
		return this.getTable().getRowCount();
	}

	public Number lag(String parName, int offset) {
		return eval(this.row + offset, parName);
	}

	public Number lagMonths(String parName, int offset) throws ParseException {
		return lagMonths(parName, offset, "date", "yyyy-MM-dd");
	}

	public Number lagMonths(String parName, int months, String dateField, String dateFormat) throws ParseException {
		Number result = null;
		try {
			DateFormat df = new SimpleDateFormat(dateFormat);
			int dateIndex = this.getTable().getRowFieldNames().indexOf(dateField);
			Date targetDate = getTargetDate(months, df, dateIndex);
			int targetRow = getClosestRow(df, dateIndex, targetDate);
			int days = daysBetween(getDateOfRow(targetRow, dateIndex, df), targetDate);
			result = days < 25 ? eval(targetRow, parName) : null;
		} catch (Exception ex) {
		}
		return result;
	}

	protected Integer getClosestRow(DateFormat df, int dateIndex, Date target) {
		int targetRow = IntStream.range(0, this.getTable().getRowCount()).mapToObj(i -> i)
				.min(Comparator.comparingInt(i -> daysBetween(getDateOfRow(i, dateIndex, df), target)))
				.orElseThrow(() -> new NoSuchElementException("No value present"));
		return targetRow;
	}

	protected int abs(Long a) {
		return (a.longValue() < 0) ? -a.intValue() : a.intValue();
	}

	protected int daysBetween(Date d1, Date d2) {
		return abs((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}

	protected Date getTargetDate(int months, DateFormat df, int dateIndex) throws ParseException {
		Date date = getDateOfRow(this.row, dateIndex, df);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, months);
		Date target = cal.getTime();
		return target;
	}

	protected Date getDateOfRow(int row, int dateIndex, DateFormat df) {
		try {
			String dateStr = this.getTable().getRowValue(row).get(dateIndex);
			Date date = df.parse(dateStr);
			return date;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public Number percentChange(String parName) {
		Number result = null;
		try {
			Number lagN = lag(parName, -1);
			if (lagN != null) {
				double lag = lagN.doubleValue();
				result = (value(parName).doubleValue() - lag) / lag * 100;
			}
		} catch (Exception e) {
		}
		return result;
	}

	public Number percentChangeMonths(String parName, int months) {
		return percentChangeMonths(parName, months, "date", "yyyy-MM-dd");
	}

	public Number percentChangeMonths(String parName, int months, String dateField, String dateFormat) {
		Number result = null;
		try {
			Number lagN = lagMonths(parName, months);
			if (lagN != null) {
				double lag = lagN.doubleValue();
				result = (value(parName).doubleValue() - lag) / lag * 100;
			}
		} catch (Exception e) {
		}
		return result;
	}

	public Number difference(String parName) {
		double lag = lag(parName, -1).doubleValue();
		return (value(parName).doubleValue() - lag);
	}

	public Number row(String parName, int row) {
		return eval(row, parName);
	}

	public Number first(String parName) {
		return eval(0, parName);
	}

	public Number last(String parName) {
		return eval(getTable().getRowCount() - 1, parName);
	}

	public TableCalculated getTable() {
		return this.table;
	}

	public Number value(String parName) {
		return eval(row, parName);
	}

	protected Number eval(int row, String parameterName) {
		return this.table.eval(row, parameterName);
	}

	@Override
	public void setForecast(IForecast f) {
		this.forecast = f;
	}

	@Override
	public IForecast getForecast() {
		return forecast;
	}

}