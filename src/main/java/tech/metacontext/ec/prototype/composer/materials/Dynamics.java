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
import tech.metacontext.ec.prototype.composer.connectors.ConnectorType;
import tech.metacontext.ec.prototype.composer.materials.enums.Intensity;

/**
 *
 * @author Jonathan
 */
public class Dynamics extends MusicMaterial<Intensity> {

    public static final Intensity DEFAULT_LOWEST_INTENSITY = Intensity.pp;
    public static final Intensity DEFAULT_HIGHEST_INTENSITY = Intensity.ff;

    Intensity lowestIntensity;
    Intensity highestIntensity;

    @Override
    public Dynamics reset() {

        this.setDivision(DEFAULT_DIVISION);
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
                .nextInt(DEFAULT_MAX_DIVISION - DEFAULT_MIN_DIVISION + 1)
                + DEFAULT_MIN_DIVISION);
        return generate();
    }

    @Override
    public Dynamics transform(ConnectorType type) {
        
        //@todo Dynamics transform()
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
