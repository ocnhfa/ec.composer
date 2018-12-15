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

import java.util.Arrays;
import java.util.stream.IntStream;
import tech.metacontext.ec.prototype.composer.enums.ComposerAim;
import tech.metacontext.ec.prototype.composer.styles.GoldenSectionClimax;
import tech.metacontext.ec.prototype.composer.styles.UnaccompaniedCello;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class Main {

    // 決定作品數量及演進世代
    static int size = 5;
    static int generation = 20;

    public static void main(String[] args) {

        System.out.println(header("創建作曲家物件"));
        Composer composer = new Composer(size, ComposerAim.Phrase,
                new UnaccompaniedCello(),
                new GoldenSectionClimax()
        );

        System.out.println(header("Evolution"));
        do {
            System.out.println("Generation = " + composer.getGenCount());
            composer.getArchive().add(composer.getPopulation());
            composer.compose();
            composer.evolve();
        } while (composer.getGenCount() < generation);

        System.out.println(header("archive"));
        IntStream.range(0, generation)
                .peek(i -> System.out.println("Generation " + i))
                .mapToObj(composer.getArchive()::get)
                .forEach(list -> list.stream().forEach(Composition::render));

        System.out.println(header("conservatory"));
        composer.getConservetory().forEach(System.out::println);
    }

    static String header(String text) {
        return "---------- " + text + " ----------";
    }

}
