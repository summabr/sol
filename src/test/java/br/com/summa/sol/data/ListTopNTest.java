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

import java.util.Arrays;

import org.junit.Test;

public class ListTopNTest extends AbstractTopNTest {
    private static final UniqueLastDigitStrategy strategy = new UniqueLastDigitStrategy();

    @Test
    public void addAllSingle() {
        validate(new ListTopN<Integer>(3, strategy), Arrays.asList(1), Arrays.asList(1));
    }

    @Test
    public void addAllNonExclusive() {
        validate(new ListTopN<Integer>(3, strategy), Arrays.asList(1, 3), Arrays.asList(3, 1));
    }

    @Test
    public void addAllExclusive() {
        validate(new ListTopN<Integer>(3, strategy), Arrays.asList(1, 11), Arrays.asList(11));
    }

    @Test
    public void addAllMany() {
        validate(new ListTopN<Integer>(3, strategy), Arrays.asList(1, 2, 3, 4, 12, 14, 22), Arrays.asList(22, 14, 3));
    }

    public static class UniqueLastDigitStrategy implements ExclusionStrategy<Integer> {
        @Override
        public boolean mutuallyExclusive(Integer elem1, Integer elem2) {
            return elem1.intValue() % 10 == elem2.intValue() % 10;
        }
    }
}
