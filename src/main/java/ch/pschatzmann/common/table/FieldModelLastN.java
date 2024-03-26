package ch.pschatzmann.common.table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Just selects the last n fields
 * @author pschatzmann
 *
 */
public class FieldModelLastN implements IFieldModel {
	private List<Integer> rows = new ArrayList();
	private List<Integer> otherRows = new ArrayList();
	
	@Override
	public List<Integer> getValues() {
		return rows;
	}
	
	@Override
	public List<Integer> getOtherValues(){
		return otherRows;
	}
	
	private void split(List<Integer> allRows,  int max) {
		rows.addAll(allRows);
		while(rows.size()>max) {
			rows.remove(0);
		}
	}

	@Override
	public void setup(List<Integer> rows, int max, Map<Integer, Double> totals) {
		split(rows, max);
	}

}
