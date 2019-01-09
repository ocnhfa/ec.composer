/*
 * Copyright 2019 Jonathan Chang, Chun-yien <ccy@musicapoetica.org>.
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class SciRangeSet {

    private List<SciRange> range_set;

    public SciRangeSet() {

        this.range_set = new ArrayList<>();
    }

    public SciRangeSet(List<SciRange> ranges) {

        this();
        this.range_set.addAll(ranges);
    }

    public SciRangeSet(SciRangeSet origin) {

        this(origin.getSciRange_set());
    }

    public SciRangeSet(int lowerbond, int upperbond) {

        this();
        IntStream.rangeClosed(lowerbond, upperbond)
                .mapToObj(ordinal -> SciRange.values()[ordinal])
                .forEach(this.range_set::add);
    }

    @Override
    public String toString() {
        return String.format("SciRangeSet[ %s ]",
                this.range_set.stream()
                        .sorted()
                        .map(Object::toString)
                        .collect(Collectors.joining(", ")));
    }

    public List<SciRange> moveForward(SciRange highest) {

        return this.getSciRange_set().stream()
                .map(r -> r.forward(highest))
                .collect(Collectors.toList());
    }

    public List<SciRange> moveBackward(SciRange lowest) {

        return this.getSciRange_set().stream()
                .map(r -> r.backward(lowest))
                .collect(Collectors.toList());
    }

    public static double getIntensityIndex(SciRangeSet rangeSet,
            SciRange lowest, SciRange highest) {

        return IntStream.rangeClosed(lowest.ordinal(), highest.ordinal())
                .filter(i -> rangeSet.getSciRange_set().contains(SciRange.valueOf(i)))
                .mapToDouble(i -> Math.pow(2, i - lowest.ordinal()))
                //                .peek(System.out::println)
                .sum() / getBase(highest.ordinal() - lowest.ordinal());
    }

    public static void main(String[] args) {

        System.out.println(getBase(SciRange.C8.ordinal() - SciRange.C5.ordinal()));
        new Random().ints(5, 8)
                .limit(100)
                .mapToObj(low -> new SciRangeSet(low, new Random().nextInt(8 - low + 1) + low))
                .peek(System.out::print)
                .map(rs -> SciRangeSet.getIntensityIndex(rs, SciRange.C5, SciRange.C8))
                .forEach(System.out::println);
//        for (int i = 0; i < 9; i++) {
//            System.out.println(getBase(i));;
//        }
    }

    public static int getBase(int coverage) {

        return IntStream.rangeClosed(0, coverage)
                .map(p -> (int) Math.pow(2, p))
                .sum();
    }

    /*
     * Default setters and getters.
     */
    public int getSize() {
        return this.range_set.size();
    }

    public List<SciRange> getSciRange_set() {
        return range_set;
    }

    public void setSciRange_set(List<SciRange> range_set) {

        this.range_set = range_set;
    }

}
