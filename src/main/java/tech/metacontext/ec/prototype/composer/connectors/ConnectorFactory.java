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
package tech.metacontext.ec.prototype.composer.connectors;

import java.util.HashMap;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.FileHandler;
import java.util.stream.Stream;
import tech.metacontext.ec.prototype.composer.SketchNode;
import tech.metacontext.ec.prototype.composer.SketchNodeFactory;
import tech.metacontext.ec.prototype.composer.materials.enums.*;

/**
 *
 * @author Jonathan
 */
public class ConnectorFactory {

    private static SketchNodeFactory sketchNodeFactory;
    private static ConnectorFactory instance;
    private static FileHandler fh;

    private ConnectorFactory(FileHandler fh) {

        ConnectorFactory.fh = fh;
        sketchNodeFactory = SketchNodeFactory.getInstance(fh);
    }

    public static ConnectorFactory getInstance(FileHandler fh) {

        if (instance == null) {
            instance = new ConnectorFactory(fh);
        }
        return instance;
    }

    public Connector newConnector(Predicate<SketchNode> styleChecker) {

        Connector conn = new Connector(fh);
        conn.setStyleChecker(styleChecker);
        switch (State.getRandom()) {
            case Total:
                TransformType tt = TransformType.getRandom();
                Stream.of(MaterialType.values())
                        .forEach(mt -> conn.addTransformType(mt, tt));
                break;
            case ByItem:
                //@todo ByItem: apply special algorithm
                Stream.of(MaterialType.values())
                        .forEach(mt -> conn.addTransformType(mt, TransformType.getRandom()));
                break;
        }
        return conn;
    }

    public Connector newConnectorWithSeed(Predicate<SketchNode> styleChecker) {

        Connector conn = newConnector(styleChecker);
        conn.setPrevious(sketchNodeFactory.newInstance(styleChecker));
        return conn;
    }

    public Connector forMutation(Connector conn) {

        Connector dupe = new Connector(fh);
        dupe.getTransformTypes().putAll(conn.getTransformTypes());
        dupe.setStyleChecker(conn.getStyleChecker());
        dupe.setPrevious(Objects.isNull(conn.getPrevious())
                ? null : sketchNodeFactory.forMutation(conn.getPrevious()));
        return dupe;
    }

    public Connector forArchiving(Connector conn) {

        Connector dupe = new Connector(conn.getId(), fh);
        dupe.getTransformTypes().putAll(conn.getTransformTypes());
        dupe.setStyleChecker(conn.getStyleChecker());
        dupe.setPrevious(Objects.isNull(conn.getPrevious())
                ? null : sketchNodeFactory.forArchiving(conn.getPrevious()));
        dupe.setNext(Objects.isNull(conn.getNext())
                ? null : sketchNodeFactory.forArchiving(conn.getNext()));
        return dupe;
    }
}
