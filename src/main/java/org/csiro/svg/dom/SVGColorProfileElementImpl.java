// SVGColorProfileElementImpl.java
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
// $Id: SVGColorProfileElementImpl.java,v 1.2 2002/02/21 07:46:09 brett Exp $
//

package org.csiro.svg.dom;

import java.util.Vector;
import java.awt.*;
import java.awt.image.*;

import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGAnimatedString;
import org.w3c.dom.svg.SVGColorProfileElement;

import java.awt.color.*;
import java.io.*;

public class SVGColorProfileElementImpl extends SVGElementImpl implements
  SVGColorProfileElement {

  protected short renderingIntent;
  protected String name;
  protected String local;
  protected SVGAnimatedString href;

  private static Vector filterUnitStrings;
  private static Vector filterUnitValues;

  public SVGColorProfileElementImpl(SVGDocumentImpl owner) {
    super(owner, "color-profile");
  }

  public SVGColorProfileElementImpl(SVGDocumentImpl owner, Element elem) {
    super(owner, elem, "color-profile");
  }

  public SVGElementImpl cloneElement() {

      // No animations on a color-profile
      SVGColorProfileElementImpl newProfile = new SVGColorProfileElementImpl(getOwnerDoc(), this);
      return newProfile;
  }


  public short getRenderingIntent() {
    return renderingIntent;
  }

  public void setRenderingIntent(short renderingIntent) throws
    org.w3c.dom.DOMException {
      throw new org.w3c.dom.DOMException( org.w3c.dom.DOMException.INDEX_SIZE_ERR, "Read only");
  }

  public String getLocal() {
    return local;
  }

  public void setLocal(String local) throws org.w3c.dom.DOMException {
      throw new org.w3c.dom.DOMException( org.w3c.dom.DOMException.INDEX_SIZE_ERR, "Read only");
  }

  public String getName() {
    return name;
  }

  public void setName(String name) throws org.w3c.dom.DOMException {
      throw new org.w3c.dom.DOMException( org.w3c.dom.DOMException.INDEX_SIZE_ERR, "Read only");
  }


  // implementation of SVGLangSpace interface

  public String getXMLlang() {
    return getAttribute("xml:lang");
  }

  public void setXMLlang(String xmllang) {
    if (xmllang != null) {
      super.setAttribute("xml:lang", xmllang);
    } else {
      removeAttribute("xml:lang");
    }
  }

  public String getXMLspace() {
    return getAttribute("xml:space");
  }

  public void setXMLspace(String xmlspace) {
    if (xmlspace != null) {
      super.setAttribute("xml:space", xmlspace);
    } else {
      removeAttribute("xml:space");
    }
  }


  // implementation of SVGURIRefenence interface

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


    public String getAttribute(String name) {

    if (name.equalsIgnoreCase("name")) {
      return getName();
    } else if (name.equalsIgnoreCase("local")) {
      return getLocal();
    } else if (name.equalsIgnoreCase("rendering-intent")) {
      return new Short(getRenderingIntent()).toString();
    } else if (name.equalsIgnoreCase("xlink:href")) {
      return getHref().getBaseVal();

    } else {
      return super.getAttribute(name);
    }
  }

  public org.w3c.dom.Attr getAttributeNode( String name) {
    org.w3c.dom.Attr attr = super.getAttributeNode( name);
    if (attr == null)
      return attr;

    if (name.equalsIgnoreCase("name")) {
      attr.setValue(getName());
    } else if (name.equalsIgnoreCase("local")) {
      attr.setValue(getLocal());
    } else if (name.equalsIgnoreCase("rendering-intent")) {
      attr.setValue(getAttribute("rendering-intent"));
   } else if (name.equalsIgnoreCase("xlink:href")) {
      attr.setValue(getHref().getBaseVal());
    }
    return attr;
  }


  public void setAttribute(String name, String value) {

    super.setAttribute(name, value);

     if (name.equalsIgnoreCase("name")) {
         this.name = value;
    } else if (name.equalsIgnoreCase("local")) {
        this.local = value;
    } else if (name.equalsIgnoreCase("rendering-intent")) {
      // @@@ whoops - get the enumeration
      //this.renderingIntent = Short.parseShort(value);
   } else if (name.equalsIgnoreCase("xlink:href")) {
      getHref().setBaseVal(value);
    }
  }

  public org.w3c.dom.Attr setAttributeNode( org.w3c.dom.Attr newAttr) throws
    org.w3c.dom.DOMException {
    String name = newAttr.getName();
    String value = newAttr.getValue();

     if (name.equalsIgnoreCase("name")) {
      this.name = value;
     } else if (name.equalsIgnoreCase("local")) {
         this.local = value;
     } else if (name.equalsIgnoreCase("rendering-intent")) {
         this.renderingIntent = Short.parseShort(value);
    } else if (name.equalsIgnoreCase("xlink:href")) {
      getHref().setBaseVal(value);

    }
    return super.setAttributeNode(newAttr);
  }

    ICC_Profile newProfile = null;
    ColorSpace newSpace = null;
    ColorSpace defaultSpace = null;
    Canvas tmpComponent = null;

    protected boolean loadProfile() {
        try {
            newProfile = ICC_Profile.getInstance(new FileInputStream(getHref().getBaseVal()));
            System.out.println("Profile: " + newProfile);

            //defaultSpace = ColorSpace.getInstance(ColorSpace.CS_sRGB);

            if (newProfile instanceof ICC_ProfileRGB) {
                ICC_ProfileRGB rgbProf = (ICC_ProfileRGB)newProfile;
                System.out.println("Matrix == ");
                float [][] mat = rgbProf.getMatrix();
                for (int p=0; p < 3; p++) {
                    for (int q=0; q< 3; q++) {
                        System.out.println(p + " " + q + " " + mat[p][q]);
                    }
                }
                for (int b=0; b<3; b++) {
                    int comp = 0;
                    switch (b) {
                    case 0: comp = ICC_ProfileRGB.REDCOMPONENT; break;
                    case 1: comp = ICC_ProfileRGB.GREENCOMPONENT; break;
                    case 2: comp = ICC_ProfileRGB.BLUECOMPONENT; break;
                    }
                    try {
                        float gamma = rgbProf.getGamma(comp);
                        System.out.println("Gamma: " + gamma);
                    } catch (Exception e) {
                    }

                    try {
                        short [] curve = rgbProf.getTRC(comp);
                        System.out.print("Curve:");
                        for (int i=0; i<curve.length; i++)
                            System.out.print(" " + (((int)curve[i])&0xFFFF) / 65535.0);
                        System.out.println();
                    } catch (Exception e) {
                    }

                }
            }

        } catch (Exception e) {
           System.out.println("\n    File not found error: " + getHref().getBaseVal() + "\n");
           return false;
        }
        return true;
    }

    public Image applyProfile(Image inImage) {

        if (!loadProfile()) {
          // File not found exception
          return null;
        }
        tmpComponent = new Canvas();

        BufferedImage rgbImage = new BufferedImage(inImage.getWidth(tmpComponent),
            inImage.getHeight(tmpComponent), BufferedImage.TYPE_INT_ARGB_PRE);

        Graphics2D g2d = (Graphics2D) rgbImage.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
                             RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.drawImage(inImage, 0, 0, tmpComponent);
        g2d.dispose();

        ColorModel rgbCM = rgbImage.getColorModel();
        ColorSpace rgbSpace = rgbCM.getColorSpace();

        ColorModel rgbCompCM = rgbCM;
        if (!(rgbCM instanceof ComponentColorModel)) {

            // All this nonsense is to ensure the image is in a component
            // sample model, so we can use a ComponentColorModel.  The only
            // concrete PackedColorModel is DirectColorModel which is RGB only.

            System.out.println("Convert to ComponentColorModel");

            rgbCompCM =
                new ComponentColorModel(rgbCM.getColorSpace(),
                                        rgbCM.getComponentSize(),
                                        false, false,
                                        Transparency.TRANSLUCENT,
                                        DataBuffer.TYPE_BYTE);

                WritableRaster wr;
                wr = rgbCompCM.createCompatibleWritableRaster
                    (rgbImage.getWidth(), rgbImage.getHeight());

                rgbImage = new BufferedImage(rgbCompCM, wr,
                                             rgbCompCM.isAlphaPremultiplied(),
                                             null);
                g2d = (Graphics2D) rgbImage.getGraphics();
                g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
                                     RenderingHints.VALUE_COLOR_RENDER_QUALITY);
                g2d.drawImage(inImage, 0, 0, tmpComponent);
                g2d.dispose();
                rgbCM = rgbCompCM;
        }

        newSpace = new ICC_ColorSpace(newProfile);

        ColorModel newCM =
            new ComponentColorModel(newSpace, rgbCM.getComponentSize(),
                                    rgbCM.hasAlpha(),
                                    rgbCM.isAlphaPremultiplied(),
                                    Transparency.TRANSLUCENT,
                                    DataBuffer.TYPE_BYTE);

        System.out.println("Before new BI");
        // USe the old image data with the new color model.
        BufferedImage newImage = new BufferedImage(newCM, rgbImage.getRaster(),
                                     newCM.isAlphaPremultiplied(),
                                     null);
        System.out.println("After new BI");
        //ICC_Profile sRGBProfile = null;
        //try {
        //    sRGBProfile = ICC_Profile.getInstance
        //    (new FileInputStream("sRGB Color Space Profile.icm"));
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}
        //ColorSpace sRGBSpace = new ICC_ColorSpace(sRGBProfile);
        //ColorModel sRGBCM =
        //    new ComponentColorModel(sRGBSpace, rgbCM.getComponentSize(),
        //                            false,
        //                            rgbCM.isAlphaPremultiplied(),
        //                            Transparency.TRANSLUCENT,
        //                            DataBuffer.TYPE_BYTE);

        //sRGBImage = new BufferedImage(sRGBCM,
        //                             sRGBCM.createCompatibleWritableRaster
        //                             (rgbImage.getWidth(),
        //                              rgbImage.getHeight()),
        //                             sRGBCM.isAlphaPremultiplied(), null);

        //ColorConvertOp cco = new ColorConvertOp(null);
        //cco.filter(newImage, sRGBImage);
        //sRGBImage = new BufferedImage(rgbCompCM, sRGBImage.getRaster(),
        //                              rgbCompCM.isAlphaPremultiplied(), null);

        return newImage;
    }

   public void attachAnimation(SVGAnimationElementImpl animation) {

  }


}


