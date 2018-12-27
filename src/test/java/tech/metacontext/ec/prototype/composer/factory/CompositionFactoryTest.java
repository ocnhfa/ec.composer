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
package tech.metacontext.ec.prototype.composer.factory;

import tech.metacontext.ec.prototype.composer.model.Composer;
import tech.metacontext.ec.prototype.composer.model.Composition;
import java.util.Objects;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import static tech.metacontext.ec.prototype.composer.Settings.RENEW_TEST;
import tech.metacontext.ec.prototype.composer.enums.ComposerAim;
import tech.metacontext.ec.prototype.composer.styles.GoldenSectionClimax;
import tech.metacontext.ec.prototype.composer.styles.UnaccompaniedCello;

/**
 *
 * @author Jonathan
 */
public class CompositionFactoryTest {

    static CompositionFactory instance;
    static Composer composer;
    Composition composition;

    @BeforeClass
    public static void prepare() throws Exception {

        instance = CompositionFactory.getInstance();
        composer = new Composer(10, ComposerAim.Phrase, RENEW_TEST,
                new UnaccompaniedCello(),
                new GoldenSectionClimax(UnaccompaniedCello.RANGE.keySet())
        );
        do {
            composer.compose().evolve();
        } while (!composer.getPopulation().stream().allMatch(composer.getAim()::completed));
    }

    @Test
    public void testGetInstance() {
    }

    @Test
    public void testForArchiving() {

        composer.getPopulation().stream().forEach(c -> {
            c.ifReRenderRequired();
            c.getEval().getScores().entrySet()
                    .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
            Composition result = instance.forArchiving(c);
            result.getEval().getScores().entrySet()
                    .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
            composer.getStyles().stream().forEach(s -> {
                assertEquals(c.getScore(s), result.getScore(s));
            });
        });
    }

    @Test
    public void testForMutation() {

        composer.getPopulation().stream().forEach(c -> {
            c.ifReRenderRequired();
            c.getEval().getScores().entrySet()
                    .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
            Composition result = instance.forMutation(c);
            result.getEval().getScores().entrySet()
                    .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
            assertFalse(composer.getStyles().stream()
                    .allMatch(s -> Objects.equals(c.getScore(s), result.getScore(s))));
        });
    }

    @Test
    public void testNewInstance() {
    }

    @Test
    public void testForCrossover() {
    }

}
