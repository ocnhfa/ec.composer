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

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tech.metacontext.ec.prototype.composer.materials.Pitch;
import tech.metacontext.ec.prototype.composer.materials.PitchSet;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class CommonTonesTest {

   static Pitch[] ps1, ps2;
   static PitchSet set1, set2;
   static CommonTones instance;

   public CommonTonesTest() {
      instance = new CommonTones(set1);
   }

   @BeforeClass
   public static void before() {
      ps1 = new Pitch[]{Pitch.D, Pitch.A, Pitch.B, Pitch.C, Pitch.C_sharp};
      ps2 = new Pitch[]{Pitch.D, Pitch.G, Pitch.C, Pitch.C_sharp};
      set1 = new PitchSet(ps1);
      set2 = new PitchSet(ps2);
      System.out.println("set1 = " + set1);
      System.out.println("set2 = " + set2);
   }

   /**
    * Test of getBase method, of class CommonTones.
    */
   @Test
   public void testGetBase() {
      System.out.println("getBase");
      PitchSet expResult = set1;
      PitchSet result = instance.getBase();
      assertEquals(expResult, result);
   }

   /**
    * Test of setBase method, of class CommonTones.
    */
   @Test
   public void testSetBase() {
      System.out.println("setBase");
      PitchSet base = set2;
      instance.setBase(base);
      assertEquals(set2, instance.getBase());
   }

   /**
    * Test of describe method, of class CommonTones.
    */
   @Test
   public void testDescribe() {
      System.out.println("describe");
      PitchSet factor = set2;
      Long expResult = 3L;
      Long result = instance.describe(factor);
      assertEquals(expResult, result);
      System.out.println("common = " + result);
   }

   /**
    * Test of common method, of class CommonTones.
    */
   @Test
   public void testCommon() {
      System.out.println("common");
      long expResult = 3L;
      long result = CommonTones.common(set1, set2);
      assertEquals(expResult, result);
   }

}
