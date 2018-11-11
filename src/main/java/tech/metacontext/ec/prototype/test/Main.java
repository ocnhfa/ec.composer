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

import java.io.IOException;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class Main {
//通常是先 eval -> select -> crossover/mutation
//因為構想是比較好的人可以繁衍下一代
//steady state, generational model

  public static void main(String[] args) throws IOException {
    MusicalIdeas p = new MusicalIdeas(50);
    p.render(0);
    for (int i = 0; i < 9; i++) {
//      System.in.read();
      System.out.println("Generation " + i);
      int diff = p.evolution();
      p.render(i + 1);
      if (diff == 0) {
        break;
      }
    }
  }
}
