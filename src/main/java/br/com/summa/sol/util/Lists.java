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

package br.com.summa.sol.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Convenience class for working with <code>List</code>s.
 *
 * @author Einar Saukas
 */
public final class Lists {

    /**
     * Prevents instantiation
     */
    private Lists() {
    }

    /**
     * Breaks list into smaller sublists with the specified (maximum) size.
     *
     * Typical usage:
     *
     * <code>
     * List<String> all = ...
     *
     * for (List<String> sub : Lists.split(all, 20)) {
     *     ...
     * }
     * </code>
     */
    public static <T> List<T>[] split(final List<T> list, final int subsize) {
        int size = list.size();
        int elems = (size + subsize - 1) / subsize;
        List<T>[] result = new List[elems];
        int fromIndex = 0;
        for (int i = 0; i < elems; i++) {
            int toIndex = Math.min(fromIndex + subsize, size);
            result[i] = list.subList(fromIndex, toIndex);
            fromIndex = toIndex;
        }
        return result;
    }

    /**
     * Breaks data into smaller sublists with the specified (maximum) size.
     *
     * Instead of allocating everything in memory at once, this variant will
     * just allocate one sublist at a time, while data is iterated, thus
     * this variant is especially useful for processing very large amounts
     * of data that may not even fit in memory (while reading from file or
     * database for instance).
     *
     * Typical usage:
     *
     * <code>
     * for (List<X> sub : Lists.split(all.iterator(), 20)) {
     *     ...
     * }
     * </code>
     */
    public static <T> Iterable<List<T>> split(final Iterator<T> it, final int subsize) {
        return new Iterable<List<T>>() {
            public Iterator<List<T>> iterator() {
                return new Iterator<List<T>>() {
                    public List<T> next() {
                        List<T> list = new ArrayList<T>();
                        while (list.size() < subsize && it.hasNext()) {
                            list.add(it.next());
                        }
                        if (list.size() == 0) {
                            throw new NoSuchElementException();
                        }
                        return list;
                    }

                    public boolean hasNext() {
                        return it.hasNext();
                    }

                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }

    /**
     * Swaps content of specified positions in a list.
     */
    public static <T> void swap(List<T> list, final int i, final int j) {
        list.set(i, list.set(j, list.get(i)));
    }
}
