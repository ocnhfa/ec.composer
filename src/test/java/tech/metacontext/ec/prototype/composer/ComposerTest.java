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

import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import tech.metacontext.ec.prototype.composer.enums.ComposerAim;
import tech.metacontext.ec.prototype.composer.styles.FreeStyle;
import tech.metacontext.ec.prototype.composer.styles.Style;

/**
 *
 * @author Jonathan Chang
 */
public class ComposerTest {

    public ComposerTest() {
    }

    /**
     * Test of compose method, of class Composer.
     */
    @Test
    public void testCompose() {

        System.out.println("compose");
        int size = 20;
        Composer c = new Composer(size, ComposerAim.Phrase, new FreeStyle());
        for (int i = 0; i < 50; i++) {
            c.compose();
            System.out.print(
                    c.getPopulation().stream()
                            .map(comp -> "" + comp.getSize())
                            .collect(Collectors.joining(" ")));
            if (c.getConservetory().size() > 0) {
                System.out.print(" // "
                        + c.getConservetory().stream()
                                .map(comp -> "" + comp.getSize())
                                .collect(Collectors.joining(" ")));
            }
            System.out.println("");
        }
        System.out.println("--conservatory--");
        System.out.println(c.getConservetory());
        assertEquals(size, c.getSize());
        assertEquals(c.getSize(), c.getPopulationSize() + c.getConservetory().size());
    }

    /**
     * Test of select method, of class Composer.
     */
    @Test
    @Ignore
    public void testSelect() {
        System.out.println("select");
        int state = 0;
        Composer instance = null;
        Composition expResult = null;
        Composition result = instance.select(state);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    @Ignore
    public void testStyleChecker() {
    }

    /**
     * Test of generateSeed method, of class Composer.
     */
    @Test
    @Ignore
    public void testGenerateSeed() {
        System.out.println("generateSeed");
        Composer instance = null;
        SketchNode expResult = null;
        SketchNode result = instance.generateSeed();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of evolve method, of class Composer.
     */
    @Test
    @Ignore
    public void testEvolve() {
        System.out.println("evolve");
        Composer instance = null;
        instance.evolve();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of conserve method, of class Composer.
     */
    @Test
    @Ignore
    public void testConserve() {
        System.out.println("conserve");
        Composition composition = null;
        Composer instance = null;
        boolean expResult = false;
        boolean result = instance.conserve(composition);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of render method, of class Composer.
     */
    @Test
    @Ignore
    public void testRender() {
        System.out.println("render");
        Composer instance = null;
        instance.render();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addStyle method, of class Composer.
     */
    @Test
    @Ignore
    public void testAddStyle() {
        System.out.println("addStyle");
        Style style = null;
        Composer instance = null;
        instance.addStyle(style);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStyles method, of class Composer.
     */
    @Test
    @Ignore
    public void testGetStyles() {
        System.out.println("getStyles");
        Composer instance = null;
        List<? extends Style> expResult = null;
        List<? extends Style> result = instance.getStyles();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setStyles method, of class Composer.
     */
    @Test
    @Ignore
    public void testSetStyles() {
        System.out.println("setStyles");
        List<Style> styles = null;
        Composer instance = null;
        instance.setStyles(styles);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAim method, of class Composer.
     */
    @Test
    @Ignore
    public void testGetAim() {
        System.out.println("getAim");
        Composer instance = null;
        ComposerAim expResult = null;
        ComposerAim result = instance.getAim();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAim method, of class Composer.
     */
    @Test
    @Ignore
    public void testSetAim() {
        System.out.println("setAim");
        ComposerAim aim = null;
        Composer instance = null;
        instance.setAim(aim);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getConservetory method, of class Composer.
     */
    @Test
    @Ignore
    public void testGetConservetory() {
        System.out.println("getConservetory");
        Composer instance = null;
        List<Composition> expResult = null;
        List<Composition> result = instance.getConservetory();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSize method, of class Composer.
     */
    @Test
    @Ignore
    public void testGetSize() {
        System.out.println("getSize");
        Composer instance = null;
        int expResult = 0;
        int result = instance.getSize();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSize method, of class Composer.
     */
    @Test
    @Ignore
    public void testSetSize() {
        System.out.println("setSize");
        int size = 0;
        Composer instance = null;
        instance.setSize(size);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mutate method, of class Composer.
     */
    @Test
    @Ignore
    public void testMutate() {
        System.out.println("mutate");
        Composition origin = null;
        Composer instance = null;
        Composition expResult = null;
        Composition result = instance.mutate(origin);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of crossover method, of class Composer.
     */
    @Test
    @Ignore
    public void testCrossover() {
        System.out.println("crossover");
        Composition parent1 = null;
        Composition parent2 = null;
        Composer instance = null;
        Composition expResult = null;
        Composition result = instance.crossover(parent1, parent2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
