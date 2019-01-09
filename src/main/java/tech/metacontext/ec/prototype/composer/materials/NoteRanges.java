/*
 * Copyright 2018 Jonathan.
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

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static tech.metacontext.ec.prototype.composer.Parameters.*;
import tech.metacontext.ec.prototype.composer.enums.TransformType;
import tech.metacontext.ec.prototype.composer.enums.mats.SciRange;
import tech.metacontext.ec.prototype.composer.enums.mats.SciRangeSet;

/**
 *
 * @author Jonathan
 */
public class NoteRanges extends MusicMaterial<SciRangeSet> {

    private SciRange lowestRange;
    private SciRange highestRange;

    public NoteRanges() {
    }

    public NoteRanges(NoteRanges origin) {

        super(origin.getDivision(), origin.getMaterials());
        this.lowestRange = origin.lowestRange;
        this.highestRange = origin.highestRange;
    }

    @Override
    public NoteRanges duplicate() {

        return new NoteRanges(this);
    }

    @Override
    public NoteRanges reset() {

        this.setDivision(DEFAULT_DIVISION.getInt());
        this.lowestRange = SciRange.valueOf(DEFAULT_LOWEST_RANGE.getInt());
        this.highestRange = SciRange.valueOf(DEFAULT_HIGHEST_RANGE.getInt());
        return this;
    }

    @Override
    public NoteRanges generate() {

        int highest = this.highestRange.ordinal(), lowerest = this.lowestRange.ordinal();
        this.setMaterials(
                new Random().ints(this.getDivision(), lowerest, highest + 1)
                        .mapToObj(lowerBond -> new SciRangeSet(lowerBond,
                        new Random().nextInt(highest - lowerBond + 1) + lowerBond))
                        .collect(Collectors.toList())
        );
        return this;
    }

    @Override
    public NoteRanges random() {

        this.setDivision(new Random()
                .nextInt(DEFAULT_MAX_DIVISION.getInt()
                        - DEFAULT_MIN_DIVISION.getInt() + 1)
                + DEFAULT_MIN_DIVISION.getInt());
        return generate();
    }

    @Override
    public NoteRanges transform(TransformType type) {

        switch (type) {
            case Repetition:
                return new NoteRanges(this);
            case Retrograde:
                return new NoteRanges(this).retrograde();
            case MoveForward:
                return new NoteRanges(this).moveForward();
            case MoveBackward:
                return new NoteRanges(this).moveBackward();
            case Disconnected:
                return new NoteRanges();
        }
        return null;
    }

    private NoteRanges retrograde() {

        this.setMaterials(IntStream.range(0, this.size())
                .mapToObj(i -> this.getMaterials().get(this.size() - i - 1))
                .collect(Collectors.toList()));
        return this;
    }

    private NoteRanges moveForward() {

        this.getMaterials().stream()
                .forEach(srs -> srs.setSciRange_set(srs.moveForward(this.highestRange)));
        return this;
    }

    private NoteRanges moveBackward() {

        this.getMaterials().stream()
                .forEach(srs -> srs.setSciRange_set(srs.moveBackward(this.lowestRange)));
        return this;
    }

    @Override
    public String toString() {
        return "{"
                + "div=" + this.getDivision()
                + ", lowest=" + lowestRange
                + ", highest=" + highestRange
                + '}'
                + this.getMaterials();
    }

    /*
     * Default setters and getters.
     */
    public SciRange getLowest() {
        return lowestRange;
    }

    public void setLowest(SciRange lowest) {
        this.lowestRange = lowest;
    }

    public SciRange getHighest() {
        return highestRange;
    }

    public void setHighest(SciRange highest) {
        this.highestRange = highest;
    }

}
