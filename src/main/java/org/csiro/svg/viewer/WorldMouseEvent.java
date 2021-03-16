// WorldMouseEvent.java
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
// $Id: WorldMouseEvent.java,v 1.2 2000/09/26 14:10:40 dino Exp $
//

package org.csiro.svg.viewer;

import org.csiro.svg.dom.*;
import org.csiro.svg.parser.*;


import java.awt.event.MouseEvent;
import java.awt.Component;

/**
 * The WorldMouseEvent is a class that extends the default MouseEvent from
 * the Java AWT to include World coordinates as well as screen values.
 */

public class WorldMouseEvent extends MouseEvent {


  double worldx;
  double worldy;

  /**
   * Constructs a WorldMouseEvent object from the given MouseEvent
   * with the specified world coordinates.
   *
   * @param event The original MouseEvent
   * @param worldx the x coordinate location of the mouse in world coordinates
   * @param worldy the y coordinate location of the mouse in world coordinates
   */
  public WorldMouseEvent(MouseEvent event, double worldx, double worldy) {
    super((Component) event.getSource(), event.getID(), event.getWhen(), 
	  event.getModifiers(), event.getX(), event.getY(), 
	  event.getClickCount(), event.isPopupTrigger());
    this.worldx = worldx;
    this.worldy = worldy;
  }

  /**
   * Constructs a WorldMouseEvent object with the specified source component,
   * type, modifiers, coordinates, and click count.
   * @param source the object where the event originated
   * @param id the event type
   * @param when the time the event occurred
   * @param modifiers the modifiers down during event
   * @param x the x coordinate location of the mouse
   * @param y the y coordinate location of the mouse
   * @param worldx the x coordinate location of the mouse in world coordinates
   * @param worldy the y coordinate location of the mouse in world coordinates
   * @param clickCount the number of mouse clicks associated with event
   * @param popupTrigger whether this event is a popup-menu trigger event
   */
  public WorldMouseEvent(Component source, int id, long when, int modifiers,
		    int x, int y, double worldx, double worldy, int clickCount, boolean popupTrigger) {
    super(source, id, when, modifiers, x, y, clickCount, popupTrigger);
    this.worldx = worldx;
    this.worldy = worldy;
  }

  /**
   * Returns the x position of the event in the world coordinates.
   */
  public double getWorldX() {
    return worldx;
  }
  
  /**
   * Returns the y position of the event in the world coordinates.
   */
  public double getWorldY() {
    return worldy;
  }
  

}
