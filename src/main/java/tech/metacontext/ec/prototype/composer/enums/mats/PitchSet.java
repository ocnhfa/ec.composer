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
package tech.metacontext.ec.prototype.composer.enums.mats;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import tech.metacontext.ec.prototype.composer.materials.PitchSets;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class PitchSet {

    private List<Pitch> pitch_set;

    public PitchSet() {

        this.pitch_set = new ArrayList<>();
    }

    public PitchSet(List<Pitch> pitches) {

        this();
        this.pitch_set.addAll(pitches);
    }

    public PitchSet(PitchSet origin) {

        this(origin.getPitch_set());
    }

    public static double getIntensityIndex(PitchSet ps) {

        return 1.0 * ps.getSize() / PitchSets.DEFAULT_MAX_PITCH_NUMBER;
    }

    @Override
    public String toString() {
        return String.format("PitchSet[ %s ]",
                this.pitch_set.stream()
                        .sorted()
                        .map(Object::toString)
                        .collect(Collectors.joining(", ")));
    }

    /*
     * Default setters and getters.
     */
    public int getSize() {
        return this.pitch_set.size();
    }

    public List<Pitch> getPitch_set() {
        return pitch_set;
    }

    public void setPitch_set(List<Pitch> pitch_set) {
        this.pitch_set = pitch_set;
    }

    public Set<Pitch> selectPitch(int commonTone) {
        Set<Pitch> selected = new HashSet<>();
        while (selected.size() < commonTone) {
            selected.add(this.pitch_set.get(new Random().nextInt(this.getSize())));
        }
        return selected;
    }

    public PitchSet moveForward() {

        return new PitchSet(this.getPitch_set().stream()
                .map(Pitch::forward)
                .collect(Collectors.toList()));
    }

    public PitchSet moveBackward() {

        return new PitchSet(this.getPitch_set().stream()
                .map(Pitch::backward)
                .collect(Collectors.toList()));
    }

}