// SVGPatternElementImpl.java
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
// $Id: SVGPatternElementImpl.java,v 1.34 2001/05/09 01:34:50 bella Exp $
//

package org.csiro.svg.dom;

import org.w3c.dom.Element;
import org.w3c.dom.svg.*;

import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * SVGPatternElementImpl is the implementation of org.w3c.dom.svg.SVGPatternElement
 */
public class SVGPatternElementImpl extends SVGStylableImpl implements SVGPatternElement {

  protected SVGAnimatedLength x;
  protected SVGAnimatedLength y;
  protected SVGAnimatedLength width;
  protected SVGAnimatedLength height;
  protected SVGAnimatedEnumeration patternUnits;
  protected SVGAnimatedEnumeration patternContentUnits;
  protected SVGAnimatedTransformList patternTransform;
  protected SVGAnimatedRect viewBox;
  protected SVGAnimatedPreserveAspectRatio preserveAspectRatio;
  protected SVGAnimatedString href;
  protected SVGAnimatedBoolean externalResourcesRequired;

  private static Vector patternUnitStrings;
  private static Vector patternUnitValues;

  /**
   * Simple constructor
   */
  public SVGPatternElementImpl(SVGDocumentImpl owner) {
    super(owner, "pattern");

    // setup initial attributes with some defaults
    super.setAttribute("x", getX().getBaseVal().getValueAsString());
    super.setAttribute("y", getY().getBaseVal().getValueAsString());
    super.setAttribute("width", getWidth().getBaseVal().getValueAsString());
    super.setAttribute("height", getHeight().getBaseVal().getValueAsString());
    super.setAttribute("patternUnits", "objectBoundingBox");
    super.setAttribute("patternContentUnits", "userSpaceOnUse");
    super.setAttribute("patternTransform", "");
    super.setAttribute("preserveAspectRatio", ((SVGPreserveAspectRatioImpl)getPreserveAspectRatio().getBaseVal()).toString());
  }

  /**
   * Constructor using an XML Parser
   */
  public SVGPatternElementImpl(SVGDocumentImpl owner, org.w3c.dom.Element elem) {
    super(owner, elem, "pattern");
  }

  public SVGElementImpl cloneElement() {
    SVGPatternElementImpl newPattern = new SVGPatternElementImpl(getOwnerDoc(), this);

    Vector xAnims = ((SVGAnimatedLengthImpl)getX()).getAnimations();
    Vector yAnims = ((SVGAnimatedLengthImpl)getY()).getAnimations();
    Vector widthAnims = ((SVGAnimatedLengthImpl)getWidth()).getAnimations();
    Vector heightAnims = ((SVGAnimatedLengthImpl)getHeight()).getAnimations();
    Vector unitsAnims = ((SVGAnimatedEnumerationImpl)getPatternUnits()).getAnimations();
    Vector contentUnitsAnims = ((SVGAnimatedEnumerationImpl)getPatternContentUnits()).getAnimations();
    Vector transformAnims = ((SVGAnimatedTransformListImpl)getPatternTransform()).getAnimations();
    Vector viewBoxAnims = ((SVGAnimatedRectImpl)getViewBox()).getAnimations();
    Vector preserveAspectRatioAnims = ((SVGAnimatedPreserveAspectRatioImpl)getPreserveAspectRatio()).getAnimations();
    Vector externalResourcesRequiredAnims = ((SVGAnimatedBooleanImpl)getExternalResourcesRequired()).getAnimations();
    Vector hrefAnims = ((SVGAnimatedStringImpl)getHref()).getAnimations();

    if (xAnims != null) {
      for (int i = 0; i < xAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)xAnims.elementAt(i);
        newPattern.attachAnimation(anim);
      }
    }
    if (yAnims != null) {
      for (int i = 0; i < yAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)yAnims.elementAt(i);
        newPattern.attachAnimation(anim);
      }
    }
    if (widthAnims != null) {
      for (int i = 0; i < widthAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)widthAnims.elementAt(i);
        newPattern.attachAnimation(anim);
      }
    }
    if (heightAnims != null) {
      for (int i = 0; i < heightAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)heightAnims.elementAt(i);
        newPattern.attachAnimation(anim);
      }
    }
    if (unitsAnims != null) {
      for (int i = 0; i < unitsAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)unitsAnims.elementAt(i);
        newPattern.attachAnimation(anim);
      }
    }
     if (contentUnitsAnims != null) {
      for (int i = 0; i < contentUnitsAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)contentUnitsAnims.elementAt(i);
        newPattern.attachAnimation(anim);
      }
    }
    if (transformAnims != null) {
      for (int i = 0; i < transformAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)transformAnims.elementAt(i);
        newPattern.attachAnimation(anim);
      }
    }
    if (viewBoxAnims != null) {
      for (int i = 0; i < viewBoxAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)viewBoxAnims.elementAt(i);
        newPattern.attachAnimation(anim);
      }
    }
    if (preserveAspectRatioAnims != null) {
      for (int i = 0; i < preserveAspectRatioAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)preserveAspectRatioAnims.elementAt(i);
        newPattern.attachAnimation(anim);
      }
    }
    if (externalResourcesRequiredAnims != null) {
      for (int i = 0; i < externalResourcesRequiredAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)externalResourcesRequiredAnims.elementAt(i);
        newPattern.attachAnimation(anim);
      }
    }
     if (hrefAnims != null) {
      for (int i = 0; i < hrefAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)hrefAnims.elementAt(i);
        newPattern.attachAnimation(anim);
      }
    }
    if (animatedProperties != null) {
      newPattern.animatedProperties = animatedProperties;
    }
    return newPattern;

  }

  private void initPatternUnitVectors() {
    if (patternUnitStrings == null) {
      patternUnitStrings = new Vector();
      patternUnitStrings.addElement("userSpaceOnUse");
      patternUnitStrings.addElement("objectBoundingBox");
    }
    if (patternUnitValues == null) {
      patternUnitValues = new Vector();
      patternUnitValues.addElement(new Short(SVG_UNIT_TYPE_USERSPACEONUSE));
      patternUnitValues.addElement(new Short(SVG_UNIT_TYPE_OBJECTBOUNDINGBOX));
      patternUnitValues.addElement(new Short(SVG_UNIT_TYPE_UNKNOWN));
    }
  }

  public SVGAnimatedLength getX() {
    if (x == null) {
      SVGPatternElementImpl refPattern = getReferencedPattern();
      if (refPattern != null) {
        return refPattern.getX();
      } else {
        x = new SVGAnimatedLengthImpl(new SVGLengthImpl("0%", this, SVGLengthImpl.X_DIRECTION), this);
      }
    }
    return x;
  }

  public SVGAnimatedLength getY() {
    if (y == null) {
      SVGPatternElementImpl refPattern = getReferencedPattern();
      if (refPattern != null) {
        return refPattern.getY();
      } else {
        y = new SVGAnimatedLengthImpl(new SVGLengthImpl("0%", this, SVGLengthImpl.Y_DIRECTION), this);
      }
    }
    return y;
  }

  public SVGAnimatedLength getWidth() {
    if (width == null) {
      SVGPatternElementImpl refPattern = getReferencedPattern();
      if (refPattern != null) {
        return refPattern.getWidth();
      } else {
        width = new SVGAnimatedLengthImpl(new SVGLengthImpl("100%", this, SVGLengthImpl.X_DIRECTION), this);
      }
    }
    return width;
  }

  public SVGAnimatedLength getHeight() {
    if (height == null) {
      SVGPatternElementImpl refPattern = getReferencedPattern();
      if (refPattern != null) {
        return refPattern.getHeight();
      } else {
        height = new SVGAnimatedLengthImpl(new SVGLengthImpl("100%", this, SVGLengthImpl.Y_DIRECTION), this);
      }
    }
    return height;
  }

  public SVGAnimatedEnumeration getPatternUnits() {
    if (patternUnits == null) {
      SVGPatternElementImpl refPattern = getReferencedPattern();
      if (refPattern != null) {
        return refPattern.getPatternUnits();
      } else {
        if (patternUnitStrings == null) {
          initPatternUnitVectors();
        }
        patternUnits = new SVGAnimatedEnumerationImpl(SVG_UNIT_TYPE_OBJECTBOUNDINGBOX, this, patternUnitStrings, patternUnitValues);
      }
    }
    return patternUnits;
  }

  public SVGAnimatedEnumeration getPatternContentUnits() {
    if (patternContentUnits == null) {
      SVGPatternElementImpl refPattern = getReferencedPattern();
      if (refPattern != null) {
        return refPattern.getPatternContentUnits();
      } else {
        if (patternUnitStrings == null) {
          initPatternUnitVectors();
        }
        patternContentUnits = new SVGAnimatedEnumerationImpl(SVG_UNIT_TYPE_USERSPACEONUSE, this, patternUnitStrings, patternUnitValues);
      }
    }
    return patternContentUnits;
  }

  public SVGAnimatedTransformList getPatternTransform() {
    if (patternTransform == null) {
      SVGPatternElementImpl refPattern = getReferencedPattern();
      if (refPattern != null) {
        return refPattern.getPatternTransform();
      } else {
        patternTransform = new SVGAnimatedTransformListImpl(new SVGTransformListImpl(), this);
      }
    }
    return patternTransform;
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
    } else if (name.equalsIgnoreCase("width")) {
      return getWidth().getBaseVal().getValueAsString();
    } else if (name.equalsIgnoreCase("height")) {
      return getHeight().getBaseVal().getValueAsString();

    } else if (name.equalsIgnoreCase("viewBox")) {
      if (getViewBox().getBaseVal() == null) {
        return "";
      } else {
        return getViewBox().getBaseVal().toString();
      }

    } else if (name.equalsIgnoreCase("preserveAspectRatio")) {
      return ((SVGPreserveAspectRatioImpl)getPreserveAspectRatio().getBaseVal()).toString();

    } else if (name.equalsIgnoreCase("patternUnits")) {
      if (getPatternUnits().getBaseVal() == SVG_UNIT_TYPE_OBJECTBOUNDINGBOX) {
       return "objectBoundingBox";
      } else {
        return "userSpaceOnUse";
      }

    } else if (name.equalsIgnoreCase("patternContentUnits")) {
      if (getPatternContentUnits().getBaseVal() == SVG_UNIT_TYPE_OBJECTBOUNDINGBOX) {
       return "objectBoundingBox";
      } else {
        return "userSpaceOnUse";
      }

    } else if (name.equalsIgnoreCase("xlink:href")) {
      return getHref().getBaseVal();

    } else if (name.equalsIgnoreCase("patternTransform")) {
      return getPatternTransform().getBaseVal().toString();

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
    } else if (name.equalsIgnoreCase("width")) {
      attr.setValue(getWidth().getBaseVal().getValueAsString());
    } else if (name.equalsIgnoreCase("height")) {
      attr.setValue(getHeight().getBaseVal().getValueAsString());

    } else if (name.equalsIgnoreCase("viewBox")) {
      if (getViewBox().getBaseVal() == null) {
        attr.setValue("");
      } else {
        attr.setValue(getViewBox().getBaseVal().toString());
      }
    } else if (name.equalsIgnoreCase("preserveAspectRatio")) {
      attr.setValue(((SVGPreserveAspectRatioImpl)getPreserveAspectRatio().getBaseVal()).toString());

    } else if (name.equalsIgnoreCase("patternUnits")) {
      if (getPatternUnits().getBaseVal() == SVG_UNIT_TYPE_OBJECTBOUNDINGBOX) {
        attr.setValue("objectBoundingBox");
      } else {
         attr.setValue("userSpaceOnUse");
      }

    } else if (name.equalsIgnoreCase("patternContentUnits")) {
      if (getPatternContentUnits().getBaseVal() == SVG_UNIT_TYPE_OBJECTBOUNDINGBOX) {
        attr.setValue("objectBoundingBox");
      } else {
        attr.setValue("userSpaceOnUse");
      }

    } else if (name.equalsIgnoreCase("xlink:href")) {
      attr.setValue(getHref().getBaseVal());

    } else if (name.equalsIgnoreCase("patternTransform")) {
      attr.setValue(patternTransform.getBaseVal().toString());

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
    } else if (name.equalsIgnoreCase("width")) {
      getWidth().getBaseVal().setValueAsString(value);
    } else if (name.equalsIgnoreCase("height")) {
      getHeight().getBaseVal().setValueAsString(value);

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

    } else if (name.equalsIgnoreCase("patternUnits")) {
      if (value.equalsIgnoreCase("userSpaceOnUse")) {
        getPatternUnits().setBaseVal(SVG_UNIT_TYPE_USERSPACEONUSE);
      } else if (value.equalsIgnoreCase("objectBoundingBox")) {
        getPatternUnits().setBaseVal(SVG_UNIT_TYPE_OBJECTBOUNDINGBOX);
      } else {
        System.out.println("invalid value '" + value + "' for patternUnits attribute, setting to default 'objectBoundingBox'");
        getPatternUnits().setBaseVal(SVG_UNIT_TYPE_OBJECTBOUNDINGBOX);
      }

    } else if (name.equalsIgnoreCase("patternContentUnits")) {
      if (value.equalsIgnoreCase("userSpaceOnUse")) {
        getPatternContentUnits().setBaseVal(SVG_UNIT_TYPE_USERSPACEONUSE);
      } else if (value.equalsIgnoreCase("objectBoundingBox")) {
        getPatternContentUnits().setBaseVal(SVG_UNIT_TYPE_OBJECTBOUNDINGBOX);
      } else {
        System.out.println("invalid value '" + value + "' for patternContentUnits attribute, setting to default 'userSpaceOnUse'");
        getPatternContentUnits().setBaseVal(SVG_UNIT_TYPE_USERSPACEONUSE);
      }


    } else if (name.equalsIgnoreCase("xlink:href")) {
      getHref().setBaseVal(value);

    } else if (name.equalsIgnoreCase("patternTransform")) {
      ((SVGAnimatedTransformListImpl)getPatternTransform()).setBaseVal(SVGTransformListImpl.createTransformList(value));

    } else if (name.equalsIgnoreCase("externalResourcesRequired")) {
      if (value.equalsIgnoreCase("true")) {
        getExternalResourcesRequired().setBaseVal(true);
      } else {
        getExternalResourcesRequired().setBaseVal(false);
      }
    }
  }


  public Paint getPaint(SVGElementImpl element, float opacity) {

    // get current animated values
    short animPatternUnits = getPatternUnits().getAnimVal();
    short animPatternContentUnits = getPatternContentUnits().getAnimVal();
    SVGLength animX = getX().getAnimVal();
    SVGLength animY = getY().getAnimVal();
    SVGLength animWidth = getWidth().getAnimVal();
    SVGLength animHeight = getHeight().getAnimVal();

    SVGSVGElement svgElement = getOwnerSVGElement();
    SVGRect vBox = svgElement.getViewBox().getAnimVal();
    if (vBox == null) {
      vBox = svgElement.getViewport();
    }

    SVGRect objectBounds = null;
    if (element instanceof SVGLocatable) {
      objectBounds = ((SVGLocatable)element).getBBox();
    } else {
      objectBounds = vBox;
    }

    float boxMinX;
    float boxMinY;
    float boxWidth;
    float boxHeight;
    float scaleX = 1;
    float scaleY = 1;

    if (animPatternUnits  == SVG_UNIT_TYPE_OBJECTBOUNDINGBOX) {

      boxMinX = objectBounds.getX();
      boxMinY = objectBounds.getY();
      boxWidth = objectBounds.getWidth();
      boxHeight = objectBounds.getHeight();

    } else { // user space

      boxMinX = vBox.getX();
      boxMinY = vBox.getY();
      boxWidth = vBox.getWidth();
      boxHeight = vBox.getHeight();
    }

    if (animPatternContentUnits == SVG_UNIT_TYPE_OBJECTBOUNDINGBOX) {
           // if bounds not square, make it square for now and do a scale transform later
      if (objectBounds.getWidth() > objectBounds.getHeight()) {
        scaleY = objectBounds.getHeight()/objectBounds.getWidth();
        boxHeight = objectBounds.getWidth();
      } else if (objectBounds.getHeight() > objectBounds.getWidth()) {
        scaleX = objectBounds.getWidth()/objectBounds.getHeight();
        boxWidth = objectBounds.getHeight();
      }
    }

 //   System.out.println("box dimensions: " + boxMinX + "," + boxMinY + "," + boxWidth + "," + boxHeight);

    float x;
    float y;
    float width;
    float height;

    if (animX.getUnitType() == SVGLength.SVG_LENGTHTYPE_PERCENTAGE) {
       x = (float)(boxMinX + animX.getValueInSpecifiedUnits()/100.0 * boxWidth);
    } else {
      if (animPatternUnits == SVG_UNIT_TYPE_USERSPACEONUSE) {
        // is a coordinate in user space
        x = animX.getValue();
      } else { // should be a value between 0 and 1
        x = boxMinX + animX.getValue() * boxWidth;
      }
    }

    if (animY.getUnitType() == SVGLength.SVG_LENGTHTYPE_PERCENTAGE) {
       y = (float)(boxMinY + animY.getValueInSpecifiedUnits()/100.0 * boxHeight);
    } else {
      if (animPatternUnits == SVG_UNIT_TYPE_USERSPACEONUSE) {
        // is a coordinate in user space
        y = animY.getValue();
      } else { // should be a value between 0 and 1
        y = boxMinY + animY.getValue() * boxHeight;
      }
    }

    if (animWidth.getUnitType() == SVGLength.SVG_LENGTHTYPE_PERCENTAGE) {
      width  = (float)(animWidth.getValueInSpecifiedUnits()/100.0 * boxWidth);
    } else {
      if (animPatternUnits == SVG_UNIT_TYPE_USERSPACEONUSE) {
        // is a coordinate in user space
        width = animWidth.getValue();
      } else { // should be a value between 0 and 1
        width = animWidth.getValue() * boxWidth;
      }
    }


    if (animHeight.getUnitType() == SVGLength.SVG_LENGTHTYPE_PERCENTAGE) {
      height = (float)(animHeight.getValueInSpecifiedUnits()/100.0 * boxHeight);
    } else {
      if (animPatternUnits == SVG_UNIT_TYPE_USERSPACEONUSE) {
        // is a coordinate in user space
        height = animHeight.getValue();
      } else { // should be a value between 0 and 1
        height = animHeight.getValue() * boxHeight;
      }
    }


    AffineTransform transform = ((SVGTransformListImpl)getPatternTransform().getAnimVal()).getAffineTransform();
    if ((scaleX != 1 || scaleY != 1) && animPatternUnits != SVG_UNIT_TYPE_USERSPACEONUSE) {
       transform.preConcatenate(AffineTransform.getTranslateInstance(-x, -y));
       transform.preConcatenate(AffineTransform.getScaleInstance(scaleX, scaleY));
       transform.preConcatenate(AffineTransform.getTranslateInstance(x, y));
    }


   // System.out.println("pattern dimensions = " + x + "," + y + "," + width + "," + height);

    SVGSVGElement root = getOwnerDoc().getRootElement();
    float scale = 1;
    if (root != null) {
      scale = root.getCurrentScale();
    }
    int imageWidth = (int)(width * scale);
    int imageHeight = (int)(height * scale);

    if (imageWidth > 500 || imageHeight > 500) {  // make sure pattern image doesn't get too large
      float widthRatio = (float)imageWidth/500;
      float heightRatio = (float)imageHeight/500;
      if (widthRatio > heightRatio) {
        imageWidth = (int)(imageWidth / widthRatio);
        imageHeight = (int)(imageHeight / widthRatio);
      } else {
        imageWidth = (int)(imageWidth / heightRatio);
        imageHeight = (int)(imageHeight / heightRatio);
      }
    }

    if (imageWidth == 0 || imageHeight == 0) {
      return null;
    }

  //  System.out.println("image dimensions: " + imageWidth + " x " + imageHeight);
  //  System.out.println("transform = " + transform.toString());

    BufferedImage image = new BufferedImage(imageWidth, imageHeight,
                                                BufferedImage.TYPE_4BYTE_ABGR);

    Graphics2D graphics = (Graphics2D)image.getGraphics();
    RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
						                                    RenderingHints.VALUE_ANTIALIAS_ON);
    graphics.setRenderingHints(hints);

    // see if this pattern has any children itself, if not then use the children of the referenced pattern if there is one
    boolean patternHasChildren = false;
    if (hasChildNodes()) {
      org.w3c.dom.NodeList children = getChildNodes();
      int numChildren = children.getLength();
      for (int i = 0; i < numChildren; i++) {
        org.w3c.dom.Node child = children.item( i);
        if (child instanceof SVGElementImpl) {
          patternHasChildren = true;
          break;
        }
      }
    }
    SVGPatternElementImpl refPattern = getReferencedPattern();
    SVGPatternElementImpl clonedPattern;
    if (patternHasChildren || refPattern == null) {
      clonedPattern = (SVGPatternElementImpl)cloneElement();
    } else {
      clonedPattern = (SVGPatternElementImpl)refPattern.cloneElement();
    }

    SVGSVGElementImpl svg = new SVGSVGElementImpl(getOwnerDoc());

    ((SVGAnimatedLengthImpl)svg.getX()).setBaseVal(new SVGLengthImpl(0, svg, SVGLengthImpl.X_DIRECTION));
    ((SVGAnimatedLengthImpl)svg.getY()).setBaseVal(new SVGLengthImpl(0, svg, SVGLengthImpl.Y_DIRECTION));
    ((SVGAnimatedLengthImpl)svg.getWidth()).setBaseVal(new SVGLengthImpl(imageWidth, svg, SVGLengthImpl.X_DIRECTION));
    ((SVGAnimatedLengthImpl)svg.getHeight()).setBaseVal(new SVGLengthImpl(imageHeight, svg, SVGLengthImpl.Y_DIRECTION));

    ((SVGAnimatedPreserveAspectRatioImpl)svg.getPreserveAspectRatio()).setBaseVal(getPreserveAspectRatio().getAnimVal());

    if (getViewBox().getBaseVal().getWidth() > 0) {
      svg.setAttribute("viewBox", ((SVGRectImpl)getViewBox().getBaseVal()).toString());
    } else {
      if (animPatternContentUnits == SVG_UNIT_TYPE_OBJECTBOUNDINGBOX) {
        svg.setAttribute("viewBox", "0,0," + (width/objectBounds.getWidth()*scaleX) + ","
                                           + (height/objectBounds.getHeight()*scaleY));
      } else {
        svg.setAttribute("viewBox", "0,0," + width + "," + height);
      }
    }

    SVGGElement g = new SVGGElementImpl(getOwnerDoc());

    Vector svgElements = new Vector();
    if (clonedPattern.hasChildNodes()) {
      org.w3c.dom.NodeList children = clonedPattern.getChildNodes();
      int numChildren = children.getLength();
      for (int i = 0; i < numChildren; i++) {
        org.w3c.dom.Node child = children.item( i);
        if (child instanceof SVGElementImpl) {
          svgElements.add(child);
        }
      }
    }
    for (int i = 0; i < svgElements.size(); i++) {
      SVGElementImpl child = (SVGElementImpl)svgElements.elementAt(i);
      g.appendChild(child);
    }
    svg.appendChild(g);

    if ((scaleX != 1 || scaleY != 1)
        && animPatternContentUnits == SVG_UNIT_TYPE_OBJECTBOUNDINGBOX
        && animPatternUnits == SVG_UNIT_TYPE_USERSPACEONUSE
        && getViewBox().getBaseVal().getWidth() == 0) {
       g.setAttribute("transform", "scale(" + scaleX + "," + scaleY + ")");
    }

    svg.draw(graphics, true);

    if (opacity < 1) {
      BufferedImage opImage = new BufferedImage(imageWidth, imageHeight,
                                                BufferedImage.TYPE_4BYTE_ABGR);
      Graphics2D opGraphics = (Graphics2D)opImage.getGraphics();
      AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
      opGraphics.setComposite(ac);
      opGraphics.drawImage(image, new AffineTransform(), null);
      image = opImage;
    }

    SVGTexturePaint paint = new SVGTexturePaint(image, new Rectangle2D.Float(x, y, width, height), transform,
                                                 SVGTexturePaint.SVG_TEXTURETYPE_PATTERN);
    return paint;
  }

  public void attachAnimation(SVGAnimationElementImpl animation) {
    String attName = animation.getAttributeName();
    if (attName.equals("x")) {
      ((SVGAnimatedValue)getX()).addAnimation(animation);
    } else if (attName.equals("y")) {
      ((SVGAnimatedValue)getY()).addAnimation(animation);
    } else if (attName.equals("width")) {
      ((SVGAnimatedValue)getWidth()).addAnimation(animation);
    } else if (attName.equals("height")) {
      ((SVGAnimatedValue)getHeight()).addAnimation(animation);
    } else if (attName.equals("patternUnits")) {
      ((SVGAnimatedValue)getPatternUnits()).addAnimation(animation);
    } else if (attName.equals("patternTransform")) {
      ((SVGAnimatedValue)getPatternTransform()).addAnimation(animation);
    } else if (attName.equals("viewBox")) {
      ((SVGAnimatedValue)getViewBox()).addAnimation(animation);
    } else if (attName.equals("preserveAspectRatio")) {
      ((SVGAnimatedValue)getPreserveAspectRatio()).addAnimation(animation);
    } else if (attName.equals("href")) {
      ((SVGAnimatedValue)getHref()).addAnimation(animation);
    } else if (attName.equals("externalResourcesRequired")) {
      ((SVGAnimatedValue)getExternalResourcesRequired()).addAnimation(animation);
    } else {
      super.attachAnimation(animation);
    }
  }

   protected SVGPatternElementImpl getReferencedPattern() {
    String ref = getHref().getAnimVal();
    if (ref.length() > 0 && ref.indexOf('#') != -1) {
      int hashIndex = ref.indexOf('#');
      String id = ref.substring(hashIndex+1, ref.length());
      Element refElem = getOwnerDoc().getElementById( id);
      if (refElem != null && refElem instanceof SVGPatternElementImpl) {
        return (SVGPatternElementImpl)refElem;
      }
    }
    return null;
  }

}
