/*******************************************************************************
 * Copyright (c) 2010, 2017 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.tests.swtbot;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ScalableFigure;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.sirius.ext.gmf.runtime.editparts.GraphicalHelper;

/**
 * Same tests as {@link BorderedNodeCreationNearCollapsedTest} but with
 * snapToGrid enabled.
 * 
 * @author <a href="mailto:laurent.redor@obeo.fr">Laurent Redor</a>
 */
public class BorderedNodeCreationNearCollapsedWithSnapToGridTest extends BorderedNodeCreationNearCollapsedTest {

    /**
     * The grid spacing in pixels.
     */
    private static final int GRID_SPACING = 100;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        editor.setSnapToGrid(true, GRID_SPACING, 2);
    }

    /**
     * Only one of the coordinates is snap to grid. The other is constrained by
     * the parent border.
     * 
     * @see org.eclipse.sirius.tests.swtbot.BorderedNodeCreationTest#assertSameLocation(String,
     *      Point, Point, Point, Point, IGraphicalEditPart)
     */
    @Override
    protected void assertSameLocation(String errorMessage, Point expectedLocation, Point nodeLocation, Point parentLocation, Point screenCreationLocation, IGraphicalEditPart parentPart) {
        PrecisionPoint snapToLocation = new PrecisionPoint();
        if (screenCreationLocation == null) {
            snapToLocation = editor.adaptLocationToSnap(expectedLocation);
        } else {
            snapToLocation = new PrecisionPoint(screenCreationLocation);
            addDiagramScrollbar(parentPart.getFigure(), snapToLocation);
            considerZoom(parentPart.getFigure(), snapToLocation);
            // GraphicalHelper.getScrollSize(parentPart);
            // GraphicalHelper.screen2logical(screenCreationLocation,
            // parentPart);
            snapToLocation = editor.adaptLocationToSnap(snapToLocation);
        }
        // Adapt the expected location to the scrollbar of the parents
        PrecisionPoint absoluteSnapToLocation = new PrecisionPoint(snapToLocation);
        if (parentPart != null) {
            addParentScrollbar(parentPart.getFigure(), absoluteSnapToLocation);
        }

        if (nodeLocation.x == parentLocation.x + 8) {
            errorMessage += " expected <Point(" + nodeLocation.x + ", " + absoluteSnapToLocation.y + ")> or <Point(" + nodeLocation.x + ", " + expectedLocation.y + ")> but was:" + nodeLocation;
            assertTrue(errorMessage, nodeLocation.x == nodeLocation.x && nodeLocation.y == absoluteSnapToLocation.y || nodeLocation.x == nodeLocation.x && nodeLocation.y == expectedLocation.y);
        } else if (nodeLocation.y == parentLocation.y) {
            // The y axis is the same as parent. Check that this axis also
            // corresponds to the grid. The x axis is constrained by the border
            // (east or west).
            errorMessage += " expected <Point(" + nodeLocation.x + ", " + snapToLocation.y + ")> or <Point(" + nodeLocation.x + ", " + expectedLocation.y + ")> but was:" + nodeLocation;
            assertTrue(errorMessage, nodeLocation.y == snapToLocation.y);
        } else {
            // Get the absolute bounds of parent
            Rectangle parentBounds = GraphicalHelper.getAbsoluteBoundsIn100Percent(parentPart, true);

            errorMessage += " At least x or y must be on the grid (grid spacing = " + GRID_SPACING + "), but was: " + nodeLocation + "for parent: " + parentBounds;

            assertTrue(errorMessage,
                    (nodeLocation.x % GRID_SPACING == 0 && (nodeLocation.y == expectedLocation.y || nodeLocation.y == parentLocation.y - 2 || nodeLocation.y == parentBounds.bottom() - 8))
                            || ((nodeLocation.x == expectedLocation.x || nodeLocation.x == parentLocation.x - 2 || nodeLocation.x == parentBounds.right() - 8) && nodeLocation.y % GRID_SPACING == 0));
        }
    }

    /**
     * Add scrolls of diagram
     * 
     * @param figure
     *            the actual figure level
     * @param location
     *            a location
     */
    private static void addDiagramScrollbar(final IFigure figure, final PrecisionPoint location) {
        if (figure instanceof Viewport && figure.getParent() != null && figure.getParent().getParent() == null) {
            location.performTranslate(((Viewport) figure).getHorizontalRangeModel().getValue(), ((Viewport) figure).getVerticalRangeModel().getValue());
        }
        if (figure.getParent() != null) {
            addDiagramScrollbar(figure.getParent(), location);
        }
    }

    /**
     * Add scrolls of diagram
     * 
     * @param figure
     *            the actual figure level
     * @param location
     *            a location
     */
    private static void considerZoom(final IFigure figure, final PrecisionPoint location) {
        if (figure instanceof ScalableFigure) {
            location.performScale(1 / ((ScalableFigure) figure).getScale());
        }
        if (figure.getParent() != null) {
            considerZoom(figure.getParent(), location);
        }
    }

    /**
     * Add scrolls of parent (except the diagram scroll)
     * 
     * @param figure
     *            the actual figure level
     * @param location
     *            a location in absolute coordinates
     */
    private static void addParentScrollbar(final IFigure figure, final PrecisionPoint location) {
        if (figure instanceof Viewport && figure.getParent() != null && figure.getParent().getParent() != null) {
            location.performTranslate(((Viewport) figure).getHorizontalRangeModel().getValue(), ((Viewport) figure).getVerticalRangeModel().getValue());
        }
        if (figure.getParent() != null) {
            addParentScrollbar(figure.getParent(), location);
        }
    }
}
