/*
 * Copyright 2018 Jonathan Chang, Chun-yien <ccy@musicapoetica.org>.
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
package tech.metacontext.ec.prototype.composer.rules;

import org.junit.Test;
import static org.junit.Assert.*;
import tech.metacontext.ec.prototype.composer.materials.PitchSets;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class SmoothPitchSetTest {

    PitchSets p1;
    PitchSets p2;
    SmoothPitchSets instance;

    public SmoothPitchSetTest() {

        p1 = new PitchSets();
        p2 = new PitchSets();
        instance = new SmoothPitchSets(p1, p2);
    }

    @Test
    public void main() {

        System.out.println("main");
        for (int i = 0; i < 5; i++) {
            SmoothPitchSets mps = new SmoothPitchSets(p1, p2);
            System.out.printf(
                    "%s(%d)\n"
                    + "%s(%d)\n"
                    + "rating = %.2f%%\n",
                    p1.getMaterials(), p1.size(),
                    p2.getMaterials(), p2.size(),
                    mps.rating() * 100);
        }
    }

}
