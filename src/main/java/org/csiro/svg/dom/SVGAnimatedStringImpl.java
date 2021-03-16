// SVGAnimatedStringImpl.java
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
// $Id: SVGAnimatedStringImpl.java,v 1.6 2000/11/30 01:34:18 bella Exp $
//

package org.csiro.svg.dom;

import org.w3c.dom.DOMException;
import org.w3c.dom.svg.SVGAnimatedString;

/**
 * SVGAnimatedStringImpl is the implementation of org.w3c.dom.svg.SVGAnimatedString
 */
public class SVGAnimatedStringImpl extends SVGAnimatedValue implements
  SVGAnimatedString {

  private String baseVal;

  public SVGAnimatedStringImpl(String baseVal, SVGElementImpl owner) {
    this.owner = owner;
    this.baseVal = baseVal;
  }

  public String getBaseVal() {
    return baseVal;
  }

  public void setBaseVal(String baseVal) throws DOMException {
    this.baseVal = baseVal;
    owner.ownerDoc.setChanged();
  }

  public String getAnimVal() {
    if (animations == null) {
      return baseVal;
    } else {

      int numAnimations = animations.size();
      String result = null;
      for (int i = 0; i < numAnimations; i++) {
        SVGAnimationElementImpl animation = (SVGAnimationElementImpl)animations.elementAt(i);
        String animVal = (String)animation.getCurrentValue(ANIMTYPE_STRING);
        if (animVal != null) {
          result = animVal;
          break;
        }
      }
      if (result != null) {
        return result;
      } else { // the animation element couldn't determine a value
        return baseVal;
      }
    }
  }
}