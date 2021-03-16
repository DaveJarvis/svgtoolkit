// SVGTransformableImpl.java
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
// $Id: SVGTransformableImpl.java,v 1.15 2000/11/22 05:00:16 bella Exp $
//

package org.csiro.svg.dom;

import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGAnimatedTransformList;
import org.w3c.dom.svg.SVGTransformable;

// note: all transformables are also stylable

public abstract class SVGTransformableImpl extends SVGLocatableImpl implements
  SVGTransformable {

  public SVGTransformableImpl( SVGDocumentImpl owner, Element elem, String name) {
    super(owner, elem, name);
  }

  public SVGTransformableImpl(SVGDocumentImpl owner, String name) {
    super(owner, name);
    super.setAttribute("transform", "");
  }

  // implementation of SVGTransformable interface

  protected SVGAnimatedTransformList transform;


  /**
   * Returns the transform list that corresponds to this element's transform
   * attribute.
   * @return This element's transform.
   */
  public SVGAnimatedTransformList getTransform() {
    if (transform == null) {
      transform = new SVGAnimatedTransformListImpl(new SVGTransformListImpl(), this);
    }
    return transform;
  }


  public String getAttribute(String name) {
    if (name.equalsIgnoreCase("transform")) {
       return ((SVGTransformListImpl)getTransform().getBaseVal()).toString();
    } else {
      return super.getAttribute(name);
    }
  }

  public org.w3c.dom.Attr getAttributeNode( String name) {
    org.w3c.dom.Attr attr = super.getAttributeNode( name);
    if (attr == null)
      return attr;
    if (name.equalsIgnoreCase("transform")) {
      attr.setValue(((SVGTransformListImpl)getTransform().getBaseVal()).toString());
    }
    return attr;
  }

  public void setAttribute(String name, String value) {
    super.setAttribute(name, value);
    if (name.equalsIgnoreCase("transform")) {
      ((SVGAnimatedTransformListImpl)getTransform()).setBaseVal(SVGTransformListImpl.createTransformList(value));
    }
  }

  public org.w3c.dom.Attr setAttributeNode( org.w3c.dom.Attr newAttr) throws
    org.w3c.dom.DOMException {
    String name = newAttr.getName();
    String value = newAttr.getValue();
    if (name.equalsIgnoreCase("transform")) {
      ((SVGAnimatedTransformListImpl)getTransform()).setBaseVal(SVGTransformListImpl.createTransformList(value));
    }
    return super.setAttributeNode(newAttr);
  }

  public void attachAnimation(SVGAnimationElementImpl animation) {
    String attName = animation.getAttributeName();
    if (attName.equals("transform")) {
      ((SVGAnimatedValue)getTransform()).addAnimation(animation);
    } else if ((attName.equals("")) && (animation instanceof SVGAnimateMotionElementImpl)) {
      ((SVGAnimatedValue)getTransform()).addAnimation(animation);
    }
    else  {
      super.attachAnimation(animation);
    }
  }
}
