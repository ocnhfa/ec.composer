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
import tech.metacontext.ec.prototype.render.LineChart_AWT;

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

    public Predicate<SketchNode> styleChecker
            = (node) -> this.getStyles().stream()
                    .allMatch(s -> s.qualifySketchNode(node));

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
                .peek(c -> c.addDebugMsg("Initialization..."))
                .collect(Collectors.toList()));

        _logger.log(Level.INFO,
                "Composer created: size = {0}, aim = {1}, styles = {2}",
                new Object[]{size, aim, this.styles.stream()
                            .map(style -> style.getClass().getSimpleName())
                            .collect(Collectors.joining(", "))});
    }

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

        _logger.log(Level.INFO,
                "Evolving from {0} parents.", this.getPopulationSize());
        List<Composition> children
                = Stream.generate(() -> randomSelect(SELECT_FROM_ALL))
                        .map(this::getChild)
                        .filter(c -> !this.conserve(c))
                        .limit(size)
                        .collect(Collectors.toList());
        _logger.log(Level.INFO,
                "Evloving finished, gen = {0}, size = {1}, {2}",
                new Object[]{this.getGenCount(),
                    children.size(),
                    getSummary(children)});
        this.setPopulation(children);
    }

    public static String getSummary(List<Composition> list) {

        return list.stream()
                .collect(Collectors.groupingBy(Composition::getSize))
                .entrySet().stream()
                .map(e -> String.format("%2d x %2d", e.getKey(), e.getValue().size()))
                .collect(Collectors.joining(", "));
    }

    /**
     * Get child via mutation or crossover.
     *
     * @param p0 if not completed, mutate; if completed there's a chance to
     * crossover.
     * @return the child produced.
     */
    public Composition getChild(Composition p0) {

        /*
            1.若p0 not completed則mutate -> children
            2.若completed則仍有一定機率走mutate -> children
            3.若則選出另一條p1 completed(不能是自己), crossover -> children
         */
        if (this.getAim().completed(p0)
                && Math.random() < CHANCE_CROSSOVER_IF_COMPLETED) {
            Composition p1 = this.randomSelect(SELECT_ONLY_COMPLETED);
            if (!Objects.equals(p0, p1)) {
                return this.crossover(p0, p1);
            }
        }
        return this.mutate(p0);
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
                if (!this.getAim().completed(origin)) {
                    mutant.getConnectors().add(selected,
                            connectorfactory.newConnector(this.styleChecker));
                    break;
                }
                type = Deletion;
            case Deletion:
                mutant.getConnectors().remove(selected);
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
            child.addConnector(connectorfactory
                    .forMutation(activated.getConnectors().get(index)));
            crossover_state += (Objects.equals(activated, p0)) ? "X" : "Y";
        } while (++index < Math.max(p0.getSize() - 1, p1.getSize() - 1));
        _logger.log(Level.INFO,
                "Crossover, [{0}, {1}] -> {2} = {3}", new Object[]{
                    p0.getId_prefix(),
                    p1.getId_prefix(),
                    child.getId_prefix(),
                    crossover_state});
//        child.getRenderedChecked(this.getClass().getSimpleName() + "::crossover");
        return child;
    }

    public Composition randomSelect(int state) {
        /*
         new Random().ints(0, this.getPopulationSize())
                .mapToObj(this.getPopulation()::get)
         */
        double avg = this.getPopulation().stream()
                //                .filter(this.getAim()::completed)
                .peek(Composition::updateEval)
                .mapToDouble(this::getMinScore)
                //                .peek(System.out::println)
                .average()
                .orElse(0.0);
//        System.out.println("average = " + average);
        List<Composition> list = this.getPopulation().stream()
                .filter(c -> state == SELECT_FROM_ALL
                || (this.getAim().completed(c) && this.getMinScore(c) >= avg))
                .collect(Collectors.toList());
        if (list.isEmpty()) {
            return null;
        }
        return list.get(new Random().nextInt(list.size()));
    }

    /**
     * Conserve qualified composition into conservatory.
     *
     * @param c composition under check to be conserved.
     * @return TRUE: if successfully conserved; FALSE: if not conserved.
     * @throws ConservationFailedException
     */
    public boolean conserve(Composition c) throws ConservationFailedException {

        if (!this.getAim().completed(c)) {
            return false;
        }
        c.getRenderedChecked(this.getClass().getSimpleName() + "::conserve");
        c.addDebugMsg("under conservation check.");
        if (getMinScore(c) < SCORE_CONSERVE_IF_COMPLETED) {
            c.addDebugMsg("fail conservation check: " + simpleScoreOutput(c));
            return false;
        }
        c.addDebugMsg("pass conservation check: " + simpleScoreOutput(c));
        _logger.log(Level.INFO, "Qualified Composition been located: {0}",
                simpleScoreOutput(c));
        _logger.log(Level.INFO,
                "Composition {0} being duplicated for conservation.",
                c.getId_prefix());
        Composition dupe = compositionFactory.forArchiving(c);
        if (Objects.nonNull(this.conservetory.put(dupe, this.getGenCount()))) {
            _logger.log(Level.WARNING,
                    "Conserving with an Id already existing in conservatory: {0}",
                    c.getId_prefix());
        }
        if (this.conservetory.containsKey(dupe)) {
            _logger.log(Level.INFO,
                    "Composition {0} been conserved.",
                    c.getId_prefix());
        } else {
            throw new ConservationFailedException(
                    "id = " + dupe.getId_prefix() + ", gen = " + this.getGenCount());
        }
        return true;
    }
    public static final int RENDERTYPE_SCATTERPLOT = 0;
    public static final int RENDERTYPE_AVERAGELINECHART = 1;

    public void render(int type) {

        _logger.log(Level.INFO, "Rendering Composer {0}", this.getId());
        switch (type) {
            case 0:
                render();
                break;
            case 1:
                renderAveLineChart();
                break;
        }
    }

    public void renderAveLineChart() {

        LineChart_AWT chart = new LineChart_AWT("Composer " + this.getId());
        IntStream.range(0, this.getGenCount())
                .forEach(i -> {
                    List<Composition> list = this.getArchive().get(i);
                    double average = list.stream()
                            .mapToDouble(this::getMinScore)
//                            .filter(s -> s > 0.0)
                            .average()
                            .orElse(0.0);
                    chart.addData(average, "average", "" + i);
                });
        chart.createLineChart("Evolutionary Computation",
                "Generation", "Score",
                1600, 630, true);
        chart.showChartWindow();
    }

    @Override
    public void render() {

        ScatterPlot_AWT plot = new ScatterPlot_AWT("Composer " + this.getId());
        List<SimpleEntry<Integer, Double>> popScores = IntStream.range(0, this.getGenCount())
                .mapToObj(i
                        -> this.getArchive().get(i).stream()
                        .filter(this.getAim()::completed)
                        .filter(c -> this.getMinScore(c) > 0.0)
                        //.peek(c -> this.scanQualifiedComposition(c, i))
                        .mapToDouble(this::getMinScore)
                        .mapToObj(s -> new SimpleEntry<>(i, s)))
                .flatMap(s -> s)
                .collect(Collectors.toList());
        plot.addSeries("Population", popScores);
        List<SimpleEntry<Integer, Double>> conserveScores = this.getConservetory().entrySet().stream()
                .map(e -> new SimpleEntry<>(e.getValue(), this.getMinScore(e.getKey())))
                .collect(Collectors.toList());
        plot.addSeries("Conservatory", conserveScores);
        plot.createScatterPlot("Evolutionary Computation",
                "Generation", "Score",
                1600, 630, true);
        plot.showPlotWindow();
    }

    /*
    public void scanQualifiedComposition(Composition c, int i) {
        if (getMinScore(c) < Settings.SCORE_CONSERVE_IF_COMPLETED) {
            return;
        }
        System.out.println("Qualified composition left in population at gen#" + i);
        System.out.println(c);
        System.out.println(c.getDebug());
    }
     */
    public double getMinScore(Composition c) {

        return this.getAim().completed(c)
                ? c.getEval().getScores().values().stream()
                        .mapToDouble(s -> s)
                        .min().getAsDouble()
                : 0.0;
    }

    public static String simpleScoreOutput(Composition... list) {

        StringBuilder report = new StringBuilder();
        Stream.of(list).forEach(composition -> report
                .append(composition.getId_prefix()).append(" ")
                .append(composition.getEval().getScores().entrySet().stream()
                        .map(e -> String.format("%s: %.3f", e.getKey(), e.getValue()))
                        .collect(Collectors.joining(" | "))));
        return report.toString();
    }

    public void persistAll() {

        this.getConservetory().keySet().stream()
                .forEach(Composition::persistent);
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
