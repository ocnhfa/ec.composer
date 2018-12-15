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

    private int minDivision, maxDivision;
    private boolean randomPitchSet = false;
    private int commonTone = -1;

    @Override
    public PitchSets reset() {

        this.setDivision(DEFAULT_DIVISION);
        return this;
    }

    @Override
    public PitchSets generate() {

        Set<Pitch> preset = new HashSet<>();
        this.setMaterials(Stream.generate(PitchSet::new)
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
                .collect(Collectors.toList())
        );
        return this;
    }

    @Override
    public PitchSets random() {

        if (this.randomPitchSet || this.maxDivision == 0) {
            this.setDivision(new Random().nextInt(
                    DEFAULT_MAX_DIVISION - DEFAULT_MIN_DIVISION + 1) + DEFAULT_MIN_DIVISION);
        } else {
            this.setDivision(new Random().nextInt(maxDivision - minDivision + 1) + minDivision);
        }
        return this.generate();
    }

    @Override
    public String toString() {

        AtomicInteger i = new AtomicInteger(0);
        return String.format("PitchSets[ Division=%2d ]\n%s",
                this.getDivision(),
                this.getMaterials().stream()
                        .map(p -> "  " + i.incrementAndGet() + ". " + p.toString())
                        .collect(Collectors.joining("\n")));
    }

    /*
     * default setters and getters
     */
    public int getMinDivision() {
        return minDivision;
    }

    public void setMinDivision(int minDivision) {
        this.minDivision = minDivision;
    }

    public int getMaxDivision() {
        return maxDivision;
    }

    public void setMaxDivision(int maxDivision) {
        this.maxDivision = maxDivision;
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
