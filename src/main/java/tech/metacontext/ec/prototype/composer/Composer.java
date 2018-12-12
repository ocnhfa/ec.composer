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
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import tech.metacontext.ec.prototype.abs.Population;
import tech.metacontext.ec.prototype.composer.enums.ComposerAim;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class Composer extends Population<Composition> {

    private List<Style> styles;
    private ComposerAim aim;
    private final List<Composition> conservetory;
    private int genCount;

    /**
     * Constructor.
     *
     * @param size number of Compositions.
     * @param aim
     */
    public Composer(int size, ComposerAim aim) {

        this.styles = new ArrayList<>();
        this.aim = aim;
        this.conservetory = new ArrayList<>();
        this.genCount = 0;
        this.setPopulation(
                Stream.generate(this::initComposition)
                        .limit(size)
                        .collect(Collectors.toList())
        );
    }

    public Composition initComposition() {

        SketchNode seed = generateSeed();
        Connector conn = new Connector(seed);
        return new Composition(seed, conn);
    }

    public SketchNode generateSeed() {

        //@todo: apply rule
        return new SketchNode();
    }

    public void addStyle(Style style) {

        this.styles.add(style);
    }

    @Override
    public List<Composition> evolve() {

        List<Composition> parents = this.getPopulation().stream()
                .peek(this::persist)
                .map(Composition::new)
                .collect(Collectors.toList());
        System.out.println(
                "// 1. compose()"
        );
        this.getPopulation().stream()
                .map(Composition::compose)
                .forEach(this::conserve);  // conserved Composition remains.
        System.out.println(
                "// 2. parents -> children via mutation, crossover..."
        );
        List<Composition> children = new ArrayList<>();
        do {
            children.add(parents.get(new Random().nextInt(parents.size())));
            //@todo: genetic operations here -> child, add and conserve
        } while (children.size() < this.getSize());
        System.out.println(
                "// 3. 符合目標、風格者保留至conservatory"
        );
        this.setPopulation(children);
        this.genCount++;
        return parents;
    }

    public boolean conserve(Composition composition) {

        if (this.styles.stream()
                .allMatch(s -> s.qualify(composition))) {
            this.conservetory.add(composition);
            return true;
        }
        return false;
    }

    /**
     * Persist population with genCount.
     *
     * @param composition
     */
    public void persist(Composition composition) {
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

    public ComposerAim getAim() {
        return aim;
    }

    public void setAim(ComposerAim aim) {
        this.aim = aim;
    }

    public List<Composition> getConservetory() {
        return conservetory;
    }

    public int getGenCount() {
        return genCount;
    }

    public void setGenCount(int genCount) {
        this.genCount = genCount;
    }

}
