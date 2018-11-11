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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;
import tech.metacontext.ec.prototype.abs.Population;
import tech.metacontext.ec.prototype.render.LineChart_AWT;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class MusicalIdeas extends Population<TensionCurve, Double> {

  int counter = 0;

  public MusicalIdeas(int size) {
    super(size);
    this.evalutaion = this::eval;
    this.selector = this::selector;
    this.operators.put("crossover", this::crossover);
    this.operators.put("mutation", this::mutation);
  }

  public List<TensionCurve> crossover(TensionCurve... e) {
    TensionCurve tc1 = e[0], tc2 = e[1];
    int csSize = (tc1.curve.size() > tc2.curve.size()) ? tc2.curve.size() : tc1.curve.size(),
            csPt = new Random().nextInt(csSize) + 1;
    List<Integer> c1 = new ArrayList<>(tc1.curve.subList(0, csPt - 1));
    c1.addAll(tc2.curve.subList(csPt - 1, tc2.curve.size()));
    List<Integer> c2 = new ArrayList<>(tc2.curve.subList(0, csPt - 1));
    c2.addAll(tc1.curve.subList(csPt - 1, tc1.curve.size()));
    TensionCurve tccs1 = new TensionCurve(++counter + "", c1),
            tccs2 = new TensionCurve(++counter + "", c2);
    List<TensionCurve> result = new ArrayList<>();
    result.add(eval(tccs1) > eval(tccs2) ? tccs1 : tccs2);
    System.out.println("Crossover occurred on TC#" + tc1.getId() + ", " + tc2.getId() + " at " + csPt);
    return result;
  }

  public List<TensionCurve> mutation(TensionCurve... e) {
    List<Integer> curve = e[0].curve;
    int locus = new Random().nextInt(curve.size()),
            value = new Random().nextInt(21) - 10;
    curve.set(locus, value);
    System.out.println("Mutation occurred on TC#" + e[0].getId() + " at " + locus);
    return Arrays.asList(e);
  }

  public void selector() {
    Double threshold = this.population.values().stream()
            .mapToDouble(a -> a)
            .average().getAsDouble();
    this.population.values().removeIf(value -> value < threshold);
  }

  public Double eval(TensionCurve tc) {
    int max = 0, min = 0, tension = 0;
    for (Integer delta : tc.curve) {
      tension += delta;
      max = (max < tension) ? tension : max;
      min = (tension < min) ? tension : min;
    }
    int diff = max - min;
    return 5 - ((diff - 5) * 0.5);
  }

  @Override
  public void initiate() {
    for (int i = 0; i < size; i++) {
      TensionCurve tc = new TensionCurve(++counter + "").generateRandom();
      this.population.put(tc, this.eval(tc));
    }
  }

  @Override
  public int evolution() {
    int size_before = this.population.size();
    System.out.println("size before selection = " + size_before);
    this.selector();
    int size_after = this.population.size();
    System.out.println("size after selection = " + size_after);
    int size_diff = size_before - size_after;
    System.out.println("size different = " + size_diff);
    List<TensionCurve> keySet = new ArrayList(this.population.keySet());
    for (int i = 0; i < size_diff; i++) {
      int p1 = new Random().nextInt(size_after), p2 = p1;
      while (p1 == p2) {
        p2 = new Random().nextInt(size_after);
      }
      TensionCurve child = crossover(keySet.get(p1), keySet.get(p2)).get(0);
      this.population.put(child, this.eval(child));
      this.mutation(keySet.get(new Random().nextInt(size_after)));
    }
    System.out.println("size of new generation = " + this.population.size());
    return size_diff;
  }

  private int index;
  private int render_generation;
  private LineChart_AWT chart;

  @Override
  public void render() {
    chart = new LineChart_AWT("Musical Ideas");
    index = 1;
    this.population.keySet().forEach(this::render);
    chart.createLineChart("Tension Curves, generation = " + render_generation,
            "Time", "Tension Level", 560, 367);
    RefineryUtilities.positionFrameOnScreen(chart,
            render_generation * 0.1 % 1.0,
            render_generation * 0.1 % 1.0);
    chart.showChartWindow();
  }

  public void render(int i) {
    this.render_generation = i;
    this.render();
  }

  public void render(TensionCurve tc) {
    int tension = 0;
//    dataset.addValue(0, "" + index, "0");
    for (int i = 0; i < tc.curve.size(); i++) {
      tension += tc.curve.get(i);
      System.out.printf("%3d ", tension);
      chart.addData(tension, "" + index, "" + (i + 1));
    }
    System.out.println();
    index++;
  }

}
