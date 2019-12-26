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

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class MapsTest {
	@Test
	public void testRemoveAll() {
		Map<String, Integer> map1 = new HashMap<String, Integer>();
		map1.put("a", 1);
		map1.put("b", 2);
		map1.put("c", 3);
		map1.put("d", 4);

		Map<String, Integer> map2 = new HashMap<String, Integer>();
		map2.put("b", 1);
		map2.put("c", 3);
		map2.put("e", 5);
		map2.put("f", 6);

		Maps.removeAll(map1, map2);

		assertEquals(3, map1.size());
		assertEquals((Integer)1, map1.get("a"));
		assertEquals((Integer)2, map1.get("b"));
		assertEquals(null, map1.get("c"));
		assertEquals((Integer)4, map1.get("d"));
		assertEquals(null, map1.get("e"));
		assertEquals(null, map1.get("f"));

		assertEquals(4, map2.size());
		assertEquals(null, map2.get("a"));
		assertEquals((Integer)1, map2.get("b"));
		assertEquals((Integer)3, map2.get("c"));
		assertEquals(null, map2.get("d"));
		assertEquals((Integer)5, map2.get("e"));
		assertEquals((Integer)6, map2.get("f"));
	}
}
