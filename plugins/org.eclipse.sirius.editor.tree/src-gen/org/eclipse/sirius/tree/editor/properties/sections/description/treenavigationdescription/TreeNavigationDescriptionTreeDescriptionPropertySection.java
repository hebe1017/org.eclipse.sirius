/*******************************************************************************
 * Copyright (c) 2007, 2018 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.tree.editor.properties.sections.description.treenavigationdescription;

// Start of user code imports

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.sirius.editor.editorPlugin.SiriusEditor;
import org.eclipse.sirius.editor.properties.sections.common.AbstractComboPropertySection;
import org.eclipse.sirius.tree.description.DescriptionPackage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

// End of user code imports

/**
 * A section for the treeDescription property of a TreeNavigationDescription object.
 */
public class TreeNavigationDescriptionTreeDescriptionPropertySection extends AbstractComboPropertySection {
    /**
     * @see org.eclipse.sirius.tree.editor.properties.sections.AbstractComboPropertySection#getDefaultLabelText()
     */
    @Override
    protected String getDefaultLabelText() {
        return "TreeDescription"; //$NON-NLS-1$
    }

    /**
     * @see org.eclipse.sirius.tree.editor.properties.sections.AbstractComboPropertySection#getLabelText()
     */
    @Override
    protected String getLabelText() {
        String labelText;
        labelText = super.getLabelText() + "*:"; //$NON-NLS-1$
        // Start of user code get label text

        // End of user code get label text
        return labelText;
    }

    /**
     * @see org.eclipse.sirius.tree.editor.properties.sections.AbstractComboPropertySection#getFeature()
     */
    @Override
    protected EReference getFeature() {
        return DescriptionPackage.eINSTANCE.getTreeNavigationDescription_TreeDescription();
    }

    /**
     * @see org.eclipse.sirius.tree.editor.properties.sections.AbstractComboPropertySection#getFeatureValue(int)
     */
    @Override
    protected Object getFeatureValue(int index) {
        return getFeatureValueAt(index);
    }

    /**
     * @see org.eclipse.sirius.tree.editor.properties.sections.AbstractComboPropertySection#isEqual(int)
     */
    @Override
    protected boolean isEqual(int index) {
        boolean isEqual = false;
        if (getFeatureValueAt(index) == null) {
            isEqual = eObject.eGet(getFeature()) == null;
        } else {
            isEqual = getFeatureValueAt(index).equals(eObject.eGet(getFeature()));
        }
        return isEqual;
    }

    /**
     * Returns the value at the specified index in the choice of values for the feature.
     *
     * @param index
     *            Index of the value.
     * @return the value at the specified index in the choice of values.
     */
    protected Object getFeatureValueAt(int index) {
        List<?> values = getChoiceOfValues();
        if (values.size() < index || values.size() == 0 || index == -1) {
            return null;
        }
        return values.get(index);
    }

    /**
     * Fetches the list of available values for the feature.
     *
     * @return The list of available values for the feature.
     */
    @Override
    protected List<?> getChoiceOfValues() {
        List<?> values = Collections.emptyList();
        List<IItemPropertyDescriptor> propertyDescriptors = getDescriptors();
        for (IItemPropertyDescriptor propertyDescriptor : propertyDescriptors) {
            if (((EStructuralFeature) propertyDescriptor.getFeature(eObject)) == getFeature()) {
                values = new ArrayList<Object>(propertyDescriptor.getChoiceOfValues(eObject));
            }
        }

        // Start of user code choice of values
        // End of user code choice of values
        return values;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
        super.createControls(parent, tabbedPropertySheetPage);
        combo.setToolTipText("The Tree Description to navigate to");

        CLabel help = getWidgetFactory().createCLabel(composite, "");
        FormData data = new FormData();
        data.top = new FormAttachment(combo, 0, SWT.TOP);
        data.left = new FormAttachment(nameLabel);
        help.setLayoutData(data);
        help.setImage(getHelpIcon());
        help.setToolTipText("The Tree Description to navigate to");
        nameLabel.setFont(SiriusEditor.getFontRegistry().get("required"));
        // Start of user code create controls

        // End of user code create controls
    }
    // Start of user code user operations

    // End of user code user operations
}
