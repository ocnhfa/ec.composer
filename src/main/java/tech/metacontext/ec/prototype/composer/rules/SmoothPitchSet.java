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
package tech.metacontext.ec.prototype.composer.rules;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import tech.metacontext.ec.prototype.composer.materials.enums.Pitch;
import tech.metacontext.ec.prototype.composer.materials.PitchSet;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class SmoothPitchSet implements Rule {

   private PitchSet p1;
   private PitchSet p2;

   public SmoothPitchSet(PitchSet p1, PitchSet p2) {

      this.p1 = p1;
      this.p2 = p2;
   }

   @Override
   public double rating() {

      AtomicInteger common = new AtomicInteger();
      Pitch[] union = Stream.of(Pitch.values())
              .filter(p -> p1.getPitches().contains(p) || p2.getPitches().contains(p))
              .peek(p -> {
                 if (p1.getPitches().contains(p) && p2.getPitches().contains(p)) {
                    common.incrementAndGet();
                 }
              })
              .toArray(Pitch[]::new);
      System.out.println("common = " + common);
      System.out.println("union  = " + union.length);
      return 1.0 * common.get() / union.length;
   }

   /*
    * default setters and getters
    */
   public PitchSet getP1() {

      return p1;
   }

   public void setP1(PitchSet p1) {

      this.p1 = p1;
   }

   public PitchSet getP2() {

      return p2;
   }

   public void setP2(PitchSet p2) {

      this.p2 = p2;
   }

}
