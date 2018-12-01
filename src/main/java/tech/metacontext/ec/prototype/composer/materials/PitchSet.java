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
package tech.metacontext.ec.prototype.composer.materials;

import java.util.AbstractMap.SimpleEntry;
import tech.metacontext.ec.prototype.composer.materials.enums.Pitch;
import java.util.Arrays;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class PitchSet implements MusicMaterial {

   public static final boolean DEFAULT_ALLOWED_ENHARMONIC = false;
   public static final int DEFAULT_MIN = 3;
   public static final int DEFAULT_MAX = 5;

   private Set<Pitch> pitches;
   private boolean allowedEnharmonic;
   private int min, max;

   public PitchSet() {

      this.pitches = new TreeSet<>(Pitch::compareToPitch);
      this.allowedEnharmonic = DEFAULT_ALLOWED_ENHARMONIC;
      this.min = DEFAULT_MIN;
      this.max = DEFAULT_MAX;
   }

   public PitchSet(Pitch... pitches) {

      this();
      this.pitches.addAll(Arrays.asList(pitches));
   }

   public Set<Pitch> getPitches() {
      return pitches;
   }

   public void setPitches(Set<Pitch> pitches) {
      this.pitches = pitches;
   }

   @Override
   public PitchSet random() {

      AtomicInteger i = new AtomicInteger(0);
      this.pitches = Stream.generate(Math::random)
              .limit((this.allowedEnharmonic) ? 17 : 12)
              .map(r -> new SimpleEntry<>(r, Pitch.values()[i.getAndIncrement()]))
              .sorted((o1, o2) -> o1.getKey().compareTo(o2.getKey()))
              .limit(new Random().nextInt(this.max - this.min + 1) + this.min)
              .map(SimpleEntry::getValue)
              .collect(Collectors.toSet());
      return this;
   }

   /**
    *
    * @param args
    */
   public static void main(String[] args) {
      PitchSet ps = new PitchSet();
      ps.setAllowedEnharmonic(false);
      for (int i = 0; i < 10; i++) {
         System.out.println(
                 (i + 1) + ". "
                 + ps.random().pitches.stream()
                         .sorted(Pitch::compareToPitch)
                         .map(Object::toString)
                         .collect(Collectors.joining(" - "))
         );
         System.out.println("---");
      }
   }

   /*
    * default setters and getters
    */
   public boolean isAllowedEnharmonic() {
      return allowedEnharmonic;
   }

   public void setAllowedEnharmonic(boolean allowedEnharmonic) {
      this.allowedEnharmonic = allowedEnharmonic;
   }

   public int getMin() {
      return min;
   }

   public void setMin(int min) {
      this.min = min;
   }

   public int getMax() {
      return max;
   }

   public void setMax(int max) {
      this.max = max;
   }

}
