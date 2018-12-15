/*
 * Copyright 2018 Jonathan Chang, Chun-yien <ccy@musicapoetica.org>.
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
package tech.metacontext.ec.prototype.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tech.metacontext.ec.prototype.abs.draft.Population;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class CurveEvaluationTest {

  public CurveEvaluationTest() {
  }

  @BeforeClass
  public static void setUpClass() {
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }
  List<Integer> tensions = new ArrayList<>();
  int tension;

  /**
   * Test of eval method, of class CurveEvaluation.
   */
  @Test
  public void testEval() {
    System.out.println("eval");
    CurveEvaluation instance = new CurveEvaluation();
    MusicalIdeas music;
    for (int i = 0; i < 10; i++) {
//      TensionCurve individual = new TensionCurve("test",
//              Stream.generate(Math::random).limit(20)
//                      .map(r -> (int) Math.floor((r - 0.5) * 10))
//                      .collect(Collectors.toList()));
      music = new MusicalIdeas(1);
      music.render(i);
      System.out.println(music.population.entrySet());
//      System.out.println(individual.getTensionCurve() + " -> " + instance.eval(individual));
    }
//    int max = Collections.max(tensions), min = Collections.min(tensions);
//    System.out.printf("max = %d, min = %d\n", max, min);
//    Double expResult = 5 - ((max - min - 5) * 0.5);
//    Double result = instance.eval(individual);
//    assertEquals(expResult, result);
  }

}
