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

import static tech.metacontext.ec.prototype.composer.Settings.*;
import tech.metacontext.ec.prototype.composer.enums.ComposerAim;
import tech.metacontext.ec.prototype.composer.model.*;
import tech.metacontext.ec.prototype.composer.styles.*;
import tech.metacontext.ec.prototype.render.LineChart_AWT;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static tech.metacontext.ec.prototype.composer.Parameters.*;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class Main {

    /**
     * Entry point of main.
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        // 決定作品數量及演進世代
        int POP_SIZE = 100;
        int SELECTED_SIZE = 5;
        int GENERATION = 0;

        Main main = new Main(
                POP_SIZE,
                SELECTED_SIZE,
                GENERATION,
                LogState.DEFAULT);
//                LogState.DISABLED);

//        main.composer.render(Composer.RENDERTYPE_AVERAGELINECHART);
        main.composer.render(Composer.RENDERTYPE_COMBINEDCHART);
        System.out.println(header("Persisting Conservatory"));
        main.composer.persistAll();

        var chart = new LineChart_AWT("Composer " + main.composer.getId());
        var gsc = new GoldenSectionClimax(UnaccompaniedCello.RANGE.keySet());
        ToDoubleFunction<SketchNode> mapper = node
                -> gsc.climaxIndex(node);
        ToDoubleBiFunction<Composition, Integer> mapper2 = (c, i)
                -> gsc.getStandard(c, i);
        var max = main.composer.getConservetory().keySet().stream()
                .sorted((o1, o2) -> o1.getId().compareTo(o2.getId()))
                .peek(gsc::updateClimaxIndexes)
                .peek(c
                        -> IntStream.range(0, c.getSize())
                        .forEach(i
                                -> chart.addData(mapper.applyAsDouble(c.getRendered().get(i)), c.getId_prefix(), "" + i)
                        ))
                .max((o1, o2) -> {
                    gsc.updateClimaxIndexes(o1);
                    double o1Peak = gsc.getPeak();
                    gsc.updateClimaxIndexes(o2);
                    double o2Peak = gsc.getPeak();
                    return Double.compare(o1Peak, o2Peak);
                })
                .get();
        IntStream.range(0, max.getSize())
                .forEach(i -> {
                    chart.addData(mapper2.applyAsDouble(max, i), "standard", "" + i);
                });
        chart.createLineChart("SketchNode Rating Chart",
                "Generation", "Score", 560, 367, true);
        chart.showChartWindow();
    }

    private Composer composer;

    /**
     * Main constructor.
     *
     * @param popSize
     * @param goalSize
     * @param generation
     * @param logState
     * @throws Exception
     */
    public Main(int popSize,
            int goalSize,
            int generation,
            LogState logState) throws Exception {

        this(popSize, goalSize, generation,
                SELECTION_THRESHOLD.getDouble(),
                SCORE_CONSERVE_IF_COMPLETED.getDouble(),
                logState);
    }

    /**
     *
     * @param popSize
     * @param goalSize
     * @param generation
     * @param threshold
     * @param conserve_score
     * @param logState
     * @throws Exception
     */
    public Main(int popSize,
            int goalSize,
            int generation,
            double threshold,
            double conserve_score,
            LogState logState) throws Exception {

        this.composer = new Composer(popSize, ComposerAim.Phrase,
                logState, threshold, conserve_score,
                new UnaccompaniedCello(),
                new GoldenSectionClimax(UnaccompaniedCello.RANGE.keySet())
        );
        System.out.println(header("Evolutionary Computation"));
        System.out.printf("Composer = [%s]\n", composer.getId());
        System.out.println("Population size = " + popSize);
        System.out.println("Expected conservatory size = " + goalSize);
        System.out.println("Threshold = " + threshold);
        System.out.println("Conserve Score = " + conserve_score);
        System.out.println("Generation = " + generation);
        System.out.println(header("Evolution"));
        int conserved = 0;
        do {
            if (composer.getGenCount() > 0) {
                if (composer.getGenCount() % 100 == 0) {
                    System.out.println(" (" + composer.getGenCount() + ")");
                } else if (composer.getGenCount() % 50 == 0) {
                    System.out.print("|");
                }
            }
            composer.compose().evolve();
            if (composer.getConservetory().size() > conserved) {
                System.out.print(composer.getConservetory().size() - conserved);
                conserved = composer.getConservetory().size();
            } else {
                System.out.print(".");
            }
        } while (composer.getConservetory().size() < goalSize
                || composer.getGenCount() < generation);
        System.out.println(" (" + composer.getGenCount() + ")");

        System.out.println(header("Dumping Archive"));
        IntStream.range(0, composer.getGenCount())
                .mapToObj(i -> String.format("%3d >> ", i)
                + Composer.getSummary(composer.getArchive().get(i)))
                .forEach(System.out::println);

        composer.getConservetory().keySet().stream()
                .peek(c -> System.out.println(Composer.simpleScoreOutput(c)))
                .forEach(c
                        -> System.out.println("GSC: " + c.getRendered().stream()
                        .map(new GoldenSectionClimax(UnaccompaniedCello.RANGE.keySet())::climaxIndex)
                        .map(s -> String.format("%.2f", s))
                        .collect(Collectors.joining(" "))));
    }

    static String header(String text) {

        return "\n---------- " + text + " ----------";
    }

    public Composer getComposer() {

        return composer;
    }

}
