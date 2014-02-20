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
 * A generic collection limited to size <code>N</code>. If more than <code>N</code>
 * elements are added, it will keep the greatest <code>N</code> elements only,
 * discarding the others.<br>
 * <br>
 * Duplicate elements are allowed. For 2 arbitrary elements <code>x</code> and
 * <code>y</code> such that <code>x.compareTo(y) == 0</code>, the oldest element
 * in this collection will be considered greater.<br>
 * <br>
 * This collection is optimized for small values of <code>N</code> (typically
 * obtaining the "top5" or "top10" elements from a large set). Internally,
 * it keeps at most <code>N</code> elements stored in a <code>LinkedList</code>.
 * For much larger values of <code>N</code>, a more complex implementation
 * would be necessary (possibly using <code>TreeMap<E, LinkedList<E>></code>
 * or Guava's <code>TreeMultiset</code> or Apache Commons'
 * <code>TreeBag</code> instead of <code>LinkedList<E></code>).
 *
 * @author Einar Saukas
 *
 * @param E the type of elements in this collection
 */
public class TopN<E extends Comparable<E>> implements Collection<E>, Serializable {
	private static final long serialVersionUID = 1L;

	private int n;
	private LinkedList<E> list = new LinkedList<E>();

	/**
	 * Constructs an empty collection limited to (greatest) <code>N</code> elements.
	 *
	 * @param n maximum number of elements for this collection
	 */
	public TopN(int n) {
		this.n = n;
	}

	private void insertSorted(E elem) {
		ListIterator<E> it = list.listIterator(list.size());
		while (it.hasPrevious()) {
			if (it.previous().compareTo(elem) >= 0) {
				it.next();
				break;
			}
		}
		it.add(elem);
	}

	/**
	 * Attempts to add the specified element to the collection. If the collection
	 * already contains <code>N</code> elements greater than or equal to the specified
	 * element, it won't be added. Otherwise it will add the specified element and, if
	 * the collection already contained <code>N</code> elements, the lowest of them will
	 * be automatically removed.
	 *
	 * @param elem element to be added
	 *
	 * @return <code>true</code> if this collection changed as a result of the call
	 * (thus if the element was successfully added), <code>false</code> otherwise
	 */
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

	/**
	 * Adds all elements from the specified collection to this collection.
	 *
	 * @param col collection containing elements to be added
	 *
	 * @return <code>true</code> if this collection changed as a result of the call
	 * (thus if at least one element was successfully added), <code>false</code> otherwise
	 */
	@Override
	public boolean addAll(Collection<? extends E> col) {
		boolean changed = false;
		for (E elem : col) {
			changed = add(elem) || changed;
		}
		return changed;
	}

	/**
	 * Returns a read-only list-iterator over all top <code>N</code> elements in
	 * this collection, sorted in descending order.
	 *
	 * @return a read-only list-iterator over all top <code>N</code> elements in
	 * this collection, sorted in descending order
	 */
	@Override
	public ListIterator<E> iterator() {
		return Collections.unmodifiableList(list).listIterator();
	}

	/**
	 * Returns the number of elements in this collection.
	 *
	 * @return the number of elements in this collection
	 */
	@Override
	public int size() {
		return list.size();
	}

	/**
	 * Returns <code>true</code> if this collection contains no elements.
	 *
	 * @return <code>true</code> if this collection contains no elements
	 */
	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	/**
	 * Removes all elements from this collection. The collection will be empty
	 * afterwards.
	 */
	@Override
	public void clear() {
		list.clear();
	}

	/**
	 * Returns <code>true</code> if this collection contains the specified element.
	 * More formally, returns <code>true</code> if and only if this list contains
	 * at least one element <code>e</code> such that
	 * <code>(obj==null ? e==null : obj.equals(e))</code>.<br>
	 * <br>
	 * This operation takes at most <code>N</code> comparisons, where <code>N</code> is
	 * the specified size limit.
	 *
	 * @param obj element whose presence in this collection is to be tested
	 *
	 * @return <code>true</code> if this collection contains the specified element
	 *
	 */
	@Override
	public boolean contains(Object obj) {
		return list.contains(obj);
	}

	/**
	 * Returns <code>true</code> if this collection contains all elements from the
	 * specified collection.
	 *
	 * @param col collection to be checked for containment in this collection
	 *
	 * @return <code>true</code> if this collection contains all elements from the
	 * specified collection, <code>false</code> otherwise
	 */
	@Override
	public boolean containsAll(Collection<?> col) {
		return list.containsAll(col);
	}

	/**
	 * Returns an array containing all top <code>N</code> elements in
	 * this collection, sorted in descending order. See 
         * {@link java.util.LinkedList#toArray()}.
	 *
	 * @return an array containing all top <code>N</code> elements in this
	 * collection, sorted in descending order
	 */
	@Override
	public Object[] toArray() {
		return list.toArray();
	}

	/**
	 * Returns an array containing all top <code>N</code> elements in
	 * this collection, sorted in descending order; the runtime type of the
	 * returned array is that of the specified array.
	 * See {@link java.util.LinkedList#toArray(Object[])}.
	 *
	 * @param a the array into which all top <code>N</code> elements in
	 * this collection are to be stored, if it is big enough; otherwise, a
	 * new array of the same runtime type will be allocated for this purpose
	 *
	 * @return an array containing all top <code>N</code> elements in this
	 * collection, sorted in descending order
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}

	/**
	 * Unsupported operation
	 *
	 * @throws UnsupportedOperationException
	 */
	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Unsupported operation
	 *
	 * @throws UnsupportedOperationException
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Unsupported operation
	 *
	 * @throws UnsupportedOperationException
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof TopN) {
			final TopN<?> that = (TopN<?>)obj;
			return n == that.n && list.equals(that.list);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return list.hashCode();
	}
}

