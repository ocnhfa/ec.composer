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

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public enum Pitch {
  C, Cs, D, Ds, E, F, Fs, G, Gs, A, As, B;

  public Pitch simple(String note) {
    switch (note) {
      case "1":
        return C;
      case "1#":
      case "2b":
        return Cs;
      case "2":
        return D;
      case "3":
        return E;
      case "4":
        return F;
      case "4#":
      case "5b":
        return Fs;
      case "5":
        return G;
      case "5#":
      case "6b":
        return Gs;
      case "6":
        return A;
      case "6#":
      case "7b":
        return As;
      case "7":
        return B;
    }
    return null;
  }
}
