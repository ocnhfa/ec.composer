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
import java.util.List;
import java.util.stream.Collectors;
import tech.metacontext.ec.prototype.abs.Individual;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class Composition extends Individual {

    private List<Connector> connectors;

    public Composition(SketchNode seed, Connector conn) {

        this.connectors = new ArrayList<>();
        this.connectors.add(conn);
        conn.setPrevious(seed);
        conn.getNext();
    }

    public Composition(Connector connector) {

        this.connectors = Arrays.asList(connector);
    }

    public Composition(Composition parent) {

        this.connectors = parent.connectors.stream()
                .map(Connector::new)
                .collect(Collectors.toList());
    }

    public Composition compose() {
        // if not meet aim, elongation
        // if meet aim, mutate or crossover
        return this;
    }

    public void render() {

        System.out.println(this);
    }

    public void addConnect(Connector connector) {

        this.connectors.add(connector);
    }

    /*
     * Default setters and getters
     */
    public List<Connector> getConnectors() {
        return connectors;
    }

    public void setConnectors(List<Connector> connectors) {
        this.connectors = connectors;
    }
}
