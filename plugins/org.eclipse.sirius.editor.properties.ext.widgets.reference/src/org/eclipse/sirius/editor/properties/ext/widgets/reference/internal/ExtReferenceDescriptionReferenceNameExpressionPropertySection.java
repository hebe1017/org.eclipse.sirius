/*******************************************************************************
 * Copyright (c) 2016, 2017 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.editor.properties.ext.widgets.reference.internal;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.sirius.editor.editorPlugin.SiriusEditor;
import org.eclipse.sirius.editor.properties.sections.common.AbstractTextWithButtonPropertySection;
import org.eclipse.sirius.editor.tools.api.assist.TypeContentProposalProvider;
import org.eclipse.sirius.editor.tools.internal.presentation.TextWithContentProposalDialog;
import org.eclipse.sirius.properties.ext.widgets.reference.propertiesextwidgetsreference.PropertiesExtWidgetsReferencePackage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/**
 * A section for the expression used to compute the name of the reference of a ExtReferenceDescription object. In order
 * to support the button used to open the dialog for the edition of an expression, this code depends on an internal
 * class from Eclipse Sirius, {@link TextWithContentProposalDialog}.
 * 
 * @author sbegaudeau
 */
@SuppressWarnings("restriction")
public class ExtReferenceDescriptionReferenceNameExpressionPropertySection extends AbstractTextWithButtonPropertySection {

    @Override
    protected String getDefaultLabelText() {
        return "Reference Name Expression"; //$NON-NLS-1$
    }

    @Override
    protected String getLabelText() {
        String labelText;
        labelText = super.getLabelText() + "*:"; //$NON-NLS-1$
        return labelText;
    }

    @Override
    public EAttribute getFeature() {
        return PropertiesExtWidgetsReferencePackage.eINSTANCE.getAbstractExtReferenceDescription_ReferenceNameExpression();
    }

    @Override
    protected Object getFeatureValue(String newText) {
        return newText;
    }

    @Override
    protected boolean isEqual(String newText) {
        return this.getFeatureAsText().equals(newText);
    }

    @Override
    public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
        super.createControls(parent, tabbedPropertySheetPage);

        text.setToolTipText(getToolTipText());
        /*
         * We set the color as it's a InterpretedExpression
         */
        text.setBackground(SiriusEditor.getColorRegistry().get("yellow")); //$NON-NLS-1$

        TypeContentProposalProvider.bindPluginsCompletionProcessors(this, text);

        FormData data = new FormData();
        data.top = new FormAttachment(text, 0, SWT.TOP);
        data.left = new FormAttachment(nameLabel);

        nameLabel.setFont(SiriusEditor.getFontRegistry().get("required")); //$NON-NLS-1$
    }

    @Override
    protected SelectionListener createButtonListener() {
        return new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                TextWithContentProposalDialog dialog = new TextWithContentProposalDialog(composite.getShell(), ExtReferenceDescriptionReferenceNameExpressionPropertySection.this, text.getText());
                dialog.open();
                text.setText(dialog.getResult());
                handleTextModified();
            }
        };
    }

    @Override
    protected String getPropertyDescription() {
        return ""; //$NON-NLS-1$
    }

}
