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
package tech.metacontext.ec.prototype.composer2.rules;

import tech.metacontext.ec.prototype.composer.rules.SmoothPitchSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import tech.metacontext.ec.prototype.composer.materials.PitchSet;
import tech.metacontext.ec.prototype.composer.materials.enums.Pitch;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class SmoothPitchSetTest {

   PitchSet p1, p2;
   SmoothPitchSet instance;

   public SmoothPitchSetTest() {

      p1 = new PitchSet(Pitch.A, Pitch.B, Pitch.C);
      p2 = new PitchSet(Pitch.C, Pitch.D, Pitch.E);
      instance = new SmoothPitchSet(p1, p2);
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

      System.out.println("main");
      PitchSet p1 = new PitchSet(), p2 = new PitchSet();
      for (int i = 0; i < 5; i++) {
         SmoothPitchSet mps = new SmoothPitchSet(p1.random(), p2.random());
         System.out.printf(
                 "%s(%d)\n"
                 + "%s(%d)\n"
                 + "rating = %.2f%%\n",
                 p1.getPitches(), p1.getPitches().size(),
                 p2.getPitches(), p2.getPitches().size(),
                 mps.rating() * 100);
      }
   }

   /**
    * Test of rating method, of class SmoothPitchSet.
    */
   @Test
   public void testRating() {

      System.out.println("rating");
      double expResult = 0.2;
      double result = instance.rating();
      assertEquals(expResult, result, 0.0);
   }

   /**
    * Test of getP1 method, of class SmoothPitchSet.
    */
   @Test
   public void testGetP1() {

      System.out.println("getP1");
      PitchSet expResult = p1;
      PitchSet result = instance.getP1();
      assertEquals(expResult, result);
   }

   /**
    * Test of setP1 method, of class SmoothPitchSet.
    */
   @Test
   public void testSetP1() {

      System.out.println("setP1");
      instance.setP1(p2);
      assertEquals(instance.rating(), 1.0, 0.0);
   }

   /**
    * Test of getP2 method, of class SmoothPitchSet.
    */
   @Test
   public void testGetP2() {

      System.out.println("getP2");
      PitchSet expResult = p2;
      PitchSet result = instance.getP2();
      assertEquals(expResult, result);
   }

   /**
    * Test of setP2 method, of class SmoothPitchSet.
    */
   @Test
   public void testSetP2() {

      System.out.println("setP2");
      instance.setP2(p1);
      assertEquals(instance.rating(), 1.0, 0.0);
   }

}
