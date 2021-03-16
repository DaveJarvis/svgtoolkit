// EventImpl.java
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
// $Id: EventImpl.java,v 1.5 2001/02/08 00:10:13 bella Exp $
//

package org.csiro.svg.dom.events;

import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventTarget;

public class EventImpl implements Event {

  EventTarget target;
  EventTarget currentTarget;
  String  eventType;
  boolean canBubble;
  boolean cancelable;

  public void initEvent(String eventTypeArg, boolean canBubbleArg, boolean cancelableArg) {
    eventType = eventTypeArg;
    canBubble = canBubbleArg;
    cancelable = cancelableArg;
  }

  public String getType() {
    return eventType;
  }

  public EventTarget getTarget() {
    return target;
  }
  public void setTarget(EventTarget target) {
    this.target = target;
  }

  public EventTarget getCurrentTarget() {
    return currentTarget;
  }

  public short getEventPhase() {
    return (short)0;
  }

  public boolean getBubbles() {
    return canBubble;
  }

  public boolean getCancelable() {
    return cancelable;
  }


  public long getTimeStamp() {
    // should return the time the event was generated
    return 0;
  }

  public void stopPropagation() {
    //TODO: Implement this org.w3c.dom.events.Event method
  }

  public void preventDefault() {
    //TODO: Implement this org.w3c.dom.events.Event method
  }

} // EventImpl
