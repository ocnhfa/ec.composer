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

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class PitchSet {

  private final Set<Pitch> pitches;

  public PitchSet(Pitch... pitches) {
    this.pitches = new TreeSet<>(Arrays.asList(pitches));
  }

  public void remove(Pitch pitchToRemove) {
    if (this.pitches.remove(pitchToRemove)) {
      System.out.println(pitchToRemove + " removed.");
    }
  }

  public void add(Pitch pitchToAdd) {
    if (this.pitches.add(pitchToAdd)) {
      System.out.println(pitchToAdd + " added.");
    }
  }

  public long common(PitchSet... sets) {
    return this.pitches.stream().filter((p) -> {
      for (PitchSet set : sets) {
        if (!set.pitches.contains(p)) {
          return false;
        }
      }
      return true;
    }).count();
  }

  @Override
  public String toString() {
    return pitches.toString();
  }

  public static void main(String[] args) {
    Pitch[] ps1 = new Pitch[]{Pitch.D, Pitch.A, Pitch.B, Pitch.C, Pitch.Cs};
    Pitch[] ps2 = new Pitch[]{Pitch.D, Pitch.B, Pitch.C, Pitch.Cs};
    PitchSet set1 = new PitchSet(ps1);
    PitchSet set2 = new PitchSet(ps2);
    System.out.println("set1 = " + set1);
    set1.remove(Pitch.B);
    System.out.println("set1 = " + set1);
    set1.add(Pitch.F);
    System.out.println("set1 = " + set1);
    System.out.println("set2 = " + set2);
    System.out.println("common = " + set1.common(set2));
  }
}
