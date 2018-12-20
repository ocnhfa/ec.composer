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

import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import tech.metacontext.ec.prototype.abs.Individual;
import tech.metacontext.ec.prototype.abs.Wrapper;
import tech.metacontext.ec.prototype.composer.connectors.Connector;
import tech.metacontext.ec.prototype.composer.connectors.ConnectorFactory;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class Composition extends Individual {

    private static ConnectorFactory factory = ConnectorFactory.getInstance();
    private final LinkedList<Connector> connectors;
    private final LinkedList<SketchNode> rendered;
    private SketchNode seed;

    public Composition(SketchNode seed, Connector conn) {

        this.rendered = new LinkedList<>();
        this.connectors = new LinkedList<>();
        this.connectors.add(conn);
        this.seed = seed;
    }

    public Composition(Composition origin) {

        super(origin.getId());
        this.rendered = new LinkedList<>(origin.rendered.stream()
                .map(SketchNode::new)
                .collect(Collectors.toList()));
        this.connectors = new LinkedList<>(origin.connectors.stream()
                .map(conn -> factory.getConnector(conn.getStyleChecker()))
                .collect(Collectors.toList()));
        this.seed = origin.seed;
    }

    public Composition elongation(Predicate<SketchNode> styleChecker) {

        this.addConnector(factory.getConnector(styleChecker));
        if (!rendered.isEmpty()) {
            rendered.clear();
        }
        return this;
    }

    public List<SketchNode> render(SketchNode seed) {

        this.seed = seed;
        rendered.clear();
        Wrapper<SketchNode> previous = new Wrapper<>(seed);
        rendered.add(seed);
        rendered.addAll(this.getConnectors().stream()
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
//        System.out.println(this);
        return rendered;
    }

    public void addConnector(Connector connector) {

        this.connectors.add(connector);
    }

    public List<SketchNode> getRendered() {
        boolean emptyConnectors = this.connectors.isEmpty();
        if (emptyConnectors) {
            System.out.println("emptyConnectors");
        } else {
            boolean mismatchedSeed = 
                    this.connectors.getFirst().getPrevious() != this.seed;
            if (mismatchedSeed) {
                System.out.println("mismatchedSeed");
            } else {
                boolean mismatchedSize = this.rendered.size() < this.getSize();
                if (mismatchedSize) {
                    System.out.println("mismatchedSize");
                } else {
                    boolean nullNodes = IntStream.range(1, this.getSize())
                            .mapToObj(i -> new SimpleEntry<>(this.connectors.get(i - 1), this.rendered.get(i)))
                            .anyMatch(e -> Objects.isNull(e.getKey().getPrevious())
                            || Objects.isNull(e.getKey().getNext()));
                    if (nullNodes) {
                        System.out.println("nullOrMismatchedNodes");
                    } else {
                        boolean mismatchedNodes = IntStream.range(1, this.getSize())
                                .mapToObj(i -> new SimpleEntry<>(this.connectors.get(i - 1), this.rendered.get(i)))
                                .anyMatch(e -> !Objects.equals(e.getKey().getNext(), e.getValue()));
                        if (mismatchedNodes) {
                            System.out.println("mismatchedNodes");
                        } else {
                            return this.rendered;
                        }
                    }
                }
            }
        }
//        System.out.println("Rendering Composition " + this.getId());
        return this.render(seed);
    }

//    public LinkedList<SketchNode> getRendered() {
//        boolean emptyConnectors = this.connectors.isEmpty();
//        boolean mismatchedSeed = this.connectors.getFirst().getPrevious() != this.seed;
//        boolean mismatchedSize = this.rendered.size() < this.getSize();
//        boolean nullOrMismatchedNodes = IntStream.range(1, this.getSize())
//                .mapToObj(i -> new SimpleEntry<>(this.connectors.get(i - 1), this.rendered.get(i)))
//                .anyMatch(e -> Objects.isNull(e.getKey().getPrevious())
//                || Objects.isNull(e.getKey().getNext())
//                || !Objects.equals(e.getKey().getNext(), e.getValue()));
//
//        if (this.connectors.isEmpty()
//                || this.connectors.getFirst().getPrevious() != this.seed
//                || this.rendered.size() < this.getSize()
//                || IntStream.range(1, this.getSize())
//                        .mapToObj(i -> new SimpleEntry<>(this.connectors.get(i - 1), this.rendered.get(i)))
//                        .anyMatch(e
//                                -> e.getKey().getPrevious() == null || e.getKey().getNext() == null
//                        || !e.getKey().getNext().equals(e.getValue()))) {
//
//            System.out.printf("%b,%b,%b,%b\n",
//                    emptyConnectors,
//                    mismatchedSeed,
//                    mismatchedSize,
//                    nullOrMismatchedNodes);
//
//            System.out.println("Rendering Composition " + this.getId());
//            this.render(seed);
//        }
//        return this.rendered;
//    }
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

    public SketchNode getSeed() {
        return seed;
    }

    public void setSeed(SketchNode seed) {
        this.seed = seed;
    }

}
