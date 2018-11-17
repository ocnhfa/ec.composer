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
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class NoteNumber extends MusicMaterial<Integer> {

   int init_size, min, max;
   private final List<Integer> numbers;

   public NoteNumber(int init_size, int min, int max) {
      this.init_size = init_size;
      this.min = min;
      this.max = max;
      numbers = new ArrayList<>();
   }

   @Override
   public void randomInit() {
      numbers.clear();
      Stream.generate(() -> new Random().nextInt(max - min + 1))
              .limit(init_size)
              .forEach(numbers::add);
   }

   @Override
   public void add(Integer element) {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   }

   @Override
   public void remove(Integer element) {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   }

   public static void main(String[] args) {
      NoteNumber nn = new NoteNumber(4, 0, 8);
      nn.randomInit();
      System.out.println(nn.numbers);
   }
}
