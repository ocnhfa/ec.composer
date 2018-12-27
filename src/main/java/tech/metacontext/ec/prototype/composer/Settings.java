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
     * 當作品達到目標之後，還繼續加長的機率。
     */
    public static final double ELONGATION_CHANCE = 0.1;
    /**
     * 作品收入conservatory所須達到的分數。
     */
    public static final double CONSERVE_SCORE = 0.9;
    public static final double CROSSOVER_CHANCE_IF_COMPLETED = 0.8;

    public static String getTimeBasedFilename() {
        return LocalDateTime.now().toString().replace(":", "-");
    }

    public static String logfile = "src/main/resources/log/";

    public static String logfile_test = "src/main/resources/log/test/";

    public static FileHandler fh;
    public static String path;

    public static void setFileHandler(Logger logger) {

        if (Objects.isNull(fh)) {
            return;
        }
        logger.setUseParentHandlers(false);
        logger.addHandler(fh);
    }

    public static int USE_EXISTING = 0, RENEW = 1, RENEW_TEST = 2;

    public static void setFileHandler(int STATE, Logger logger)
            throws Exception {

//        if (Objects.isNull(fh) || STATE != USE_EXISTING) {
//            fh = new FileHandler(
//                    ((STATE == RENEW_TEST) ? logfile_test : logfile)
//                    + getTimeBasedFilename() + ".log",
//                    true);
//            fh.setEncoding("UTF-8");
//            fh.setFormatter(new SimpleFormatter());
//        }
//        setFileHandler(logger);
        logger.log(Level.INFO,
                "Setting FileHandler, STATE = {0}", STATE);
    }
}
