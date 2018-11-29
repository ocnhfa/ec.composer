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
package tech.metacontext.ec.prototype.composer.nodes.materials;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Describe the serial change in note numbers of a Node.
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class NoteNumber extends MusicMaterial<Integer> {

  private int init_size = 4;
  private int min = 0;
  private int max = 16;
  private final List<Integer> numbers;

  public NoteNumber() {
    
    numbers = new ArrayList<>();
  }

  public NoteNumber(int init_size, int min, int max) {
    
    this();
    this.init_size = init_size;
    this.min = min;
    this.max = max;
  }

  @Override
  public void randomInit() {
    
    numbers.clear();
    this.init_size = 4;
    this.min = 0;
    this.max = 16;
    Stream.generate(Math::random)
            .limit(init_size)
            .map(r -> (int) (Math.ceil(max - min) * r))
            .forEach(this::add);
  }

  @Override
  public void add(Integer element) {
    
    this.numbers.add(element);
  }

  @Override
  public void remove(Integer element) {
    
    this.numbers.remove(element);
  }

  @Override
  public String toString() {
    
    return String.format("NoteNumber(%d, %d, %d)%s",
            init_size, min, max, numbers);
  }

  public int getInit_size() {
    
    return init_size;
  }

  public void setInit_size(int init_size) {
    
    this.init_size = init_size;
  }

  public int getMin() {
    
    return min;
  }

  public void setMin(int min) {
    
    this.min = min;
  }

  public int getMax() {
    
    return max;
  }

  public void setMax(int max) {
    
    this.max = max;
  }
}
