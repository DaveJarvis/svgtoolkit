// SVGCursorElementImpl.java
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
// $Id: SVGCursorElementImpl.java,v 1.6 2001/05/09 01:34:49 bella Exp $
//

package org.csiro.svg.dom;

import org.w3c.dom.Element;
import org.w3c.dom.svg.*;

import java.util.Vector;
import java.awt.*;
import java.net.*;

public class SVGCursorElementImpl extends SVGElementImpl implements
  SVGCursorElement {

  protected SVGAnimatedLength x;
  protected SVGAnimatedLength y;
  protected SVGAnimatedString href;
  protected SVGAnimatedBoolean externalResourcesRequired;

  /**
   * Simple constructor
   */
  public  SVGCursorElementImpl(SVGDocumentImpl owner) {
    super(owner, "cursor");
  }

  /**
   * Constructor using an XML Parser
   */
  public  SVGCursorElementImpl(SVGDocumentImpl owner, Element elem) {
    super(owner, elem, "cursor");
  }

  public SVGElementImpl cloneElement() {
    SVGCursorElementImpl newCursor = new SVGCursorElementImpl(getOwnerDoc(), this);

    Vector xAnims = ((SVGAnimatedLengthImpl)getX()).getAnimations();
    Vector yAnims = ((SVGAnimatedLengthImpl)getY()).getAnimations();
    Vector hrefAnims = ((SVGAnimatedStringImpl)getHref()).getAnimations();
    Vector externalResourcesRequiredAnims = ((SVGAnimatedBooleanImpl)getExternalResourcesRequired()).getAnimations();

    if (xAnims != null) {
      for (int i = 0; i < xAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)xAnims.elementAt(i);
        newCursor.attachAnimation(anim);
      }
    }
    if (yAnims != null) {
      for (int i = 0; i < yAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)yAnims.elementAt(i);
        newCursor.attachAnimation(anim);
      }
    }
    if (hrefAnims != null) {
      for (int i = 0; i < hrefAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)hrefAnims.elementAt(i);
        newCursor.attachAnimation(anim);
      }
    }
    if (externalResourcesRequiredAnims != null) {
      for (int i = 0; i < externalResourcesRequiredAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)externalResourcesRequiredAnims.elementAt(i);
        newCursor.attachAnimation(anim);
      }
    }
    return newCursor;
  }

    public SVGAnimatedLength getX() {
    if (x == null) {
      x = new SVGAnimatedLengthImpl(new SVGLengthImpl(0, this, SVGLengthImpl.X_DIRECTION), this);
    }
    return x;
  }

  public SVGAnimatedLength getY() {
    if (y == null) {
      y = new SVGAnimatedLengthImpl(new SVGLengthImpl(0, this, SVGLengthImpl.Y_DIRECTION), this);
    }
    return y;
  }

  public String getXlinkType() {
    return getAttribute("xlink:type");
  }

  public void setXlinkType(String xlinkType) throws org.w3c.dom.DOMException {
    setAttribute("xlink:type", xlinkType);
  }

  public String getXlinkRole() {
    return getAttribute("xlink:role");
  }

  public void setXlinkRole(String xlinkRole) throws org.w3c.dom.DOMException {
    setAttribute("xlink:role", xlinkRole);
  }

  public String getXlinkArcRole() {
    return getAttribute("xlink:arcrole");
  }

  public void setXlinkArcRole(String xlinkArcRole) throws
    org.w3c.dom.DOMException {
    setAttribute("xlink:arcrole", xlinkArcRole);
  }

  public String getXlinkTitle() {
    return getAttribute("xlink:title");
  }

  public void setXlinkTitle(String xlinkTitle) throws
    org.w3c.dom.DOMException {
    setAttribute("xlink:title", xlinkTitle);
  }

  public String getXlinkShow() {
    return getAttribute("xlink:show");
  }

  public void setXlinkShow(String xlinkShow) throws org.w3c.dom.DOMException {
    setAttribute("xlink:show", xlinkShow);
  }

  public String getXlinkActuate() {
    return getAttribute("xlink:actuate");
  }

  public void setXlinkActuate(String xlinkActuate) throws
    org.w3c.dom.DOMException {
    setAttribute("xlink:actuate", xlinkActuate);
  }

  public SVGAnimatedString getHref() {
    if (href == null) {
      href = new SVGAnimatedStringImpl("", this);
    }
    return href;
  }


   // implementation of SVGTests interface

  protected SVGStringListImpl requiredFeatures;
  protected SVGStringListImpl requiredExtensions;
  protected SVGStringListImpl systemLanguage;

  public SVGStringList getRequiredFeatures() {
    return requiredFeatures;
  }

  public SVGStringList getRequiredExtensions() {
    return requiredExtensions;
  }

  public SVGStringList getSystemLanguage() {
    return systemLanguage;
  }

  // not sure if this does what it is supposed to
  public boolean hasExtension(String extension) {
    if (extension.equalsIgnoreCase("svg")) {
      return true;
    } else {
      return false;
    }
  }

  // implementation of SVGExternalResourcesRequired interface

  public SVGAnimatedBoolean getExternalResourcesRequired() {
    if (externalResourcesRequired == null) {
      externalResourcesRequired = new SVGAnimatedBooleanImpl(false, this);
    }
    return externalResourcesRequired;
  }


  public String getAttribute(String name) {
    if (name.equalsIgnoreCase("x")) {
      return getX().getBaseVal().getValueAsString();
    } else if (name.equalsIgnoreCase("y")) {
      return getY().getBaseVal().getValueAsString();
    } else if (name.equalsIgnoreCase("xlink:href")) {
      return getHref().getBaseVal();
    } else if (name.equalsIgnoreCase("externalResourcesRequired")) {
      if (getExternalResourcesRequired().getBaseVal()) {
        return "true";
      } else {
        return "false";
      }
    } else {
      return super.getAttribute(name);
    }
  }

  public org.w3c.dom.Attr getAttributeNode( String name) {
    org.w3c.dom.Attr attr = super.getAttributeNode( name);
    if (attr == null)
      return attr;
    if (name.equalsIgnoreCase("x")) {
      attr.setValue(getX().getBaseVal().getValueAsString());
    } else if (name.equalsIgnoreCase("y")) {
      attr.setValue(getY().getBaseVal().getValueAsString());
    } else if (name.equalsIgnoreCase("xlink:href")) {
      attr.setValue(getHref().getBaseVal());
    } else if (name.equalsIgnoreCase("externalResourcesRequired")) {
      if (getExternalResourcesRequired().getBaseVal()) {
        attr.setValue("true");
      } else {
        attr.setValue("false");
      }
    }
    return attr;
  }

  public void setAttribute(String name, String value) {
    super.setAttribute(name, value);
    setAttributeValue(name, value);
  }

  public org.w3c.dom.Attr setAttributeNode( org.w3c.dom.Attr newAttr) throws
    org.w3c.dom.DOMException {
    setAttributeValue(newAttr.getName(), newAttr.getValue());
    return super.setAttributeNode(newAttr);
  }

  private void setAttributeValue(String name, String value) {
    if (name.equalsIgnoreCase("x")) {
      getX().getBaseVal().setValueAsString(value);
    } else if (name.equalsIgnoreCase("y")) {
      getY().getBaseVal().setValueAsString(value);
    } else if (name.equalsIgnoreCase("xlink:href")) {
      getHref().setBaseVal(value);
    } else if (name.equalsIgnoreCase("externalResourcesRequired")) {
      if (value.equalsIgnoreCase("true")) {
        getExternalResourcesRequired().setBaseVal(true);
      } else {
        getExternalResourcesRequired().setBaseVal(false);
      }
    }
  }



  Cursor cursor = null;
  String oldHref = "";

  public Cursor getCursor() {

    String href = getHref().getAnimVal();
    String origHref = href;
    float animX = getX().getAnimVal().getValue();
    float animY = getY().getAnimVal().getValue();
    String id = getAttribute("id");

  //  System.out.println("in cursor.getCursor, href = " + href + ", " + animX + ", " + animY );
    if (href.length() > 0) {

      if (cursor != null && href.equals(oldHref)) {
        return cursor;
      }

      String svgDocPath = "";
      SVGDocument parentSvgDoc = (SVGDocument)getOwnerDocument();
      if (parentSvgDoc != null && parentSvgDoc.getURL() != null) {
        String docUrl = parentSvgDoc.getURL();
        // System.out.println("doc url = " + url);
        if ((docUrl.indexOf('/') != -1) || (docUrl.indexOf('\\') != -1)) {
          int index = docUrl.lastIndexOf('/');
          if (index == -1)
            index = docUrl.lastIndexOf('\\');
          svgDocPath = docUrl.substring(0,index+1);
        }
        //   System.out.println("path = " + svgDocPath);

        // absolute whopper of a conditional
        if (href != null && !href.startsWith("http://") &&
                    !href.startsWith("ftp://") && !href.startsWith("file:")
                    && svgDocPath != null
                    && (svgDocPath.startsWith("http://") ||
                        svgDocPath.startsWith("ftp://"))) {
            href = svgDocPath + href;
        }
          //  System.out.println("imageref = " + imageRef);
        Image image = null;

        if (href != null &&
              (href.startsWith("http://") ||
              href.startsWith("file:") ||
              href.startsWith("ftp://"))) {
          try {
            URL url = new URL(href);
            image = Toolkit.getDefaultToolkit().getImage(url);
          } catch (MalformedURLException e) {
            System.out.println("Bad URL in cursor element : " + href);
          }
        } else {
            //   System.out.println("trying to load image: " + svgDocPath + imageRef);
          image = Toolkit.getDefaultToolkit().getImage(svgDocPath + href);
        }
        if (image != null) {
          cursor = Toolkit.getDefaultToolkit().createCustomCursor(
							    image, new Point((int)animX,(int)animY), id);
          oldHref = origHref;
          return cursor;
        }
      }
    }
    return null;
  }

}