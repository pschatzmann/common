package ch.pschatzmann.common.table;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface IFieldModel extends  Serializable  {
	List<Integer> getValues();
	List<Integer> getOtherValues();
	void setup(List<Integer> cols, int max, Map<Integer, Double> totalCols);
}