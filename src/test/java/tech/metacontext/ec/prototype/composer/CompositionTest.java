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

import tech.metacontext.ec.prototype.composer.connectors.Connector;
import java.util.LinkedList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import tech.metacontext.ec.prototype.composer.connectors.ConnectorFactory;
import tech.metacontext.ec.prototype.composer.enums.ComposerAim;
import tech.metacontext.ec.prototype.composer.styles.FreeStyle;
import tech.metacontext.ec.prototype.composer.styles.GoldenSectionClimax;
import tech.metacontext.ec.prototype.composer.styles.UnaccompaniedCello;

/**
 *
 * @author Jonathan Chang
 */
public class CompositionTest {

    static CompositionFactory compositionFactory;
    static Composer composer;

    @BeforeClass
    public static void setUpClass() throws Exception {
        compositionFactory = CompositionFactory.getInstance();
        composer = new Composer(1, ComposerAim.Phrase,
                new UnaccompaniedCello(),
                new GoldenSectionClimax(UnaccompaniedCello.RANGE.keySet())
        );
    }

    /**
     * Test of getRendered method, of class Composition.
     */
    @Test
    public void testGetRendered() {

        System.out.println("getRendered");
        List<SketchNode> expResult = null;
        List<SketchNode> result;
        composer.compose();
        System.out.println("1 compose");
        result = composer.getPopulation().get(0).getRenderedChecked();
        composer.compose();
        System.out.println("2 compose");
        result = composer.getPopulation().get(0).getRenderedChecked();
        composer.compose().evolve();
        System.out.println("3 compose and evolve");
        result = composer.getPopulation().get(0).getRenderedChecked();
        composer.compose().evolve();
        System.out.println("4 compose and evolve");
        result = composer.getPopulation().get(0).getRenderedChecked();
//        System.out.println(result);
//        assertEquals(expResult, result);
    }

    /**
     * Test of render method, of class Composition.
     */
    @Test
    public void testRender() {

        System.out.println("render");
        for (int i = 0; i < 1; i++) {
            composer.compose().evolve();
        }
        Composition instance = composer.getPopulation().get(0);
        System.out.println(instance);
        List<SketchNode> list = instance.render();
        list.forEach(System.out::println);

    }

    @Test
    public void ObjectCopy() {

        System.out.println("ObjectCopy");
        Composition c1 = compositionFactory.newInstance(composer.generateSeed(),
                ConnectorFactory.getInstance().newConnector(composer::styleChecker),
                composer.getStyles()),
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
        instance.setSeed(seed);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
