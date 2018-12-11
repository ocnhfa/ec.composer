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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import tech.metacontext.ec.prototype.composer.styles.GoldenSectionClimax;
import tech.metacontext.ec.prototype.composer.styles.UnaccompaniedCello;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class Main {

    public static void main(String[] args) {

        int size = 10;
        Composer composer = new Composer(size);

        composer.setStyles(Arrays.asList(
                new UnaccompaniedCello(),
                new GoldenSectionClimax()
        ));

        int generation = 5;
        List<List<Composition>> archive = new ArrayList<>();

        System.out.println(composer.getPopulation());
        
        for (int i = 0; i < generation; i++) {
            archive.add(composer.evolve());
        }

        System.out.println("---------------------------------");
        IntStream.range(0, 5)
                .peek(i -> System.out.println("Generation " + i))
                .mapToObj(archive::get)
                .forEach(list -> list.stream().forEach(Composition::render));
    }

}
