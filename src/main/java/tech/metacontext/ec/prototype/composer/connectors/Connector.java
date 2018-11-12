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
package tech.metacontext.ec.prototype.composer.connectors;

import tech.metacontext.ec.prototype.abs.Individual;
import tech.metacontext.ec.prototype.composer.nodes.SketchNode;
import tech.metacontext.ec.prototype.composer.nodes.SketchNodeFactory;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class Connector extends Individual {

   private ConnectorRemark mark = ConnectorRemark.Default;
   private SketchNode previous, next;

   public Connector(ConnectorRemark mark, SketchNode... nodes) {
      this.mark = mark;
      
   }

   public Connector() {
   }

   public ConnectorRemark getType() {
      return mark;
   }

   public Connector setType(ConnectorRemark type) {
      this.mark = type;
      return this;
   }

   public SketchNode getPrevious() {
      return previous;
   }

   public void setPrevious(SketchNode previous) {
      this.previous = previous;
   }

   public SketchNode getNext() {
      return next;
   }

   public void setNext(SketchNode next) {
      this.next = next;
   }

   public SketchNode generate() {
      switch (mark) {
         case MirrorNextNodeConnector:
            return getNext();
         case Default:
         case MirrorConnector:
         default:
            next = SketchNodeFactory.getInstance().create();
            return next;
      }
   }

}
