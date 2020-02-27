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

import java.util.Map;

/**
 * Convenience class for working with {@link java.util.Map}s.
 *
 * @author Einar Saukas
 */
public final class Maps {

    /**
     * Prevents instantiation
     */
    private Maps() {
    }

    /**
     * Removes all entries from the first map, that are currently mapped with the same key and value in the second map.
     * 
     * @param <K> The type of keys stored in maps
     * @param <V> The type of values stored in maps
     * @param minuend The first map, from where elements will be removed
     * @param subtrahend The second map, whose elements will be removed from first map
     */
    public static <K,V> void removeAll(Map<K,V> minuend, Map<K,V> subtrahend) {
        for (Map.Entry<K,V> entry : subtrahend.entrySet()) {
            minuend.remove(entry.getKey(), entry.getValue());
        }
    }
}
