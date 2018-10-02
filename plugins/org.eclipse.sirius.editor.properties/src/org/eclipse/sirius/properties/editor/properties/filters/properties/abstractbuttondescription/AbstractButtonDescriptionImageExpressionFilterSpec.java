/*******************************************************************************
 * Copyright (c) 2017 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.properties.editor.properties.filters.properties.abstractbuttondescription;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.sirius.editor.properties.filters.common.ViewpointPropertyFilter;
import org.eclipse.sirius.properties.AbstractButtonDescription;
import org.eclipse.sirius.properties.PropertiesPackage;

/**
 * A filter for the imageExpression property section.
 * 
 * @author sbegaudeau
 */
public class AbstractButtonDescriptionImageExpressionFilterSpec extends ViewpointPropertyFilter {

    @Override
    protected EStructuralFeature getFeature() {
        return PropertiesPackage.eINSTANCE.getAbstractButtonDescription_ImageExpression();
    }

    @Override
    protected boolean isRightInputType(Object arg0) {
        return arg0 instanceof AbstractButtonDescription;
    }

}
