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
package tech.metacontext.ec.prototype.composer.connectors;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;
import tech.metacontext.ec.prototype.abs.Individual;
import tech.metacontext.ec.prototype.composer.SketchNode;
import tech.metacontext.ec.prototype.composer.materials.enums.MaterialType;
import tech.metacontext.ec.prototype.composer.materials.enums.TransformType;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class Connector extends Individual {

    private final Map<MaterialType, TransformType> transformTypes;
    private Predicate<SketchNode> styleChecker;
    private SketchNode previous;
    private SketchNode next;

    public Connector(
            //            SketchNode previous,
            Predicate<SketchNode> styleChecker
    ) {

        this.transformTypes = new HashMap<>();
        this.styleChecker = styleChecker;
//        this.previous = previous;
//        this.next = Stream.generate(SketchNode::new)
//                .filter(this.styleChecker)
//                .findFirst()
//                .get();
    }

    public Connector(Connector conn) {

        super(conn.getId());
        this.transformTypes = new HashMap<>(conn.getTransformTypes());
        this.styleChecker = conn.getStyleChecker();
        this.previous = (conn.previous == null)
                ? null : new SketchNode(conn.previous);
        this.next = (conn.next == null)
                ? null : new SketchNode(conn.next);
    }

    public void addTransformType(MaterialType mt, TransformType tt) {

        this.transformTypes.put(mt, tt);
    }

    @Override
    public String toString() {

        return "Connector: " + previous + " => " + next;
    }

    /*
     * Default setters and getters
     */
    public SketchNode getPrevious() {
        return previous;
    }

    public void setPrevious(SketchNode previous) {
        this.previous = previous;
    }

    public SketchNode getNext() {
        return next;
    }

    public void setNext(SketchNode next) {
        this.next = next;
    }

    public Predicate<SketchNode> getStyleChecker() {
        return styleChecker;
    }

    public void setStyleChecker(Predicate<SketchNode> styleChecker) {
        this.styleChecker = styleChecker;
    }

    public Map<MaterialType, TransformType> getTransformTypes() {
        return transformTypes;
    }

}
