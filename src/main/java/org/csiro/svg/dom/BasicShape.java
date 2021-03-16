// BasicShape.java
//
/*****************************************************************************
 * Copyright (C) CSIRO Mathematical and Information Sciences.                *
 * All rights reserved.                                                      *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the CSIRO Software License  *
 * version 1.0, a copy of which has been included with this distribution in  *
 * the LICENSE file.                                                         *
 *****************************************************************************/
//
// $Id: BasicShape.java,v 1.5 2000/09/26 14:10:17 dino Exp $
//

package org.csiro.svg.dom;

import java.awt.Shape;

/**
 * The BasicShape interface is implemented by the basic shapeelements, the
 * path element and the text element. ie. those SVGElements that represent a
 * single drawing object.
 */
public interface BasicShape {
  public Shape getShape();
} 
