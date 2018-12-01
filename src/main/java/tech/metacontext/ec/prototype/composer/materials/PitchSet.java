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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import tech.metacontext.ec.prototype.composer.materials.enums.Pitch;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class PitchSet extends MusicMaterial<Pitch> {

  public static final int DEFAULT_MIN = 1;
  public static final int DEFAULT_MAX = 5;
  public static final int DEFAULT_NUMBER = 3;
  public static final int ALLOW = 1, NOT_ALLOW = 0;
  public static final int DEFAULT_ENHARMONIC_ALLOWED = NOT_ALLOW;
  public static final int DEFAULT_SHARP_ALLOWED = ALLOW;

  private int min, max;
  private int number;
  private boolean sharpAllowed;
  private boolean enharmonicAllowed;
  private List<Pitch> preset = new ArrayList<>();

  @Override

  public PitchSet reset() {

    this.min = DEFAULT_MIN;
    this.max = DEFAULT_MAX;
    this.number = DEFAULT_NUMBER;
    this.sharpAllowed = (DEFAULT_SHARP_ALLOWED == ALLOW);
    this.enharmonicAllowed = (DEFAULT_ENHARMONIC_ALLOWED == ALLOW);
    this.preset = new ArrayList<>();
    return this;
  }

  @Override
  public PitchSet random() {

    this.sharpAllowed = new Random().nextBoolean();
    this.number = new Random().nextInt(this.max - this.min + 1) + this.min;
    return this.generate();
  }

  @Override
  public PitchSet generate() {

    this.setMaterials(
            Stream.of(Pitch.values())
                    .limit(this.sharpAllowed ? 17 : 12)
                    .map(p -> new SimpleEntry<>(this.preset.contains(p) ? 0.0 : Math.random(), p))
                    .sorted((o1, o2) -> o1.getKey().compareTo(o2.getKey()))
                    .map(e -> (this.enharmonicAllowed) ? e.getValue().ordinal() : e.getValue().ordinalEnharmonic())
                    .distinct()
                    .map(i -> Pitch.values()[i])
                    .limit(this.number)
                    .collect(Collectors.toList())
    );
    return this;
  }

  public static void main(String[] args) {

    List<Pitch> preset = new ArrayList<>();

    Stream.generate(PitchSet::new)
            .limit(10)
            .peek(ps -> {
              preset.clear();
              Pitch p = ps.getMaterials().get(0);
              ps.preset.add(p);
              System.out.println("peek: " + ps + "->" + p);
            })
            .map(PitchSet::random)
            .forEach(System.out::println);
  }

  @Override
  public String toString() {
    return String.format("PitchSet[ %d -> %s ]",
            this.number,
            this.getMaterials().stream()
                    .sorted()
                    .map(Object::toString)
                    .collect(Collectors.joining(", ")));
  }

  /*
   * Default setters and getters.
   */
  public boolean isAllowedEnharmonic() {
    return sharpAllowed;
  }

  public void setAllowedEnharmonic(boolean allowedEnharmonic) {
    this.sharpAllowed = allowedEnharmonic;
  }

  public List<Pitch> getPreset() {
    return preset;
  }

  public void setPreset(List<Pitch> preset) {
    this.preset = preset;
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

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public boolean isSharpAllowed() {
    return sharpAllowed;
  }

  public void setSharpAllowed(boolean sharpAllowed) {
    this.sharpAllowed = sharpAllowed;
  }

  public boolean isEnharmonicAllowed() {
    return enharmonicAllowed;
  }

  public void setEnharmonicAllowed(boolean enharmonicAllowed) {
    this.enharmonicAllowed = enharmonicAllowed;
  }

}
