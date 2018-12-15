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
package tech.metacontext.ec.prototype.composer.draft.nodes.materials;

import tech.metacontext.ec.prototype.composer.draft.enums.Pitch;
import java.util.Arrays;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class PitchSet extends MusicMaterial<Pitch> {

  private final Set<Pitch> pitches;

  public PitchSet(Pitch... pitches) {
    this.pitches = new TreeSet<>(Arrays.asList(pitches));
  }

  public PitchSet() {
    this.pitches = new TreeSet<>(Pitch.comparator);
  }

  public Set<Pitch> getPitches() {
    return pitches;
  }

  @Override
  public void randomInit() {
    this.pitches.clear();
    Arrays.asList(Pitch.values()).forEach((Pitch p) -> {
      if (new Random().nextBoolean()) {
        pitches.add(p);
      }
    });
  }

  @Override
  public void remove(Pitch pitchToRemove) {
    if (this.pitches.remove(pitchToRemove)) {
      System.out.println(pitchToRemove + " removed.");
    }
  }

  @Override
  public void add(Pitch pitchToAdd) {
    if (this.pitches.add(pitchToAdd)) {
      System.out.println(pitchToAdd + " added.");
    }
  }

  @Override
  public String toString() {
    return pitches.toString();
  }

  public static void main(String[] args) {
    PitchSet ps = new PitchSet();
    for (int i = 0; i < 10; i++) {
      ps.randomInit();
      System.out.println(ps);
    }
  }
}
