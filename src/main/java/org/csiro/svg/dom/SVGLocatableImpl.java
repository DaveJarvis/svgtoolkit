// SVGLocatableImpl.java
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
// $Id $
//

package org.csiro.svg.dom;

import org.w3c.dom.Element;
import org.w3c.dom.svg.*;

import java.awt.geom.*;

public abstract class SVGLocatableImpl extends SVGStylableImpl implements SVGLocatable {


  public SVGLocatableImpl( SVGDocumentImpl owner, Element elem, String name) {
    super(owner, elem, name);
  }

  public SVGLocatableImpl(SVGDocumentImpl owner, String name) {
    super(owner, name);
  }


  /**
   * Returns the element which established the current viewport. Often, the
   * nearest ancestor 'svg' element. Null if this element is the
   * outermost 'svg' element.
   * @return The element which established the current viewport.
   */
  public SVGElement getNearestViewportElement() {
    return getViewportElement();
  }

  /**
   * Returns the farthest ancestor 'svg' element. Null if this element is the
   * outermost 'svg' element.
   * @return The farthest ancestor 'svg' element.
   */
  public SVGElement getFarthestViewportElement() {
    SVGSVGElement root = getOwnerDoc().getRootElement();
    if (root != this) {
      return root;
    } else {
      return null;
    }
  }


  public SVGMatrix getCTM() {

    // create SVGMatrix that represents this element's transform
    SVGMatrix thisMatrix = new SVGMatrixImpl();
    if (this instanceof SVGTransformable ) {
      SVGTransform consolidate = ((SVGTransformable)this).getTransform().getAnimVal().consolidate();
      if (consolidate != null) {
        thisMatrix = new SVGMatrixImpl((SVGMatrixImpl)consolidate.getMatrix());
      }
    }

    // find nearest ancestor that is either transformable or the viewport element
    org.w3c.dom.Node parent = getParentNode();
    while (parent != null && parent != (org.w3c.dom.Node)getViewportElement()
            && !(parent instanceof SVGTransformable)) {
      parent = parent.getParentNode();
    }

    if (parent instanceof SVGTransformable) {
      // found a parent that is transformable
      // ctm = parent.getCTM() x thisMatix
      SVGMatrix parentMatrix = ((SVGTransformable)parent).getCTM();
      return parentMatrix.multiply(thisMatrix);

    } else {  // (parent == null || parent == (Node)getViewportElement())
      return thisMatrix;
    }
  }

  public SVGMatrix getScreenCTM() {

    AffineTransform screenCTM = ((SVGMatrixImpl)getCTM()).getAffineTransform();
    SVGSVGElement root = getOwnerDoc().getRootElement();
    SVGSVGElementImpl parentSVGElement = (SVGSVGElementImpl)getOwnerSVGElement();

    if (parentSVGElement != null) {

      AffineTransform viewboxToViewportTransform = parentSVGElement.getViewboxToViewportTransform();
      if (viewboxToViewportTransform != null) {
        screenCTM.preConcatenate(viewboxToViewportTransform);
      }

      screenCTM.preConcatenate(AffineTransform.getTranslateInstance(
                           parentSVGElement.getX().getAnimVal().getValue(),
                           parentSVGElement.getY().getAnimVal().getValue()));
    //}

     while (parentSVGElement != root) {

      if (parentSVGElement.getParentNode() instanceof SVGLocatable
          && !(parentSVGElement.getParentNode() instanceof SVGSVGElementImpl)) {
        SVGMatrix ctmMatrix = ((SVGLocatable)parentSVGElement.getParentNode()).getCTM();
        AffineTransform ctm = ((SVGMatrixImpl)ctmMatrix).getAffineTransform();
        screenCTM.preConcatenate(ctm);
        parentSVGElement = (SVGSVGElementImpl)((SVGElement)parentSVGElement.getParentNode()).getOwnerSVGElement();

      } else if (parentSVGElement.getParentNode() instanceof SVGSVGElementImpl) {
        parentSVGElement = (SVGSVGElementImpl)parentSVGElement.getParentNode();
      } else {
        parentSVGElement = null;
      }


      if (parentSVGElement != null) {
       // AffineTransform viewboxToViewportTransform = parentSVGElement.getViewboxToViewportTransform();
         viewboxToViewportTransform = parentSVGElement.getViewboxToViewportTransform();

        if (viewboxToViewportTransform != null) {
          screenCTM.preConcatenate(viewboxToViewportTransform);
        }

        screenCTM.preConcatenate(AffineTransform.getTranslateInstance(
                             parentSVGElement.getX().getAnimVal().getValue(),
                             parentSVGElement.getY().getAnimVal().getValue()));
      }
     }
    }

    SVGMatrix screenCTMMatrix = new SVGMatrixImpl();
    screenCTMMatrix.setA((float)screenCTM.getScaleX());
    screenCTMMatrix.setB((float)screenCTM.getShearY());
    screenCTMMatrix.setC((float)screenCTM.getShearX());
    screenCTMMatrix.setD((float)screenCTM.getScaleY());
    screenCTMMatrix.setE((float)screenCTM.getTranslateX());
    screenCTMMatrix.setF((float)screenCTM.getTranslateY());
    return screenCTMMatrix;
  }

  public SVGMatrix getTransformToElement (SVGElement element) throws SVGException {
    SVGMatrix thisScreenCTM = getScreenCTM();
    SVGMatrix elemScreenCTM = null;
    if (element instanceof SVGLocatable) {
      elemScreenCTM = ((SVGLocatable)element).getScreenCTM();
    } else {
      org.w3c.dom.Node parent = element.getParentNode();
      while (parent != null && !(parent instanceof SVGLocatable)) {
        parent = parent.getParentNode();
      }
      if (parent == null || !(parent instanceof SVGLocatable)) {
        elemScreenCTM = new SVGMatrixImpl();
      } else {
        elemScreenCTM = ((SVGLocatable)parent).getScreenCTM();
      }
    }
    SVGMatrix transform = elemScreenCTM.inverse().multiply(thisScreenCTM);
    return transform;
  }

  public String getAttribute(String name) {
    return super.getAttribute(name);
  }

  public org.w3c.dom.Attr getAttributeNode( String name) {
    org.w3c.dom.Attr attr = super.getAttributeNode( name);
    return attr;
  }

  public void setAttribute(String name, String value) {
    super.setAttribute(name, value);
  }

  public org.w3c.dom.Attr setAttributeNode( org.w3c.dom.Attr newAttr) throws
    org.w3c.dom.DOMException {
    return super.setAttributeNode(newAttr);
  }


}