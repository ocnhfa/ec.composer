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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;
import tech.metacontext.ec.prototype.composer.Composition;
import tech.metacontext.ec.prototype.composer.SketchNode;
import tech.metacontext.ec.prototype.composer.connectors.Connector;
import tech.metacontext.ec.prototype.composer.materials.enums.Range;
import tech.metacontext.ec.prototype.composer.materials.enums.MaterialType;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class UnaccompaniedCello implements Style {

    /**
     * 音域
     */
    public static final Map<Range, Double> RANGE = new HashMap<>();

    static {
        RANGE.put(Range.C2, 1.0);
        RANGE.put(Range.C3, 1.0);
        RANGE.put(Range.C4, 1.0);
        RANGE.put(Range.C5, 0.5);
        RANGE.put(Range.C6, 0.25);
    }

    public static void main(String[] args) {
        UnaccompaniedCello style = new UnaccompaniedCello();
        Stream.generate(SketchNode::new)
                .limit(50)
                .peek(node -> System.out.println(node.getMat(MaterialType.NoteRanges)))
                .map(style::qualifySketchNode)
                .forEach(System.out::println);
    }

    @Override
    public boolean qualifySketchNode(SketchNode sketchNode) {

        return sketchNode.getMat(MaterialType.NoteRanges)
                .getMaterials()
                .stream()
                .allMatch(range
                        -> RANGE.containsKey((Range) range)
                /*...*/ && RANGE.get((Range) range) > Math.random());
    }

    @Override
    public double rateComposition(Composition composition) {

        return composition.getRendered().stream()
                .allMatch(this::qualifySketchNode) ? 1.0 : 0.0;
    }

}
