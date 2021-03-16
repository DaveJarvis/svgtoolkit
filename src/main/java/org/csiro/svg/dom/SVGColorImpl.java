// SVGColorImpl.java
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
// $Id: SVGColorImpl.java,v 1.8 2002/02/21 07:46:09 brett Exp $
//

package org.csiro.svg.dom;

import org.w3c.css.sac.InputSource;
import com.steadystate.css.parser.CSSOMParser;
import org.w3c.dom.DOMException;
import org.w3c.dom.css.RGBColor;
import org.w3c.dom.svg.SVGColor;
import org.w3c.dom.svg.SVGException;
import org.w3c.dom.svg.SVGICCColor;

import java.io.StringReader;
import java.io.IOException;
import java.awt.Color;

/**
 * SVGColorImpl is the implementation of org.w3c.dom.svg.SVGColor
 */
public class SVGColorImpl implements SVGColor {

  protected short colorType;
  protected org.w3c.dom.css.RGBColor rgbColor;
  protected SVGICCColor iccColor;

  public SVGColorImpl() {
    colorType = SVG_COLORTYPE_UNKNOWN;
  }

  public SVGColorImpl(String cssText) {
    setCssText(cssText);
  }

  public String getCssText() {
    if (colorType == SVG_COLORTYPE_RGBCOLOR) {
      return "rgb(" + rgbColor.getRed().getCssText() + ", "
                    + rgbColor.getGreen().getCssText() + ", "
                    + rgbColor.getBlue().getCssText() + ")";
    } else if (colorType == SVG_COLORTYPE_RGBCOLOR_ICCCOLOR) {
      return iccColor.getColorProfile();
    } else {
      return "";
    }
  }

  public void setCssText(String cssText) throws DOMException {

    CSSOMParser parser = new CSSOMParser();
    org.w3c.dom.css.CSSValue colorVal = null;
    try {
      colorVal = parser.parsePropertyValue(new InputSource(new StringReader(cssText)));
    } catch (IOException e) {
      System.out.println(e.getMessage());
      return;
    }

    if (colorVal != null && colorVal.getCssValueType() == org.w3c.dom.css.CSSValue.CSS_PRIMITIVE_VALUE) {
      if (((org.w3c.dom.css.CSSPrimitiveValue)colorVal).getPrimitiveType() == org.w3c.dom.css.CSSPrimitiveValue.CSS_RGBCOLOR) {

        rgbColor = new RGBColorImpl(colorVal);
        colorType = SVG_COLORTYPE_RGBCOLOR;

      } else { // must be one of the predefined colours
        Color col = getColor(cssText);
        String rgbString = "rgb(" + col.getRed() + "," + col.getGreen() + "," + col.getBlue() + ")";
        CSSOMParser parser2 = new CSSOMParser();
        org.w3c.dom.css.CSSValue colorVal2 = null;
        try {
          colorVal2 = parser2.parsePropertyValue(new InputSource(new StringReader(rgbString)));
          rgbColor = new RGBColorImpl(colorVal2);
          colorType = SVG_COLORTYPE_RGBCOLOR;
        } catch (IOException e) {
          System.out.println(e.getMessage());
        }
      }
    }
  }

  public short getCssValueType() {
    return org.w3c.dom.css.CSSValue.CSS_CUSTOM;
  }


  public short getColorType() {
    return colorType;
  }

  public RGBColor getRGBColor( ) {
    return rgbColor;
  }

  public SVGICCColor getICCColor( ) {
    return iccColor;
  }


  public void setRGBColor (String rgbColor) throws SVGException {
    setCssText(rgbColor);
  }

  public void setRGBColorICCColor (String rgbColor, String iccColor )
                                      throws SVGException {
    setCssText(rgbColor);
  //  this.iccColor = iccColor;
  }


  public void setColor ( short colorType, String rgbColor, String iccColor )
                                      throws SVGException {
  }

  private static SVGColorDecoder colorDecoder = new SVGColorDecoder();

  /**
   * Returns the Color object that represents the specified color name.
   * @param color The name of the color, should be one of the 16 predefined colours.
   * @return The Color object that represents the specified color name.
   */
  private static Color getColor(String color) {
    Color col = colorDecoder.getColor(color);
    if (col == null) {
      System.out.println("cannot decode colour: " + color);
      return Color.black;
    }
    return col;
  }

  public Color getColor() {
    if (colorType == SVG_COLORTYPE_RGBCOLOR) {
      return ((RGBColorImpl)rgbColor).getColor();
    }
    return null;
  }

} // SVGColorImpl
