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

package br.com.summa.sol.data;

import java.util.Map;
import java.util.TreeMap;

import br.com.summa.sol.data.RangeMap.Range;
import br.com.summa.sol.util.Nullables;

/**
 * A generic collection that maps key ranges to values.<br>
 * <br>
 * Typical usage:
 *
 * <pre>{@code
 * RangeMap<Integer, String> map = new RangeMap<Integer, String>();
 * map.put(new Range<Integer>(1,3), "a");
 * map.put(new Range<Integer>(4,7), "b");
 * map.put(new Range<Integer>(8,10), "c");
 * ...
 * String s = map.rangedValue(6);
 * // returns "b"
 * }</pre>
 *
 * @author Einar Saukas
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
public class RangeMap<K extends Comparable<K>, V> extends TreeMap<Range<K>, V> {
	private static final long serialVersionUID = 1L;

	public RangeMap() {
		super();
	}

	public RangeMap(Map<Range<K>, ? extends V> map) {
		super(map);
	}

	public Map.Entry<Range<K>, V> rangedEntry(K key) {
		Map.Entry<Range<K>, V> entry = floorEntry(new Range<K>(key, null));
		if (entry != null && entry.getKey().getX().compareTo(key) <= 0 && entry.getKey().getY().compareTo(key) >= 0) {
			return entry;
		}
		return null;
	}

	public Range<K> rangedKey(K key) {
		Map.Entry<Range<K>, V> entry = rangedEntry(key);
		return entry != null ? entry.getKey() : null;
	}

	public V rangedValue(K key) {
		Map.Entry<Range<K>, V> entry = rangedEntry(key);
		return entry != null ? entry.getValue() : null;
	}

	public static class Range<K extends Comparable<K>> extends Pair<K, K> implements Comparable<Range<K>> {
		private final static long serialVersionUID = 1L;

		public Range(K x, K y) {
			super(x, y);
		}

		@Override
		public int compareTo(Range<K> other) {
			if (this == other) {
				return 0;
			}
			if (null == other) {
				return -1;
			}
			int result = Nullables.compareToWithNullsLast(getX(), other.getX());
			if (result == 0) {
				result = Nullables.compareToWithNullsLast(getY(), other.getY());
			}
			return result;
		}
	}
}
