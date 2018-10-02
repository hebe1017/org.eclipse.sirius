/**
 * Copyright (c) 2007, 2016 THALES GLOBAL SERVICES and others.
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
package org.eclipse.sirius.diagram.description.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IChildCreationExtender;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.sirius.diagram.description.DescriptionFactory;
import org.eclipse.sirius.diagram.description.DiagramDescription;
import org.eclipse.sirius.diagram.description.Layer;
import org.eclipse.sirius.diagram.description.util.DescriptionAdapterFactory;
import org.eclipse.sirius.diagram.internal.description.provider.BooleanLayoutOptionItemProviderSpec;
import org.eclipse.sirius.diagram.internal.description.provider.CustomLayoutConfigurationItemProviderSpec;
import org.eclipse.sirius.diagram.internal.description.provider.DiagramDescriptionItemProviderSpec;
import org.eclipse.sirius.diagram.internal.description.provider.DoubleLayoutOptionItemProviderSpec;
import org.eclipse.sirius.diagram.internal.description.provider.EnumLayoutOptionItemProviderSpec;
import org.eclipse.sirius.diagram.internal.description.provider.EnumLayoutValueItemProviderSpec;
import org.eclipse.sirius.diagram.internal.description.provider.EnumSetLayoutOptionItemProviderSpec;
import org.eclipse.sirius.diagram.internal.description.provider.IntegerLayoutOptionItemProviderSpec;
import org.eclipse.sirius.diagram.internal.description.provider.StringLayoutOptionItemProviderSpec;
import org.eclipse.sirius.diagram.ui.provider.DiagramUIPlugin;
import org.eclipse.sirius.diagram.ui.provider.Messages;
import org.eclipse.sirius.viewpoint.description.DecorationDescriptionsSet;
import org.eclipse.sirius.viewpoint.description.DescriptionPackage;
import org.eclipse.sirius.viewpoint.description.Viewpoint;
import org.eclipse.sirius.viewpoint.description.util.DescriptionSwitch;

/**
 * This is the factory that is used to provide the interfaces needed to support Viewers. The adapters generated by this
 * factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged fireNotifyChanged}. The adapters
 * also support Eclipse property sheets. Note that most of the adapters are shared among multiple instances. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class DescriptionItemProviderAdapterFactory extends DescriptionAdapterFactory implements ComposeableAdapterFactory, IChangeNotifier, IDisposable {
    /**
     * This keeps track of the root adapter factory that delegates to this adapter factory. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected ComposedAdapterFactory parentAdapterFactory;

    /**
     * This is used to implement {@link org.eclipse.emf.edit.provider.IChangeNotifier}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected IChangeNotifier changeNotifier = new ChangeNotifier();

    /**
     * This keeps track of all the supported types checked by {@link #isFactoryForType isFactoryForType}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected Collection<Object> supportedTypes = new ArrayList<Object>();

    /**
     * This constructs an instance. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public DescriptionItemProviderAdapterFactory() {
        supportedTypes.add(IEditingDomainItemProvider.class);
        supportedTypes.add(IStructuredItemContentProvider.class);
        supportedTypes.add(ITreeItemContentProvider.class);
        supportedTypes.add(IItemLabelProvider.class);
        supportedTypes.add(IItemPropertySource.class);
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.diagram.description.DiagramDescription} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected DiagramDescriptionItemProvider diagramDescriptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.diagram.description.DiagramDescription}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @not-generated
     */
    @Override
    public Adapter createDiagramDescriptionAdapter() {
        if (diagramDescriptionItemProvider == null) {
            diagramDescriptionItemProvider = new DiagramDescriptionItemProviderSpec(this);
        }

        return diagramDescriptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.diagram.description.DiagramImportDescription} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected DiagramImportDescriptionItemProvider diagramImportDescriptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.diagram.description.DiagramImportDescription}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createDiagramImportDescriptionAdapter() {
        if (diagramImportDescriptionItemProvider == null) {
            diagramImportDescriptionItemProvider = new DiagramImportDescriptionItemProvider(this);
        }

        return diagramImportDescriptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.diagram.description.DiagramExtensionDescription} instances. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    protected DiagramExtensionDescriptionItemProvider diagramExtensionDescriptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.diagram.description.DiagramExtensionDescription}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createDiagramExtensionDescriptionAdapter() {
        if (diagramExtensionDescriptionItemProvider == null) {
            diagramExtensionDescriptionItemProvider = new DiagramExtensionDescriptionItemProvider(this);
        }

        return diagramExtensionDescriptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.diagram.description.NodeMapping}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected NodeMappingItemProvider nodeMappingItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.diagram.description.NodeMapping}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createNodeMappingAdapter() {
        if (nodeMappingItemProvider == null) {
            nodeMappingItemProvider = new NodeMappingItemProvider(this);
        }

        return nodeMappingItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.diagram.description.ContainerMapping}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ContainerMappingItemProvider containerMappingItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.diagram.description.ContainerMapping}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createContainerMappingAdapter() {
        if (containerMappingItemProvider == null) {
            containerMappingItemProvider = new ContainerMappingItemProvider(this);
        }

        return containerMappingItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.diagram.description.NodeMappingImport}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected NodeMappingImportItemProvider nodeMappingImportItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.diagram.description.NodeMappingImport}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createNodeMappingImportAdapter() {
        if (nodeMappingImportItemProvider == null) {
            nodeMappingImportItemProvider = new NodeMappingImportItemProvider(this);
        }

        return nodeMappingImportItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.diagram.description.ContainerMappingImport} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected ContainerMappingImportItemProvider containerMappingImportItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.diagram.description.ContainerMappingImport}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createContainerMappingImportAdapter() {
        if (containerMappingImportItemProvider == null) {
            containerMappingImportItemProvider = new ContainerMappingImportItemProvider(this);
        }

        return containerMappingImportItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.diagram.description.EdgeMapping}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected EdgeMappingItemProvider edgeMappingItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.diagram.description.EdgeMapping}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createEdgeMappingAdapter() {
        if (edgeMappingItemProvider == null) {
            edgeMappingItemProvider = new EdgeMappingItemProvider(this);
        }

        return edgeMappingItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.diagram.description.EdgeMappingImport}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected EdgeMappingImportItemProvider edgeMappingImportItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.diagram.description.EdgeMappingImport}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createEdgeMappingImportAdapter() {
        if (edgeMappingImportItemProvider == null) {
            edgeMappingImportItemProvider = new EdgeMappingImportItemProvider(this);
        }

        return edgeMappingImportItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.diagram.description.ConditionalNodeStyleDescription} instances. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    protected ConditionalNodeStyleDescriptionItemProvider conditionalNodeStyleDescriptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.diagram.description.ConditionalNodeStyleDescription}.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createConditionalNodeStyleDescriptionAdapter() {
        if (conditionalNodeStyleDescriptionItemProvider == null) {
            conditionalNodeStyleDescriptionItemProvider = new ConditionalNodeStyleDescriptionItemProvider(this);
        }

        return conditionalNodeStyleDescriptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.diagram.description.ConditionalEdgeStyleDescription} instances. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    protected ConditionalEdgeStyleDescriptionItemProvider conditionalEdgeStyleDescriptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.diagram.description.ConditionalEdgeStyleDescription}.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createConditionalEdgeStyleDescriptionAdapter() {
        if (conditionalEdgeStyleDescriptionItemProvider == null) {
            conditionalEdgeStyleDescriptionItemProvider = new ConditionalEdgeStyleDescriptionItemProvider(this);
        }

        return conditionalEdgeStyleDescriptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.diagram.description.ConditionalContainerStyleDescription} instances. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ConditionalContainerStyleDescriptionItemProvider conditionalContainerStyleDescriptionItemProvider;

    /**
     * This creates an adapter for a
     * {@link org.eclipse.sirius.diagram.description.ConditionalContainerStyleDescription}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createConditionalContainerStyleDescriptionAdapter() {
        if (conditionalContainerStyleDescriptionItemProvider == null) {
            conditionalContainerStyleDescriptionItemProvider = new ConditionalContainerStyleDescriptionItemProvider(this);
        }

        return conditionalContainerStyleDescriptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.diagram.description.OrderedTreeLayout}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected OrderedTreeLayoutItemProvider orderedTreeLayoutItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.diagram.description.OrderedTreeLayout}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createOrderedTreeLayoutAdapter() {
        if (orderedTreeLayoutItemProvider == null) {
            orderedTreeLayoutItemProvider = new OrderedTreeLayoutItemProvider(this);
        }

        return orderedTreeLayoutItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.diagram.description.CompositeLayout}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected CompositeLayoutItemProvider compositeLayoutItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.diagram.description.CompositeLayout}. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createCompositeLayoutAdapter() {
        if (compositeLayoutItemProvider == null) {
            compositeLayoutItemProvider = new CompositeLayoutItemProvider(this);
        }

        return compositeLayoutItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.diagram.description.CustomLayoutConfiguration} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected CustomLayoutConfigurationItemProvider customLayoutConfigurationItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.diagram.description.CustomLayoutConfiguration}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createCustomLayoutConfigurationAdapter() {
        if (customLayoutConfigurationItemProvider == null) {
            customLayoutConfigurationItemProvider = new CustomLayoutConfigurationItemProviderSpec(this);
        }

        return customLayoutConfigurationItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.diagram.description.BooleanLayoutOption} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected BooleanLayoutOptionItemProvider booleanLayoutOptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.diagram.description.BooleanLayoutOption}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createBooleanLayoutOptionAdapter() {
        if (booleanLayoutOptionItemProvider == null) {
            booleanLayoutOptionItemProvider = new BooleanLayoutOptionItemProviderSpec(this);
        }

        return booleanLayoutOptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.diagram.description.StringLayoutOption} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected StringLayoutOptionItemProvider stringLayoutOptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.diagram.description.StringLayoutOption}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createStringLayoutOptionAdapter() {
        if (stringLayoutOptionItemProvider == null) {
            stringLayoutOptionItemProvider = new StringLayoutOptionItemProviderSpec(this);
        }

        return stringLayoutOptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.diagram.description.IntegerLayoutOption} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected IntegerLayoutOptionItemProvider integerLayoutOptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.diagram.description.IntegerLayoutOption}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createIntegerLayoutOptionAdapter() {
        if (integerLayoutOptionItemProvider == null) {
            integerLayoutOptionItemProvider = new IntegerLayoutOptionItemProviderSpec(this);
        }

        return integerLayoutOptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.diagram.description.DoubleLayoutOption} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected DoubleLayoutOptionItemProvider doubleLayoutOptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.diagram.description.DoubleLayoutOption}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createDoubleLayoutOptionAdapter() {
        if (doubleLayoutOptionItemProvider == null) {
            doubleLayoutOptionItemProvider = new DoubleLayoutOptionItemProviderSpec(this);
        }

        return doubleLayoutOptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.diagram.description.EnumLayoutOption}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected EnumLayoutOptionItemProvider enumLayoutOptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.diagram.description.EnumLayoutOption}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createEnumLayoutOptionAdapter() {
        if (enumLayoutOptionItemProvider == null) {
            enumLayoutOptionItemProvider = new EnumLayoutOptionItemProviderSpec(this);
        }

        return enumLayoutOptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.diagram.description.EnumSetLayoutOption} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected EnumSetLayoutOptionItemProvider enumSetLayoutOptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.diagram.description.EnumSetLayoutOption}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createEnumSetLayoutOptionAdapter() {
        if (enumSetLayoutOptionItemProvider == null) {
            enumSetLayoutOptionItemProvider = new EnumSetLayoutOptionItemProviderSpec(this);
        }

        return enumSetLayoutOptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.diagram.description.EnumLayoutValue}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected EnumLayoutValueItemProvider enumLayoutValueItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.diagram.description.EnumLayoutValue}. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createEnumLayoutValueAdapter() {
        if (enumLayoutValueItemProvider == null) {
            enumLayoutValueItemProvider = new EnumLayoutValueItemProviderSpec(this);
        }

        return enumLayoutValueItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.diagram.description.MappingBasedDecoration} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected MappingBasedDecorationItemProvider mappingBasedDecorationItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.diagram.description.MappingBasedDecoration}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createMappingBasedDecorationAdapter() {
        if (mappingBasedDecorationItemProvider == null) {
            mappingBasedDecorationItemProvider = new MappingBasedDecorationItemProvider(this);
        }

        return mappingBasedDecorationItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.diagram.description.Layer} instances.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected LayerItemProvider layerItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.diagram.description.Layer}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createLayerAdapter() {
        if (layerItemProvider == null) {
            layerItemProvider = new LayerItemProvider(this);
        }

        return layerItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.diagram.description.AdditionalLayer}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected AdditionalLayerItemProvider additionalLayerItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.diagram.description.AdditionalLayer}. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createAdditionalLayerAdapter() {
        if (additionalLayerItemProvider == null) {
            additionalLayerItemProvider = new AdditionalLayerItemProvider(this);
        }

        return additionalLayerItemProvider;
    }

    /**
     * This returns the root adapter factory that contains this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ComposeableAdapterFactory getRootAdapterFactory() {
        return parentAdapterFactory == null ? this : parentAdapterFactory.getRootAdapterFactory();
    }

    /**
     * This sets the composed adapter factory that contains this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory) {
        this.parentAdapterFactory = parentAdapterFactory;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isFactoryForType(Object type) {
        return supportedTypes.contains(type) || super.isFactoryForType(type);
    }

    /**
     * This implementation substitutes the factory itself as the key for the adapter. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter adapt(Notifier notifier, Object type) {
        return super.adapt(notifier, this);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object adapt(Object object, Object type) {
        if (isFactoryForType(type)) {
            Object adapter = super.adapt(object, type);
            if (!(type instanceof Class<?>) || (((Class<?>) type).isInstance(adapter))) {
                return adapter;
            }
        }

        return null;
    }

    /**
     * This adds a listener. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void addListener(INotifyChangedListener notifyChangedListener) {
        changeNotifier.addListener(notifyChangedListener);
    }

    /**
     * This removes a listener. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void removeListener(INotifyChangedListener notifyChangedListener) {
        changeNotifier.removeListener(notifyChangedListener);
    }

    /**
     * This delegates to {@link #changeNotifier} and to {@link #parentAdapterFactory}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    @Override
    public void fireNotifyChanged(Notification notification) {
        changeNotifier.fireNotifyChanged(notification);

        if (parentAdapterFactory != null) {
            parentAdapterFactory.fireNotifyChanged(notification);
        }
    }

    /**
     * This disposes all of the item providers created by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void dispose() {
        if (diagramDescriptionItemProvider != null) {
            diagramDescriptionItemProvider.dispose();
        }
        if (diagramImportDescriptionItemProvider != null) {
            diagramImportDescriptionItemProvider.dispose();
        }
        if (diagramExtensionDescriptionItemProvider != null) {
            diagramExtensionDescriptionItemProvider.dispose();
        }
        if (nodeMappingItemProvider != null) {
            nodeMappingItemProvider.dispose();
        }
        if (containerMappingItemProvider != null) {
            containerMappingItemProvider.dispose();
        }
        if (nodeMappingImportItemProvider != null) {
            nodeMappingImportItemProvider.dispose();
        }
        if (containerMappingImportItemProvider != null) {
            containerMappingImportItemProvider.dispose();
        }
        if (edgeMappingItemProvider != null) {
            edgeMappingItemProvider.dispose();
        }
        if (edgeMappingImportItemProvider != null) {
            edgeMappingImportItemProvider.dispose();
        }
        if (conditionalNodeStyleDescriptionItemProvider != null) {
            conditionalNodeStyleDescriptionItemProvider.dispose();
        }
        if (conditionalEdgeStyleDescriptionItemProvider != null) {
            conditionalEdgeStyleDescriptionItemProvider.dispose();
        }
        if (conditionalContainerStyleDescriptionItemProvider != null) {
            conditionalContainerStyleDescriptionItemProvider.dispose();
        }
        if (orderedTreeLayoutItemProvider != null) {
            orderedTreeLayoutItemProvider.dispose();
        }
        if (compositeLayoutItemProvider != null) {
            compositeLayoutItemProvider.dispose();
        }
        if (customLayoutConfigurationItemProvider != null) {
            customLayoutConfigurationItemProvider.dispose();
        }
        if (booleanLayoutOptionItemProvider != null) {
            booleanLayoutOptionItemProvider.dispose();
        }
        if (stringLayoutOptionItemProvider != null) {
            stringLayoutOptionItemProvider.dispose();
        }
        if (integerLayoutOptionItemProvider != null) {
            integerLayoutOptionItemProvider.dispose();
        }
        if (doubleLayoutOptionItemProvider != null) {
            doubleLayoutOptionItemProvider.dispose();
        }
        if (enumLayoutOptionItemProvider != null) {
            enumLayoutOptionItemProvider.dispose();
        }
        if (enumSetLayoutOptionItemProvider != null) {
            enumSetLayoutOptionItemProvider.dispose();
        }
        if (enumLayoutValueItemProvider != null) {
            enumLayoutValueItemProvider.dispose();
        }
        if (mappingBasedDecorationItemProvider != null) {
            mappingBasedDecorationItemProvider.dispose();
        }
        if (layerItemProvider != null) {
            layerItemProvider.dispose();
        }
        if (additionalLayerItemProvider != null) {
            additionalLayerItemProvider.dispose();
        }
    }

    /**
     * A child creation extender for the {@link DescriptionPackage}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static class DescriptionChildCreationExtender implements IChildCreationExtender {
        /**
         * The switch for creating child descriptors specific to each extended class. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        protected static class CreationSwitch extends DescriptionSwitch<Object> {
            /**
             * The child descriptors being populated. <!-- begin-user-doc --> <!-- end-user-doc -->
             *
             * @generated
             */
            protected List<Object> newChildDescriptors;

            /**
             * The domain in which to create the children. <!-- begin-user-doc --> <!-- end-user-doc -->
             *
             * @generated
             */
            protected EditingDomain editingDomain;

            /**
             * Creates the a switch for populating child descriptors in the given domain. <!-- begin-user-doc --> <!--
             * end-user-doc -->
             *
             * @generated
             */
            CreationSwitch(List<Object> newChildDescriptors, EditingDomain editingDomain) {
                this.newChildDescriptors = newChildDescriptors;
                this.editingDomain = editingDomain;
            }

            /**
             * <!-- begin-user-doc --> <!-- end-user-doc -->
             *
             * @not-generated
             */
            @Override
            public Object caseViewpoint(Viewpoint object) {
                DiagramDescription diagramDescription = DescriptionFactory.eINSTANCE.createDiagramDescription();
                diagramDescription.setEnablePopupBars(true);
                Layer layer = DescriptionFactory.eINSTANCE.createLayer();
                layer.setName(Messages.DefaultLayerName);
                diagramDescription.setDefaultLayer(layer);
                newChildDescriptors.add(createChildParameter(DescriptionPackage.Literals.VIEWPOINT__OWNED_REPRESENTATIONS, diagramDescription));

                newChildDescriptors.add(createChildParameter(DescriptionPackage.Literals.VIEWPOINT__OWNED_REPRESENTATIONS, DescriptionFactory.eINSTANCE.createDiagramImportDescription()));

                newChildDescriptors.add(createChildParameter(DescriptionPackage.Literals.VIEWPOINT__OWNED_REPRESENTATION_EXTENSIONS, DescriptionFactory.eINSTANCE.createDiagramExtensionDescription()));

                return null;
            }

            /**
             * <!-- begin-user-doc --> <!-- end-user-doc -->
             *
             * @generated
             */
            public Object caseViewpointGen(Viewpoint object) {
                newChildDescriptors.add(createChildParameter(DescriptionPackage.Literals.VIEWPOINT__OWNED_REPRESENTATIONS, DescriptionFactory.eINSTANCE.createDiagramDescription()));

                newChildDescriptors.add(createChildParameter(DescriptionPackage.Literals.VIEWPOINT__OWNED_REPRESENTATIONS, DescriptionFactory.eINSTANCE.createDiagramImportDescription()));

                newChildDescriptors.add(createChildParameter(DescriptionPackage.Literals.VIEWPOINT__OWNED_REPRESENTATION_EXTENSIONS, DescriptionFactory.eINSTANCE.createDiagramExtensionDescription()));

                return null;
            }

            /**
             * <!-- begin-user-doc --> <!-- end-user-doc -->
             *
             * @generated
             */
            @Override
            public Object caseDecorationDescriptionsSet(DecorationDescriptionsSet object) {
                newChildDescriptors
                        .add(createChildParameter(DescriptionPackage.Literals.DECORATION_DESCRIPTIONS_SET__DECORATION_DESCRIPTIONS, DescriptionFactory.eINSTANCE.createMappingBasedDecoration()));

                return null;
            }

            /**
             * <!-- begin-user-doc --> <!-- end-user-doc -->
             *
             * @generated
             */
            protected CommandParameter createChildParameter(Object feature, Object child) {
                return new CommandParameter(null, feature, child);
            }

        }

        /**
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        @Override
        public Collection<Object> getNewChildDescriptors(Object object, EditingDomain editingDomain) {
            ArrayList<Object> result = new ArrayList<Object>();
            new CreationSwitch(result, editingDomain).doSwitch((EObject) object);
            return result;
        }

        /**
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        @Override
        public ResourceLocator getResourceLocator() {
            return DiagramUIPlugin.INSTANCE;
        }
    }

}
