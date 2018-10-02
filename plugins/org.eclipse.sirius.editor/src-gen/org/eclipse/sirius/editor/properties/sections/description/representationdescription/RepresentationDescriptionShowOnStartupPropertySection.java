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
package org.eclipse.sirius.editor.properties.sections.description.representationdescription;

// Start of user code imports
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.sirius.editor.properties.sections.common.AbstractCheckBoxPropertySection;
import org.eclipse.sirius.viewpoint.description.DescriptionPackage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

// End of user code imports

/**
 * A section for the showOnStartup property of a RepresentationDescription object.
 */
public class RepresentationDescriptionShowOnStartupPropertySection extends AbstractCheckBoxPropertySection {
    /**
     * @see org.eclipse.sirius.editor.properties.sections.AbstractCheckBoxPropertySection#getDefaultLabelText()
     */
    @Override
    protected String getDefaultLabelText() {
        return "ShowOnStartup"; //$NON-NLS-1$
    }

    /**
     * @see org.eclipse.sirius.editor.properties.sections.AbstractCheckBoxPropertySection#getLabelText()
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
     * @see org.eclipse.sirius.editor.properties.sections.AbstractCheckBoxPropertySection#getFeature()
     */
    @Override
    protected EAttribute getFeature() {
        return DescriptionPackage.eINSTANCE.getRepresentationDescription_ShowOnStartup();
    }

    /**
     * @see org.eclipse.sirius.editor.properties.sections.AbstractCheckBoxPropertySection#getFeatureAsInteger()
     */
    @Override
    protected String getDefaultFeatureAsText() {
        String value = new String();
        if (eObject.eGet(getFeature()) != null) {
            value = toBoolean(eObject.eGet(getFeature()).toString()).toString();
        }
        return value;
    }

    /**
     * @see org.eclipse.sirius.editor.properties.sections.AbstractCheckBoxPropertySection#getFeatureValue(int)
     */
    @Override
    protected Object getFeatureValue(String newText) {
        return toBoolean(newText);
    }

    /**
     * @see org.eclipse.sirius.editor.properties.sections.AbstractCheckBoxPropertySection#isEqual(int)
     */
    @Override
    protected boolean isEqual(String newText) {
        boolean equal = true;
        if (toBoolean(newText) != null) {
            equal = getFeatureAsText().equals(toBoolean(newText).toString());
        } else {
            refresh();
        }
        return equal;
    }

    /**
     * Converts the given text to the boolean it represents if applicable.
     *
     * @return The boolean the given text represents if applicable, <code>null</code> otherwise.
     */
    private Boolean toBoolean(String text) {
        Boolean booleanValue = null;
        if (text.toLowerCase().matches("true|false")) {
            booleanValue = Boolean.parseBoolean(text);
        }
        return booleanValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
        super.createControls(parent, tabbedPropertySheetPage);
        checkbox.setToolTipText("If true, existing representations of this type will be automatically opened when a session is opened.");

        CLabel help = getWidgetFactory().createCLabel(composite, "");
        FormData data = new FormData();
        data.top = new FormAttachment(checkbox, 0, SWT.TOP);
        data.left = new FormAttachment(nameLabel);
        help.setLayoutData(data);
        help.setImage(getHelpIcon());
        help.setToolTipText("If true, existing representations of this type will be automatically opened when a session is opened.");
    }
}
