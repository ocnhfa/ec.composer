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
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import tech.metacontext.ec.prototype.composer.materials.RhythmicPoints;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class GoldenSectionClimax extends Style {

    public static final double RATIO = 1.6180339887498948482;

    private final LinkedList<Range> sortedRanges;

    public GoldenSectionClimax(Collection<Range> ranges) {

        this.sortedRanges = new LinkedList<>(ranges);
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

        List<Double> climaxIndexes = composition
                .getRenderedChecked(this.getClass().getSimpleName() + "::rateComposition")
                .stream()
                .map(this::climaxIndex)
                .collect(Collectors.toList());
        Double peak = climaxIndexes.stream()
                .max(Comparator.naturalOrder())
                .get();
        int peakNodeIndex = (int) Math.floor(composition.getSize() / RATIO) + 1;
        List<Double> scores = new ArrayList<>();
        double base = 0.0;
        for (int i = 0; i < composition.getSize(); i++) {
            double standard = (i < peakNodeIndex)
                    ? i * peak / peakNodeIndex
                    : (composition.getSize() - i - 1) * peak
                    / (composition.getSize() - peakNodeIndex - 1);
            double score = standard
                    * Math.abs(climaxIndexes.get(i) - standard);
            scores.add(score);
            base += standard * peak;
        }
        double sum = scores.stream().mapToDouble(d -> d).sum();
        return (base - sum) / base;
    }

    public double climaxIndex(SketchNode node) {

        DoubleAdder index = new DoubleAdder();
        node.getMats().forEach((MaterialType mt, MusicMaterial mm) -> {
            switch (mt) {
                case Dynamics:
                    addIndex(index, mm, (mat)
                            -> ((Intensity) mat).getIntensityIndex());
                    break;
                case NoteRanges:
                    addIndex(index, mm, (mat)
                            -> ((Range) mat).getIntensityIndex(
                                    sortedRanges.getFirst(),
                                    sortedRanges.getLast()));
                    break;
                case PitchSets:
                    break;
                case RhythmicPoints:
                    addIndex(index, mm, (mat)
                            -> 1.0 * (int) mat * mm.getDivision() / RhythmicPoints.DEFAULT_MAX_POINTS);
                    break;
                default:
            }
        });
        return index.doubleValue();
    }

    private void addIndex(DoubleAdder index, MusicMaterial mm,
            ToDoubleFunction<Object> mapper) {
        index.add(mm.getMaterials().stream()
                .mapToDouble(mapper)
                .average()
                .getAsDouble());
    }
}
