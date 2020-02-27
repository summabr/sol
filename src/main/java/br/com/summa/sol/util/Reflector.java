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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Utility class to locate all fields of a certain type, anywhere within a provided object,
 * replacing them with the specified instance.<br>
 * <br>
 * It can be used to inject mocks in cases where <code>@InjectMock</code> don't work. For instance:<br>
 * <br>
 * <pre>
 * &#64;Mock
 * private FirstRepository firstRepositoryMock;
 *
 * &#64;Mock
 * private SecondRepository secondRepositoryMock;
 *
 * &#64;Inject &#64;Qualifier("A")       // &#64;InjectMock won't work here
 * public GenericService service;
 *
 * &#64;BeforeEach
 * public void setup() throws Exception {
 *   new Reflector()
 *      .prepare(FirstRepository.class, firstRepositoryMock)
 *      .prepare(SecondRepository.class, secondRepositoryMock)
 *      .replaceFields(service);
 * }
 * </pre>
 *
 * @author Einar Saukas
 */
public final class Reflector {

    private Map<Class<?>, Object> map = new HashMap<Class<?>, Object>();
    private Set<Object> visited = new HashSet<Object>();

    public Reflector() {}

    private Map.Entry<Class<?>, Object> findMatch(Class<?> type) {
        for (Map.Entry<Class<?>, Object> entry : map.entrySet()) {
            if (entry.getKey().isAssignableFrom(type)) {
                return entry;
            }
        }
        return null;
    }

    /**
     * Specify the field type to be replaced by a certain instance.
     *
     * @param <T> Type of object to be replaced
     * @param type Class type to be replaced
     * @param replacement Replacement instance
     * @return Itself
     */
    public <T> Reflector prepare(Class<T> type, T replacement) {
        map.put(type, replacement);
        return this;
    }

    /**
     * Apply all specified replacements upon an object.
     *
     * @param obj Object to be modified
     * @throws IllegalAccessException If a certain field is not accessible
     */
    public void replaceFields(Object obj) throws IllegalAccessException {
        if (obj != null && visited.add(obj)) {
            for (Class<?> t = obj.getClass(); t != null && t != Object.class; t = t.getSuperclass()) {
                for (Field field : t.getDeclaredFields()) {
                    if (!Modifier.isStatic(field.getModifiers()) && !field.getType().isPrimitive()) {
                        Map.Entry<Class<?>, Object> entry = findMatch(field.getType());
                        if (entry != null) {
                            field.setAccessible(true);
                            field.set(obj, entry.getValue());
                        } else if (Collection.class.isAssignableFrom(field.getType())) {
                            field.setAccessible(true);
                            Collection<?> collect = (Collection<?>)field.get(obj);
                            if (collect != null) {
                                for (Object elem : collect) {
                                    replaceFields(elem);
                                }
                            }
                        } else if (field.getType() == Object.class || (!field.getType().toString().startsWith("class java.lang.") && !field.getType().toString().startsWith("class java.math."))) {
                            field.setAccessible(true);
                            replaceFields(field.get(obj));
                        }
                    }
                }
            }
        }
    }
}
