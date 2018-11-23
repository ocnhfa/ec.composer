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
package tech.metacontext.ec.prototype.composer.descriptors;

import tech.metacontext.ec.prototype.composer.enums.ConsonanceType;
import tech.metacontext.ec.prototype.composer.connectors.descriptors.DegreeOfConsonance;
import tech.metacontext.ec.prototype.composer.enums.Pitch;
import tech.metacontext.ec.prototype.composer.nodes.materials.PitchSet;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import static tech.metacontext.ec.prototype.composer.enums.ConsonanceType.getType;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class DegreeOfConsonanceTest {

   DegreeOfConsonance instance;

   public DegreeOfConsonanceTest() {
      instance = DegreeOfConsonance.getInstance();
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
   public void main() {
      List<PitchSet> tester = new ArrayList<>();
      tester.add(new PitchSet(Pitch.C, Pitch.G, Pitch.E));
      tester.add(new PitchSet(Pitch.C, Pitch.G, Pitch.E, Pitch.B_flat));
      tester.add(new PitchSet(Pitch.C, Pitch.G, Pitch.E, Pitch.B));
      tester.add(new PitchSet(Pitch.C, Pitch.G, Pitch.E_flat, Pitch.B_flat));
      tester.add(new PitchSet(Pitch.C, Pitch.G, Pitch.E_flat, Pitch.B));
      tester.forEach(this::test);
   }

   void test(PitchSet factor) {
      System.out.println(factor);
      System.out.printf("%.2f\n", instance.describe(factor));
   }

   /**
    * Test of getInstance method, of class DegreeOfConsonance.
    */
   @Test
   @Ignore
   public void testGetInstance() {
      System.out.println("getInstance");
      DegreeOfConsonance expResult = null;
      DegreeOfConsonance result = DegreeOfConsonance.getInstance();
      assertEquals(expResult, result);
      // TODO review the generated test code and remove the default call to fail.
      fail("The test case is a prototype.");
   }

   /**
    * Test of getType method, of class DegreeOfConsonance.
    */
   @Test
   @Ignore
   public void testType() {
      System.out.println("type");
      Pitch p1 = null;
      Pitch p2 = null;
      ConsonanceType expResult = null;
      ConsonanceType result = getType(p1, p2);
      assertEquals(expResult, result);
      // TODO review the generated test code and remove the default call to fail.
      fail("The test case is a prototype.");
   }

   /**
    * Test of describe method, of class DegreeOfConsonance.
    */
   @Test
   @Ignore
   public void testDescribe() {
      System.out.println("describe");
      PitchSet factor = null;
      DegreeOfConsonance instance = null;
      Double expResult = null;
      Double result = instance.describe(factor);
      assertEquals(expResult, result);
      // TODO review the generated test code and remove the default call to fail.
      fail("The test case is a prototype.");
   }

}
