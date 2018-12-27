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
import tech.metacontext.ec.prototype.composer.enums.mats.TransformType;

/**
 *
 * @author Jonathan
 */
public class NoteRangesTest {

    public NoteRangesTest() {
    }

    @Test
    public void copyObject() {

        NoteRanges nr1 = Stream.generate(NoteRanges::new)
                .filter(rp -> rp.size() == 4)
                .findFirst()
                .get(),
                nr2 = nr1,
                nr3 = new NoteRanges(nr1);
        nr1.setMaterials(nr1.transform(TransformType.Retrograde).getMaterials());
        System.out.println(nr1);
        System.out.println(nr2);
        System.out.println(nr3);
        assertEquals(nr1.toString(), nr2.toString());
        assertNotEquals(nr1.toString(), nr3.toString());
    }

    @Test
    public void testTransform() {
        
        System.out.println("testTransform");
        NoteRanges nr1 = Stream.generate(NoteRanges::new)
                .filter(nr -> nr.size() == 4)
                .findFirst()
                .get();
        System.out.printf("Original    : %s\n", nr1);
        System.out.printf("Repetition  : %s\n", nr1.transform(TransformType.Repetition));
        System.out.printf("MoveForward : %s\n", nr1.transform(TransformType.MoveForward));
        System.out.printf("MoveBackward: %s\n", nr1.transform(TransformType.MoveBackward));
        System.out.printf("Retrograde  : %s\n", nr1.transform(TransformType.Retrograde));
        System.out.printf("Disconnected: %s\n", nr1.transform(TransformType.Disconnected));
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
    public void testGetLowest() {
    }

    @Test
    @Ignore
    public void testSetLowest() {
    }

    @Test
    @Ignore
    public void testGetHighest() {
    }

    @Test
    @Ignore
    public void testSetHighest() {
    }

}
