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
    Composition composition;
    Composer composer;

    public CompositionFactoryTest() {
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
        Map<Style, Double> rating1 = new HashMap<>(),
                rating2 = new HashMap<>();
        boolean quit = false;
        do {
            composer.compose().evolve();
            composition = composer.randomSelect(Composer.SELECT_ONLY_COMPLETED);
            if (Objects.nonNull(composition)) {
                rating1.clear();
                rating2.clear();
                composer.getStyles().stream()
                        .map(s -> new SimpleEntry<>(s, s.rateComposition(composition)))
                        .forEach(e -> rating1.put(e.getKey(), e.getValue()));
                rating1.forEach((s, rate)
                        -> System.out.println(s.getClass().getSimpleName() + ": " + rate));
                Composition result = instance.forArchiving(composition);
                composer.getStyles().stream()
                        .map(s -> new SimpleEntry<>(s, s.rateComposition(result)))
                        .forEach(e -> rating2.put(e.getKey(), e.getValue()));
                rating2.forEach((s, rate)
                        -> System.out.println(s.getClass().getSimpleName() + ": " + rate));
                rating1.entrySet().stream().forEach(e_rating1 -> {
                    assertEquals(e_rating1.getValue(), rating2.get(e_rating1.getKey()));
                });
                quit = rating1.values().stream().allMatch(r -> r > Composer.CONSERVE_SCORE);
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
