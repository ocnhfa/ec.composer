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

import java.util.Random;
import tech.metacontext.ec.prototype.abs.Individual;
import tech.metacontext.ec.prototype.composer.Composition;
import tech.metacontext.ec.prototype.composer.SketchNode;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class GoldenSectionClimax implements Style {

    public static final double RATIO = 1.6180339887498948482;

    public static double intensityIndex() {

        //@todo
        return 0.0;
    }

    public static double climaxEval(Composition c) {

        //@todo
        return 0.0;
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

    /**
     * @todo: dummy
     * @param composition
     * @return
     */
    @Override
    public double rateComposition(Composition composition) {

        return Math.random();
    }

}
