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
package tech.metacontext.ec.prototype.composer.materials;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class NoteRange extends MusicMaterial<Range> {

  int size;
  private final List<Range> range;
  private final Set<Range> rangeAvailable;

  public NoteRange() {
    this.range = new ArrayList<>();
    this.rangeAvailable = new HashSet<>();
  }

  public NoteRange(int size, Range... rangeAvailable) {
    this();
    this.size = size;
    this.rangeAvailable.addAll(Arrays.asList(rangeAvailable));
  }

  @Override
  public void randomInit() {
    range.clear();
    Stream.generate(() -> new Random().nextInt(rangeAvailable.size()))
            .limit(size)
            .map(r -> rangeAvailable.toArray(new Range[0])[r])
            .forEach(range::add);
  }

  @Override
  public void add(Range element) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void remove(Range element) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  public static void main(String[] args) {
    NoteRange r = new NoteRange(10, Range.C2, Range.C3, Range.C4, Range.C5, Range.C6);
    r.randomInit();
    System.out.println(r.range);

  }
}
