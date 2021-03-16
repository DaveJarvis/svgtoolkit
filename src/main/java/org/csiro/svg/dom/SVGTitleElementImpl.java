// SVGTitleElementImpl.java
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
// $Id: SVGTitleElementImpl.java,v 1.7 2000/09/26 14:10:35 dino Exp $
//

package org.csiro.svg.dom;

import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGTitleElement;

/**
 * SVGTitleElementImpl is the implementation of org.w3c.dom.svg.SVGTitleElement
 */
public class SVGTitleElementImpl extends SVGStylableImpl implements
  SVGTitleElement {

  public SVGTitleElementImpl(SVGDocumentImpl owner) {
    super(owner, "title");
  }

  public SVGTitleElementImpl(SVGDocumentImpl owner, Element elem) {
    super(owner, elem, "title");
  }

  public SVGElementImpl cloneElement() {
    SVGTitleElementImpl newTitle = new SVGTitleElementImpl(getOwnerDoc(), this);
    if (animatedProperties != null) {
      newTitle.animatedProperties = animatedProperties;
    }
    return newTitle;
  }


  // implementation of SVGLangSpace interface

  /**
   * Returns the value of this element's xml:lang attribute.
   * @return This element's xml:lang attribute.
   */
  public String getXMLlang() {
    return getAttribute("xml:lang");
  }

  /**
   * Sets the xml:lang attribute.
   * @param xmllang The value to use when setting the xml:lang attribute.
   */
  public void setXMLlang(String xmllang) {
    if (xmllang != null) {
      setAttribute("xml:lang", xmllang);
    } else {
      removeAttribute("xml:lang");
    }
  }

  /**
   * Returns the value of this element's xml:space attribute.
   * @return This element's xml:space attribute.
   */
  public String getXMLspace() {
    return getAttribute("xml:space");
  }

  /**
   * Sets the xml:space attribute.
   * @param xmlspace The value to use when setting the xml:space attribute.
   */
  public void setXMLspace(String xmlspace) {
    if (xmlspace != null) {
      setAttribute("xml:space", xmlspace);
    } else {
      removeAttribute("xml:space");
    }
  }

  public void attachAnimation(SVGAnimationElementImpl animation) {
    super.attachAnimation(animation);
  }
}
