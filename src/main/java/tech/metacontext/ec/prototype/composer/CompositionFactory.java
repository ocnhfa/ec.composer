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

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import tech.metacontext.ec.prototype.abs.Factory;
import tech.metacontext.ec.prototype.composer.connectors.Connector;
import tech.metacontext.ec.prototype.composer.connectors.ConnectorFactory;
import tech.metacontext.ec.prototype.composer.styles.Style;

/**
 *
 * @author Jonathan
 */
public class CompositionFactory implements Factory<Composition> {

    private static final Logger _logger
            = Logger.getLogger(Composition.class.getName());
    private static ConnectorFactory connectorFactory;
    private static SketchNodeFactory sketchNodeFactory;
    private static CompositionFactory instance;

    private static FileHandler fh;

    private CompositionFactory(FileHandler fh) {

        this.fh = fh;
        _logger.setUseParentHandlers(false);
        _logger.addHandler(fh);
        connectorFactory = ConnectorFactory.getInstance(fh);
        sketchNodeFactory = SketchNodeFactory.getInstance(fh);
    }

    public static CompositionFactory getInstance(FileHandler fh) {

        if (Objects.isNull(instance)) {
            instance = new CompositionFactory(fh);
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

        origin.ifReRenderRequired();
        Composition dupe = new Composition(fh, origin.getId(),
                origin.getEval().getStyles());
        dupe.getRendered().addAll(origin.getRenderedChecked());
        dupe.getConnectors().addAll(origin.getConnectors().stream()
                .map(connectorFactory::forArchiving)
                .collect(Collectors.toList()));
        dupe.resetSeed(dupe.getConnectors().get(0).getPrevious());
        dupe.setEval(new CompositionEval(origin.getEval()));
        _logger.log(Level.INFO,
                "Composition {0} being duplicated for archiving.",
                origin.getId_prefix());
        return dupe;
    }

    /**
     * Create duplicated Composition instance for mutation.
     *
     * @param origin
     * @return
     */
    public Composition forMutation(Composition origin) {

        Composition dupe = new Composition(fh, origin.getEval().getStyles());
        dupe.getConnectors().addAll(origin.getConnectors().stream()
                .map(connectorFactory::forMutation)
                .collect(Collectors.toList()));
        dupe.resetSeed(sketchNodeFactory.forMutation(origin.getSeed()));
        _logger.log(Level.INFO,
                "Composition {0} being duplicated to {1} for mutation.",
                new Object[]{origin.getId_prefix(), dupe.getId_prefix()});
        return dupe;
    }

    public Composition newInstance(Predicate<SketchNode> styleChecker,
            Collection<? extends Style> styles) {

        Composition newInstance = new Composition(fh, styles);
        Connector conn = connectorFactory.newConnectorWithSeed(styleChecker);
        newInstance.addConnector(conn);
        newInstance.setSeed(conn.getPrevious());
        return newInstance;
    }

    Composition forCrossover(SketchNode seed, Connector conn,
            Collection<? extends Style> styles) {

        Composition newInstance = new Composition(fh, styles);
        Connector dupeConn = connectorFactory
                .newConnectorWithSeed(conn.getStyleChecker());
        newInstance.addConnector(dupeConn);
        newInstance.resetSeed(dupeConn.getPrevious());
        return newInstance;
    }

}
