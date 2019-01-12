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
package tech.metacontext.ec.prototype.composer.styles;

import tech.metacontext.ec.prototype.composer.model.*;
import tech.metacontext.ec.prototype.composer.enums.mats.*;
import tech.metacontext.ec.prototype.composer.enums.*;
import tech.metacontext.ec.prototype.composer.materials.*;
import static tech.metacontext.ec.prototype.composer.Settings.*;
import static tech.metacontext.ec.prototype.composer.Parameters.DEFAULT_MAX_RHYTHMIC_POINTS;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class GoldenSectionClimax extends Style {

    public static void main(String[] args) throws Exception {

        var gsc = new GoldenSectionClimax(UnaccompaniedCello.getRange());
        var composer = new Composer(100, ComposerAim.Phrase, LogState.TEST,
                0.9, 0.9,
                new UnaccompaniedCello(),
                gsc);
        do {
            composer.compose().evolve();
            System.out.print(".");
        } while (composer.getConservetory().isEmpty());
        System.out.println("");
        composer.getConservetory().keySet().stream()
                .peek(c -> System.out.println(Composer.simpleScoreOutput(c)))
                .forEach(c -> {
                    var list = c.getRendered();
                    IntStream.range(0, list.size())
                            .peek(i -> System.out.printf("%.2f -> ", gsc.getStandard(c, i)))
                            .mapToObj(gsc.getClimaxIndexes()::get)
                            .forEach(System.out::println);
                });

//        Stream.generate(() -> SketchNodeFactory.getInstance().newInstance(composer.styleChecker))
//                .limit(50)
//                .peek(System.out::println)
//                .map(gsc::climaxIndex)
//                .forEach(System.out::println);
    }

    public static final double RATIO = 1.6180339887498948482;

    public final NoteRange lowest, highest;
    private List<Double> climaxIndexes;
    private List<Double> standards;
    private double peak;
    private double base;

    public GoldenSectionClimax(Collection<NoteRange> ranges) {

        TreeSet<NoteRange> sortedRanges = new TreeSet<>(ranges);
        this.lowest = sortedRanges.first();
        this.highest = sortedRanges.last();
    }

    /**
     * Golden Section Style is not about single SketchNodes.
     *
     * @param sketchNode
     * @return Always true.
     */
    @Override
    public boolean qualifySketchNode(SketchNode sketchNode) {

        return true;
    }

    @Override
    public double rateComposition(Composition composition) {

//        for (int i = 0; i < composition.getSize(); i++) {
//            double standard = getStandard(composition, i);
//            double score = standard
//                    * Math.abs(climaxIndexes.get(i) - standard);
//            scores.add(score);
//            base += standard * peak;
//        }
//        System.out.println("rateComposition");
        this.updateClimaxIndexes(composition);
        double sum = IntStream.range(0, composition.getSize())
                .mapToDouble(i
                        -> Math.abs(climaxIndexes.get(i) - this.standards.get(i)) * this.standards.get(i))
                //                .peek(System.out::println)
                .sum();
        return (base - sum) / base;
    }

    public void updateClimaxIndexes(Composition composition) {

        this.climaxIndexes = composition
                .getRenderedChecked("GoldenSectionClimax::rateComposition")
                .stream()
                .map(this::climaxIndex)
                .collect(Collectors.toList());
        this.peak = climaxIndexes.stream()
                .mapToDouble(s -> s)
                .max().orElse(0.0);
        this.base = 0.0;
        this.standards = IntStream.range(0, composition.getSize())
                .mapToDouble(i -> this.getStandard(composition, i))
                .peek(s -> this.base += s * peak)
                .boxed()
                .collect(Collectors.toList());
    }

    public double getStandard(Composition composition, int i) {

        if (i < 0 || i > composition.getSize() - 1) {
            return 0.0;
        }
        long peakNodeIndex = Math.round((composition.getSize() - 1) / RATIO);
        return (i < peakNodeIndex)
                ? i * peak / peakNodeIndex
                : (composition.getSize() - i - 1) * peak
                / (composition.getSize() - peakNodeIndex - 1);
    }

    public double climaxIndex(SketchNode node) {

        DoubleAdder index = new DoubleAdder();
        node.getMats().forEach((mt, mm) -> {
            double mti = 0.0;
            switch (mt) {
                case DYNAMICS:
                    mti = ((Dynamics) mm).getAvgIntensityIndex(Intensity::getIntensityIndex);
                    break;
                case NOTE_RANGES:
                    mti = ((NoteRanges) mm).getAvgIntensityIndex(mat
                            -> NoteRanges.getIntensityIndex(mat, lowest, highest));
                    break;
                case PITCH_SETS:
                    mti = ((PitchSets) mm).getAvgIntensityIndex(PitchSets::getIntensityIndex);
                    break;
                case RHYTHMIC_POINTS:
                    mti = ((RhythmicPoints) mm).getAvgIntensityIndex(mat
                            -> 1.0 * mat / DEFAULT_MAX_RHYTHMIC_POINTS.getInt());
                    break;
                default:
            }
//            System.out.println(mt + ":" + mti);
            index.add(mti);
        });
        return index.doubleValue() / node.getMats().size();
    }

    /*
     * Default setters and getters.
     */
    public List<Double> getClimaxIndexes() {
        return climaxIndexes;
    }

    public void setClimaxIndexes(List<Double> climaxIndexes) {
        this.climaxIndexes = climaxIndexes;
    }

    public List<Double> getStandards() {
        return standards;
    }

    public void setStandards(List<Double> standards) {
        this.standards = standards;
    }

    public double getPeak() {
        return peak;
    }

    public void setPeak(double peak) {
        this.peak = peak;
    }

    @Override
    public <M extends MusicMaterial> void matInitializer(M m) {
    }

}
