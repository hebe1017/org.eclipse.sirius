/*******************************************************************************
 * Copyright (c) 2007, 2018 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.diagram.editor.properties.filters.tool.deletehookparameter;

// Start of user code specific imports

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.sirius.diagram.description.tool.ToolPackage;
import org.eclipse.sirius.editor.properties.filters.common.ViewpointPropertyFilter;

// End of user code specific imports

/**
 * A filter for the value property section.
 */
public class DeleteHookParameterValueFilter extends ViewpointPropertyFilter {

    /**
     * {@inheritDoc}
     */
    @Override
    protected EStructuralFeature getFeature() {
        return ToolPackage.eINSTANCE.getDeleteHookParameter_Value();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isRightInputType(Object arg0) {
        return arg0 instanceof org.eclipse.sirius.diagram.description.tool.DeleteHookParameter;
    }

    // Start of user code user methods

    // End of user code user methods

}
