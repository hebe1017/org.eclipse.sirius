/*******************************************************************************
 * Copyright (c) 2011, 2014 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.business.internal.dialect.description;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.sirius.business.api.dialect.description.IInterpretedExpressionTargetSwitch;
import org.eclipse.sirius.diagram.ContainerLayout;
import org.eclipse.sirius.diagram.description.ContainerMapping;
import org.eclipse.sirius.diagram.description.DiagramElementMapping;
import org.eclipse.sirius.diagram.description.EdgeMapping;
import org.eclipse.sirius.diagram.description.NodeMapping;
import org.eclipse.sirius.diagram.description.filter.FilterPackage;
import org.eclipse.sirius.diagram.description.filter.MappingFilter;
import org.eclipse.sirius.diagram.description.filter.VariableFilter;
import org.eclipse.sirius.diagram.description.filter.util.FilterSwitch;



/**
 * A switch that will return the Target Types associated to a given element
 * (part of the
 * {@link org.eclipse.sirius.viewpoint.description.filter.FilterPackage}) and
 * feature corresponding to an Interpreted Expression. For example, for a
 * NodeMapping :
 * <p>
 * <li>if the feature is semantic candidate expression, we return the domain
 * class of the first valid container (representation element mapping or
 * representation description).</li>
 * <li>if the feature is any other interpreted expression, we return the domain
 * class associated to this mapping</li>
 * </p>
 * 
 * Can return {@link Options#newNone()} if the given expression does not require
 * any target type (for example, a Popup menu contribution only uses variables
 * in its expressions).
 * 
 * @author <a href="mailto:alex.lagarde@obeo.fr">Alex Lagarde</a>
 */
public class FilterInterpretedExpressionTargetSwitch extends FilterSwitch<java.util.Optional<Collection<String>>> {

    /**
     * Constant used in switches on feature id to consider the case when the
     * feature must not be considered.
     */
    private static final int DO_NOT_CONSIDER_FEATURE = -1;

    /**
     * The ID of the feature containing the Interpreted expression.
     */
    protected int featureID;

    private IInterpretedExpressionTargetSwitch globalSwitch;

    private int lastFeatureID;

    private EStructuralFeature feature;

    /**
     * Default constructor.
     * 
     * @param feature
     *            the feature containing the Interpreted expression
     * @param defaultInterpretedExpressionTargetSwitch
     *            the global switch to use
     */
    public FilterInterpretedExpressionTargetSwitch(EStructuralFeature feature, IInterpretedExpressionTargetSwitch defaultInterpretedExpressionTargetSwitch) {
        super();
        this.featureID = feature != null ? feature.getFeatureID() : DO_NOT_CONSIDER_FEATURE;
        this.lastFeatureID = featureID;
        this.globalSwitch = defaultInterpretedExpressionTargetSwitch;
        this.feature = feature;
    }

    /**
     * 
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.viewpoint.description.tool.util.ToolSwitch#doSwitch(org.eclipse.emf.ecore.EObject)
     */
    @Override
    public java.util.Optional<Collection<String>> doSwitch(EObject theEObject) {
        java.util.Optional<Collection<String>> doSwitch = super.doSwitch(theEObject);
        if (doSwitch != null) {
            return doSwitch;
        }
        Collection<String> targets = new LinkedHashSet<>();
        return java.util.Optional.of(targets);
    }

    /**
     * 
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.viewpoint.description.filter.util.FilterSwitch#caseMappingFilter(org.eclipse.sirius.viewpoint.description.filter.MappingFilter)
     */
    @Override
    public java.util.Optional<Collection<String>> caseMappingFilter(MappingFilter object) {
        Collection<String> targetTypes = new LinkedHashSet<>();
        for (DiagramElementMapping mapping : object.getMappings()) {
            if (feature == FilterPackage.Literals.MAPPING_FILTER__VIEW_CONDITION_EXPRESSION) {
                if (mapping instanceof EdgeMapping) {
                    targetTypes.add("diagram.DEdge"); //$NON-NLS-1$
                } else if (mapping instanceof NodeMapping) {
                    targetTypes.add("diagram.DNode"); //$NON-NLS-1$
                } else if (mapping instanceof ContainerMapping) {
                    if (((ContainerMapping) mapping).getChildrenPresentation() == ContainerLayout.LIST) {
                        targetTypes.add("diagram.DNodeContainer"); //$NON-NLS-1$
                    } else {
                        targetTypes.add("diagram.DNodeList"); //$NON-NLS-1$
                    }
                }
            } else {
                java.util.Optional<Collection<String>> targetTypesForMapping = globalSwitch.doSwitch(mapping, false);
                if (targetTypesForMapping.isPresent()) {
                    targetTypes.addAll(targetTypesForMapping.get());
                }
            }

        }
        return java.util.Optional.of(targetTypes);
    }

    /**
     * 
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.viewpoint.description.filter.util.FilterSwitch#caseVariableFilter(org.eclipse.sirius.viewpoint.description.filter.VariableFilter)
     */
    @Override
    public java.util.Optional<Collection<String>> caseVariableFilter(VariableFilter object) {
        // A VariableFilter has no context, and yet it should be evaluated
        return java.util.Optional.empty();
    }

    /**
     * Changes the behavior of this switch : if true, then the feature will be
     * considered to calculate target types ; if false, then the feature will be
     * ignored.
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
}
