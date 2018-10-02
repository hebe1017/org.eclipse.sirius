/*******************************************************************************
 * Copyright (c) 2012 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.business.internal.migration.description;

import org.eclipse.emf.common.util.URI;
import org.eclipse.sirius.business.internal.migration.AbstractVersionSAXParser;
import org.eclipse.sirius.viewpoint.description.DescriptionPackage;

/**
 * A parser to get VSM version.
 * 
 * @author fbarbin
 * 
 */
public class VSMVersionSAXParser extends AbstractVersionSAXParser {

    /* In VSM files, the Group element is versioned. */
    private static final String VERSIONED_ELEMENT_QUALIFIED_NAME = DescriptionPackage.eINSTANCE.getNsPrefix() + ":" + DescriptionPackage.eINSTANCE.getGroup().getName(); //$NON-NLS-1$

    /**
     * Constructor.
     * 
     * @param vsmResourceUri
     *            the VSM resource to parse version.
     */
    public VSMVersionSAXParser(URI vsmResourceUri) {
        super(vsmResourceUri);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getVersionedElementQualifiedName() {
        return VERSIONED_ELEMENT_QUALIFIED_NAME;
    }
}
