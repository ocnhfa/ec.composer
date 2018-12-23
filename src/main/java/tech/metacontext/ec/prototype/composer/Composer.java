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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import tech.metacontext.ec.prototype.abs.Population;
import tech.metacontext.ec.prototype.composer.connectors.ConnectorFactory;
import tech.metacontext.ec.prototype.composer.enums.ComposerAim;
import tech.metacontext.ec.prototype.composer.operations.MutationType;
import static tech.metacontext.ec.prototype.composer.operations.MutationType.Deletion;
import static tech.metacontext.ec.prototype.composer.operations.MutationType.Insertion;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class Composer extends Population<Composition> {

    Logger _logger = Logger.getLogger(Composer.class.getName());

    CompositionFactory compositionFactory = CompositionFactory.getInstance();
    ConnectorFactory connectorfactory = ConnectorFactory.getInstance();

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
    static final double CONSERVE_SCORE = 0.8;
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

        this.size = size;
        this.aim = aim;
        this.styles = new ArrayList<>(Arrays.asList(styles));
        this.conservetory = new ArrayList<>();
        this.setPopulation(Stream.generate(()
                -> compositionFactory.newInstance(
                        this.generateSeed(),
                        connectorfactory.newConnector(this::styleChecker),
                        this.styles))
                .limit(size)
                .collect(Collectors.toList())
        );
        _logger.log(Level.INFO,
                "Composer created: size = {0}, aim = {1}, styles = {2}",
                new Object[]{size, aim, this.styles.stream()
                            .map(style -> style.getClass().getSimpleName())
                            .collect(Collectors.joining(", "))});
    }

    public boolean styleChecker(SketchNode node) {

        boolean checker = this.getStyles().stream().allMatch(
                style -> style.qualifySketchNode(node));
        _logger.log(Level.INFO,
                "Check SketchNode {0}, result = {1}", new Object[]{
                    node.getId_prefix(),
                    checker});
        return checker;
    }
    private static final SketchNodeFactory sketchNodeFactory = SketchNodeFactory.getInstance();

    public SketchNode generateSeed() {

        SketchNode seed = sketchNodeFactory.newInstance(this::styleChecker);
        _logger.log(Level.INFO,
                "Seed {0} generated.",
                seed.getId_prefix());
        return seed;
    }

    public Composer compose() {

        this.archive(compositionFactory);
        _logger.log(Level.INFO,
                "Composing, {0} Compositions archived as Generation {1}.",
                new Object[]{
                    this.getArchive().get(this.getGenCount()).size(),
                    this.getGenCount()});
        this.genCountIncrement();
        long elongated = this.getPopulation().stream()
                .filter(c -> !aim.completed(c) || this.ifElongate(c))
                .peek(c -> c.elongation(this::styleChecker))
                .collect(Collectors.counting());
        _logger.log(Level.INFO,
                "Composing, {0} Compositions elongated.",
                elongated);
        int original = this.getSize();
        this.getPopulation().removeIf(this::conserve);
        if (original - this.getSize() > 0) {
            _logger.log(Level.INFO,
                    "Composing, {0} Composition(s) conserved.",
                    original - this.getSize());
        }
        return this;
    }

    private boolean ifElongate(Composition c) {

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
            Composition sel_1 = this.randomSelect(SELECT_FROM_ALL);
            Composition sel_2 = this.randomSelect(SELECT_ONLY_COMPLETED);
            Composition child;
            if (this.getAim().completed(sel_1)
                    && Math.random() < CROSSOVER_CHANCE_IF_COMPLETED
                    && !Objects.equals(sel_1, sel_2)) {
                child = this.crossover(sel_1, sel_2);
            } else {
                child = this.mutate(sel_1);
            }
            children.add(child);
            int original = children.size();
            children.removeIf(this::conserve);
            if (original - children.size() > 0) {
                _logger.log(Level.INFO,
                        "Evloving, {0} Composition(s) conserved.",
                        original - children.size());
            }
        } while (children.size() < this.getSize());
        _logger.log(Level.INFO,
                "Evloving finished. New population: {0} Compositions: {1}",
                new Object[]{children.size(),
                    children.stream()
                            .map(Composition::getSize)
                            .map(String::valueOf)
                            .collect(Collectors.joining(" "))});
        this.setPopulation(children);
    }

    public Composition randomSelect(int state) {
        /*
         new Random().ints(0, this.getPopulationSize())
                .mapToObj(this.getPopulation()::get)
         */
        List<Composition> list = this.getPopulation().stream()
                .filter(c -> state == SELECT_FROM_ALL || this.getAim().completed(c))
                .collect(Collectors.toList());
        if (list.isEmpty()) {
            return null;
        }
        return list.get(new Random().nextInt(list.size()));
    }

    public Composition mutate(Composition origin) {

        Composition mutant = compositionFactory.forMutation(origin);
        int selected = new Random().nextInt(mutant.getSize() - 1);
        MutationType type = MutationType.getRandom();
        switch (type) {
            case Alteration:
                mutant.getConnectors().set(selected,
                        connectorfactory.newConnector(this::styleChecker));
                break;
            case Insertion:
            case Deletion:
                if (this.getAim().completed(origin)) {
                    mutant.getConnectors().remove(selected);
                    type = Deletion;
                } else {
                    mutant.getConnectors().add(selected,
                            connectorfactory.newConnector(this::styleChecker));
                    type = Insertion;
                }
                break;
        }
        _logger.log(Level.INFO,
                "Mutation, mutant: {0}, type: {1}, loci: {2}, length: {3} -> {4}",
                new Object[]{
                    mutant.getId_prefix(), type, selected,
                    origin.getSize(), mutant.getSize()});
        return mutant;
    }

    public Composition crossover(Composition parent1, Composition parent2) {

        int index = 1;
        Composition child = compositionFactory.forCrossover(
                parent1.getSeed(),
                parent1.getConnectors().get(0),
                this.styles);
        String crossover_state = "A";
        do {
            Composition activated = new Random().nextBoolean()
                    ? ((parent1.getSize() - 1 > index) ? parent1 : parent2)
                    : ((parent2.getSize() - 1 > index) ? parent2 : parent1);
            child.addConnector(activated.getConnectors().get(index));
            crossover_state += (Objects.equals(activated, parent1)) ? "A" : "B";
        } while (++index < Math.max(parent1.getSize() - 1, parent2.getSize() - 1));
        _logger.log(Level.INFO,
                "Crossover, [{0}, {1}] -> {2}", new Object[]{
                    parent1.getId_prefix(),
                    parent2.getId_prefix(),
                    child.getId_prefix()});
        _logger.log(Level.INFO,
                "Crossover, {0} = {1}", new Object[]{
                    child.getId_prefix(),
                    crossover_state});
        return child;
    }

    /*
     || this.styles.stream()
                .peek(style -> System.out.println(style.getClass().getSimpleName()
                + "-> " + style.rateComposition(composition)))
                .anyMatch(s -> s.rateComposition(composition) < CONSERVE_SCORE)
     */
    public boolean conserve(Composition composition) {

        if (this.conservetory.contains(composition)) {
            _logger.log(Level.WARNING,
                    "Checking composition already in the conservatory: {0}",
                    composition.getId_prefix());
        }
        if (!this.getAim().completed(composition)) {
            return false;
        }
        composition.getRenderedChecked();
        if (this.styles.stream()
                .anyMatch(s -> composition.getScore(s) < CONSERVE_SCORE)) {
            return false;
        }
        this.styles.forEach(s
                -> System.out.println(
                        s.getClass().getSimpleName() + ": "
                        + composition.getScore(s)));
        this.conservetory.add(compositionFactory.forArchiving(composition));
        if (this.conservetory.contains(composition)) {
            _logger.log(Level.INFO,
                    "Composition {0} been conserved.",
                    composition.getId_prefix());
        }
        return true;
    }

    @Override
    public void render() {

        StringBuilder report = new StringBuilder();
        this.getConservetory().stream()
                .forEach(composition -> this.getStyles().forEach(style
                -> report.append(String.format("%.4f %s %s\n",
                        composition.getScore(style),
                        style.getClass().getSimpleName(),
                        composition.getId_prefix()))));
        System.out.println(report);
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
