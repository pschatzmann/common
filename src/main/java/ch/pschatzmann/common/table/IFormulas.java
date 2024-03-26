package ch.pschatzmann.common.table;

/**
 * Interface for Formulas
 * @author pschatzmann
 *
 */
public interface IFormulas {
	/**
	 * Factory method which creates a new Formulas Object (Context) for the indicated row
	 * @param table
	 * @param row
	 * @return
	 */
	public IFormulas create (TableCalculated table, int row);
	
	/**
	 * Defines the forecasting method
	 * @param f
	 */
	public void setForecast(IForecast f);
	
	/**
	 * Determines the actually defined Forecast Implementation
	 */
	public IForecast getForecast();

}
