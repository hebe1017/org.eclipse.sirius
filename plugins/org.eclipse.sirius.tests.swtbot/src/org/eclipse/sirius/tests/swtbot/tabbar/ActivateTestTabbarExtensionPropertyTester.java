/*******************************************************************************
 * Copyright (c) 2010, 2014 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.tests.swtbot.tabbar;

import org.eclipse.core.expressions.PropertyTester;

/**
 * Property tester to enable some tabbar extensions only for and from the
 * concerned test.
 * 
 * @author mporhel
 */
public class ActivateTestTabbarExtensionPropertyTester extends PropertyTester {

    private static boolean active = false;

    /**
     * {@inheritDoc}
     */
    public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
        if ("shouldActivateTestTabbarExtension".equals(property)) {
            return active;
        }
        return false;
    }

    /**
     * Enable/Disable test tabbar extensions.
     * @param activate activate the extension.
     */
    public static void enableTestTabbarExtensions(boolean activate) {
        active = activate;
    }

}
