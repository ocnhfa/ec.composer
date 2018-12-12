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
import tech.metacontext.ec.prototype.composer.materials.enums.Range;

/**
 *
 * @author Jonathan
 */
public class NoteRange extends MusicMaterial<Range> {

    public static final int DEFAULT_DIVISION = 4;
    public static final int DEFAULT_MIN_DIVISION = 1;
    public static final int DEFAULT_MAX_DIVISION = 6;
    public static final Range DEFAULT_LOWEST_RANGE = Range.C0;
    public static final Range DEFAULT_HIGHEST_RANGE = Range.C8;

    private Range lowest;
    private Range highest;

    @Override
    public NoteRange reset() {

        this.setDivision(DEFAULT_DIVISION);
        this.lowest = DEFAULT_LOWEST_RANGE;
        this.highest = DEFAULT_HIGHEST_RANGE;
        return this;
    }

    @Override
    public NoteRange random() {

        this.setDivision(new Random()
                .nextInt(DEFAULT_MAX_DIVISION - DEFAULT_MIN_DIVISION + 1)
                + DEFAULT_MIN_DIVISION);
        return generate();
    }

    @Override
    public NoteRange generate() {

        List<Range> rangeList = Arrays.asList(Range.values());
        int lowest_value = rangeList.indexOf(this.lowest);
        int highest_value = rangeList.indexOf(this.highest);
        this.setMaterials(
                new Random().ints(this.getDivision(), lowest_value, highest_value + 1)
                        .mapToObj(rangeList::get)
                        .collect(Collectors.toList())
        );
        return this;
    }

    /*
     * Default setters and getters.
     */
    public Range getLowest() {
        return lowest;
    }

    public void setLowest(Range lowest) {
        this.lowest = lowest;
    }

    public Range getHighest() {
        return highest;
    }

    public void setHighest(Range highest) {
        this.highest = highest;
    }

}
