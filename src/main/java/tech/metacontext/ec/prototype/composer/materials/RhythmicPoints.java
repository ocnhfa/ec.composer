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

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class RhythmicPoints extends MusicMaterial<Integer> {

    public static final int DEFAULT_DIVISION = 4;
    public static final int DEFAULT_MIN_DIVISION = 1;
    public static final int DEFAULT_MAX_DIVISION = 6;
    public static final int DEFAULT_MIN_NUMBER = 0;
    public static final int DEFAULT_MAX_NUMBER = 8;

    private int min, max;

    @Override
    public RhythmicPoints reset() {

        this.setDivision(DEFAULT_DIVISION);
        this.min = DEFAULT_MIN_NUMBER;
        this.max = DEFAULT_MAX_NUMBER;
        return this;
    }

    @Override
    public RhythmicPoints random() {

        this.setDivision(new Random()
                .nextInt(DEFAULT_MAX_DIVISION - DEFAULT_MIN_DIVISION + 1)
                + DEFAULT_MIN_DIVISION);
        return generate();
    }

    @Override
    public RhythmicPoints generate() {

        this.setMaterials(
                new Random().ints(this.getDivision(), this.min, this.max + 1)
                        .boxed()
                        .collect(Collectors.toList())
        );
        return this;
    }

    public static void main(String[] args) {

        RhythmicPoints nn = new RhythmicPoints();
        Stream.generate(() -> nn.random())
                .limit(50)
                .map(RhythmicPoints::getMaterials)
                .forEach(System.out::println);
    }

    @Override
    public String toString() {
        return "RhythmicPoints{"
                + "div=" + this.getDivision()
                + ", min=" + min
                + ", max=" + max + '}'
                + this.getMaterials();
    }

    /*
     * Default setters and getters
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

}
