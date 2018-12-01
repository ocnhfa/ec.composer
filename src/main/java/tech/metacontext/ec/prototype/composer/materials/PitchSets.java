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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import tech.metacontext.ec.prototype.composer.materials.enums.Pitch;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class PitchSets extends MusicMaterial<PitchSet> {

  public static final int DEFAULT_DIVISION = 4;
  public static final int DEFAULT_MIN_DIVISION = 1;
  public static final int DEFAULT_MAX_DIVISION = 6;

  private int min, max;
  private boolean randomPitchSet = false;
  private int commonTone = -1;

  @Override
  public PitchSets reset() {

    this.setDivision(DEFAULT_DIVISION);
    return this;
  }

  @Override
  public PitchSets random() {

    this.setDivision(new Random().nextInt(
            DEFAULT_MAX_DIVISION - DEFAULT_MIN_DIVISION + 1) + DEFAULT_MIN_DIVISION);
    return this.generate();
  }

  private List<PitchSet> mats;

  @Override
  public PitchSets generate() {

    Set<Pitch> preset = new HashSet<>();
    mats = Stream.generate(PitchSet::new)
            .limit(this.getDivision())
            .peek(ps -> ps.setPreset(new ArrayList<>(preset)))
            .map(PitchSet::random)
            .peek(ps -> {
              preset.clear();
              while (preset.size() < Math.min(ps.size(), this.commonTone)) {
                preset.add(ps.getMaterials().get(new Random().nextInt(ps.size())));
              }
//              System.out.println(preset);
            })
            .collect(Collectors.toList());
    this.setMaterials(mats);
    return this;
  }

  /**
   *
   * @param args
   */
  public static void main(String[] args) {

    AtomicInteger i = new AtomicInteger(0);
    Stream.generate(PitchSets::new)
            .limit(10)
            .peek(ps -> {
              ps.setCommonTone(1);
              ps.setRandomPitchSet(true);
            })
            .map(PitchSets::random)
            .peek(ps -> System.out.print(i.incrementAndGet() + ". "))
            .forEach(System.out::println);
  }

  @Override
  public String toString() {

    AtomicInteger i = new AtomicInteger(0);
    return String.format("PitchSets[ Division=%2d ]\n%s\n---",
            this.getDivision(),
            this.getMaterials().stream()
                    .map(p -> "  " + i.incrementAndGet() + ". " + p.toString())
                    .collect(Collectors.joining("\n")));
  }

  /*
    * default setters and getters
   */
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

  public List<PitchSet> getMats() {
    return mats;
  }

  public void setMats(List<PitchSet> mats) {
    this.mats = mats;
  }

  public boolean isRandomPitchSet() {
    return randomPitchSet;
  }

  public void setRandomPitchSet(boolean randomPitchSet) {
    this.randomPitchSet = randomPitchSet;
  }

  public int getCommonTone() {
    return commonTone;
  }

  public void setCommonTone(int commonTone) {
    this.commonTone = commonTone;
  }

}
