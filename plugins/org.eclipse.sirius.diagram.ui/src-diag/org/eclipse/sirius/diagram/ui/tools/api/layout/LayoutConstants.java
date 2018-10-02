/*******************************************************************************
 * Copyright (c) 2007, 2011 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.diagram.ui.tools.api.layout;

/**
 * This interface is used to keep track of the different constants available in
 * the layout plug-in.
 * 
 * @author cbrun
 */
public interface LayoutConstants {
    /**
     * The lowest priority.
     */
    int LOWEST_PRIORITY = 20;

    /**
     * A low priority.
     */
    int LOW_PRIORITY = 10;

    /**
     * A normal priority.
     */
    int NORMAL_PRIORITY = 5;

    /**
     * A high priority.
     */
    int HIGH_PRIORITY = 1;

    /**
     * The highest priority.
     */
    int HIGHEST_PRIORITY = 0;
}
