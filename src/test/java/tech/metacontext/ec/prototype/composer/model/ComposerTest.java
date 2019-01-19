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

import tech.metacontext.ec.prototype.composer.TestCenter;
import tech.metacontext.ec.prototype.composer.styles.Style;
import tech.metacontext.ec.prototype.composer.factory.SketchNodeFactory;
import tech.metacontext.ec.prototype.composer.enums.*;
import static tech.metacontext.ec.prototype.composer.Parameters.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class ComposerTest {

    static final SketchNodeFactory sketchNodeFactory = SketchNodeFactory.getInstance();

    static TestCenter tc;

    @BeforeAll
    public static void prepare() {
        tc = TestCenter.getInstance();
    }

    /**
     * Test of getAimSize method, of class Composer.
     */
    @Test
    public void testGetSize() {
        System.out.println("getSize");
        assertEquals(TestCenter.PRESET_POPULATION_SIZE, tc.getComposer().getSize());
        assertEquals(tc.getComposer().getSize(), tc.getComposer().getPopulationSize());
    }

    /**
     * Test of crossover method, of class Composer.
     */
    @Test
    public void testCrossover() {
        System.out.println("crossover");
        Composition p1, p2;
        do {
            tc.getComposer().sketch().evolve();
            p1 = tc.getComposer().select(Composer.SELECT_ONLY_COMPLETED, SELECTION_THRESHOLD.getDouble());
            p2 = tc.getComposer().select(Composer.SELECT_ONLY_COMPLETED, SELECTION_THRESHOLD.getDouble());
        } while (tc.getComposer().select(Composer.SELECT_ONLY_COMPLETED, SELECTION_THRESHOLD.getDouble()) == null
                || Objects.equals(p1, p2)
                || p1.getSize() == p2.getSize());
        Composition result = tc.getComposer().crossover(p1, p2);
        assertEquals(result.getSize(), Math.max(p1.getSize(), p2.getSize()));
    }

    /**
     * Test of compose method, of class Composer.
     */
    @Test
    public void testSketch() {
        System.out.println("sketch");
        while (tc.getComposer().getConservetory().size() < 2) {
            tc.getComposer().sketch().evolve();
        }
        System.out.println("--conservatory--");
        System.out.println(tc.getComposer().getConservetory());
        tc.getComposer().drawScatterPlot();
    }

    /**
     * Test of select method, of class Composer.
     */
    @Test
    public void testRandomSelect() {
        System.out.println("randomSelect");
        int state = Composer.SELECT_ONLY_COMPLETED;
        Composition result = Stream.generate(() -> {
            tc.getComposer().sketch().evolve();
            return tc.getComposer().select(state, SELECTION_THRESHOLD.value.doubleValue());
        })
                .filter(Objects::nonNull)
                .findFirst()
                .get();
        assertTrue(tc.getComposer().getAim().isCompleted(result));
    }

    /**
     * Test of conserve method, of class Composer.
     */
    @Test
    public void testConserve() {
        System.out.println("conserve");
        while (tc.getComposer().getConservetory().size() < 1) {
            tc.getComposer().sketch().evolve();
        }
        System.out.println(Composer.simpleScoreOutput(tc.getComposer().getConservetory()
                .keySet().toArray(Composition[]::new)));
        tc.getComposer().getConservetory().keySet().forEach(c -> {
            System.out.println(c.getId_prefix());
            System.out.println(c.getConnectors().size());
            System.out.println(c.getRendered().size());
        });
//        System.out.println( tc.getComposer().getConservetory().get(0));
        while (tc.getComposer().getConservetory().size() > 0) {
            Composition c = tc.getComposer().getConservetory().entrySet().stream()
                    .findFirst()
                    .get()
                    .getKey();
            tc.getComposer().getConservetory().remove(c);
            System.out.println(Composer.simpleScoreOutput(c));
            boolean expResult = tc.getComposer().getStyles().stream()
                    .map(c::getScore)
                    .allMatch(score -> score > tc.getComposer().getConserve_score());
            System.out.println(Composer.simpleScoreOutput(c));
            System.out.println("expResult = " + expResult);
            boolean result = tc.getComposer().conserve(c);
            System.out.println("Result = " + result);
            assertEquals(expResult, result);
            tc.getComposer().getConservetory().remove(c);
        }
    }

    /**
     * Test of evolve method, of class Composer.
     */
    @Test
    @Disabled
    public void testEvolve() {
        System.out.println("evolve");
        Composer instance = null;
        tc.getComposer().evolve();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addStyle method, of class Composer.
     */
    @Test
    @Disabled
    public void testAddStyle() {
        System.out.println("addStyle");
        Style style = null;
        Composer instance = null;
        tc.getComposer().addStyle(style);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStyles method, of class Composer.
     */
    @Test
    @Disabled
    public void testGetStyles() {
        System.out.println("getStyles");
        Composer instance = null;
        List<? extends Style> expResult = null;
        List<? extends Style> result = tc.getComposer().getStyles();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setStyles method, of class Composer.
     */
    @Test
    @Disabled
    public void testSetStyles() {
        System.out.println("setStyles");
        List<Style> styles = null;
        Composer instance = null;
        tc.getComposer().setStyles(styles);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAim method, of class Composer.
     */
    @Test
    @Disabled
    public void testGetAim() {
        System.out.println("getAim");
        Composer instance = null;
        ComposerAim expResult = null;
        ComposerAim result = tc.getComposer().getAim();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAim method, of class Composer.
     */
    @Test
    @Disabled
    public void testSetAim() {
        System.out.println("setAim");
        ComposerAim aim = null;
        Composer instance = null;
        tc.getComposer().setAim(aim);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getConservetory method, of class Composer.
     */
    @Test
    @Disabled
    public void testGetConservetory() {
        System.out.println("getConservetory");
        Composer instance = null;
        List<Composition> expResult = null;
        Map<Composition, Integer> result = tc.getComposer().getConservetory();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSize method, of class Composer.
     */
    @Test
    @Disabled
    public void testSetSize() {
        System.out.println("setSize");
        int size = 0;
        Composer instance = null;
        tc.getComposer().setSize(size);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mutate method, of class Composer.
     */
    @Test
    @Disabled
    public void testMutate() {
        System.out.println("mutate");
        Composition origin = null;
        Composer instance = null;
        Composition expResult = null;
        Composition result = tc.getComposer().mutate(origin);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class Composer.
     */
    @Test
    @Disabled
    public void testMain() throws Exception {
        System.out.println("main");
        String[] args = null;
        Composer.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSummary method, of class Composer.
     */
    @Test
    @Disabled
    public void testGetSummary() {
        System.out.println("getSummary");
        List<Composition> list = null;
        String expResult = "";
        String result = Composer.getSummary(list);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getChild method, of class Composer.
     */
    @Test
    @Disabled
    public void testGetChild() {
        System.out.println("getChild");
        Composer instance = null;
        Composition expResult = null;
        Composition result = instance.getChild();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of select method, of class Composer.
     */
    @Test
    @Disabled
    public void testSelect_Predicate_double() {
        System.out.println("select");
        Predicate<Composition> criteria = null;
        double threshold = 0.0;
        Composer instance = null;
        Composition expResult = null;
        Composition result = instance.select(criteria, threshold);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of select method, of class Composer.
     */
    @Test
    @Disabled
    public void testSelect_int_double() {
        System.out.println("select");
        int state = 0;
        double threshold = 0.0;
        Composer instance = null;
        Composition expResult = null;
        Composition result = instance.select(state, threshold);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of drawCombinedChart method, of class Composer.
     */
    @Test
    @Disabled
    public void testRenderCombinedChart() {
        System.out.println("renderCombinedChart");
        Composer instance = null;
        instance.drawCombinedChart();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of drawAvgLineChart method, of class Composer.
     */
    @Test
    @Disabled
    public void testRenderAvgLineChart() {
        System.out.println("renderAvgLineChart");
        Composer instance = null;
        instance.drawAvgLineChart();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of drawScatterPlot method, of class Composer.
     */
    @Test
    @Disabled
    public void testRenderScatterPlot() {
        System.out.println("renderScatterPlot");
        Composer instance = null;
        instance.drawScatterPlot();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMinScore method, of class Composer.
     */
    @Test
    @Disabled
    public void testGetMinScore() {
        System.out.println("getMinScore");
        Composition c = null;
        Composer instance = null;
        double expResult = 0.0;
        double result = instance.getMinScore(c);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of simpleScoreOutput method, of class Composer.
     */
    @Test
    @Disabled
    public void testSimpleScoreOutput() {
        System.out.println("simpleScoreOutput");
        Composition[] list = null;
        String expResult = "";
        String result = Composer.simpleScoreOutput(list);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of persistAll method, of class Composer.
     */
    @Test
    @Disabled
    public void testPersistAll() {
        System.out.println("persistAll");
        Composer instance = null;
        instance.persistAll();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
