/*******************************************************************************
 * Copyright (c) 2011 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.tests.unit.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;



import org.eclipse.sirius.synchronizer.AutomaticCreator;
import org.eclipse.sirius.synchronizer.Mapping;
import org.eclipse.sirius.synchronizer.MappingHiearchy;
import org.eclipse.sirius.synchronizer.MappingHiearchyTable;
import org.eclipse.sirius.synchronizer.SemanticPartition;
import org.junit.Assert;

import junit.framework.TestCase;

public class MappingHierarchyTableTest extends TestCase {

    MappingHiearchyTable table;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        table = new MappingHiearchyTable();
    }

    public void testMappingHierarchy() throws Exception {

        final Mapping a = new MockMapping();
        Mapping b = new MockMapping() {
            @Override
            public java.util.Optional<Mapping> getSuper() {
                return java.util.Optional.of(a);
            }
        };
        MappingHiearchy hierarchy = new MappingHiearchy(b);
        Iterator<Mapping> it = hierarchy.fromMostSpecificToMostGeneral();
        Assert.assertSame(b, it.next());
        Assert.assertSame(a, it.next());
        Assert.assertFalse(it.hasNext());

    }

    public void testASingleMappingHasAHierarchy() throws Exception {
        Mapping a = new MockMapping();
        table.compute(new ArrayList<Mapping>(Arrays.asList(a)));

        Assert.assertNotNull(table.getHierarchy(a));
    }

    public void test2UnrelatedElementsHaveDifferentHierarchies() throws Exception {

        Mapping a = new MockMapping();
        Mapping b = new MockMapping();

        table.compute(new ArrayList<Mapping>(Arrays.asList(a, b)));

        Assert.assertNotSame(table.getHierarchy(a).iterator().next(), table.getHierarchy(b).iterator().next());

    }

    public void testAisSuperOfB() throws Exception {

        final Mapping a = new MockMapping();
        Mapping b = new MockMapping() {
            @Override
            public java.util.Optional<Mapping> getSuper() {
                return java.util.Optional.of(a);
            }
        };

        table.compute(new ArrayList<Mapping>(Arrays.asList(a, b)));

        Assert.assertSame(table.getHierarchy(a).iterator().next(), table.getHierarchy(b).iterator().next());

    }

    public void _testCyclingHierarchy() throws Exception {

        final MockMapping a = new MockMapping();
        final MockMapping b = new MockMapping();
        b.setSuper(a);
        a.setSuper(b);

        table.compute(new ArrayList<Mapping>(Arrays.asList(a, b)));

        Assert.assertSame(table.getHierarchy(a).iterator().next(), table.getHierarchy(b).iterator().next());
    }

    class MockMapping implements Mapping {

        Mapping superMap = null;

        public java.util.Optional<Mapping> getSuper() {
            return java.util.Optional.of(superMap);
        }

        public java.util.Optional<AutomaticCreator> getCreator() {
            return java.util.Optional.empty();
        }

        public SemanticPartition getSemanticPartition() {
            return SemanticPartition.NONE;
        }

        public List<Mapping> getChildMappings() {
            return new ArrayList<>();
        }

        public boolean isEnabled() {
            return true;
        }

        public void setSuper(Mapping newSuper) {
            superMap = newSuper;
        }

    }
}
