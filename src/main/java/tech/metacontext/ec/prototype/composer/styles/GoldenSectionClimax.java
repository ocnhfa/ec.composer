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
import tech.metacontext.ec.prototype.composer.enums.MaterialType;
import tech.metacontext.ec.prototype.composer.materials.MusicMaterial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import tech.metacontext.ec.prototype.composer.enums.ComposerAim;
import tech.metacontext.ec.prototype.composer.factory.SketchNodeFactory;
import tech.metacontext.ec.prototype.composer.materials.RhythmicPoints;
import tech.metacontext.ec.prototype.composer.materials.*;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class GoldenSectionClimax extends Style {

    public static final double RATIO = 1.6180339887498948482;

    public final Range lowest, highest;
    List<Double> climaxIndexes;
    double peak;

    public GoldenSectionClimax(Collection<Range> ranges) {

        TreeSet<Range> sortedRanges = new TreeSet<>(ranges);
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

        updateClimaxIndexes(composition);
        List<Double> scores = new ArrayList<>();
        double base = 0.0;
        for (int i = 0; i < composition.getSize(); i++) {
            double standard = getStandard(composition, i);
            double score = standard
                    * Math.abs(climaxIndexes.get(i) - standard);
            scores.add(score);
            base += standard * peak;
        }
        double sum = scores.stream().mapToDouble(d -> d).sum();
        return (base - sum) / base;
    }

    public void updateClimaxIndexes(Composition composition) {

        this.climaxIndexes = composition
                .getRenderedChecked("GoldenSectionClimax::rateComposition")
                .stream()
                .map(this::climaxIndex)
                .collect(Collectors.toList());
        this.peak = climaxIndexes.stream()
                .max(Comparator.naturalOrder())
                .get();
    }

    public double getStandard(Composition composition, int index) {

        int peakNodeIndex = (int) Math.floor(composition.getSize() / RATIO) + 1;
        return (index < peakNodeIndex)
                ? index * peak / peakNodeIndex
                : (composition.getSize() - index - 1) * peak
                / (composition.getSize() - peakNodeIndex - 1);
    }

    public double climaxIndex(SketchNode node) {

        DoubleAdder index = new DoubleAdder();
        node.getMats().forEach((MaterialType mt, MusicMaterial mm) -> {
            double mti = 0.0;
            switch (mt) {
                case Dynamics:
                    mti = ((Dynamics) mm).getAverageIntensityIndex(Intensity::getIntensityIndex);
                    break;
                case NoteRanges:
                    mti = ((NoteRanges) mm).getAverageIntensityIndex(
                            mat -> Range.getIntensityIndex(mat, lowest, highest));
                    break;
                case PitchSets:
                    mti = ((PitchSets) mm).getAverageIntensityIndex(PitchSet::getIntensityIndex);
                    break;
                case RhythmicPoints:
                    mti = ((RhythmicPoints) mm).getAverageIntensityIndex(
                            mat -> 1.0 * mat / RhythmicPoints.DEFAULT_MAX_POINTS);
                    break;
                default:
            }
//            System.out.println(mt + ":" + mti);
            index.add(mti);
        });
        return index.doubleValue();
    }

    public static void main(String[] args) throws Exception {
        var gsc = new GoldenSectionClimax(UnaccompaniedCello.getRange());
        var composer = new Composer(1, ComposerAim.Movement, 0, new UnaccompaniedCello(), gsc);
        Stream.generate(() -> SketchNodeFactory.getInstance().newInstance(composer.styleChecker))
                .limit(50)
                .peek(System.out::println)
                .map(gsc::climaxIndex)
                .forEach(System.out::println);

    }
}
