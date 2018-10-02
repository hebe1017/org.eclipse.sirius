/*******************************************************************************
 * Copyright (c) 2010, 2018 THALES GLOBAL SERVICES.
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

import java.lang.reflect.Field;

import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.ui.tools.internal.util.EditPartQuery;
import org.eclipse.sirius.tests.swtbot.support.api.editor.SWTBotSiriusDiagramEditor;
import org.eclipse.sirius.tests.swtbot.support.utils.SWTBotUtils;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.part.EditorActionBarContributor;
import org.junit.Assert;

/**
 * <p>
 * Ensures that the Sirius's UI allows to activate/deactivate the Layouting Mode, and checks that, when activated, the
 * Diagram's status line is updated.
 * </p>
 * 
 * @author <a href="mailto:alex.lagarde@obeo.fr">Alex Lagarde</a>
 */
public class LayoutingModeTest extends AbstractModeTest {

    /**
     * Ensures that :
     * <ul>
     * <li>it is possible to activate/deactivate the LayoutingMode through the Tabbar</li>
     * <li>when activated, the Layouting mode forbids Drag and Drop operations</li>
     * <li>when activate, the diagram's status line indicates that Layouting mode is on</li>
     * </ul>
     * 
     * @throws Exception
     */
    public void testLayoutingModeActivationThroughTabbar() throws Exception {
        // Step 1 : layouting should be off
        checkLayoutingModeisInExpectedState(false);

        // Step 2 : when clicking on the tabbar icon
        // layouting mode should be activated
        activateLayoutingModeUsingTabbar();
        checkLayoutingModeisInExpectedState(true);

        // Step 3 : when clicking on the tabbar icon
        // layouting mode should be disabled
        activateStandardModeUsingTabbar();
        checkLayoutingModeisInExpectedState(false);

        // Step 4 : when activating layouting mode and reopening diagram,
        // layouting mode should be disabled
        activateLayoutingModeUsingTabbar();
        checkLayoutingModeisInExpectedState(true);
        editor.close();
        editor = (SWTBotSiriusDiagramEditor) openRepresentation(localSession.getOpenedSession(), REPRESENTATION_DESCRIPTION_NAME, REPRESENTATION_INSTANCE_NAME, DDiagram.class);

        checkLayoutingModeisInExpectedState(false);
    }

    /**
     * Ensures that :
     * <p>
     * <ul>
     * <li>The layouting mode is in expected state</li>
     * <li>the status line of the current Diagram is correctly displayed</li>
     * </ul>
     * 
     * </p>
     * 
     * @param layoutingModeShouldBeActive
     */
    private void checkLayoutingModeisInExpectedState(boolean layoutingModeShouldBeActive) {
        // Step 1 : ensures that the layouting mode is in expected state
        assertEquals(layoutingModeShouldBeActive, new EditPartQuery((IGraphicalEditPart) editor.getEditPart("new Package 1").part()).isInLayoutingMode());

        // Step 2 : ensures that the status line displays the expected message
        checkStatusLineShowExpectedMessage(layoutingModeShouldBeActive);
    }

    /**
     * Ensures that the editor's status line displays the expected message, according to the enablement of the layouting
     * mode.
     * 
     * @param layoutingModeIsActive
     *            indicates whether layoutingMode should be active or not
     */
    private void checkStatusLineShowExpectedMessage(boolean layoutingModeIsActive) {
        // Step 1 : getting the status line's message
        String statusLineMessage = "";
        SWTBotUtils.waitAllUiEvents();
        IEditorSite site = (IEditorSite) editor.getReference().getPage().getActiveEditor().getSite();
        IStatusLineManager statusLineManager = ((EditorActionBarContributor) site.getActionBarContributor()).getActionBars().getStatusLineManager();
        try {
            Field statusLineManagerMessage = statusLineManager.getClass().getDeclaredField("message");
            statusLineManagerMessage.setAccessible(true);
            statusLineMessage = (String) statusLineManagerMessage.get(statusLineManager);
            if (statusLineMessage == null) {
                statusLineMessage = "";
            }
        } catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            Assert.fail(e.getMessage());
        }

        // Step 2 : if layouting mode is disabled, message should be empty.
        // otherwise, it should be "Layouting Mode"
        assertEquals(layoutingModeIsActive, statusLineMessage.equals("Layouting Mode"));
    }

}
