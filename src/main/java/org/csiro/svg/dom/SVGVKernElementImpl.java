// SVGVKernElementImpl.java
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
// $Id: SVGVKernElementImpl.java,v 1.3 2000/09/26 14:10:35 dino Exp $
//

package org.csiro.svg.dom;

import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGVKernElement;

public class SVGVKernElementImpl extends SVGElementImpl implements
  SVGVKernElement {

   public SVGVKernElementImpl(SVGDocumentImpl owner) {
    super(owner, "vkern");
  }

  public SVGVKernElementImpl(SVGDocumentImpl owner, Element elem) {
    super(owner, elem, "vkern");
  }

  public SVGElementImpl cloneElement() {
    return new SVGVKernElementImpl(getOwnerDoc(), this);
  }

}
