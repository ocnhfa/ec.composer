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

    public static final int DEFAULT_MIN_PITCH_NUMBER = 1;
    public static final int DEFAULT_MAX_PITCH_NUMBER = 5;
    public static final int DEFAULT_PITCH_NUMBER = 3;
    public static final int SHARP_ALLOWED = 1, SHARP_NOT_ALLOWED = 0;
    public static final int DEFAULT_ENHARMONIC_ALLOWED = SHARP_NOT_ALLOWED;
    public static final int DEFAULT_SHARP_ALLOWED = SHARP_ALLOWED;

    private int minPitchNumber, maxPitchNumber;
    private int pitchNumber;
    /**
     * 是否容許混合升記號音符
     */
    private boolean sharpAllowed;
    /**
     * 同音異名是否視為同音
     */
    private boolean enharmonicAllowed;
    /**
     * 保留音
     */
    private List<Pitch> preset = new ArrayList<>();

    public static void main(String[] args) {

        List<Pitch> preset = new ArrayList<>();

        Stream.generate(PitchSet::new)
                .limit(10)
                .peek(ps -> {
                    System.out.println("new: " + ps);
                    ps.preset.addAll(preset);
                    preset.clear();
                    preset.add(ps.getMaterials().get(0));
                    System.out.println("preset = " + preset);
                })
                .map(PitchSet::random)
                .forEach(System.out::println);
    }

    @Override
    public PitchSet reset() {

        this.minPitchNumber = DEFAULT_MIN_PITCH_NUMBER;
        this.maxPitchNumber = DEFAULT_MAX_PITCH_NUMBER;
        this.pitchNumber = DEFAULT_PITCH_NUMBER;
        this.sharpAllowed = (DEFAULT_SHARP_ALLOWED == SHARP_ALLOWED);
        this.enharmonicAllowed = (DEFAULT_ENHARMONIC_ALLOWED == SHARP_ALLOWED);
        this.preset = new ArrayList<>();
        return this;
    }

    @Override
    public PitchSet generate() {
        //若不允許升記號則將preset中的升記號以降記號取代
        if (!this.sharpAllowed && !preset.isEmpty()) {
            for (int i = 0; i < preset.size(); i++) {
                preset.set(i, Pitch.values()[preset.get(i).ordinalEnharmonic()]);
            }
        }
        this.setMaterials(Stream.of(Pitch.values())
                .limit(this.sharpAllowed ? 17 : 12)
                .map(p -> new SimpleEntry<>(this.preset.contains(p) ? 0.0 : Math.random(), p))
                .sorted((o1, o2) -> o1.getKey().compareTo(o2.getKey()))
                .map(e -> (this.enharmonicAllowed) ? e.getValue().ordinal() : e.getValue().ordinalEnharmonic())
                .distinct()
                .map(i -> Pitch.values()[i])
                .limit(this.pitchNumber)
                .collect(Collectors.toList())
        );
        return this;
    }

    @Override
    public PitchSet random() {

        this.sharpAllowed = new Random().nextBoolean();
        this.pitchNumber = new Random().nextInt(this.maxPitchNumber - this.minPitchNumber + 1) + this.minPitchNumber;
        return this.generate();
    }

    @Override
    public String toString() {
        return String.format("PitchSet[ %d -> %s ]",
                this.pitchNumber,
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
        return minPitchNumber;
    }

    public void setMin(int min) {
        this.minPitchNumber = min;
    }

    public int getMax() {
        return maxPitchNumber;
    }

    public void setMax(int max) {
        this.maxPitchNumber = max;
    }

    public int getNumber() {
        return pitchNumber;
    }

    public void setNumber(int number) {
        this.pitchNumber = number;
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
