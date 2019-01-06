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
import static tech.metacontext.ec.prototype.composer.Settings.*;

/**
 *
 * @author Jonathan
 */
public class TestCenter {

    public static final int PRESET_POPULATION_SIZE = 100;
    public static final int GOAL_CONSERVATORY_SIZE = 2;
    public static final int GOAL_GENERATION = 0;
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
                    GOAL_GENERATION,
                    LogState.TEST).getComposer();
        } catch (Exception ex) {
            Logger.getGlobal().log(Level.SEVERE, null, ex);
        }
        composer.render(Composer.RENDERTYPE_AVERAGELINECHART);
        composer.render(Composer.RENDERTYPE_SCATTERPLOT);
    }

    public Composer getComposer() {
        return composer;
    }
}
