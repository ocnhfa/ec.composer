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

import tech.metacontext.ec.prototype.composer.factory.CompositionFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import tech.metacontext.ec.prototype.composer.connectors.Connector;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Ignore;
import tech.metacontext.ec.prototype.composer.TestCenter;
import tech.metacontext.ec.prototype.composer.factory.SketchNodeFactory;
import tech.metacontext.ec.prototype.composer.styles.FreeStyle;

/**
 *
 * @author Jonathan Chang
 */
public class CompositionTest {

    static SketchNodeFactory sketchNodeFactory;
    static CompositionFactory compositionFactory;
    static TestCenter tc;

    @BeforeClass
    public static void prepare() {
        tc = TestCenter.getInstance();
        compositionFactory = CompositionFactory.getInstance();
        sketchNodeFactory = SketchNodeFactory.getInstance();
    }

    /**
     * Test of getRendered method, of class Composition.
     */
    @Test
    public void testGetRendered() throws IOException {

        System.out.println("getRendered");
        tc.getComposer().compose();
        System.out.println("1 compose");
        Composition composition = tc.getComposer().getPopulation().get(0);
        SketchNode expResult =sketchNodeFactory.newInstance(tc.getComposer().styleChecker);
        composition.setSeed(expResult);
        assertTrue(composition.ifReRenderRequired());
        SketchNode result = composition.getRenderedChecked().get(0);
        assertEquals(expResult, result);
        tc.getComposer().compose();
        System.out.println("2 compose");
        composition = tc.getComposer().getPopulation().get(0);
        assertTrue(composition.ifReRenderRequired());
        result = composition.getRenderedChecked().get(0);
        assertEquals(expResult, result);
        tc.getComposer().compose().evolve();
        System.out.println("3 compose and evolve");
        composition = tc.getComposer().getPopulation().get(0);
        assertTrue(composition.ifReRenderRequired());
        result = composition.getRenderedChecked().get(0);
        assertNotEquals(expResult, result);
        tc.getComposer().compose().evolve();
        System.out.println("4 compose and evolve");
        composition = tc.getComposer().getPopulation().get(0);
        assertTrue(composition.ifReRenderRequired());
        result = composition.getRenderedChecked().get(0);
        assertNotEquals(expResult, result);
        Path path = composition.persistent();
        Files.delete(path);
    }

    /**
     * Test of render method, of class Composition.
     */
    @Test
    public void testRender() {

        System.out.println("render");
        for (int i = 0; i < 1; i++) {
            tc.getComposer().compose().evolve();
        }
        Composition instance = tc.getComposer().getPopulation().get(0);
        System.out.println(instance);
        List<SketchNode> list = instance.render();
        list.forEach(System.out::println);

    }

    @Test
    public void ObjectCopy() {

        System.out.println("ObjectCopy");
        Composition c1 = compositionFactory.newInstance(
                tc.getComposer().styleChecker,
                tc.getComposer().getStyles()),
                c2 = c1,
                c3 = compositionFactory.forArchiving(c1);
        c1.elongation(FreeStyle::checker);
        assertEquals(
                c1.getConnectors().size(),
                c2.getConnectors().size());
        assertNotEquals(
                c1.getConnectors().size(),
                c3.getConnectors().size());
    }

    /**
     * Test of elongation method, of class Composition.
     */
    @Test
    @Ignore
    public void testElongation() {
        System.out.println("elongation");
        Composition instance = null;
        Composition expResult = null;
        Composition result = instance.elongation((t) -> true);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addConnect method, of class Composition.
     */
    @Test
    @Ignore
    public void testAddConnect() {
        System.out.println("addConnect");
        Connector connector = null;
        Composition instance = null;
        instance.addConnector(connector);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Composition.
     */
    @Test
    @Ignore
    public void testToString() {
        System.out.println("toString");
        Composition instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getConnectors method, of class Composition.
     */
    @Test
    @Ignore
    public void testGetConnectors() {
        System.out.println("getConnectors");
        Composition instance = null;
        LinkedList<Connector> expResult = null;
        LinkedList<Connector> result = instance.getConnectors();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addConnector method, of class Composition.
     */
    @Test
    @Ignore
    public void testAddConnector() {
        System.out.println("addConnector");
        Connector connector = null;
        Composition instance = null;
        instance.addConnector(connector);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSize method, of class Composition.
     */
    @Test
    @Ignore
    public void testGetSize() {
        System.out.println("getSize");
        Composition instance = null;
        int expResult = 0;
        int result = instance.getSize();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSeed method, of class Composition.
     */
    @Test
    @Ignore
    public void testGetSeed() {
        System.out.println("getSeed");
        Composition instance = null;
        SketchNode expResult = null;
        SketchNode result = instance.getSeed();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSeed method, of class Composition.
     */
    @Test
    @Ignore
    public void testSetSeed() {
        System.out.println("setSeed");
        SketchNode seed = null;
        Composition instance = null;
        instance.resetSeed(seed);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
