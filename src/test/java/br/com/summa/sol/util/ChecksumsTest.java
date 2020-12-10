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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class ChecksumsTest {
	@Test
	public void testEmpty() throws Exception {
		assertEquals(Checksums.checksum(), Checksums.checksum());
		assertNotEquals(Checksums.checksum(), Checksums.checksum((Object[])null));
		assertNotEquals(Checksums.checksum(), Checksums.checksum((Object)null));
		assertNotEquals(Checksums.checksum(), Checksums.checksum(""));
		assertNotEquals(Checksums.checksum(), Checksums.checksum("abc"));
	}

	@Test
	public void testPermutations() {
		Object[] objs = new Object[] { "", "1", "2", "12", "abc", null };
		List<Object[]> perms = repeatedPerms(objs, 5);

		Set<Long> set = new HashSet<Long>();
		for (Object[] array : perms) {
			long x = Checksums.checksum(array);
			assertTrue(set.add(x));
			assertEquals(x, Checksums.checksum(array));
		}
	}

	public static List<Object[]> repeatedPerms(Object[] elems, int maxSize) {
		List<Object[]> perms = new ArrayList<Object[]>();
		repeatedPerms(perms, new ArrayList<Object>(), elems, maxSize);
		return perms;
	}

	private static void repeatedPerms(List<Object[]> perms, List<Object> list, Object[] elems, int maxSize) {
		perms.add(list.toArray());
		if (list.size() < maxSize) {
			for (int i = 0; i < elems.length; i++) {
				list.add(elems[i]);
				repeatedPerms(perms, list, elems, maxSize);
				list.remove(list.size()-1);
			}
		}
	}
}
