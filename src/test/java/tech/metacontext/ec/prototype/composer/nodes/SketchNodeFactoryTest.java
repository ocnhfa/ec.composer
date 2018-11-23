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
package tech.metacontext.ec.prototype.composer.nodes;

import java.util.stream.Stream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tech.metacontext.ec.prototype.composer.enums.MusicMaterialType;
import tech.metacontext.ec.prototype.composer.nodes.materials.NoteNumber;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class SketchNodeFactoryTest {

  SketchNodeFactory instance;

  public SketchNodeFactoryTest() {
    this.instance = SketchNodeFactory.getInstance();
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

  @Test
  public void main() {
    System.out.println("main");
    Stream.generate(instance::create).limit(10)
            .map(n -> n.getMaterial(MusicMaterialType.NoteNumber))
            .map(m -> (NoteNumber) m)
            .peek(NoteNumber::randomInit)
            .forEach(System.out::println);
  }

  /**
   * Test of getInstance method, of class SketchNodeFactory.
   */
  @Test
  public void testGetInstance() {
    System.out.println("getInstance");
    assertNotNull(instance);
  }

  /**
   * Test of create method, of class SketchNodeFactory.
   */
  @Test
  public void testCreate() {
    System.out.println("create");
    SketchNode result = instance.create();
    assertEquals(SketchNode.class, result.getClass());
  }

}
