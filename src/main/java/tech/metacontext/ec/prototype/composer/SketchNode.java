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

import java.util.List;
import tech.metacontext.ec.prototype.composer.materials.MusicMaterial;
import tech.metacontext.ec.prototype.composer.materials.enums.Type;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import tech.metacontext.ec.prototype.abs.Individual;
import tech.metacontext.ec.prototype.composer.styles.Style;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class SketchNode extends Individual {

    private Map<Type, ? extends MusicMaterial> musicMats;

    public SketchNode() {

        this.musicMats = Stream.of(Type.values())
                .collect(Collectors.toMap(t -> t, Type::getInstance));
    }

    public SketchNode(SketchNode parent) {

        super(parent.getId());
        this.musicMats = parent.getMats().entrySet().stream()
                .collect(Collectors.toMap(
                        Entry::getKey,
                        e -> {
                            MusicMaterial mm = e.getKey().getInstance();
                            mm.setMaterials(e.getValue().getMaterials());
                            return mm;
                        }));
    }

    public MusicMaterial getMat(Type type) {

        return musicMats.get(type);
    }

    /*
     * Default setters and getters
     */
    public Map<Type, ? extends MusicMaterial> getMats() {
        return musicMats;
    }

    public void setMats(Map<Type, MusicMaterial> mats) {
        this.musicMats = mats;
    }

}
