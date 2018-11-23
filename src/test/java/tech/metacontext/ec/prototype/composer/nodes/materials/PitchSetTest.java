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
package tech.metacontext.ec.prototype.composer.nodes.materials;

import tech.metacontext.ec.prototype.composer.enums.Pitch;
import tech.metacontext.ec.prototype.composer.nodes.materials.PitchSet;
import java.util.Set;
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
public class PitchSetTest {

  public PitchSetTest() {
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

  @Test
  public void test() {
    PitchSet ps = new PitchSet(Pitch.D, Pitch.A, Pitch.B, Pitch.C, Pitch.C_sharp);
    System.out.println("set1 = " + ps);
    ps.remove(Pitch.B);
    System.out.println("set1 = " + ps);
    ps.add(Pitch.F);
    System.out.println("set1 = " + ps);
  }

  /**
   * Test of getPitches method, of class PitchSet.
   */
  @Test
  @Ignore
  public void testGetPitches() {
    System.out.println("getPitches");
    PitchSet instance = new PitchSet();
    Set<Pitch> expResult = null;
    Set<Pitch> result = instance.getPitches();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of randomInit method, of class PitchSet.
   */
  @Test
  @Ignore
  public void testRandomInit() {
    System.out.println("randomInit");
    PitchSet instance = new PitchSet();
    instance.randomInit();
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of remove method, of class PitchSet.
   */
  @Test
  @Ignore
  public void testRemove() {
    System.out.println("remove");
    Pitch pitchToRemove = null;
    PitchSet instance = new PitchSet();
    instance.remove(pitchToRemove);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of add method, of class PitchSet.
   */
  @Test
  @Ignore
  public void testAdd() {
    System.out.println("add");
    Pitch pitchToAdd = null;
    PitchSet instance = new PitchSet();
    instance.add(pitchToAdd);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of toString method, of class PitchSet.
   */
  @Test
  @Ignore
  public void testToString() {
    System.out.println("toString");
    PitchSet instance = new PitchSet();
    String expResult = "";
    String result = instance.toString();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of main method, of class PitchSet.
   */
  @Test
  @Ignore
  public void testMain() {
    System.out.println("main");
    String[] args = null;
    PitchSet.main(args);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

}
