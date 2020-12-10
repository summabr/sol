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

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import br.com.summa.sol.data.RangeMap.Range;

public class RangeMapTest {

	@Test
	public void validatePut() {
		RangeMap<Integer, String> ranges = new RangeMap<Integer, String>();
		ranges.put(new Range<Integer>(1,2), "1-2");
		ranges.put(new Range<Integer>(3,5), "3-5");
		ranges.put(new Range<Integer>(6,7), "6-7");
		ranges.put(new Range<Integer>(10,10), "10");
		ranges.put(new Range<Integer>(12,14), "12-14");

		validateRanges(ranges);
	}

	@Test
	public void validatePutAll() {
		Map<Range<Integer>, String> map = new HashMap<Range<Integer>, String>();
		map.put(new Range<Integer>(1,2), "1-2");
		map.put(new Range<Integer>(3,5), "3-5");
		map.put(new Range<Integer>(6,7), "6-7");
		map.put(new Range<Integer>(10,10), "10");
		map.put(new Range<Integer>(12,14), "12-14");
		RangeMap<Integer, String> ranges = new RangeMap<Integer, String>(map);

		validateRanges(ranges);
	}

	private void validateRanges(RangeMap<Integer, String> ranges) {
		assertEquals(null, ranges.rangedValue(0));
		assertEquals("1-2", ranges.rangedValue(1));
		assertEquals("1-2", ranges.rangedValue(2));
		assertEquals("3-5", ranges.rangedValue(3));
		assertEquals("3-5", ranges.rangedValue(4));
		assertEquals("3-5", ranges.rangedValue(5));
		assertEquals("6-7", ranges.rangedValue(6));
		assertEquals("6-7", ranges.rangedValue(7));
		assertEquals(null, ranges.rangedValue(8));
		assertEquals(null, ranges.rangedValue(9));
		assertEquals("10", ranges.rangedValue(10));
		assertEquals(null, ranges.rangedValue(11));
		assertEquals("12-14", ranges.rangedValue(12));
		assertEquals("12-14", ranges.rangedValue(13));
		assertEquals("12-14", ranges.rangedValue(14));
		assertEquals(null, ranges.rangedValue(15));
		assertEquals(null, ranges.rangedValue(16));
	}
}