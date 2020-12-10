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

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ReflectionsTest {

    public static class Hierarchy1 {
        private String str;
        public String getStr() {
            return str;
        }
        public Hierarchy1(String str) {
            this.str = str;
        }
    }

    public static class Hierarchy2 extends Hierarchy1 {
        public Hierarchy2(String str) {
            super(str);
        }
    }

    public static class Hierarchy3 extends Hierarchy2 {
        public Hierarchy3(String str) {
            super(str);
        }
    }

    public static class TypedFields {
        public static Hierarchy2 ss = new Hierarchy2("none");
        private int i = 1;
        private Integer ii = 2;
        private Hierarchy1 obj1 = new Hierarchy1("1");
        private Hierarchy2 obj2 = new Hierarchy2("2");
        private Hierarchy3 obj3 = new Hierarchy3("3");
        private Hierarchy1 obj4 = new Hierarchy3("4");
        private Hierarchy2 obj5 = new Hierarchy3("5");
        public int getI() {
            return i;
        }
        public Integer getIi() {
            return ii;
        }
        public Hierarchy1 getObj1() {
            return obj1;
        }
        public Hierarchy2 getObj2() {
            return obj2;
        }
        public Hierarchy3 getObj3() {
            return obj3;
        }
        public Hierarchy1 getObj4() {
            return obj4;
        }
        public Hierarchy2 getObj5() {
            return obj5;
        }
    }

    @Test
    public void testTypedFields() throws Exception {
        TypedFields sample = new TypedFields();
        new Reflector().prepare(Hierarchy2.class, new Hierarchy3("new")).replaceFields(sample);

        assertEquals("none", TypedFields.ss.getStr());
        assertEquals(1, sample.getI());
        assertEquals((Integer)2, sample.getIi());
        assertEquals("1", sample.getObj1().getStr());
        assertEquals("new", sample.getObj2().getStr());
        assertEquals("new", sample.getObj3().getStr());
        assertEquals("4", sample.getObj4().getStr());
        assertEquals("new", sample.getObj5().getStr());
    }

    public static class ObjectFields {
        public static Object ss = new Hierarchy2("none");
        private int i = 1;
        private Integer ii = 2;
        private Object obj1 = new Hierarchy1("1");
        private Object obj2 = new Hierarchy2("2");
        private Object obj3 = new Hierarchy3("3");
        private Object obj4 = new TypedFields();
        public int getI() {
            return i;
        }
        public Integer getIi() {
            return ii;
        }
        public Object getObj1() {
            return obj1;
        }
        public Object getObj2() {
            return obj2;
        }
        public Object getObj3() {
            return obj3;
        }
        public Object getObj4() {
            return obj4;
        }
    }

    @Test
    public void testObjectFields() throws Exception {
        ObjectFields sample = new ObjectFields();
        new Reflector().prepare(Hierarchy2.class, new Hierarchy3("new")).replaceFields(sample);

        assertEquals("none", TypedFields.ss.getStr());
        assertEquals(1, sample.getI());
        assertEquals((Integer)2, sample.getIi());
        assertEquals("none", ((Hierarchy2)ObjectFields.ss).getStr());
        assertEquals("1", ((Hierarchy1)sample.getObj1()).getStr());
        assertEquals("2", ((Hierarchy1)sample.getObj2()).getStr());
        assertEquals("3", ((Hierarchy1)sample.getObj3()).getStr());
        assertEquals(1, ((TypedFields)sample.getObj4()).getI());
        assertEquals((Integer)2, ((TypedFields)sample.getObj4()).getIi());
        assertEquals("1", ((TypedFields)sample.getObj4()).getObj1().getStr());
        assertEquals("new", ((TypedFields)sample.getObj4()).getObj2().getStr());
        assertEquals("new", ((TypedFields)sample.getObj4()).getObj3().getStr());
        assertEquals("4", ((TypedFields)sample.getObj4()).getObj4().getStr());
        assertEquals("new", ((TypedFields)sample.getObj4()).getObj5().getStr());
    }

    public static class CyclicReferenceFields {
        private String s = "tst";
        private BigDecimal z = BigDecimal.ZERO;
        private Hierarchy2 obj2;
        private CyclicReferenceFields prev;
        private Object next;
        private List<Object> list1 = new ArrayList<Object>();
        private List<CyclicReferenceFields> list2 = new ArrayList<CyclicReferenceFields>();
        public String getS() {
            return s;
        }
        public void setS(String s) {
            this.s = s;
        }
        public BigDecimal getZ() {
            return z;
        }
        public void setZ(BigDecimal z) {
            this.z = z;
        }
        public Hierarchy2 getObj2() {
            return obj2;
        }
        public void setObj2(Hierarchy2 obj2) {
            this.obj2 = obj2;
        }
        public CyclicReferenceFields getPrev() {
            return prev;
        }
        public void setPrev(CyclicReferenceFields prev) {
            this.prev = prev;
        }
        public Object getNext() {
            return next;
        }
        public void setNext(Object next) {
            this.next = next;
        }
        public List<Object> getList1() {
            return list1;
        }
        public void setList1(List<Object> list1) {
            this.list1 = list1;
        }
        public List<CyclicReferenceFields> getList2() {
            return list2;
        }
        public void setList2(List<CyclicReferenceFields> list2) {
            this.list2 = list2;
        }
    }

    @Test
    public void testCyclicReferenceFields() throws Exception {
        CyclicReferenceFields sample1 = new CyclicReferenceFields();
        CyclicReferenceFields sample2 = new CyclicReferenceFields();
        CyclicReferenceFields sample3 = new CyclicReferenceFields();
        CyclicReferenceFields sample4 = new CyclicReferenceFields();
        CyclicReferenceFields sample5 = new CyclicReferenceFields();
        sample1.setObj2(new Hierarchy2("2"));
        sample2.setObj2(null);
        sample3.setObj2(new Hierarchy3("3"));
        sample1.setPrev(sample1);
        sample2.setPrev(sample3);
        sample3.setPrev(sample2);
        sample1.setNext(sample2);
        sample2.setNext(sample3);
        sample3.setNext(sample3);
        sample1.setList1(null);
        sample2.getList1().add(sample3);
        sample3.getList1().add(sample2);
        sample3.getList1().add(sample4);
        sample1.getList2().add(sample5);

        new Reflector().prepare(Hierarchy2.class, new Hierarchy3("new")).replaceFields(sample1);
        assertEquals("new", sample1.getObj2().getStr());
        assertEquals("new", sample2.getObj2().getStr());
        assertEquals("new", sample3.getObj2().getStr());
        assertEquals("new", sample4.getObj2().getStr());
        assertEquals("new", sample5.getObj2().getStr());
    }
}
