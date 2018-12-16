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

import java.util.function.Predicate;
import java.util.stream.Stream;
import tech.metacontext.ec.prototype.abs.Individual;
import tech.metacontext.ec.prototype.composer.SketchNode;
import tech.metacontext.ec.prototype.composer.connectors.ConnectorType.State;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class Connector extends Individual {

    private SketchNode previous;
    private SketchNode next;
    private State type;
    private Predicate<SketchNode> styleChecker;

    public Connector(SketchNode previous, Predicate<SketchNode> styleChecker, State type) {
        
        this.previous = previous;
        this.styleChecker = styleChecker;
        this.type = type;
        this.next = type.getNext(this.previous);
    }

    public Connector(SketchNode previous, Predicate<SketchNode> styleChecker) {

        this.previous = previous;
        this.styleChecker = styleChecker;
        this.next = Stream.generate(SketchNode::new)
                .filter(this.styleChecker)
                .findFirst()
                .get();
    }

    public Connector(Connector conn) {

        super(conn.getId());
        this.previous = new SketchNode(conn.previous);
        this.next = new SketchNode(conn.next);
        this.type = conn.getType();
        this.styleChecker = conn.getStyleChecker();
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

    public State getType() {
        return type;
    }

    public void setType(State type) {
        this.type = type;
    }

    public Predicate<SketchNode> getStyleChecker() {
        return styleChecker;
    }

    public void setStyleChecker(Predicate<SketchNode> styleChecker) {
        this.styleChecker = styleChecker;
    }

}
