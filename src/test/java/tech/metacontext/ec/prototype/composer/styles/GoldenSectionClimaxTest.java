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

import org.junit.jupiter.api.*;
import static tech.metacontext.ec.prototype.composer.Parameters.*;
import tech.metacontext.ec.prototype.composer.TestCenter;

/**
 *
 * @author Jonathan
 */
public class GoldenSectionClimaxTest {

    static GoldenSectionClimax instance;
    static TestCenter tc;

    @BeforeAll
    public static void prepare() {
        instance = new GoldenSectionClimax(UnaccompaniedCello.getRange());
        tc = TestCenter.getInstance();
    }

    @Test
    public void testClimaxIndex() {

        System.out.println("climaxIndex");
        tc.getComposer().getConservatory().keySet().stream()
                .flatMap(c -> c.getRendered().stream())
                .map(instance::climaxIndex)
                .forEach(System.out::println);
//                .map(s -> s > 0.0 && s < 1.0)
//                .forEach(Assert::assertTrue);
    }

    @Test
    public void testRateComposition() {

        System.out.println("rateComposition");
        tc.getComposer().getConservatory().keySet().stream()
                .peek(c -> c.getRenderedChecked(
                this.getClass().getSimpleName() + "::testRateComposition"))
                .map(instance::rateComposition)
                .map(score -> score >= tc.getComposer().getConserve_score())
                .forEach(Assertions::assertTrue);
    }

}
