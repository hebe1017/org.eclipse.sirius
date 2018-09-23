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
package org.eclipse.sirius.editor.properties.sections.description.viewpoint;

import java.util.ArrayList;

// Start of user code imports

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.sirius.editor.properties.sections.common.AbstractTextPropertySection;
import org.eclipse.sirius.editor.properties.sections.common.AbstractViewpointPropertySection;
import org.eclipse.sirius.editor.tools.internal.presentation.ViewpoitnDependenciesSelectionDialog;

import org.eclipse.sirius.viewpoint.description.DescriptionPackage;
import org.eclipse.sirius.viewpoint.description.Viewpoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

// End of user code imports

/**
 * A section for the reuses property of a Viewpoint object.
 */
public class ViewpointReusesPropertySection extends AbstractTextPropertySection {

    /**
     * @see org.eclipse.sirius.editor.properties.sections.AbstractTextPropertySection#getDefaultLabelText()
     */
    @Override
    protected String getDefaultLabelText() {
        return "Reuses"; //$NON-NLS-1$
    }

    /**
     * @see org.eclipse.sirius.editor.properties.sections.AbstractTextPropertySection#getLabelText()
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
     * @see org.eclipse.sirius.editor.properties.sections.AbstractTextPropertySection#getFeature()
     */
    @Override
    public EAttribute getFeature() {
        return DescriptionPackage.eINSTANCE.getViewpoint_Reuses();
    }

    /**
     * @see org.eclipse.sirius.editor.properties.sections.AbstractTextPropertySection#getFeatureValue(String)
     */
    @Override
    protected Object getFeatureValue(String newText) {
        String[] values = newText.substring(1, newText.length() - 1).split(", ");
        return Arrays.asList(values);
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

        // Start of user code create controls
        /*
         * We want to add a "Select" button on the right of the text field, but the layout data
         */
        FormData data = new FormData();
        data.left = new FormAttachment(0, AbstractViewpointPropertySection.LABEL_WIDTH);
        data.right = new FormAttachment(90, 0);
        data.top = new FormAttachment(0, ITabbedPropertyConstants.VSPACE);
        text.setLayoutData(data);
        // Force users to use the dialog box instead of directly editing the
        // text file
        text.setEnabled(false);

        data = new FormData();
        data.left = new FormAttachment(0, 0);
        data.right = new FormAttachment(text, -ITabbedPropertyConstants.HSPACE - 20);
        data.top = new FormAttachment(text, 0, SWT.CENTER);
        nameLabel.setLayoutData(data);

        Button selectionButton = getWidgetFactory().createButton(composite, "Select", SWT.PUSH);
        data = new FormData();
        data.left = new FormAttachment(90, ITabbedPropertyConstants.HSPACE);
        data.right = new FormAttachment(100, 0);
        data.top = new FormAttachment(text, 0, SWT.CENTER);
        selectionButton.setLayoutData(data);
        selectionButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (eObject instanceof Viewpoint) {
                    java.util.Optional<Set<URI>> userChoice = new ViewpoitnDependenciesSelectionDialog((Viewpoint) eObject).selectReusedViewpoints(composite.getShell());
                    if (userChoice.isPresent()) {
                        List<URI> value = new ArrayList<URI>(userChoice.get());
                        EditingDomain editingDomain = ((IEditingDomainProvider) getPart()).getEditingDomain();
                        editingDomain.getCommandStack().execute(SetCommand.create(editingDomain, eObject, getFeature(), value));
                    }
                }
            }
        });
        // End of user code create controls

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getPropertyDescription() {
        return "";
    }

    // Start of user code user operations

    // End of user code user operations
}
