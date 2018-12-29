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

import tech.metacontext.ec.prototype.composer.model.Composition;
import java.util.Objects;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import tech.metacontext.ec.prototype.composer.TestCenter;

/**
 *
 * @author Jonathan
 */
public class CompositionFactoryTest {

    static CompositionFactory instance;
    static TestCenter tc;

    @BeforeClass
    public static void prepare() {
        tc = TestCenter.getInstance();
        instance = CompositionFactory.getInstance(tc.getComposer().getId());
    }

    @Test
    public void testGetInstance() {
    }

    @Test
    public void testForArchiving() {

        System.out.println("forArchiving");
        tc.getComposer().getPopulation().stream().forEach(c -> {
            c.ifReRenderRequired();
            System.out.printf("Composition %s from population.\n", c.getId_prefix());
            c.getEval().getScores().entrySet()
                    .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
            Composition result = instance.forArchiving(c);
            System.out.printf("Composition %s for Archiving.\n", result.getId_prefix());
            result.getEval().getScores().entrySet()
                    .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
            tc.getComposer().getStyles().stream().forEach(s -> {
                assertEquals(c.getScore(s), result.getScore(s));
            });
        });
    }

    @Test
    public void testForMutation() {

        System.out.println("forMutation");
        tc.getComposer().getPopulation().stream().forEach(c -> {
            c.ifReRenderRequired();
            c.getEval().getScores().entrySet()
                    .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
            Composition result = instance.forMutation(c);
            result.getEval().getScores().entrySet()
                    .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
            assertFalse(tc.getComposer().getStyles().stream()
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
