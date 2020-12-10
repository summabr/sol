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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.lang.Character.UnicodeScript;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import br.com.summa.sol.data.ProximityMap.Round;

public class ProximityMapTest {

    private static final Pair<BigDecimal, UnicodeScript> javanese = new Pair<BigDecimal, UnicodeScript>(new BigDecimal(-42), UnicodeScript.JAVANESE);
    private static final Pair<BigDecimal, UnicodeScript> greek = new Pair<BigDecimal, UnicodeScript>(new BigDecimal(-22), UnicodeScript.GREEK);
    private static final Pair<BigDecimal, UnicodeScript> latin = new Pair<BigDecimal, UnicodeScript>(new BigDecimal(0), UnicodeScript.LATIN);
    private static final Pair<BigDecimal, UnicodeScript> gothic = new Pair<BigDecimal, UnicodeScript>(new BigDecimal(22), UnicodeScript.GOTHIC);

    @Test
    public void validateRoundDown() {
        ProximityMap<UnicodeScript> prox = new ProximityMap<UnicodeScript>();

        putAll(prox, Arrays.asList(javanese, greek, gothic, latin));

        validate(prox, -43, javanese);
        validate(prox, -42, javanese);
        validate(prox, -41, javanese);
        validate(prox, -33, javanese);
        validate(prox, -32, javanese);
        validate(prox, -31, greek);
        validate(prox, -23, greek);
        validate(prox, -22, greek);
        validate(prox, -21, greek);
        validate(prox, -12, greek);
        validate(prox, -11, greek);
        validate(prox, -10, latin);
        validate(prox, -1, latin);
        validate(prox, 0, latin);
        validate(prox, 1, latin);
        validate(prox, 10, latin);
        validate(prox, 11, latin);
        validate(prox, 12, gothic);
        validate(prox, 21, gothic);
        validate(prox, 22, gothic);
        validate(prox, 23, gothic);
    }

    @Test
    public void validateRoundUp() {
        ProximityMap<UnicodeScript> prox = new ProximityMap<UnicodeScript>(Round.UP);

        putAll(prox, Arrays.asList(javanese, greek, gothic, latin));

        validate(prox, -43, javanese);
        validate(prox, -42, javanese);
        validate(prox, -41, javanese);
        validate(prox, -33, javanese);
        validate(prox, -32, greek);
        validate(prox, -31, greek);
        validate(prox, -23, greek);
        validate(prox, -22, greek);
        validate(prox, -21, greek);
        validate(prox, -12, greek);
        validate(prox, -11, latin);
        validate(prox, -10, latin);
        validate(prox, -1, latin);
        validate(prox, 0, latin);
        validate(prox, 1, latin);
        validate(prox, 10, latin);
        validate(prox, 11, gothic);
        validate(prox, 12, gothic);
        validate(prox, 21, gothic);
        validate(prox, 22, gothic);
        validate(prox, 23, gothic);
    }

    @Test
    public void validateSingle() {
        ProximityMap<UnicodeScript> prox = new ProximityMap<UnicodeScript>();

        putAll(prox, Arrays.asList(javanese));

        validate(prox, -1000, javanese);
        validate(prox, -43, javanese);
        validate(prox, -42, javanese);
        validate(prox, 1000, javanese);
    }

    @Test
    public void validateEmpty() {
        ProximityMap<UnicodeScript> prox = new ProximityMap<UnicodeScript>();

        validate(prox, -1000, null);
        validate(prox, 1000, null);
    }

    private static <V> void putAll(ProximityMap<V> prox, List<Pair<BigDecimal, V>> pairs) {
        for (Pair<BigDecimal, V> pair : pairs) {
            prox.put(pair.getX(), pair.getY());
        }
    }

    private static void validate(ProximityMap<UnicodeScript> prox, int pos, Pair<BigDecimal, UnicodeScript> expected) {
        BigDecimal key = new BigDecimal(pos);
        if (expected == null) {
            assertNull(prox.closestEntry(key));
            assertNull(prox.closestKey(key));
            assertNull(prox.closestValue(key));
        } else {
            assertEquals(expected.getX(), prox.closestEntry(key).getKey());
            assertEquals(expected.getX(), prox.closestKey(key));
            assertEquals(expected.getY(), prox.closestEntry(key).getValue());
            assertEquals(expected.getY(), prox.closestValue(key));
        }
    }
}

