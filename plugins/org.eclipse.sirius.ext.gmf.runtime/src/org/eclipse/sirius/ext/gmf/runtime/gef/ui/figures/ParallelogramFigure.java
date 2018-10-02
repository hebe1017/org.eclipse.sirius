/*******************************************************************************
 * Copyright (c) 2008, 2018 THALES GLOBAL SERVICES and others.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.ext.gmf.runtime.gef.ui.figures;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * A parallelogram figure.
 * 
 * @author cbrun
 * 
 *         This Figure represents a Parallelogram Figure
 */
public class ParallelogramFigure extends AbstractGeoShapePolygonFigure {

    /** the offset. */
    protected static final int JITTER = 20;

    /**
     * Constructor - Creates a Parallelogram with a given Default size.
     */
    public ParallelogramFigure() {
        super(10, 10, 2);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.ext.gmf.runtime.gef.ui.figures.AbstractGeoShapePolygonFigure#calculatePoints(org.eclipse.draw2d.geometry.Rectangle)
     */
    @Override
    protected PointList calculatePoints(final Rectangle rect) {

        final PointList points = new PointList();
        final Point p1 = new Point(rect.x + JITTER, rect.y);
        final Point p2 = new Point(rect.x + rect.width - 1, rect.y);

        final Point p3 = new Point(rect.x + rect.width - JITTER, rect.y + rect.height - 1);
        final Point p4 = new Point(rect.x, rect.y + rect.height - 1);

        points.addPoint(p1);
        points.addPoint(p2);
        points.addPoint(p3);
        points.addPoint(p4);
        points.addPoint(p1);

        return points;
    }
}
