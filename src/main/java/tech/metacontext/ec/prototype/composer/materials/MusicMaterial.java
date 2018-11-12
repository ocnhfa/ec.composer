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
package tech.metacontext.ec.prototype.composer.materials;

import tech.metacontext.ec.prototype.composer.abs.AbstractElement;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 * @param <E> Element type of this material.
 */
public abstract class MusicMaterial<E> extends AbstractElement {

  public abstract void randomInit();

  public abstract void add(E element);

  public abstract void remove(E element);
}
