/*******************************************************************************
 * Copyright (c) 2007, 2016 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.diagram.formatdata;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract class of
 * the model. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.diagram.formatdata.FormatdataPackage
 * @generated
 */
public interface FormatdataFactory extends EFactory {
    /**
     * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    FormatdataFactory eINSTANCE = org.eclipse.sirius.diagram.formatdata.impl.FormatdataFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Node Format Data</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Node Format Data</em>'.
     * @generated
     */
    NodeFormatData createNodeFormatData();

    /**
     * Returns a new object of class '<em>Edge Format Data</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Edge Format Data</em>'.
     * @generated
     */
    EdgeFormatData createEdgeFormatData();

    /**
     * Returns a new object of class '<em>Point</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Point</em>'.
     * @generated
     */
    Point createPoint();

    /**
     * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the package supported by this factory.
     * @generated
     */
    FormatdataPackage getFormatdataPackage();

} // FormatdataFactory
