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
package tech.metacontext.ec.prototype.composer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.logging.FileHandler;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import tech.metacontext.ec.prototype.composer.materials.MusicMaterial;
import tech.metacontext.ec.prototype.composer.materials.enums.MaterialType;

/**
 *
 * @author Jonathan Chang
 */
public class SketchNodeTest {

    static String logfile = "src/main/resources/log/test/"
            + LocalDateTime.now().toString().replace(":", "-") + ".log";
    static SketchNodeFactory sketchNodeFactory;

    public SketchNodeTest() throws IOException {

        FileHandler fh = new FileHandler(logfile);
        sketchNodeFactory = SketchNodeFactory.getInstance(fh);
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void copyObject() {
        SketchNode node1 = sketchNodeFactory.newInstance(),
                node2 = node1,
                node3 = sketchNodeFactory.forArchiving(node1);
        node1.getMat(MaterialType.PitchSets).random();
        assertEquals(node1, node2);
        assertEquals(node1, node3);
        assertEquals(node1.getMat(MaterialType.PitchSets),
                node2.getMat(MaterialType.PitchSets));
        assertNotEquals(node1.getMat(MaterialType.PitchSets),
                node3.getMat(MaterialType.PitchSets));
    }

    /**
     * Test of getMat method, of class SketchNode.
     */
    @Test
    @Ignore
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
    @Ignore
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
    @Ignore
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
    @Ignore
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
