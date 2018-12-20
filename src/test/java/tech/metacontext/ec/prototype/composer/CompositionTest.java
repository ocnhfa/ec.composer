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
import org.junit.Test;
import static org.junit.Assert.*;
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

    Composer composer;

    public CompositionTest() {
        composer = new Composer(1, ComposerAim.Phrase,
                new UnaccompaniedCello(),
                new GoldenSectionClimax(UnaccompaniedCello.RANGE.keySet())
        );
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
        SketchNode seed = composer.generateSeed();
        List<SketchNode> list = instance.render(seed);
        list.forEach(System.out::println);

    }

    @Test
    public void ObjectCopy() {

        System.out.println("ObjectCopy");
        Composition c1 = new Composition(composer.generateSeed(),
                ConnectorFactory.getInstance().getConnector(composer::styleChecker)),
                c2 = c1,
                c3 = new Composition(c1);
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

}
