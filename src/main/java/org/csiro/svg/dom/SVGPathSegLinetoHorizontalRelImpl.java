// SVGPathSegLinetoHorizontalRelImpl.java
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
// $Id: SVGPathSegLinetoHorizontalRelImpl.java,v 1.6 2000/11/28 06:09:58 cath Exp $
//

package org.csiro.svg.dom;

import org.w3c.dom.svg.SVGPathSegLinetoHorizontalRel;
import org.w3c.dom.svg.SVGPoint;

public class SVGPathSegLinetoHorizontalRelImpl extends SVGPathSegImpl implements
  SVGPathSegLinetoHorizontalRel {

  protected float x;

  public SVGPathSegLinetoHorizontalRelImpl(float x) {
    this.x = x;
  }

  public short getPathSegType() {
    return PATHSEG_LINETO_HORIZONTAL_REL;
  }
  public String getPathSegTypeAsLetter() {
    return "h";
  }

  public float getX() {
    return x;
  }
  public void setX(float x) {
    this.x = x;
  }

  /**
   * Returns the path segment as a string as it would appear in a path
   * data string.
   * @return The path segment data as a string.
   */
  public String toString() {
    return getPathSegTypeAsLetter() + " " + getX();
  }

    /**
   * Returns the length of this path segment.
   * @param startPoint The starting point for this segment.
   * @param lastControlPoint Used for Smooth bezier curves.
   */
  public float getTotalLength( SVGPoint startPoint, SVGPoint lastControlPoint) {
    float x1 = startPoint.getX();
    float XendPoint = x + x1;
    double distance;

    distance = Math.abs(x1-XendPoint);

    return (float) distance;
  }

  /**
   * Returns the point that is at the specified distance along this path
   * segment.
   * @param distance The distance along the path seg.
   * @param startPoint The starting point for this segment.
   * @param lastControlPoint Used for Smooth bezier curves.
   */
  public SVGPoint getPointAtLength(float distance, SVGPoint startPoint, SVGPoint lastControlPoint) {
    float x1 = startPoint.getX();
    float y1 = startPoint.getY();
    float xendPoint = x + x1;
    float tempX, tempY;
    float ratio;

    float totalLength = getTotalLength(startPoint,lastControlPoint);

    ratio = distance/totalLength;
    tempX = ratio*(xendPoint-x1) + x1;
    tempY = y1;

    SVGPoint newPoint = new SVGPointImpl(tempX, tempY);

    return newPoint;
  }
}
