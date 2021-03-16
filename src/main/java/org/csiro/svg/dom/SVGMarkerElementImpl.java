// SVGMarkerElementImpl.java
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
// $Id: SVGMarkerElementImpl.java,v 1.24 2002/03/11 01:00:52 bella Exp $
//

package org.csiro.svg.dom;

import java.util.Vector;
import java.awt.*;
import java.util.StringTokenizer;

import org.w3c.dom.Element;
import org.w3c.dom.svg.*;

/**
 * SVGMarkerElementImpl is the implementation of org.w3c.dom.svg.SVGMarkerElement
 */
public class SVGMarkerElementImpl extends SVGStylableImpl implements
  SVGMarkerElement {

  protected SVGAnimatedLength refX;
  protected SVGAnimatedLength refY;
  protected SVGAnimatedEnumeration markerUnits;
  protected SVGAnimatedLength markerWidth;
  protected SVGAnimatedLength markerHeight;
  protected SVGAnimatedEnumeration orientType;
  protected SVGAnimatedAngle orientAngle;
  protected SVGAnimatedRect viewBox;
  protected SVGAnimatedPreserveAspectRatio preserveAspectRatio;
  protected SVGAnimatedBoolean externalResourcesRequired;

  private static Vector markerUnitStrings;
  private static Vector markerUnitValues;
  private static Vector orientTypeStrings;
  private static Vector orientTypeValues;

  public SVGMarkerElementImpl(SVGDocumentImpl owner) {
    super(owner, "marker");

    // setup initial attributes with some defaults
    super.setAttribute("refX", getRefX().getBaseVal().getValueAsString());
    super.setAttribute("refY", getRefY().getBaseVal().getValueAsString());
    super.setAttribute("markerUnits", "strokeWidth");
    super.setAttribute("markerWidth", getMarkerWidth().getBaseVal().getValueAsString());
    super.setAttribute("markerHeight", getMarkerHeight().getBaseVal().getValueAsString());
    super.setAttribute("orient", "0");
    super.setAttribute("preserveAspectRatio", getPreserveAspectRatio().getBaseVal()
                                                                      .toString());
  }

  public SVGMarkerElementImpl(SVGDocumentImpl owner, Element elem) {
    super(owner, elem, "marker");
  }

  public SVGElementImpl cloneElement() {
    SVGMarkerElementImpl newMarker = new SVGMarkerElementImpl(getOwnerDoc(), this);

    Vector refXAnims = ((SVGAnimatedLengthImpl)getRefX()).getAnimations();
    Vector refYAnims = ((SVGAnimatedLengthImpl)getRefY()).getAnimations();
    Vector markerUnitsAnims = ((SVGAnimatedEnumerationImpl)getMarkerUnits()).getAnimations();
    Vector markerWidthAnims = ((SVGAnimatedLengthImpl)getMarkerWidth()).getAnimations();
    Vector markerHeightAnims = ((SVGAnimatedLengthImpl)getMarkerHeight()).getAnimations();
    Vector orientTypeAnims = ((SVGAnimatedEnumerationImpl)getOrientType()).getAnimations();
    Vector orientAngleAnims = ((SVGAnimatedAngleImpl)getOrientAngle()).getAnimations();
    Vector viewBoxAnims = ((SVGAnimatedRectImpl)getViewBox()).getAnimations();
    Vector preserveAspectRatioAnims = ((SVGAnimatedPreserveAspectRatioImpl)getPreserveAspectRatio()).getAnimations();
    Vector externalResourcesRequiredAnims = ((SVGAnimatedBooleanImpl)getExternalResourcesRequired()).getAnimations();

    if (refXAnims != null) {
      for (int i = 0; i < refXAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)refXAnims.elementAt(i);
        newMarker.attachAnimation(anim);
      }
    }
    if (refYAnims != null) {
      for (int i = 0; i < refYAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)refYAnims.elementAt(i);
        newMarker.attachAnimation(anim);
      }
    }
    if (markerUnitsAnims != null) {
      for (int i = 0; i < markerUnitsAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)markerUnitsAnims.elementAt(i);
        newMarker.attachAnimation(anim);
      }
    }
    if (markerWidthAnims != null) {
      for (int i = 0; i < markerWidthAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)markerWidthAnims.elementAt(i);
        newMarker.attachAnimation(anim);
      }
    }
    if (markerHeightAnims != null) {
      for (int i = 0; i < markerHeightAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)markerHeightAnims.elementAt(i);
        newMarker.attachAnimation(anim);
      }
    }
    if (orientTypeAnims != null) {
      for (int i = 0; i < orientTypeAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)orientTypeAnims.elementAt(i);
        newMarker.attachAnimation(anim);
      }
    }
    if (orientAngleAnims != null) {
      for (int i = 0; i < orientAngleAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)orientAngleAnims.elementAt(i);
        newMarker.attachAnimation(anim);
      }
    }
    if (viewBoxAnims != null) {
      for (int i = 0; i < viewBoxAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)viewBoxAnims.elementAt(i);
        newMarker.attachAnimation(anim);
      }
    }
    if (preserveAspectRatioAnims != null) {
      for (int i = 0; i < preserveAspectRatioAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)preserveAspectRatioAnims.elementAt(i);
        newMarker.attachAnimation(anim);
      }
    }
    if (externalResourcesRequiredAnims != null) {
      for (int i = 0; i < externalResourcesRequiredAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)externalResourcesRequiredAnims.elementAt(i);
        newMarker.attachAnimation(anim);
      }
    }
    if (animatedProperties != null) {
      newMarker.animatedProperties = animatedProperties;
    }
    return newMarker;
  }

  private void initMarkerUnitVectors() {
    if (markerUnitStrings == null) {
      markerUnitStrings = new Vector();
      markerUnitStrings.addElement("userSpaceOnUse");
      markerUnitStrings.addElement("strokeWidth");
    }
    if (markerUnitValues == null) {
       markerUnitValues = new Vector();
       markerUnitValues.addElement(new Short(SVG_MARKERUNITS_USERSPACEONUSE));
       markerUnitValues.addElement(new Short(SVG_MARKERUNITS_STROKEWIDTH));
       markerUnitValues.addElement(new Short(SVG_MARKERUNITS_UNKNOWN));
    }
  }

  private void initOrientTypeVectors() {
    if (orientTypeStrings == null) {
      orientTypeStrings = new Vector();
      orientTypeStrings.addElement("auto");
    }
    if (orientTypeValues == null) {
      orientTypeValues = new Vector();
      orientTypeValues.addElement(new Short(SVG_MARKER_ORIENT_AUTO));
      orientTypeValues.addElement(new Short(SVG_MARKER_ORIENT_ANGLE));
    }
  }

  /**
   * Corresponds to attribute refX on the given 'marker' element.
   */
  public SVGAnimatedLength getRefX() {
    if (refX == null) {
      refX = new SVGAnimatedLengthImpl(new SVGLengthImpl(0, this, SVGLengthImpl.X_DIRECTION), this);
    }
    return refX;
  }

  /**
   * Corresponds to attribute refY on the given 'marker' element.
   */
  public SVGAnimatedLength getRefY() {
    if (refY == null) {
      refY = new SVGAnimatedLengthImpl(new SVGLengthImpl(0, this, SVGLengthImpl.Y_DIRECTION), this);
    }
    return refY;
  }

  /**
   * Returns the value of the markerUnits attribute.
   */
  public SVGAnimatedEnumeration getMarkerUnits() {
    if (markerUnits == null) {
      if (markerUnitStrings == null) {
        initMarkerUnitVectors();
      }
      markerUnits = new SVGAnimatedEnumerationImpl(SVG_MARKERUNITS_STROKEWIDTH, this, markerUnitStrings, markerUnitValues);
    }
    return markerUnits;
  }

  /**
   * Corresponds to attribute markerWidth on the given 'marker' element.
   */
  public SVGAnimatedLength getMarkerWidth() {
    if (markerWidth == null) {
      markerWidth = new SVGAnimatedLengthImpl(new SVGLengthImpl(3, this, SVGLengthImpl.X_DIRECTION), this);
    }
    return markerWidth;
  }

  /**
   * Corresponds to attribute markerHeight on the given 'marker' element.
   */
  public SVGAnimatedLength getMarkerHeight() {
    if (markerHeight == null) {
      markerHeight = new SVGAnimatedLengthImpl(new SVGLengthImpl(3, this, SVGLengthImpl.Y_DIRECTION), this);
    }
    return markerHeight;
  }

  /**
   * Returns the orientation type of this marker. Should be either auto or
   * angle.
   */
  public SVGAnimatedEnumeration getOrientType() {
    if (orientType == null) {
      if (orientTypeStrings == null) {
        initOrientTypeVectors();
      }
      orientType = new SVGAnimatedEnumerationImpl(SVG_MARKER_ORIENT_ANGLE, this, orientTypeStrings, orientTypeValues);
    }
    return orientType;
  }

  /**
   * Corresponds to attribute orient on the given 'marker' element.
   * If the orient type is SVG_MARKER_ORIENT_ANGLE, the angle value for attribute
   * orient; otherwise, it will be set to zero.
   */
  public SVGAnimatedAngle getOrientAngle() {
    if (orientAngle == null) {
       orientAngle = new SVGAnimatedAngleImpl(new SVGAngleImpl(), this);
    }
    return orientAngle;
  }

  /**
   * Sets the value of attribute orient to 'auto'.
   */
  public void setOrientToAuto() {
    getOrientType().setBaseVal(SVG_MARKER_ORIENT_AUTO);
    ((SVGAnimatedAngleImpl)getOrientAngle()).setBaseVal(new SVGAngleImpl());
    super.setAttribute("orient", "auto");
  }

  /**
   * Sets the value of attribute orient to the given angle.
   * @param angle The angle value to use for attribute orient.
   */
  public void setOrientToAngle (SVGAngle angle)  {
    getOrientType().setBaseVal(SVG_MARKER_ORIENT_ANGLE);
    ((SVGAnimatedAngleImpl)getOrientAngle()).setBaseVal(angle);
    super.setAttribute("orient", angle.getValueAsString());
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
      super.setAttribute("xml:lang", xmllang);
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
      super.setAttribute("xml:space", xmlspace);
    } else {
      removeAttribute("xml:space");
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


  public String getAttribute(String name) {

    if (name.equalsIgnoreCase("orient")) {
      if (getOrientType().getBaseVal() == SVG_MARKER_ORIENT_AUTO) {
        return "auto";
      } else if (getOrientType().getBaseVal() == SVG_MARKER_ORIENT_ANGLE) {
        return getOrientAngle().getBaseVal().getValueAsString();
      } else {
        return "";
      }

    } else if (name.equalsIgnoreCase("markerUnits")) {
     if (getMarkerUnits().getBaseVal() == this.SVG_MARKERUNITS_USERSPACEONUSE) {
        return "userSpaceOnUse";
      } else {
        return "strokeWidth";
      }

    } else if (name.equalsIgnoreCase("refX")) {
      return getRefX().getBaseVal().getValueAsString();

    } else if (name.equalsIgnoreCase("refY")) {
      return getRefY().getBaseVal().getValueAsString();

    } else if (name.equalsIgnoreCase("markerWidth")) {
      return getMarkerWidth().getBaseVal().getValueAsString();

    } else if (name.equalsIgnoreCase("markerHeight")) {
      return getMarkerHeight().getBaseVal().getValueAsString();

    } else if (name.equalsIgnoreCase("viewBox")) {
      if (getViewBox().getBaseVal() == null) {
        return "";
      } else {
        return getViewBox().getBaseVal().toString();
      }

    } else if (name.equalsIgnoreCase("preserveAspectRatio")) {
      return getPreserveAspectRatio().getBaseVal().toString();

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

    if (name.equalsIgnoreCase("orient")) {
      if (getOrientType().getBaseVal() == SVG_MARKER_ORIENT_AUTO) {
        attr.setValue("auto");
      } else if (getOrientType().getBaseVal() == SVG_MARKER_ORIENT_ANGLE) {
        attr.setValue(getOrientAngle().getBaseVal().getValueAsString());
      }

    } else if (name.equalsIgnoreCase("markerUnits")) {
      if (getMarkerUnits().getBaseVal() == this.SVG_MARKERUNITS_USERSPACEONUSE) {
        attr.setValue("userSpaceOnUse");
      } else {
        attr.setValue("strokeWidth");
      }

    } else if (name.equalsIgnoreCase("refX")) {
      attr.setValue(getRefX().getBaseVal().getValueAsString());

    } else if (name.equalsIgnoreCase("refY")) {
      attr.setValue(getRefY().getBaseVal().getValueAsString());

    } else if (name.equalsIgnoreCase("markerWidth")) {
      attr.setValue(getMarkerWidth().getBaseVal().getValueAsString());

    } else if (name.equalsIgnoreCase("markerHeight")) {
      attr.setValue(getMarkerHeight().getBaseVal().getValueAsString());

    } else if (name.equalsIgnoreCase("viewBox")) {
      if (getViewBox().getBaseVal() == null) {
        attr.setValue("");
      } else {
        attr.setValue(getViewBox().getBaseVal().toString());
      }

    } else if (name.equalsIgnoreCase("preserveAspectRatio")) {
      attr.setValue(getPreserveAspectRatio().getBaseVal().toString());

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
    if (name.equalsIgnoreCase("orient")) {
      if (value.equalsIgnoreCase("auto")) {
        getOrientType().setBaseVal(SVG_MARKER_ORIENT_AUTO);
        getOrientAngle().getBaseVal().setValue(0);
      } else { // should be an angle
        getOrientType().setBaseVal(SVG_MARKER_ORIENT_ANGLE);
        getOrientAngle().getBaseVal().setValueAsString(value);
      }

    } else if (name.equalsIgnoreCase("refX")) {
      getRefX().getBaseVal().setValueAsString(value);

    } else if (name.equalsIgnoreCase("refY")) {
      getRefY().getBaseVal().setValueAsString(value);

    } else if (name.equalsIgnoreCase("markerWidth")) {
      getMarkerWidth().getBaseVal().setValueAsString(value);

    } else if (name.equalsIgnoreCase("markerHeight")) {
      getMarkerHeight().getBaseVal().setValueAsString(value);

    } else if (name.equalsIgnoreCase("markerUnits")) {
      if (value.equalsIgnoreCase("strokeWidth")) {
        getMarkerUnits().setBaseVal(SVG_MARKERUNITS_STROKEWIDTH);
      } else if (value.equalsIgnoreCase("userSpaceOnUse")) {
        getMarkerUnits().setBaseVal(SVG_MARKERUNITS_USERSPACEONUSE);
      } else {
        System.out.println("bad markerUnits attribute value " + value
                             + ", setting to default value 'strokeWidth'");
        getMarkerUnits().setBaseVal(SVG_MARKERUNITS_STROKEWIDTH);
      }

    } else if (name.equalsIgnoreCase("viewBox")) {

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
      getExternalResourcesRequired().setBaseVal( value.equalsIgnoreCase( "true" ) );
    }
  }




   SVGMarkerElementImpl clonedMarker = null;
   String styleText = "";

   public void drawMarker(Graphics2D graphics, SVGElementImpl element, float x, float y,
                           float angle, float strokeWidth, boolean refreshData) {

    // creates temporary svg structure that looks like:
    //
    //   <parent>
    //     <g transform="translate(x,y) rotate(angle) scale(strokeWidth)
    //                    scale(markerWidth/viewBoxWidth, markerHeight/viewBoxHeight)
    //                    translate(-refX, -refY)"
    //        style="marker's style">
    //       <all drawable marker children>
    //     <g>
    //   <parent>

    if (refreshData || clonedMarker == null) {

      refreshData();  // tells all of the stylable stuff to refresh any cached data

      clonedMarker = (SVGMarkerElementImpl)cloneElement();
      styleText = getAttribute("style");
      if (!styleText.endsWith(";")) {
        styleText += ";";
      }
      if (styleText.indexOf("marker-start") == -1 || getAttribute("marker-start").length() == 0) {
         styleText += "marker-start:none;";
      }
      if (styleText.indexOf("marker-mid") == -1 || getAttribute("marker-mid").length() == 0) {
        styleText += "marker-mid:none;";
      }
      if (styleText.indexOf("marker-end") == -1 || getAttribute("marker-end").length() == 0) {
        styleText += "marker-end:none;";
      }
      if (styleText.indexOf("marker:") == -1 || getAttribute("marker").length() == 0) {
        styleText += "marker:none;";
      }
    }

    if (clonedMarker != null) {

      SVGElementImpl parent = (SVGElementImpl)getParentNode();

      SVGGElementImpl gElement = new SVGGElementImpl(parent.getOwnerDoc());

      parent.appendChild(gElement);

      gElement.copyAttributes(this);
      gElement.setAttribute("style", styleText);
      gElement.animatedProperties = clonedMarker.animatedProperties;

      // append the marker children to the g2Element
      Vector drawableChildren = new Vector();
      if (clonedMarker.hasChildNodes()) {
        org.w3c.dom.NodeList children = clonedMarker.getChildNodes();
        int numChildren = children.getLength();
        for ( int i = 0; i < numChildren; i++) {
          org.w3c.dom.Node child = children.item( i);
          if (child instanceof Drawable) {
            drawableChildren.add(child);
          }
        }
      }
      for (int i = 0; i < drawableChildren.size(); i++) {
        org.w3c.dom.Node child = (org.w3c.dom.Node)drawableChildren.elementAt( i);
        clonedMarker.removeChild(child);
        gElement.appendChild(child);
      }

      if (getOrientType().getAnimVal() == SVG_MARKER_ORIENT_ANGLE) {
        angle = getOrientAngle().getAnimVal().getValue();
      }

      String transformString = "translate(" + x + "," + y + ")rotate(" + angle + ")";
      if (getMarkerUnits().getAnimVal() == SVG_MARKERUNITS_STROKEWIDTH) {
        transformString += "scale(" + strokeWidth + ")";
      }

      float width = getMarkerWidth().getAnimVal().getValue();
      float height = getMarkerHeight().getAnimVal().getValue();
      float viewBoxWidth;
      float viewBoxHeight;
      if (clonedMarker.getViewBox().getAnimVal().getWidth() > 0) {
        viewBoxWidth = clonedMarker.getViewBox().getAnimVal().getWidth();
        viewBoxHeight = clonedMarker.getViewBox().getAnimVal().getHeight();
      } else {
        viewBoxWidth = width;
        viewBoxHeight = height;
      }

      transformString += "scale(" + width/viewBoxWidth + "," + height/viewBoxHeight + ")";
      transformString += "translate(" + (getRefX().getAnimVal().getValue() * -1) + ","
                                      + (getRefY().getAnimVal().getValue() * -1) + ")";

      SVGTransformList transform = SVGTransformListImpl.createTransformList( transformString);
      ((SVGAnimatedTransformListImpl)gElement.getTransform()).setBaseVal(transform);

      // do actual drawing
      // refreshData needs to be true because the marker children may now have different parents
      // and so may inherit different style properties
      gElement.draw(graphics, true);

      // need to put cloned children back onto clonedMarker
      for (int i = 0; i < drawableChildren.size(); i++) {
        org.w3c.dom.Node child = (org.w3c.dom.Node)drawableChildren.elementAt( i);
        gElement.removeChild(child);
        clonedMarker.appendChild(child);
      }
      parent.removeChild(gElement);
    }
  }

   public void attachAnimation(SVGAnimationElementImpl animation) {
    String attName = animation.getAttributeName();
    if (attName.equals("refX")) {
      ((SVGAnimatedValue)getRefX()).addAnimation(animation);
    } else if (attName.equals("refY")) {
      ((SVGAnimatedValue)getRefY()).addAnimation(animation);
    } else if (attName.equals("markerWidth")) {
      ((SVGAnimatedValue)getMarkerWidth()).addAnimation(animation);
    } else if (attName.equals("markerHeight")) {
      ((SVGAnimatedValue)getMarkerHeight()).addAnimation(animation);
    } else if (attName.equals("markerUnits")) {
      ((SVGAnimatedValue)getMarkerUnits()).addAnimation(animation);
    } else if (attName.equals("orient")) {
      ((SVGAnimatedValue)getOrientAngle()).addAnimation(animation);
      ((SVGAnimatedValue)getOrientType()).addAnimation(animation);
    } else if (attName.equals("viewBox")) {
      ((SVGAnimatedValue)getViewBox()).addAnimation(animation);
    } else if (attName.equals("preserveAspectRatio")) {
      ((SVGAnimatedValue)getPreserveAspectRatio()).addAnimation(animation);
    } else if (attName.equals("externalResourcesRequired")) {
      ((SVGAnimatedValue)getExternalResourcesRequired()).addAnimation(animation);
    } else {
      super.attachAnimation(animation);
    }
  }

}
