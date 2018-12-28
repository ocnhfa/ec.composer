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

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import tech.metacontext.ec.prototype.composer.TestCenter;
import tech.metacontext.ec.prototype.composer.model.Composition;

/**
 *
 * @author Jonathan
 */
public class GoldenSectionClimaxTest {

    GoldenSectionClimax instance;
    static TestCenter tc;

    @BeforeClass
    public static void prepare() {
        tc = TestCenter.getInstance();
    }

    @Test
    public void testClimaxIndex() {

        System.out.println("climaxIndex");
        tc.getComposer().getPopulation().stream()
                .limit(1)
                .peek(System.out::println)
                .map(Composition::getRenderedChecked)
                .forEach(list -> {
                    list.stream()
                            .mapToDouble(instance::climaxIndex)
                            .forEach(score -> {
                                System.out.printf("%.1f ", score);
                                assertTrue(score <= 2.0);
                            });
                    System.out.println("");
                });
    }

    @Test
    public void testRateComposition() {

        System.out.println("rateComposition");
        tc.getComposer().getPopulation().stream()
                .peek(composition -> {
//                    System.out.println(composition.getId());
                    composition.getRenderedChecked().stream()
                            .mapToDouble(instance::climaxIndex)
                            .forEach(score -> {
                                System.out.printf("%.1f ", score);
                            });
                    System.out.println();
                })
                .mapToDouble(instance::rateComposition)
                .forEach(System.out::println);
    }

}
