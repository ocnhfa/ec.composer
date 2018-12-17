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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import tech.metacontext.ec.prototype.composer.connectors.Connector;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import tech.metacontext.ec.prototype.abs.Individual;
import tech.metacontext.ec.prototype.abs.Wrapper;
import tech.metacontext.ec.prototype.composer.connectors.ConnectorFactory;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class Composition extends Individual {

    private LinkedList<Connector> connectors = new LinkedList<>();
    private static ConnectorFactory factory = ConnectorFactory.getInstance();

    public Composition(Connector conn) {

        this.connectors.add(conn);
    }

    public Composition(Composition parent) {

        super(parent.getId());
        this.connectors = parent.connectors.stream()
                .map(conn -> factory.getConnector(conn.getStyleChecker()))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public Composition elongation(Predicate<SketchNode> styleChecker) {

        this.addConnector(factory.getConnector(styleChecker));
        return this;
    }

    public List<SketchNode> render(SketchNode seed) {

        Wrapper<SketchNode> previous = new Wrapper<>(seed);
        List<SketchNode> nodes = new ArrayList<>();
        nodes.add(seed);
        nodes.addAll(this.getConnectors().stream()
                .map(conn -> {
                    /*
                    1. conn.setPrevious(previous.get())
                    2. previous.set(conn.transform())
                    3. return conn.getNext()
                     */
                    conn.setPrevious(previous.get());
                    return previous.set(conn.transform());
                })
                .collect(Collectors.toList()));
        System.out.println(this);
        return nodes;
    }

    public void addConnector(Connector connector) {

        this.connectors.add(connector);
    }

    @Override
    public String toString() {

        return String.format("%s (size = %d):\n  %s\n",
                super.toString(), this.getSize(),
                this.getConnectors().stream()
                        .map(Connector::toString)
                        .collect(Collectors.joining("\n  ")));
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
