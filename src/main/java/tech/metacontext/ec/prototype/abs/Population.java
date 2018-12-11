/*
 * Copyright 2018 Jonathan Chang.
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
package tech.metacontext.ec.prototype.abs;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jonathan Chang
 * @param <E>
 */
public abstract class Population<E extends Individual> {

    private List<E> population;

    public Population() {

        this.population = new ArrayList<>();
    }

    abstract public List<E> evolve();

    abstract public void render();

    public int getSize() {

        return population.size();
    }

    /*
     * Default setters and getters.
     */
    public List<E> getPopulation() {
        return population;
    }

    public void setPopulation(List<E> population) {
        this.population = population;
    }

}
