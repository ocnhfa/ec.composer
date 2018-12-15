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

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 * @param <E>
 */
public abstract class MusicMaterial<E> {


    private int division;
    private List<E> materials = new ArrayList<>();

    public MusicMaterial(int division, List<E> materials) {

        this.division = division;
        this.materials = materials;
    }

    public MusicMaterial() {

        this.reset().generate();
    }

    abstract public <M extends MusicMaterial> M reset();

    abstract public <M extends MusicMaterial> M random();

    abstract public <M extends MusicMaterial> M generate();

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
