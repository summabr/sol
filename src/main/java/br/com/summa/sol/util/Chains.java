/*
 *  Copyright 2018 by Summa Technologies do Brasil.
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

import java.util.Iterator;

/**
 * Convenience class to build a (delimiter separated) string from a provided
 * sequence of elements.<br>
 * <br>
 * It's somewhat similar to {@link java.lang.String}.join(...) and
 * {@link java.util.StringJoiner} from Java 1.8, except it can be used with
 * arbitrary data types, not just {@link java.lang.CharSequence}.
 *
 * @author Einar Saukas
 */
public final class Chains {

    /**
     * Prevents instantiation
     */
    private Chains() {
    }

    /**
     * Returns a string containing the provided sequence of elements (separated by the provided delimiter).<br>
     * <br>
     * It's somewhat similar to {@link java.lang.String#join(CharSequence delimiter, CharSequence... elems)}
     * from Java 1.8, except it accepts all data types, not just {@link java.lang.CharSequence}.<br>
     * <br>
     * Typical usage:
     *
     * <pre>{@code
     * String str1 = Chains.join(" - ", 0, 1, 1, 2, 3, 5, 8, 13);
     * // returns "0 - 1 - 1 - 2 - 3 - 5 - 8 - 13"
     *
     * String str2 = Chains.join(".", "", "a", "", "", "b", null, "c", "");
     * // returns ".a...b.null.c."
     * }</pre>
     *
     * @param <T> Type of elements
     * @param delimiter Provided delimiter
     * @param elems Sequence of elements
     * @return A string containing the provided sequence of elements (separated by the provided delimiter)
     */
    @SafeVarargs
    public static <T> String join(final CharSequence delimiter, final T... elems) {
        StringBuilder sb = null;
        for (Object elem : elems) {
            if (sb == null) {
                sb = new StringBuilder();
            } else {
                sb.append(delimiter);
            }
            sb.append(elem);
        }
        return sb != null ? sb.toString() : "";
    }

    /**
     * Returns a String containing elements from the provided iterable object (separated by the provided delimiter).<br>
     * <br>
     * It's somewhat similar to {@link java.lang.String#join(CharSequence delimiter, Iterable elems)}
     * from Java 1.8, except it accepts all data types, not just {@link java.lang.CharSequence}.<br>
     * <br>
     * Typical usage:
     *
     * <pre>{@code
     * List<Integer> list = Arrays.asList(1,2,3,4,5);
     * String str = Chains.traverse(" - ", list);
     * // returns "1 - 2 - 3 - 4 - 5"
     * }</pre>
     *
     * Notice it also works for other collections:
     *
     * <pre>{@code
     * Map<String, Object> map = new LinkedHashMap<String, Object>();
     * map.put("a", 1);
     * map.put("b", 2);
     * map.put("c", 3);
     * ...
     * String keys = Chains.traverse("-", map.keySet());
     * // returns "a-b-c"
     *
     * String vals = Chains.traverse("-", map.values());
     * // returns "1-2-3"
     *
     * String both = Chains.traverse(" AND ", map.entrySet());
     * // returns "a=1 AND b=2 AND c=3"
     * }</pre>
     *
     * @param delimiter Provided delimiter
     * @param obj Iterable object
     * @return A String containing elements from the provided iterable object (separated by the provided delimiter)
     */
    public static String traverse(final CharSequence delimiter, final Iterable<?> obj) {
        return traverse(delimiter, obj.iterator());
    }

    /**
     * Returns a string containing elements from the provided iterator (separated by the provided delimiter)).<br>
     * <br>
     * It's somewhat similar to {@link java.lang.String#join(CharSequence delimiter, Iterable elems)}
     * from Java 1.8, except it accepts all data types, not just {@link java.lang.CharSequence}.<br>
     * <br>
     * Typical usage:
     *
     * <pre>{@code
     * LinkedList list = new LinkedList(Arrays.asList('a', 'b', 'c'));
     * String str = Chains.traverse(" - ", list.descendingIterator());
     * // returns "c - b - a"
     * }</pre>
     *
     * @param delimiter Provided delimiter
     * @param it Iterator
     * @return A String containing elements from the provided iterator (separated by the provided delimiter)
     */
    public static String traverse(final CharSequence delimiter, final Iterator<?> it) {
        final StringBuilder sb = new StringBuilder();
        if (it.hasNext()) {
            sb.append(it.next());
            while (it.hasNext()) {
                sb.append(delimiter).append(it.next());
            }
        }
        return sb.toString();
    }

    /**
     * Returns a string containing the provided sequence of elements, separated by the provided delimiter),
     * but skipping empty (or null) strings. So it can be used to combine results produced by multiple
     * <code>Chains</code>.<br>
     * <br>
     * Typical usage:
     *
     * <pre>{@code
     * String str1 = Chains.traverse("-", list1);
     * String str2 = Chains.traverse("-", list2);
     * String str = Chains.merge(".", str1, str2);
     * }</pre>
     *
     * @param delimiter Provided delimiter
     * @param elems Sequence of elements
     * @return A string containing the provided sequence of elements, separated by the provided delimiter), but skipping empty (or null) strings
     */
    public static String merge(final CharSequence delimiter, final CharSequence... elems) {
        final StringBuilder sb = new StringBuilder();
        for (CharSequence elem : elems) {
            if (elem != null && elem.length() > 0) {
                if (sb.length() > 0) {
                    sb.append(delimiter);
                }
                sb.append(elem);
            }
        }
        return sb.toString();
    }
}
