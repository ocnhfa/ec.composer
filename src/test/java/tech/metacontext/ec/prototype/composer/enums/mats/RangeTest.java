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
package tech.metacontext.ec.prototype.composer.enums.mats;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class RangeTest {

    public RangeTest() {
    }

    /**
     * Test of getIntensityIndex method, of class Range.
     */
    @Test
    public void testGetIntensityIndex() {
        System.out.println("getIntensityIndex");
        SciRange lowest = SciRange.C1;
        SciRange highest = SciRange.C4;
        Arrays.asList(SciRange.values()).stream()
                .forEach(instance -> {
                    double result = SciRange.getIntensityIndex(instance, lowest, highest);
                    double expResult = 1.0;
                    System.out.println(instance + "=" + result);
                    assertTrue(result <= expResult);
                });
    }

}
