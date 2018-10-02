/**
 * Copyright (c) 2014 Obeo
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - initial API and implementation
 */
package org.eclipse.sirius.sample.basicfamily.services;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.sirius.sample.basicfamily.Family;
import org.eclipse.sirius.sample.basicfamily.Person;

public class FamilyServices {

    public List<Person> getMembersWithoutMother(Person person) {
        Family family = (Family) person.eContainer();
        List<Person> result = new ArrayList<Person>();
        for (Person person2 : family.getMembers()) {
            if (person2.getMother() == null)
                result.add(person2);
        }
        return result;
    }
}
