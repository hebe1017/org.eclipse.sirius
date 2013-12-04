/*******************************************************************************
 * Copyright (c) 2009 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.business.api.componentization;

/**
 * A listener interface to listen events in the ViewpointRegistry.
 * 
 * @author mchauvin
 * @since 0.9.0
 */
public interface ViewointRegistryListener2 {

    /**
     * Called when one or several modeler description files have been loaded or
     * reloaded.
     */
    void modelerDesciptionFilesLoaded();

}
