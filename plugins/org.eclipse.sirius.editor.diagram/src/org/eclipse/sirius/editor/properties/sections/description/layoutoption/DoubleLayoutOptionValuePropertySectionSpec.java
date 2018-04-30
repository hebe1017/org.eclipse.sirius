/*******************************************************************************
 * Copyright (c) 2007, 2017 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.editor.properties.sections.description.layoutoption;

import org.eclipse.sirius.diagram.description.DoubleLayoutOption;
import org.eclipse.sirius.diagram.description.LayoutOption;
import org.eclipse.sirius.diagram.editor.properties.sections.description.doublelayoutoption.DoubleLayoutOptionValuePropertySection;
import org.eclipse.sirius.editor.Messages;
import org.eclipse.sirius.editor.properties.ViewpointPropertySheetPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/**
 * A property section allowing to edit double value of {@link DoubleLayoutOption} object.
 * 
 * This section contains a label, an help button, a text widget to edit the value and a removal item button to remove
 * container of the attribute.
 * 
 */
public class DoubleLayoutOptionValuePropertySectionSpec extends DoubleLayoutOptionValuePropertySection {

    /** Help control of the section. */
    protected CLabel help;

    /**
     * The button allowing to remove this layout option override.
     */
    protected Button removeOverrideButton;

    /**
     * The layout option represented by this property section.
     */
    private LayoutOption layoutOption;

    /**
     * Initialize this property section with the semantic element behind.
     * 
     * @param layoutOption
     *            the semantic element behind the section.
     */
    public DoubleLayoutOptionValuePropertySectionSpec(LayoutOption layoutOption) {
        this.layoutOption = layoutOption;
    }

    @Override
    protected String getLabelText() {
        return layoutOption.getLabel() == null ? Messages.LayoutOptionValue_defaultLabel : layoutOption.getLabel();
    }

    @Override
    public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
        super.createControls(parent, tabbedPropertySheetPage);
        eObject = layoutOption;

        FormData layoutData = (FormData) text.getLayoutData();
        layoutData.right = new FormAttachment(94, 0);

        removeOverrideButton = LayoutOptionPropertiesUtils.createRemoveOptionButton(text, composite, (ViewpointPropertySheetPage) tabbedPropertySheetPage, getWidgetFactory(), layoutOption);

        help = getWidgetFactory().createCLabel(composite, "");
        FormData data = new FormData();
        data.top = new FormAttachment(text, 0, SWT.TOP);
        data.left = new FormAttachment(nameLabel);
        help.setLayoutData(data);
        help.setImage(getHelpIcon());
        help.setToolTipText(getToolTipText());

    }

    @Override
    protected String getPropertyDescription() {
        return layoutOption.getDescription();
    }

    @Override
    protected void handleTextModified() {
        if (getPart() != null) {
            super.handleTextModified();
        }
    }

    @Override
    public void dispose() {
        if (help != null) {
            help.dispose();
        }
        if (removeOverrideButton != null) {
            removeOverrideButton.dispose();
        }
        super.dispose();
    }

}
