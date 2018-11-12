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
package tech.metacontext.ec.prototype.test;

import tech.metacontext.ec.prototype.abs.Evaluation;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class CurveEvaluation implements Evaluation<TensionCurve, Double> {

  @Override
  public Double eval(TensionCurve individual) {
    int max = 0, diff = 0, tension = 0;
    for (Integer delta : individual.getCurve()) {
      tension += delta;
      max = Math.max(tension, max);
      diff = Math.max(diff, max - tension);
    }
    return 5 - ((diff - 5) * 0.5);
  }
}
