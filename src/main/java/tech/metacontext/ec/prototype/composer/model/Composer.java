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
import tech.metacontext.ec.prototype.composer.enums.ComposerAim;
import tech.metacontext.ec.prototype.composer.operations.MutationType;
import tech.metacontext.ec.prototype.composer.styles.*;
import tech.metacontext.ec.prototype.composer.factory.*;
import tech.metacontext.ec.prototype.render.*;
import static tech.metacontext.ec.prototype.composer.operations.MutationType.*;
import static tech.metacontext.ec.prototype.composer.Settings.*;
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
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.Map.Entry;
import java.util.function.Function;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.ScatterRenderer;
import tech.metacontext.ec.prototype.composer.Main;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class Composer extends Population<Composition> {

    public static void main(String[] args) throws Exception {

        Main main = new Main(50, 1, 0, TEST);
        main.getComposer().render(RENDERTYPE_COMBINEDCHART);
    }

    private static Logger _logger;

    private static CompositionFactory compositionFactory;
    private static ConnectorFactory connectorfactory;
    private static SketchNodeFactory sketchNodeFactory;

    private ComposerAim aim;
    private List<Style> styles;
    private int size;

    private final Map<Composition, Integer> conservetory;

    public Predicate<SketchNode> styleChecker
            = (node) -> this.getStyles().stream()
                    .allMatch(s -> s.qualifySketchNode(node));

    public static final int SELECT_FROM_ALL = 0, SELECT_ONLY_COMPLETED = 1;
    public static final int RENDERTYPE_SCATTERPLOT = 0,
            RENDERTYPE_AVERAGELINECHART = 1,
            RENDERTYPE_COMBINEDCHART = 2;

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
//        _logger.setFilter((r) -> false);

        _logger.log(Level.INFO,
                "Initilizing Composer [{0}]", this.getId());

        _logger.log(Level.INFO,
                "Initializing ConnectorFactory...");
        Composer.connectorfactory = ConnectorFactory.getInstance();

        _logger.log(Level.INFO,
                "Initializing CompositionFactory...");
        Composer.compositionFactory = CompositionFactory.getInstance(this.getId());

        _logger.log(Level.INFO,
                "Initializing SketchNodeFactory...");
        Composer.sketchNodeFactory = SketchNodeFactory.getInstance();

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
                .parallel()
                .filter(this::toBeElongated)
                .peek(c -> _logger.log(Level.INFO, "Composition {0} been elongated.", c.getId_prefix()))
                .sequential()
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

        if (aim.isCompleted(c) && Math.random()
                >= Math.pow(CHANCE_ELONGATION_IF_COMPLETED,
                        c.getSize() - this.getAim().getAimSize() - 1)) {
            return false;
        }
        c.elongation(this.styleChecker);
        return true;
    }

    @Override
    public void evolve() {

        _logger.log(Level.INFO,
                "Evolving from {0} parents.", this.getPopulationSize());
        List<Composition> children
                = Stream.generate(this::getChild)
                        .parallel()
                        .filter(c -> !this.conserve(c))
                        .limit(size)
                        .sequential()
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
    public Composition getChild() {

        /*
            1.若p0 not completed則mutate -> children
            2.若completed則仍有一定機率走mutate -> children
            3.若則選出另一條p1 completed(不能是自己), crossover -> children
         */
        Composition p0 = select(SELECT_FROM_ALL, SELECTION_THRESHOLD);
        if (this.getAim().isCompleted(p0)
                && Math.random() < CHANCE_CROSSOVER_IF_COMPLETED) {
            Composition p1 = this.select(SELECT_ONLY_COMPLETED, SELECTION_THRESHOLD);
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
                if (!this.getAim().isCompleted(origin)) {
                    mutant.getConnectors().add(selected,
                            connectorfactory.newConnector(this.styleChecker));
                    break;
                }
                type = Deletion;
            case Deletion:
                mutant.getConnectors().remove(selected);
                break;
        }
        boolean reseeding = Math.random() < CHANCE_RESEEDING;
        if (reseeding) {
            mutant.resetSeed(sketchNodeFactory.newInstance(styleChecker));
        }
        _logger.log(Level.INFO,
                "Mutation, mutant: {0}, type: {1}, loci: {2}, reseed = {3}, length: {4} -> {5}",
                new Object[]{
                    mutant.getId_prefix(),
                    type, selected,
                    origin.getSize(),
                    reseeding,
                    mutant.getSize()});
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

    @Override
    public Composition select(Predicate<Composition> criteria, double threshold) {

        List<Composition> subset = this.getPopulation().stream()
                .filter(criteria)
                .peek(Composition::updateEval)
                .sorted((c1, c2) -> (int) (this.getMinScore(c2) - this.getMinScore(c1)))
                .collect(Collectors.toList());
        if (subset.isEmpty() || threshold > 1.0 || threshold < 0.0) {
            return null;
        }
        int thresholdIndex = (int) ((subset.size() - 1) * threshold);
        double std = this.getMinScore(subset.get(thresholdIndex));
        List<Composition> selectedSubset = subset.stream()
                .filter(c -> this.getMinScore(c) >= std)
                .collect(Collectors.toList());
//        if (selectedSubset.size() < subset.size() && subset.size() < this.getSize()) {
//            System.out.println("->" + selectedSubset.stream()
//                    .map(this::getMinScore)
//                    .sorted()
//                    .map(s -> String.format("%.2f", s))
//                    .collect(Collectors.joining(", ")));
//            System.out.println("subsetsize = " + subset.size());
//            System.out.println("thresholdIndex = " + thresholdIndex);
//            System.out.println("std = " + std);
//            System.out.println("selectedSubset size = " + selectedSubset.size());
//        }
        /*
        subsetsize = 100
        threshold = 7.5
        thresholdIndex = 100*7.5 = 75
        filtered subset size = 25
        subset.size - thresholdIndex = 25.
         */
        return selectedSubset.get(new Random().nextInt(selectedSubset.size()));
    }

    /**
     * Randomly select composition from population with specified state and
     * threshold.
     *
     * @param state SELECT_FROM_ALL = 0, SELECT_ONLY_COMPLETED = 1.
     * @param threshold in percentage. For eg., 0.9 stands for that selected
     * score must be higher than 90% population.
     * @return Selected composition.
     */
    public Composition select(int state, double threshold) {

        return select(c -> state == SELECT_FROM_ALL || this.getAim().isCompleted(c),
                threshold);
    }

    /**
     * Conserve qualified composition into conservatory.
     *
     * @param c composition under check to be conserved.
     * @return TRUE: if successfully conserved; FALSE: if not conserved.
     * @throws ConservationFailedException
     */
    public boolean conserve(Composition c) throws ConservationFailedException {

        if (!this.getAim().isCompleted(c)) {
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

    @Override
    public void render(int type) {

        _logger.log(Level.INFO, "Rendering Composer {0}", this.getId());
        switch (type) {
            case 0:
                renderScatterPlot();
                break;
            case 1:
                renderAvgLineChart();
                break;
            case 2:
                renderCombinedChart();
                break;
        }
    }

    public void renderCombinedChart() {

        CombinedChart_AWT chart = new CombinedChart_AWT("Composer " + this.getId());
        Map<Integer, Double> avgs;
        avgs = IntStream.range(0, this.getGenCount())
                .boxed()
                .collect(Collectors.toMap(
                        Function.identity(),
                        i -> this.getArchive().get(i).stream()
                                /*...*/.mapToDouble(this::getMinScore)
                                /*...*/.filter(score -> score > 0.0)
                                /*...*/.average().orElse(0.0)));

        Map<Integer, List<Double>> xys, xyc;
        xys = IntStream.range(0, this.getGenCount())
                .boxed()
                .collect(Collectors.toMap(
                        Function.identity(),
                        i -> this.getArchive().get(i).stream()
                                /*...*/.map(this::getMinScore)
                                /*...*/.filter(score -> score > 0.0)
                                /*...*/.collect(Collectors.toList())));

        xyc = this.getConservetory().entrySet().stream()
                .collect(Collectors.groupingBy(Entry::getValue,
                        Collectors.mapping(e -> this.getMinScore(e.getKey()),
                                Collectors.toList())));

        double dotSize = 3.0;
        double delta = dotSize / 2.0;
        Shape shape = new Ellipse2D.Double(-delta, -delta, dotSize, dotSize);
        var scatterRenderer0 = new ScatterRenderer();
        var scatterRenderer1 = new ScatterRenderer();
        var lineAndShapeRenderer = new LineAndShapeRenderer();
        scatterRenderer0.setSeriesPaint(0, Color.GRAY);
        scatterRenderer0.setSeriesShape(0, shape);
        scatterRenderer1.setSeriesPaint(0, Color.RED);
        scatterRenderer1.setSeriesShape(0, shape);
        lineAndShapeRenderer.setDefaultShapesVisible(false);
        chart.addRenderer(
                new String[]{"score", "conservatory"},
                new CategoryItemRenderer[]{scatterRenderer0, scatterRenderer1},
                xys, xyc);
        lineAndShapeRenderer.setSeriesPaint(0, Color.BLUE);
        lineAndShapeRenderer.setSeriesShapesVisible(0, false);
        chart.addRenderer(2, "average", lineAndShapeRenderer, avgs);
        chart.addMarker(SCORE_CONSERVE_IF_COMPLETED, Color.BLACK);
        chart.createChart("Evolutionary Computation", "Generation", "Score", 1600, 630, true);
        chart.showChartWindow();
    }

    public void renderAvgLineChart() {

        LineChart_AWT chart = new LineChart_AWT("Composer " + this.getId());
//        LineChart_AWT chartStat = new LineChart_AWT("Composer " + this.getId());

        IntStream.range(0, this.getGenCount())
                .forEach(i -> {
                    List<Composition> list = this.getArchive().get(i);
                    List<Double> values = list.stream()
                            .map(this::getMinScore)
                            .filter(score -> score > 0.0)
                            .collect(Collectors.toList());
                    chart.addData(values, "average", "" + i);
//                    chartStat.addStatData(values, "score", "" + i);
                });
        chart.createLineChart("Evolutionary Computation",
                "Generation", "Score",
                1600, 630, true);
        chart.showChartWindow();

//        chartStat.createStatLineChart("Evolutionary Computation",
//                "Generation", "Score",
//                1600, 630, true);
//        chartStat.showChartWindow();
    }

    public void renderScatterPlot() {

        ScatterPlot_AWT plot = new ScatterPlot_AWT("Composer " + this.getId());
        List<SimpleEntry<Integer, Double>> popScores = IntStream.range(0, this.getGenCount())
                .mapToObj(i
                        -> this.getArchive().get(i).stream()
                        .mapToDouble(this::getMinScore)
                        .filter(score -> score > 0.0)
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
        plot.setSeriesDot(0, 3.0, Color.GRAY);
        plot.setSeriesDot(1, 3.0, Color.RED);
        plot.addHorizontalLine(SCORE_CONSERVE_IF_COMPLETED, Color.BLACK);
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

        return this.getAim().isCompleted(c)
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
