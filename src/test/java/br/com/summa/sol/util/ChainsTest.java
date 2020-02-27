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

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class ChainsTest {
    @Test
    public void testJoin() {
        assertEquals("do/re/mi",
                Chains.join("/", "do", "re", "mi"));
        assertEquals("2 < 3 < 5 < 7 < 11 < 13 < 17",
                Chains.join(" < ", 2, 3, 5, 7, 11, 13, 17));
        assertEquals("false, 1, two, 3, IV, null, 6",
                Chains.join(", ", false, 1, "two", 3L, "IV", null, '6'));
        Integer array[] = {0, 1, 1, 2, 3, 5, 8};
        assertEquals("0-1-1-2-3-5-8",
                Chains.join("-", array));
        assertEquals("..null.a.",
                Chains.join(".", "", "", null, "a", ""));
    }

    @Test
    public void testTraverse() {
        List<String> notes = Arrays.asList("do", "re", "mi");
        assertEquals("do/re/mi",
                Chains.traverse("/", notes));
        assertEquals("do/re/mi",
                Chains.traverse("/", notes.iterator()));
        List<Integer> primes = Arrays.asList(2, 3, 5, 7, 11, 13, 17);
        assertEquals("2 < 3 < 5 < 7 < 11 < 13 < 17",
                Chains.traverse(" < ", primes));
        assertEquals("2 < 3 < 5 < 7 < 11 < 13 < 17",
                Chains.traverse(" < ", primes.iterator()));
        List<?> list = Arrays.asList(false, 1, "two", 3L, "IV", null, '6');
        assertEquals("false, 1, two, 3, IV, null, 6",
                Chains.traverse(", ", list));
        assertEquals("false, 1, two, 3, IV, null, 6",
                Chains.traverse(", ", list.iterator()));
        List<String> list2 = Arrays.asList("", "", null, "a", "");
        assertEquals("**null*a*",
                Chains.traverse("*", list2));
        assertEquals("..null.a.",
                Chains.traverse(".", list2.iterator()));
        LinkedList<Character> list3 = new LinkedList<Character>(Arrays.asList('a', 'b', 'c'));
        assertEquals("c - b - a",
                Chains.traverse(" - ", list3.descendingIterator()));
    }

    @Test
    public void testMap() {
        Map<String, Object> codes = new LinkedHashMap<String, Object>();
        codes.put("USA", 1);
        codes.put("UK", 44);
        codes.put("Brazil", 55);

        assertEquals("USA=1, UK=44, Brazil=55",
                Chains.traverse(", ", codes.entrySet()));
        assertEquals("USA=1, UK=44, Brazil=55",
                Chains.traverse(", ", codes.entrySet().iterator()));
        assertEquals("USA, UK, Brazil",
                Chains.traverse(", ", codes.keySet()));
        assertEquals("USA, UK, Brazil",
                Chains.traverse(", ", codes.keySet().iterator()));
        assertEquals("1, 44, 55",
                Chains.traverse(", ", codes.values()));
        assertEquals("1, 44, 55",
                Chains.traverse(", ", codes.values().iterator()));
    }

    @Test
    public void testMerge() {
        String none = null;
        String empty = Chains.join(".", "");
        String one = Chains.join(".", 1);
        String half = Chains.join(".", 0, 5);

        assertEquals("", Chains.merge("/", none, none));
        assertEquals("", Chains.merge("/", none, empty));
        assertEquals("0.5", Chains.merge("/", none, half));

        assertEquals("", Chains.merge("/", empty, none));
        assertEquals("", Chains.merge("/", empty, empty));
        assertEquals("0.5", Chains.merge("/", empty, half));

        assertEquals("1", Chains.merge("/", one, none));
        assertEquals("1", Chains.merge("/", one, empty));
        assertEquals("1/0.5", Chains.merge("/", one, half));
    }
}
