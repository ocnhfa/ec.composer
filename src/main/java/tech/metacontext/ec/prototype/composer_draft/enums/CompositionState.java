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
package tech.metacontext.ec.prototype.composer_draft.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tech.metacontext.ec.prototype.composer_draft.abs.CompositionEvalRule;
import tech.metacontext.ec.prototype.composer_draft.rules.SmoothPitchSet;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public enum CompositionState {
  
  Motive(SmoothPitchSet.class),
  Phrase(SmoothPitchSet.class),
  Section(SmoothPitchSet.class),
  Movement(SmoothPitchSet.class);

  public List<CompositionEvalRule> rules;

  CompositionState(Class<? extends CompositionEvalRule>... rules) {

    this.rules = new ArrayList<>();
    for (Class<? extends CompositionEvalRule> rule : rules) {
      try {
        this.rules.add(rule.newInstance());
      } catch (InstantiationException | IllegalAccessException ex) {
        Logger.getLogger(CompositionState.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }
}
