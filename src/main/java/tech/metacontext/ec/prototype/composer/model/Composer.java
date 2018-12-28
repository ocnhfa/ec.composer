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
package tech.metacontext.ec.prototype.composer.model;

import tech.metacontext.ec.prototype.abs.Population;
import tech.metacontext.ec.prototype.composer.factory.*;
import tech.metacontext.ec.prototype.composer.styles.Style;
import tech.metacontext.ec.prototype.composer.enums.ComposerAim;
import tech.metacontext.ec.prototype.composer.operations.MutationType;
import static tech.metacontext.ec.prototype.composer.operations.MutationType.*;
import static tech.metacontext.ec.prototype.composer.Settings.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class Composer extends Population<Composition> {

    private final static Logger _logger
            = Logger.getLogger(Composer.class.getName());

    private static CompositionFactory compositionFactory;
    private static ConnectorFactory connectorfactory;

    private ComposerAim aim;
    private List<Style> styles;
    private int size;

    private final List<Composition> conservetory;

    public static final int SELECT_FROM_ALL = 0, SELECT_ONLY_COMPLETED = 1;

    /**
     * Constructor.
     *
     * @param size
     * @param logState: USE_EXISTING = 0, RENEW = 1, RENEW_TEST = 2;
     * @param aim
     * @param styles
     * @throws java.lang.Exception
     */
    public Composer(int size, ComposerAim aim, int logState, Style... styles)
            throws Exception {

        setFileHandler(logState, _logger);

        _logger.log(Level.INFO,
                "Initializing ConnectorFactory...");
        Composer.connectorfactory = ConnectorFactory.getInstance();

        _logger.log(Level.INFO,
                "Initializing CompositionFactory...");
        Composer.compositionFactory = CompositionFactory.getInstance();

        this.size = size;
        this.aim = aim;
        this.styles = new ArrayList<>(Arrays.asList(styles));
        this.conservetory = new ArrayList<>();

        _logger.log(Level.INFO,
                "Initializing Composition Population...");
        this.setPopulation(Stream.generate(()
                -> compositionFactory.newInstance(
                        styleChecker, this.styles))
                .limit(size)
                .collect(Collectors.toList()));

        _logger.log(Level.INFO,
                "Composer created: size = {0}, aim = {1}, styles = {2}",
                new Object[]{size, aim, this.styles.stream()
                            .map(style -> style.getClass().getSimpleName())
                            .collect(Collectors.joining(", "))});
    }

    public Predicate<SketchNode> styleChecker = (node) -> this.getStyles().stream()
            .allMatch(s -> s.qualifySketchNode(node));

    public Composer compose() {

        this.archive(compositionFactory);
        _logger.log(Level.INFO,
                "Compose... {0} Compositions archived as Generation {1}.",
                new Object[]{
                    this.getArchive().get(this.getGenCount()).size(),
                    this.getGenCount()});

        long num_elongated = this.getPopulation().stream()
                .filter(this::toBeElongated)
                .peek(c -> {
                    c.elongation(this.styleChecker);
                    _logger.log(Level.INFO,
                            "Composition {0} been elongated.",
                            c.getId_prefix());
                })
                .collect(Collectors.counting());
        _logger.log(Level.INFO,
                "Composing, {0} Compositions elongated.", num_elongated);

        int original = this.getSize();
        this.getPopulation().removeIf(this::conserve);
        if (original - this.getSize() > 0) {
            _logger.log(Level.INFO,
                    "Composing, {0} Composition(s) conserved.",
                    original - this.getSize());
        }
        return this;
    }

    /**
     * Decide if a composition to be elongated. It's always true if the
     * composition has not been completed. Otherwise, it's decided by
     * ELONGATION_CHANCE.
     *
     * @param composition
     * @return true: to be elongated. false: not to be elongated.
     */
    private boolean toBeElongated(Composition c) {

        return !aim.completed(c) || Math.random()
                < Math.pow(ELONGATION_CHANCE,
                        c.getSize() - this.getAim().getSize());
    }

    @Override
    public void evolve() {

        _logger.log(Level.INFO,
                "Evolving from {0} parents.", this.getPopulationSize());
        List<Composition> children = Stream.generate(
                () -> this.randomSelect(SELECT_FROM_ALL))
                .unordered()
                .parallel()
                .map(this::getChild)
                .filter(child -> !this.conserve(child))
                .sequential()
                .limit(size)
                .collect(Collectors.toList());
        /*
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
         */
        _logger.log(Level.INFO,
                "Evloving finished. New population: {0} Compositions: {1}",
                new Object[]{children.size(),
                    children.stream()
                            .map(Composition::getSize)
                            .map(String::valueOf)
                            .collect(Collectors.joining(" "))});
        this.setPopulation(children);
        this.genCountIncrement();
    }

    public Composition getChild(Composition p0) {

        if (this.getAim().completed(p0)
                && Math.random() < CROSSOVER_CHANCE_IF_COMPLETED) {
            Composition p1 = this.randomSelect(SELECT_ONLY_COMPLETED);
            if (!Objects.equals(p0, p1)) {
                return this.crossover(p0, p1);
            }
        }
        return this.mutate(p0);
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
        _logger.log(Level.INFO,
                "Composition {0} being duplicated to {1} for mutation.",
                new Object[]{origin.getId_prefix(), mutant.getId_prefix()});

        int selected = new Random().nextInt(mutant.getSize() - 1);
        MutationType type = MutationType.getRandom();
        switch (type) {
            case Alteration:
                mutant.getConnectors().set(selected,
                        connectorfactory.newConnector(this.styleChecker));
                break;
            case Insertion:
            case Deletion:
                if (this.getAim().completed(origin)) {
                    mutant.getConnectors().remove(selected);
                    type = Deletion;
                } else {
                    mutant.getConnectors().add(selected,
                            connectorfactory.newConnector(this.styleChecker));
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

    public Composition crossover(Composition p0, Composition p1) {

        int index = 1;
        Composition child = compositionFactory.forCrossover(
                p0.getSeed(),
                p0.getConnectors().get(0),
                this.styles);
        _logger.log(Level.INFO,
                "Composition {0} being transformed to {1} for crossover.",
                new Object[]{p0.getId_prefix(), child.getId_prefix()});

        String crossover_state = "X";
        do {
            Composition activated = new Random().nextBoolean()
                    ? ((p0.getSize() - 1 > index) ? p0 : p1)
                    : ((p1.getSize() - 1 > index) ? p1 : p0);
            child.addConnector(activated.getConnectors().get(index));
            crossover_state += (Objects.equals(activated, p0)) ? "X" : "Y";
        } while (++index < Math.max(p0.getSize() - 1, p1.getSize() - 1));
        _logger.log(Level.INFO,
                "Crossover, [{0}, {1}] -> {2}", new Object[]{
                    p0.getId_prefix(),
                    p1.getId_prefix(),
                    child.getId_prefix()});
        _logger.log(Level.INFO,
                "Crossover, {0} = {1}", new Object[]{
                    child.getId_prefix(),
                    crossover_state});
        return child;
    }

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
        System.out.println(output(composition));
        _logger.log(Level.INFO,
                "Composition {0} being duplicated for conservation.",
                composition.getId_prefix());
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

        System.out.println(
                output(this.getConservetory().toArray(Composition[]::new)));
    }

    public static String output(Composition... list) {

        StringBuilder report = new StringBuilder();
        Stream.of(list).forEach(composition -> report
                .append(composition.getId_prefix()).append(" ")
                .append(composition.getEval().getScores().entrySet().stream()
                        .map(e -> String.format("%s: %.3f", e.getKey(), e.getValue()))
                        .collect(Collectors.joining(" | ")))
                .append('\n'));
        return report.toString();
    }

    public void persistAll() {

        this.getConservetory().stream().forEach(Composition::persistent);
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
