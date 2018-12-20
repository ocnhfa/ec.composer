/*
 * Copyright 2018 Jonathan.
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

import java.util.stream.IntStream;
import org.junit.Test;
import static org.junit.Assert.*;
import tech.metacontext.ec.prototype.composer.Composer;
import tech.metacontext.ec.prototype.composer.enums.ComposerAim;

/**
 *
 * @author Jonathan
 */
public class GoldenSectionClimaxTest {

    GoldenSectionClimax instance;
    Composer composer;

    public GoldenSectionClimaxTest() {
        composer = new Composer(10, ComposerAim.Phrase,
                instance = new GoldenSectionClimax(UnaccompaniedCello.RANGE.keySet()),
                new UnaccompaniedCello());
        IntStream.range(0, 20)
                //                .peek(System.out::println)
                .forEach(i -> composer.compose().evolve());
//        System.out.println(composer.getConservetory().size());
    }

    @Test
    public void testClimaxIndex() {
        System.out.println("climaxIndex");
    }

    @Test
    public void testQualifySketchNode() {
    }

    @Test
    public void testRateComposition() {
    }

}
