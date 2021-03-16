// SVGPathSegImpl.java
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
// $Id: SVGPathSegImpl.java,v 1.8 2000/11/21 05:30:40 cath Exp $
//

package org.csiro.svg.dom;

import org.w3c.dom.svg.SVGPathSeg;
import org.w3c.dom.svg.SVGPoint;

public abstract class SVGPathSegImpl implements SVGPathSeg {

 public abstract float getTotalLength( SVGPoint startPoint, SVGPoint lastControlPoint);
 public abstract SVGPoint getPointAtLength(float distance, SVGPoint startPoint, SVGPoint lastControlPoint);

} 