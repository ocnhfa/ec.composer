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
package tech.metacontext.ec.prototype.composer;

import tech.metacontext.ec.prototype.abs.Population;

/**
 * Composer class bears a number of Composition objects and evolves them.
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class Composer extends Population<Composition, CompositionEval> {

   public Composer(int size) {
      super(size, CompositionEval::new);
   }

   public CompositionEval eval(Composition c) {
      CompositionEval e = new CompositionEval(c);
      return e;
   }

   @Override
   public void initiate(int size) {
      for (int i = 0; i < size; i++) {
         Composition composition = new Composition();
         CompositionEval eval = new CompositionEval(composition);
         this.population.put(composition, eval);
      }
   }

   @Override
   public int evolution() {
      population.keySet().forEach(Composition::addNode);
      return size();
   }

   @Override
   public void render() {
      population.keySet().forEach(System.out::println);
   }

}
