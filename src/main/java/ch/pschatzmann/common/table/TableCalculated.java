package ch.pschatzmann.common.table;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleBindings;

import ch.pschatzmann.common.utils.Tuple;

/**
 * Table which supports calculated columns which are specified with a formula.
 * We use the javascript scripting engine to evaluate the formula. The Column names
 * are used as variable names.
 * 
 * @author pschatzmann
 *
 */
public class TableCalculated implements ITableEx<Number> {
	private static final long serialVersionUID = 1L;
	private ITableEx<Number> table;
	private ScriptEngine engine;
	private List<Tuple<String, String>> calculatedColumns = new ArrayList();
	private List<Bindings> rowBindings = null;
	private List<String> parameterNames = new ArrayList();
	private IFormulas formulas;
	/**
	 * Calculated Table
	 * @param tableP
	 * @param calculatedColumns
	 */
	public TableCalculated(ITableEx tableP, IFormulas formulas, Tuple<String, String>... calculatedColumns) {
		this(tableP,formulas, Arrays.asList(calculatedColumns));
	}

	
	/**
	 * Default Constructor
	 * 
	 * @param tableP
	 */
	public TableCalculated(ITableEx tableP, IFormulas formulas, List<Tuple<String, String>> calculatedColumns) {
		this.table = tableP;
		this.formulas = formulas;
		this.calculatedColumns = calculatedColumns;
		// register calculate parameters
		calculatedColumns.forEach(tuple -> this.addColumnKey(tuple.x));

		ScriptEngineManager factory = new ScriptEngineManager();
		engine = factory.getEngineByName("JavaScript");
	}


	protected void setupRowBindings() {
		if (this.rowBindings==null) {
			this.rowBindings = new ArrayList();
			// create bindings for all rows
			for (int row = 0; row < table.getRowCount(); row++) {
				RowBindings bindings = new RowBindings(row);
				rowBindings.add(bindings);
				bindings.addCalculatedValues(row);
			}
		}
	}
	
	/**
	 * Provides the implementation of the formulas
	 * @return
	 */
	public IFormulas getFormulas() {
		return this.formulas;
	}
	
	@Override
	public List<String> getRowFieldNames() {
		return table.getRowFieldNames();
	}

	@Override
	public int getColumnCount() {
		return this.table.getColumnCount();
	}

	@Override
	public String getColumnTitle(int col) {
		return this.table.getColumnTitle(col);
	}

	@Override
	public int getRowCount() {
		return table.getRowCount();
	}

	@Override
	public List<String> getRowValue(int row) {
		return table.getRowValue(row);
	}

	@Override
	public Number getValue(int col, int row) {
		Number result =null;
		try {
			setupRowBindings();
			
			if (row < this.getRowCount()) {
				String parameterName = getParameterNames().get(col);
				result =  (Number)rowBindings.get(row).get(parameterName);
				result = result == null ? null : result.doubleValue();
			}
		} catch(Exception ex) {}
		return result;
	}

	@Override
	public ITable getBaseTable() {
		return this.table;
	}

	@Override
	public TableCalculated addColumnKey(String parameterName) {
		this.table.addColumnKey(parameterName);
		return this;
	}

	/**
	 * Returns all parameter names in the order of the displayed table
	 * 
	 * @return
	 */
	public List<String> getParameterNames() {
		if (this.parameterNames.isEmpty()) {
			for (int j = 0; j < table.getColumnCount(); j++) {
				String name = table.getColumnTitle(j);
				if (Character.isDigit(name.charAt(0))) {
					name = "$"+name;
				}
				parameterNames.add(name);
			}
		}
		return this.parameterNames;
	}
	
	public Number eval(int row, String parameterName) {
		Number result = null;
		try {
			setupRowBindings();
			if (row >= 0 && row < table.getRowCount()) {
				Optional<Tuple<String,String>> formulaTuple = this.calculatedColumns.stream().filter(t -> t.x.equals(parameterName)).findFirst();
				result = (Number) (formulaTuple.isPresent()? evalFormula(row, formulaTuple.get().y) : rowBindings.get(row).get(parameterName));
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return result;
	}

	protected Number evalFormula(int row, String formula) {
		Number result = null;
		try {
			setupRowBindings();
			Object evalResult = engine.eval(formula, rowBindings.get(row));
			result = toNumber(evalResult);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return result;
	}

	public Number toNumber(Object value) {
		if (value == null)
			return null;
		try {
			Number result = (value instanceof Number) ? (Number) value : Double.valueOf(value.toString());
			return new BigDecimal(result.doubleValue()).setScale(2, RoundingMode.HALF_EVEN);
		} catch(Exception ex) {
			return null;
		}
	}

	/**
	 * Bindings which support parameters with an offset: e.g. NetSales-1 or
	 * NetSales+1 to address a different row
	 * 
	 * @author pschatzmann
	 *
	 */

	class RowBindings extends SimpleBindings {
		private int row = 0;

		public RowBindings(int row) {
			this.row = row;
			this.put("Edgar", getFormulas().create(TableCalculated.this,row));
			// add the parameter values
			List<String> parameterNames = getParameterNames();
			for (int j = 0; j < parameterNames.size(); j++) {
				Number val = table.getValue(j, row);
				if (val != null) {
					this.put(parameterNames.get(j), val);
				}
			}			
		}

		private void addCalculatedValues(int row) {
			// add calculated values in the order of definition
			for (Tuple<String,String> entry : calculatedColumns) {
				Number val = evalFormula(row, entry.y);
				if (val!=null) {
					this.put(entry.x, val);
				}
			}
		}
	}
	
	
	public String getRowValue(int row, String fieldName) {
		String result = "";
		int pos = this.getRowFieldNames().indexOf(fieldName);
		if (pos>=0) {
			result = this.getRowValue(row).get(pos);
		}
		return result;
	}

}
