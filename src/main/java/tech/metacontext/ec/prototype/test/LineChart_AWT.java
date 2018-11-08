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
package tech.metacontext.ec.prototype.test;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class LineChart_AWT extends ApplicationFrame {

  public LineChart_AWT(String applicationTitle) {
    super(applicationTitle);
  }

  public void addLineChart(String chartTitle, String xLabel, String yLabel,
          CategoryDataset dataset) {
    JFreeChart lineChart = ChartFactory.createLineChart(
            chartTitle, xLabel, yLabel, dataset,
            PlotOrientation.VERTICAL,
            true, true, false);

    ChartPanel chartPanel = new ChartPanel(lineChart);
    chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
    setContentPane(chartPanel);
  }

  private DefaultCategoryDataset createDataset() {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    dataset.addValue(15, "schools", "1970");
    dataset.addValue(30, "schools", "1980");
    dataset.addValue(60, "schools", "1990");
    dataset.addValue(120, "schools", "2000");
    dataset.addValue(240, "schools", "2010");
    dataset.addValue(300, "schools", "2014");
    return dataset;
  }

  public static void main(String[] args) {
    LineChart_AWT chart = new LineChart_AWT(
            "School Vs Years");
    chart.addLineChart("Numer of Schools vs years",
            "Years", "Number of Schools", chart.createDataset());

    chart.pack();
    RefineryUtilities.centerFrameOnScreen(chart);
    chart.setVisible(true);
  }
}
