// MouseHandler.java
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
// $Id: MouseHandler.java,v 1.6 2000/09/26 14:10:39 dino Exp $
//

package org.csiro.svg.viewer;


import java.awt.event.*;

public abstract class MouseHandler
	implements MouseListener, MouseMotionListener {

  /**
   * The Canvas that this mouse handler uses
   */
  Canvas canvas;

  /**
   * Constructs a Mouse Handler object with the given canvas. The
   * handler will perform some function on the canvas when it
   * is given mouse events.
   *
   * @param canvas The canvas this mouse handler is handling.
   */
  public MouseHandler(Canvas canvas) {
    setCanvas(canvas);
  }


  /**
   * Sets the Canvas object.
   *
   * @param canvas The new Canvas object.
   */
  public void setCanvas(Canvas canvas) {
    this.canvas = canvas;
  }


  /**
   * Gets the Canvas object.
   *
   * @return The Canvas that this handler is associated with.
   */
  public Canvas getCanvas() {
    return canvas;
  }

  /**
   * Invoked when the mouse has been clicked in the Canvas canvas.
   */
  public void mouseClicked(MouseEvent e) {

  }

  /**
   * Invoked when the mouse has been pressed in the Canvas canvas.
   */
  public void mousePressed(MouseEvent e) {

  }

  /**
   * Invoked when the mouse has been released in the Canvas canvas.
   */
  public void mouseReleased(MouseEvent e) {

  }

  /**
   * Invoked when the mouse has entered the Canvas canvas.
   */
  public void mouseEntered(MouseEvent e) {

  }

  /**
   * Invoked when the mouse has exited the Canvas canvas.
   *
   * This default implementation clears the status
   */
  public void mouseExited(MouseEvent e) {

  }

  /**
   * Invoked when a mouse button is pressed within the Canvas canvas and then
   * dragged.
   */
  public void mouseDragged(MouseEvent e) {

  }

  /**
   * Invoked when the mouse button has been moved in the Canvas canvas,
   * when there are no buttons pressed.
   *
   * This default implementation shows the current world coordinates as the
   * status
   */
  public void mouseMoved ( MouseEvent e ) {

  }

 
}
