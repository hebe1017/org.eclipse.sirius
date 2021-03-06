/*******************************************************************************
 * Copyright (c) 2013, 2014 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.tests.sample.benchmark;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Test Case</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.sirius.tests.sample.benchmark.TestCase#getInputData
 * <em>Input Data</em>}</li>
 * <li>{@link org.eclipse.sirius.tests.sample.benchmark.TestCase#getResults <em>
 * Results</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.sirius.tests.sample.benchmark.BenchmarkPackage#getTestCase()
 * @model
 * @generated
 */
public interface TestCase extends NamedElement {
    /**
     * Returns the value of the '<em><b>Input Data</b></em>' reference. It is
     * bidirectional and its opposite is '
     * {@link org.eclipse.sirius.tests.sample.benchmark.InputData#getTests
     * <em>Tests</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Input Data</em>' containment reference list
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Input Data</em>' reference.
     * @see #setInputData(InputData)
     * @see org.eclipse.sirius.tests.sample.benchmark.BenchmarkPackage#getTestCase_InputData()
     * @see org.eclipse.sirius.tests.sample.benchmark.InputData#getTests
     * @model opposite="tests"
     * @generated
     */
    InputData getInputData();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.tests.sample.benchmark.TestCase#getInputData
     * <em>Input Data</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Input Data</em>' reference.
     * @see #getInputData()
     * @generated
     */
    void setInputData(InputData value);

    /**
     * Returns the value of the '<em><b>Results</b></em>' containment reference
     * list. The list contents are of type
     * {@link org.eclipse.sirius.tests.sample.benchmark.TimeResult}. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Results</em>' containment reference list isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Results</em>' containment reference list.
     * @see org.eclipse.sirius.tests.sample.benchmark.BenchmarkPackage#getTestCase_Results()
     * @model containment="true"
     * @generated
     */
    EList<TimeResult> getResults();

} // TestCase
