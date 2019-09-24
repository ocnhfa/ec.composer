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

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static tech.metacontext.ec.prototype.composer.Settings.*;
import static tech.metacontext.ec.prototype.composer.Parameters.*;
import tech.metacontext.ec.prototype.composer.model.*;
import tech.metacontext.ec.prototype.composer.styles.*;
import tech.metacontext.ec.prototype.composer.enums.ComposerAim;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class Studio {

    private Composer composer;

    /**
     * Main constructor.
     *
     * @param popSize
     * @param goalSize
     * @param generation
     * @param logState
     * @throws Exception
     */
    public Studio(int popSize,
            int goalSize,
            int generation,
            LogState logState) throws Exception {

        this(popSize, goalSize, generation,
                SELECTION_THRESHOLD.getDouble(),
                MIN_CONSERVE_SCORE.getDouble(),
                logState);
    }

    /**
     *
     * @param popSize
     * @param goalSize
     * @param generation
     * @param threshold
     * @param conserve_score
     * @param logState
     * @throws Exception
     */
    public Studio(int popSize,
            int goalSize,
            int generation,
            double threshold,
            double conserve_score,
            LogState logState) throws Exception {

        var gsc = new GoldenSectionClimax(UnaccompaniedCello.RANGE.keySet());
        Style[] styles = {new UnaccompaniedCello(), gsc};
        this.composer = new Composer(popSize, ComposerAim.Phrase,
                logState, threshold, conserve_score, styles);
        if (generation <= 300) {
            this.composer.ARCHIVE_TO_DISK = false;
        }
        System.out.println(header("Evolutionary Computation"));
        System.out.printf("Composer = [%s]\n", composer.getId());
        System.out.println("Population size = " + popSize);

        if (generation > 0)
            System.out.println("Generation = " + generation);
        else
            System.out.println("Selected (Conservatory) size = " + goalSize);

        System.out.println("Random Seed = " + RANDOM_SEED);
        System.out.println("Threshold = " + threshold);
        System.out.println("Conserve Score = " + conserve_score);
        System.out.println(header("Evolution"));
        int conserved = 0;
        do {
            if (composer.getGenCount() > 0) {
                if (composer.getGenCount() % 100 == 0) {
                    System.out.println(" (" + composer.getGenCount() + ")");
                } else if (composer.getGenCount() % 50 == 0) {
                    System.out.print("|");
                }
            }
            //
            composer.sketch().evolve();
            //
            if (composer.getConservatory().size() > conserved) {
                System.out.print(composer.getConservatory().size() - conserved);
                conserved = composer.getConservatory().size();
            } else {
                System.out.print(".");
            }
        } while (composer.getConservatory().size() < goalSize
                || composer.getGenCount() < generation);
        System.out.println(" (" + composer.getGenCount() + ")");

        composer.save();

        System.out.println(header("Dumping Archive"));

        if (composer.getArchive().isEmpty())
            composer.readArchive();

        IntStream.range(0, composer.getGenCount())
                .mapToObj(i -> String.format("%3d >> ", i) + Composer.getSummary(composer.getArchive().get(i)))
                .forEach(System.out::println);

        composer.getConservatory().keySet().stream()
                .sorted(gsc::comparator)
                .peek(c -> System.out.print(Composer.simpleScoreOutput(c) + "\n GSC: "))
                .map(c -> gsc.updateClimaxIndexes(c).stream().map(i -> String.format("%.2f", i)).collect(Collectors.joining(" ")))
                .forEach(System.out::println);
    }

    public Composer getComposer() {

        return composer;
    }

}
