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
import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.EdgeTarget;
import org.eclipse.sirius.diagram.sequence.Messages;



import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
/**
 * Represents the LostMEssageEnd marker which can appear at at the end of a
 * message. This element can be present if a message do not have a starting end
 * or a finishing end.
 * 
 * @author mporhel
 */
public class LostMessageEnd extends AbstractSequenceNode {
    /**
     * The visual ID. Same as a standard node.
     * 
     * see org.eclipse.sirius.diagram.internal.edit.parts.DNodeEditPart.
     * VISUAL_ID
     */
    public static final int VISUAL_ID = 2001;

    /**
     * Predicate to check whether a Sirius DDiagramElement represents an Lost
     * Message End.
     */
    private enum SiriusElementPredicate implements Predicate<DDiagramElement> {
        INSTANCE;

        @Override
        public boolean apply(DDiagramElement input) {
            boolean result = AbstractSequenceElement.isSequenceDiagramElement(input, org.eclipse.sirius.diagram.description.DescriptionPackage.eINSTANCE.getNodeMapping());
            if (input instanceof EdgeTarget) {
                EdgeTarget et = (EdgeTarget) input;
                result = result && Iterables.any(Iterables.concat(et.getIncomingEdges(), et.getOutgoingEdges()), Message.viewpointElementPredicate());
            }

            List<Predicate<DDiagramElement>> potentialMessageTarget = new ArrayList<>();
            potentialMessageTarget.add(EndOfLife.viewpointElementPredicate());
            potentialMessageTarget.add(AbstractNodeEvent.viewpointElementPredicate());
            potentialMessageTarget.add(Lifeline.viewpointElementPredicate());
            potentialMessageTarget.add(InstanceRole.viewpointElementPredicate());

            result = result && !Predicates.or(potentialMessageTarget).apply(input);

            return result;
        }
    }

    /**
     * Constructor.
     * 
     * @param node
     *            the GMF Node which represents this Lost Message End.
     */
    LostMessageEnd(Node node) {
        super(node);
        Preconditions.checkArgument(LostMessageEnd.notationPredicate().apply(node), Messages.LostMessage_nonLostMessageEndNode);
    }

    /**
     * Returns a predicate to check whether a GMF View represents an Lost
     * Message End.
     * 
     * @return a predicate to check whether a GMF View represents an Lost
     *         Message End.
     */
    public static Predicate<View> notationPredicate() {
        return new NotationPredicate(NotationPackage.eINSTANCE.getNode(), VISUAL_ID, LostMessageEnd.viewpointElementPredicate());
    }

    /**
     * Returns a predicate to check whether a Sirius DDiagramElement represents
     * an EndOfLife.
     * 
     * @return a predicate to check whether a Sirius DDiagramElement represents
     *         an EndOfLife.
     */
    public static Predicate<DDiagramElement> viewpointElementPredicate() {
        return SiriusElementPredicate.INSTANCE;
    }

    /**
     * Returns the message which references this lost end.
     * 
     * @return the message which references this lost end.
     */
    public java.util.Optional<Message> getMessage() {
        Message msg = null;
        Node node = getNotationNode();
        Iterable<Edge> srcEdges = Iterables.filter(node.getSourceEdges(), Edge.class);
        Iterable<Edge> tgtEdges = Iterables.filter(node.getTargetEdges(), Edge.class);
        for (Edge edge : Iterables.concat(srcEdges, tgtEdges)) {
            java.util.Optional<Message> message = ISequenceElementAccessor.getMessage(edge);
            if (message.isPresent()) {
                msg = message.get();
                break;
            }
        }

        return java.util.Optional.of(msg);
    }

    @Override
    public java.util.Optional<Lifeline> getLifeline() {
        return java.util.Optional.empty();
    }

    @Override
    public Rectangle getProperLogicalBounds() {
        if (getNotationNode().getLayoutConstraint() instanceof Bounds) {
            Bounds bounds = (Bounds) getNotationNode().getLayoutConstraint();
            return new Rectangle(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
        } else {
            throw new RuntimeException();
        }
    }
}
