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
package tech.metacontext.ec.prototype.test;

import java.util.List;
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
public class MusicalIdeasTest {

  public MusicalIdeasTest() {
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

    MusicalIdeas test = new MusicalIdeas(0);
    TensionCurve tc1 = new TensionCurve("1");
    tc1.generateRandom();
    test.add(tc1);
    test.render(1);
    TensionCurve tc2 = new TensionCurve("2", tc1.getCurve());
    test.add(tc2);
    test.render(2);
  }

  /**
   * Test of initiate method, of class MusicalIdeas.
   */
  @Test
  @Ignore
  public void testInitiate() {
    System.out.println("initiate");
    int size = 0;
    MusicalIdeas instance = null;
    instance.initiate(size);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of evolution method, of class MusicalIdeas.
   */
  @Test
  @Ignore
  public void testEvolution() {
    System.out.println("evolution");
    MusicalIdeas instance = null;
    int expResult = 0;
    int result = instance.evolution();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of crossover method, of class MusicalIdeas.
   */
  @Test
  @Ignore
  public void testCrossover() {
    System.out.println("crossover");
    TensionCurve[] e = null;
    MusicalIdeas instance = null;
    List<TensionCurve> expResult = null;
    List<TensionCurve> result = instance.crossover(e);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of mutation method, of class MusicalIdeas.
   */
  @Test
  @Ignore
  public void testMutation() {
    System.out.println("mutation");
    TensionCurve[] e = null;
    MusicalIdeas instance = null;
    List<TensionCurve> expResult = null;
    List<TensionCurve> result = instance.mutation(e);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of render method, of class MusicalIdeas.
   */
  @Test
  @Ignore
  public void testRender_0args() {
    System.out.println("render");
    MusicalIdeas instance = null;
    instance.render();
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of render method, of class MusicalIdeas.
   */
  @Test
  @Ignore
  public void testRender_int() {
    System.out.println("render");
    int i = 0;
    MusicalIdeas instance = null;
    instance.render(i);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of addDataToChart method, of class MusicalIdeas.
   */
  @Test
  @Ignore
  public void testAddDataToChart() {
    System.out.println("addDataToChart");
    TensionCurve tc = null;
    MusicalIdeas instance = null;
    instance.addDataToChart(tc);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of renderHighest method, of class MusicalIdeas.
   */
  @Test
  @Ignore
  public void testRenderHighest() {
    System.out.println("renderHighest");
    int i = 0;
    MusicalIdeas instance = null;
    instance.renderHighest(i);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

}
