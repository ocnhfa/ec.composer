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
import java.util.stream.Collectors;
import java.util.stream.Stream;
import tech.metacontext.ec.prototype.abs.Individual;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class TensionCurve extends Individual {

  private List<Integer> curve;
  static int size = 19;

  public TensionCurve(String id, List<Integer> curve) {
    
    super(id);
    this.curve = curve;
  }

    public TensionCurve(String id) {
      
    this(id, new ArrayList<>());
  }

  public TensionCurve(TensionCurve tc) {
    
    this(tc.getId(), new ArrayList<>(tc.curve));
  }

  public List<Integer> getCurve() {
    return curve;
  }

  public List<Integer> getTensionCurve() {
    
    int tension = 0;
    List<Integer> tensions = new ArrayList<>();
    tensions.add(0);
    for (int i = 0; i < curve.size(); i++) {
      tensions.add(tension += curve.get(i));
    }
    return tensions;
  }

  public TensionCurve generateRandom() {
    
    curve = Stream.generate(Math::random).limit(size)
            .map(r -> (int) ((r - 0.5) * 10))
            .collect(Collectors.toList());
    return this;
  }

  @Override
  public int hashCode() {
    
    int hash = super.hashCode();
    hash = 79 * hash + Arrays.deepHashCode(this.curve.toArray());
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    
    if (super.equals(obj)) {
      return true;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final TensionCurve other = (TensionCurve) obj;
    for (int i = 0; i < this.curve.size(); i++) {
      if (this.curve.get(i) != other.getCurve().get(i)) {
        return false;
      }
    }
//    System.out.println("All match, regarded as equal.");
    return true;
  }

  @Override
  public String toString() {
    
    String s = "";
    for (int i = 0; i < curve.size() - 1; i++) {
      s += String.format("%+4d,", curve.get(i));
    }
    s += String.format("%+4d", curve.get(curve.size() - 1));
    return s;
  }
}
