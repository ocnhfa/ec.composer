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
package tech.metacontext.ec.prototype.composer.factory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import tech.metacontext.ec.prototype.abs.Factory;
import tech.metacontext.ec.prototype.composer.model.Composition;
import tech.metacontext.ec.prototype.composer.model.CompositionEval;
import tech.metacontext.ec.prototype.composer.model.SketchNode;
import tech.metacontext.ec.prototype.composer.connectors.Connector;
import tech.metacontext.ec.prototype.composer.styles.Style;

/**
 *
 * @author Jonathan
 */
public class CompositionFactory implements Factory<Composition> {

    private static final ConnectorFactory connectorFactory = ConnectorFactory.getInstance();
    private static final SketchNodeFactory sketchNodeFactory = SketchNodeFactory.getInstance();
    private static final Map<String, CompositionFactory> instances = new HashMap<>();
    private final String composer_id;

    private CompositionFactory(String composer_id) {

        this.composer_id = composer_id;
    }

    public static CompositionFactory getInstance(String composer_id) {

        if (Objects.isNull(instances.get(composer_id))) {
            instances.put(composer_id, new CompositionFactory(composer_id));
        }
        return instances.get(composer_id);
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
        Composition dupe = new Composition(this.composer_id, origin.getId(),
                origin.getEval().getStyles());
        dupe.getRendered().addAll(origin.getRenderedChecked());
        dupe.getConnectors().addAll(origin.getConnectors().stream()
                .map(connectorFactory::forArchiving)
                .collect(Collectors.toList()));
        dupe.resetSeed(dupe.getConnectors().get(0).getPrevious());
        dupe.setEval(new CompositionEval(origin.getEval()));
        return dupe;
    }

    /**
     * Create duplicated Composition instance for mutation.
     *
     * @param origin
     * @return
     */
    public Composition forMutation(Composition origin) {

        Composition dupe = new Composition(this.composer_id, origin.getEval().getStyles());
        dupe.getConnectors().addAll(origin.getConnectors().stream()
                .map(connectorFactory::forMutation)
                .collect(Collectors.toList()));
        dupe.resetSeed(sketchNodeFactory.forMutation(origin.getSeed()));
        return dupe;
    }

    public Composition newInstance(Predicate<SketchNode> styleChecker,
            Collection<? extends Style> styles) {

        Composition newInstance = new Composition(this.composer_id, styles);
        Connector conn = connectorFactory.newConnectorWithSeed(styleChecker);
        newInstance.addConnector(conn);
        newInstance.setSeed(conn.getPrevious());
        return newInstance;
    }

    public Composition forCrossover(SketchNode seed, Connector conn,
            Collection<? extends Style> styles) {

        Composition newInstance = new Composition(this.composer_id, styles);
        Connector dupeConn = connectorFactory
                .newConnectorWithSeed(conn.getStyleChecker());
        newInstance.addConnector(dupeConn);
        newInstance.resetSeed(dupeConn.getPrevious());
        return newInstance;
    }

}
