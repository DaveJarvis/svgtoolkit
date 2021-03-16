// SVGLengthListImpl.java
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
// $Id: SVGLengthListImpl.java,v 1.11 2000/11/21 05:57:00 bella Exp $
//

package org.csiro.svg.dom;

import org.w3c.dom.DOMException;
import org.w3c.dom.svg.SVGException;
import org.w3c.dom.svg.SVGLength;
import org.w3c.dom.svg.SVGLengthList;


/**
 * SVGLengthListImpl is the implementation of org.w3c.dom.svg.SVGLengthList
 */
public class SVGLengthListImpl extends SVGListImpl implements SVGLengthList {

  /**
   * Constructs a new empty SVGLengthListImpl.
   */
  public SVGLengthListImpl() {
  }

  public SVGLength initialize ( SVGLength newItem)
                  throws org.w3c.dom.DOMException, SVGException {
    return (SVGLength)super.initialize(newItem);
  }

  public SVGLength getItem ( int index )
                  throws org.w3c.dom.DOMException {
    return (SVGLength)super.getItemAt(index);
  }

  public SVGLength insertItemBefore ( SVGLength newItem, int index )
                  throws org.w3c.dom.DOMException, SVGException {
    return (SVGLength)super.insertItemBefore(newItem, index);
  }

  public SVGLength replaceItem ( SVGLength newItem, int index )
                  throws org.w3c.dom.DOMException, SVGException {
    return (SVGLength)super.replaceItem(newItem, index);
  }

  public SVGLength removeItem ( int index )
                  throws org.w3c.dom.DOMException {
    return (SVGLength)super.removeItemAt(index);
  }

  public SVGLength appendItem ( SVGLength newItem )
                  throws DOMException, SVGException {
    return (SVGLength)super.appendItem(newItem);
  }


  /**
   * Checks that the item is the correct type for the given list.
   * @param item The item to check.
   * @exception SVGException
   *     Raised if the item is the wrong type of object for the given list.
   */
  protected void checkItemType(Object item) throws SVGException {
    if (!(item instanceof SVGLengthImpl)) {
       throw new SVGExceptionImpl(SVGException.SVG_WRONG_TYPE_ERR,
                "Wrong item type for this list. Was expecting SVGLengthImpl.");
    }
  }

  public String toString() {
    String lengthString = "";
    long numLengths = getNumberOfItems();
    for (int i = 0; i < numLengths; i++) {
      SVGLength length = (SVGLength)getItem(i);
      lengthString += length.getValueAsString();
      if (i != numLengths-1) {
        lengthString += ", ";
      }
    }
    return lengthString;
  }

} // SVGLengthListImpl

