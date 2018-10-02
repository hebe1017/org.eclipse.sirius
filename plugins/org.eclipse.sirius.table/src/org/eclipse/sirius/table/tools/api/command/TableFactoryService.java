/*******************************************************************************
 * Copyright (c) 2007, 2008, 2009 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.table.tools.api.command;

import org.eclipse.sirius.table.tools.internal.command.TableCommandFactoryProvider;

/**
 * The Table Command Factory service. For the moment the class has only one
 * provider.
 * 
 * @author <a href="mailto:laurent.redor@obeo.fr">Laurent Redor</a>
 */
public final class TableFactoryService {

    private static final TableFactoryService INSTANCE = new TableFactoryService();

    private TableFactoryService() {

    }

    /**
     * Get the single instance.
     * 
     * @return the single instance
     */
    public static TableFactoryService getInstance() {
        return INSTANCE;
    }

    /**
     * Get the EMF Command Factory provider.
     * 
     * @return the EMF Command Factory provider
     */
    public ITableCommandFactoryProvider getNewProvider() {
        return new TableCommandFactoryProvider();
    }
}
