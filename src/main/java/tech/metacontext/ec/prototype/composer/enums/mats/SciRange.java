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
package tech.metacontext.ec.prototype.composer.enums.mats;

/**
 *
 * @author Jonathan
 */
public enum SciRange {

    C0, C1, C2, C3, C4, C5, C6, C7, C8;

    public static double getIntensityIndex(SciRange range, SciRange lowest, SciRange highest) {

        if (range.ordinal() < lowest.ordinal()) {
            return 0.0;
        }
        if (range.ordinal() > highest.ordinal()) {
            return 1.0;
        }
        return 1.0 * (range.ordinal() - lowest.ordinal() + 1) / (highest.ordinal());
    }
}
