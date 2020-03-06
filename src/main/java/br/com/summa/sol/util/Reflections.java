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
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Convenience class for working with Java reflection.
 *
 * @author Einar Saukas
 */
public final class Reflections {

    /**
     * Prevents instantiation
     */
    private Reflections() {
    }

    /**
     * Returns a list of all methods declared by the specified class or interface, or inherited from its super
     * classes. However methods inherited from "super interfaces" are ignored.
     *
     * @param type Specified class or interface
     * @return list of all methods declared by the specified class or interface, or inherited from its super
     * classes
     * @throws SecurityException If security manager denied access to method, or class loader didn't match
     */
    public static List<Method> getAllMethods(Class<?> type) throws SecurityException {
        List<Method> methods = new ArrayList<Method>();
        for (Class<?> t = type; t != null && t != Object.class; t = t.getSuperclass()) {
            methods.addAll(Arrays.asList(t.getDeclaredMethods()));
        }
        return methods;
    }

    /**
     * @deprecated Replaced by <code>new Reflector().prepare(type, replacement).replaceFields(obj)</code>
     * @param <T> Type of object to be replaced
     * @param obj Object to be modified
     * @param type Class type to be replaced
     * @param replacement Replacement instance
     * @throws IllegalAccessException If a certain field is not accessible
     */
    public static <T> void replaceFields(Object obj, Class<T> type, T replacement) throws IllegalAccessException {
        new Reflector().prepare(type, replacement).replaceFields(obj);
    }

    /**
     * Returns a list of all fields declared by the specified class or interface, or inherited from its super
     * classes. However fields inherited from "super interfaces" are ignored.
     *
     * @param type Specified class or interface
     * @return list of all fields declared by the specified class or interface, or inherited from its super
     * classes
     * @throws SecurityException If security manager denied access to field, or class loader didn't match
     */
    public static List<Field> getAllFields(Class<?> type) throws SecurityException {
        List<Field> fields = new ArrayList<Field>();
        for (Class<?> t = type; t != null && t != Object.class; t = t.getSuperclass()) {
            fields.addAll(Arrays.asList(t.getDeclaredFields()));
        }
        return fields;
    }

    /**
     * @deprecated Renamed to {@link #getAllFields}
     *
     * @param type Specified class or interface
     * @return list of all fields declared by the specified class or interface, or inherited from its super
     * classes
     * @throws SecurityException If security manager denied access to field, or class loader didn't match
     */
    public static List<Field> getAllInheritedFields(Class<?> type) throws SecurityException {
        return getAllFields(type);
    }

    /**
     * Returns the field declared by the specified class or interface, or inherited from its super
     * classes, corresponding to the specified simple field name. Fields inherited from "super interfaces"
     * are ignored.
     *
     * @param type Specified class or interface
     * @param name Simple field name
     * @return Field declared by the specified class or interface, or inherited from its super
     * classes, corresponding to the specified simple field name
     * @throws NoSuchFieldException If a field with the specified name is not found
     * @throws SecurityException If security manager denied access to field, or class loader didn't match
     */
    public static Field getAnyField(Class<?> type, String name)
            throws NoSuchFieldException, SecurityException {
        try {
            return type.getDeclaredField(name);
        } catch (NoSuchFieldException e1) {
            for (Class<?> t = type.getSuperclass(); t != null && t != Object.class; t = t.getSuperclass()) {
                try {
                    return t.getDeclaredField(name);
                } catch (NoSuchFieldException e2) {
                }
            }
            throw e1;
        }
    }

    /**
     * @deprecated Renamed to {@link #getAnyField}
     *
     * @param type Specified class or interface
     * @param name Simple field name
     * @return Field declared by the specified class or interface, or inherited from its super
     * classes, corresponding to the specified simple field name
     * @throws NoSuchFieldException If a field with the specified name is not found
     * @throws SecurityException If security manager denied access to field, or class loader didn't match
     */
    public static Field getAnyInheritedField(Class<?> type, String name)
            throws NoSuchFieldException, SecurityException {
        return getAnyField(type, name);
    }
}
