/**
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
 *
 */
package org.eclipse.sirius.workflow.provider;

import org.eclipse.emf.common.notify.Adapter;

/**
 * Subclass used to not have to modify the generated code.
 *
 * @author sbegaudeau
 */
public class WorkflowItemProviderAdapterFactorySpec extends WorkflowItemProviderAdapterFactory {

    @Override
    public Adapter createWorkflowDescriptionAdapter() {
        if (workflowDescriptionItemProvider == null) {
            workflowDescriptionItemProvider = new WorkflowDescriptionItemProviderSpec(this);
        }

        return workflowDescriptionItemProvider;
    }

    @Override
    public Adapter createPageDescriptionAdapter() {
        if (pageDescriptionItemProvider == null) {
            pageDescriptionItemProvider = new PageDescriptionItemProviderSpec(this);
        }

        return pageDescriptionItemProvider;
    }

    @Override
    public Adapter createSectionDescriptionAdapter() {
        if (sectionDescriptionItemProvider == null) {
            sectionDescriptionItemProvider = new SectionDescriptionItemProviderSpec(this);
        }

        return sectionDescriptionItemProvider;
    }

    @Override
    public Adapter createActivityDescriptionAdapter() {
        if (activityDescriptionItemProvider == null) {
            activityDescriptionItemProvider = new ActivityDescriptionItemProviderSpec(this);
        }

        return activityDescriptionItemProvider;
    }
}
