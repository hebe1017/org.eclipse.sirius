/*******************************************************************************
 * Copyright (c) 2009, 2009 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.diagram.ui.tools.api.migration;

import org.eclipse.sirius.diagram.DDiagramElement;

/**
 * Provides identifier for diagram elements.
 * 
 * @author ymortier
 */
public interface IDiagramIdentifierProvider {

    /**
     * Returns <code>true</code> if this provider provides an identifier for the
     * given diagram element.
     * 
     * @param element
     *            the element.
     * @return <code>true</code> if this provider provides an identifier for the
     *         given diagram element;
     */
    boolean provides(DDiagramElement element);

    /**
     * Returns the identifier of the given diagram element.
     * 
     * @param element
     *            the element.
     * @return the identifier of the given diagram element.
     */
    String computeIdentifier(DDiagramElement element);

}
