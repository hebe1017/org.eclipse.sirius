/*******************************************************************************
 * Copyright (c) 2010, 2017 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.tests.swtbot.sequence;

import static org.junit.Assert.assertNotEquals;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.sirius.diagram.sequence.business.internal.layout.LayoutConstants;
import org.eclipse.sirius.diagram.sequence.ui.tool.internal.edit.part.CombinedFragmentEditPart;
import org.eclipse.sirius.diagram.sequence.ui.tool.internal.edit.part.ExecutionEditPart;
import org.eclipse.sirius.diagram.sequence.ui.tool.internal.edit.part.InteractionUseEditPart;
import org.eclipse.sirius.diagram.sequence.ui.tool.internal.edit.part.OperandEditPart;

import org.eclipse.sirius.tests.swtbot.sequence.condition.CheckMessageEditPartIsDisplayed;
import org.eclipse.sirius.tests.swtbot.sequence.condition.CheckNumberOfDescendants;
import org.eclipse.sirius.tests.swtbot.sequence.condition.CheckResize;
import org.eclipse.sirius.tests.swtbot.support.api.condition.CheckEditPartResized;
import org.eclipse.sirius.tests.swtbot.support.api.condition.CheckSelectedCondition;
import org.eclipse.sirius.tests.swtbot.support.api.condition.CheckToolIsActivated;
import org.eclipse.sirius.tests.swtbot.support.api.condition.OperationDoneCondition;
import org.eclipse.sirius.tests.unit.diagram.sequence.InteractionsConstants;
import org.eclipse.swtbot.eclipse.gef.finder.matchers.IsInstanceOf;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.TimeoutException;
import org.junit.Assert;

/**
 * Tests only zoom and creation with single/double click, others features to test are Junit Plugin Tests.
 * 
 * @author edugueperoux, smonnier
 */
public class CombinedFragmentsOperandOverlapTests extends AbstractCombinedFragmentSequenceTests {

    /**
     * VP-1338 Validate that moving an execution, on lifeline, will not result to have its source message other end
     * overlaping an Interaction Use.
     */
    public void testMoveExecutionOnLifelineCanNotHaveItsSourceMessageOverlapingIU() {
        // Remove the first CF to have space
        editor.select(firstCombinedFragmentBot);
        deleteSelectedElement();

        // create an IU on lifeline A
        Point iuSourceLocation = firstCombinedFragmentBounds.getTopLeft().getCopy();
        Point iuTargetLocation = firstCombinedFragmentBounds.getTop().getCopy().getTranslated(0, 10);
        SWTBotGefEditPart iuBot = createInteractionUseWithResult(iuSourceLocation, iuTargetLocation);
        bot.waitUntil(new CheckNumberOfDescendants(sequenceDiagramBot, InteractionUseEditPart.class, 1));
        Rectangle iuBounds = editor.getBounds(iuBot);

        // add a message from e1
        Point messageSourceLocation = e1Bounds.getTop().getCopy().getTranslated(0, 10);
        Point messageTargetLocation = new Point(instanceRoleEditPartABounds.getCenter().x, e1Bounds.getTop().getCopy().getTranslated(0, 10).y);
        ICondition done = new OperationDoneCondition();
        createMessage(InteractionsConstants.READ_TOOL_ID, messageSourceLocation, messageTargetLocation);
        bot.waitUntil(done);

        editor.select(editor.mainEditPart());

        // Move e1 to have its source message on range of IU
        CheckSelectedCondition checkSelected = new CheckSelectedCondition(editor, e1EditPart);
        editor.drag(e1Bounds.getTop(), iuBounds.getCenter());
        bot.waitUntil(checkSelected);

        // Validate that the move is forbidden
        Assert.assertEquals(e1Bounds, editor.getBounds(e1Bot));
        // Undo create message
        undo();
        // Undo create IU
        undo();
        // Undo delete
        undo();
        assertNotChange();
    }

    /**
     * VP-1338 Validate that moving an execution, on lifeline, will not result to have its target message other end
     * overlaping an Interaction Use.
     */
    public void testMoveExecutionOnLifelineCanNotHaveItsTargetMessageOverlapingIU() {
        // Remove the first CF to have space
        editor.select(firstCombinedFragmentBot);
        deleteSelectedElement();

        // create an IU on lifeline A
        Point iuSourceLocation = firstCombinedFragmentBounds.getTopLeft().getCopy();
        Point iuTargetLocation = firstCombinedFragmentBounds.getTop().getCopy().getTranslated(0, 10);
        SWTBotGefEditPart iuBot = createInteractionUseWithResult(iuSourceLocation, iuTargetLocation);
        bot.waitUntil(new CheckNumberOfDescendants(sequenceDiagramBot, InteractionUseEditPart.class, 1));
        Rectangle iuBounds = editor.getBounds(iuBot);

        // add a message to e1
        Point messageSourceLocation = new Point(instanceRoleEditPartABounds.getCenter().x, e1Bounds.getTop().getCopy().getTranslated(0, 10).y);
        Point messageTargetLocation = e1Bounds.getTop().getCopy().getTranslated(0, 10);
        ICondition done = new OperationDoneCondition();
        createMessage(InteractionsConstants.READ_TOOL_ID, messageSourceLocation, messageTargetLocation);
        bot.waitUntil(done);

        editor.select(editor.mainEditPart());

        // Move e1 to have its source message on range of IU
        CheckSelectedCondition checkSelected = new CheckSelectedCondition(editor, e1EditPart);
        editor.drag(e1Bounds.getTop(), iuBounds.getCenter());
        bot.waitUntil(checkSelected);

        // Validate that the move is forbidden
        Assert.assertEquals(e1Bounds, editor.getBounds(e1Bot));

        // Validate that the move is forbidden
        Assert.assertEquals(e1Bounds, editor.getBounds(e1Bot));
        // Undo create message
        undo();
        // Undo create IU
        undo();
        // Undo delete
        undo();
        assertNotChange();
    }

    /**
     * VP-1338 Validate that moving an execution, on lifeline, will not result to have its source message other end
     * overlaping an Combined Fragment.
     */
    public void testMoveExecutionOnLifelineCanNotHaveItSourceMessageOverlapingCF() {
        // create an execution on lifeline B
        Point executionLocation = new Point(instanceRoleEditPartCBounds.getCenter().x, e1Bounds.getBottom().getTranslated(0, 10).y);
        java.util.Optional<SWTBotGefEditPart> newExecutionBotOption = createExecutionWithResult(executionLocation);
        Assert.assertTrue(newExecutionBotOption.isPresent());
        SWTBotGefEditPart newExecutionBot = newExecutionBotOption.get();
        Rectangle newExecutionBounds = editor.getBounds(newExecutionBot);

        // add a message from the new execution
        Point messageSourceLocation = newExecutionBounds.getTop().getCopy().getTranslated(0, 10);
        Point messageTargetLocation = new Point(instanceRoleEditPartBBounds.getCenter().x, newExecutionBounds.getTop().getCopy().getTranslated(0, 10).y);
        createMessage(InteractionsConstants.READ_TOOL_ID, messageSourceLocation, messageTargetLocation);

        // Move e1 to have its source message on range of IU
        CheckSelectedCondition checkSelected = new CheckSelectedCondition(editor, newExecutionBot.part());
        editor.drag(newExecutionBot, editor.getBounds(firstCombinedFragmentBot).getLocation().translate(0, 10));
        bot.waitUntil(checkSelected);

        // Validate that the move is forbidden
        Assert.assertEquals(newExecutionBounds, editor.getBounds(newExecutionBot));

        // Validate that the move is forbidden
        Assert.assertEquals(e1Bounds, editor.getBounds(e1Bot));
        // Undo create message
        undo();
        // Undo create Execution
        undo();
        assertNotChange();
    }

    /**
     * VP-1338 Validate that moving an execution, on lifeline, will not result to have its target message other end
     * overlaping an Combined Fragment.
     */
    public void testMoveExecutionOnLifelineCanNotHaveItTargetMessageOverlapingCF() {
        // create an execution on lifeline B
        Point executionLocation = new Point(instanceRoleEditPartCBounds.getCenter().x, e1Bounds.getBottom().getTranslated(0, 10).y);
        java.util.Optional<SWTBotGefEditPart> newExecutionBotOption = createExecutionWithResult(executionLocation);
        Assert.assertTrue(newExecutionBotOption.isPresent());
        SWTBotGefEditPart newExecutionBot = newExecutionBotOption.get();
        Rectangle newExecutionBounds = editor.getBounds(newExecutionBot);

        // add a message from the new execution
        Point messageSourceLocation = new Point(instanceRoleEditPartBBounds.getCenter().x, newExecutionBounds.getTop().getCopy().getTranslated(0, 10).y);
        Point messageTargetLocation = newExecutionBounds.getTop().getCopy().getTranslated(0, 10);
        createMessage(InteractionsConstants.READ_TOOL_ID, messageSourceLocation, messageTargetLocation);

        // Move e1 to have its source message on range of IU
        CheckSelectedCondition checkSelected = new CheckSelectedCondition(editor, newExecutionBot.part());
        editor.drag(newExecutionBounds.getTop(), editor.getBounds(firstCombinedFragmentBot).getLocation().translate(0, 10));
        bot.waitUntil(checkSelected);

        // Validate that the move is forbidden
        Assert.assertEquals(newExecutionBounds, editor.getBounds(newExecutionBot));

        // Validate that the move is forbidden
        Assert.assertEquals(e1Bounds, editor.getBounds(e1Bot));
        // Undo create message
        undo();
        // Undo create Execution
        undo();
        assertNotChange();
    }

    /**
     * VP-1338 Validate that moving an execution, on lifeline, will not result to have its source message other end
     * overlaping an Operand.
     */
    public void testMoveExecutionOnLifelineCanNotHaveItsSourceMessageOverlapingOperand() {
        // create an execution on lifeline B
        Point executionLocation = new Point(instanceRoleEditPartCBounds.getCenter().x, e1Bounds.getBottom().getTranslated(0, 10).y);
        java.util.Optional<SWTBotGefEditPart> newExecutionBotOption = createExecutionWithResult(executionLocation);
        Assert.assertTrue(newExecutionBotOption.isPresent());
        SWTBotGefEditPart newExecutionBot = newExecutionBotOption.get();
        Rectangle newExecutionBounds = editor.getBounds(newExecutionBot);

        // add a message from the new execution
        Point messageSourceLocation = newExecutionBounds.getTop().getCopy().getTranslated(0, 10);
        Point messageTargetLocation = new Point(instanceRoleEditPartBBounds.getCenter().x, newExecutionBounds.getTop().getCopy().getTranslated(0, 10).y);
        createMessage(InteractionsConstants.READ_TOOL_ID, messageSourceLocation, messageTargetLocation);

        // Move e1 to have its source message on range of IU
        CheckSelectedCondition checkSelected = new CheckSelectedCondition(editor, newExecutionBot.part());
        editor.drag(newExecutionBounds.getTop(), firstCombinedFragmentBounds.getCenter());
        bot.waitUntil(checkSelected);

        // Validate that the move is forbidden
        Assert.assertEquals(newExecutionBounds, editor.getBounds(newExecutionBot));

        // Validate that the move is forbidden
        Assert.assertEquals(e1Bounds, editor.getBounds(e1Bot));
        // Undo create message
        undo();
        // Undo create Execution
        undo();
        assertNotChange();
    }

    /**
     * VP-1338 Validate that moving an execution, on lifeline, will not result to have its source message other end
     * overlaping an Operand.
     */
    public void testMoveExecutionOnLifelineCanNotHaveItsTargetMessageOverlapingOperand() {
        // create an execution on lifeline B
        Point executionLocation = new Point(instanceRoleEditPartCBounds.getCenter().x, e1Bounds.getBottom().getTranslated(0, 10).y);
        java.util.Optional<SWTBotGefEditPart> newExecutionBotOption = createExecutionWithResult(executionLocation);
        Assert.assertTrue(newExecutionBotOption.isPresent());
        SWTBotGefEditPart newExecutionBot = newExecutionBotOption.get();
        Rectangle newExecutionBounds = editor.getBounds(newExecutionBot);

        // add a message from the new execution
        Point messageSourceLocation = new Point(instanceRoleEditPartBBounds.getCenter().x, newExecutionBounds.getTop().getCopy().getTranslated(0, 10).y);
        Point messageTargetLocation = newExecutionBounds.getTop().getCopy().getTranslated(0, 10);
        createMessage(InteractionsConstants.READ_TOOL_ID, messageSourceLocation, messageTargetLocation);

        // Move e1 to have its source message on range of IU
        CheckSelectedCondition checkSelected = new CheckSelectedCondition(editor, newExecutionBot.part());
        editor.drag(newExecutionBounds.getTop(), firstCombinedFragmentBounds.getCenter());
        bot.waitUntil(checkSelected);

        // Validate that the move is forbidden
        Assert.assertEquals(newExecutionBounds, editor.getBounds(newExecutionBot));

        // Validate that the move is forbidden
        Assert.assertEquals(e1Bounds, editor.getBounds(e1Bot));
        // Undo create message
        undo();
        // Undo create Execution
        undo();
        assertNotChange();
    }

    /**
     * VP-1338 Validate that moving an execution, in operand, will not result to have its source message other end
     * overlaping an Interaction Use.
     */
    public void testMoveExecutionInOperandCanNotHaveItsSourceMessageOverlapingIU() {
        // create an IU on lifeline A
        Point iuSourceLocation = firstOperandOfFirstCombinedFragmentBounds.getBottomLeft().getTranslated(0, -10);
        Point iuTargetLocation = firstOperandOfFirstCombinedFragmentBounds.getBottom().getTranslated(0, -5);
        SWTBotGefEditPart iuBot = createInteractionUseWithResult(iuSourceLocation, iuTargetLocation);
        bot.waitUntil(new CheckNumberOfDescendants(sequenceDiagramBot, InteractionUseEditPart.class, 1));
        Rectangle iuBounds = editor.getBounds(iuBot);
        Rectangle newInstanceRoleEditPartABounds = editor.getBounds(instanceRoleEditPartABot);
        Rectangle e2NewBounds = editor.getBounds(e2Bot);

        // add a message to e2
        Point messageSourceLocation = e2NewBounds.getTop().getCopy().getTranslated(0, 10);
        Point messageTargetLocation = new Point(newInstanceRoleEditPartABounds.getCenter().x, e2NewBounds.getTop().getCopy().getTranslated(0, 10).y);
        createMessage(InteractionsConstants.READ_TOOL_ID, messageSourceLocation, messageTargetLocation);
        bot.waitUntil(new CheckMessageEditPartIsDisplayed(FIRST_MESSAGE_ON_LIFELINE_A, editor));

        // Move e1 to have its source message on range of IU
        CheckSelectedCondition checkSelected = new CheckSelectedCondition(editor, e2EditPart);
        editor.drag(e2NewBounds.getTop(), iuBounds.getCenter());
        bot.waitUntil(checkSelected);

        // Validate that the move is forbidden
        Assert.assertEquals(e2NewBounds, editor.getBounds(e2Bot));

        // Validate that the move is forbidden
        Assert.assertEquals(e2NewBounds, editor.getBounds(e2Bot));
        // Undo create message
        undo();
        // Undo create IU
        undo();
        assertNotChange();
    }

    /**
     * VP-1338 Validate that moving an execution, in operand, will not result to have its target message other end
     * overlaping an Interaction Use.
     */
    public void testMoveExecutionInOperandCanNotHaveItsTargetMessageOverlapingIU() {
        // create an IU on lifeline A
        Point iuSourceLocation = firstOperandOfFirstCombinedFragmentBounds.getBottomLeft().getTranslated(0, -10);
        Point iuTargetLocation = firstOperandOfFirstCombinedFragmentBounds.getBottom().getTranslated(0, -5);
        SWTBotGefEditPart iuBot = createInteractionUseWithResult(iuSourceLocation, iuTargetLocation);
        bot.waitUntil(new CheckNumberOfDescendants(sequenceDiagramBot, InteractionUseEditPart.class, 1));
        Rectangle iuBounds = editor.getBounds(iuBot);
        Rectangle newInstanceRoleEditPartABounds = editor.getBounds(instanceRoleEditPartABot);
        Rectangle e2NewBounds = editor.getBounds(e2Bot);

        // add a message to e2
        Point messageSourceLocation = new Point(newInstanceRoleEditPartABounds.getCenter().x, e2NewBounds.getTop().getCopy().getTranslated(0, 10).y);
        Point messageTargetLocation = e2NewBounds.getTop().getCopy().getTranslated(0, 10);
        createMessage(InteractionsConstants.READ_TOOL_ID, messageSourceLocation, messageTargetLocation);
        bot.waitUntil(new CheckMessageEditPartIsDisplayed(FIRST_MESSAGE, editor));

        // Move e1 to have its source message on range of IU
        CheckSelectedCondition checkSelected = new CheckSelectedCondition(editor, e2EditPart);
        editor.drag(e2NewBounds.getTop(), iuBounds.getCenter());
        bot.waitUntil(checkSelected);

        // Validate that the move is forbidden
        Assert.assertEquals(e2NewBounds, editor.getBounds(e2Bot));

        // Validate that the move is forbidden
        Assert.assertEquals(e2NewBounds, editor.getBounds(e2Bot));
        // Undo create message
        undo();
        // Undo create IU
        undo();
        assertNotChange();
    }

    /**
     * VP-1338 Validate that moving an execution, in operand, will not result to have its source message other end
     * overlaping an Combined Fragment.
     */
    public void testMoveExecutionInOperandCanNotHaveItsSourceMessagesOverlapingOtherCF() {
        // create an IU on lifeline A
        Point iuSourceLocation = firstOperandOfFirstCombinedFragmentBounds.getBottomLeft().getTranslated(0, -10);
        Point iuTargetLocation = firstOperandOfFirstCombinedFragmentBounds.getBottom().getTranslated(0, -5);
        SWTBotGefEditPart cfBot = createCombinedFragmentWithResult(iuSourceLocation, iuTargetLocation);
        bot.waitUntil(new CheckNumberOfDescendants(sequenceDiagramBot, CombinedFragmentEditPart.class, 3));
        Rectangle cfBounds = editor.getBounds(cfBot);
        Rectangle newInstanceRoleEditPartABounds = editor.getBounds(instanceRoleEditPartABot);
        Rectangle e2NewBounds = editor.getBounds(e2Bot);

        // add a message to e2
        Point messageSourceLocation = e2NewBounds.getTop().getCopy().getTranslated(0, 10);
        Point messageTargetLocation = new Point(newInstanceRoleEditPartABounds.getCenter().x, e2NewBounds.getTop().getCopy().getTranslated(0, 10).y);
        createMessage(InteractionsConstants.READ_TOOL_ID, messageSourceLocation, messageTargetLocation);
        bot.waitUntil(new CheckMessageEditPartIsDisplayed(FIRST_MESSAGE_ON_LIFELINE_A, editor));

        // Move e1 to have its source message on range of CF (Header)
        CheckSelectedCondition checkSelected = new CheckSelectedCondition(editor, e2EditPart);
        editor.drag(e2NewBounds.getTop(), cfBounds.getTop().getTranslated(0, 10));
        bot.waitUntil(checkSelected);

        // Validate that the move is forbidden
        Assert.assertEquals(e2NewBounds, editor.getBounds(e2Bot));

        // Validate that the move is forbidden
        Assert.assertEquals(e2NewBounds, editor.getBounds(e2Bot));
        // Undo create message
        undo();
        // Undo create IU
        undo();
        assertNotChange();
    }

    /**
     * VP-1338 Validate that moving an execution, in operand, will not result to have its source message other end
     * overlaping an Combined Fragment.
     */
    public void testMoveExecutionInOperandCanNotHaveItsTargetMessagesOverlapingOtherCF() {
        // create an IU on lifeline A
        Point iuSourceLocation = firstOperandOfFirstCombinedFragmentBounds.getBottomLeft().getTranslated(0, -10);
        Point iuTargetLocation = firstOperandOfFirstCombinedFragmentBounds.getBottom().getTranslated(0, -5);
        SWTBotGefEditPart cfBot = createCombinedFragmentWithResult(iuSourceLocation, iuTargetLocation);
        bot.waitUntil(new CheckNumberOfDescendants(sequenceDiagramBot, CombinedFragmentEditPart.class, 3));
        Rectangle cfBounds = editor.getBounds(cfBot);
        Rectangle newInstanceRoleEditPartABounds = editor.getBounds(instanceRoleEditPartABot);
        Rectangle e2NewBounds = editor.getBounds(e2Bot);

        // add a message to e2
        Point messageSourceLocation = new Point(newInstanceRoleEditPartABounds.getCenter().x, e2NewBounds.getTop().getCopy().getTranslated(0, 10).y);
        Point messageTargetLocation = e2NewBounds.getTop().getCopy().getTranslated(0, 10);
        createMessage(InteractionsConstants.READ_TOOL_ID, messageSourceLocation, messageTargetLocation);
        bot.waitUntil(new CheckMessageEditPartIsDisplayed(FIRST_MESSAGE, editor));

        // Move e1 to have its source message on range of CF (Header)
        CheckSelectedCondition checkSelected = new CheckSelectedCondition(editor, e2EditPart);
        editor.drag(e2NewBounds.getTop(), cfBounds.getTop().getTranslated(0, 10));
        bot.waitUntil(checkSelected);

        // Validate that the move is forbidden
        Assert.assertEquals(e2NewBounds, editor.getBounds(e2Bot));

        // Validate that the move is forbidden
        Assert.assertEquals(e2NewBounds, editor.getBounds(e2Bot));
        // Undo create message
        undo();
        // Undo create IU
        undo();
        assertNotChange();
    }

    /**
     * VP-1338 Validate that moving an execution, in operand, will not result to have its source message other end
     * overlaping an Operand.
     */
    public void testMoveExecutionInOperandCanNotHaveItsSourceMessageOverlapingOtherOperand() {
        // create an IU on lifeline A
        Point iuSourceLocation = firstOperandOfFirstCombinedFragmentBounds.getBottomLeft().getTranslated(0, -10);
        Point iuTargetLocation = firstOperandOfFirstCombinedFragmentBounds.getBottom().getTranslated(0, -5);
        SWTBotGefEditPart cfBot = createCombinedFragmentWithResult(iuSourceLocation, iuTargetLocation);
        bot.waitUntil(new CheckNumberOfDescendants(sequenceDiagramBot, CombinedFragmentEditPart.class, 3));
        Rectangle cfBounds = editor.getBounds(cfBot);
        Rectangle newInstanceRoleEditPartABounds = editor.getBounds(instanceRoleEditPartABot);
        Rectangle e2NewBounds = editor.getBounds(e2Bot);

        // add a message to e2
        Point messageSourceLocation = e2NewBounds.getTop().getCopy().getTranslated(0, 10);
        Point messageTargetLocation = new Point(newInstanceRoleEditPartABounds.getCenter().x, e2NewBounds.getTop().getCopy().getTranslated(0, 10).y);
        createMessage(InteractionsConstants.READ_TOOL_ID, messageSourceLocation, messageTargetLocation);
        bot.waitUntil(new CheckMessageEditPartIsDisplayed(FIRST_MESSAGE_ON_LIFELINE_A, editor));

        // Move e1 to have its source message on range of CF (Header)
        CheckSelectedCondition checkSelected = new CheckSelectedCondition(editor, e2EditPart);
        editor.drag(e2NewBounds.getTop(), cfBounds.getCenter());
        bot.waitUntil(checkSelected);

        // Validate that the move is forbidden
        Assert.assertEquals(e2NewBounds, editor.getBounds(e2Bot));

        // Validate that the move is forbidden
        Assert.assertEquals(e2NewBounds, editor.getBounds(e2Bot));
        // Undo create message
        undo();
        // Undo create IU
        undo();
        assertNotChange();
    }

    /**
     * VP-1338 Validate that moving an execution, in operand, will not result to have its target message other end
     * overlaping an Operand.
     */
    public void testMoveExecutionInOperandCanNotHaveItsTargetMessageOverlapingOtherOperand() {
        // create an IU on lifeline A
        Point iuSourceLocation = firstOperandOfFirstCombinedFragmentBounds.getBottomLeft().getTranslated(0, -10);
        Point iuTargetLocation = firstOperandOfFirstCombinedFragmentBounds.getBottom().getTranslated(0, -5);
        SWTBotGefEditPart cfBot = createCombinedFragmentWithResult(iuSourceLocation, iuTargetLocation);
        bot.waitUntil(new CheckNumberOfDescendants(sequenceDiagramBot, CombinedFragmentEditPart.class, 3));
        Rectangle cfBounds = editor.getBounds(cfBot);
        Rectangle newInstanceRoleEditPartABounds = editor.getBounds(instanceRoleEditPartABot);
        Rectangle e2NewBounds = editor.getBounds(e2Bot);

        // add a message to e2
        Point messageSourceLocation = new Point(newInstanceRoleEditPartABounds.getCenter().x, e2NewBounds.getTop().getCopy().getTranslated(0, 10).y);
        Point messageTargetLocation = e2NewBounds.getTop().getCopy().getTranslated(0, 10);
        createMessage(InteractionsConstants.READ_TOOL_ID, messageSourceLocation, messageTargetLocation);
        bot.waitUntil(new CheckMessageEditPartIsDisplayed(FIRST_MESSAGE, editor));

        // Move e1 to have its source message on range of CF (Header)
        CheckSelectedCondition checkSelected = new CheckSelectedCondition(editor, e2EditPart);
        editor.drag(e2NewBounds.getTop(), cfBounds.getCenter());
        bot.waitUntil(checkSelected);

        // Validate that the move is forbidden
        Assert.assertEquals(e2NewBounds, editor.getBounds(e2Bot));

        // Validate that the move is forbidden
        Assert.assertEquals(e2NewBounds, editor.getBounds(e2Bot));
        // Undo create message
        undo();
        // Undo create IU
        undo();
        assertNotChange();
    }

    /**
     * Test that tries to resize an execution in the first operand lower than
     * the operand bottom bound. Then, dimension of resized or moved elements
     * are checked.
     */
    public void testResizeExecutionOutOfFirstOperandLowerBound() {
        e2Bot.select();

        checkExecutionResize(LIFELINE_B, 1, 0, e2Bounds, 200);
    }
    
    /**
     * Test that tries to resize an execution in the first operand lower than the operand bottom bound (not accepted).
     */
    public void testResizeExecutionOutOfFirstOperandUpperBound() {
        e2Bot.select();

        Point expectedTranslation = new Point(0, 200);
        try {
            CheckResize cR = new CheckResize(LIFELINE_B, 1, expectedTranslation, editor);
            editor.drag(e2Bounds.getBottom(), e2Bounds.getBottom().getCopy().getTranslated(expectedTranslation));
            bot.waitUntil(cR);
            fail("An execution can be resized further than its parent operand bottom bound only if it is the last operand of its parent combined fragment");
        } catch (TimeoutException te) {
            // Failed as expected.
        }
    }

    /**
     * Test that resizes an execution in the last operand upper than the operand
     * top bound. Then, dimension of resized or moved elements are checked.
     */
    public void testResizeExecutionOutOfLastOperandUpperBound() {
        String toolName = "Execution";
        ICondition done = new CheckToolIsActivated(editor, toolName);
        editor.activateTool(toolName);
        bot.waitUntil(done);
        editor.click(e2Bounds.getCenter().x, secondOperandOfFirstCombinedFragmentBounds.getCenter().y);
        SWTBotGefEditPart e3Bot = instanceRoleEditPartBBot.parent().descendants(IsInstanceOf.instanceOf(ExecutionEditPart.class)).get(2);
        e3Bot.select();

        Rectangle executionBounds = editor.getBounds(e3Bot);
        checkExecutionResize(LIFELINE_B, 2, 1, executionBounds, secondOperandOfFirstCombinedFragmentBounds.getTop().y - executionBounds.getTop().y - 10);
    }

    private void checkExecutionResize(String labelOfLifelineEditPart, int indexOfExecution, int indexOfParentOperand, Rectangle executionBounds, int expectedResize) {
        CheckResize cR = new CheckResize(labelOfLifelineEditPart, indexOfExecution, new Dimension(0, Math.abs(expectedResize)), editor);
        if (expectedResize > 0) {
            editor.drag(executionBounds.getBottom(), executionBounds.getBottom().translate(0, expectedResize));
        } else {
            editor.drag(executionBounds.getTop(), executionBounds.getTop().translate(0, expectedResize));
        }
        bot.waitUntil(cR);
        SWTBotGefEditPart executionAfterRezize = instanceRoleEditPartBBot.parent().descendants(IsInstanceOf.instanceOf(ExecutionEditPart.class)).get(indexOfExecution);
        Rectangle executionBoundsAfterRezize = editor.getBounds(executionAfterRezize);

        SWTBotGefEditPart parentOperand = firstCombinedFragmentBot.descendants(IsInstanceOf.instanceOf(OperandEditPart.class)).get(indexOfParentOperand);
        Rectangle parentOperandBoundsAfterRezize = editor.getBounds(parentOperand);

        SWTBotGefEditPart firstCombinedFragmentBotAfterRezize = sequenceDiagramBot.descendants(IsInstanceOf.instanceOf(CombinedFragmentEditPart.class)).get(0);
        Rectangle firstCombinedFragmentBoundsAfterRezize = editor.getBounds(firstCombinedFragmentBotAfterRezize);

        SWTBotGefEditPart secondCombinedFragmentBotAfterRezize = sequenceDiagramBot.descendants(IsInstanceOf.instanceOf(CombinedFragmentEditPart.class)).get(1);
        Rectangle secondCombinedFragmentBoundsAfterRezize = editor.getBounds(secondCombinedFragmentBotAfterRezize);

        assertEquals("The execution should have resized.", executionBounds.height + Math.abs(expectedResize), executionBoundsAfterRezize.height);
        assertNotEquals("The size of the containing operand should have changed.", firstCombinedFragmentBounds.height, parentOperandBoundsAfterRezize.height);
        if (expectedResize > 0) {
            // Resize in the bottom direction
            assertEquals("Unexpected gap between the end of the resized execution and the containing operand.", parentOperandBoundsAfterRezize.getBottom().y,
                    executionBoundsAfterRezize.getBottom().y + LayoutConstants.EXECUTION_CHILDREN_MARGIN);
            assertEquals("The gap between both Combined Fragment should be unchanged.", secondCombinedFragmentBounds.getTop().y - firstCombinedFragmentBounds.getBottom().y,
                    secondCombinedFragmentBoundsAfterRezize.getTop().y - firstCombinedFragmentBoundsAfterRezize.getBottom().y);
            assertEquals("Second Combined Fragment dimension should be unchanged.", secondCombinedFragmentBounds.getSize(), secondCombinedFragmentBoundsAfterRezize.getSize());
        } else {
            // Resize in the top direction
            assertEquals("Unexpected gap between the start of the resized execution and the containing operand.", parentOperandBoundsAfterRezize.getTop().y,
                    executionBoundsAfterRezize.getTop().y - LayoutConstants.EXECUTION_CHILDREN_MARGIN);
        }
    }

    /**
     * Test that resizes an execution in the last operand lower than the operand bottom bound (accepted). Then,
     * dimension of resized or moved elements are checked.
     */
    public void testResizeExecutionOutOfLastOperandLowerBound() {
        String toolName = "Execution";
        ICondition done = new CheckToolIsActivated(editor, toolName);
        editor.activateTool(toolName);
        bot.waitUntil(done);
        editor.click(e2Bounds.getCenter().x, secondOperandOfFirstCombinedFragmentBounds.getCenter().y);
        SWTBotGefEditPart e3Bot = instanceRoleEditPartBBot.parent().descendants(IsInstanceOf.instanceOf(ExecutionEditPart.class)).get(2);
        Rectangle e3Bounds = editor.getBounds(e3Bot);
        e3Bot.select();

        Dimension expectedResize = new Dimension(0, 200);
        CheckResize cR = new CheckResize(LIFELINE_B, 2, expectedResize, editor);

        editor.drag(e3Bounds.getBottom(), e3Bounds.getBottom().getCopy().getTranslated(expectedResize));
        bot.waitUntil(cR);
        SWTBotGefEditPart e3BotAfterRezize = instanceRoleEditPartBBot.parent().descendants(IsInstanceOf.instanceOf(ExecutionEditPart.class)).get(2);
        Rectangle e3BoundsAfterRezize = editor.getBounds(e3BotAfterRezize);

        SWTBotGefEditPart secondOperandOfFirstCombinedFragmentBotAfterRezize = firstCombinedFragmentBot.descendants(IsInstanceOf.instanceOf(OperandEditPart.class)).get(1);
        Rectangle secondOperandOfFirstCombinedFragmentBoundsAfterRezize = editor.getBounds(secondOperandOfFirstCombinedFragmentBotAfterRezize);

        SWTBotGefEditPart secondCombinedFragmentBotAfterRezize = sequenceDiagramBot.descendants(IsInstanceOf.instanceOf(CombinedFragmentEditPart.class)).get(1);
        Rectangle secondCombinedFragmentBoundsAfterRezize = editor.getBounds(secondCombinedFragmentBotAfterRezize);

        assertEquals("Unexpected gap between the end of the resized execution and the containing operand.", secondOperandOfFirstCombinedFragmentBoundsAfterRezize.getBottom().y,
                e3BoundsAfterRezize.getBottom().y + LayoutConstants.EXECUTION_CHILDREN_MARGIN);
        assertEquals("The gap between both Combined Fragment should be unchanged.", secondCombinedFragmentBounds.getTop().y - firstCombinedFragmentBounds.getBottom().y,
                secondCombinedFragmentBoundsAfterRezize.getTop().y - secondOperandOfFirstCombinedFragmentBoundsAfterRezize.getBottom().y);
        assertEquals("Second Combined Fragment dimension should be unchanged.", secondCombinedFragmentBounds.getSize(), secondCombinedFragmentBoundsAfterRezize.getSize());
    }

    /**
     * Test that resizes last operand in the bottom direction. Expected effect:
     * Expand the whole combined fragment.
     */
    public void testResizeLastOperand() {
        editor.click(secondOperandOfFirstCombinedFragmentBounds.getCenter());
        bot.waitUntil(new CheckSelectedCondition(editor, secondOperandOfFirstCombinedFragmentBot.part()));

        Dimension expectedResize = new Dimension(0, 200);
        CheckEditPartResized cR = new CheckEditPartResized(secondOperandOfFirstCombinedFragmentBot);
        editor.drag(secondOperandOfFirstCombinedFragmentBounds.getBottom(), secondOperandOfFirstCombinedFragmentBounds.getBottom().getCopy().getTranslated(expectedResize));
        bot.waitUntil(cR, 2000);

        SWTBotGefEditPart secondOperandOfFirstCombinedFragmentBotAfterRezize = firstCombinedFragmentBot.descendants(IsInstanceOf.instanceOf(OperandEditPart.class)).get(1);
        Rectangle secondOperandOfFirstCombinedFragmentBoundsAfterRezize = editor.getBounds(secondOperandOfFirstCombinedFragmentBotAfterRezize);

        SWTBotGefEditPart firstCombinedFragmentBotAfterRezize = sequenceDiagramBot.descendants(IsInstanceOf.instanceOf(CombinedFragmentEditPart.class)).get(0);
        Rectangle firstCombinedFragmentBoundsAfterRezize = editor.getBounds(firstCombinedFragmentBotAfterRezize);

        SWTBotGefEditPart secondCombinedFragmentBotAfterRezize = sequenceDiagramBot.descendants(IsInstanceOf.instanceOf(CombinedFragmentEditPart.class)).get(1);
        Rectangle secondCombinedFragmentBoundsAfterRezize = editor.getBounds(secondCombinedFragmentBotAfterRezize);

        assertEquals("The Combined Fragment containing the resized Operand should be resized too.", firstCombinedFragmentBounds.getBottom().y - firstCombinedFragmentBoundsAfterRezize.getBottom().y,
                secondOperandOfFirstCombinedFragmentBounds.getBottom().y - secondOperandOfFirstCombinedFragmentBoundsAfterRezize.getBottom().y);
        assertEquals("The gap between both Combined Fragment should be unchanged.", secondCombinedFragmentBounds.getTop().y - firstCombinedFragmentBounds.getBottom().y,
                secondCombinedFragmentBoundsAfterRezize.getTop().y - secondOperandOfFirstCombinedFragmentBoundsAfterRezize.getBottom().y);
    }

    /**
     * Test that resizes the first operand in top direction. Expected effect:
     * Expand the whole combined fragment.
     */
    public void testResizeFirstOperand() {
        editor.click(firstOperandOfFirstCombinedFragmentBounds.getCenter());
        bot.waitUntil(new CheckSelectedCondition(editor, firstOperandOfFirstCombinedFragmentBot.part()));

        // Make a resize just after the first execution of the life line (that
        // constraints the resize)
        double maxDelta = (firstCombinedFragmentBounds.getTop().preciseY() - e1Bounds.getBottom().preciseY() - LayoutConstants.EXECUTION_CHILDREN_MARGIN);
        CheckEditPartResized cR = new CheckEditPartResized(firstOperandOfFirstCombinedFragmentBot);
        editor.drag(firstOperandOfFirstCombinedFragmentBounds.getTop(),
                firstOperandOfFirstCombinedFragmentBounds.getTop().translate(0, -maxDelta));
        bot.waitUntil(cR, 2000);

        SWTBotGefEditPart firstOperandOfFirstCombinedFragmentBotAfterRezize = firstCombinedFragmentBot.descendants(IsInstanceOf.instanceOf(OperandEditPart.class)).get(0);
        Rectangle firstOperandOfFirstCombinedFragmentBoundsAfterRezize = editor.getBounds(firstOperandOfFirstCombinedFragmentBotAfterRezize);

        SWTBotGefEditPart firstCombinedFragmentBotAfterRezize = sequenceDiagramBot.descendants(IsInstanceOf.instanceOf(CombinedFragmentEditPart.class)).get(0);
        Rectangle firstCombinedFragmentBoundsAfterRezize = editor.getBounds(firstCombinedFragmentBotAfterRezize);

        SWTBotGefEditPart secondCombinedFragmentBotAfterRezize = sequenceDiagramBot.descendants(IsInstanceOf.instanceOf(CombinedFragmentEditPart.class)).get(1);
        Rectangle secondCombinedFragmentBoundsAfterRezize = editor.getBounds(secondCombinedFragmentBotAfterRezize);

        assertEquals("The Combined Fragment containing the resized Operand should be resized too.", firstCombinedFragmentBounds.getTop().y - firstCombinedFragmentBoundsAfterRezize.getTop().y,
                firstOperandOfFirstCombinedFragmentBounds.getTop().y - firstOperandOfFirstCombinedFragmentBoundsAfterRezize.getTop().y);
        assertEquals("The gap between both Combined Fragment should be unchanged.", secondCombinedFragmentBounds.getTop().y - firstCombinedFragmentBounds.getBottom().y,
                secondCombinedFragmentBoundsAfterRezize.getTop().y - firstCombinedFragmentBoundsAfterRezize.getBottom().y);
    }

    /**
     * Test that resizes the first operand in top direction, but forbidden
     * because the combined fragment can not be resized as an execution exists
     * above. Expected effect: Nothing changes.
     */
    public void testResizeFirstOperandOutOfAuthorized() {
        editor.click(firstOperandOfFirstCombinedFragmentBounds.getCenter());
        bot.waitUntil(new CheckSelectedCondition(editor, firstOperandOfFirstCombinedFragmentBot.part()));

        // Make a resize just before the first execution of the life line (that
        // forbids the resize)
        double maxDelta = (firstCombinedFragmentBounds.getTop().preciseY() - e1Bounds.getBottom().preciseY() + LayoutConstants.EXECUTION_CHILDREN_MARGIN);
        CheckEditPartResized cR = new CheckEditPartResized(firstOperandOfFirstCombinedFragmentBot);
        editor.drag(firstOperandOfFirstCombinedFragmentBounds.getTop(),
                firstOperandOfFirstCombinedFragmentBounds.getTop().translate(0, -maxDelta - 1));
        try {
            bot.waitUntil(cR, 2000);
            fail("An execution can be resized further than its parent operand top bound only if the combined fragment can be resized too.");
        } catch (TimeoutException te) {
            // Failed as expected.
        }
    }
}
