// SVGFontFaceSrcElementImpl.java
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
// $Id: SVGFontFaceSrcElementImpl.java,v 1.3 2000/09/26 14:10:23 dino Exp $
//

package org.csiro.svg.dom;

import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGFontFaceSrcElement;

public class SVGFontFaceSrcElementImpl extends SVGElementImpl implements
  SVGFontFaceSrcElement {

   public SVGFontFaceSrcElementImpl(SVGDocumentImpl owner) {
    super(owner, "font-face-src");
  }

  public SVGFontFaceSrcElementImpl(SVGDocumentImpl owner, Element elem) {
    super(owner, elem, "font-face-src");
  }

  public SVGElementImpl cloneElement() {
    return new SVGFontFaceSrcElementImpl(getOwnerDoc(), this);
  }

} 