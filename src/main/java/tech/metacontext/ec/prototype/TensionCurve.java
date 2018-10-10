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
import java.util.List;
import java.util.Random;
import tech.metacontext.ec.prototype.abs.Individual;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class TensionCurve implements Individual {

   List<Integer> curve;

   public TensionCurve() {
      curve = new ArrayList<>();
   }

   public TensionCurve generateRandom() {
      for (int i = 0; i < 20; i++) {
         int delta = new Random().nextInt(21) - 10;
         curve.add(delta);
      }
      return this;
   }

   @Override
   public String toString() {
      String s = "";
      for (int i = 0; i < curve.size() - 1; i++) {
         s += String.format("%+4d,", curve.get(i));
      }
      s += String.format("%+4d", curve.get(curve.size() - 1));
      return s;
   }

}
