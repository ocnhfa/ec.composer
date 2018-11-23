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
package tech.metacontext.ec.prototype.composer.connectors.descriptors;

import tech.metacontext.ec.prototype.composer.nodes.materials.PitchSet;
import tech.metacontext.ec.prototype.composer.enums.Pitch;
import tech.metacontext.ec.prototype.composer.abs.IdeaDescriptor;
import static tech.metacontext.ec.prototype.composer.enums.ConsonanceType.getType;

/**
 * Describe the consonance of a PitchSet with a double getType number ranging
 * from 0.0 (extremely dissonant) to 100.0 (perfectly consonant).
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class DegreeOfConsonance extends IdeaDescriptor<PitchSet, Double> {

   private static DegreeOfConsonance instance;

   private DegreeOfConsonance() {
   }

   public static DegreeOfConsonance getInstance() {
      if (instance == null) {
         instance = new DegreeOfConsonance();
      }
      return instance;
   }

   double degree;

   @Override
   public Double describe(PitchSet factor) {
      degree = 100;
      factor.getPitches().stream().forEach((Pitch p1) -> {
         factor.getPitches().forEach((Pitch p2) -> {
            if (p1.compareTo(p2) < 0) {
               degree *= getType(p1, p2).factor;
               System.out.printf("%s, %s: %s -> %.2f\n", p1, p2, getType(p1, p2), degree);
            }
         });
      });
      return degree;
   }

}
