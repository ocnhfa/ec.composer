/*
 * Copyright 2018 Jonathan.
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
package tech.metacontext.ec.prototype.composer.enums;

import tech.metacontext.ec.prototype.composer.model.Composition;

/**
 *
 * @author Jonathan
 */
public enum ComposerAim {
    Phrase(8),
    Section(16),
    Movement(32),
    MultiMovement(-1);

    private int size;

    ComposerAim(int size) {
        this.size = size;
    }

    public boolean completed(Composition composition) {
        switch (this) {
            case Phrase:
            case Section:
            case Movement:
//                System.out.println("composition:" + composition);
//                System.out.println("composition.getConnectors:" + composition.getConnectors());
                return composition.getConnectors().size() >= this.size;
            case MultiMovement:
            default:
        }
        return false;
    }

    public int getSize() {
        return size;
    }
}
