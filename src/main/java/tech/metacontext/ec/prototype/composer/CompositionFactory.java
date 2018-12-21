/*
 * Copyright 2018 Jonathan.
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

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import tech.metacontext.ec.prototype.abs.Factory;
import tech.metacontext.ec.prototype.composer.connectors.Connector;
import tech.metacontext.ec.prototype.composer.connectors.ConnectorFactory;

/**
 *
 * @author Jonathan
 */
public class CompositionFactory implements Factory<Composition> {

    private static final Logger _logger = Logger.getLogger(CompositionFactory.class.getName());
    private static ConnectorFactory factory = ConnectorFactory.getInstance();
    private static CompositionFactory instance;

    private CompositionFactory() {
    }

    public static CompositionFactory getInstance() {

        if (Objects.isNull(instance)) {
            instance = new CompositionFactory();
        }
        return instance;
    }

    /**
     * Create duplicated Composition instance for archiving.
     *
     * @param origin
     * @return
     */
    @Override
    public Composition forArchiving(Composition origin) {

        Composition dupe = new Composition(origin.getId());
        dupe.getConnectors().addAll(origin.getConnectors().stream()
                .map(conn -> factory.getConnector(conn.getStyleChecker()))
                .collect(Collectors.toList()));
        dupe.setSeed(new SketchNode(origin.getSeed()));
        if (origin.ifReRenderRequired()) {
            dupe.render(dupe.getSeed());
        } else {
            dupe.getRendered().addAll(origin.getRendered().stream()
                    .map(SketchNode::new)
                    .collect(Collectors.toList()));
        }
        _logger.log(Level.INFO,
                "Composition {0} being duplicated for archiving.",
                origin.getId());
        return dupe;
    }

    /**
     * Create duplicated Composition instance for mutation.
     *
     * @param origin
     * @return
     */
    public Composition forMutation(Composition origin) {

        Composition dupe = new Composition();
        dupe.getConnectors().addAll(origin.getConnectors().stream()
                .map(conn -> factory.getConnector(conn.getStyleChecker()))
                .collect(Collectors.toList()));
        dupe.setSeed(new SketchNode(origin.getSeed()));
        _logger.log(Level.INFO,
                "Composition {0} being duplicated to {1} for mutation.",
                new Object[]{origin.getId(), dupe.getId()});
        return dupe;
    }

    public Composition newInstance(SketchNode seed, Connector conn) {

        Composition newInstance = new Composition();
        newInstance.addConnector(conn);
        newInstance.setSeed(seed);
        return newInstance;
    }

    Composition forCrossover(SketchNode seed, Connector conn) {

        Composition newInstance = new Composition();
        newInstance.addConnector(new Connector(conn));
        newInstance.setSeed(new SketchNode(seed));
        return newInstance;
    }

}
