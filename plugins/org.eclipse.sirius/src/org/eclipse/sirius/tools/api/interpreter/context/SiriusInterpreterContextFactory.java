/*******************************************************************************
 * Copyright (c) 2013 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.tools.api.interpreter.context;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.sirius.business.api.dialect.DialectManager;
import org.eclipse.sirius.business.api.dialect.description.IInterpretedExpressionQuery;
import org.eclipse.sirius.common.tools.api.interpreter.DefaultInterpreterContextFactory;
import org.eclipse.sirius.common.tools.api.interpreter.IInterpreterContext;
import org.eclipse.sirius.common.tools.api.interpreter.VariableType;


/**
 * Provides facilities for creating {@link IInterpreterContext}s, using the
 * Sirius metamodel to get useful informations.
 * 
 * @since 0.9.0
 * @author alagarde
 * 
 */
public final class SiriusInterpreterContextFactory {

    private SiriusInterpreterContextFactory() {

    }

    /**
     * Creates the {@link InterpreterContext} allowing to validate the
     * InterpretedExpression of the given target, stored in the given feature.
     * 
     * @param element
     *            the element containing the InterpretedExpression (NodeMapping,
     *            ModelOperation...)
     * @param feature
     *            the feature corresponding to the InterpretedExpression to
     *            evaluate ( NodeMapping.semanticCandidatesExpression...)
     * @return the {@link InterpreterContext} allowing to validate the
     *         InterpretedExpression of the given target, stored in the given
     *         feature
     */
    public static IInterpreterContext createInterpreterContext(EObject element, EStructuralFeature feature) {
        Collection<String> targetDomainClasses = new LinkedHashSet<>();
        Collection<EPackage> avalaiblePackages = new LinkedHashSet<>();
        Collection<String> dependencies = new LinkedHashSet<>();
        Map<String, VariableType> variables = new LinkedHashMap<>();
        boolean requiresTargetType = true;

        // Step 1 : getting the InterpretedExpressionQuery from the given
        // element
        IInterpretedExpressionQuery query = DialectManager.INSTANCE.createInterpretedExpressionQuery(element, feature);

        // Step 2 : getting the DomainClass of the target
        java.util.Optional<Collection<String>> targetDomainClassesOption = query.getTargetDomainClasses();

        // If the considered expression does not need any Target class to be
        // validated
        if (!targetDomainClassesOption.isPresent()) {
            requiresTargetType = false;
        } else {
            for (String domainClass : targetDomainClassesOption.get()) {
                targetDomainClasses.add(domainClass);
            }
        }

        if (!targetDomainClassesOption.isPresent() || !targetDomainClassesOption.get().isEmpty()) {
            // Step 2 : getting the packages to import to evaluate the
            // expression
            avalaiblePackages = query.getPackagesToImport();

            // Step 3 : getting the defined variables
            variables = query.getAvailableVariables();

            // Step 4 : getting the defined variables
            dependencies = query.getDependencies();
        }

        // Step 5 : building the IInterpretedContext
        IInterpreterContext context = DefaultInterpreterContextFactory.createInterpreterContext(element, requiresTargetType, feature, VariableType.fromStrings(targetDomainClasses), avalaiblePackages,
                variables, dependencies);
        return context;
    }
}
