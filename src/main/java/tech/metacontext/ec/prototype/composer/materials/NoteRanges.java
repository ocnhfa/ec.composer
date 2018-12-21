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
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import tech.metacontext.ec.prototype.composer.materials.enums.TransformType;
import tech.metacontext.ec.prototype.composer.materials.enums.Range;

/**
 *
 * @author Jonathan
 */
public class NoteRanges extends MusicMaterial<Range> {

    public static final Range DEFAULT_LOWEST_RANGE = Range.C0;
    public static final Range DEFAULT_HIGHEST_RANGE = Range.C8;

    private Range lowestRange;
    private Range highestRange;

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

        this.setDivision(DEFAULT_DIVISION);
        this.lowestRange = DEFAULT_LOWEST_RANGE;
        this.highestRange = DEFAULT_HIGHEST_RANGE;
        return this;
    }

    @Override
    public NoteRanges generate() {

        List<Range> rangeList = Arrays.asList(Range.values());
        int lowest_value = rangeList.indexOf(this.lowestRange);
        int highest_value = rangeList.indexOf(this.highestRange);
        this.setMaterials(
                new Random().ints(this.getDivision(), lowest_value, highest_value + 1)
                        .mapToObj(rangeList::get)
                        .collect(Collectors.toList())
        );
        return this;
    }

    @Override
    public NoteRanges random() {

        this.setDivision(new Random()
                .nextInt(DEFAULT_MAX_DIVISION - DEFAULT_MIN_DIVISION + 1)
                + DEFAULT_MIN_DIVISION);
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

        IntStream.range(0, this.size())
                .forEach(i -> {
                    int o = Math.min(this.getMaterials().get(i).ordinal() + 1,
                            Range.values().length - 1);
                    this.getMaterials().set(i, Range.values()[o]);
                });
        return this;
    }

    private NoteRanges moveBackward() {

        IntStream.range(0, this.size())
                .forEach(i -> {
                    int o = Math.max(this.getMaterials().get(i).ordinal() - 1, 0);
                    this.getMaterials().set(i, Range.values()[o]);
                });
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
    public Range getLowest() {
        return lowestRange;
    }

    public void setLowest(Range lowest) {
        this.lowestRange = lowest;
    }

    public Range getHighest() {
        return highestRange;
    }

    public void setHighest(Range highest) {
        this.highestRange = highest;
    }

}
