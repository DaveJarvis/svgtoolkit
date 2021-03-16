// SVGICCColorImpl.java
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
// $Id: SVGICCColorImpl.java,v 1.4 2000/11/21 05:56:59 bella Exp $
//

package org.csiro.svg.dom;

import org.w3c.dom.svg.SVGICCColor;
import org.w3c.dom.svg.SVGNumberList;

/**
 * SVGICCColorImpl is the implementation of org.w3c.dom.svg.SVGICCColor
 */
public class SVGICCColorImpl implements SVGICCColor {

  protected String colorProfile;

  public SVGICCColorImpl() {
  }

  public String getColorProfile( ) {
    return colorProfile;
  }

  public void setColorProfile( String colorProfile ) {
    this.colorProfile = colorProfile;
  }

  public SVGNumberList getColors( ) {
    return null;
  }

} // SVGICCColorImpl
