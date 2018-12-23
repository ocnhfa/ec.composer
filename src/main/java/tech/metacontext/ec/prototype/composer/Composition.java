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

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import tech.metacontext.ec.prototype.abs.Individual;
import tech.metacontext.ec.prototype.abs.Wrapper;
import tech.metacontext.ec.prototype.composer.connectors.Connector;
import tech.metacontext.ec.prototype.composer.connectors.ConnectorFactory;
import tech.metacontext.ec.prototype.composer.styles.Style;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class Composition extends Individual<CompositionEval> {

    static final Logger _logger = Logger.getLogger(Composition.class.getName());

    private static ConnectorFactory factory = ConnectorFactory.getInstance();
    private final LinkedList<Connector> connectors;
    private final LinkedList<SketchNode> rendered;
    private SketchNode seed;

    public Composition(Collection<? extends Style> styles) {

        this.rendered = new LinkedList<>();
        this.connectors = new LinkedList<>();
        this.setEval(new CompositionEval(styles));
    }

    public Composition(String id, Collection<? extends Style> styles) {

        super(id);
        this.rendered = new LinkedList<>();
        this.connectors = new LinkedList<>();
        this.setEval(new CompositionEval(styles));
    }

    public Composition elongation(Predicate<SketchNode> styleChecker) {

        this.addConnector(factory.newConnector(styleChecker));
        return this;
    }

    public void addConnector(Connector connector) {

        this.connectors.add(connector);
    }

    public List<SketchNode> render() {

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

    public List<SketchNode> getRenderedChecked() {

        if (!this.ifReRenderRequired()) {
            return this.rendered;
        }
        List<SketchNode> result = this.render();
        updateEval();
        return result;
    }

    public boolean ifReRenderRequired() {

        if (this.rendered.isEmpty()) {
            _logger.log(Level.INFO,
                    "Not rendered yet, rendering required for Composition {0}.",
                    this.getId_prefix());
            return true;
        }
        if (!Objects.equals(this.connectors.getFirst().getPrevious(), this.seed)) {
            _logger.log(Level.INFO,
                    "Seed mismatched, rerendering required for Composition {0}.",
                    this.getId_prefix());
            return true;
        }
        if (this.rendered.size() != this.getSize()) {
            _logger.log(Level.INFO,
                    "Size mismatched: {0} to {1}, rerendering required for Composition {2}.", new Object[]{
                        this.rendered.size(),
                        this.getSize(),
                        this.getId_prefix()});
            return true;
        }
        if (this.connectors.stream().anyMatch(conn
                -> Objects.isNull(conn.getPrevious()) || Objects.isNull(conn.getNext()))) {
            _logger.log(Level.INFO,
                    "Connector without connected SketchNode found, rerendering required for Composition {0}.",
                    this.getId_prefix());
            return true;
        }
        if (IntStream.range(1, this.getSize()).anyMatch(i
                -> !Objects.equals(this.connectors.get(i - 1).getNext(), this.rendered.get(i)))) {
            _logger.log(Level.INFO,
                    "Mismatched SketchNodes, rerendering required for Composition {0}.",
                    this.getId_prefix());
            return true;
        }
        _logger.log(Level.INFO,
                "Rendered list remained consistant, no rerendering required for {0}.",
                this.getId_prefix());
        return false;
    }

    private void updateEval() {

        this.getEval().getStyles().stream()
                .forEach(this::updateScore);
    }

    public void updateScore(Style style) {

        this.getEval().getScores()
                .put(style, style.rateComposition(this));
    }

    public Double getScore(Style style) {

        return this.getEval().getScores().get(style);
    }

    public int getSize() {

        return this.getConnectors().size() + 1;
    }

    @Override
    public String toString() {

        return String.format("%s (size = %d):\n  %s\n",
                super.toString(), this.getSize(),
                this.getConnectors().stream()
                        .map(Connector::toString)
                        .collect(Collectors.joining("\n  ")));
    }

    public Path persistent() {

        Path destination = new File("src/main/resources/composition", this.getId() + ".txt").toPath();
        try (BufferedWriter out = Files.newBufferedWriter(
                destination, StandardCharsets.UTF_8)) {
            out.write(this.toString());
            out.flush();
        } catch (IOException ex) {
            _logger.log(Level.SEVERE, "Failed to persist {0}. {1}", new Object[]{
                this.getId_prefix(), ex.getMessage()});
        }
        _logger.log(Level.INFO, "{0} has been persisted to {1}", new Object[]{
            this.getId_prefix(),
            destination.getFileName()});
        return destination;
    }

    public void resetSeed(SketchNode seed) {

        this.seed = seed;
        this.connectors.getFirst().setPrevious(seed);
        if (!this.rendered.contains(seed)) {
            if (this.rendered.size() < this.getSize()) {
                this.rendered.add(0, seed);
            } else {
                _logger.warning("Rendered size mismatch of missing seed, rerendering.");
                this.render();
            }
        }
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

    public List<SketchNode> getRendered() {
        return this.rendered;
    }

}
