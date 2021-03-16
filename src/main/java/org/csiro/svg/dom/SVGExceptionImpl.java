// SVGExceptionImpl.java
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
// $Id: SVGExceptionImpl.java,v 1.3 2000/09/26 14:10:22 dino Exp $
//

package org.csiro.svg.dom;

import org.w3c.dom.svg.SVGException;

/**
 * SVGExceptionImpl is the implementation of org.w3c.dom.svg.SVGException
 */
public class SVGExceptionImpl extends SVGException {

  public SVGExceptionImpl(short code, String message) {
    super(code, message);
  }
} 
