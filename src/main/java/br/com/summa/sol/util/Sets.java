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
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Convenience class for working with <code>Set</code>s.
 *
 * @author Einar Saukas
 */
public final class Sets {

    /**
     * Prevents instantiation
     */
    private Sets() {
    }

    /**
     * Creates new <code>HashSet < T ></code> from a sequence of elements T or array T[]
     */
    public static <T> HashSet<T> asHashSet(T... values) {
        HashSet<T> set = new HashSet<T>(values.length);
        Collections.addAll(set, values);
        return set;
    }

    /**
     * Creates new <code>HashSet</code> that contains all elements (without repetition) 
     * from both provided <code>HashSet</code>s.
     */
    public static <T> HashSet<T> union(Collection<T> x, Collection<T> y) {
        HashSet<T> result = new HashSet<T>(x.size() + y.size());
        result.addAll(x);
        result.addAll(y);
        return result;
    }

    /**
     * Creates new <code>HashSet</code> that only contains elements from both
     * provided <code>HashSet</code>s.
     */
    public static <T> HashSet<T> intersection(Set<T> x, Set<T> y) {
        HashSet<T> result = new HashSet<T>();
        if (x.size() <= y.size()) {
            for (T elem : x) {
                if (y.contains(elem)) {
                    result.add(elem);
                }
            }
        } else {
            for (T elem : y) {
                if (x.contains(elem)) {
                    result.add(elem);
                }
            }
        }
        return result;
    }
}
