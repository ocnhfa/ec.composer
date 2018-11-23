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
package tech.metacontext.ec.prototype.composer.nodes;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import tech.metacontext.ec.prototype.abs.Individual;
import tech.metacontext.ec.prototype.composer.nodes.materials.MusicMaterial;
import tech.metacontext.ec.prototype.composer.enums.MusicMaterialType;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class SketchNode extends Individual {

  private Map<MusicMaterialType, MusicMaterial> materials;

  public SketchNode() {
    this.materials = new HashMap<>();
  }

  public <M extends MusicMaterial> void addMaterial(M material) {
    materials.put(material.getType(), material);
  }

  public Map<MusicMaterialType, ? extends MusicMaterial> getMaterials() {
    return materials;
  }

  public void setMaterials(Map<MusicMaterialType, MusicMaterial> materials) {
    this.materials = materials;
  }

  public MusicMaterial getMaterial(MusicMaterialType type) {
    return this.materials.get(type);
  }

}
