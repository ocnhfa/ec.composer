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
package tech.metacontext.ec.prototype.render;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.ScatterRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.DefaultMultiValueCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * Rendering a Line Chart.
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class CombinedChart_AWT extends ApplicationFrame {

    public static void main(String[] args) {

        CombinedChart_AWT chart = new CombinedChart_AWT("Demo");

        List<SimpleEntry<Integer, Double>> series1A = new ArrayList<>(),
                series1B = new ArrayList<>(),
                series2 = new ArrayList<>();

        IntStream.range(1, 50)
                .forEach(i -> {
                    new Random().doubles(10, Math.random(), (50.0 + i) / 50 + Math.random())
                            .mapToObj(value -> new SimpleEntry<>(i, value))
                            .forEach(series1A::add);
                    new Random().doubles(10, Math.random(), (50.0 + i) / 50 + Math.random())
                            .mapToObj(value -> new SimpleEntry<>(i, value))
                            .forEach(series1B::add);
                    series2.add(new SimpleEntry<>(i, series1A.stream()
                            .mapToDouble(SimpleEntry::getValue)
                            .average().getAsDouble()));
                });
        double size = 3.0;
        double delta = size / 2.0;
        Shape shape = new Ellipse2D.Double(-delta, -delta, size, size);
        ScatterRenderer sr1 = new ScatterRenderer();
        sr1.setSeriesPaint(0, Color.GRAY);
        sr1.setSeriesShape(0, shape);
        ScatterRenderer sr2 = new ScatterRenderer();
        sr2.setSeriesPaint(0, Color.RED);
        sr2.setSeriesShape(0, shape);
        chart.addRenderer(0, "Series1A", series1A, sr1);
        chart.addRenderer(1, "Series1B", series1B, sr2);

        chart.addRenderer(2, "Series2", series2, new LineAndShapeRenderer());
        chart.createChart("HelloWorld", "Generation", "Score", 560, 367, true);
        chart.showChartWindow();
    }

    private final CategoryPlot plot;
    private JFreeChart chart;

    public CombinedChart_AWT(String applicationTitle) {

        super(applicationTitle);
        plot = new CategoryPlot();
    }

    public void addRenderer(
            int index, String series,
            List<SimpleEntry<Integer, Double>> data,
            CategoryItemRenderer renderer) {
        CategoryDataset dataset;
        if (renderer instanceof ScatterRenderer) {
            System.out.println("ScatterRenderer");
            dataset = new DefaultMultiValueCategoryDataset();
            data.stream()
                    .collect(Collectors.groupingBy(e -> e.getKey()))
                    .forEach((key, list)
                            -> ((DefaultMultiValueCategoryDataset) dataset).add(list.stream()
                            .map(SimpleEntry::getValue).collect(Collectors.toList()), series, key));
        } else {
            dataset = new DefaultCategoryDataset();
            data.stream()
                    .forEach(e -> ((DefaultCategoryDataset) dataset).addValue(e.getValue(), series, e.getKey()));
        }
        plot.setDataset(index, dataset);
        plot.setRenderer(index, renderer);
    }

    public void createChart(String chartTitle, String xLabel, String yLabel,
            int x, int y, boolean legend) {

        plot.setDomainAxis(new CategoryAxis(xLabel));
        plot.setRangeAxis(new NumberAxis(yLabel));
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
        chart = new JFreeChart(plot);
        chart.setTitle(chartTitle);
        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new java.awt.Dimension(x, y));
        setContentPane(panel);

        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        CategoryAxis xAxis = plot.getDomainAxis();
        xAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 2.0));
    }

    public void showChartWindow() {

        this.setVisible(true);
    }

    /*
     * Default setters and getters.
     */
    public JFreeChart getChart() {
        return chart;
    }

    public void setChart(JFreeChart chart) {
        this.chart = chart;
    }

    public CategoryPlot getPlot() {
        return plot;
    }

}
