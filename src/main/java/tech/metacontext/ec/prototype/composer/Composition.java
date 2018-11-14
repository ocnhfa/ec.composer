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
import tech.metacontext.ec.prototype.composer.nodes.SketchNode;
import tech.metacontext.ec.prototype.composer.connectors.ConnectorFactory;
import java.util.ArrayList;
import java.util.List;
import tech.metacontext.ec.prototype.abs.Individual;
import tech.metacontext.ec.prototype.composer.connectors.Connector;
import tech.metacontext.ec.prototype.composer.connectors.ConnectorRemark;
import tech.metacontext.ec.prototype.composer.abs.Factory;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class Composition extends Individual {

   private final List<SketchNode> nodes;
   private final List<Connector> ideas;

   private static final Factory<SketchNode> SN_FACTORY = SketchNodeFactory.getInstance();
   private static final Factory<Connector> CI_FACTORY = ConnectorFactory.getInstance();

   public Composition() {
      super();
      nodes = new ArrayList<>();
      ideas = new ArrayList<>();
      nodes.add(SN_FACTORY.create());  // Create initial node.
   }

   public void addNode() {
      Connector idea = ((ConnectorFactory) CI_FACTORY).create(ConnectorRemark.Default,
              nodes.get(nodes.size() - 1));
      ideas.add(idea);
      nodes.add(idea.getNext());
   }

   public int length() {
      return nodes.size();
   }

   @Override
   public String toString() {
      String result = super.toString() + "\n"
              + "*** " + nodes.get(0).toString();
      for (int i = 0; i < ideas.size(); i++) {
         result += String.format(" --> %s\n--> %s", ideas.get(i).toString(),
                 nodes.get(i + 1).toString());
      }
      return result;
   }

}
