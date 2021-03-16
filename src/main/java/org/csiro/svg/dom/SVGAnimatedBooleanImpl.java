// SVGAnimatedBooleanImpl.java
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
// $Id: SVGAnimatedBooleanImpl.java,v 1.6 2000/11/30 01:34:17 bella Exp $
//

package org.csiro.svg.dom;

import org.w3c.dom.DOMException;
import org.w3c.dom.svg.SVGAnimatedBoolean;

/**
 * SVGAnimatedBooleanImpl is the implementation of org.w3c.dom.svg.SVGAnimatedBoolean
 */
public class SVGAnimatedBooleanImpl extends SVGAnimatedValue implements
  SVGAnimatedBoolean {

  private boolean baseVal;

  public SVGAnimatedBooleanImpl(boolean baseVal, SVGElementImpl owner) {
    this.owner = owner;
    this.baseVal = baseVal;
  }

  public boolean getBaseVal() {
    return baseVal;
  }

  public void setBaseVal(boolean baseVal) throws DOMException {
    this.baseVal = baseVal;
    owner.ownerDoc.setChanged();
  }

  public boolean getAnimVal() {
    if (animations == null) {
      return baseVal;
    } else {
      int numAnimations = animations.size();
      Boolean result = null;
      for (int i = 0; i < numAnimations; i++) {
        SVGAnimationElementImpl animation = (SVGAnimationElementImpl)animations.elementAt(i);
        Boolean animVal = (Boolean)animation.getCurrentValue(ANIMTYPE_BOOLEAN);
        if (animVal != null) {
          result = animVal;
          break;
        }
      }
      if (result != null) {
        return result.booleanValue();
      } else { // the animation element couldn't determine a value
        return baseVal;
      }
    }
  }
}