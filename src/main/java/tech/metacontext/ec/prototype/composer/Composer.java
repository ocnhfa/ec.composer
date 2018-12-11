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
package tech.metacontext.ec.prototype.composer;

import tech.metacontext.ec.prototype.composer.styles.Style;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import tech.metacontext.ec.prototype.abs.Population;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class Composer extends Population<Composition> {

    private List<Style> styles;

    /**
     * Constructor.
     *
     * @param size number of Compositions.
     */
    public Composer(int size) {

        this.styles = new ArrayList<>();
        this.setPopulation(
                Stream.generate(Composition::new)
                        .limit(size)
                        .collect(Collectors.toList())
        );
    }

    public void addStyle(Style style) {

        this.styles.add(style);
    }

    @Override
    public List<Composition> evolve() {
        List<Composition> population
                = this.getPopulation();
        this.setPopulation(Stream.generate(Composition::new)
                .limit(this.getSize())
                .collect(Collectors.toList()));
        return population;
    }

    @Override
    public void render() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /*
    * Default getters and setters.
     */
    public List<? extends Style> getStyles() {
        return this.styles;
    }

    public void setStyles(List<Style> styles) {
        this.styles = styles;
    }

}
