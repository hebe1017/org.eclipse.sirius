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
package org.eclipse.sirius.properties.core.api;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.eef.core.api.InputDescriptor;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.sirius.properties.core.internal.SiriusContext;

/**
 * An EEF InputDescriptor for elements selected in a Sirius context.
 * 
 * @author Pierre-Charles David <pierre-charles.david@obeo.fr>
 */
public class SiriusInputDescriptor implements InputDescriptor {
    /**
     * The full context, determined from the original input.
     */
    private final SiriusContext context;

    /**
     * Creates a SiriusInputDescriptor from the specified input.
     * 
     * @param input
     *            the original input.
     */
    public SiriusInputDescriptor(Object input) {
        this.context = SiriusContext.from(input);
    }

    @Override
    public Object getOriginalSelection() {
        return context.getInput();
    }

    @Override
    public EObject getSemanticElement() {
        java.util.Optional<EObject> obj = context.getMainSemanticElement();
        if (obj.isPresent()) {
            return obj.get();
        } else {
            return null;
        }
    }

    /**
     * Returns all the semantic model element associated with the current selection, including secondary associated
     * elements if any.
     * 
     * @return all the semantic model element associated with the current selection.
     */
    public Set<EObject> getAllSemanticElements() {
        Set<EObject> result = new LinkedHashSet<>();
        result.add(getSemanticElement());
        java.util.Optional<List<EObject>> additional = context.getAdditionalSemanticElements();
        if (additional.isPresent()) {
            result.addAll(additional.get());
        }
        return result;
    }

    /**
     * Returns the full Sirius context determined from the original input, which may include addition Sirius-specific
     * information in addition to what can be exposed through the generic {@link InputDescriptor} API.
     * 
     * @return the full Sirius context determined from the original input.
     */
    public SiriusContext getFullContext() {
        return context;
    }

}
