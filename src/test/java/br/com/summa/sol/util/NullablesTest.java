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

import static br.com.summa.sol.util.Nullables.coalesce;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NullablesTest {
    @Test
    public void testCoalesce1Param() {
        assertEquals(null, coalesce(null));
        assertEquals(null, coalesce((String)null));
        assertEquals("a", coalesce("a"));
        assertEquals(null, coalesce((String[])null));
        assertEquals(null, coalesce(new String[] { null }));
        assertEquals(null, coalesce(new String[] { null, null }));
        assertEquals("b", coalesce(new String[] { null, "b" }));
        assertEquals("a", coalesce(new String[] { "a", null }));
        assertEquals("a", coalesce(new String[] { "a", "b" }));
    }

    @Test
    public void testCoalesce2Params() {
        assertEquals(null, coalesce(null, null));
        assertEquals("b", coalesce(null, "b"));
        assertEquals("a", coalesce("a", null));
        assertEquals("a", coalesce("a", "b"));
    }

    @Test
    public void testCoalesce3Params() {
        assertEquals(null, coalesce(null, null, null));
        assertEquals("c", coalesce(null, null, "c"));
        assertEquals("b", coalesce(null, "b", null));
        assertEquals("b", coalesce(null, "b", "c"));
        assertEquals("a", coalesce("a", null, null));
        assertEquals("a", coalesce("a", null, "c"));
        assertEquals("a", coalesce("a", "b", null));
        assertEquals("a", coalesce("a", "b", "c"));
    }

    @Test
    public void testCoalesce4Params() {
        assertEquals(null, coalesce(null, null, null, null));
        assertEquals("d", coalesce(null, null, null, "d"));
        assertEquals("c", coalesce(null, null, "c", null));
        assertEquals("c", coalesce(null, null, "c", "d"));
        assertEquals("b", coalesce(null, "b", null, null));
        assertEquals("b", coalesce(null, "b", null, "d"));
        assertEquals("b", coalesce(null, "b", "c", null));
        assertEquals("b", coalesce(null, "b", "c", "d"));
        assertEquals("a", coalesce("a", null, null, null));
        assertEquals("a", coalesce("a", null, null, "d"));
        assertEquals("a", coalesce("a", null, "c", null));
        assertEquals("a", coalesce("a", null, "c", "d"));
        assertEquals("a", coalesce("a", "b", null, null));
        assertEquals("a", coalesce("a", "b", null, "d"));
        assertEquals("a", coalesce("a", "b", "c", null));
        assertEquals("a", coalesce("a", "b", "c", "d"));
    }
}
