/*******************************************************************************
 * Copyright (c) 2010 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.sequence.business.internal.ordering;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.diagram.sequence.SequenceDDiagram;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.ISequenceEvent;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.SequenceDiagram;
import org.eclipse.sirius.diagram.sequence.ordering.CompoundEventEnd;
import org.eclipse.sirius.diagram.sequence.ordering.EventEnd;
import org.eclipse.sirius.diagram.sequence.ordering.SingleEventEnd;


import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

/**
 * Helper class to factor common code for semantic and graphical orders
 * refreshing.
 * 
 * @author mporhel
 */
public final class EventEndHelper {

    /**
     * Function to find and returns the EventEnds corresponding to the given
     * part.
     */
    public static final Function<ISequenceEvent, List<EventEnd>> EVENT_ENDS = new Function<ISequenceEvent, List<EventEnd>>() {
        public List<EventEnd> apply(ISequenceEvent from) {
            return EventEndHelper.findEndsFromSemanticOrdering(from);
        }
    };

    /**
     * A function which compute the semantic end element from a event end.
     */
    public static final Function<EventEnd, EObject> SEMANTIC_END = new Function<EventEnd, EObject>() {
        public EObject apply(EventEnd from) {
            return from.getSemanticEnd();
        }
    };

    /**
     * A predicate which check that the given {@link SingleEventEnd} is a
     * starting event.
     */
    public static final Predicate<SingleEventEnd> IS_START = new Predicate<SingleEventEnd>() {
        public boolean apply(SingleEventEnd from) {
            return from.isStart();
        }
    };

    /**
     * A predicate which check that the given {@link EventEnd} is a punctual
     * compound event end.
     */
    public static final Predicate<EventEnd> PUNCTUAL_COMPOUND_EVENT_END = new Predicate<EventEnd>() {
        public boolean apply(EventEnd input) {
            return input instanceof CompoundEventEnd && EventEndHelper.getSemanticEvents(input).size() == 1;
        }
    };

    private EventEndHelper() {
        // Prevent instantiation.
    }

    /**
     * Helper to get semanticEvents from an event end.
     * 
     * @param eventEnd
     *            an EventEnd representing the end of an event.
     * @return a list of semantic element representing the event itself.
     */
    public static List<EObject> getSemanticEvents(EventEnd eventEnd) {
        List<EObject> result = new ArrayList<>();
        if (eventEnd instanceof SingleEventEnd) {
            result.add(((SingleEventEnd) eventEnd).getSemanticEvent());
        } else if (eventEnd instanceof CompoundEventEnd) {
            result.addAll(((CompoundEventEnd) eventEnd).getSemanticEvents());
        }
        return result;
    }

    /**
     * Helper to get the correct SingleEventEnd from an event end.
     * 
     * @param eventEnd
     *            an EventEnd representing the end of an event.
     * @param semanticEvent
     *            the wanted semantic event.
     * @return a list of semantic element representing the event itself.
     */
    public static SingleEventEnd getSingleEventEnd(EventEnd eventEnd, EObject semanticEvent) {
        SingleEventEnd result = null;
        if (eventEnd instanceof SingleEventEnd) {
            SingleEventEnd see = (SingleEventEnd) eventEnd;
            if (semanticEvent != null && semanticEvent.equals(see.getSemanticEvent())) {
                result = see;
            }
        } else if (eventEnd instanceof CompoundEventEnd) {
            CompoundEventEnd cee = (CompoundEventEnd) eventEnd;
            for (SingleEventEnd see : cee.getEventEnds()) {
                if (semanticEvent != null && semanticEvent.equals(see.getSemanticEvent())) {
                    result = see;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Finds and returns the EventEnds corresponding to the given part, using
     * the semantic ordering instead of the graphical ordering used by the plain
     * {@link #findEnds(ISequenceEventEditPart)}.
     * 
     * @param part
     *            the part to look for
     * @return the EventEnds corresponding to the given part
     */
    public static List<EventEnd> findEndsFromSemanticOrdering(ISequenceEvent part) {
        List<EventEnd> ends = new ArrayList<>();
        SequenceDiagram sdep = part.getDiagram();
        SequenceDDiagram seqDiag = (SequenceDDiagram) sdep.getNotationDiagram().getElement();
        java.util.Optional<EObject> semanticEvent = part.getSemanticTargetElement();
        for (EventEnd ee : seqDiag.getSemanticOrdering().getEventEnds()) {
            if (semanticEvent.isPresent() && EventEndHelper.getSemanticEvents(ee).contains(semanticEvent.get())) {
                ends.add(ee);
            }
        }
        return ends;
    }

    /**
     * Filter the given list : remove the events linked by compound events.
     * 
     * @param self
     *            the parent event part.
     * @param childrenEvents
     *            event to filter.
     * @return a filtered Iterable.
     * 
     */
    public static Iterable<ISequenceEvent> getIndependantEvents(ISequenceEvent self, Collection<ISequenceEvent> childrenEvents) {
        final List<EventEnd> parentEnds = EventEndHelper.findEndsFromSemanticOrdering(self);
        Predicate<ISequenceEvent> isValidSubEvent = new Predicate<ISequenceEvent>() {
            public boolean apply(ISequenceEvent input) {
                List<EventEnd> inputEnds = EventEndHelper.findEndsFromSemanticOrdering(input);
                boolean res = inputEnds.removeAll(parentEnds);
                return !res;
            }
        };
        return Iterables.filter(childrenEvents, isValidSubEvent);
    }

    /**
     * Returns the list of direct compound-events of this given event, in
     * chronological (and thus also graphical) order. This includes events with
     * same semantic ends
     * 
     * @param self
     *            The given {@link ISequenceEventEditPart}.
     * 
     * @return the list of direct compound-events of this event, in
     *         chronological order.
     */
    public static List<ISequenceEvent> getCompoundEvents(ISequenceEvent self) {
        List<ISequenceEvent> compoundEvents = new ArrayList<>();
        SequenceDiagram sdep = self.getDiagram();
        EObject semanticEvent = self.getSemanticTargetElement().get();
        List<EventEnd> ends = EventEndHelper.findEndsFromSemanticOrdering(self);
        for (CompoundEventEnd cee : Iterables.filter(ends, CompoundEventEnd.class)) {
            for (SingleEventEnd see : cee.getEventEnds()) {
                if (see.getSemanticEvent() != semanticEvent) {
                    ISequenceEvent ise = EventEndHelper.findISequenceEvent(see, sdep);
                    // if (!isMessageToSelf(ise)) {
                    if (ise != null) {
                        compoundEvents.add(ise);
                    }
                }
            }
        }
        return compoundEvents;
    }

    /**
     * Finds and returns the ISequenceEvent corresponding to the given
     * SingleEventEnd.
     * 
     * @param end
     *            the end to look for
     * @param sdep
     *            the SequenceDiagramEditPart to inspect
     * @return the ISequenceEvent corresponding to the given part
     */
    public static ISequenceEvent findISequenceEvent(SingleEventEnd end, SequenceDiagram sdep) {
        for (ISequenceEvent ise : sdep.getAllDelimitedSequenceEvents()) {
            java.util.Optional<EObject> semanticEvent = ise.getSemanticTargetElement();
            if (semanticEvent.isPresent() && end.getSemanticEvent().equals(semanticEvent.get())) {
                return ise;
            }
        }
        return null;
    }
}
