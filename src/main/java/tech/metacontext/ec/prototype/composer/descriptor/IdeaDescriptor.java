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
package tech.metacontext.ec.prototype.composer.descriptor;

import tech.metacontext.ec.prototype.composer.materials.MusicMaterial;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 * @param <M>
 */
public abstract class IdeaDescriptor<M extends MusicMaterial> {

  private List<M> materials;

  public IdeaDescriptor() {

    this.materials = new ArrayList<>();
  }

  public abstract <E extends IdeaDescriptor> E describe();

  /*
    * default setters and getters
   */
  public List<M> getMaterials() {

    return materials;
  }

  public void setMaterials(List<M> materials) {

    this.materials = materials;
  }

}
