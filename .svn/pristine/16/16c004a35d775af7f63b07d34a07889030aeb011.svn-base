package ch.pschatzmann.common.table;

import com.fasterxml.jackson.annotation.JsonValue;

import ch.pschatzmann.common.utils.Utils;

/**
 * Value which can represent a number or a String
 * @author pschatzmann
 *
 */
public class Value extends Number {
	private static final long serialVersionUID = 1L;
	private Number number = 0.0;
	private String str = "";
    public static final Value NaN = new Value(Double.NaN);

	public Value(){		
	}

    
	public Value(Number number){
		setValue(number);
	}

	public Value(Object obj){
		if (obj!=null) {
			if (obj instanceof Number) {
				this.setValue((Number)obj);
			} else {
				this.setValue(obj.toString());				
			}
		}
	}

	public Value(String str){
		setValue(str);
	}

	public void setValue(Number number) {
		if (number!=null) {
			this.number = number;
			this.str = number.toString();
		}
	}
	
	public void setValue(String str) {
		if (str!=null) {
			try {
				if (Utils.isNumber(str, false)) {
					this.number = Double.parseDouble(str);
				} else {
					this.number = NaN;
				}
				
			} catch(Exception ex) {
				this.number = NaN;				
			}
			this.str = str;
		}
	}
	
	@JsonValue
	public String getValue() {
		return this.str;
	}
	
	@Override
	public int intValue() {
		return number.intValue();
	}

	@Override
	public long longValue() {
		return number.longValue();
	}

	@Override
	public float floatValue() {
		return number.floatValue();
	}

	@Override
	public double doubleValue() {
		return number.doubleValue();
	}
	
	@Override
	public String toString() {
		return this.str;
	}

}
