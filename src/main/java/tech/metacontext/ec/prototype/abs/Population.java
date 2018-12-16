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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author Jonathan Chang
 * @param <E>
 */
public abstract class Population<E extends Individual> {

    private List<E> population;
    private final List<List<E>> archive;
    private int genCount;

    public Population() {

        this.population = new ArrayList<>();
        this.archive = new ArrayList<>();
        this.genCount = 0;
    }

    /**
     * Make population evolve.
     *
     */
    abstract public void evolve();

    abstract public void render();

    public int getSize() {

        return population.size();
    }

    public int genCountIncrement() {

        return ++this.genCount;
    }

    public void archive(List<E> p) {

        this.archive.add(p.stream()
                .map(this::copyInstance)
                .collect(Collectors.toList()));
    }

    public E copyInstance(E e) {

        try {
            return (E) e.getClass().getDeclaredConstructor(e.getClass()).newInstance(e);
        } catch (Exception ex) {
            Logger.getLogger(Population.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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

    public int getGenCount() {
        return genCount;
    }

    public void setGenCount(int genCount) {
        this.genCount = genCount;
    }

    public List<List<E>> getArchive() {
        return this.archive;
    }
}
