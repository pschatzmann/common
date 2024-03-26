package ch.pschatzmann.common.table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ch.pschatzmann.common.utils.Utils;

/**
 * Converts a ITable to a List of Map entries
 * 
 * @author pschatzmann
 *
 */

public class TableBackedListOfMap<T> implements List<Map<String, T>>, Iterator<Map<String, T>>, Serializable  {
	private ITable<T> table;
	private int posIterator;

	public TableBackedListOfMap(ITable<T> table) {
		this.table = table;
	}

	@Override
	public int size() {
		return table.getRowCount();
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public boolean contains(Object o) {
		throw new UnsupportedOperationException("contains");
	}

	@Override
	public Iterator<Map<String, T>> iterator() {
		posIterator = 0;
		return this;
	}

	@Override
	public Object[] toArray() {
		return IntStream.range(0, table.getRowCount())
				.mapToObj(pos -> this.get(pos))
				.collect(Collectors.toList()).toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return (T[]) toArray();
	}

	@Override
	public boolean add(Map<String, T> e) {
		throw new UnsupportedOperationException("add");
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException("remove");
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException("containsAll");
	}

	@Override
	public boolean addAll(Collection<? extends Map<String, T>> c) {
		throw new UnsupportedOperationException("addAll");
	}

	@Override
	public boolean addAll(int index, Collection<? extends Map<String, T>> c) {
		throw new UnsupportedOperationException("addAll");
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException("removeAll");
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException("retainAll");
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException("clear");
	}

	@Override
	public Map<String, T> get(int index) {
		Map record = new TreeMap();
		List titles = table.getRowFieldNames();
		List values = table.getRowValue(index);
		IntStream.range(0, titles.size()).forEach(pos -> record.put(titles.get(pos), values.get(pos)));
		for (int col = 0; col < table.getColumnCount(); col++) {
			Value value =  new Value(table.getValue(col, index));
			if (value == null){
				value = Value.NaN;
			}
			record.put(table.getColumnTitle(col), value);
			 
		}
		return record;
	}

	@Override
	public Map<String, T> set(int index, Map<String, T> element) {
		throw new UnsupportedOperationException("set");
	}

	@Override
	public void add(int index, Map<String, T> element) {
		throw new UnsupportedOperationException("add");
	}

	@Override
	public Map<String, T> remove(int index) {
		throw new UnsupportedOperationException("remove");
	}

	@Override
	public int indexOf(Object o) {
		throw new UnsupportedOperationException("indexOf");
	}

	@Override
	public int lastIndexOf(Object o) {
		throw new UnsupportedOperationException("lastIndexOf");
	}

	@Override
	public ListIterator<Map<String, T>> listIterator() {
		throw new UnsupportedOperationException("listIterator");
	}

	@Override
	public ListIterator<Map<String, T>> listIterator(int index) {
		throw new UnsupportedOperationException("listIterator(index)");
	}

	@Override
	public List<Map<String, T>> subList(int fromIndex, int toIndex) {
		return IntStream.range(fromIndex, toIndex).mapToObj(pos -> this.get(pos)).collect(Collectors.toList());
	}

	@Override
	public boolean hasNext() {
		return this.posIterator < table.getRowCount();
	}

	@Override
	public Map<String, T> next() {
		Map<String, T> result = this.get(posIterator);
		++posIterator;
		return result;
	}


}
