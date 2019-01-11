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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import tech.metacontext.ec.prototype.composer.model.Composition;
import tech.metacontext.ec.prototype.composer.model.SketchNode;
import tech.metacontext.ec.prototype.composer.enums.mats.NoteRange;
import tech.metacontext.ec.prototype.composer.enums.MaterialType;
import tech.metacontext.ec.prototype.composer.materials.NoteRanges;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class UnaccompaniedCello extends Style {

    /**
     * 音域
     */
    public static final Map<NoteRange, Double> RANGE = new HashMap<>();

    static {
        RANGE.put(NoteRange.C2, 1.0);
        RANGE.put(NoteRange.C3, 1.0);
        RANGE.put(NoteRange.C4, 1.0);
        RANGE.put(NoteRange.C5, 0.5);
        RANGE.put(NoteRange.C6, 0.25);
    }

    @Override
    public boolean qualifySketchNode(SketchNode sketchNode) {

        return ((NoteRanges) sketchNode.getMat(MaterialType.NOTE_RANGES))
                .getMaterials()
                .stream()
                .flatMap(set -> set.getNoteRange_set().stream())
                .filter(RANGE::containsKey)
                .map(RANGE::get)
                .allMatch(chance -> chance > Math.random());
    }

    @Override
    public double rateComposition(Composition composition) {

        if (composition.getRenderedChecked(this.getClass().getSimpleName() + "::rateComposition")
                .stream()
                .map(node -> ((NoteRanges) node.getMat(MaterialType.NOTE_RANGES)))
                .map(mm -> mm.getMaterials())
                .flatMap(lnrs -> lnrs.stream())
                .flatMap(nrs -> nrs.getNoteRange_set().stream())
                .allMatch(RANGE::containsKey)) {
            return 1.0;
        }
        return 0.0;
    }

    public static Collection<NoteRange> getRange() {

        return RANGE.keySet();
    }
}
