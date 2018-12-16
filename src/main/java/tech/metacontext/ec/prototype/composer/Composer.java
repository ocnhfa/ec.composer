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

import tech.metacontext.ec.prototype.composer.connectors.Connector;
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

    private ComposerAim aim;
    private List<Style> styles;
    private int size;

    private final List<Composition> conservetory;

    /**
     * 當作品達到目標之後，還繼續加長的機率。
     */
    private static final double ELONGATION_CHANCE = 0.1;
    /**
     * 作品收入conservatory所須達到的分數。
     */
    private static final double CONSERVE_SCORE = 0.99;
    private static final double CROSSOVER_CHANCE_IF_COMPLETED = 0.8;
    public static final int SELECT_FROM_ALL = 0, SELECT_ONLY_COMPLETED = 1;

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
        this.setPopulation(
                Stream.generate(() -> generateSeed())
                        .map(seed
                                -> new Composition(seed,
                                new Connector(seed, this::styleChecker)))
                        .limit(size)
                        .collect(Collectors.toList())
        );
    }

    public boolean styleChecker(SketchNode node) {

        return this.getStyles().stream().allMatch(
                style -> style.qualifySketchNode(node));
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

        List<Composition> parents = this.getPopulation().stream()
                .map(Composition::new)
                .collect(Collectors.toList());
        this.archive(parents);
        this.getPopulation().stream()
                .filter(c -> !aim.completed(c) || this.toElongate(c))
                .forEach(c -> c.elongation(this::styleChecker));
        this.getPopulation().removeIf(this::conserve);
    }

    private boolean toElongate(Composition c) {

        return Math.random()
                < Math.pow(ELONGATION_CHANCE, c.getSize() - this.getAim().getSize());
    }

    @Override
    public void evolve() {

        List<Composition> children = new ArrayList<>();
        do {
            //1.選出一條
            //2.若not completed則mutate -> children
            //3.若completed則決定是否走mutate, yes -> mutate -> children
            //4.若走crossover則選出另一條completed(也許是自己)
            //5.crossover -> children
            Composition sel_1 = this.select(SELECT_FROM_ALL);
            if (this.getAim().completed(sel_1)
                    && Math.random() < CROSSOVER_CHANCE_IF_COMPLETED) {
                Composition sel_2 = this.select(SELECT_ONLY_COMPLETED);
                children.add(this.crossover(sel_1, sel_2));
            } else {
                children.add(this.mutate(sel_1));
            }
            children.removeIf(this::conserve);
        } while (children.size() < this.getSize());

        this.setPopulation(children);
        this.genCountIncrement();
    }

    public Composition select(int state) {

        return new Random().ints(0, this.getPopulationSize())
                .mapToObj(this.getPopulation()::get)
                .filter(c -> state == SELECT_FROM_ALL || this.getAim().completed(c))
                .findFirst().orElse(null);
    }

    public Composition mutate(Composition origin) {
        //@todo Composer::mutate
        return new Composition(origin);
    }

    public Composition crossover(Composition parent1, Composition parent2) {
        //@todo Composer::crossover
        return new Composition(parent1);
    }

    public boolean conserve(Composition composition) {

        if (this.getAim().completed(composition)
                && this.styles.stream()
                        .allMatch(s -> s.rateComposition(composition) > CONSERVE_SCORE)) {
            this.conservetory.add(composition);
            return true;
        }
        return false;
    }

    @Override
    public void render() {

        //@todo Composer::render
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
