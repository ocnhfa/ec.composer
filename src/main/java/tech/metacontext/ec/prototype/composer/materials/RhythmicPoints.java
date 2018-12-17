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
import java.util.stream.IntStream;
import java.util.stream.Stream;
import tech.metacontext.ec.prototype.composer.materials.enums.TransformType;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class RhythmicPoints extends MusicMaterial<Integer> {

    public static final int DEFAULT_MIN_POINTS = 0;
    public static final int DEFAULT_MAX_POINTS = 8;

    private int minPoints, maxPoints;

    public RhythmicPoints() {
    }

    public RhythmicPoints(RhythmicPoints origin) {

        super(origin.getDivision(), origin.getMaterials());
        this.minPoints = origin.minPoints;
        this.maxPoints = origin.maxPoints;
    }

    @Override
    public RhythmicPoints reset() {

        this.setDivision(DEFAULT_DIVISION);
        this.minPoints = DEFAULT_MIN_POINTS;
        this.maxPoints = DEFAULT_MAX_POINTS;
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
    public RhythmicPoints generate() {

        this.setMaterials(new Random().ints(this.getDivision(), this.minPoints, this.maxPoints + 1)
                .boxed()
                .collect(Collectors.toList())
        );
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
    public RhythmicPoints transform(TransformType type) {
        switch (type) {
            case Repetition:
                return new RhythmicPoints(this);
            case Retrograde:
                return new RhythmicPoints(this).retrograde();
            case MoveForward:
                return new RhythmicPoints(this).moveForward();
            case MoveBackward:
                return new RhythmicPoints(this).moveBackward();
        }
        return null;
    }

    private RhythmicPoints retrograde() {

        this.setMaterials(IntStream.range(0, this.size())
                .mapToObj(i -> this.getMaterials().get(this.size() - i - 1))
                .collect(Collectors.toList()));
        return this;
    }

    private RhythmicPoints moveForward() {

        IntStream.range(0, this.size())
                .forEach(i -> {
                    int o = Math.max(this.getMaterials().get(i) + 1,
                            this.maxPoints);
                    this.getMaterials().set(i, o);
                });
        return this;
    }

    private RhythmicPoints moveBackward() {

        IntStream.range(0, this.size())
                .forEach(i -> {
                    int o = Math.min(this.getMaterials().get(i) - 1, 0);
                    this.getMaterials().set(i, o);
                });
        return this;
    }

    @Override
    public String toString() {
        return "RhythmicPoints{"
                + "div=" + this.getDivision()
                + ", min=" + minPoints
                + ", max=" + maxPoints + '}'
                + this.getMaterials();
    }

    /*
     * Default setters and getters
     */
    public int getMin() {
        return minPoints;
    }

    public void setMin(int min) {
        this.minPoints = min;
    }

    public int getMax() {
        return maxPoints;
    }

    public void setMax(int max) {
        this.maxPoints = max;
    }

}
