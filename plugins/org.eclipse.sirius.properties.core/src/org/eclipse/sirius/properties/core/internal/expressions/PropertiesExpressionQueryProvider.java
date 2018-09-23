/*******************************************************************************
 * Copyright (c) 2016, 2017 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.properties.core.internal.expressions;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.sirius.business.api.dialect.description.IInterpretedExpressionQuery;
import org.eclipse.sirius.business.api.dialect.description.IInterpretedExpressionQueryProvider;


import org.eclipse.sirius.properties.PropertiesPackage;

/**
 * An {@link IInterpretedExpressionQueryProvider} for properties view
 * description expressions.
 * 
 * @author pcdavid
 */
public class PropertiesExpressionQueryProvider implements IInterpretedExpressionQueryProvider {
    @Override
    public java.util.Optional<IInterpretedExpressionQuery> getExpressionQueryFor(EObject context, EStructuralFeature expressionAttribute) {
        if (VSMNavigation.isInsideViewExtensionDescription(context) || context.eClass().getEPackage() == PropertiesPackage.eINSTANCE) {
            IInterpretedExpressionQuery value = new PropertiesInterpretedExpressionQuery(context, expressionAttribute);
            return java.util.Optional.of(value);
        } else {
            return java.util.Optional.empty();
        }
    }
}
