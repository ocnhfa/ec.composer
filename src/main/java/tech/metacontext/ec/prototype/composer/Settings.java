/*
 * Copyright 2018 Jonathan Chang.
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

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author Jonathan Chang
 */
public class Settings {

    /**
     * Score for a mature composition to reach to be conserved.
     */
    public static final double SCORE_CONSERVE_IF_COMPLETED = 0.9;
    /**
     * Chance for a mature composition to be elongated.
     */
    public static final double CHANCE_ELONGATION_IF_COMPLETED = 0.1;
    /**
     * Chance for a mature composition to do crossover than mutation.
     */
    public static final double CHANCE_CROSSOVER_IF_COMPLETED = 0.6;

    /**
     * Chance to produce a Total Connecting State
     */
    public static final double CHANCE_TOTAL_CONNECTING_STATE = 0.2;
    /**
     * Chance to preserve when producing a Repetition Transform Type
     */
    public static final double CHANCE_REPETITION = 0.2;
    /**
     * Chance to preserve when producing a MoveForward Transform Type
     */
    public static final double CHANCE_MOVEFORWARD = 0.8;
    /**
     * Chance to preserve when producing a MoveBackward Transform Type
     */
    public static final double CHANCE_MOVEBACKWARD = 0.8;
    /**
     * Chance to preserve when producing a Retrograde Transform Type
     */
    public static final double CHANCE_RETROGRADE = 0.6;
    /**
     * Chance to preserve when producing a Disconnected Transform Type
     */
    public static final double CHANCE_DISCONNECTED = 1.0;

    public static String getTimeBasedFilename() {
        return LocalDateTime.now().toString().replace(":", "-").substring(0, 25);
    }

    public static String logfile = "src/main/resources/log/";

    public static String logfile_test = "src/main/resources/log/test/";

    public static int DEFAULT = 0, TEST = 1;

    public static void setFileHandler(int STATE, Logger logger)
            throws Exception {

        logger.log(Level.INFO,
                "Setting FileHandler, STATE = {0}", STATE);

        FileHandler fh;
        fh = new FileHandler(
                ((STATE == TEST) ? logfile_test : logfile)
                + getTimeBasedFilename() + ".log",
                true);
        fh.setEncoding("UTF-8");
        fh.setFormatter(new SimpleFormatter());
//        setFileHandler(logger);
        logger.setUseParentHandlers(false);
        logger.addHandler(fh);
    }
}
