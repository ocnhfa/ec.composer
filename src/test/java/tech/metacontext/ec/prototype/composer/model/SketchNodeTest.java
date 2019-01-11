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
package tech.metacontext.ec.prototype.composer.model;

import tech.metacontext.ec.prototype.composer.factory.SketchNodeFactory;
import java.io.IOException;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import tech.metacontext.ec.prototype.composer.enums.MaterialType;
import static tech.metacontext.ec.prototype.composer.enums.MaterialType.*;
import tech.metacontext.ec.prototype.composer.materials.MusicMaterial;


/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class SketchNodeTest {

    static SketchNodeFactory sketchNodeFactory;

    public SketchNodeTest() throws IOException {

        sketchNodeFactory = SketchNodeFactory.getInstance();
    }

    @Test
    public void copyObject() {
        SketchNode node1 = sketchNodeFactory.newInstance(),
                node2 = node1,
                node3 = sketchNodeFactory.forArchiving(node1);
        node1.getMat(PITCH_SETS).random();
        assertEquals(node1, node2);
        assertEquals(node1, node3);
        assertEquals(node1.getMat(PITCH_SETS),
                node2.getMat(PITCH_SETS));
        assertNotEquals(node1.getMat(PITCH_SETS),
                node3.getMat(PITCH_SETS));
    }

    /**
     * Test of getMat method, of class SketchNode.
     */
    @Test
    @Disabled
    public void testGetMat() {
        System.out.println("getMat");
        MaterialType type = null;
        SketchNode instance = new SketchNode();
        MusicMaterial expResult = null;
        MusicMaterial result = instance.getMat(type);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMats method, of class SketchNode.
     */
    @Test
    @Disabled
    public void testGetMats() {
        System.out.println("getMats");
        SketchNode instance = new SketchNode();
        Map<MaterialType, ? extends MusicMaterial> expResult = null;
        Map<MaterialType, ? extends MusicMaterial> result = instance.getMats();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMats method, of class SketchNode.
     */
    @Test
    @Disabled
    public void testSetMats() {
        System.out.println("setMats");
        Map<MaterialType, MusicMaterial> mats = null;
        SketchNode instance = new SketchNode();
        instance.setMats(mats);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class SketchNode.
     */
    @Test
    @Disabled
    public void testToString() {
        System.out.println("toString");
        SketchNode instance = new SketchNode();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
