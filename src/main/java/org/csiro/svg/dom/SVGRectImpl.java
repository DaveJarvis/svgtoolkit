// SVGRectImpl.java
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
// $Id: SVGRectImpl.java,v 1.7 2000/09/26 14:10:32 dino Exp $
//

package org.csiro.svg.dom;

import org.w3c.dom.DOMException;
import org.w3c.dom.svg.SVGRect;

import java.awt.geom.*;


/**
 * SVGRectImpl is the implementation of org.w3c.dom.svg.SVGRect
 */
public class SVGRectImpl implements SVGRect {

  protected float x;
  protected float y;
  protected float width;
  protected float height;

  /**
   * Construct a new SVGRectImpl. Initialize to: x=0, y=0, width=0, height=0.
   */
  public SVGRectImpl() {
    x = 0;
    y = 0;
    width = 0;
    height = 0;
  }

  public SVGRectImpl(SVGRect rect) {
    x = rect.getX();
    y = rect.getY();
    width = rect.getWidth();
    height = rect.getHeight();
  }

  /**
   * Constructs a new SVGRectImpl and intializes it to contain the same values
   * as the given Rectangle2D.
   * @param rect The Rectangle2D object to use when initializing.
   */
  public SVGRectImpl(Rectangle2D rect) {
    if (rect == null) {
      rect = new Rectangle2D.Float();
    }
    x = (float)rect.getX();
    y = (float)rect.getY();
    width = (float)rect.getWidth();
    height = (float)rect.getHeight();
  }

  public SVGRectImpl(float x, float y, float width, float height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  /**
   * Returns the value of x.
   * @return Value of x.
   */
  public float getX() {
    return x;
  }

  /**
   * Sets the value of x.
   * @param x  Value to assign to x.
   */
  public void setX(float x) throws DOMException {
    this.x = x;
  }

  /**
   * Returns the value of y.
   * @return Value of y.
   */
  public float getY() {
    return y;
  }

  /**
   * Sets the value of y.
   * @param y  Value to assign to y.
   */
  public void setY(float y) throws DOMException {
    this.y = y;
  }

  /**
   * Returns the value of width.
   * @return Value of width.
   */
  public float getWidth() {
    return width;
  }

  /**
   * Sets the value of width.
   * @param width  Value to assign to width.
   */
  public void setWidth(float width) throws DOMException {
    this.width = width;
  }

  /**
   * Returns the value of height.
   * @return Value of height.
   */
  public float getHeight() {
    return height;
  }

  /**
   * Sets the value of height.
   * @param height  Value to assign to height.
   */
  public void setHeight(float height) throws DOMException {
    this.height = height;
  }

  /**
   * Returns a string representation of this rectangle in the format:
   * "x, y, width, height".
   * @return A string representaion of this rectangle.
   */
  public String toString() {
    return x + ", " + y + ", " + width + ", " + height;
  }

} // SVGRectImpl


