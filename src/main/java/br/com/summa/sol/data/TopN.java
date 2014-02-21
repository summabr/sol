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
import java.util.Collection;

/**
 * A generic collection limited to size <code>N</code>. If more than <code>N</code>
 * elements are added, it will keep the greatest <code>N</code> elements only,
 * discarding the others.<br>
 *
 * @author Einar Saukas
 */
public interface TopN<E> extends Collection<E>, Serializable {
}
