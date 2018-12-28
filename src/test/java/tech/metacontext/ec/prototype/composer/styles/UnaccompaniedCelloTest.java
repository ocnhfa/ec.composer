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

/**
 *
 * @author Jonathan
 */
public class UnaccompaniedCelloTest {

    static UnaccompaniedCello instance;
    static TestCenter tc;

    @BeforeClass
    public static void prepare() {
        tc = TestCenter.getInstance();
        instance = new UnaccompaniedCello();
    }

    @Test
    public void testRateComposition() {

        System.out.println("rateComposition");
        tc.getComposer().getConservetory().stream()
                .map(instance::rateComposition)
                .forEach(score -> assertTrue(score > 0.0));
    }

}
