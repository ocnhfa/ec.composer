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
import tech.metacontext.ec.prototype.composer.connectors.Connector;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import tech.metacontext.ec.prototype.composer.TestCenter;
import tech.metacontext.ec.prototype.composer.factory.SketchNodeFactory;
import tech.metacontext.ec.prototype.composer.styles.FreeStyle;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class CompositionTest {

    static SketchNodeFactory sketchNodeFactory;
    static CompositionFactory compositionFactory;
    static TestCenter tc;

    @BeforeAll
    public static void prepare() {
        tc = TestCenter.getInstance();
        compositionFactory = CompositionFactory.getInstance(tc.getComposer().getId());
        sketchNodeFactory = SketchNodeFactory.getInstance();
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
     * Test of addConnect method, of class Composition.
     */
    @Test
    @Disabled
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
    @Disabled
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
    @Disabled
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
    @Disabled
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
    @Disabled
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
    @Disabled
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
    @Disabled
    public void testSetSeed() {
        System.out.println("setSeed");
        SketchNode seed = null;
        Composition instance = null;
        instance.resetSeed(seed);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
