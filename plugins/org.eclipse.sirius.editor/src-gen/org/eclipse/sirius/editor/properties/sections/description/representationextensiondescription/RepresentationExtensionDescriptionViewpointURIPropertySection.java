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
package org.eclipse.sirius.editor.properties.sections.description.representationextensiondescription;

// Start of user code imports

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.sirius.editor.editorPlugin.SiriusEditor;
import org.eclipse.sirius.editor.properties.sections.common.AbstractTextPropertySection;
import org.eclipse.sirius.viewpoint.description.DescriptionPackage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

// End of user code imports

/**
 * A section for the viewpointURI property of a RepresentationExtensionDescription object.
 */
public class RepresentationExtensionDescriptionViewpointURIPropertySection extends AbstractTextPropertySection {

    /** Help control of the section. */
    protected CLabel help;

    /**
     * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
     */
    @Override
    public void refresh() {
        super.refresh();

        final String tooltip = getToolTipText();
        if (tooltip != null && help != null) {
            help.setToolTipText(getToolTipText());
        }
    }

    /**
     * @see org.eclipse.sirius.editor.properties.sections.AbstractTextPropertySection#getDefaultLabelText()
     */
    @Override
    protected String getDefaultLabelText() {
        return "ViewpointURI"; //$NON-NLS-1$
    }

    /**
     * @see org.eclipse.sirius.editor.properties.sections.AbstractTextPropertySection#getLabelText()
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
     * @see org.eclipse.sirius.editor.properties.sections.AbstractTextPropertySection#getFeature()
     */
    @Override
    public EAttribute getFeature() {
        return DescriptionPackage.eINSTANCE.getRepresentationExtensionDescription_ViewpointURI();
    }

    /**
     * @see org.eclipse.sirius.editor.properties.sections.AbstractTextPropertySection#getFeatureValue(String)
     */
    @Override
    protected Object getFeatureValue(String newText) {
        return newText;
    }

    /**
     * @see org.eclipse.sirius.editor.properties.sections.AbstractTextPropertySection#isEqual(String)
     */
    @Override
    protected boolean isEqual(String newText) {
        return getFeatureAsText().equals(newText);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
        super.createControls(parent, tabbedPropertySheetPage);

        text.setToolTipText(getToolTipText());

        help = getWidgetFactory().createCLabel(composite, "");
        FormData data = new FormData();
        data.top = new FormAttachment(text, 0, SWT.TOP);
        data.left = new FormAttachment(nameLabel);
        help.setLayoutData(data);
        help.setImage(getHelpIcon());
        help.setToolTipText(getToolTipText());
        nameLabel.setFont(SiriusEditor.getFontRegistry().get("required"));

        // Start of user code create controls

        // End of user code create controls

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getPropertyDescription() {
        return "URI of the viewpoint which contains the representation to extend. Example : viewpoint:/pluginID/ViewpointName.\nIn case of diagram extension that contains only Style Customizations, you can match several Viewpoints by using a java regular expression.";
    }

    // Start of user code user operations

    // End of user code user operations
}
