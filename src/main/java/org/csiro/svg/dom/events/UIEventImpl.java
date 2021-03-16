// UIEventImpl.java
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
// $Id: UIEventImpl.java,v 1.3 2000/09/26 14:10:37 dino Exp $
//

package org.csiro.svg.dom.events;

import org.w3c.dom.events.UIEvent;
import org.w3c.dom.views.AbstractView;

public class UIEventImpl extends EventImpl implements UIEvent {

  AbstractView  abstractView;
  int detail;

  public UIEventImpl() {
  }

  //
  //  typeArg is one of DOMFocusIn, DOMFocusOut, DOMActivate
  //
  public void initUIEvent(String typeArg, boolean canBubbleArg,
                          boolean cancelableArg, AbstractView viewArg,
                          int detailArg) {
    //TODO: Implement this org.w3c.dom.events.UIEvent method
    super.initEvent(typeArg,canBubbleArg,cancelableArg);
    abstractView=viewArg;
    detail=detailArg;
  }

  public AbstractView getView() {
    //TODO: Implement this org.w3c.dom.events.UIEvent method
    return abstractView;
  }

  public int getDetail() {
    //TODO: Implement this org.w3c.dom.events.UIEvent method
    return detail;
  }

} // UIEventImpl
