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
package tech.metacontext.ec.prototype.composer;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import tech.metacontext.ec.prototype.abs.Individual;
import tech.metacontext.ec.prototype.composer.styles.Style;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class Composition extends Individual {

    private LinkedList<Connector> connectors = new LinkedList<>();

    public Composition(SketchNode seed, Connector conn) {

        this.connectors.add(conn);
        conn.setPrevious(seed);
        conn.getNext();
    }

    public Composition(Connector connector) {

        this.connectors.add(connector);
    }

    public Composition(Composition parent) {

        super(parent.getId());
        this.connectors = parent.connectors.stream()
                .map(Connector::new)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public Composition elongation(List<? extends Style> styles) {
        //@todo: compose
        // if not meet aim, elongation
        // if meet aim, mutate or crossover
        this.connectors.add(
                Stream.generate(() -> new Connector(this.connectors.getLast().getNext()))
                        .filter(conn
                                -> styles == null || styles.stream()
                                .allMatch(style
                                        -> style
                                        .qualifySketchNode(conn.getNext())))
                        .findAny()
                        .get()
        );
        return this;
    }

    public void render() {

        System.out.println(this);
    }

    public void addConnect(Connector connector) {

        this.connectors.add(connector);
    }

    @Override
    public String toString() {

        return String.format("%s (size = %d):\n  %s",
                super.toString(), this.getSize(),
                this.getConnectors().stream()
                        .map(Connector::toString)
                        .collect(Collectors.joining("\n  "))) + "\n";
    }

    public int getSize() {

        return this.getConnectors().size() + 1;
    }

    /*
     * Default setters and getters
     */
    public LinkedList<Connector> getConnectors() {
        return connectors;
    }

    public void setConnectors(LinkedList<Connector> connectors) {
        this.connectors = connectors;
    }
}
