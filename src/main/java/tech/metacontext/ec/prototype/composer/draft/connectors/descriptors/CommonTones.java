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
package tech.metacontext.ec.prototype.composer.draft.connectors.descriptors;

import tech.metacontext.ec.prototype.composer.draft.abs.IdeaDescriptor;
import tech.metacontext.ec.prototype.composer.draft.nodes.materials.PitchSet;
import tech.metacontext.ec.prototype.composer.draft.enums.Pitch;
import java.util.Arrays;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class CommonTones extends IdeaDescriptor<PitchSet, Long> {

   private PitchSet base;

   public CommonTones(PitchSet base) {
     
      this.base = base;
   }

   public PitchSet getBase() {
     
      return base;
   }

   public void setBase(PitchSet base) {
     
      this.base = base;
   }

   @Override
   public Long describe(PitchSet factor) {
     
      return common(base, factor);
   }

   public static long common(PitchSet... sets) {
     
      return Arrays.asList(Pitch.values()).stream().filter((p) -> {
         for (PitchSet set : sets) {
            if (!set.getPitches().contains(p)) {
               return false;
            }
         }
         return true;
      }).count();
   }

}
