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
package tech.metacontext.ec.prototype.composer.descriptors;

import tech.metacontext.ec.prototype.composer.enums.ConsonanceType;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import static tech.metacontext.ec.prototype.composer.enums.ConsonanceType.*;
import tech.metacontext.ec.prototype.composer.enums.Pitch;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class ConsonanceTypeTest {

  public ConsonanceTypeTest() {
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

  /**
   * Test of valueOf and values method, of class ConsonanceType.
   */
  @Test
  public void testValueOf_testValues() {
    System.out.println("valueOf");
    System.out.println("values");
    Stream.of("PerfectConsonance", "ImperfectConsonance", "Dissonance")
            .map(ConsonanceType::valueOf)
            .forEach((ConsonanceType v) -> {
              assertTrue(Arrays.asList(ConsonanceType.values()).contains(v));
            });
  }

  /**
   * Test of getType method, of class ConsonanceType.
   */
  @Test
  public void testGetType() {
    System.out.println("getType");

    List<TestPair> pairs = Arrays.asList(new TestPair[]{
      new TestPair(Pitch.C, Pitch.G, PerfectConsonance),
      new TestPair(Pitch.E, Pitch.B, PerfectConsonance),
      new TestPair(Pitch.D, Pitch.A, PerfectConsonance),
      new TestPair(Pitch.G, Pitch.D, PerfectConsonance),
      new TestPair(Pitch.A, Pitch.E, PerfectConsonance),
      new TestPair(Pitch.C, Pitch.A, ImperfectConsonance),
      new TestPair(Pitch.A, Pitch.F_sharp, ImperfectConsonance),
      new TestPair(Pitch.F, Pitch.D, ImperfectConsonance),
      new TestPair(Pitch.F_sharp, Pitch.D_sharp, ImperfectConsonance),
      new TestPair(Pitch.F_sharp, Pitch.E_flat, Dissonance),
      new TestPair(Pitch.F, Pitch.F_sharp, Dissonance),
      new TestPair(Pitch.E_flat, Pitch.B_flat, PerfectConsonance),
      new TestPair(Pitch.E_flat, Pitch.A_sharp, Dissonance),
      new TestPair(Pitch.B_flat, Pitch.F, PerfectConsonance),
      new TestPair(Pitch.A_sharp, Pitch.F, Dissonance)
    });
    pairs.stream()
            .peek(System.out::println)
            .forEach((TestPair pair) -> {
              Pitch p1 = pair.p1;
              Pitch p2 = pair.p2;
              ConsonanceType expResult = pair.result;
              ConsonanceType result = ConsonanceType.getType(p1, p2);
              assertEquals(expResult, result);
            });

  }

  public class TestPair {

    Pitch p1, p2;
    ConsonanceType result;

    public TestPair(Pitch p1, Pitch p2, ConsonanceType result) {
      this.p1 = p1;
      this.p2 = p2;
      this.result = result;
    }

    @Override
    public String toString() {
      return String.format("%-7s, %-7s -> %s", p1, p2, result);
    }
  }
}
