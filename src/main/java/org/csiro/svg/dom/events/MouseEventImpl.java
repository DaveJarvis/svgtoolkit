// MouseEventImpl.java
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
// $Id: MouseEventImpl.java,v 1.5 2002/03/11 23:46:52 bella Exp $
//

package org.csiro.svg.dom.events;

import org.w3c.dom.events.MouseEvent;
import org.w3c.dom.views.AbstractView;
import org.w3c.dom.events.EventTarget;

public class MouseEventImpl extends UIEventImpl implements MouseEvent {

  int screenX;
  int screenY;
  int clientX;
  int clientY;
  boolean ctrlKey;
  boolean altKey;
  boolean shiftKey;
  boolean metaKey;
  short button;
  EventTarget relatedTarget;

  public MouseEventImpl() {
  }

   public void initMouseEvent( String typeArg,
                               boolean canBubbleArg,
                               boolean cancelableArg,
                               AbstractView viewArg,
                               int detailArg,
                               int screenXArg,
                               int screenYArg,
                               int clientXArg,
                               int clientYArg,
                               boolean ctrlKeyArg,
                               boolean altKeyArg,
                               boolean shiftKeyArg,
                               boolean metaKeyArg,
                               short buttonArg,
                               EventTarget relatedTargetArg) {

    super.initUIEvent(typeArg, canBubbleArg, cancelableArg, viewArg, detailArg);
    screenX = screenXArg;
    screenY = screenYArg;
    clientX = clientXArg;
    clientY = clientYArg;
    ctrlKey = ctrlKeyArg;
    altKey = altKeyArg;
    shiftKey = shiftKeyArg;
    metaKey = metaKeyArg;
    button = buttonArg;
    relatedTarget = relatedTargetArg;
  }

  public int getScreenX() {
    return screenX;
  }

  public int getScreenY() {
    return screenY;
  }

  public int getClientX() {
    return clientX;
  }

  public int getClientY() {
    return clientY;
  }

  public boolean getCtrlKey(){
    return ctrlKey;
  }

  public boolean getShiftKey() {
    return shiftKey;
  }

  public boolean getAltKey() {
    return altKey;
  }

  public boolean getMetaKey() {
    return metaKey;
  }

  public short getButton() {
    return button;
  }

  public EventTarget getRelatedTarget() {
    return relatedTarget;

  }
} // MouseEventImpl
