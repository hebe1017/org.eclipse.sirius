/*******************************************************************************
 * Copyright (c) 2011, 2017 THALES GLOBAL SERVICES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.business.api.dialect.description;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.sirius.business.internal.dialect.description.DescriptionInterpretedExpressionTargetSwitch;
import org.eclipse.sirius.business.internal.dialect.description.StyleInterpretedExpressionTargetSwitch;
import org.eclipse.sirius.business.internal.dialect.description.ToolInterpretedExpressionTargetSwitch;
import org.eclipse.sirius.business.internal.dialect.description.ValidationInterpretedExpressionTargetSwitch;


import org.eclipse.sirius.viewpoint.description.RepresentationDescription;
import org.eclipse.sirius.viewpoint.description.RepresentationElementMapping;
import org.eclipse.sirius.viewpoint.description.RepresentationExtensionDescription;

/**
 * A switch that will return the Target Types associated to a given element and
 * feature corresponding to an Interpreted Expression. This default switch will
 * treat all description elements expected representation-specific elements.
 * 
 * @since 0.9.0
 * @author <a href="mailto:alex.lagarde@obeo.fr">Alex Lagarde</a>
 */
public class DefaultInterpretedExpressionTargetSwitch implements IInterpretedExpressionTargetSwitch {

    private DescriptionInterpretedExpressionTargetSwitch descriptionSwitch;

    private StyleInterpretedExpressionTargetSwitch styleSwitch;

    private ToolInterpretedExpressionTargetSwitch toolSwitch;

    private ValidationInterpretedExpressionTargetSwitch validationSwitch;

    /**
     * Default constructor.
     * 
     * @param feature
     *            the feature containing the Interpreted expression
     * @param globalSwitch
     *            the global switch to use
     */
    public DefaultInterpretedExpressionTargetSwitch(EStructuralFeature feature, IInterpretedExpressionTargetSwitch globalSwitch) {
        IInterpretedExpressionTargetSwitch theGlobalSwitch = Optional.ofNullable(globalSwitch).orElse(this);
        this.descriptionSwitch = new DescriptionInterpretedExpressionTargetSwitch(feature, theGlobalSwitch);
        this.styleSwitch = new StyleInterpretedExpressionTargetSwitch(feature, theGlobalSwitch);
        this.toolSwitch = new ToolInterpretedExpressionTargetSwitch(feature, theGlobalSwitch);
        this.validationSwitch = new ValidationInterpretedExpressionTargetSwitch(feature, theGlobalSwitch);
    }

    /**
     * 
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.business.api.dialect.description.IInterpretedExpressionTargetSwitch#doSwitch(org.eclipse.emf.ecore.EObject,
     *      boolean)
     */
    public java.util.Optional<Collection<String>> doSwitch(EObject theEObject, boolean considerFeature) {
        Collection<String> targetTypes = new LinkedHashSet<>();
        java.util.Optional<Collection<String>> expressionTarget = java.util.Optional.of(targetTypes);
        if (theEObject != null) {
            descriptionSwitch.setConsiderFeature(considerFeature);
            expressionTarget = descriptionSwitch.doSwitch(theEObject);

            if (expressionTarget.isPresent() && expressionTarget.get().isEmpty()) {
                styleSwitch.setConsiderFeature(considerFeature);
                expressionTarget = styleSwitch.doSwitch(theEObject);
            }

            if (expressionTarget.isPresent() && expressionTarget.get().isEmpty()) {
                validationSwitch.setConsiderFeature(considerFeature);
                expressionTarget = validationSwitch.doSwitch(theEObject);
            }

            // Tool in last position -> tool will return a default EObject value
            // as domain class.
            if (expressionTarget.isPresent() && expressionTarget.get().isEmpty()) {
                toolSwitch.setConsiderFeature(considerFeature);
                expressionTarget = toolSwitch.doSwitch(theEObject);
            }
        }
        return expressionTarget;
    }

    @Override
    public EObject getFirstRelevantContainer(EObject obj) {
        if (obj != null) {
            EObject container = obj.eContainer();
            while (container != null && !isRelevant(container)) {
                container = container.eContainer();
            }
            return container;
        } else {
            return null;
        }
    }

    private boolean isRelevant(EObject container) {
        return container instanceof RepresentationDescription || container instanceof RepresentationElementMapping || container instanceof RepresentationExtensionDescription;
    }

}
