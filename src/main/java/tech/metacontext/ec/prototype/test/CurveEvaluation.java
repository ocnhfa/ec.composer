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

import java.util.Collections;
import java.util.List;
import tech.metacontext.ec.prototype.abs.Evaluation;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class CurveEvaluation implements Evaluation<TensionCurve, Double> {

  @Override
  public Double eval(TensionCurve individual) {
    List<Integer> tensions = individual.getTensionCurve();
    double range = range(tensions), climax_count = climax_count(tensions),
            climax_position = climax_position(tensions);
//    System.out.printf("range = %2.2f, climax_count = %2.2f, "
//            + "climax_position = %2.2f, Total = %2.2f\n",
//            range, climax_count, climax_position, range + climax_count + climax_position);
    return range + climax_count + climax_position;
  }

  public static Double range(List<Integer> tensions) {
    int max = Collections.max(tensions), min = Collections.min(tensions);
    return 10.0 / (1 + (Math.abs(max - min - 10.0) * 0.5));
  }

  public static Double climax_count(List<Integer> tensions) {
    int max = Collections.max(tensions);
    long climax_count = tensions.stream().filter(t -> t == max).count();
    if (tensions.get(0) == max) {
      climax_count++;
    }
    if (tensions.get(tensions.size() - 1) == max) {
      climax_count++;
    }
    return 10.0 / climax_count;
  }

  public static Double climax_position(List<Integer> tensions) {
    int max = Collections.max(tensions);
    double position = 2.0 * tensions.size() / 3.0;
    double rating = 10.0;
    for (int i = 0; i < tensions.size(); i++) {
      if (tensions.get(i) == max) {
        rating /= (1.0 + Math.abs(position - i) / tensions.size());
      }
    }
    return rating;
  }

  public static void main(String[] args) {
    MusicalIdeas music;
    for (int i = 0; i < 10; i++) {
//      TensionCurve individual = new TensionCurve("test",
//              Stream.generate(Math::random).limit(20)
//                      .map(r -> (int) Math.floor((r - 0.5) * 10))
//                      .collect(Collectors.toList()));
      music = new MusicalIdeas(1);
      music.render(i);
      System.out.print(i + ". ");
      music.population.entrySet().forEach(e
              -> System.out.printf("%s -> %.2f\n",
                      e.getKey().getTensionCurve(),
                      e.getValue())
      );
//      System.out.println(individual.getTensionCurve() + " -> " + instance.eval(individual));
    }

  }
}
