/*******************************************************************************
 * Copyright (c) 2016, 2017 Obeo.
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
package org.eclipse.sirius.ui.properties.internal.preferences;

import org.eclipse.sirius.ext.base.I18N;
import org.eclipse.sirius.ext.base.I18N.TranslatableMessage;
import org.eclipse.sirius.ui.properties.internal.SiriusUIPropertiesPlugin;

/**
 * Helper class to obtain translated strings.
 * 
 * @author mbats
 */
public final class Messages {

    static {
        I18N.initializeMessages(Messages.class, SiriusUIPropertiesPlugin.INSTANCE);
    }

    // CHECKSTYLE:OFF
    @TranslatableMessage
    public static String SiriusPropertiesPreferencePage_filterGroup;

    @TranslatableMessage
    public static String SiriusPropertiesPreferencePage_semanticTab;

    @TranslatableMessage
    public static String SiriusPropertiesPreferencePage_defaultTab;

    @TranslatableMessage
    public static String SiriusPropertiesPreferencePage_tabGroup;

    @TranslatableMessage
    public static String SiriusPropertiesPreferencePage_maxLengthTabName;

    @TranslatableMessage
    public static String SiriusPropertiesPreferencePage_maxLengthTabName_invalidValue;

    @TranslatableMessage
    public static String SiriusPropertiesPreferencePage_maxLengthTabName_invalidInteger;

    // CHECKSTYLE:ON

    private Messages() {
        // Prevents instanciation.
    }
}
