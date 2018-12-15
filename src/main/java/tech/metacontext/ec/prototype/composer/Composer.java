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
import tech.metacontext.ec.prototype.composer.styles.GoldenSectionClimax;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class Composer extends Population<Composition> {

    private ComposerAim aim;
    private List<Style> styles;
    private int size;

    private final List<Composition> conservetory;
    // Constants
    private static final double ELONGATION_RATE = 0.1;
    private static final double CONSERVE_RATE = 0.99;

    /**
     * Constructor.
     *
     * @param size
     * @param aim
     * @param styles
     */
    public Composer(int size, ComposerAim aim, Style... styles) {

        this.aim = aim;
        this.styles = new ArrayList<>();
        for (Style style : styles) {
            this.styles.add(style);
        }
        this.size = size;
        this.conservetory = new ArrayList<>();
        this.setPopulation(Stream.generate(this::initComposition)
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

        return Stream.generate(SketchNode::new)
                .filter(node
                        -> this.getStyles().stream()
                        .allMatch(s -> s.qualifySketchNode(node)))
                .findFirst()
                .get();
    }

    public void compose() {

        this.getPopulation().stream()
                .filter(c -> !aim.completed(c) || Math.random() < ELONGATION_RATE)
                .forEach(c -> c.elongation(this.getStyles()));
        this.getPopulation().removeIf(this::conserve);
    }

    @Override
    public void evolve() {

        List<Composition> parents = this.getPopulation().stream()
                .map(Composition::new)
                .collect(Collectors.toList());

        this.archive(parents);

        System.out.println(
                "// 2. parents -> children via mutation, crossover..."
        );
        List<Composition> children = new ArrayList<>();
        do {
            children.add(parents.get(new Random().nextInt(parents.size())));
            //@todo: genetic operations here -> child, add and conserve
        } while (children.size() < this.getPopulationSize());
        System.out.println(
                "// 3. 符合目標、風格者保留至conservatory"
        );
        this.setPopulation(children);
        this.genCountIncrement();
    }

    public boolean conserve(Composition composition) {

        if (this.getAim().completed(composition)
                && this.styles.stream()
                        .allMatch(s -> s.rateComposition(composition) > CONSERVE_RATE)) {
            this.conservetory.add(composition);
            return true;
        }
        return false;
    }

    @Override
    public void render() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addStyle(Style style) {

        this.styles.add(style);
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
