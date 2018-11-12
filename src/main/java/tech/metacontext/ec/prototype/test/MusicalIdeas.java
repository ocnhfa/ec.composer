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
import java.util.HashMap;
import java.util.List;
import java.util.Random;
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
    super(size, new CurveEvaluation(), new AverageSelector());
    this.operators.put("crossover", this::crossover);
    this.operators.put("mutation", this::mutation);
  }

  @Override
  public void initiate(int size) {
    for (int i = 0; i < size; i++) {
      TensionCurve tc = new TensionCurve(++counter + "").generateRandom();
      this.add(tc);
    }
  }

  @Override
  public int evolution() {
    int size_originl = this.size();
    System.out.println("original size = " + size_originl);

    int size_after = this.selection.selector(this);
    System.out.println("selected size = " + size_after);

    List<TensionCurve> curves = new ArrayList(this.population.keySet());
    this.population = new HashMap<>();

    while (size() < size_originl) {
      int p1 = new Random().nextInt(size_after), p2 = p1;
      while (p1 == p2) {
        p2 = new Random().nextInt(size_after);
      }
      this.add(this.operators.get("crossover")
              .operator(curves.get(p1), curves.get(p2)).get(0));
      this.operators.get("mutation")
              .operator(curves.get(new Random().nextInt(size_after)));
    }
    return size_originl - size_after;
  }

  public Double eval(TensionCurve tc) {
    int max = 0, min = 0, tension = 0;
    for (Integer delta : tc.getCurve()) {
      tension += delta;
      max = (max < tension) ? tension : max;
      min = (tension < min) ? tension : min;
    }
    int diff = max - min;
    return 5 - ((diff - 5) * 0.5);
  }

  public List<TensionCurve> crossover(TensionCurve... e) {
    TensionCurve tc1 = e[0], tc2 = e[1];
    int csSize = (tc1.getCurve().size() > tc2.getCurve().size()) ? tc2.getCurve().size() : tc1.getCurve().size(),
            csPt = new Random().nextInt(csSize) + 1;
    List<Integer> c1 = new ArrayList<>(tc1.getCurve().subList(0, csPt - 1));
    c1.addAll(tc2.getCurve().subList(csPt - 1, tc2.getCurve().size()));
    List<Integer> c2 = new ArrayList<>(tc2.getCurve().subList(0, csPt - 1));
    c2.addAll(tc1.getCurve().subList(csPt - 1, tc1.getCurve().size()));
    TensionCurve tccs1 = new TensionCurve(++counter + "", c1),
            tccs2 = new TensionCurve(++counter + "", c2);
    List<TensionCurve> result = new ArrayList<>();
    result.add(eval(tccs1) > eval(tccs2) ? tccs1 : tccs2);
//      System.out.println("Crossover occurred on TC#" + tc1.getId() + ", " + tc2.getId() + " at " + csPt);
    return result;
  }

  public List<TensionCurve> mutation(TensionCurve... e) {
    List<Integer> curve = e[0].getCurve();
    int locus = new Random().nextInt(curve.size()),
            value = new Random().nextInt(21) - 10;
    curve.set(locus, value);
    return Arrays.asList(e);
  }

  private int index;
  private int render_generation;
  private LineChart_AWT chart;
  private static double x, y;

  @Override
  public void render() {
    chart = new LineChart_AWT("Musical Ideas");
    index = 1;
    this.population.keySet().forEach(this::render);
    chart.createLineChart("Tension Curves, generation = " + render_generation,
            "Time", "Tension Level", 560, 367, false);
    x = (x + 0.05) % 1.0;
    y = (y + 0.05) % 1.0;
    RefineryUtilities.positionFrameOnScreen(chart, x, y);
    chart.showChartWindow();
  }

  public void render(int i) {
    this.render_generation = i;
    this.render();
  }

  public void render(TensionCurve tc) {
    List<Integer> tensions = new ArrayList<>();
    int tension = 0;
    for (int i = 0; i < tc.getCurve().size(); i++) {
      tension += tc.getCurve().get(i);
      tensions.add(tension);
    }
    int min = tensions.stream().reduce(Math::min).get();
//      System.out.println("min = " + min);
    for (int i = 0; i < tensions.size(); i++) {
//      System.out.printf("%d(%d) ", tensions.get(i) - min, tc.curve.get(i));
      chart.addData(tensions.get(i) - min, "" + index, "" + i);
    }
//    System.out.println();
    index++;
  }

  public static void main(String[] args) {
    MusicalIdeas test = new MusicalIdeas(0);
    TensionCurve tc1 = new TensionCurve("1");
    tc1.generateRandom();
    test.add(tc1);
    test.render(1);
    TensionCurve tc2 = new TensionCurve("2", tc1.getCurve());
    test.add(tc2);
    test.render(2);
  }

}
