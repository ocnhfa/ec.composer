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

import java.util.Objects;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.io.File;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Random;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class Settings {

    public static void main(String[] args) {
        
        System.out.println(new Random().nextLong());
    }
    
    public static long RANDOM_SEED = 4131318980864143334L;
    private static Random r;

    public static void initialize(long seed) {

        RANDOM_SEED = seed;
        r = new Random(seed);
    }

    public static Random getRandom() {

        if (Objects.isNull(r))
            r = new Random(RANDOM_SEED);
        return r;
    }

    public static String LOG_PATH = "log/";
    public static String LOG_PATH_TEST = "log/test/";
    public static String SER_PATH = "ser/";

    public static enum LogState {

        DEFAULT, TEST, DISABLED
    }

    public static String getTimeBasedFilename() {
        return LocalDateTime.now().toString().replace(":", "-").substring(0, 25);
    }

    public static void setFileHandler(LogState STATE, Logger logger)
            throws Exception {

        logger.log(Level.INFO,
                "Setting FileHandler, STATE = {0}", STATE);
        logger.setUseParentHandlers(false);

        File file_path = switch (STATE) {
            case DISABLED->
                null;
            case TEST->
                new File(LOG_PATH_TEST);
            case DEFAULT->
                new File(LOG_PATH);
        };
        if (Objects.isNull(file_path)) {
            logger.setUseParentHandlers(false);
        } else {
            file_path.mkdirs();
            FileHandler fh = new FileHandler(Path.of(file_path.getPath(),
                    getTimeBasedFilename() + ".log").toString(), true);
            fh.setEncoding("UTF-8");
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
        }
    }

    public static String header(String text) {

        return "\n---------- " + text + " ----------";
    }
}
