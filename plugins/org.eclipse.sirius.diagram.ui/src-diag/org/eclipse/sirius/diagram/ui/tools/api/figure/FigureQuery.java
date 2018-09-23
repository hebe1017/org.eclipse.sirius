/*******************************************************************************
 * Copyright (c) 2011 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.ui.tools.api.figure;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel;


import org.eclipse.sirius.ext.gmf.runtime.gef.ui.figures.SiriusWrapLabel;

import com.google.common.collect.Iterables;

/**
 * A class aggregating all the queries (read-only!) having a {@link IFigure} as
 * starting point.
 * 
 * @author lredor
 * 
 */
public class FigureQuery {

    private IFigure figure;

    /**
     * Create a new query.
     * 
     * @param figure
     *            the element to query.
     */
    public FigureQuery(IFigure figure) {
        this.figure = figure;
    }

    /**
     * Return the label figure of this figure. Search in all chidren the first
     * figure of kind {@link Label}, {@link WrapLabel} or
     * {@link SiriusWrapLabel}.
     * 
     * @return the first label figure or null if any
     */
    public java.util.Optional<IFigure> getLabelFigure() {
        java.util.Optional<IFigure> result = java.util.Optional.empty();
        if (figure instanceof SiriusWrapLabel || figure instanceof WrapLabel || figure instanceof Label) {
            result = java.util.Optional.of(figure);
        } else {
            for (IFigure childFigure : Iterables.filter(figure.getChildren(), IFigure.class)) {
                java.util.Optional<IFigure> temp = new FigureQuery(childFigure).getLabelFigure();
                if (temp.isPresent()) {
                    result = temp;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Return the label of the first label figure of this figure. Search in all
     * chidren the first figure of kind {@link Label}, {@link WrapLabel} or
     * {@link SiriusWrapLabel}.
     * 
     * @return the label of the first label figure or null if any
     */
    public java.util.Optional<String> getText() {
        java.util.Optional<String> result = java.util.Optional.empty();
        java.util.Optional<IFigure> labelFigure = getLabelFigure();
        if (labelFigure.isPresent()) {
            if (labelFigure.get() instanceof SiriusWrapLabel) {
                result = java.util.Optional.of(((SiriusWrapLabel) labelFigure.get()).getText());
            }
            if (labelFigure.get() instanceof WrapLabel) {
                result = java.util.Optional.of(((WrapLabel) labelFigure.get()).getText());
            } else if (labelFigure.get() instanceof Label) {
                result = java.util.Optional.of(((Label) labelFigure.get()).getText());
            }
        }
        return result;
    }
}
