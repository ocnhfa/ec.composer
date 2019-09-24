package tech.metacontext.ec.prototype.composer;

/*
 * Copyright 2019 Administrator.
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
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static tech.metacontext.ec.prototype.composer.Settings.*;
import tech.metacontext.ec.prototype.composer.model.Composer;
import tech.metacontext.ec.prototype.composer.styles.GoldenSectionClimax;
import tech.metacontext.ec.prototype.composer.styles.UnaccompaniedCello;
import tech.metacontext.ec.prototype.draw.LineChart_AWT;

/**
 *
 * @author Administrator
 */
public class Main {

    /**
     * Entry point of main.
     *
     * @param args: <code>POPULATION</code>, <code>GENERATION/SELECTED<code>, <code>RANDOM_SEED<code>
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        var params = Stream.of(args)
                .map(arg -> arg.split("="))
                .filter(s -> s.length == 2)
                //                .peek(s -> System.out.printf("%s=%s\n", s[0], s[1]))
                .collect(Collectors.toMap(s -> s[0], s -> s[1]));

        var pop_size = getParam(params, "POPULATION",
                DEFAULT_POP_SIZE, Integer::valueOf);

        var generation = getParam(params, "GENERATION",
                DEFAULT_GENERATION, Integer::valueOf);

        var selected_size = switch (generation) {
            case -1,0->
                getParam(params, "SELECTED", DEFAULT_SELECTED_SIZE, Integer::valueOf);
            default-> {
                if (getParam(params, "SELECTED", -1, Integer::valueOf) > 0)
                    System.out.println("GENERATION specified, SELECTED ignored.");
                yield -1;
            }
        };

        var random_seed = getParam(params, "RANDOM_SEED",
                RANDOM_SEED, Long::valueOf);

        initialize(random_seed);

        Studio studio = new Studio(
                pop_size,
                selected_size,
                generation,
                LogState.DISABLED);
        //                LogState.DEFAULT);

//        main.composer.draw(Composer.DRAWTYPE_AVERAGELINECHART);
        studio.getComposer()
                .draw(Composer.DRAWTYPE_COMBINEDCHART);
        System.out.println(header("Persisting Conservatory"));
        
        studio.getComposer()
                .persistAll();

        var chart = new LineChart_AWT("Composer " + studio.getComposer().getId());
        var gsc = new GoldenSectionClimax(UnaccompaniedCello.RANGE.keySet());

        studio.getComposer()
                .getConservatory().keySet().stream()
                .sorted((o1, o2) -> o1.getId().compareTo(o2.getId()))
                .peek(gsc::updateClimaxIndexes)
                .forEach(c
                        -> IntStream.range(0, c.getSize())
                        .forEach(i
                                // -> chart.addData(gsc.climaxIndex(c.getRendered().get(i)), c.getId_prefix(), "" + i)
                                -> chart.addData(gsc.getClimaxIndexes().get(i), c.getId_prefix(), "" + i)
                        ));
        var max = studio.getComposer().getConservatory().keySet().stream()
                .max(gsc::compareToPeak)
                .get();

        IntStream.range(
                0, max.getSize())
                .forEach(i -> {
                    chart.addData(gsc.getStandard(max, i), "standard", "" + i);
                }
                );
        chart.createLineChart(
                "SketchNode Rating Chart",
                "SketchNode", "Intensity Index", 560, 367, true);
        chart.showChartWindow();
    }

    public static <T> T getParam(Map<String, String> params, String key, T default_value, Function<String, T> function) {

        try {
            var value = params.get(key);
            if (Objects.nonNull(value)) {
                System.out.println(key + "=" + value);
                return function.apply(value);
            }
        } catch (Exception ignore) {
            System.out.println("Illegal value, using default value instead: " + default_value);
        }
        return default_value;
    }
}
