/**
 * Copyright (c) 2007, 2018 THALES GLOBAL SERVICES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *
 */
package org.eclipse.sirius.viewpoint.description.util;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.XMLLoad;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.sirius.business.internal.migration.IMigrationHandler;


import org.eclipse.sirius.viewpoint.SiriusPlugin;

/**
 * <!-- begin-user-doc --> The <b>Resource </b> associated with the package. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.viewpoint.description.util. DescriptionResourceFactoryImpl
 * @not-generated
 */
public class DescriptionResourceImpl extends XMIResourceImpl {

    /**
     * Option to specify if we use uri fragment as id in {@link XMLResourceImpl#getIDToEObjectMap()} map to enhance
     * inter resources proxy resolution. Default is false. This option is considered only for odesign in plugin.
     */
    public static final String OPTION_USE_URI_FRAGMENT_AS_ID = "SIRIUS_USE_URI_FRAGMENT_AS_ID"; //$NON-NLS-1$

    private boolean useURIFragmentAsId;

    /** Handler used to configure migration process. */
    private Optional<IMigrationHandler> migrationHandler;

    /**
     * Creates an instance of the resource. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param uri
     *            the URI of the new resource.
     * @not-generated
     */
    protected DescriptionResourceImpl(URI uri) {
        super(uri);
        migrationHandler = Optional.ofNullable(SiriusPlugin.getDefault().getMigrationHandler());
    }

    @Override
    protected XMLHelper createXMLHelper() {
        if (!migrationHandler.isPresent()) {
            return super.createXMLHelper();
        } else {
            return migrationHandler.get().createXMLHelper(this);
        }
    }

    @Override
    protected XMLLoad createXMLLoad(Map<?, ?> options) {
        XMLLoad xmlLoad = null;
        if (migrationHandler.isPresent()) {
            xmlLoad = migrationHandler.get().createXMLLoad(options, this);
        }
        if (xmlLoad == null) {
            xmlLoad = super.createXMLLoad(options);
        }
        return xmlLoad;
    }

    @Override
    public void setModified(boolean isModified) {
        super.setModified(isModified);
        if (isModified) {
            getIDToEObjectMap().clear();
        }
    }

    @Override
    public void load(Map<?, ?> options) throws IOException {
        if (!isLoaded) {
            useURIFragmentAsId = Boolean.TRUE.equals(options.get(DescriptionResourceImpl.OPTION_USE_URI_FRAGMENT_AS_ID)) && getURI().isPlatformPlugin();
            Diagnostic migrationMismatchDiagnostic = null;
            if (migrationHandler.isPresent()) {
                migrationMismatchDiagnostic = migrationHandler.get().handleMigrationOptions(getURI(), this.getDefaultLoadOptions(), this.getDefaultSaveOptions());
            }
            super.load(options);
            if (migrationMismatchDiagnostic != null) {
                getErrors().add(migrationMismatchDiagnostic);
            }
        }
    }

    /**
     * Override to migrate fragment if necessary (when a reference has been renamed) before getting the EObject.
     */
    @Override
    public EObject getEObject(String uriFragment) {
        java.util.Optional<String> optionalRewrittenFragment = java.util.Optional.empty();
        if (migrationHandler.isPresent()) {
            optionalRewrittenFragment = migrationHandler.get().getOptionalRewrittenFragment(uriFragment);
        }
        if (optionalRewrittenFragment.isPresent()) {
            return getEObject(optionalRewrittenFragment.get());
        } else if (useURIFragmentAsId) {
            return getEObjectUsingURIFragmentAsId(uriFragment);
        } else {
            return super.getEObject(uriFragment);
        }
    }

    private EObject getEObjectUsingURIFragmentAsId(String uriFragment) {
        EObject eObject = null;
        if (isLoading) {
            eObject = getEObjectAndUpdateIDMap(uriFragment);
        } else {
            eObject = getEObjectByID(uriFragment);
            if (eObject == null) {
                eObject = getEObjectAndUpdateIDMap(uriFragment);
            }
        }
        return eObject;
    }

    private EObject getEObjectAndUpdateIDMap(String uriFragment) {
        EObject eObject = super.getEObject(uriFragment);
        if (eObject != null) {
            setID(eObject, uriFragment);
        }
        return eObject;
    }

} // DescriptionResourceImpl
