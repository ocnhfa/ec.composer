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
package tech.metacontext.ec.prototype.composer;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import tech.metacontext.ec.prototype.composer.enums.ComposerAim;
import tech.metacontext.ec.prototype.composer.styles.GoldenSectionClimax;
import tech.metacontext.ec.prototype.composer.styles.Style;
import tech.metacontext.ec.prototype.composer.styles.UnaccompaniedCello;

/**
 *
 * @author Jonathan
 */
public class CompositionFactoryTest {

    static final CompositionFactory instance = CompositionFactory.getInstance();
    static Composer composer;
    Composition composition;

    @BeforeClass
    public static void prepare() {
        composer = new Composer(10, ComposerAim.Phrase,
                new UnaccompaniedCello(),
                new GoldenSectionClimax(UnaccompaniedCello.RANGE.keySet())
        );
    }

    @Test
    public void testGetInstance() {
    }

    @Test
    public void testForArchiving() {
        boolean quit = false;
        do {
            composer.compose().evolve();
            composition = composer.randomSelect(Composer.SELECT_ONLY_COMPLETED);
            if (Objects.nonNull(composition)) {
                composition.render();
                composer.getStyles().stream().forEach(composition::getScore);
                composition.getEval().getScores().entrySet().forEach(System.out::println);
                Composition result = instance.forArchiving(composition);
                composer.getStyles().stream().forEach(result::getScore);
                result.getEval().getScores().entrySet().forEach(System.out::println);
                composer.getStyles().stream().forEach(s -> {
                    assertEquals(composition.getScore(s), result.getScore(s));
                });
                quit = composition.getEval().getScores().entrySet().stream()
                        .allMatch(e -> e.getValue() > Composer.CONSERVE_SCORE);
            }
        } while (!quit);
    }

    @Test
    public void testForMutation() {
    }

    @Test
    public void testNewInstance() {
    }

    @Test
    public void testForCrossover() {
    }

}
