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

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static tech.metacontext.ec.prototype.composer.Parameters.*;
import tech.metacontext.ec.prototype.composer.enums.TransformType;
import tech.metacontext.ec.prototype.composer.enums.mats.Intensity;

/**
 *
 * @author Jonathan
 */
public class Dynamics extends MusicMaterial<Intensity> {

    public static final Intensity DEFAULT_LOWEST_INTENSITY = Intensity.pp;
    public static final Intensity DEFAULT_HIGHEST_INTENSITY = Intensity.ff;

    Intensity lowestIntensity;
    Intensity highestIntensity;

    public Dynamics() {
    }

    public Dynamics(Dynamics origin) {

        super(origin.getDivision(), origin.getMaterials());
        this.lowestIntensity = origin.lowestIntensity;
        this.highestIntensity = origin.highestIntensity;
    }

    @Override
    public Dynamics duplicate() {

        return new Dynamics(this);
    }

    @Override
    public Dynamics reset() {

        this.setDivision(DEFAULT_DIVISION.getInt());
        this.lowestIntensity = DEFAULT_LOWEST_INTENSITY;
        this.highestIntensity = DEFAULT_HIGHEST_INTENSITY;
        return this;
    }

    @Override
    public Dynamics generate() {

        this.setMaterials(new Random().ints(this.getDivision(),
                this.lowestIntensity.ordinal(),
                this.highestIntensity.ordinal() + 1)
                .mapToObj(i -> Intensity.values()[i])
                .collect(Collectors.toList())
        );
        return this;
    }

    @Override
    public Dynamics random() {

        this.setDivision(new Random()
                .nextInt(DEFAULT_MAX_DIVISION.getInt()
                        - DEFAULT_MIN_DIVISION.getInt() + 1)
                + DEFAULT_MIN_DIVISION.getInt());
        return generate();
    }

    @Override
    public Dynamics transform(TransformType type) {

        switch (type) {
            case Repetition:
                return new Dynamics(this);
            case Retrograde:
                return new Dynamics(this).retrograde();
            case MoveForward:
                return new Dynamics(this).moveForward();
            case MoveBackward:
                return new Dynamics(this).moveBackward();
            case Disconnected:
                return new Dynamics();
        }
        return null;
    }

    private Dynamics retrograde() {

        this.setMaterials(IntStream.range(0, this.size())
                .mapToObj(i -> this.getMaterials().get(this.size() - i - 1))
                .collect(Collectors.toList()));
        return this;
    }

    private Dynamics moveForward() {

        IntStream.range(0, this.size())
                .forEach(i -> {
                    int o = Math.min(this.getMaterials().get(i).ordinal() + 1,
                            Intensity.values().length - 1);
                    this.getMaterials().set(i, Intensity.values()[o]);
                });
        return this;
    }

    private Dynamics moveBackward() {

        IntStream.range(0, this.size())
                .forEach(i -> {
                    int o = Math.max(this.getMaterials().get(i).ordinal() - 1, 0);
                    this.getMaterials().set(i, Intensity.values()[o]);
                });
        return this;
    }

}
