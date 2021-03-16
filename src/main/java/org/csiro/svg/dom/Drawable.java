/*****************************************************************************
 * Copyright (C) CSIRO Mathematical and Information Sciences.                *
 * All rights reserved.                                                      *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the CSIRO Software License  *
 * version 1.0, a copy of which has been included with this distribution in  *
 * the LICENSE file.                                                         *
 *****************************************************************************/

package org.csiro.svg.dom;

import org.w3c.dom.svg.SVGElement;

import java.awt.*;

/**
 * The Drawable interface should be implemented by all {@link SVGElement}s
 * that are drawn.
 */
public interface Drawable {

  void draw( Graphics2D graphics, boolean refreshData );

  boolean contains( double x, double y );

  double boundingArea();

}
