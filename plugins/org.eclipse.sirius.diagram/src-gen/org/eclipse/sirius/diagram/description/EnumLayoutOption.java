/**
 * Copyright (c) 2007, 2017 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *
 */
package org.eclipse.sirius.diagram.description;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Enum Layout java.util.Optional</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.diagram.description.EnumLayoutOption#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.diagram.description.DescriptionPackage#getEnumLayoutOption()
 * @model abstract="true"
 * @generated
 */
public interface EnumLayoutOption extends EnumOption {
    /**
     * Returns the value of the '<em><b>Value</b></em>' containment reference. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Value</em>' containment reference isn't clear, there really should be more of a
     * description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Value</em>' containment reference.
     * @see #setValue(EnumLayoutValue)
     * @see org.eclipse.sirius.diagram.description.DescriptionPackage#getEnumLayoutOption_Value()
     * @model containment="true" resolveProxies="true"
     * @generated
     */
    EnumLayoutValue getValue();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.diagram.description.EnumLayoutOption#getValue <em>Value</em>}'
     * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Value</em>' containment reference.
     * @see #getValue()
     * @generated
     */
    void setValue(EnumLayoutValue value);

} // EnumLayoutOption
