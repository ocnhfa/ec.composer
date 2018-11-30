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
package tech.metacontext.ec.prototype.composer2;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class Connector {

   private SketchNode previous;
   private SketchNode next;
   private List<IdeaDescriptor> descriptors;

   public Connector(SketchNode previous) {

      this.descriptors = new ArrayList<>();
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

   public List<IdeaDescriptor> getDescriptors() {
      return descriptors;
   }

   public void setDescriptors(List<IdeaDescriptor> descriptors) {
      this.descriptors = descriptors;
   }

}
