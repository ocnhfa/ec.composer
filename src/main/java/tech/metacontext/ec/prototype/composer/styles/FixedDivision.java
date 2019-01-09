/*
 * Copyright 2019 Jonathan.
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

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import tech.metacontext.ec.prototype.composer.factory.SketchNodeFactory;
import tech.metacontext.ec.prototype.composer.materials.MusicMaterial;
import tech.metacontext.ec.prototype.composer.model.Composition;
import tech.metacontext.ec.prototype.composer.model.SketchNode;

/**
 *
 * @author Jonathan
 */
public class FixedDivision extends Style {

    public static void main(String[] args) {
        var instance = new FixedDivision();
        Stream.generate(SketchNodeFactory.getInstance()::newRandomInstance)
                .peek(node
                        -> System.out.println(node.getMats().values().stream()
                        .map(n -> "" + n.getDivision())
                        .collect(Collectors.joining(" "))))
                .filter(instance::qualifySketchNode)
                .limit(1)
                .forEach(node -> System.out.println(instance.qualifySketchNode(node)));
    }

    @Override
    public boolean qualifySketchNode(SketchNode sketchNode) {

        AtomicInteger c = new AtomicInteger();
        return sketchNode.getMats().values().stream()
                .mapToInt(MusicMaterial::getDivision)
                .peek(div -> c.compareAndExchange(0, div))
                .allMatch(div -> c.compareAndSet(div, div));
    }

    @Override
    public double rateComposition(Composition composition) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
