// SVGPathSegMovetoRelImpl.java
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
// $Id: SVGPathSegMovetoRelImpl.java,v 1.4 2000/11/21 05:30:41 cath Exp $
//

package org.csiro.svg.dom;

import org.w3c.dom.svg.SVGPathSegMovetoRel;
import org.w3c.dom.svg.SVGPoint;

public class SVGPathSegMovetoRelImpl extends SVGPathSegImpl implements
  SVGPathSegMovetoRel {

  protected float x;
  protected float y;

  public SVGPathSegMovetoRelImpl(float x, float y) {
    this.x = x;
    this.y = y;
  }

  public short getPathSegType() {
    return PATHSEG_MOVETO_REL;
  }
  public String getPathSegTypeAsLetter() {
    return "m";
  }

  public float getX() {
    return x;
  }
  public void setX(float x) {
    this.x = x;
  }
  public float getY() {
    return y;
  }
  public void setY(float y) {
    this.y = y;
  }

  /**
   * Returns the path segment as a string as it would appear in a path
   * data string.
   * @return The path segment data as a string.
   */
  public String toString() {
    return getPathSegTypeAsLetter() + " " + getX() + " " + getY();
  }

            /**
   * Returns the length of this path segment.
   * @param startPoint The starting point for this segment.
   * @param lastControlPoint Used for Smooth bezier curves.
   */
  public float getTotalLength( SVGPoint startPoint, SVGPoint lastControlPoint) {

    double distance;

    distance = 0;

    return (float) distance;
  }

  /**
   * Returns the point that is at the specified distance along this path
   * segment.
   * @param distance The distance along the path seg.
   * @param startPoint The starting point for this segment.
   * @param lastControlPoint Used for Smooth bezier curves .
   */
  public SVGPoint getPointAtLength(float distance, SVGPoint startPoint, SVGPoint lastControlPoint) {

    SVGPoint newPoint = new SVGPointImpl(getX(), getY());

    return newPoint;
  }
} 
