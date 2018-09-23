/*******************************************************************************
 * Copyright (c) 2011, 2017 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.business.internal.dialect.description;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.sirius.business.api.dialect.description.IInterpretedExpressionTargetSwitch;
import org.eclipse.sirius.business.api.query.EObjectQuery;


import org.eclipse.sirius.viewpoint.description.ColorDescription;
import org.eclipse.sirius.viewpoint.description.ColorStep;
import org.eclipse.sirius.viewpoint.description.ConditionalStyleDescription;
import org.eclipse.sirius.viewpoint.description.DescriptionPackage;
import org.eclipse.sirius.viewpoint.description.EAttributeCustomization;
import org.eclipse.sirius.viewpoint.description.GenericDecorationDescription;
import org.eclipse.sirius.viewpoint.description.InterpolatedColor;
import org.eclipse.sirius.viewpoint.description.SelectionDescription;
import org.eclipse.sirius.viewpoint.description.SemanticBasedDecoration;
import org.eclipse.sirius.viewpoint.description.TypedVariable;
import org.eclipse.sirius.viewpoint.description.VSMElementCustomization;
import org.eclipse.sirius.viewpoint.description.util.DescriptionSwitch;

/**
 * A switch that will return the Target Types associated to a given element (part of the {@link DescriptionPackage}) and
 * feature corresponding to an Interpreted Expression. For example, for a NodeMapping :
 * <p>
 * <li>if the feature is semantic candidate expression, we return the domain class of the first valid container
 * (representation element mapping or representation description).</li>
 * <li>if the feature is any other interpreted expression, we return the domain class associated to this mapping</li>
 * </p>
 * 
 * Can return {@link Options#newNone()} if the given expression does not require any target type (for example, a Popup
 * menu contribution only uses variables in its expressions).
 * 
 * @author <a href="mailto:alex.lagarde@obeo.fr">Alex Lagarde</a>
 * 
 */
public class DescriptionInterpretedExpressionTargetSwitch extends DescriptionSwitch<java.util.Optional<Collection<String>>> {

    /**
     * Constant used in switches on feature id to consider the case when the feature must not be considered.
     */
    private static final int DO_NOT_CONSIDER_FEATURE = -1;

    /**
     * The ID of the feature containing the Interpreted expression.
     */
    protected int featureID;

    /**
     * The global switch to delegate the doSwitch method to.
     */
    protected IInterpretedExpressionTargetSwitch globalSwitch;

    private int lastFeatureID;

    /**
     * Default constructor.
     * 
     * @param feature
     *            the feature containing the Interpreted expression
     * @param theGlobalSwitch
     *            the global switch to use
     */
    public DescriptionInterpretedExpressionTargetSwitch(EStructuralFeature feature, IInterpretedExpressionTargetSwitch theGlobalSwitch) {
        super();
        this.featureID = feature != null ? feature.getFeatureID() : DO_NOT_CONSIDER_FEATURE;
        lastFeatureID = featureID;
        this.globalSwitch = theGlobalSwitch;
    }

    /**
     * 
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.viewpoint.description.util.DescriptionSwitch#doSwitch(org.eclipse.emf.ecore.EObject)
     */
    @Override
    public java.util.Optional<Collection<String>> doSwitch(EObject theEObject) {
        java.util.Optional<Collection<String>> doSwitch = super.doSwitch(theEObject);
        if (doSwitch != null) {
            return doSwitch;
        }
        Collection<String> defaultResult = new LinkedHashSet<>();
        return java.util.Optional.of(defaultResult);
    }

    /**
     * Changes the behavior of this switch : if true, then the feature will be considered to calculate target types ; if
     * false, then the feature will be ignored.
     * 
     * @param considerFeature
     *            true if the feature should be considered, false otherwise
     */
    public void setConsiderFeature(boolean considerFeature) {
        if (considerFeature) {
            this.featureID = lastFeatureID;
        } else {
            lastFeatureID = this.featureID;
            this.featureID = DO_NOT_CONSIDER_FEATURE;
        }

    }

    /*
     * @see IInterpretedExpressionTargetSwitch#getFirstRelevantContainer()
     */
    private EObject getFirstRelevantContainer(EObject element) {
        return globalSwitch.getFirstRelevantContainer(element);
    }

    /**
     * 
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.viewpoint.description.util.DescriptionSwitch#caseSemanticBasedDecoration(org.eclipse.sirius.viewpoint.description.SemanticBasedDecoration)
     */
    @Override
    public java.util.Optional<Collection<String>> caseSemanticBasedDecoration(SemanticBasedDecoration object) {
        java.util.Optional<Collection<String>> result = null;
        Collection<String> target = new LinkedHashSet<>();
        switch (featureID) {
        case DescriptionPackage.SEMANTIC_BASED_DECORATION__PRECONDITION_EXPRESSION:
        case DescriptionPackage.DECORATION_DESCRIPTION__TOOLTIP_EXPRESSION:
        case DescriptionPackage.DECORATION_DESCRIPTION__IMAGE_EXPRESSION:
        case DO_NOT_CONSIDER_FEATURE:
            target.add(object.getDomainClass());
            result = java.util.Optional.of(target);
            break;
        default:
            break;
        }

        return result;
    }

    @Override
    public java.util.Optional<Collection<String>> caseGenericDecorationDescription(GenericDecorationDescription object) {
        java.util.Optional<Collection<String>> result = null;
        switch (featureID) {
        case DescriptionPackage.DECORATION_DESCRIPTION__PRECONDITION_EXPRESSION:
        case DescriptionPackage.DECORATION_DESCRIPTION__TOOLTIP_EXPRESSION:
        case DescriptionPackage.DECORATION_DESCRIPTION__IMAGE_EXPRESSION:
        case DO_NOT_CONSIDER_FEATURE:
            result = java.util.Optional.empty();
            break;
        default:
            break;
        }
        return result;
    }

    /**
     * 
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.viewpoint.description.util.DescriptionSwitch#caseConditionalStyleDescription(org.eclipse.sirius.viewpoint.description.ConditionalStyleDescription)
     */
    @Override
    public java.util.Optional<Collection<String>> caseConditionalStyleDescription(ConditionalStyleDescription styleDescription) {
        Collection<String> target = new LinkedHashSet<>();
        java.util.Optional<Collection<String>> result = java.util.Optional.of(target);
        switch (featureID) {
        case DescriptionPackage.CONDITIONAL_STYLE_DESCRIPTION__PREDICATE_EXPRESSION:
            result = globalSwitch.doSwitch(getFirstRelevantContainer(styleDescription), false);
            break;
        default:
            break;
        }
        return result;
    }

    @Override
    public java.util.Optional<Collection<String>> caseColorDescription(ColorDescription object) {
        return java.util.Optional.empty();
    }

    @Override
    public java.util.Optional<Collection<String>> caseColorStep(ColorStep object) {
        return java.util.Optional.empty();
    }

    @Override
    public java.util.Optional<Collection<String>> caseInterpolatedColor(InterpolatedColor object) {
        return java.util.Optional.empty();
    }

    @Override
    public java.util.Optional<Collection<String>> caseVSMElementCustomization(VSMElementCustomization object) {
        java.util.Optional<Collection<String>> result = null;
        switch (featureID) {
        case DescriptionPackage.VSM_ELEMENT_CUSTOMIZATION__PREDICATE_EXPRESSION:
        case DO_NOT_CONSIDER_FEATURE:
            result = java.util.Optional.empty();
            break;
        default:
            break;
        }

        return result;
    }

    @Override
    public java.util.Optional<Collection<String>> caseEAttributeCustomization(EAttributeCustomization object) {
        java.util.Optional<Collection<String>> result = null;
        switch (featureID) {
        case DescriptionPackage.EATTRIBUTE_CUSTOMIZATION__VALUE:
        case DO_NOT_CONSIDER_FEATURE:
            result = java.util.Optional.empty();
            break;
        default:
            break;
        }

        return result;
    }

    /**
     * 
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.viewpoint.description.util.DescriptionSwitch#caseSelectionDescription(org.eclipse.sirius.viewpoint.description.SelectionDescription)
     */
    @Override
    public java.util.Optional<Collection<String>> caseSelectionDescription(SelectionDescription selectionDescription) {
        java.util.Optional<Collection<String>> result = null;
        switch (featureID) {
        case DescriptionPackage.SELECTION_DESCRIPTION__CANDIDATES_EXPRESSION:
        case DescriptionPackage.SELECTION_DESCRIPTION__ROOT_EXPRESSION:
        case DescriptionPackage.SELECTION_DESCRIPTION__CHILDREN_EXPRESSION:
            EObjectQuery query = new EObjectQuery(selectionDescription);
            java.util.Optional<EObject> parentRepresentationDescription = query.getFirstAncestorOfType(DescriptionPackage.eINSTANCE.getRepresentationDescription());
            if (parentRepresentationDescription.isPresent()) {
                result = globalSwitch.doSwitch(parentRepresentationDescription.get(), false);
            }
            break;

        default:
            break;
        }
        return result;
    }

    @Override
    public java.util.Optional<Collection<String>> caseTypedVariable(TypedVariable object) {
        java.util.Optional<Collection<String>> result = null;
        switch (featureID) {
        case DescriptionPackage.TYPED_VARIABLE__DEFAULT_VALUE_EXPRESSION:
            EObjectQuery query = new EObjectQuery(object);
            java.util.Optional<EObject> parentRepresentationDescription = query.getFirstAncestorOfType(DescriptionPackage.eINSTANCE.getRepresentationDescription());
            if (parentRepresentationDescription.isPresent()) {
                result = globalSwitch.doSwitch(parentRepresentationDescription.get(), false);
            }
            break;

        default:
            break;
        }
        return result;
    }
}
