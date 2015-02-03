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

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

public class ProximityMap<V> extends TreeMap<BigDecimal, V> {
    private static final long serialVersionUID = 1L;

    public static enum Round {
        DOWN,
        UP;
    }

    private Round round;

    public ProximityMap() {
        this(Round.DOWN);
    }

    public ProximityMap(Round round) {
        this.round = round;
    }

    public Map.Entry<BigDecimal, V> closestEntry(BigDecimal key) {
        // find exact or lower key match
        Map.Entry<BigDecimal, V> lower = floorEntry(key);

        // exact match?
        if (lower != null && lower.getKey().compareTo(key) == 0) {
            return lower;
        }

        // find higher key match
        Map.Entry<BigDecimal, V> higher = higherEntry(key);

        // beyond minimum or maximum keys?
        if (lower == null) {
            return higher;
        } else if (higher == null) {
            return lower;
        }

        // closer to lower or higher key match?
        int cmp = higher.getKey().subtract(key).compareTo(key.subtract(lower.getKey()));
        if (cmp > 0) {
            // closer to lower key
            return lower;
        } else if (cmp < 0) {
            // closer to higher key
            return higher;
        }

        // closest
        return round == Round.DOWN ? lower : higher;
    }

    public BigDecimal closestKey(BigDecimal key) {
        Map.Entry<BigDecimal, V> entry = closestEntry(key);
        return entry != null ? entry.getKey() : null;
    }

    public V closestValue(BigDecimal key) {
        Map.Entry<BigDecimal, V> entry = closestEntry(key);
        return entry != null ? entry.getValue() : null;
    }
}
