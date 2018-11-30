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
package tech.metacontext.ec.prototype.composer_draft;

import tech.metacontext.ec.prototype.composer_draft.Composition;
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
public class CompositionTest {

   public CompositionTest() {
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
    * Test of addNode method, of class Composition.
    */
   @Test
   public void testAddNode() {
      System.out.println("addNode");
      Composition instance = new Composition();
      int expResult = 10;
      for (int i = 1; i < expResult; i++) {
         instance.compose();
      }
      int result = instance.length();
      assertEquals(expResult, result);
   }

   /**
    * Test of toString method, of class Composition.
    */
   @Test
   public void testToString() {
      System.out.println("toString");
      Composition instance = new Composition();
      instance.compose();
      instance.compose();
      instance.compose();
      String result = instance.toString();
      assertTrue(result.contains("Composition")
              && result.contains("SketchNode")
              && result.contains("Connector"));
      System.out.println(result);
   }

}
