/*******************************************************************************
 * Copyright (c) 2007, 2018 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.diagram.editor.properties.sections.style.edgestyledescription;

// Start of user code imports

import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.sirius.diagram.description.FoldingStyle;
import org.eclipse.sirius.diagram.description.style.StylePackage;
import org.eclipse.sirius.editor.properties.sections.common.AbstractRadioButtonPropertySection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

// End of user code imports

/**
 * A section for the foldingStyle property of a EdgeStyleDescription object.
 */
public class EdgeStyleDescriptionFoldingStylePropertySection extends AbstractRadioButtonPropertySection {
    /**
     * @see org.eclipse.sirius.diagram.editor.properties.sections.AbstractRadioButtonPropertySection#getDefaultLabelText()
     */
    @Override
    protected String getDefaultLabelText() {
        return "FoldingStyle"; //$NON-NLS-1$
    }

    /**
     * @see org.eclipse.sirius.diagram.editor.properties.sections.AbstractRadioButtonPropertySection#getLabelText()
     */
    @Override
    protected String getLabelText() {
        String labelText;
        labelText = super.getLabelText() + ":"; //$NON-NLS-1$
        // Start of user code get label text

        // End of user code get label text
        return labelText;
    }

    /**
     * @see org.eclipse.sirius.diagram.editor.properties.sections.AbstractRadioButtonPropertySection#getFeature()
     */
    @Override
    protected EAttribute getFeature() {
        return StylePackage.eINSTANCE.getEdgeStyleDescription_FoldingStyle();
    }

    /**
     * @see org.eclipse.sirius.diagram.editor.properties.sections.AbstractRadioButtonPropertySection#getFeatureValue(int)
     */
    @Override
    protected Object getFeatureValue(int index) {
        return getChoiceOfValues().get(index);
    }

    /**
     * @see org.eclipse.sirius.diagram.editor.properties.sections.AbstractRadioButtonPropertySection#isEqual(int)
     */
    @Override
    protected boolean isEqual(int index) {
        return getChoiceOfValues().get(index).equals(eObject.eGet(getFeature()));
    }

    /**
     * @see org.eclipse.sirius.diagram.editor.properties.sections.AbstractRadioButtonPropertySection#getEnumerationFeatureValues()
     */
    @Override
    protected List<?> getChoiceOfValues() {
        return FoldingStyle.VALUES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
        super.createControls(parent, tabbedPropertySheetPage);

    }
}
