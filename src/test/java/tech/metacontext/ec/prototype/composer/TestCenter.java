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
package tech.metacontext.ec.prototype.composer;

import java.util.logging.Level;
import java.util.logging.Logger;
import tech.metacontext.ec.prototype.composer.enums.ComposerAim;
import tech.metacontext.ec.prototype.composer.model.Composer;
import tech.metacontext.ec.prototype.composer.styles.GoldenSectionClimax;
import tech.metacontext.ec.prototype.composer.styles.UnaccompaniedCello;

/**
 *
 * @author Jonathan
 */
public class TestCenter {

    public static final int PRESET_POPULATION_SIZE = 50;
    private static TestCenter instance;
    private static Composer composer;

    public static TestCenter getInstance() {
        if (instance == null) {
            instance = new TestCenter();
        }
        return instance;
    }

    private TestCenter() {
        try {
            composer = new Composer(PRESET_POPULATION_SIZE, ComposerAim.Phrase, Settings.RENEW_TEST,
                    new UnaccompaniedCello(),
                    new GoldenSectionClimax(UnaccompaniedCello.getRange()));
            do {
                composer.compose().evolve();
            } while (composer.getConservetory().size() < 3);
        } catch (Exception ex) {
            Logger.getLogger(TestCenter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Composer getComposer() {
        return composer;
    }
}
