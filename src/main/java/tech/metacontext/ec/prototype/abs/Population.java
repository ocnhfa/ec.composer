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
package tech.metacontext.ec.prototype.abs;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 * @param <E> Type of population
 * @param <R> type of evaluation score
 */
public abstract class Population<E extends Individual, R> {

   public Map<E, R> population;
   public Evaluation<E, R> evalutaion;
   public Selector selection;
   public Map<String, GeneticOperator<E>> operators;

   public Population(int size,
           Evaluation<E, R> eval,
           Selector selection) {
      this.population = new HashMap<>();
      this.evalutaion = eval;
      this.selection = selection;
      this.operators = new HashMap<>();
      this.initiate(size);
   }

   /**
    * Initialization of the population.
    *
    * @param size Initial size of the population.
    */
   public abstract void initiate(int size);

   /**
    * Add Individual to Population.
    *
    * @param individual
    */
   public void add(E individual) {
      try {
         population.keySet()
                 .stream()
                 .filter(t -> t.equals(individual))
                 .findAny()
                 .get();
      } catch (NoSuchElementException ex) {
         R eval = this.evalutaion.eval(individual);
         population.put(individual, eval);
      }
   }

   public int size() {
      return population.size();
   }

   /**
    * Evolution function.
    *
    * @return size difference after selection.
    */
   public abstract int evolution();

   /**
    * Rendering function of the population.
    */
   public abstract void render();

}
