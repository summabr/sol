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
     * Locates all fields of a certain type, anywhere within a provided object, replacing them with the 
     * specified instance.<br>
     * <br>
     * It's equivalent to: 
     * 
     * <pre>
     * new Replacer().prepare(type, replacement).replaceFields(obj);
     * </pre>
     * 
     * @param obj Object to be modified
     * @param type Class type to be replaced
     * @param replacement Replacement instance
     * @throws IllegalAccessException if a certain field is not accessible
     */
	public static <T> void replaceFields(Object obj, Class<T> type, T replacement) throws IllegalAccessException {
		new Reflector().prepare(type, replacement).replaceFields(obj);
	}
	
	/**
     * @deprecated Renamed to {@link #getAllInheritedFields}
     */
    public static List<Field> getAllFields(Class<?> type) throws SecurityException {
    	return getAllInheritedFields(type);
    }    
    
    /**
     * Returns a list of all fields declared by the specified class or interface, or inherired from its super
     * classes. However fields inherired from "super interfaces" are ignored.
     *
     * @param type Specified class or interface
     * @return list of all fields declared by the specified class or interface, or inherired from its super
     * classes
     * @throws SecurityException if security manager denied access to field, or class loader didn't match
     */
    public static List<Field> getAllInheritedFields(Class<?> type) throws SecurityException {
        List<Field> fields = new ArrayList<Field>();
        for (Class<?> t = type; t != null && t != Object.class; t = t.getSuperclass()) {
            fields.addAll(Arrays.asList(t.getDeclaredFields()));
        }
        return fields;
    }

    /**
     * @deprecated Renamed to {@link #getAnyInheritedField}
     */
    public static Field getAnyField(Class<?> type, String name) 
    		throws NoSuchFieldException, SecurityException {
    	return getAnyInheritedField(type, name);
    }

    /**
     * Returns the field declared by the specified class or interface, or inherired from its super
     * classes, corresponding to the specified simple field name. Fields inherired from "super interfaces"
     * are ignored.
     *
     * @param type Specified class or interface
     * @param name Simple field name
     * @return Field declared by the specified class or interface, or inherired from its super
     * classes, corresponding to the specified simple field name
     * @throws NoSuchFieldException if a field with the specified name is not found
     * @throws SecurityException if security manager denied access to field, or class loader didn't match
     */
    public static Field getAnyInheritedField(Class<?> type, String name)
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
}
