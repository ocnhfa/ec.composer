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
import java.util.List;
import tech.metacontext.ec.prototype.composer.connectors.ConnectorType;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 * @param <E>
 */
public abstract class MusicMaterial<E> {

    public static final int DEFAULT_DIVISION = 2;
    public static final int DEFAULT_MIN_DIVISION = 1;
    public static final int DEFAULT_MAX_DIVISION = 4;

    private int division;
    private List<E> materials = new ArrayList<>();

//    public static MusicMaterial create() {
//        MusicMaterial mm = new MusicMaterial
//    }
    /**
     * Constructor with specified division and material content.
     *
     * @param division
     * @param materials
     */
    public MusicMaterial(int division, List<E> materials) {

        this.division = division;
        this.materials = materials;
    }

    /**
     * Constructor with default parameters.
     */
    public MusicMaterial() {

        this.reset().generate();
    }

    /**
     * Reset parameters to the default state.
     *
     * @param <M>
     * @return this instance for cascading.
     */
    abstract public <M extends MusicMaterial> M reset();

    /**
     * Generate materials according to current parameters.
     *
     * @param <M>
     * @return this instance for cascading.
     */
    abstract public <M extends MusicMaterial> M generate();

    /**
     * Randomize parameters.
     *
     * @param <M>
     * @return this instance for cascading.
     */
    abstract public <M extends MusicMaterial> M random();

    /**
     * Material transformation according to ConnectorType.
     *
     * @param <M>
     * @param type
     * @return
     */
    abstract public <M extends MusicMaterial> M transform(ConnectorType type);

    public int size() {
        return this.materials.size();
    }

    /*
   * Default setters and getters.
     */
    public int getDivision() {
        return division;
    }

    public void setDivision(int division) {
        this.division = division;
    }

    public List<E> getMaterials() {
        return materials;
    }

    public void setMaterials(List<E> materials) {
        this.materials = materials;
    }

}
