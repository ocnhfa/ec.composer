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

import tech.metacontext.ec.prototype.composer.enums.TransformType;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class TransformTypeTest {

    public TransformTypeTest() {
    }

    /**
     * Test of getRandom method, of class TransformType.
     */
    @Test
    public void testGetRandom() {
        System.out.println("getRandom");
//        TransformType expResult = null;
        Stream.generate(TransformType::getRandom)
                .limit(20)
                .forEach(System.out::println);
//        assertEquals(expResult, result);
    }

}
