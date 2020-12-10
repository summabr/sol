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

import java.util.Arrays;

import org.junit.Test;

public class TreeTopNTest extends AbstractTopNTest {
    @Test
    public void addAll1Lenient() {
        validate(new TreeTopN<Integer>(3, Integer.MAX_VALUE), Arrays.asList(1), Arrays.asList(1));
    }

    @Test
    public void addAll2Lenient() {
        validate(new TreeTopN<Integer>(3, Integer.MAX_VALUE), Arrays.asList(1, 2), Arrays.asList(2, 1));
    }

    @Test
    public void addAll3Lenient() {
        validate(new TreeTopN<Integer>(3, Integer.MAX_VALUE), Arrays.asList(1, 3, 2), Arrays.asList(3, 2, 1));
    }

    @Test
    public void addAll4Lenient() {
        validate(new TreeTopN<Integer>(3, Integer.MAX_VALUE), Arrays.asList(1, 3, 2, 4), Arrays.asList(4, 3, 2));
    }

    @Test
    public void addAllAscendingLenient() {
        validate(new TreeTopN<Integer>(3, Integer.MAX_VALUE), Arrays.asList(1, 2, 3, 4, 5, 6, 7), Arrays.asList(7, 6, 5));
    }

    @Test
    public void addAllDescendingLenient() {
        validate(new TreeTopN<Integer>(3, Integer.MAX_VALUE), Arrays.asList(7, 6, 5, 4, 3, 2, 1), Arrays.asList(7, 6, 5));
    }

    @Test
    public void addAllTipsFirstLenient() {
        validate(new TreeTopN<Integer>(3, Integer.MAX_VALUE), Arrays.asList(7, 1, 6, 2, 5, 3, 4), Arrays.asList(7, 6, 5));
    }

    @Test
    public void addAllMiddleFirstLenient() {
        validate(new TreeTopN<Integer>(3, Integer.MAX_VALUE), Arrays.asList(4, 3, 5, 2, 6, 1, 7), Arrays.asList(7, 6, 5));
    }

    @Test
    public void addAllAscendingWithRepetitionLenient() {
        validate(new TreeTopN<Integer>(3, Integer.MAX_VALUE), Arrays.asList(1, 1, 2, 2, 3, 3, 4, 4), Arrays.asList(4, 4, 3, 3));
    }

    @Test
    public void addAllDescendingWithRepetitionLenient() {
        validate(new TreeTopN<Integer>(3, Integer.MAX_VALUE), Arrays.asList(4, 4, 3, 3, 2, 2, 1, 1), Arrays.asList(4, 4, 3, 3));
    }

    @Test
    public void addAllTipsFirstWithRepetitionLenient() {
        validate(new TreeTopN<Integer>(3, Integer.MAX_VALUE), Arrays.asList(4, 1, 4, 1, 2, 3, 2, 3), Arrays.asList(4, 4, 3, 3));
    }

    @Test
    public void addAllMiddleFirstWithRepetitionLenient() {
        validate(new TreeTopN<Integer>(3, Integer.MAX_VALUE), Arrays.asList(3, 2, 2, 3, 1, 4, 4, 1), Arrays.asList(4, 4, 3, 3));
    }

    @Test
    public void addAllAscendingValidateRepetitionOrderLenient() {
        Integer x1a = 1;
        Integer x1b = 1;
        Integer x2a = 2;
        Integer x2b = 2;
        Integer x0 = 0;
        validateRepetitionOrder(new TreeTopN<Integer>(3, Integer.MAX_VALUE),
                Arrays.asList(x0, x1a, x1b, x2a, x2b),
                Arrays.asList(x2a, x2b, x1a, x1b));
    }

    @Test
    public void addAllDescendingValidateRepetitionOrderLenient() {
        Integer x1a = 1;
        Integer x1b = 1;
        Integer x2a = 2;
        Integer x2b = 2;
        Integer x0 = 0;
        validateRepetitionOrder(new TreeTopN<Integer>(3, Integer.MAX_VALUE),
                Arrays.asList(x2b, x2a, x1a, x1b, x0),
                Arrays.asList(x2b, x2a, x1a, x1b));
    }
}
