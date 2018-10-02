/*******************************************************************************
 * Copyright (c) 2011 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.synchronizer;

public class StringSignature implements Signature {

    private String value;

    /**
     * Creates a new StringSignature.
     * 
     * @param value
     *            the value of this signature
     */
    public StringSignature(String value) {
        this.value = value;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof StringSignature) {
            return this.value.equals(((StringSignature) arg0).value);
        }
        return false;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }

}
