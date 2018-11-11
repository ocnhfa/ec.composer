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

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 * @param <E> Type of population
 * @param <R> type of eval score
 */
public abstract class Population<E extends Individual, R> {

  public int size;
  public Map<E, R> population = new HashMap<>();
  public Evaluation<E, R> evalutaion;
  public Selection selector;
  public Map<String, GeneticOperator<E>> operators = new HashMap<>();

  public Population(int size) {
    this.size = size;
    this.initiate();
  }

  /**
   * Initialization of the population.
   */
  public abstract void initiate();

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
