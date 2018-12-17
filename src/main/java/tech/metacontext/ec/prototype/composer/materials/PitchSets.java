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

import tech.metacontext.ec.prototype.composer.materials.enums.PitchSet;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import tech.metacontext.ec.prototype.composer.materials.enums.TransformType;
import tech.metacontext.ec.prototype.composer.materials.enums.PitchSetFactory;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class PitchSets extends MusicMaterial<PitchSet> {

    public static final int DEFAULT_MIN_PITCH_NUMBER = 1;
    public static final int DEFAULT_MAX_PITCH_NUMBER = 5;
    public static final int DEFAULT_PITCH_NUMBER = 3;
    public static final int SHARP_ALLOWED = 1, SHARP_NOT_ALLOWED = 0;
    public static final int DEFAULT_ENHARMONIC_ALLOWED = SHARP_NOT_ALLOWED;
    public static final int DEFAULT_SHARP_ALLOWED = SHARP_ALLOWED;

    private boolean randomDivision = false;
    private int commonTone = 0;
    public PitchSetFactory factory;

    public static void main(String[] args) {

        PitchSets pss = new PitchSets();
        pss.setCommonTone(2);
        for (int i = 0; i < 10; i++) {
            System.out.println(pss.random());
            pss.factory.randomize();
        }
    }

    public PitchSets() {
    }

    public PitchSets(PitchSets origin) {

        super(origin.getDivision(), origin.getMaterials());
        this.randomDivision = origin.randomDivision;
        this.commonTone = origin.commonTone;
    }

    @Override
    public PitchSets reset() {

        this.setDivision(DEFAULT_DIVISION);
        this.factory = new PitchSetFactory();
        return this;
    }

    @Override
    public PitchSets generate() {

        if (factory.getMinPitchNumber() < this.commonTone) {
            factory.setMinPitchNumber(this.commonTone);
        }
        this.setMaterials(Stream.generate(factory::generate)
                .peek(ps -> factory.setPresetPitches(ps.selectPitch(this.commonTone)))
                .limit(this.getDivision())
                .collect(Collectors.toList())
        );
        return this;
    }

    @Override
    public PitchSets random() {

        this.setDivision(new Random().nextInt(
                DEFAULT_MAX_DIVISION - DEFAULT_MIN_DIVISION + 1) + DEFAULT_MIN_DIVISION);
        return this.generate();
    }

    @Override
    public PitchSets transform(TransformType type) {

        switch (type) {
            case Repetition:
                return new PitchSets(this);
            case Retrograde:
                return new PitchSets(this).retrograde();
            case MoveForward:
                return new PitchSets(this).moveForward();
            case MoveBackward:
                return new PitchSets(this).moveBackward();
        }
        return null;
    }

    private PitchSets retrograde() {

        this.setMaterials(IntStream.range(0, this.size())
                .mapToObj(i -> this.getMaterials().get(this.size() - i))
                .collect(Collectors.toList()));
        return this;
    }

    private PitchSets moveForward() {

        //@todo: pitchset moveforward
//        IntStream.range(0, this.size())
//                .forEach(i -> {
//                    int o = Math.max(this.getMaterials().get(i).ordinal() + 1,
//                            Range.values().length);
//                    this.getMaterials().set(i, Range.values()[o]);
//                });
        return this;
    }

    private PitchSets moveBackward() {

        //@todo: pitchset movebackward
//        IntStream.range(0, this.size())
//                .forEach(i -> {
//                    int o = Math.min(this.getMaterials().get(i).ordinal() - 1, 0);
//                    this.getMaterials().set(i, Range.values()[o]);
//                });
        return this;
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
    public boolean isRandomPitchSet() {
        return randomDivision;
    }

    public void setRandomPitchSet(boolean randomPitchSet) {
        this.randomDivision = randomPitchSet;
    }

    public int getCommonTone() {
        return commonTone;
    }

    public void setCommonTone(int commonTone) {
        this.commonTone = commonTone;
    }

}
