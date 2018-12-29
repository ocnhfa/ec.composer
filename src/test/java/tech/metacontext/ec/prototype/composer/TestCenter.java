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
import tech.metacontext.ec.prototype.composer.model.Composer;

/**
 *
 * @author Jonathan
 */
public class TestCenter {

    public static final int PRESET_POPULATION_SIZE = 100;
    public static final int GOAL_CONSERVATORY_SIZE = 3;
    private static TestCenter instance;
    private static Composer composer;

    public static synchronized TestCenter getInstance() {
        if (instance == null) {
            instance = new TestCenter();
        }
        return instance;
    }

    private TestCenter() {
        try {
            composer = new Main(PRESET_POPULATION_SIZE,
                    GOAL_CONSERVATORY_SIZE,
                    Settings.TEST).getComposer();
        } catch (Exception ex) {
            Logger.getLogger(composer.getId()).log(Level.SEVERE, null, ex);
        }
    }

    public Composer getComposer() {
        return composer;
    }
}
