/*******************************************************************************
 * Copyright (c) 2018 Obeo.
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
package org.eclipse.sirius.services.diagram.api;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.diagram.WorkspaceImage;

/**
 * This interface is used in order to retrieve the path of images.
 *
 * @author sbegaudeau
 */
public interface ISiriusDiagramImagePathProvider {
    /**
     * Returns the path of the static image described by the given workspace
     * image.
     *
     * @param workspaceImage
     *            The workspace image
     * @return The path of the static image
     */
    Optional<String> getStaticImagePath(WorkspaceImage workspaceImage);

    /**
     * Returns the path of the image computed by the label provider of EMF Edit.
     *
     * @param eObject
     *            The eObject
     * @return The path of the image for the given EObject
     */
    Optional<String> getLabelProviderImagePath(EObject eObject);
}
