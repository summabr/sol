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

import java.io.Serializable;

import br.com.summa.sol.util.Nullables;

/**
 * Generic immutable class to store a set of 3 objects.<br>
 * <br>
 * DISCLAIMER: This class is only intended to temporarily store a disjoint
 * set of objects, for instance when a certain method needs to return three
 * distinct results. However, in cases where these objects make any sense
 * together, it's much better OOP to create a proper custom class to combine
 * them instead.
 *
 * @author Einar Saukas
 */
public class Trio<X, Y, Z> implements Serializable {
    private final static long serialVersionUID = 1L;

    private final X x;
    private final Y y;
    private final Z z;

    public Trio(X x, Y y, Z z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public X getX() {
        return x;
    }

    public Y getY() {
        return y;
    }

    public Z getZ() {
        return z;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Trio) {
            final Trio<?, ?, ?> that = (Trio<?, ?, ?>)obj;
            return Nullables.equals(x, that.x) &&
                   Nullables.equals(y, that.y) &&
                   Nullables.equals(z, that.z);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Nullables.hashCode(x, y, z);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + "," + z + ")";
    }
}
