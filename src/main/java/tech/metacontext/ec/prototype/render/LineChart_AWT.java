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

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * Rendering a Line Chart.
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class LineChart_AWT extends ApplicationFrame {

  DefaultCategoryDataset dataset;
  JFreeChart lineChart;

  public LineChart_AWT(String applicationTitle) {

    super(applicationTitle);
    dataset = new DefaultCategoryDataset();
  }

  public void addData(double value, String rowKey, String colKey) {

    dataset.addValue(value, rowKey, colKey);
  }

  public void createLineChart(String chartTitle, String xLabel, String yLabel,
          int x, int y) {

    lineChart = ChartFactory.createLineChart(
            chartTitle, xLabel, yLabel, dataset,
            PlotOrientation.VERTICAL,
            true, true, false);

    ChartPanel chartPanel = new ChartPanel(lineChart);
    chartPanel.setPreferredSize(new java.awt.Dimension(x, y));
    setContentPane(chartPanel);

    this.pack();
    RefineryUtilities.centerFrameOnScreen(this);

  }

  public void showChartWindow() {

    this.setVisible(true);
  }

}
