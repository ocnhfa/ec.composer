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
package tech.metacontext.ec.prototype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Random;
import tech.metacontext.ec.prototype.abs.Population;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class MusicalIdeas extends Population<TensionCurve, Double> {

   public MusicalIdeas(int size) {
      super(size);
      this.eval = this::eval;
      this.selector = this::selector;
      this.operators = new HashMap<>();
      this.operators.put("crossover", this::crossover);
      this.operators.put("mutation", this::mutation);
   }

   public List<TensionCurve> crossover(TensionCurve... e) {
      //TODO
      return null;
   }

   public List<TensionCurve> mutation(TensionCurve... e) {
      //TODO
      return null;
   }

   public double eval(TensionCurve tc) {
      int max = 0, min = 0, tension = 0;
      for (Integer delta : tc.curve) {
         tension += delta;
         max = (max < tension) ? tension : max;
         min = (tension < min) ? tension : min;
      }
      int diff = max - min;
      return 5 - ((diff - 5) * 0.5);
   }

   public void selector() {
      Double threshold = this.population.values().stream()
              .mapToDouble(a -> a)
              .average().getAsDouble();
      this.population.values().removeIf(value -> value < threshold);
   }

   @Override
   public void initiate(int size) {
      for (int i = 0; i < size; i++) {
         TensionCurve tc = new TensionCurve().generateRandom();
         this.population.put(tc, eval(tc));
      }
   }

   @Override
   public void evolution() {
      this.selector();
      List<TensionCurve> keySet = new ArrayList(this.population.keySet());
      for (int i = 0; i < size - this.population.size(); i++) {
         int p1 = new Random().nextInt(this.population.size()), p2 = p1;
         while (p1 == p2) {
            p2 = new Random().nextInt(this.population.size());
         }
         TensionCurve child = crossover(keySet.get(p1), keySet.get(p2)).get(0);
         this.population.put(child, eval(child));
      }
   }
}
