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
package tech.metacontext.ec.prototype.composer2.materials;

import java.util.Arrays;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class PitchSet implements MusicMaterial {

   private Set<Pitch> pitches;

   public PitchSet() {

      this.pitches = new TreeSet<>(Pitch.comparator);
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
   public void randomInit() {

      this.pitches = Stream.of(Pitch.values())
              .filter(t -> new Random().nextBoolean())
              .collect(Collectors.toSet());
   }

   public static void main(String[] args) {

      PitchSet ps = new PitchSet();
      ps.randomInit();
      System.out.println(ps.pitches);
   }

}
