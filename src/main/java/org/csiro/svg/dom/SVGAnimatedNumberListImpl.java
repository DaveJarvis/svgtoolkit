// SVGAnimatedNumberListImpl.java
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
// $Id: SVGAnimatedNumberListImpl.java,v 1.4 2000/11/30 01:34:18 bella Exp $
//

package org.csiro.svg.dom;

import org.w3c.dom.DOMException;
import org.w3c.dom.svg.SVGAnimatedNumberList;
import org.w3c.dom.svg.SVGNumberList;

public class SVGAnimatedNumberListImpl extends SVGAnimatedValue implements
  SVGAnimatedNumberList {

  private SVGNumberList baseVal;

  public SVGAnimatedNumberListImpl(SVGNumberList baseVal, SVGElementImpl owner) {
    this.owner = owner;
    this.baseVal = baseVal;
  }

  public SVGNumberList getBaseVal() {
    return baseVal;
  }

  void setBaseVal(SVGNumberList baseVal) throws DOMException {
    this.baseVal = baseVal;
    owner.ownerDoc.setChanged();
  }

  public SVGNumberList getAnimVal() {
    if (animations == null) {
      return baseVal;
    } else {
      int numAnimations = animations.size();
      SVGNumberList result = null;
      for (int i = 0; i < numAnimations; i++) {
        SVGAnimationElementImpl animation = (SVGAnimationElementImpl)animations.elementAt(i);
        SVGNumberList animVal = (SVGNumberList)animation.getCurrentValue(ANIMTYPE_NUMBERLIST);
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