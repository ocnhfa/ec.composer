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
package tech.metacontext.ec.prototype.composer.descriptors;

import tech.metacontext.ec.prototype.composer.materials.Pitch;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public enum ConsonanceType {
  PerfectConsonance(1.0),
  ImperfectConsonance(0.9),
  Dissonance(0.7);

  public double factor;

  ConsonanceType(double factor) {
    this.factor = factor;
  }

  public static ConsonanceType getType(Pitch p1, Pitch p2) {
    String names = p1.name() + "." + p2.name() + ".";
    if (names.contains("sharp") && names.contains("flat")) {
      return Dissonance;
    }
    switch ((p2.compareToPitch(p1) + 12) % 12) {
      case 7:
        if (names.contains("F.") && names.contains("sharp")
                || names.contains("B.") && names.contains("flat")) {
          return Dissonance;
        }
        return PerfectConsonance;
      case 3:
      case 4:
      case 8:
      case 9:
        return ImperfectConsonance;
      default:
        return Dissonance;
    }
  }

}
