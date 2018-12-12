/*
 * Copyright 2018 Jonathan Chang, Chun-yien <ccy@musicapoetica.org>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tech.metacontext.ec.prototype.composer_draft.nodes.materials;

import tech.metacontext.ec.prototype.composer.draft.nodes.materials.NoteNumber;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class NoteNumberTest {

  public NoteNumberTest() {
  }

  @BeforeClass
  public static void setUpClass() {
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

  /**
   * Test of randomInit method, of class NoteNumber.
   */
  @Test

  public void testRandomInit() {
    System.out.println("randomInit");
    NoteNumber nn = new NoteNumber();
    nn.randomInit();
    System.out.println(nn);
  }

  /**
   * Test of add method, of class NoteNumber.
   */
  @Test
  @Ignore
  public void testAdd() {
    System.out.println("add");
    Integer element = null;
    NoteNumber instance = new NoteNumber();
    instance.add(element);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of remove method, of class NoteNumber.
   */
  @Test
  @Ignore
  public void testRemove() {
    System.out.println("remove");
    Integer element = null;
    NoteNumber instance = new NoteNumber();
    instance.remove(element);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of toString method, of class NoteNumber.
   */
  @Test
  @Ignore
  public void testToString() {
    System.out.println("toString");
    NoteNumber instance = new NoteNumber();
    String expResult = "";
    String result = instance.toString();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of getInit_size method, of class NoteNumber.
   */
  @Test
  @Ignore
  public void testGetInit_size() {
    System.out.println("getInit_size");
    NoteNumber instance = new NoteNumber();
    int expResult = 0;
    int result = instance.getInit_size();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of setInit_size method, of class NoteNumber.
   */
  @Test
  @Ignore
  public void testSetInit_size() {
    System.out.println("setInit_size");
    int init_size = 0;
    NoteNumber instance = new NoteNumber();
    instance.setInit_size(init_size);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of getMin method, of class NoteNumber.
   */
  @Test
  @Ignore
  public void testGetMin() {
    System.out.println("getMin");
    NoteNumber instance = new NoteNumber();
    int expResult = 0;
    int result = instance.getMin();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of setMin method, of class NoteNumber.
   */
  @Test
  @Ignore
  public void testSetMin() {
    System.out.println("setMin");
    int min = 0;
    NoteNumber instance = new NoteNumber();
    instance.setMin(min);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of getMax method, of class NoteNumber.
   */
  @Test
  @Ignore
  public void testGetMax() {
    System.out.println("getMax");
    NoteNumber instance = new NoteNumber();
    int expResult = 0;
    int result = instance.getMax();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of setMax method, of class NoteNumber.
   */
  @Test
  @Ignore
  public void testSetMax() {
    System.out.println("setMax");
    int max = 0;
    NoteNumber instance = new NoteNumber();
    instance.setMax(max);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

}
