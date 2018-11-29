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
package tech.metacontext.ec.prototype.composer2;

import tech.metacontext.ec.prototype.composer2.styles.Style;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class Composer {

  private List<Composition> compositions;
  private List<Style> styles;

  public Composer(int size) {

    this.compositions = Stream.generate(Composition::new)
            .limit(size)
            .collect(Collectors.toList());
    this.styles = new ArrayList<>();
  }

  public List<Composition> getCompositions() {

    return compositions;
  }

  public void setCompositions(List<Composition> compositions) {

    this.compositions = compositions;
  }

  public List<? extends Style> getStyles() {

    return this.styles;
  }

  public void setStyles(List<Style> styles) {

    this.styles = styles;
  }

  public void addStyle(Style style) {

    this.styles.add(style);
  }

  public List<Composition> compose() {

    return this.compositions;
  }

}
