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
package tech.metacontext.ec.prototype.composer.draft.nodes;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import tech.metacontext.ec.prototype.composer.draft.abs.Factory;
import tech.metacontext.ec.prototype.composer.draft.nodes.materials.MusicMaterial;
import tech.metacontext.ec.prototype.composer.draft.enums.MusicMaterialType;

/**
 * Factory for producing SketchNode.
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class SketchNodeFactory implements Factory<SketchNode> {

  private static SketchNodeFactory instance;
  List<Class<? extends MusicMaterial>> template;

  /**
   * Constructor.
   */
  private SketchNodeFactory() {
    
    template = new ArrayList<>();
    Stream.of(MusicMaterialType.values())
            .map(MusicMaterialType::getClazz)
            .forEach(template::add);
  }

  public static SketchNodeFactory getInstance() {
    
    if (instance == null) {
      instance = new SketchNodeFactory();
    }
    return instance;
  }

  @Override
  public SketchNode create() {
    
    SketchNode node = new SketchNode();
    template.stream().forEach((c) -> {
      try {
        node.addMaterial(c.newInstance());
      } catch (InstantiationException | IllegalAccessException ex) {
        Logger.getLogger(SketchNodeFactory.class.getName()).log(Level.SEVERE, null, ex);
      }
    });
    return node;
  }

}
