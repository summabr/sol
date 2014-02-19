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

import br.com.summa.sol.util.Nullables;

/**
 * A comparable Trio.
 *
 * DISCLAIMER: This class is only intended to temporarily store a disjoint
 * set of objects, for instance when a certain method needs to return three
 * distinct results. However, in cases where these objects make any sense
 * together, it's much better OOP to create a proper custom class to combine
 * them instead.
 *
 * @author Einar Saukas
 */
public class Triple<X extends Comparable<X>, Y extends Comparable<Y>, Z extends Comparable<Z>>
extends Trio<X, Y, Z> implements Comparable<Triple<X, Y, Z>> {

    private final static long serialVersionUID = 1L;

    public Triple(X x, Y y, Z z)
    {
        super(x, y, z);
    }

    public int compareTo(Triple<X, Y, Z> other)
    {
        if (this == other) {
            return 0;
        }
        if (null == other) {
            return 1;
        }
        int result = Nullables.compareToWithNullsFirst(getX(), other.getX());
        if (result == 0) {
            result = Nullables.compareToWithNullsFirst(getY(), other.getY());
            if (result == 0) {
                result = Nullables.compareToWithNullsFirst(getZ(), other.getZ());
            }
        }
        return result;
    }
}

