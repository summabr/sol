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
     * <pre>
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
     * </pre>
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
     * <pre>
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
     * </pre>
     */
    public static <T> boolean equals(T x, T y) {
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
     */
    public static <T> boolean equalsNotNull(T x, T y) {
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
     */
    public static <T> boolean equalsOrNull(T x, T y) {
        return x == null || y == null || x.equals(y);
    }

    /**
     * Converts object to <code>String</code> if not <code>null</code>,
     * returns <code>null</code> otherwise.
     */
    public static <T> String asString(T obj) {
        return obj == null ? null : obj.toString();
    }

    /**
     * Converts object to <code>String</code> if not <code>null</code>,
     * returns an empty <code>String</code> otherwise.
     */
    public static <T> String asStringOrEmpty(T obj) {
        return obj == null ? "" : obj.toString();
    }

    /**
     * Compares two (possibly null) objects, i.e. it's similar to <code>x.compareTo(y)</code> except it
     * supports <code>null</code> values, assuming that <code>null</code> is lower than any object.
     */
    public static <T extends Comparable<T>> int compareToWithNullsFirst(T x, T y) {
        return x == null ? y == null ? 0 : -1 : y == null ? 1 : x.compareTo(y);
    }

    /**
     * Compares two (possibly null) objects, i.e. it's similar to <code>x.compareTo(y)</code> except it
     * supports <code>null</code> values, assuming that <code>null</code> is greater than any object.
     */
    public static <T extends Comparable<T>> int compareToWithNullsLast(T x, T y) {
        return x == null ? y == null ? 0 : 1 : y == null ? -1 : x.compareTo(y);
    }
}
