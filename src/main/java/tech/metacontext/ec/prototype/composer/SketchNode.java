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

import tech.metacontext.ec.prototype.composer.materials.MusicMaterial;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class SketchNode {

    private Map<MusicMaterial.Type, MusicMaterial> mats;

    public SketchNode() {

        this.mats = new HashMap<>();
    }

    public SketchNode(SketchNode parent) {

        this.mats = parent.getMats().entrySet().stream()
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }

    /*
     * Default setters and getters
     */
    public Map<MusicMaterial.Type, MusicMaterial> getMats() {
        return mats;
    }

    public void setMats(Map<MusicMaterial.Type, MusicMaterial> mats) {
        this.mats = mats;
    }

}
