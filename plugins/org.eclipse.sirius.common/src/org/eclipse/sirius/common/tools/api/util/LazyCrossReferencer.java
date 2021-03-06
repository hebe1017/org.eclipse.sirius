/*******************************************************************************
 * Copyright (c) 2009, 2017 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.common.tools.api.util;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;

import com.google.common.collect.Iterables;

/**
 * A lazy cross referencer which does nothing until one of its method is called. <BR>
 * <BR>
 * This cross referencer also reacts to {@link EObject} removal from their containing reference : it removes itself
 * automatically from their adapters and recursively from those of their contents. If the new container is already set
 * and also has the cross referencer, in this case cross referencer is not removed. <BR>
 * <BR>
 * This cross referencer also provide a way to disable the resolution of proxy. This can be useful to avoid reloading of
 * a resource during the unloading of it (caused by resolution of some proxy with crossReferencer).<BR>
 * <BR>
 * 
 * @see {@link org.eclipse.emf.transaction.impl.ResourceSetManager#observe(org.eclipse.emf.ecore.resource.Resource, Notification)}
 *      and message {@link org.eclipse.emf.transaction.internal.EMFTransactionStatusCodes.RELOAD_DURING_UNLOAD} this
 *      one.
 * 
 * @author mchauvin
 */
@SuppressWarnings("restriction")
public class LazyCrossReferencer extends ECrossReferenceAdapterWithUnproxyCapability {
    /**
     * Flag to know if the LazyCrossReferencer has been initialized.
     */
    protected boolean initialized;

    private Predicate<EObject> eObjectToBeIgnoredPredicate = obj -> false;

    /**
     * Subclasses should override, and call super.initialize().
     */
    protected void initialize() {
        initialized = true;
    }

    @Override
    public void dump() {
        if (!initialized) {
            initialize();
        }
        super.dump();
    }

    /**
     * Set a predicate to know if inverse references has to be searched for a given EObject.</br>
     * This can be useful to prevent the resolution of some references of the given EObject.
     * 
     * @param predicate
     *            the predicate
     */
    public void setEObjectToBeIgnored(Predicate<EObject> predicate) {
        this.eObjectToBeIgnoredPredicate = predicate;
    }

    @Override
    public Collection<Setting> getInverseReferences(final EObject object, final boolean resolve) {
        if (!eObjectToBeIgnoredPredicate.test(object)) {
            if (!initialized) {
                initialize();
            }
            return super.getInverseReferences(object, resolve);
        }
        return Collections.emptyList();
    }

    @Override
    public Collection<Setting> getInverseReferences(final EObject object) {
        if (!eObjectToBeIgnoredPredicate.test(object)) {
            if (!initialized) {
                initialize();
            }
            return super.getInverseReferences(object);
        }
        return Collections.emptyList();
    }

    @Override
    public Collection<Setting> getNonNavigableInverseReferences(final EObject object, final boolean resolve) {
        if (!eObjectToBeIgnoredPredicate.test(object)) {
            if (!initialized) {
                initialize();
            }
            return super.getNonNavigableInverseReferences(object, resolve);
        }
        return Collections.emptyList();
    }

    @Override
    public Collection<Setting> getNonNavigableInverseReferences(final EObject object) {
        if (!eObjectToBeIgnoredPredicate.test(object)) {
            if (!initialized) {
                initialize();
            }
            return super.getNonNavigableInverseReferences(object);
        }
        return Collections.emptyList();
    }

    @Override
    public Notifier getTarget() {
        if (!initialized) {
            initialize();
        }
        return super.getTarget();
    }

    @Override
    public boolean isAdapterForType(final Object type) {
        if (!initialized) {
            initialize();
        }
        return super.isAdapterForType(type);
    }

    @Override
    public void notifyChanged(final Notification notification) {
        if (!initialized) {
            initialize();
        }
        super.notifyChanged(notification);
    }

    @Override
    public void setTarget(final Notifier target) {
        if (!initialized) {
            initialize();
        }
        super.setTarget(target);
    }

    @Override
    public void unsetTarget(final Notifier target) {
        if (!initialized) {
            initialize();
        }
        super.unsetTarget(target);
    }

    /**
     * Look at all EObjects of this resource and resolve proxy cross reference that reference these EObjects.
     * 
     * @param resource
     *            Each cross reference pointing to a proxy of this <code>resource</code> will be resolved.
     */
    @Override
    public void resolveProxyCrossReferences(Resource resource) {
        if (initialized) {
            // The resolution of proxy is called only is the cross-referencer
            // has already been initialized.
            super.resolveProxyCrossReferences(resource);
        }
    }

    @Override
    protected void handleContainment(Notification notification) {
        deregisterDeletedElements(notification);

        super.handleContainment(notification);
    }

    /**
     * This method removes the current cross referencer adapter from adapters of removed elements. The removeAdapter
     * method propagate the removal to all contents of its parameter.
     * 
     * @param notification
     *            a containment notification
     */
    protected void deregisterDeletedElements(Notification notification) {
        switch (notification.getEventType()) {

        case Notification.UNSET:
        case Notification.SET:
        case Notification.REMOVE:
            Object oldValue = notification.getOldValue();
            if (oldValue instanceof Notifier) {
                removeAdapterIfNecessary(notification, (Notifier) oldValue);
            }
            break;

        case Notification.REMOVE_MANY:
            for (Notifier oldVal : Iterables.filter((Collection<?>) notification.getOldValue(), Notifier.class)) {
                removeAdapterIfNecessary(notification, oldVal);
            }
            break;

        default:
            break;
        }
    }

    /**
     * This method does not remove the adapter from the notification old value in two cases:
     * <ul>
     * <li>if its new container is already set and also has the adapter</li>
     * <li>if the element is now a root of a resource which has the adapter</li>
     * </ul>
     * 
     * @param notification
     *            a containment notification
     * @param oldValue
     *            notification old value on which the adapter may be removed
     */
    private void removeAdapterIfNecessary(Notification notification, Notifier oldValue) {
        boolean toRemove = true;

        if (oldValue instanceof EObject) {
            EObject removedEObject = (EObject) oldValue;

            Notifier currentContainer = removedEObject.eContainer();
            if (currentContainer == null) {
                currentContainer = removedEObject.eResource();
            }

            if (currentContainer != null && currentContainer != notification.getNotifier() && currentContainer.eAdapters().contains(this)) {
                toRemove = false;
            }
        }

        if (toRemove) {
            removeAdapter(oldValue);
        }
    }
}
