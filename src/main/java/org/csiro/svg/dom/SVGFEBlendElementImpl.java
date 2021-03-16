// SVGFEBlendElementImpl.java
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
// $Id: SVGFEBlendElementImpl.java,v 1.4 2000/09/26 14:10:22 dino Exp $
//

package org.csiro.svg.dom;

import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGAnimatedEnumeration;
import org.w3c.dom.svg.SVGAnimatedString;
import org.w3c.dom.svg.SVGFEBlendElement;

import java.util.Vector;

public class SVGFEBlendElementImpl extends SVGFilterPrimitive implements
  SVGFEBlendElement {

  protected SVGAnimatedString in1;
  protected SVGAnimatedString in2;
  protected SVGAnimatedEnumeration mode;

  private static Vector modeStrings;
  private static Vector modeValues;

  public SVGFEBlendElementImpl(SVGDocumentImpl owner) {
    super(owner, "feBlend");
  }

  public SVGFEBlendElementImpl(SVGDocumentImpl owner, Element elem) {
    super(owner, elem, "feBlend");
  }

  public SVGElementImpl cloneElement() {
    SVGFEBlendElementImpl newFEBlend = new SVGFEBlendElementImpl(getOwnerDoc(), this);

    Vector xAnims = ((SVGAnimatedLengthImpl)getX()).getAnimations();
    Vector yAnims = ((SVGAnimatedLengthImpl)getY()).getAnimations();
    Vector widthAnims = ((SVGAnimatedLengthImpl)getWidth()).getAnimations();
    Vector heightAnims = ((SVGAnimatedLengthImpl)getHeight()).getAnimations();
    Vector resultAnims = ((SVGAnimatedStringImpl)getResult()).getAnimations();

     Vector in1Anims = ((SVGAnimatedStringImpl)getIn1()).getAnimations();
     Vector in2Anims = ((SVGAnimatedStringImpl)getIn2()).getAnimations();
     Vector modeAnims = ((SVGAnimatedEnumerationImpl)getMode()).getAnimations();

    if (xAnims != null) {
      for (int i = 0; i < xAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)xAnims.elementAt(i);
        newFEBlend.attachAnimation(anim);
      }
    }
    if (yAnims != null) {
      for (int i = 0; i < yAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)yAnims.elementAt(i);
        newFEBlend.attachAnimation(anim);
      }
    }
    if (widthAnims != null) {
      for (int i = 0; i < widthAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)widthAnims.elementAt(i);
        newFEBlend.attachAnimation(anim);
      }
    }
    if (heightAnims != null) {
      for (int i = 0; i < heightAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)heightAnims.elementAt(i);
        newFEBlend.attachAnimation(anim);
      }
    }
    if (resultAnims != null) {
      for (int i = 0; i < resultAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)resultAnims.elementAt(i);
        newFEBlend.attachAnimation(anim);
      }
    }

    if (in1Anims != null) {
      for (int i = 0; i < in1Anims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)in1Anims.elementAt(i);
        newFEBlend.attachAnimation(anim);
      }
    }
     if (in2Anims != null) {
      for (int i = 0; i < in2Anims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)in2Anims.elementAt(i);
        newFEBlend.attachAnimation(anim);
      }
    }
     if (modeAnims != null) {
      for (int i = 0; i < modeAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)modeAnims.elementAt(i);
        newFEBlend.attachAnimation(anim);
      }
    }
    return newFEBlend;
  }



  public SVGAnimatedString getIn1() {
    if (in1 == null) {
      in1 = new SVGAnimatedStringImpl("", this);
    }
    return in1;
  }

  public SVGAnimatedString getIn2() {
   if (in2 == null) {
      in2 = new SVGAnimatedStringImpl("", this);
    }
    return in2;
  }

  public SVGAnimatedEnumeration getMode() {
    if (mode == null) {
      if (modeStrings == null) {
        initModeVectors();
      }
      mode = new SVGAnimatedEnumerationImpl(SVG_FEBLEND_MODE_NORMAL, this, modeStrings, modeValues);
    }
    return mode;
  }

  private void initModeVectors() {
    if (modeStrings == null) {
      modeStrings = new Vector();
      modeStrings.addElement("normal");
      modeStrings.addElement("multiply");
      modeStrings.addElement("screen");
      modeStrings.addElement("darken");
      modeStrings.addElement("lighten");
    }
    if (modeValues == null) {
      modeValues = new Vector();
      modeValues.addElement(new Short(SVG_FEBLEND_MODE_NORMAL));
      modeValues.addElement(new Short(SVG_FEBLEND_MODE_MULTIPLY));
      modeValues.addElement(new Short(SVG_FEBLEND_MODE_SCREEN));
      modeValues.addElement(new Short(SVG_FEBLEND_MODE_DARKEN));
      modeValues.addElement(new Short(SVG_FEBLEND_MODE_LIGHTEN));
      modeValues.addElement(new Short(SVG_FEBLEND_MODE_UNKNOWN));
    }
  }


  public String getAttribute(String name) {
    if (name.equalsIgnoreCase("in")) {
      return getIn1().getBaseVal();
    } else if (name.equalsIgnoreCase("in2")) {
      return getIn2().getBaseVal();
    } else if (name.equalsIgnoreCase("mode")) {
      if (getMode().getBaseVal() == SVG_FEBLEND_MODE_MULTIPLY) {
        return "multiply";
      } else if (getMode().getBaseVal() == SVG_FEBLEND_MODE_SCREEN) {
       return "screen";
      } else if (getMode().getBaseVal() == SVG_FEBLEND_MODE_DARKEN) {
       return "darken";
       } else if (getMode().getBaseVal() == SVG_FEBLEND_MODE_LIGHTEN) {
       return "lighten";
      } else {
        return "normal";
      }
    } else {
      return super.getAttribute(name);
    }
  }

  public org.w3c.dom.Attr getAttributeNode( String name) {
    org.w3c.dom.Attr attr = super.getAttributeNode( name);
    if (attr == null)
      return attr;
    if (name.equalsIgnoreCase("in")) {
      attr.setValue(getIn1().getBaseVal());
    } else if (name.equalsIgnoreCase("in2")) {
      attr.setValue(getIn2().getBaseVal());
    } else if (name.equalsIgnoreCase("mode")) {
      if (getMode().getBaseVal() == SVG_FEBLEND_MODE_MULTIPLY) {
        attr.setValue("multiply");
      } else if (getMode().getBaseVal() == SVG_FEBLEND_MODE_SCREEN) {
        attr.setValue("screen");
      } else if (getMode().getBaseVal() == SVG_FEBLEND_MODE_DARKEN) {
        attr.setValue("darken");
      } else if (getMode().getBaseVal() == SVG_FEBLEND_MODE_LIGHTEN) {
        attr.setValue("lighten");
      } else {
         attr.setValue("normal");
      }
    }
    return attr;
  }

  public void setAttribute(String name, String value) {
    super.setAttribute(name, value);
    if (name.equalsIgnoreCase("in")) {
      getIn1().setBaseVal(value);

    } else if (name.equalsIgnoreCase("in2")) {
      getIn2().setBaseVal(value);

    } else if (name.equalsIgnoreCase("mode")) {
      if (value.equalsIgnoreCase("normal")) {
        getMode().setBaseVal(SVG_FEBLEND_MODE_NORMAL);
      } else if (value.equalsIgnoreCase("multiply")) {
        getMode().setBaseVal(SVG_FEBLEND_MODE_MULTIPLY);
      } else if (value.equalsIgnoreCase("screen")) {
        getMode().setBaseVal(SVG_FEBLEND_MODE_SCREEN);
      } else if (value.equalsIgnoreCase("darken")) {
        getMode().setBaseVal(SVG_FEBLEND_MODE_DARKEN);
      } else if (value.equalsIgnoreCase("lighten")) {
        getMode().setBaseVal(SVG_FEBLEND_MODE_LIGHTEN);
      } else {
        System.out.println("invalid value '" + value + "' for mode attribute, setting to default 'normal'");
        getMode().setBaseVal(SVG_FEBLEND_MODE_NORMAL);
        super.setAttribute("mode", "normal");
      }
    }
  }

  public org.w3c.dom.Attr setAttributeNode( org.w3c.dom.Attr newAttr) throws
    org.w3c.dom.DOMException {
    String name = newAttr.getName();
    String value = newAttr.getValue();

    if (name.equalsIgnoreCase("in")) {
      getIn1().setBaseVal(value);

    } else if (name.equalsIgnoreCase("in2")) {
      getIn2().setBaseVal(value);

    } else if (name.equalsIgnoreCase("mode")) {
       if (value.equalsIgnoreCase("normal")) {
        getMode().setBaseVal(SVG_FEBLEND_MODE_NORMAL);
      } else if (value.equalsIgnoreCase("multiply")) {
        getMode().setBaseVal(SVG_FEBLEND_MODE_MULTIPLY);
      } else if (value.equalsIgnoreCase("screen")) {
        getMode().setBaseVal(SVG_FEBLEND_MODE_SCREEN);
      } else if (value.equalsIgnoreCase("darken")) {
        getMode().setBaseVal(SVG_FEBLEND_MODE_DARKEN);
      } else if (value.equalsIgnoreCase("lighten")) {
        getMode().setBaseVal(SVG_FEBLEND_MODE_LIGHTEN);
      } else {
        System.out.println("invalid value '" + value + "' for mode attribute, setting to default 'normal'");
        getMode().setBaseVal(SVG_FEBLEND_MODE_NORMAL);
        newAttr.setValue("normal");
      }
    }
    return super.setAttributeNode(newAttr);
  }


  public void attachAnimation(SVGAnimationElementImpl animation) {
    String attName = animation.getAttributeName();
    if (attName.equals("in")) {
      ((SVGAnimatedValue)getIn1()).addAnimation(animation);
    } else if (attName.equals("in2")) {
      ((SVGAnimatedValue)getIn2()).addAnimation(animation);
    } else if (attName.equals("mode")) {
      ((SVGAnimatedValue)getMode()).addAnimation(animation);
    } else {
      super.attachAnimation(animation);
    }
  }

    public void drawPrimitive(SVGFilterElementImpl filterEl) {

    }


}
