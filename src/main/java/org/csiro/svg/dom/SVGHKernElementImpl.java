// SVGHKernElementImpl.java
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
// $Id: SVGHKernElementImpl.java,v 1.3 2000/09/26 14:10:25 dino Exp $
//

package org.csiro.svg.dom;

import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGHKernElement;

public class SVGHKernElementImpl extends SVGElementImpl implements
  SVGHKernElement {

   public SVGHKernElementImpl(SVGDocumentImpl owner) {
    super(owner, "hkern");
  }

  public SVGHKernElementImpl(SVGDocumentImpl owner, Element elem) {
    super(owner, elem, "hkern");
  }

  public SVGElementImpl cloneElement() {
    return new SVGHKernElementImpl(getOwnerDoc(), this);
  }

}