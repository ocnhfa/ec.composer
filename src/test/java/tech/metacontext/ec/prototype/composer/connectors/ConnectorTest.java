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

import tech.metacontext.ec.prototype.composer.factory.ConnectorFactory;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import tech.metacontext.ec.prototype.composer.factory.SketchNodeFactory;
import tech.metacontext.ec.prototype.composer.enums.MaterialType;
import tech.metacontext.ec.prototype.composer.enums.TransformType;
import tech.metacontext.ec.prototype.composer.styles.FreeStyle;

/**
 *
 * @author Jonathan
 */
public class ConnectorTest {

    static ConnectorFactory connectorFactory;
    static SketchNodeFactory sketchNodeFactory;

    public ConnectorTest() throws IOException {

        connectorFactory = ConnectorFactory.getInstance();
        sketchNodeFactory = SketchNodeFactory.getInstance();
    }

    @Test
    public void testTransform() {
        Connector instance = connectorFactory.newConnector(FreeStyle::checker);
        instance.addTransformType(MaterialType.Dynamics, TransformType.MoveForward);
        instance.addTransformType(MaterialType.NoteRanges, TransformType.MoveForward);
        instance.addTransformType(MaterialType.PitchSets, TransformType.MoveForward);
        instance.addTransformType(MaterialType.RhythmicPoints, TransformType.MoveForward);
        instance.setPrevious(sketchNodeFactory.newInstance());
        System.out.println(instance.getPrevious());
        instance.transform();
        System.out.println(instance.getNext());
        System.out.println("----------------------------------------------");
        Connector instance2 = connectorFactory.newConnector(FreeStyle::checker);
        instance2.addTransformType(MaterialType.Dynamics, TransformType.MoveBackward);
        instance2.addTransformType(MaterialType.NoteRanges, TransformType.MoveBackward);
        instance2.addTransformType(MaterialType.PitchSets, TransformType.MoveBackward);
        instance2.addTransformType(MaterialType.RhythmicPoints, TransformType.MoveBackward);
        instance2.setPrevious(sketchNodeFactory.newInstance());
        System.out.println(instance2.getPrevious());
        instance2.transform();
        System.out.println(instance2.getNext());
    }

    @Test
    @Disabled
    public void testAddTransformType() {
    }

    @Test
    @Disabled
    public void testToString() {
    }

    @Test
    @Disabled
    public void testGetPrevious() {
    }

    @Test
    @Disabled
    public void testSetPrevious() {
    }

    @Test
    @Disabled
    public void testGetNext() {
    }

    @Test
    @Disabled
    public void testSetNext() {
    }

    @Test
    @Disabled
    public void testGetStyleChecker() {
    }

    @Test
    @Disabled
    public void testSetStyleChecker() {
    }

    @Test
    @Disabled
    public void testGetTransformTypes() {
    }

}
