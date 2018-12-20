/*
 * Copyright 2018 Jonathan Chang.
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
package tech.metacontext.ec.prototype.composer.materials.enums;

import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jonathan Chang
 */
public class RangeTest {

    public RangeTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getIntensityIndex method, of class Range.
     */
    @Test
    public void testGetIntensityIndex() {
        System.out.println("getIntensityIndex");
        Range lowest = Range.C1;
        Range highest = Range.C4;
        Arrays.asList(Range.values()).stream()
                .forEach(instance -> {
                    double result = instance.getIntensityIndex(lowest, highest);
                    double expResult = 1.0;
                    System.out.println(instance + "=" + result);
                    assertTrue(result <= expResult);
                });
    }

}
