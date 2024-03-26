package ch.pschatzmann.common.table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Split all field maps into list of biggest values and others
 * 
 * @author pschatzmann
 *
 */
public class FieldModelTopN implements IFieldModel {
	private List<Integer> rows = new ArrayList();
	private List<Integer> otherRows = new ArrayList();
	
	public void setup(List<Integer> fieldIndexList, int max, Map<Integer, Double> totals) {
		Collections.sort(fieldIndexList, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				// sort descening by totals
				return -1 * totals.get(o1).compareTo(totals.get(o2));
			}
		});
		split(fieldIndexList, max);
	}
	
	public List<Integer> getValues() {
		return rows;
	}
	
	public List<Integer> getOtherValues(){
		return otherRows;
	}
	
	private void split(List<Integer> allRows,  int max) {
		int pos = Math.min(max-1, allRows.size());
		rows.addAll(allRows);
		otherRows.addAll(rows.subList(pos, rows.size()));
		rows.removeAll(otherRows);
	}

}
