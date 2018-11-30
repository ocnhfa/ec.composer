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

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import tech.metacontext.ec.prototype.composer2.materials.Pitch;
import tech.metacontext.ec.prototype.composer2.materials.PitchSet;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class SmoothPitchSet implements Rule {

   PitchSet p1, p2;

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

   public static void main(String[] args) {
      PitchSet p1 = new PitchSet(), p2 = new PitchSet();
      for (int i = 0; i < 5; i++) {
         p1.randomInit();
         p2.randomInit();
         SmoothPitchSet mps = new SmoothPitchSet(p1, p2);
         System.out.printf("%s(%d)\n%s(%d)\n%.2f%%\n",
                 p1.getPitches(), p1.getPitches().size(),
                 p2.getPitches(), p2.getPitches().size(),
                 mps.rating() * 100);
      }
   }
}
