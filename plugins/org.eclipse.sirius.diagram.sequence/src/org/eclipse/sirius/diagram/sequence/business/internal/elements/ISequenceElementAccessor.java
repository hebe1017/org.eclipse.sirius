/*******************************************************************************
 * Copyright (c) 2010, 2012 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.sequence.business.internal.elements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DSemanticDiagram;
import org.eclipse.sirius.diagram.sequence.SequenceDDiagram;


import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.sirius.viewpoint.ViewpointPackage;

import com.google.common.collect.Iterators;

/**
 * Accessor to get the sequence element corresponding to a notation one.
 * 
 * @author mporhel
 * 
 */
public final class ISequenceElementAccessor {
    /**
     * Avoid instanciation
     */
    private ISequenceElementAccessor() {

    }

    /**
     * TODO Comment.
     * 
     * @param notationView
     *            .
     * @return .
     */
    public static java.util.Optional<ISequenceElement> getISequenceElement(View notationView) {
        return ISequenceElementAccessor.getOrCreate(notationView, ISequenceElement.class);
    }

    /**
     * Tell if the specified GMF {@link View} represents a sequence element or a
     * part of it.
     * 
     * @param notationView
     *            the specified GMF view
     * @return true if the specified GMF view is a sequence element or a part of
     *         it, false otherwise
     */
    public static boolean isPartOfSequenceElement(View notationView) {
        boolean isPartOfSequenceElement = false;
        View view = notationView;
        if (view.eContainer() instanceof View && (CombinedFragment.compartmentNotationPredicate().apply(view) || Operand.compartmentNotationPredicate().apply(view))) {
            view = (View) view.eContainer();
        }
        java.util.Optional<ISequenceElement> sequenceElementOption = ISequenceElementAccessor.getISequenceElement(view);
        isPartOfSequenceElement = sequenceElementOption.isPresent();
        return isPartOfSequenceElement;
    }

    /**
     * TODO Comment.
     * 
     * @param diagramView
     *            .
     * @return .
     */
    public static java.util.Optional<SequenceDiagram> getSequenceDiagram(Diagram diagramView) {
        return ISequenceElementAccessor.getOrCreate(diagramView, SequenceDiagram.class);
    }

    /**
     * TODO COmment.
     * 
     * @param view
     *            .
     * @return .
     */
    public static java.util.Optional<ISequenceEvent> getISequenceEvent(View view) {
        return ISequenceElementAccessor.getOrCreate(view, ISequenceEvent.class);
    }

    /**
     * .
     * 
     * @param view
     *            .
     * @return .
     */
    public static java.util.Optional<ISequenceNode> getISequenceNode(View view) {
        return ISequenceElementAccessor.getOrCreate(view, ISequenceNode.class);
    }

    /**
     * .
     * 
     * @param view
     *            .
     * @return .
     */
    public static java.util.Optional<InstanceRole> getInstanceRole(View view) {
        return ISequenceElementAccessor.getOrCreate(view, InstanceRole.class);
    }

    /**
     * .
     * 
     * @param view
     *            .
     * @return .
     */
    public static java.util.Optional<Lifeline> getLifeline(View view) {
        return ISequenceElementAccessor.getOrCreate(view, Lifeline.class);
    }

    /**
     * .
     * 
     * @param view
     *            .
     * @return .
     */
    public static java.util.Optional<EndOfLife> getEndOfLife(View view) {
        return ISequenceElementAccessor.getOrCreate(view, EndOfLife.class);
    }

    /**
     * .
     * 
     * @param view
     *            .
     * @return .
     */
    public static java.util.Optional<AbstractNodeEvent> getAbstractNodeEvent(View view) {
        return ISequenceElementAccessor.getOrCreate(view, AbstractNodeEvent.class);
    }

    /**
     * .
     * 
     * @param view
     *            .
     * @return .
     */
    public static java.util.Optional<Execution> getExecution(View view) {
        return ISequenceElementAccessor.getOrCreate(view, Execution.class);
    }

    /**
     * .
     * 
     * @param view
     *            .
     * @return .
     */
    public static java.util.Optional<State> getState(View view) {
        return ISequenceElementAccessor.getOrCreate(view, State.class);
    }

    /**
     * .
     * 
     * @param view
     *            .
     * @return .
     */
    public static java.util.Optional<Message> getMessage(View view) {
        return ISequenceElementAccessor.getOrCreate(view, Message.class);
    }

    /**
     * .
     * 
     * @param view
     *            .
     * @return .
     */
    public static java.util.Optional<InteractionUse> getInteractionUse(View view) {
        return ISequenceElementAccessor.getOrCreate(view, InteractionUse.class);
    }

    /**
     * .
     * 
     * @param view
     *            .
     * @return .
     */
    public static java.util.Optional<CombinedFragment> getCombinedFragment(View view) {
        return ISequenceElementAccessor.getOrCreate(view, CombinedFragment.class);
    }

    /**
     * .
     * 
     * @param view
     *            .
     * @return .
     */
    public static java.util.Optional<Operand> getOperand(View view) {
        return ISequenceElementAccessor.getOrCreate(view, Operand.class);
    }

    /**
     * .
     * 
     * @param view
     *            .
     * @return .
     */
    public static java.util.Optional<LostMessageEnd> getLostMessageEnd(View view) {
        return ISequenceElementAccessor.getOrCreate(view, LostMessageEnd.class);
    }

    /**
     * .
     * 
     * @param view
     *            .
     * @return .
     */
    public static java.util.Optional<ObservationPoint> getObservationPoint(View view) {
        return ISequenceElementAccessor.getOrCreate(view, ObservationPoint.class);
    }

    /**
     * Get the existing {@link ISequenceElement} corresponding to the given View
     * or create it.
     * 
     * @param notationView
     *            the notation view.
     * @return existing or new {@link ISequenceElement}.
     */
    private static <T extends ISequenceElement> java.util.Optional<T> getOrCreate(View notationView, Class<T> expectedType) {
        T ise = null;

        if (notationView != null) {
            Iterator<ISequenceElement> it = Iterators.filter(notationView.eAdapters().iterator(), ISequenceElement.class);
            if (it.hasNext()) {
                ISequenceElement seqElt = it.next();
                if (expectedType.isInstance(seqElt)) {
                    ise = expectedType.cast(seqElt);
                }
            } else {
                ISequenceElement element = ISequenceElementAccessor.createSequenceElement(notationView);
                if (element != null && expectedType.isInstance(element)) {
                    ise = expectedType.cast(element);
                }
            }
        }
        return java.util.Optional.of(ise);
    }

    private static ISequenceElement createSequenceElement(View notationView) {
        ISequenceElement created = null;
        if (SequenceDiagram.notationPredicate().apply(notationView)) {
            created = new SequenceDiagram((Diagram) notationView);
        } else if (InstanceRole.notationPredicate().apply(notationView)) {
            created = new InstanceRole((Node) notationView);
        } else if (Lifeline.notationPredicate().apply(notationView)) {
            created = new Lifeline((Node) notationView);
        } else if (EndOfLife.notationPredicate().apply(notationView)) {
            created = new EndOfLife((Node) notationView);
        } else if (Execution.notationPredicate().apply(notationView)) {
            created = new Execution((Node) notationView);
        } else if (State.notationPredicate().apply(notationView)) {
            created = new State((Node) notationView);
        } else if (Message.notationPredicate().apply(notationView)) {
            created = new Message((Edge) notationView);
        } else if (CombinedFragment.notationPredicate().apply(notationView)) {
            created = new CombinedFragment((Node) notationView);
        } else if (Operand.notationPredicate().apply(notationView)) {
            created = new Operand((Node) notationView);
        } else if (InteractionUse.notationPredicate().apply(notationView)) {
            created = new InteractionUse((Node) notationView);
        } else if (LostMessageEnd.notationPredicate().apply(notationView)) {
            created = new LostMessageEnd((Node) notationView);
        } else if (ObservationPoint.notationPredicate().apply(notationView)) {
            created = new ObservationPoint((Node) notationView);
        }

        if (created != null) {
            notationView.eAdapters().add(created);
        }
        return created;
    }

    /**
     * Finds all the events in the specified diagram which have a specific
     * semantic target.
     * 
     * @param diagram
     *            the sequence diagram.
     * @param semanticElement
     *            the semantic element.
     * @return all the events in the diagram which have the specified semantic
     *         element as target.
     */
    public static Collection<ISequenceEvent> getEventsForSemanticElement(SequenceDiagram diagram, EObject semanticElement) {
        ECrossReferenceAdapter xref = ISequenceElementAccessor.getCrossReferencer(semanticElement);
        if (xref == null) {
            return Collections.emptySet();
        } else {
            Collection<ISequenceEvent> result = new ArrayList<>();
            for (Setting setting : xref.getInverseReferences(semanticElement)) {
                if (ISequenceElementAccessor.isDiagramElementTargetReference(setting)) {
                    DDiagramElement dde = (DDiagramElement) setting.getEObject();
                    java.util.Optional<View> optView = ISequenceElementAccessor.getGMFView(dde, xref);
                    if (optView.isPresent()) {
                        java.util.Optional<ISequenceEvent> elt = ISequenceElementAccessor.getISequenceEvent(optView.get());
                        if (elt.isPresent() && diagram.equals(elt.get().getDiagram())) {
                            result.add(elt.get());
                        }
                    }
                }
            }
            return result;
        }
    }

    /**
     * Finds all the events in the specified diagram which have a specific
     * semantic target.
     * 
     * @param diagram
     *            the sequence diagram.
     * @param semanticElement
     *            the semantic element.
     * @return all the events in the diagram which have the specified semantic
     *         element as target.
     */
    public static Collection<View> getViewsForSemanticElement(SequenceDDiagram diagram, EObject semanticElement) {
        ECrossReferenceAdapter xref = ISequenceElementAccessor.getCrossReferencer(semanticElement);
        if (xref == null || diagram == null) {
            return Collections.emptySet();
        } else {
            Collection<View> result = new ArrayList<>();
            for (Setting setting : xref.getInverseReferences(semanticElement)) {
                if (ISequenceElementAccessor.isDiagramElementTargetReference(setting)) {
                    DDiagramElement dde = (DDiagramElement) setting.getEObject();
                    java.util.Optional<View> optView = ISequenceElementAccessor.getGMFView(dde, xref);
                    if (optView.isPresent() && diagram.equals(dde.getParentDiagram())) {
                        result.add(optView.get());
                    }
                } else if (ISequenceElementAccessor.isDiagramTargetReference(setting)) {
                    DSemanticDiagram foundDiag = (DSemanticDiagram) setting.getEObject();
                    java.util.Optional<View> optView = ISequenceElementAccessor.getGMFView(foundDiag, xref);
                    if (optView.isPresent() && diagram.equals(foundDiag)) {
                        result.add(optView.get());
                    }
                }
            }
            return result;
        }
    }

    /**
     * Finds all the events in the specified diagram which have a specific
     * semantic target.
     * 
     * @param diagram
     *            the sequence diagram.
     * @param semanticElement
     *            the semantic element.
     * @return all the events in the diagram which have the specified semantic
     *         element as target.
     */
    public static Collection<DDiagramElement> getDiagramElementsForSemanticElement(SequenceDiagram diagram, EObject semanticElement) {
        ECrossReferenceAdapter xref = ISequenceElementAccessor.getCrossReferencer(semanticElement);
        if (xref == null) {
            return Collections.emptySet();
        } else {
            Collection<DDiagramElement> result = new ArrayList<>();
            for (Setting setting : xref.getInverseReferences(semanticElement)) {
                if (ISequenceElementAccessor.isDiagramElementTargetReference(setting)) {
                    result.add((DDiagramElement) setting.getEObject());
                }
            }
            return result;
        }
    }

    private static java.util.Optional<View> getGMFView(DSemanticDecorator dSem, ECrossReferenceAdapter xref) {
        for (Setting setting : xref.getInverseReferences(dSem)) {
            if (ISequenceElementAccessor.isViewElementReference(setting)) {
                EObject view = setting.getEObject();
                if (view instanceof View && ((View) view).getDiagram() != null) {
                    return java.util.Optional.of((View) view);
                }
            }
        }
        return java.util.Optional.empty();
    }

    private static boolean isViewElementReference(Setting setting) {
        EReference elementRef = NotationPackage.eINSTANCE.getView_Element();
        return setting.getEObject() instanceof View && setting.getEStructuralFeature().equals(elementRef);
    }

    private static boolean isDiagramElementTargetReference(Setting setting) {
        EReference targetReference = ViewpointPackage.eINSTANCE.getDSemanticDecorator_Target();
        return setting.getEObject() instanceof DDiagramElement && setting.getEStructuralFeature().equals(targetReference);
    }

    private static boolean isDiagramTargetReference(Setting setting) {
        EReference targetReference = ViewpointPackage.eINSTANCE.getDSemanticDecorator_Target();
        return setting.getEObject() instanceof DDiagram && setting.getEStructuralFeature().equals(targetReference);
    }

    private static ECrossReferenceAdapter getCrossReferencer(EObject semanticElement) {
        Session session = SessionManager.INSTANCE.getSession(semanticElement);
        if (session != null) {
            return session.getSemanticCrossReferencer();
        } else {
            return null;
        }
    }

}
