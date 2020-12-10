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

import java.nio.charset.Charset;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

/**
 * Utility class to calculate a checksum for a sequence of instances.
 *
 * @author Einar Saukas
 */
public final class Checksums {
	public static Charset UTF_8 = Charset.forName("UTF-8");

	/*
	 * Prevents instantiation
	 */
	private Checksums() {
	}

	/**
	 * Calculates checksum of a sequence of (possibly <code>null</code>)
	 * objects.<br>
	 * <br>
	 * It's somewhat similar to {@link br.com.summa.sol.util.Nullables.hashCode(Object... objs)}
	 * except using <code>obj.toString()</code> instead of <code>obj.hashCode()</code>. It should
	 * provide better results regardless of the quality of individual <code>hashCode()</code>
	 * implementations, although it's more expensive to calculate.<br>
	 * <br>
	 * Typical usage:
	 *
	 * <pre>{@code
	 * long ck = Checksums.checksum(1, "abc", 7L);
	 * }</pre>
	 *
	 * @param objs Sequence of objects
	 * @return Calculated checksum
	 */
	public static long checksum(Object... objs) {
		if (objs == null) {
			return -1L;
		}
		Checksum ck = new CRC32();
		for (Object obj : objs) {
			if (obj == null) {
				ck.update(0);
			} else if (obj.toString().isEmpty()) {
				ck.update(1);
			} else {
				byte[] b = obj.toString().getBytes(UTF_8);
				ck.update(2);
				ck.update(b, 0, b.length);
			}
		}
		return ck.getValue();
	}
}
