/*******************************************************************************
 * Copyright (c) 2012 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.business.internal.query;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.business.api.logger.RuntimeLoggerManager;
import org.eclipse.sirius.business.internal.metamodel.helper.BestStyleDescriptionKey;
import org.eclipse.sirius.common.tools.api.interpreter.IInterpreter;
import org.eclipse.sirius.common.tools.api.interpreter.IInterpreterSiriusVariables;
import org.eclipse.sirius.common.tools.api.util.StringUtil;
import org.eclipse.sirius.viewpoint.description.DescriptionPackage;
import org.eclipse.sirius.viewpoint.description.EAttributeCustomization;

/**
 * A query for {@link EAttributeCustomization}.
 * 
 * @author <a href="mailto:esteban.dugueperoux@obeo.fr">Esteban Dugueperoux</a>
 */
public class EAttributeCustomizationQuery {

    private EAttributeCustomization eAttributeCustomization;

    /**
     * Default constructor.
     * 
     * @param eAttributeCustomization
     *            the {@link EAttributeCustomization} to query
     */
    public EAttributeCustomizationQuery(EAttributeCustomization eAttributeCustomization) {
        this.eAttributeCustomization = eAttributeCustomization;
    }

    /**
     * Get the new value computed for the current
     * {@link EAttributeCustomization} in a string representation.
     * 
     * @param bestStyleDescriptionKey
     *            the {@link BestStyleDescriptionKey} identifying the best
     *            StyleDescription to customize
     * @param interpreter
     *            the interpreter used to get the new value
     * @return the new value as a string representation
     */
    public String getNewAttributeValue(BestStyleDescriptionKey bestStyleDescriptionKey, IInterpreter interpreter) {
        String newAttributeValue = null;
        if (eAttributeCustomization.getValue() != null && !StringUtil.isEmpty(eAttributeCustomization.getValue().trim())) {
            interpreter.setVariable(IInterpreterSiriusVariables.VIEW, bestStyleDescriptionKey.getViewVariable());
            interpreter.setVariable(IInterpreterSiriusVariables.CONTAINER, bestStyleDescriptionKey.getContainerVariable());

            newAttributeValue = RuntimeLoggerManager.INSTANCE.decorate(interpreter).evaluateString(bestStyleDescriptionKey.getModelElement(), eAttributeCustomization,
                    DescriptionPackage.eINSTANCE.getEAttributeCustomization_Value());

            interpreter.unSetVariable(IInterpreterSiriusVariables.VIEW);
            interpreter.unSetVariable(IInterpreterSiriusVariables.CONTAINER);
        }
        return newAttributeValue;
    }

    /**
     * Tells if the current {@link EAttributeCustomization} is conforms to the
     * specified style description element.
     * 
     * @param eObject
     *            the specified style description element
     * @return true if the current {@link EAttributeCustomization} is conforms,
     *         false else
     */
    public boolean isStyleDescriptionEltConformToEAttributeCustomization(EObject eObject) {
        boolean isStyleDescriptionEltConformToEAttributeCustomization = false;
        String attributeName = eAttributeCustomization.getAttributeName();
        if (attributeName != null && attributeName.length() > 0) {
            isStyleDescriptionEltConformToEAttributeCustomization = eObject.eClass().getEStructuralFeature(attributeName) instanceof EAttribute;
        } else {
            isStyleDescriptionEltConformToEAttributeCustomization = true;
        }
        return isStyleDescriptionEltConformToEAttributeCustomization;
    }
}
