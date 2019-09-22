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
import java.util.stream.IntStream;
import static tech.metacontext.ec.prototype.composer.Settings.*;
import tech.metacontext.ec.prototype.composer.Studio;
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
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        // 決定作品數量及演進世代
        int POP_SIZE = 100;
        int SELECTED_SIZE = 0;
        int GENERATION = 300;

//        long RANDOM_SEED = 1L;
//        initialize(RANDOM_SEED);

        Studio studio = new Studio(
                POP_SIZE,
                SELECTED_SIZE,
                GENERATION,
                LogState.DISABLED);
        //                LogState.DEFAULT);

//        main.composer.draw(Composer.DRAWTYPE_AVERAGELINECHART);
        studio.getComposer().draw(Composer.DRAWTYPE_COMBINEDCHART);
        System.out.println(header("Persisting Conservatory"));
        studio.getComposer().persistAll();

        var chart = new LineChart_AWT("Composer " + studio.getComposer().getId());
        var gsc = new GoldenSectionClimax(UnaccompaniedCello.RANGE.keySet());
        studio.getComposer().getConservatory().keySet().stream()
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
        IntStream.range(0, max.getSize())
                .forEach(i -> {
                    chart.addData(gsc.getStandard(max, i), "standard", "" + i);
                });
        chart.createLineChart("SketchNode Rating Chart",
                "SketchNode", "Intensity Index", 560, 367, true);
        chart.showChartWindow();
    }
}
