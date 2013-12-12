/*******************************************************************************
 * Copyright (c) 2010 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.business.internal.metamodel.description.tool.spec;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.sirius.diagram.description.DescriptionPackage;
import org.eclipse.sirius.diagram.description.DiagramElementMapping;
import org.eclipse.sirius.viewpoint.description.tool.impl.DeleteElementDescriptionImpl;

/**
 * Implementation of DeleteElementDescription.
 * 
 * @author nlepine
 * 
 */
public class DeleteElementDescriptionSpec extends DeleteElementDescriptionImpl {

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.viewpoint.description.tool.impl.DeleteElementDescriptionImpl#getMappings()
     */
    @Override
    public EList<DiagramElementMapping> getMappings() {
        if (this.eResource() == null) {
            throw new UnsupportedOperationException();
        }
        ECrossReferenceAdapter crossReferencer = ECrossReferenceAdapter.getCrossReferenceAdapter(this.eResource());
        if (crossReferencer == null) {
            throw new UnsupportedOperationException();
        }
        final List<DiagramElementMapping> diagramElementsMappings = new LinkedList<DiagramElementMapping>();
        final Collection<Setting> settings = crossReferencer.getInverseReferences(this, true);
        for (final Setting setting : settings) {
            final EObject eReferencer = setting.getEObject();
            final EStructuralFeature eFeature = setting.getEStructuralFeature();
            if (eReferencer instanceof DiagramElementMapping && eFeature.equals(DescriptionPackage.eINSTANCE.getDiagramElementMapping_DeletionDescription())) {
                diagramElementsMappings.add((DiagramElementMapping) eReferencer);
            }
        }
        return new BasicEList<DiagramElementMapping>(diagramElementsMappings);
    }

}
