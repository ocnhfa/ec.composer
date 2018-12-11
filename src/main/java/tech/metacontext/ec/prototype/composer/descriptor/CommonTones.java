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
package tech.metacontext.ec.prototype.composer.descriptor;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import tech.metacontext.ec.prototype.composer.materials.PitchSet;
import tech.metacontext.ec.prototype.composer.materials.enums.Pitch;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class CommonTones extends IdeaDescriptor<PitchSet> {

  private Set<Pitch> common_tones;
  private boolean enharmonicEquivalent = true;

  public CommonTones(PitchSet... sets) {

    this.setMaterials(Arrays.asList(sets));
  }

  public CommonTones() {

    this(new PitchSet().random(), new PitchSet().random());
  }

  @Override
  public CommonTones describe() {

    this.common_tones = Stream.of(Pitch.values())
            .filter(pitch
                    -> this.getMaterials().stream().allMatch(
                    pitch_set -> pitch_set.getMaterials().stream().anyMatch(
                            pitch2 -> (this.enharmonicEquivalent
                                    ? (pitch2.compareToPitch(pitch) == 0)
                                    : pitch2.compareTo(pitch) == 0)))
            ).collect(Collectors.toSet());
    return this;
  }

  public static void main(String[] args) {

    Stream.generate(CommonTones::new)
            .limit(50)
            .map(CommonTones::describe)
            .forEach(System.out::println);
  }

  @Override
  public String toString() {

    return String.format("CommonTones{common_tones=%s, enharmonicEquivalent=%b}\n%s\n---",
            common_tones,
            enharmonicEquivalent,
            this.getMaterials().stream()
                    .map(Object::toString)
                    .collect(Collectors.joining("\n")));
  }

  public int getComon_tone() {

    return this.getCommon_tones().size();
  }

  /*
   * Default setters and getters.
   */
  public Set<Pitch> getCommon_tones() {
    return common_tones;
  }

  public void setCommon_tones(Set<Pitch> common_tones) {
    this.common_tones = common_tones;
  }

  public boolean isAllowedEnharmonic() {
    return enharmonicEquivalent;
  }

  public void setAllowedEnharmonic(boolean allowedEnharmonic) {
    this.enharmonicEquivalent = allowedEnharmonic;
  }

}
