// SVGFontElementImpl.java
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
// $Id: SVGFontElementImpl.java,v 1.9 2000/09/26 14:10:23 dino Exp $
//

package org.csiro.svg.dom;

import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGAnimatedBoolean;
import org.w3c.dom.svg.SVGFontElement;
import org.w3c.dom.svg.SVGRect;

import java.awt.*;
import java.awt.geom.*;
import java.util.Vector;


public class SVGFontElementImpl extends SVGStylableImpl implements
  SVGFontElement {
    
    // locations
    protected static final int INITIAL = 0;
    protected static final int MEDIAL = 1;
    protected static final int TERMINAL = 2;
    protected static final int ISOLATED = 3;

  protected SVGAnimatedBoolean externalResourcesRequired;

  public SVGFontElementImpl(SVGDocumentImpl owner) {
    super(owner, "font");
  }

  public SVGFontElementImpl(SVGDocumentImpl owner, Element elem) {
    super(owner, elem, "font");
  }

  public SVGElementImpl cloneElement() {
    SVGFontElementImpl newFont = new SVGFontElementImpl(getOwnerDoc(), this);
    Vector externalResourcesRequiredAnims = ((SVGAnimatedBooleanImpl)getExternalResourcesRequired()).getAnimations();

    if (externalResourcesRequiredAnims != null) {
      for (int i = 0; i < externalResourcesRequiredAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)externalResourcesRequiredAnims.elementAt(i);
        newFont.attachAnimation(anim);
      }
    }

    if (animatedProperties != null) {
      newFont.animatedProperties = animatedProperties;
    }
    return newFont;
  }

  // implementation of SVGExternalResourcesRequired interface

  public SVGAnimatedBoolean getExternalResourcesRequired() {
    if (externalResourcesRequired == null) {
      externalResourcesRequired = new SVGAnimatedBooleanImpl(false, this);
    }
    return externalResourcesRequired;
  }

  public String getAttribute(String name) {
    if (name.equalsIgnoreCase("externalResourcesRequired")) {
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
    if (name.equalsIgnoreCase("externalResourcesRequired")) {
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
    if (name.equalsIgnoreCase("externalResourcesRequired")) {
      if (value.equalsIgnoreCase("true")) {
        getExternalResourcesRequired().setBaseVal(true);
      } else {
        getExternalResourcesRequired().setBaseVal(false);
      }
    }
  }

  public org.w3c.dom.Attr setAttributeNode( org.w3c.dom.Attr newAttr) throws
    org.w3c.dom.DOMException {
    String name = newAttr.getName();
    String value = newAttr.getValue();
    if (name.equalsIgnoreCase("externalResourcesRequired")) {
      if (value.equalsIgnoreCase("true")) {
        getExternalResourcesRequired().setBaseVal(true);
      } else {
        getExternalResourcesRequired().setBaseVal(false);
      }
    }
    return super.setAttributeNode(newAttr);
  }

  public SVGGlyphElementImpl getGlyph(String unicode, int position) {

    org.w3c.dom.NodeList children = getChildNodes();
    int numChildren = children.getLength();

    String arabic = "";
    if (position == INITIAL) {
	arabic = "initial";
    } else if (position == ISOLATED) {
	arabic = "isolated";
    } else if (position == TERMINAL) {
	arabic = "terminal";
    } else if (position == MEDIAL) {
	arabic = "medial";
    }

    String han = "";
    if (position == INITIAL) {
	han = "zht";
    } else if (position == ISOLATED) {
	han = "ja";
    } else if (position == TERMINAL) {
	han = "kor";
    } else if (position == MEDIAL) {
	han = "zhs";
    }
    for (int i = 0; i < numChildren; i++) {
      org.w3c.dom.Node child = children.item( i);
      if (child instanceof SVGGlyphElementImpl) {
        SVGGlyphElementImpl glyph = (SVGGlyphElementImpl)child;
	
        if (glyph.getAttribute("unicode").equals(unicode) &&
	    (glyph.getAttribute("arabic") == "" || glyph.getAttribute("arabic").equals(arabic)) &&
	    (glyph.getAttribute("han") == "" || glyph.getAttribute("han").equals(han))) {
          return glyph;
        }
      }
    }
    return null;
  }

  public SVGMissingGlyphElementImpl getMissingGlyph() {
    org.w3c.dom.NodeList children = getChildNodes();
    int numChildren = children.getLength();
    for (int i = 0; i < numChildren; i++) {
      org.w3c.dom.Node child = children.item( i);
      if (child instanceof SVGMissingGlyphElementImpl) {
        return (SVGMissingGlyphElementImpl)child;
      }
    }
    return null;
  }

  private SVGFontFaceElementImpl fontFaceElem;

  public SVGFontFaceElementImpl getFontFace() {
    if (fontFaceElem != null) {
      return fontFaceElem;
    }
    org.w3c.dom.NodeList children = getChildNodes();
    int numChildren = children.getLength();
    for (int i = 0; i < numChildren; i++) {
      org.w3c.dom.Node child = children.item( i);
      if (child instanceof SVGFontFaceElementImpl) {
         fontFaceElem = (SVGFontFaceElementImpl)child;
         return fontFaceElem;
      }
    }
    return null;
  }

  public String getFamilyName() {
    SVGFontFaceElementImpl fontFace = getFontFace();
    if (fontFace != null) {
      return fontFace.getAttribute("font-family");
    }
    return "";
  }

  public String getFontWeight() {
    SVGFontFaceElementImpl fontFace = getFontFace();
    if (fontFace != null) {
      String style = fontFace.getAttribute("font-weight");
      if (style.length() > 0) {
        return style;
      }
    }
    return "normal";
  }
  
  public String getFontStyle() {
    SVGFontFaceElementImpl fontFace = getFontFace();
    if (fontFace != null) {
      String style = fontFace.getAttribute("font-style");
      if (style.length() > 0) {
        return style;
      }
    }
    return "normal";
  }

  public float getFontUnitsPerEm() {
    SVGFontFaceElementImpl fontFace = getFontFace();
    if (fontFace != null) {
      String fontEmString = fontFace.getAttribute("units-per-em");
      if (fontEmString.length() == 0) {
        fontEmString = fontFace.getAttribute("ascent");
      }
      if (fontEmString.length() == 0) {
        fontEmString = fontFace.getAttribute("x-height");
      }
      if (fontEmString.length() == 0) {
        fontEmString = fontFace.getAttribute("cap-height");
      }
      if (fontEmString.length() == 0) {
        fontEmString = fontFace.getAttribute("top-line");
      }
      if (fontEmString.length() > 0) {
        return Float.parseFloat(fontEmString);
      }
    }
    return 1.0f;
  }

  public float getFontAscent() {
    SVGFontFaceElementImpl fontFace = getFontFace();
    if (fontFace != null) {
      String fontAscentString = fontFace.getAttribute("ascent");
      if (fontAscentString.length() == 0) {
        fontAscentString = fontFace.getAttribute("x-height");
      }
      if (fontAscentString.length() == 0) {
        fontAscentString = fontFace.getAttribute("cap-height");
      }
      if (fontAscentString.length() == 0) {
        fontAscentString = fontFace.getAttribute("top-line");
      }
      if (fontAscentString.length() > 0) {
        return Float.parseFloat(fontAscentString);
      }
    }
    return 1.0f;
  }

  public float getFontDescent() {
    SVGFontFaceElementImpl fontFace = getFontFace();
    if (fontFace != null) {
      String fontDescentString = fontFace.getAttribute("descent");
       if (fontDescentString.length() > 0) {
        return Float.parseFloat(fontDescentString);
      }
    }
    return 0;
  }

  public float getHorizAdvX() {
    String horizAdvX = getAttribute("horiz-adv-x");
    if (horizAdvX.length() > 0) {
      return Float.parseFloat(horizAdvX);
    }
    return 0;
  }

  public Rectangle2D getBounds(String text, float x, float y, float fontSize) {
    float fontAscent = getFontAscent();
    float fontDescent = getFontDescent();
    if (fontDescent < 0) fontDescent = -fontDescent;
    float horizAdvX = getHorizAdvX();
    //float scaleFactor = fontSize/fontAscent;
    float scaleFactor = fontSize/getFontUnitsPerEm();

    // for the moment, assume that the width of each char is half the height
    // should really do calculations for each glyph in the string

    Rectangle2D bounds = new Rectangle2D.Float();
    float bottom = y + fontDescent*scaleFactor;
    float top = y - fontAscent*scaleFactor;
    float left = x;

    int numChars = text.length();
    float width = 0;
    int position = ISOLATED;
    for (int i = 0; i < numChars; i++) {
      String ch = "" + text.charAt(i);

       // ouch!!! horrible if that should be simplified
       // this handles the arabic glyph selection
       boolean spaceBefore = false;
       boolean spaceAfter = false;

       if (numChars == 1 || i == 0 || text.charAt(i-1) == ' ') {
	   spaceBefore = true;
       }
       if ((i+1) == numChars || text.charAt(i+1) == ' ') {
	   spaceAfter = true;
       }

       if (spaceBefore && spaceAfter) {
	   position = ISOLATED;
       } else if (spaceBefore && !spaceAfter) {
	   position = INITIAL;
       } else if (!spaceBefore && spaceAfter) {
	   position = TERMINAL;
       } else if (!spaceBefore && !spaceAfter) {
	   position = MEDIAL;
       }
       
      SVGGlyphElementImpl glyph = getGlyph(ch, position);
      if (glyph != null) {
        SVGRect glyphBBox = glyph.getBBox();
        float glyphWidth = (glyphBBox.getX()*2 + glyphBBox.getWidth())*scaleFactor;
        width += glyphWidth;
      } else {
        SVGMissingGlyphElementImpl missingGlyph = getMissingGlyph();
        if (missingGlyph != null) {
          SVGRect glyphBBox = missingGlyph.getBBox();
          float glyphWidth = (glyphBBox.getX()*2 + glyphBBox.getWidth())*scaleFactor;
          width += glyphWidth;
         } else {
          width += horizAdvX*scaleFactor;
        }
      }
    }
    bounds.setRect(left, top, width, bottom-top);
    return bounds;
  }

  public void drawText(Graphics2D graphics, SVGTextElementImpl parent, String text,
                       float x, float y, float fontSize, boolean refreshData) {

     float horizAdvX = getHorizAdvX();
     float fontAscent = getFontAscent();
     //float scaleFactor = fontSize/fontAscent;
     float scaleFactor = fontSize/getFontUnitsPerEm();

     int numChars = text.length();
     float xPos = x;
     int position = ISOLATED;
     for (int i = 0; i < numChars; i++) {
       String ch = "" + text.charAt(i);

       // ouch!!! horrible if that should be simplified
       // this handles the arabic glyph selection
       boolean spaceBefore = false;
       boolean spaceAfter = false;

       if (numChars == 1 || i == 0 || text.charAt(i-1) == ' ') {
	   spaceBefore = true;
       }
       if ((i+1) == numChars || text.charAt(i+1) == ' ') {
	   spaceAfter = true;
       }

       if (spaceBefore && spaceAfter) {
	   position = ISOLATED;
       } else if (spaceBefore && !spaceAfter) {
	   position = INITIAL;
       } else if (!spaceBefore && spaceAfter) {
	   position = TERMINAL;
       } else if (!spaceBefore && !spaceAfter) {
	   position = MEDIAL;
       }
       
		  
       SVGGlyphElementImpl glyph = getGlyph(ch, position);
       if (glyph != null) {
         glyph.drawGlyph(graphics, parent, xPos, y, fontSize, refreshData);
         // calculate next xPos
	 float glyphWidth = 0f;
	 if (glyph.getHorizAdvX() != 0) {
	     glyphWidth = glyph.getHorizAdvX() * scaleFactor;
	 } else {
	     SVGRect glyphBBox = glyph.getBBox();
	     glyphWidth = (glyphBBox.getX()*2 + glyphBBox.getWidth())*scaleFactor;
	 }
         xPos += glyphWidth;
       } else {
         // draw missing glyph
         SVGMissingGlyphElementImpl missingGlyph = getMissingGlyph();
         if (missingGlyph != null) {
           missingGlyph.drawGlyph(graphics, parent, xPos, y, fontSize, refreshData);
           // calulate next xPos
           float glyphWidth = 0f;
	   if (missingGlyph.getHorizAdvX() != 0) {
	       glyphWidth = missingGlyph.getHorizAdvX() * scaleFactor;
	   } else {
	       SVGRect glyphBBox = missingGlyph.getBBox();
	       glyphWidth = (glyphBBox.getX()*2 + glyphBBox.getWidth())*scaleFactor;
	   }
           xPos += glyphWidth;
         } else {
           xPos += horizAdvX*scaleFactor;
         }
       }
     }
  }


  public void attachAnimation(SVGAnimationElementImpl animation) {
    String attName = animation.getAttributeName();
    if (attName.equals("externalResourcesRequired")) {
      ((SVGAnimatedValue)getExternalResourcesRequired()).addAnimation(animation);
    } else {
      super.attachAnimation(animation);
    }
  }

}
