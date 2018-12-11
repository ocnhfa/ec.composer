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
package tech.metacontext.ec.prototype.composer.rules;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import tech.metacontext.ec.prototype.composer.materials.enums.Pitch;
import tech.metacontext.ec.prototype.composer.materials.PitchSets;

/**
 * TODO.
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class SmoothPitchSets implements Rule {

    private PitchSets p1;
    private PitchSets p2;

    public SmoothPitchSets(PitchSets p1, PitchSets p2) {

        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public double rating() {

        AtomicInteger common = new AtomicInteger(),
                base = new AtomicInteger();
        int max = Math.max(p1.size(), p2.size());
        System.out.println("max = " + max);
        Stream.of(Pitch.values())
                .forEach(p -> {
                    IntStream.range(0, max)
                            .forEach(i -> {
                                int p1ci = Math.max(p1.size() - 1 - i, 0),
                                        p2ci = Math.min(p2.size() - 1, i);
                                boolean p1c = p1.getMaterials().get(p1ci)
                                        .getMaterials().contains(p),
                                        p2c = p2.getMaterials().get(p2ci)
                                                .getMaterials().contains(p);
                                if (p1c || p2c) {
                                    base.getAndAdd(max - i);
//                          System.out.println("base + " + (max - i));
                                    if (p1c && p2c) {
                                        common.getAndAdd(max - i);
//                            System.out.println("common + " + (max - i));
                                    }
                                }
                            });
                });
        System.out.println("common = " + common);
        System.out.println("base = " + base);
        return 1.0 * common.get() / base.get();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            PitchSets ps1 = new PitchSets().random();
            PitchSets ps2 = new PitchSets().random();
            SmoothPitchSets sps = new SmoothPitchSets(ps1, ps2);
            System.out.println(ps1);
            System.out.println(ps2);
            System.out.printf("%.2f\n", sps.rating());
        }
    }

    /*
     * default setters and getters
     */
    public PitchSets getP1() {
        return p1;
    }

    public void setP1(PitchSets p1) {
        this.p1 = p1;
    }

    public PitchSets getP2() {
        return p2;
    }

    public void setP2(PitchSets p2) {
        this.p2 = p2;
    }

}
