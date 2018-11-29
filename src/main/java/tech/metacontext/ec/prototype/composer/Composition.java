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

import tech.metacontext.ec.prototype.composer.nodes.SketchNodeFactory;
import tech.metacontext.ec.prototype.composer.connectors.ConnectorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import tech.metacontext.ec.prototype.abs.Individual;
import tech.metacontext.ec.prototype.composer.connectors.Connector;
import tech.metacontext.ec.prototype.composer.connectors.ConnectorRemark;
import tech.metacontext.ec.prototype.composer.enums.CompositionState;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class Composition extends Individual {

//   private final List<SketchNode> nodes;
  private final List<Connector> ideas;

  private static final SketchNodeFactory SN_FACTORY = SketchNodeFactory.getInstance();
  private static final ConnectorFactory CI_FACTORY = ConnectorFactory.getInstance();

  public Composition() {

    super();
    ideas = new ArrayList<>();
    Connector idea = CI_FACTORY.create(ConnectorRemark.Default,
            SN_FACTORY.create());
    ideas.add(idea);
  }

  public void compose() {

    // Elongation
    Connector idea = CI_FACTORY.create(ConnectorRemark.Default,
            ideas.get(this.length() - 1).getNext());
    ideas.add(idea);
  }

  public int length() {

    return ideas.size();
  }

  public CompositionState getState() {
    
    if (this.length() < 2) {
      return CompositionState.Motive;
    } else if (this.length() < 4) {
      return CompositionState.Phrase;
    } else if (this.length() < 8) {
      return CompositionState.Section;
    }
    return CompositionState.Movement;
  }

  @Override
  public String toString() {

    String result = "*** " + super.toString() + "\n"
            + ideas.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining("\n"));
    return result;
  }

  public List<Connector> getIdeas() {
    
    return ideas;
  }
}
