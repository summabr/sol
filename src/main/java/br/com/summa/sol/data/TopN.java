/*
 *  Copyright 2012 by Summa Technologies do Brasil.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package br.com.summa.sol.data;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * A generic collection limited to size N. If more than N elements are added,
 * it will keep the greatest N elements only, discarding the others.
 *
 * Duplicate elements are allowed. For 2 arbitrary elements x and y such that
 * <code>x.compareTo(y) == 0</code>, the oldest element in this collection will be
 * considered greater.
 *
 * @author Einar Saukas
 *
 * @param E - the type of elements in this collection
 */
public class TopN<E extends Comparable<E>> implements Collection<E>, Serializable {
	private static final long serialVersionUID = 1L;

	private int n;
	private LinkedList<E> list = new LinkedList<E>();

	public TopN(int n) {
		this.n = n;
	}

	private void insertSorted(E elem) {
		ListIterator<E> it = list.listIterator();
		while (it.hasNext()) {
			if (it.next().compareTo(elem) < 0) {
				it.previous();
				break;
			}
		}
		it.add(elem);
	}

	@Override
	public boolean add(E elem) {
		if (list.size() >= n) {
			if (list.getLast().compareTo(elem) >= 0) {
				return false;
			}
			list.pollLast();
		}
		insertSorted(elem);
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean changed = false;
		for (E elem : c) {
			changed = add(elem) || changed;
		}
		return changed;
	}

	/**
	 * Returns a list-iterator on the remaining top N elements in this data
	 * structure, sorted in descending order.
	 */
	@Override
	public ListIterator<E> iterator() {
		return Collections.unmodifiableList(list).listIterator();
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public void clear() {
		list.clear();
	}

	@Override
	public boolean contains(Object obj) {
		return list.contains(obj);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return list.containsAll(c);
	}

	@Override
	public Object[] toArray() {
		return list.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !(obj instanceof TopN)) {
			return false;
		}
		final TopN<?> other = (TopN<?>)obj;
		return n == other.n && list.equals(other.list);
	}

	@Override
	public int hashCode() {
		return list.hashCode();
	}
}

