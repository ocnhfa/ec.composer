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

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Ignore;
import tech.metacontext.ec.prototype.composer.enums.ComposerAim;
import tech.metacontext.ec.prototype.composer.enums.MaterialType;
import tech.metacontext.ec.prototype.composer.enums.mats.Range;
import tech.metacontext.ec.prototype.composer.styles.GoldenSectionClimax;
import tech.metacontext.ec.prototype.composer.styles.Style;
import tech.metacontext.ec.prototype.composer.styles.UnaccompaniedCello;
import static tech.metacontext.ec.prototype.composer.Settings.*;

/**
 *
 * @author Jonathan Chang
 */
public class ComposerTest {

    static Composer instance;
    static final int PRESET_POPULATION_SIZE = 30;

    @BeforeClass
    public static void prepare() throws Exception {
        instance = new Composer(PRESET_POPULATION_SIZE, ComposerAim.Phrase,
                RENEW_TEST,
                new UnaccompaniedCello(),
                new GoldenSectionClimax(UnaccompaniedCello.RANGE.keySet()));
        assertEquals(PRESET_POPULATION_SIZE, instance.getSize());
        assertEquals(instance.getSize(), instance.getPopulationSize());
    }

    /**
     * Test of compose method, of class Composer.
     */
    @Test
    public void testCompose() {
        System.out.println("compose");
        while (instance.getConservetory().size() < 2) {
            instance.compose().evolve();
        }
        System.out.println("--conservatory--");
        System.out.println(instance.getConservetory());
        instance.render();
    }

    /**
     * Test of select method, of class Composer.
     */
    @Test
    public void testRandomSelect() {
        System.out.println("randomSelect");
        int state = Composer.SELECT_ONLY_COMPLETED;
        Composition result = Stream.generate(() -> {
            instance.compose().evolve();
            return instance.randomSelect(state);
        })
                .filter(Objects::nonNull)
                .findFirst()
                .get();
        assertTrue(instance.getAim().completed(result));
    }

    @Test
    public void testStyleChecker() {
        System.out.println("styleChecker");
        System.out.println("generateSeed");
        SketchNode node = instance.generateSeed();
        assertTrue(Stream.generate(() -> instance.styleChecker(node))
                .filter(b -> b)
                .findFirst()
                .get());
        node.getMat(MaterialType.NoteRanges).setMaterials(List.of(Range.C0));
        assertFalse(Stream.generate(() -> instance.styleChecker(node))
                .limit(100)
                .allMatch(b -> b));
    }

    /**
     * Test of conserve method, of class Composer.
     */
    @Test
    public void testConserve() {
        System.out.println("conserve");
        while (instance.getConservetory().size() < 3) {
            instance.compose().evolve();
        }
        System.out.println(Composer.output(instance.getConservetory().toArray(Composition[]::new)));
        instance.getConservetory().forEach(c -> {
            System.out.println(c.getId_prefix());
            System.out.println(c.getConnectors().size());
            System.out.println(c.getRendered().size());
        });
//        System.out.println(instance.getConservetory().get(0));
        while (instance.getConservetory().size() > 0) {
            Composition c = instance.getConservetory().remove(0);
            System.out.println(Composer.output(c));
            boolean expResult = instance.getStyles().stream()
                    .map(c::getScore)
                    .allMatch(score -> score > CONSERVE_SCORE);
            System.out.println(Composer.output(c));
            System.out.println("expResult = " + expResult);
            boolean result = instance.conserve(c);
            System.out.println("Result = " + result);
            assertEquals(expResult, result);
            instance.getConservetory().remove(c);
        }
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
