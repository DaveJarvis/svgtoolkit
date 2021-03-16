// SVGViewElementImpl.java
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
// $Id: SVGViewElementImpl.java,v 1.7 2002/02/21 07:46:09 brett Exp $
//

package org.csiro.svg.dom;

import org.w3c.dom.Element;
import org.w3c.dom.svg.*;

import java.util.Vector;
import java.util.StringTokenizer;

public class SVGViewElementImpl extends SVGElementImpl implements SVGViewElement {

  protected SVGAnimatedRect viewBox;
  protected SVGAnimatedPreserveAspectRatio preserveAspectRatio;
  protected SVGAnimatedBoolean externalResourcesRequired;

  /**
   * Simple constructor
   */
  public SVGViewElementImpl(SVGDocumentImpl owner) {
    super(owner, "view");
  }

  /**
   * Constructor using an XML Parser
   */
  public SVGViewElementImpl(SVGDocumentImpl owner, Element elem) {
    super(owner, elem, "view");
  }

  public SVGElementImpl cloneElement() {

    SVGViewElementImpl newView = new SVGViewElementImpl(getOwnerDoc(), this);

    Vector viewBoxAnims = ((SVGAnimatedRectImpl)getViewBox()).getAnimations();
    Vector preserveAspectRatioAnims = ((SVGAnimatedPreserveAspectRatioImpl)getPreserveAspectRatio()).getAnimations();
    Vector externalResourcesRequiredAnims = ((SVGAnimatedBooleanImpl)getExternalResourcesRequired()).getAnimations();


    if (viewBoxAnims != null) {
      for (int i = 0; i < viewBoxAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)viewBoxAnims.elementAt(i);
        newView.attachAnimation(anim);
      }
    }
    if (preserveAspectRatioAnims != null) {
      for (int i = 0; i < preserveAspectRatioAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)preserveAspectRatioAnims.elementAt(i);
        newView.attachAnimation(anim);
      }
    }
    if (externalResourcesRequiredAnims != null) {
      for (int i = 0; i < externalResourcesRequiredAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)externalResourcesRequiredAnims.elementAt(i);
        newView.attachAnimation(anim);
      }
    }

    return newView;

  }

   // implementation of SVGZoomAndPan interface

  public short getZoomAndPan() {
    String zoomAndPan = getAttribute("zoomAndPan");
    if (zoomAndPan.equals("disable")) {
      return SVG_ZOOMANDPAN_DISABLE;
    } else if (zoomAndPan.equals("magnify")) {
      return SVG_ZOOMANDPAN_MAGNIFY;
    } else {
      return SVG_ZOOMANDPAN_MAGNIFY; // default value
    }
  }

  public void setZoomAndPan(short zoomAndPan) {
    if (zoomAndPan == SVG_ZOOMANDPAN_DISABLE) {
      setAttribute("zoomAndPan", "disable");
    } else if (zoomAndPan == SVG_ZOOMANDPAN_MAGNIFY) {
      setAttribute("zoomAndPan", "magnify");
    } else {
      System.out.println("bad zoomAndPan value: " + zoomAndPan + ", setting to default value 'magnify'");
      setAttribute("zoomAndPan", "magnify");
    }
  }

   // implementation of SVGFitToViewBox interface

  public SVGAnimatedRect getViewBox() {
    if (viewBox == null) {
      viewBox = new SVGAnimatedRectImpl(new SVGRectImpl(), this);
    }
    return viewBox;
  }

  public SVGAnimatedPreserveAspectRatio getPreserveAspectRatio() {
    if (preserveAspectRatio == null) {
      preserveAspectRatio = new SVGAnimatedPreserveAspectRatioImpl(new SVGPreserveAspectRatioImpl(), this);
    }
    return preserveAspectRatio;
  }

  // implementation of SVGExternalResourcesRequired interface


  public SVGAnimatedBoolean getExternalResourcesRequired() {
   if (externalResourcesRequired == null) {
      externalResourcesRequired = new SVGAnimatedBooleanImpl(false, this);
    }
    return externalResourcesRequired;
  }


  public SVGStringList getViewTarget() {
    SVGStringListImpl targetNames = new SVGStringListImpl(getAttribute("viewTarget"));
    return targetNames;
  }

  public void setViewTarget( SVGElement viewTarget) throws
    org.w3c.dom.DOMException {
    setAttribute("viewTarget", viewTarget.getId());
  }

  public String getAttribute(String name) {

    if (name.equalsIgnoreCase("viewBox")) {
      if (getViewBox().getBaseVal().getWidth() == 0) {
        return "";
      } else {
        return ((SVGRectImpl)getViewBox().getBaseVal()).toString();
      }

    } else if (name.equalsIgnoreCase("preserveAspectRatio")) {
      return ((SVGPreserveAspectRatioImpl)getPreserveAspectRatio().getBaseVal()).toString();

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
    if (name.equalsIgnoreCase("viewBox")) {
      if (getViewBox().getBaseVal().getWidth() == 0) {
        attr.setValue("");
      } else {
        attr.setValue(((SVGRectImpl)getViewBox().getBaseVal()).toString());
      }
    } else if (name.equalsIgnoreCase("preserveAspectRatio")) {
      attr.setValue(((SVGPreserveAspectRatioImpl)getPreserveAspectRatio().getBaseVal()).toString());

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
    if (name.equalsIgnoreCase("viewBox")) {
      StringTokenizer st = new StringTokenizer(value, ", ");
      if (st.countTokens() == 4) {
        getViewBox().getBaseVal().setX(Float.parseFloat(st.nextToken()));
        getViewBox().getBaseVal().setY(Float.parseFloat(st.nextToken()));
        getViewBox().getBaseVal().setWidth(Float.parseFloat(st.nextToken()));
        getViewBox().getBaseVal().setHeight(Float.parseFloat(st.nextToken()));
      }

    } else if (name.equalsIgnoreCase("preserveAspectRatio")) {
      StringTokenizer preserveST = new StringTokenizer(value, ", ");
      String align = null;
      String meetOrSlice = null;
      int tokenCount = preserveST.countTokens();
      if (tokenCount > 0) {
        align = preserveST.nextToken();
        if (tokenCount > 1) {
          meetOrSlice = preserveST.nextToken();
        }
      }
      if (align != null) {
        short alignConst = SVGPreserveAspectRatioImpl.getAlignConst(align);
        getPreserveAspectRatio().getBaseVal().setAlign(alignConst);
      }
      if (meetOrSlice != null) {
        short meetOrSliceConst = SVGPreserveAspectRatioImpl.getMeetOrSliceConst(meetOrSlice);
        getPreserveAspectRatio().getBaseVal().setMeetOrSlice(meetOrSliceConst);
      }

    } else if (name.equalsIgnoreCase("externalResourcesRequired")) {
      if (value.equalsIgnoreCase("true")) {
        getExternalResourcesRequired().setBaseVal(true);
      } else {
        getExternalResourcesRequired().setBaseVal(false);
      }
    }
  }

}