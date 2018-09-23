/*******************************************************************************
 * Copyright (c) 2016 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.properties.core.internal.expressions;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.sirius.common.tools.api.interpreter.TypeName;


import org.eclipse.sirius.properties.CustomExpression;
import org.eclipse.sirius.properties.CustomOperation;
import org.eclipse.sirius.properties.DynamicMappingForDescription;
import org.eclipse.sirius.properties.DynamicMappingIfDescription;
import org.eclipse.sirius.properties.GroupDescription;
import org.eclipse.sirius.properties.GroupStyle;
import org.eclipse.sirius.properties.OperationDescription;
import org.eclipse.sirius.properties.PageDescription;
import org.eclipse.sirius.properties.PropertiesPackage;
import org.eclipse.sirius.properties.ToolbarAction;
import org.eclipse.sirius.properties.WidgetAction;
import org.eclipse.sirius.properties.WidgetConditionalStyle;
import org.eclipse.sirius.properties.WidgetDescription;
import org.eclipse.sirius.properties.WidgetStyle;
import org.eclipse.sirius.properties.util.PropertiesSwitch;

/**
 * Computes the domainClass (i.e. expected type of the receiver) for any interpreted expression defined on properties
 * views elements. Note that this does not handle element inside a properties view description which come from other
 * Sirius package (e.g. model operations).
 *
 * @author pcdavid
 */
public class DomainClassSwitch extends PropertiesSwitch<java.util.Optional<Collection<String>>> {
    /**
     * Constant used in switches on feature id to consider the case when the feature must not be considered.
     */
    private static final int DO_NOT_CONSIDER_FEATURE = -1;

    /**
     * The feature containing the Interpreted expression.
     */
    protected EStructuralFeature feature;

    /**
     * Indicates if the feature must be considered.
     */
    protected boolean considerFeature;

    /**
     * Default constructor.
     *
     * @param feature
     *            the feature containing the Interpreted expression
     */
    public DomainClassSwitch(EStructuralFeature feature) {
        this.feature = feature;
    }

    @Override
    public java.util.Optional<Collection<String>> doSwitch(EObject theEObject) {
        java.util.Optional<Collection<String>> doSwitch = super.doSwitch(theEObject);
        if (doSwitch != null && doSwitch.isPresent() && doSwitch.get().size() > 0) {
            return doSwitch;
        }
        Collection<String> defaultResult = new LinkedHashSet<>();
        defaultResult.add(TypeName.EOBJECT_TYPENAME.getCompleteName());
        return java.util.Optional.of(defaultResult);
    }

    /**
     * Changes the behavior of this switch: if true, then the feature will be considered to calculate target types; if
     * false, then the feature will be ignored.
     *
     * @param considerFeature
     *            true if the feature should be considered, false otherwise
     */
    public void setConsiderFeature(boolean considerFeature) {
        this.considerFeature = considerFeature;
    }

    @Override
    public java.util.Optional<Collection<String>> casePageDescription(PageDescription page) {
        java.util.Optional<Collection<String>> result = null;
        switch (getFeatureId(page.eClass())) {
        case PropertiesPackage.PAGE_DESCRIPTION__SEMANTIC_CANDIDATE_EXPRESSION:
            /*
             * A page can be activated from almost any kind of input: anything selectable from any of the
             * representations defined in the same VSM can trigger teh evaluation of the page's
             * semanticCandidateExpression. Technically we could compute a union of all the relevant semantic types, but
             * in practice there is little chance that it would be more useful than just EObject.
             */
            Collection<String> target = new LinkedHashSet<>();
            target.add(TypeName.EOBJECT_TYPENAME.getCompleteName());
            result = java.util.Optional.of(target);
            break;
        case PropertiesPackage.PAGE_DESCRIPTION__LABEL_EXPRESSION:
        case PropertiesPackage.PAGE_DESCRIPTION__PRECONDITION_EXPRESSION:
        case DO_NOT_CONSIDER_FEATURE:
            result = java.util.Optional.of(VSMNavigation.getPageDomainClass(page));
            break;
        default:
            break;
        }
        return result;
    }

    @Override
    public java.util.Optional<Collection<String>> caseGroupDescription(GroupDescription group) {
        java.util.Optional<Collection<String>> result = null;
        switch (getFeatureId(group.eClass())) {
        case PropertiesPackage.GROUP_DESCRIPTION__SEMANTIC_CANDIDATE_EXPRESSION:
            /*
             * A Group's semanticCandidateExpression is evaluated from the target of its referencing page, which is an
             * instance of the page's domainClass.
             */
            Collection<String> target = new LinkedHashSet<>();
            for (PageDescription page : VSMNavigation.findReferencingPages(group)) {
                target.addAll(VSMNavigation.getPageDomainClass(page));
            }
            result = java.util.Optional.of(target);
            break;
        case PropertiesPackage.GROUP_DESCRIPTION__LABEL_EXPRESSION:
        case PropertiesPackage.GROUP_DESCRIPTION__PRECONDITION_EXPRESSION:
        case DO_NOT_CONSIDER_FEATURE:
            result = java.util.Optional.of(VSMNavigation.getGroupDomainClass(group));
            break;
        default:
            break;
        }
        return result;
    }

    @Override
    public java.util.Optional<Collection<String>> caseToolbarAction(ToolbarAction object) {
        return VSMNavigation.getDomainClassFromContainingGroup(object);
    }

    @Override
    public java.util.Optional<Collection<String>> caseWidgetDescription(WidgetDescription object) {
        return VSMNavigation.getDomainClassFromContainingGroup(object);
    }

    @Override
    public java.util.Optional<Collection<String>> caseWidgetStyle(WidgetStyle object) {
        return VSMNavigation.getDomainClassFromContainingGroup(object);
    }

    @Override
    public java.util.Optional<Collection<String>> caseWidgetConditionalStyle(WidgetConditionalStyle object) {
        return VSMNavigation.getDomainClassFromContainingGroup(object);
    }

    @Override
    public java.util.Optional<Collection<String>> caseWidgetAction(WidgetAction object) {
        return VSMNavigation.getDomainClassFromContainingGroup(object);
    }

    @Override
    public java.util.Optional<Collection<String>> caseGroupStyle(GroupStyle object) {
        return VSMNavigation.getDomainClassFromContainingGroup(object);
    }

    @Override
    public java.util.Optional<Collection<String>> caseCustomExpression(CustomExpression object) {
        return VSMNavigation.getDomainClassFromContainingGroup(object);
    }

    @Override
    public java.util.Optional<Collection<String>> caseCustomOperation(CustomOperation object) {
        return VSMNavigation.getDomainClassFromContainingGroup(object);
    }

    @Override
    public java.util.Optional<Collection<String>> caseOperationDescription(OperationDescription object) {
        return VSMNavigation.getDomainClassFromContainingGroup(object);
    }

    @Override
    public java.util.Optional<Collection<String>> caseDynamicMappingForDescription(DynamicMappingForDescription object) {
        return VSMNavigation.getDomainClassFromContainingGroup(object);
    }

    @Override
    public java.util.Optional<Collection<String>> caseDynamicMappingIfDescription(DynamicMappingIfDescription object) {
        return VSMNavigation.getDomainClassFromContainingGroup(object);
    }

    private int getFeatureId(EClass eClass) {
        if (considerFeature && feature != null) {
            return eClass.getFeatureID(feature);
        } else {
            return DomainClassSwitch.DO_NOT_CONSIDER_FEATURE;
        }
    }
}
