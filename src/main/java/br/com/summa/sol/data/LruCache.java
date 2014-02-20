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

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Generic local cache with limited size.<br>
 * <br>
 * It automatically discards least recently accessed entries so it never
 * exceeds the maximum specified size.
 *
 * @author Einar Saukas
 */
public class LruCache<K,V> extends LinkedHashMap<K,V> {
    private static final long serialVersionUID = 1L;

    // same as DEFAULT_INITIAL_CAPACITY
    private static final int INITIAL_CAPACITY = 16;

    // same as DEFAULT_LOAD_FACTOR
    private static final float LOAD_FACTOR = 0.75f;

    private final int maxEntries;

    public LruCache(int maxEntries) {
        super(INITIAL_CAPACITY, LOAD_FACTOR, true);
        this.maxEntries = maxEntries;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
        return size() > maxEntries;
    }
}
