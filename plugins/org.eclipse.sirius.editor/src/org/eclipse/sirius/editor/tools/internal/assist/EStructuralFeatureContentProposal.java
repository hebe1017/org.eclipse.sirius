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
package org.eclipse.sirius.editor.tools.internal.assist;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.fieldassist.IContentProposal;

/**
 * A {@link IContentProposal} for EAttribute names.
 * 
 * @author <a href="mailto:esteban.dugueperoux@obeo.fr">Esteban Dugueperoux</a>
 */
public class EStructuralFeatureContentProposal implements IContentProposal {

    private EStructuralFeature eStructuralFeature;

    private int cursorPosition;

    /**
     * Default constructor.
     * 
     * @param eStructuralFeature
     *            the proposed {@link EStructuralFeature}
     * @param cursorPosition
     *            the current cursor position
     */
    public EStructuralFeatureContentProposal(EStructuralFeature eStructuralFeature, int cursorPosition) {
        this.eStructuralFeature = eStructuralFeature;
        this.cursorPosition = cursorPosition;
    }

    public String getContent() {
        return eStructuralFeature.getName().substring(cursorPosition);
    }

    public int getCursorPosition() {
        return cursorPosition;
    }

    public String getLabel() {
        return eStructuralFeature.getName();
    }

    public String getDescription() {
        return eStructuralFeature.eClass().getName() + "." + eStructuralFeature.getName();
    }

}
