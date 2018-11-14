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
package tech.metacontext.ec.prototype.composer.eval;

import tech.metacontext.ec.prototype.abs.Evaluation;
import tech.metacontext.ec.prototype.composer.Composition;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class CompositionEval implements Evaluation<Composition, CompositionEval> {

  Composition composition;
  CompositionState state;

  public CompositionEval() {
  }

  public CompositionEval(Composition composition) {
    this.composition = composition;
  }

  @Override
  public CompositionEval eval(Composition composition) {
    updateState();
    return new CompositionEval(composition);
  }

  public void updateState() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

}
