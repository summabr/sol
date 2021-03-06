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

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * A generic collection limited to size <code>N</code>. If more than <code>N</code>
 * elements are added, it will attempt to keep the greatest <code>N</code> elements
 * only, discarding the others. However if there are more elements "tied" at the
 * <code>N</code>th position, they will be also kept in the collection, up to a strict
 * limit of <code>max</code> elements.<br>
 * <br>
 * Duplicate elements are allowed. For 2 arbitrary elements <code>x</code> and
 * <code>y</code> such that <code>x.compareTo(y) == 0</code>, the oldest element
 * in this collection will be considered greater for the purpose of sorting, and
 * perhaps also for the purpose of discarding (only when necessary to prevent more
 * than <code>max</code> elements in this collection).<br>
 * <br>
 * This collection is optimized for large values of <code>N</code>, keeping its
 * elements stored in a <code>TreeMap</code> of <code>LinkedList</code>s. For
 * small values of <code>N</code>, use {@link ListTopN} instead, unless the
 * distinction between <code>N</code> and <code>max</code> provided by
 * {@link TreeTopN} is really needed.
 *
 * @param <E> The type of elements stored in this collection
 *
 * @author Einar Saukas
 */
public class TreeTopN<E extends Comparable<E>> implements TopN<E> {
    private static final long serialVersionUID = 1L;

    private int n;
    private int max;
    private TreeMap<E, LinkedList<E>> data = new TreeMap<E, LinkedList<E>>();
    private int size = 0;

    /**
     * Constructs an empty collection limited strictly to (greatest) <code>N</code>
     * elements. This works exactly like {@link TreeTopN#TreeTopN(int, int)} when
     * <code>max = N</code>.
     *
     * @param n Strict limit <code>N</code> on the number of elements stored in this collection
     */
    public TreeTopN(int n) {
        this(n, n);
    }

    /**
     * Constructs an empty collection limited to (greatest) <code>N</code> elements. If
     * there are multiple elements "tied" at the <code>N</code>th position, they will be
     * also kept in the collection, up to a strict limit of <code>max</code> elements.
     * Technically, only elements strictly lower than the first <code>N</code> elements
     * (when sorted in descending order) will be discarded, up to a maximum limit of
     * <code>max</code> elements. Therefore this collection may get considerably larger
     * than size <code>N</code>, depending on the specified limit <code>max</code> and
     * the amount of "repetition" (regarding element comparison). Moreover, oldest
     * elements in this collection will be considered greater than other "repeated"
     * elements for the purpose of ordering, and perhaps also for the purpose of
     * discarding (only when necessary to prevent more than <code>max</code> elements
     * in this collection).<br>
     * <br>
     * When <code>max = N</code> this collection will enforce a strict limit of
     * <code>N</code> (greatest) elements, discarding the others. Therefore it will
     * work exactly like {@link ListTopN} except it will be more efficient for larger
     * values of <code>N</code>.
     *
     * @param n Intended limit <code>N</code> on the number of elements stored in this collection
     * @param max Strict maximum limit on the number of elements stored in this collection
     */
    public TreeTopN(int n, int max) {
        this.n = n;
        this.max = max;
    }

    private void insertSorted(E elem) {
        List<E> list = data.get(elem);
        if (list != null) {
            list.add(elem);
        } else {
            data.put(elem, new LinkedList<E>());
        }
        size++;
    }

    /**
     * Attempts to add the specified element to the collection. If the collection
     * already contains (at least) <code>N</code> elements, and the specified new
     * element is lower than all of them, it won't be added. Moreover, if the
     * collection already contains <code>max</code> elements, and the specified
     * new element is not greater than any of them, it won't be added either.
     * Otherwise the specified element will be added, and other (lower) element(s)
     * may be automatically removed in this case.
     *
     * @param elem Element to be added
     *
     * @return <code>true</code> if this collection changed as a result of the call
     * (thus if the element was successfully added), <code>false</code> otherwise
     */
    @Override
    public boolean add(E elem) {
        if (size >= n) {
            Entry<E, LinkedList<E>> lowest = data.firstEntry();
            final int cmp = lowest.getKey().compareTo(elem);
            if (cmp < 0) {
                if (size - lowest.getValue().size() == n) {
                    size -= lowest.getValue().size()+1;
                    data.pollFirstEntry();
                } else if (size >= max) {
                    if (lowest.getValue().size() > 0) {
                        lowest.getValue().pollLast();
                    } else {
                        data.pollFirstEntry();
                    }
                    size--;
                }
            } else if (cmp > 0 || size >= max) {
                return false;
            }
        }
        insertSorted(elem);
        return true;
    }

    /**
     * Adds all elements from the specified collection to this collection.
     *
     * @param coll Collection containing elements to be added
     *
     * @return <code>true</code> if this collection changed as a result of the call
     * (thus if at least one element was successfully added), <code>false</code> otherwise
     */
    @Override
    public boolean addAll(Collection<? extends E> coll) {
        boolean changed = false;
        for (E elem : coll) {
            changed = add(elem) || changed;
        }
        return changed;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Iterator<Entry<E, LinkedList<E>>> iter = data.descendingMap().entrySet().iterator();
            private Iterator<E> subIter = null;

            @Override
            public E next() {
                if (subIter != null && subIter.hasNext()) {
                    return subIter.next();
                }
                Entry<E, LinkedList<E>> entry = iter.next();
                subIter = entry.getValue().iterator();
                return entry.getKey();
            }

            @Override
            public boolean hasNext() {
                return (subIter != null && subIter.hasNext()) || iter.hasNext();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    /**
     * Returns the number of elements in this collection.
     *
     * @return The number of elements in this collection
     */
     @Override
     public int size() {
        return size;
    }

    /**
     * Returns <code>true</code> if this collection contains no elements.
     *
     * @return <code>true</code> if this collection contains no elements
     */
     @Override
     public boolean isEmpty() {
         return size == 0;
     }

     /**
      * Removes all elements from this collection. The collection will be empty
      * afterwards.
      */
     @Override
     public void clear() {
         data.clear();
         size = 0;
     }

     /**
      * Returns <code>true</code> if this collection contains the specified element.
      * More formally, returns <code>true</code> if and only if this list contains
      * at least one element <code>e</code> such that
      * <code>(obj==null ? e==null : obj.equals(e))</code>.
      *
      * @param obj Element whose presence in this collection is to be tested
      *
      * @return <code>true</code> if this collection contains the specified element
      *
      */
     @Override
     public boolean contains(Object obj) {
         Entry<E, LinkedList<E>> entry = data.floorEntry((E)obj);
         return entry != null &&
                 (entry.getKey().equals(obj) ||
                         (entry.getKey().compareTo((E)obj) == 0 && entry.getValue().contains(obj)));
     }

     /**
      * Returns <code>true</code> if this collection contains all elements from the
      * specified collection.
      *
      * @param coll Collection to be checked for containment in this collection
      *
      * @return <code>true</code> if this collection contains all elements from the
      * specified collection, <code>false</code> otherwise
      */
     @Override
     public boolean containsAll(Collection<?> coll) {
         for (Object obj : coll) {
             if (!contains(obj)) {
                 return false;
             }
         }
         return true;
     }

     /**
      * Returns an array containing all top <code>N</code> elements in
      * this collection, sorted in descending order. See
      * {@link java.util.LinkedList#toArray()}.
      *
      * @return An array containing all top <code>N</code> elements in this
      * collection, sorted in descending order
      */
     @Override
     public Object[] toArray() {
         Object[] array = new Object[size];
         int i = 0;
         for (Entry<E, LinkedList<E>> entry : data.descendingMap().entrySet()) {
             array[i++] = entry.getKey();
             for (E elem : entry.getValue()) {
                 array[i++] = elem;
             }
         }
         return array;
     }

     /**
      * Returns an array containing all top <code>N</code> elements in
      * this collection, sorted in descending order; the runtime type of the
      * returned array is that of the specified array.
      * See {@link java.util.LinkedList#toArray(Object[])}.
      *
      * @param array The array into which all top <code>N</code> elements in
      * this collection are to be stored, if it is big enough; otherwise, a
      * new array of the same runtime type will be allocated for this purpose
      *
      * @return An array containing all top <code>N</code> elements in this
      * collection, sorted in descending order
      */
     @Override
     public <T> T[] toArray(T[] array) {
         if (array.length < size) {
             array = (T[])Array.newInstance(array.getClass().getComponentType(), size);
         }
         int i = 0;
         for (Entry<E, LinkedList<E>> entry : data.descendingMap().entrySet()) {
             array[i++] = (T)entry.getKey();
             for (E elem : entry.getValue()) {
                 array[i++] = (T)elem;
             }
         }
         if (array.length > size) {
             array[size] = null;
         }
         return array;
     }

     /**
      * Unsupported operation
      *
      * @throws UnsupportedOperationException Unsupported operation
      */
     @Override
     public boolean remove(Object obj) {
         throw new UnsupportedOperationException();
     }

     /**
      * Unsupported operation
      *
      * @throws UnsupportedOperationException Unsupported operation
      */
     @Override
     public boolean removeAll(Collection<?> coll) {
         throw new UnsupportedOperationException();
     }

     /**
      * Unsupported operation
      *
      * @throws UnsupportedOperationException Unsupported operation
      */
     @Override
     public boolean retainAll(Collection<?> coll) {
         throw new UnsupportedOperationException();
     }

     @Override
     public boolean equals(Object obj) {
         if (this == obj) {
             return true;
         }
         if (obj instanceof TreeTopN) {
             final TreeTopN<?> that = (TreeTopN<?>)obj;
             return n == that.n && max == that.max &&
                     size == that.size && data.equals(that.data);
         }
         return false;
     }

     @Override
     public int hashCode() {
         return data.hashCode();
     }
}
