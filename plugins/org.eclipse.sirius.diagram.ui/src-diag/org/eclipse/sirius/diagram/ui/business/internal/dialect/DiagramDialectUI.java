/*******************************************************************************
 * Copyright (c) 2008 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.diagram.ui.business.internal.dialect;

import org.eclipse.sirius.ui.business.api.dialect.DialectUI;
import org.eclipse.sirius.ui.business.api.dialect.DialectUIServices;

/**
 * The dialect UI for the diagram.
 * 
 * @author CBrun
 */
public class DiagramDialectUI implements DialectUI {

    private DialectUIServices services;

    /**
     * 
     * {@inheritDoc}
     */
    public String getName() {
        return "diagram"; //$NON-NLS-1$
    }

    /**
     * 
     * {@inheritDoc}
     */
    public DialectUIServices getServices() {
        if (services == null) {
            services = new DiagramDialectUIServices();
        }
        return services;
    }

}
