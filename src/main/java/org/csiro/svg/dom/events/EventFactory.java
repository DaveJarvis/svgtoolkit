// EventFactory.java
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
// $Id: EventFactory.java,v 1.4 2000/09/26 14:10:36 dino Exp $
//

package org.csiro.svg.dom.events;

import org.w3c.dom.DOMException;
import org.w3c.dom.events.Event;

public class EventFactory {

  public EventFactory() {
  }

  public static Event createEvent(String eventType) throws DOMException {

    Event theEvent = null;

    if (eventType.equalsIgnoreCase("Event")) {
      theEvent = new org.csiro.svg.dom.events.EventImpl();

    } else if (eventType.equalsIgnoreCase("UIEvent")) {
      theEvent = new org.csiro.svg.dom.events.UIEventImpl();

    } else if (eventType.equalsIgnoreCase("MouseEvent")) {
      theEvent = new org.csiro.svg.dom.events.MouseEventImpl();

    } else {
      System.out.println("Unrecognised event type '" + eventType
                           + "' you're about to get a null event!");
      //throw new DOMException(0, "Unrecognised event type '" + eventType
      //                        + "' you're about to get a null event!");
    }
    return theEvent;
  }

} // EventFactory
