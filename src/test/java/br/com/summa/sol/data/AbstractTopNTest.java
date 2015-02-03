/**
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTopNTest {

    private void validateContent(TopN<Integer> topN) {
        Object[] objArray = topN.toArray();
        Integer[] intArray = topN.toArray(new Integer[topN.size()]);
        List<Integer> result = new ArrayList<Integer>(topN);
        int i = 0;
        for (Integer elem : topN) {
            assertTrue(elem == objArray[i]);
            assertTrue(elem == intArray[i]);
            assertTrue(elem == result.get(i++));
        }
        assertEquals(topN.size(), i);
        assertEquals(objArray.length, i);
        assertEquals(intArray.length, i);
        assertEquals(result.size(), i);
    }

    protected void validate(int n, List<Integer> input, List<Integer> expected) {
        validate(new ListTopN<Integer>(n), input, expected);
        validate(new TreeTopN<Integer>(n), input, expected);
    }

    protected List<Integer> validate(TopN<Integer> topN, List<Integer> input,
            List<Integer> expected) {
        assertTrue(topN.isEmpty());
        topN.addAll(input);
        List<Integer> result = new ArrayList<Integer>(topN);
        assertEquals(expected, result);
        assertEquals(expected.size(), result.size());
        assertEquals(expected.size(), topN.size());
        assertFalse(topN.isEmpty());
        validateContent(topN);
        return result;
    }

    protected void validateRepetitionOrder(int n, List<Integer> input, List<Integer> expected) {
        validateRepetitionOrder(new ListTopN<Integer>(n), input, expected);
        validateRepetitionOrder(new TreeTopN<Integer>(n), input, expected);
    }

    protected void validateRepetitionOrder(TopN<Integer> topN, List<Integer> input,
            List<Integer> expected) {
        List<Integer> result = validate(topN, input, expected);
        for (int i=0; i < result.size(); i++) {
            assertTrue(expected.get(i) == result.get(i));
        }
        validateContent(topN);
    }
}
