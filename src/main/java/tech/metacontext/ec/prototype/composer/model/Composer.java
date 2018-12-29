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
import tech.metacontext.ec.prototype.composer.Settings;
import tech.metacontext.ec.prototype.composer.ex.ConservationFailedException;
import tech.metacontext.ec.prototype.composer.factory.*;
import tech.metacontext.ec.prototype.composer.styles.Style;
import tech.metacontext.ec.prototype.composer.enums.ComposerAim;
import tech.metacontext.ec.prototype.composer.operations.MutationType;
import static tech.metacontext.ec.prototype.composer.operations.MutationType.*;
import static tech.metacontext.ec.prototype.composer.Settings.*;
import tech.metacontext.ec.prototype.render.ScatterPlot_AWT;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class Composer extends Population<Composition> {

    private static Logger _logger;

    private static CompositionFactory compositionFactory;
    private static ConnectorFactory connectorfactory;

    private ComposerAim aim;
    private List<Style> styles;
    private int size;

    private final Map<Composition, Integer> conservetory;

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

        _logger = Logger.getLogger(getId());
        setFileHandler(logState, _logger);

        _logger.log(Level.INFO,
                "Initilizing Composer [{0}]", this.getId());

        _logger.log(Level.INFO,
                "Initializing ConnectorFactory...");
        Composer.connectorfactory = ConnectorFactory.getInstance();

        _logger.log(Level.INFO,
                "Initializing CompositionFactory...");
        Composer.compositionFactory = CompositionFactory.getInstance(this.getId());

        this.size = size;
        this.aim = aim;
        this.styles = new ArrayList<>(Arrays.asList(styles));
        this.conservetory = new HashMap<>();

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
        this.genCountIncrement();
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
                "Composing, totally {0} Compositions been elongated.", num_elongated);

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
                < Math.pow(CHANCE_ELONGATION_IF_COMPLETED,
                        c.getSize() - this.getAim().getSize());
    }

    @Override
    public void evolve() {

        /*
            1.選出一條
            2.若not completed則mutate -> children
            3.若completed則決定是否走mutate, yes -> mutate -> children
            4.若走crossover則選出另一條completed(也許是自己)
            5.crossover -> children
         */
        _logger.log(Level.INFO,
                "Evolving from {0} parents.", this.getPopulationSize());
        List<Composition> children = Stream.generate(() -> randomSelect(SELECT_FROM_ALL))
                .map(this::getChild)
                .filter(child -> !this.conserve(child))
                .limit(size)
                .collect(Collectors.toList());
        _logger.log(Level.INFO,
                "Evloving finished, gen = {0}, size = {1}, {2}",
                new Object[]{this.getGenCount(), children.size(), getSummary(children)});
        this.setPopulation(children);
    }

    public static String getSummary(List<Composition> list) {

        return list.stream()
                .collect(Collectors.groupingBy(Composition::getSize))
                .entrySet().stream()
                .map(e -> String.format("%2d x %2d", e.getKey(), e.getValue().size()))
                .collect(Collectors.joining(", "));
    }

    public Composition getChild(Composition p0) {

        if (this.getAim().completed(p0)
                && Math.random() < CHANCE_CROSSOVER_IF_COMPLETED) {
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

    public boolean conserve(Composition composition) throws ConservationFailedException {

        if (this.conservetory.containsKey(composition)) {
            _logger.log(Level.WARNING,
                    "Checking composition already in the conservatory: {0}",
                    composition.getId_prefix());
        }
        if (!this.getAim().completed(composition)) {
            return false;
        }
        composition.getRenderedChecked();
        if (getMinScore(composition) < SCORE_CONSERVE_IF_COMPLETED) {
            return false;
        }
        _logger.log(Level.INFO, "Qualified Composition been located: {0}",
                output(composition));
        _logger.log(Level.INFO,
                "Composition {0} being duplicated for conservation.",
                composition.getId_prefix());
        Composition dupe = compositionFactory.forArchiving(composition);
        this.conservetory.put(dupe, this.getGenCount());
        if (this.conservetory.containsKey(dupe)) {
            _logger.log(Level.INFO,
                    "Composition {0} been conserved.",
                    composition.getId_prefix());
        } else {
            throw new ConservationFailedException(
                    "id = " + dupe.getId_prefix() + ", gen = " + this.getGenCount());
        }
        return true;
    }

    @Override
    public void render() {

        _logger.log(Level.INFO, "Rendering Composer {0}", this.getId());
        ScatterPlot_AWT plot = new ScatterPlot_AWT("Composer " + this.getId());
        List<SimpleEntry<Integer, Double>> popScores = IntStream.range(0, this.getGenCount())
                .mapToObj(i
                        -> this.getArchive().get(i).stream()
                        .filter(this.getAim()::completed)
                        .filter(c -> getMinScore(c) > 0.0)
                        .peek(c -> {
                            if (getMinScore(c) > Settings.SCORE_CONSERVE_IF_COMPLETED) {
                                System.out.println("Qualified composition left in population at gen#" + i);
                                System.out.println(c);
                            }
                        })
                        .mapToDouble(Composer::getMinScore)
                        .mapToObj(s -> new SimpleEntry<>(i, s)))
                .flatMap(s -> s)
                .collect(Collectors.toList());
        plot.addSeries("Population", popScores);
        List<SimpleEntry<Integer, Double>> conserveScores = this.getConservetory().entrySet().stream()
                .map(e -> new SimpleEntry<>(e.getValue(), getMinScore(e.getKey())))
                .collect(Collectors.toList());
        plot.addSeries("Conservatory", conserveScores);
        plot.createScatterPlot("Evolutionary Computation", "Generation", "Score", 560, 367, true);
        plot.showPlotWindow();
    }

    public static double getMinScore(Composition c) {

        return c.getEval().getScores().values().stream()
                .mapToDouble(s -> s)
                .min()
                .getAsDouble();
    }

    public static String output(Composition... list) {

        StringBuilder report = new StringBuilder();
        Stream.of(list).forEach(composition -> report
                .append(composition.getId_prefix()).append(" ")
                .append(composition.getEval().getScores().entrySet().stream()
                        .map(e -> String.format("%s: %.3f", e.getKey(), e.getValue()))
                        .collect(Collectors.joining(" | "))));
        return report.toString();
    }

    public void persistAll() {

        this.getConservetory().keySet().stream().forEach(Composition::persistent);
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

    public Map<Composition, Integer> getConservetory() {
        return conservetory;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
