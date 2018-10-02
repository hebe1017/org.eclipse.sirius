/*******************************************************************************
 * Copyright (c) 2010, 2014 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.tests.unit.diagram.tools.data;

//Start of user code imports

import org.eclipse.sirius.viewpoint.description.Group;
import org.eclipse.sirius.viewpoint.description.Viewpoint;

//End of user code

public class GroupToolsApplicability {

    private Group val;

    public GroupToolsApplicability(Group val) {
        this.val = val;
    }

    public SiriusDesign design() {
        return new SiriusDesign((Viewpoint) val.getOwnedViewpoints().get(0));
    }

    public Group object() {
        return this.val;
    }
}
