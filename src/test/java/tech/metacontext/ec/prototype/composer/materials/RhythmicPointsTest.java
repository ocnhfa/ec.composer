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
package tech.metacontext.ec.prototype.composer.materials;

import java.util.stream.Stream;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import tech.metacontext.ec.prototype.composer.enums.TransformType;

/**
 *
 * @author Jonathan
 */
public class RhythmicPointsTest {

    public RhythmicPointsTest() {
    }

    @Test
    public void copyObject() {

        RhythmicPoints rp1 = Stream.generate(RhythmicPoints::new)
                .filter(rp -> rp.size() == 4)
                .findFirst()
                .get(),
                rp2 = rp1,
                rp3 = new RhythmicPoints(rp1);
        rp1.setMaterials(rp1.transform(TransformType.Retrograde).getMaterials());
        assertEquals(rp1.toString(), rp2.toString());
        assertNotEquals(rp1.toString(), rp3.toString());
    }

    @Test
    public void testTransform() {
        RhythmicPoints rp1 = Stream.generate(RhythmicPoints::new)
                .filter(rp -> rp.size() == 4)
                .findFirst()
                .get();
        System.out.printf("Original    : %s\n", rp1);
        System.out.printf("Repetition  : %s\n", rp1.transform(TransformType.Repetition));
        System.out.printf("MoveForward : %s\n", rp1.transform(TransformType.MoveForward));
        System.out.printf("MoveBackward: %s\n", rp1.transform(TransformType.MoveBackward));
        System.out.printf("Retrograde  : %s\n", rp1.transform(TransformType.Retrograde));
        System.out.printf("Disconnected: %s\n", rp1.transform(TransformType.Disconnected));
    }

    @Test
    @Ignore
    public void testReset() {
    }

    @Test
    @Ignore
    public void testGenerate() {
    }

    @Test
    @Ignore
    public void testRandom() {
    }

    @Test
    @Ignore
    public void testToString() {
    }

    @Test
    @Ignore
    public void testGetMin() {
    }

    @Test
    @Ignore
    public void testSetMin() {
    }

    @Test
    @Ignore
    public void testGetMax() {
    }

    @Test
    @Ignore
    public void testSetMax() {
    }

}
