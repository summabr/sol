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

import java.util.Collection;

/**
 * Convenience class for working with (possibly <code>null</code>) objects.<br>
 * <br>
 * This implementation does not use reflection, thus avoiding security restrictions
 * and providing roughly the same performance as a "manual" implementation.
 *
 * @author Einar Saukas
 */
public final class Nullables {

    /**
     * Prevents instantiation
     */
    private Nullables() {
    }

    /**
     * Calculates <code>hashCode()</code> for a sequence of (possibly <code>null</code>)
     * objects.<br>
     * <br>
     * Typical usage:<br>
     * <pre>{@code
     * public class Example {
     *     private ... x;
     *     private ... y;
     *     private ... z;
     *
     *     public int hashCode()
     *     {
     *         return Nullables.hashCode(x, y, z);
     *     }
     * }
     * }</pre>
     *
     * @param objs Sequence of objects
     * @return Calculated hashCode
     */
    public static int hashCode(Object... objs) {
        final int prime = 31;

        int result = 1;
        for (Object obj : objs) {
            result = result * prime + (obj != null ? obj.hashCode() : 0);
        }
        return result;
    }

    /**
     * Indicates whether two (possibly <code>null</code>) objects are "equal", i.e.
     * they are both <code>null</code>, or not <code>null</code> with
     * <code>x.equals(y)</code>.<br>
     * <br>
     * Typical usage:<br>
     * <pre>{@code
     * public class Example {
     *     private ... x;
     *     private ... y;
     *     private ... z;
     *
     *     public boolean equals(Object obj) {
     *         if (this == obj) {
     *             return true;
     *         }
     *         if (obj instanceof Example) {
     *             final Example that = (Example)obj;
     *             return Nullables.equals(x, that.x) &&
     *                    Nullables.equals(y, that.y) &&
     *                    Nullables.equals(z, that.z);
     *         }
     *         return false;
     *     }
     * }
     * }</pre>
     *
     * @param x An element to be compared
     * @param y Another element to be compared
     * @return <code>true</code> if both elements are null or identical, <code>false</code> otherwise
     */
    public static boolean equals(Object x, Object y) {
        if (x == y) {
            return true;
        }
        if (x == null || y == null) {
            return false;
        }
        return x.equals(y);
    }

    /**
     * Indicates whether two (possibly <code>null</code>) objects are
     * strictly not <code>null</code> with <code>x.equals(y)</code>.
     *
     * @param x An element to be compared
     * @param y Another element to be compared
     * @return <code>true</code> if both elements are not null and identical, <code>false</code> otherwise
     */
    public static boolean equalsNotNull(Object x, Object y) {
        if (x == null || y == null) {
            return false;
        }
        if (x == y) {
            return true;
        }
        return x.equals(y);
    }

    /**
     * Indicates whether any of two objects is <code>null</code>
     * or <code>x.equals(y)</code>.
     *
     * @param x An element to be compared
     * @param y Another element to be compared
     * @return <code>true</code> if both elements are identical or at least one of them is null, <code>false</code> otherwise
     */
    public static boolean equalsOrNull(Object x, Object y) {
        return x == null || y == null || x.equals(y);
    }

    /**
     * Converts object to <code>String</code> if not <code>null</code>,
     * returns <code>null</code> otherwise.
     *
     * @param obj Object to be converted
     * @return Object converted to <code>String</code> if not <code>null</code>, or <code>null</code> otherwise
     */
    public static String asString(Object obj) {
        return obj != null ? obj.toString() : null;
    }

    /**
     * Converts object to <code>String</code> if not <code>null</code>,
     * returns the provided fallback <code>String</code> otherwise.<br>
     * <br>
     * It works exactly like <code>asString(coalesce(obj, fallback))</code>.
     *
     * @param obj Object to be converted
     * @param fallback Fallback <code>String</code> to be returned if object is <code>null</code>
     * @return Object converted to <code>String</code> if not <code>null</code>, or fallback <code>String</code> otherwise
     */
    public static String asString(Object obj, String fallback) {
        return obj != null ? obj.toString() : fallback;
    }

    /**
     * Compares two (possibly null) objects, i.e. it's similar to <code>x.compareTo(y)</code> except it
     * supports <code>null</code> values, assuming that <code>null</code> is lower than any object.
     *
     * @param <T> The type of elements to be compared
     * @param x An element to be compared
     * @param y Another element to be compared
     * @return Result of comparison between objects
     */
    public static <T extends Comparable<T>> int compareToWithNullsFirst(T x, T y) {
        return x == null ? y == null ? 0 : -1 : y == null ? 1 : x.compareTo(y);
    }

    /**
     * Compares two (possibly null) objects, i.e. it's similar to <code>x.compareTo(y)</code> except it
     * supports <code>null</code> values, assuming that <code>null</code> is greater than any object.
     *
     * @param <T> The type of elements to be compared
     * @param x An element to be compared
     * @param y Another element to be compared
     * @return Result of comparison between objects
     */
    public static <T extends Comparable<T>> int compareToWithNullsLast(T x, T y) {
        return x == null ? y == null ? 0 : 1 : y == null ? -1 : x.compareTo(y);
    }

    /**
     * Indicates whether provided Collection is either <code>null</code> or empty.
     *
     * @param coll Provided Collection
     * @return <code>true</code> if Collection is either <code>null</code> or empty, <code>false</code> otherwise
     */
    public static boolean isNullOrEmpty(final Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    /**
     * Indicates whether provided String is either <code>null</code> or empty.
     *
     * @param str Provided String
     * @return <code>true</code> if String is either <code>null</code> or empty, <code>false</code> otherwise
     */
    public static boolean isNullOrEmpty(final String str) {
        return str == null || str.isEmpty();
    }

    /**
     * Returns first non-null element from a sequence
     *
     * @param <T> The type of elements
     * @param elems Sequence of elements
     * @return First non-null element from the provided sequence
     */
    @SafeVarargs
    public static <T> T coalesce(T... elems) {
        if (elems != null) {
            for (T elem : elems) {
                if (elem != null) {
                    return elem;
                }
            }
        }
        return null;
    }

    /**
     * Returns first non-null element from a sequence
     *
     * @param <T> The type of elements
     * @param elem1 First element
     * @param elem2 Second element
     * @return First non-null element from the provided sequence
     */
    public static <T> T coalesce(T elem1, T elem2) {
        return elem1 != null ? elem1 : elem2;
    }

    /**
     * Returns first non-null element from a sequence
     *
     * @param <T> The type of elements
     * @param elem1 First element
     * @param elem2 Second element
     * @param elem3 Third element
     * @return First non-null element from the provided sequence
     */
    public static <T> T coalesce(T elem1, T elem2, T elem3) {
        return elem1 != null ? elem1 : elem2 != null ? elem2 : elem3;
    }
}
