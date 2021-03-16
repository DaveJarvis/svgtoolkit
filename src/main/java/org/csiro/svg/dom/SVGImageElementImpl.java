// SVGImageElementImpl.java
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
// $Id: SVGImageElementImpl.java,v 1.42 2002/02/21 07:46:09 brett Exp $
//

package org.csiro.svg.dom;

import java.awt.geom.*;
import java.awt.*;
import java.awt.image.*;
import org.csiro.svg.parser.*;
import java.net.*;

import org.w3c.dom.Element;
import org.w3c.dom.svg.*;

import java.util.Vector;
import java.util.StringTokenizer;

/**
 * SVGImageElementImpl is the implementation of org.w3c.dom.svg.SVGImageElement
 */
public class SVGImageElementImpl extends SVGGraphic implements SVGImageElement, Drawable {

  protected SVGAnimatedLength x;
  protected SVGAnimatedLength y;
  protected SVGAnimatedLength width;
  protected SVGAnimatedLength height;
  protected SVGAnimatedString href;
  protected SVGAnimatedPreserveAspectRatio preserveAspectRatio;

  /**
   * Simple constructor, image initialized to: x=0, y=0, width=0, height=0.
   */
  public SVGImageElementImpl(SVGDocumentImpl owner) {
    super(owner, "image");

    // setup initial attributes with some defaults
    super.setAttribute("x", getX().getBaseVal().getValueAsString());
    super.setAttribute("y", getY().getBaseVal().getValueAsString());
    super.setAttribute("width", getWidth().getBaseVal().getValueAsString());
    super.setAttribute("height", getHeight().getBaseVal().getValueAsString());
    super.setAttribute("href", getHref().getBaseVal());
    super.setAttribute("preserveAspectRatio", getPreserveAspectRatio().getBaseVal()
                                                                      .toString());
  }

  /**
   * Constructor using an XML Parser
   */
  public SVGImageElementImpl(SVGDocumentImpl owner, Element elem) {
    super(owner, elem, "image");
  }

  public SVGElementImpl cloneElement() {
    SVGImageElementImpl newImage = new SVGImageElementImpl(getOwnerDoc(), this);

    Vector xAnims = ((SVGAnimatedLengthImpl)getX()).getAnimations();
    Vector yAnims = ((SVGAnimatedLengthImpl)getY()).getAnimations();
    Vector widthAnims = ((SVGAnimatedLengthImpl)getWidth()).getAnimations();
    Vector heightAnims = ((SVGAnimatedLengthImpl)getHeight()).getAnimations();
    Vector hrefAnims = ((SVGAnimatedStringImpl)getHref()).getAnimations();
    Vector transformAnims = ((SVGAnimatedTransformListImpl)getTransform()).getAnimations();
    Vector preserveAspectRatioAnims = ((SVGAnimatedPreserveAspectRatioImpl)getPreserveAspectRatio()).getAnimations();

    if (xAnims != null) {
      for (int i = 0; i < xAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)xAnims.elementAt(i);
        newImage.attachAnimation(anim);
      }
    }
    if (yAnims != null) {
      for (int i = 0; i < yAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)yAnims.elementAt(i);
        newImage.attachAnimation(anim);
      }
    }
    if (widthAnims != null) {
      for (int i = 0; i < widthAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)widthAnims.elementAt(i);
        newImage.attachAnimation(anim);
      }
    }
    if (heightAnims != null) {
      for (int i = 0; i < heightAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)heightAnims.elementAt(i);
        newImage.attachAnimation(anim);
      }
    }
    if (hrefAnims != null) {
      for (int i = 0; i < hrefAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)hrefAnims.elementAt(i);
        newImage.attachAnimation(anim);
      }
    }
    if (transformAnims != null) {
      for (int i = 0; i < transformAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)transformAnims.elementAt(i);
        newImage.attachAnimation(anim);
      }
    }
    if (preserveAspectRatioAnims != null) {
      for (int i = 0; i < preserveAspectRatioAnims.size(); i++) {
        SVGAnimationElementImpl anim = (SVGAnimationElementImpl)preserveAspectRatioAnims.elementAt(i);
        newImage.attachAnimation(anim);
      }
    }
    if (animatedProperties != null) {
      newImage.animatedProperties = animatedProperties;
    }
    return newImage;
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

  public SVGAnimatedLength getWidth() {
    if (width == null) {
      width = new SVGAnimatedLengthImpl(new SVGLengthImpl(0, this, SVGLengthImpl.X_DIRECTION), this);
    }
    return width;
  }

  public SVGAnimatedLength getHeight() {
    if (height == null) {
      height = new SVGAnimatedLengthImpl(new SVGLengthImpl(0, this, SVGLengthImpl.Y_DIRECTION), this);
    }
    return height;
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


  public SVGAnimatedPreserveAspectRatio getPreserveAspectRatio() {
    if (preserveAspectRatio == null) {
      preserveAspectRatio = new SVGAnimatedPreserveAspectRatioImpl(new SVGPreserveAspectRatioImpl(), this);
    }
    return preserveAspectRatio;
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
    } else if (name.equalsIgnoreCase("xlink:href")) {
      return getHref().getBaseVal();
    } else if (name.equalsIgnoreCase("preserveAspectRatio")) {
      return getPreserveAspectRatio().getBaseVal().toString();
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
    } else if (name.equalsIgnoreCase("xlink:href")) {
      attr.setValue(getHref().getBaseVal());
    } else if (name.equalsIgnoreCase("preserveAspectRatio")) {
      attr.setValue( getPreserveAspectRatio().getBaseVal().toString());
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
    } else if (name.equalsIgnoreCase("xlink:href")) {
      getHref().setBaseVal(value);
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
    }
  }



  boolean visible = true;
  boolean display = true;
  AffineTransform thisTransform = null;
  Shape clipShape = null;
  Image image = null;
  Image imageWithProfile = null;
  SVGDocumentImpl doc = null;
  float opacity = 1;
  SVGClipPathElementImpl clipPath = null;
  String oldHref = null;

  public void draw(Graphics2D graphics, boolean refreshData) {

    if (refreshData || (image == null && doc == null)) {

      refreshData();  // tells all of the stylable stuff to refresh any cached data

      // get visibility
      visible = getVisibility();
      display = getDisplay();

      // get opacity
      opacity = getOpacity();

      // get clipping shape
      clipPath = getClippingPath();
      clipShape = null;
      if (clipPath != null) {
        clipShape = clipPath.getClippingShape(this);
      }

      // get transform matrix
      thisTransform = ((SVGTransformListImpl)getTransform().getAnimVal()).getAffineTransform();

      // get either Image object or an SVGDocumentImpl

      String imageRef = getHref().getAnimVal();

      if (oldHref == null || !oldHref.equals(imageRef)) {

        image = null;
        doc = null;


       if (imageRef != null && imageRef.length() > 0)  {
        oldHref = imageRef;

        if (imageRef.toLowerCase().endsWith(".svg")) {     // need to read in another svg file

          if (!imageRef.startsWith("http://") && !imageRef.startsWith("ftp://")
              && !imageRef.startsWith("file:")) {
            SVGDocument parentSvgDoc = (SVGDocument)getOwnerDocument();
            if (parentSvgDoc != null && parentSvgDoc.getURL() != null) {
              String url = parentSvgDoc.getURL();
              if ((url.indexOf('/') != -1) || (url.indexOf('\\') != -1)) {
                int index = url.lastIndexOf('/');
                if (index == -1)
                  index = url.lastIndexOf('\\');
                String svgDocPath = url.substring(0,index+1);
                imageRef = svgDocPath + imageRef;
              }
            }
          }

          try {
            SVGParser parser = new SVGParser();
            doc = parser.parseSVG(imageRef);
            if (doc == null) {
				return;
			}

          } catch (SVGParseException e) {
            System.out.println("parse exception while reading: " + imageRef);
            System.out.println(e.getMessage());
          }

        } else {   // is a normal image file eg. jpeg or png
          String svgDocPath = "";
          SVGDocument parentSvgDoc = (SVGDocument)getOwnerDocument();
          if (parentSvgDoc != null && parentSvgDoc.getURL() != null) {
            String url = parentSvgDoc.getURL();
            // System.out.println("doc url = " + url);
            if ((url.indexOf('/') != -1) || (url.indexOf('\\') != -1)) {
              int index = url.lastIndexOf('/');
              if (index == -1)
                index = url.lastIndexOf('\\');
              svgDocPath = url.substring(0,index+1);
            }
          }

          //   System.out.println("path = " + svgDocPath);

          // absolute whopper of a conditional
          if (imageRef != null && !imageRef.startsWith("http://") &&
                    !imageRef.startsWith("ftp://") && !imageRef.startsWith("file:")
                    && svgDocPath != null
                    && (svgDocPath.startsWith("http://") ||
                        svgDocPath.startsWith("ftp://"))) {
            imageRef = svgDocPath + imageRef;
          }
          //  System.out.println("imageref = " + imageRef);

          if (imageRef != null &&
              (imageRef.startsWith("http://") ||
              imageRef.startsWith("file:") ||
              imageRef.startsWith("ftp://"))) {
            try {
              URL url = new URL(imageRef);
              // image = Toolkit.getDefaultToolkit().getImage(url);
              byte[] imageData = org.csiro.svg.tools.ConnectionManager.getInstance().retrieveData(url);
              image = Toolkit.getDefaultToolkit().createImage(imageData);
            } catch (MalformedURLException e) {
              System.out.println("Bad URL in image element : " + imageRef);
            }
          } else {
            //   System.out.println("trying to load image: " + svgDocPath + imageRef);
            image = Toolkit.getDefaultToolkit().getImage(svgDocPath + imageRef);
          }
          if (image != null) {
            // wait till loaded
            MediaTracker tracker = new MediaTracker(new Canvas());
            tracker.addImage(image,0);
            try {
              tracker.waitForAll();
            } catch (InterruptedException e) {}
            //  System.out.println("got image");
          }
        }
      }
      }
    }


    if (visible && display && opacity > 0) {

      // save current settings
      AffineTransform oldGraphicsTransform = graphics.getTransform();
      Shape oldClip = graphics.getClip();
      Composite oldComposite = graphics.getComposite();

      // do transformations
      graphics.transform(thisTransform);

      // do clipping
      if (clipShape != null) {
        graphics.clip(clipShape);
      }

      // draw image

      if (doc != null) { // image is another SVG document

        SVGSVGElementImpl imageRoot = (SVGSVGElementImpl)doc.getRootElement();
        SVGRect viewport = imageRoot.getViewport();

        if (opacity < 1) {

          // need to draw into an offscreen buffer
          SVGSVGElement root = getOwnerDoc().getRootElement();
          float currentScale = root.getCurrentScale();
          SVGPoint currentTranslate = root.getCurrentTranslate();
          if (currentTranslate == null) {
            currentTranslate = new SVGPointImpl();
          }

          // create buffer image to draw on

          SVGRect bbox = getBBox();
          Shape shape = new Rectangle2D.Double(bbox.getX(), bbox.getY(),
                                         bbox.getWidth(), bbox.getHeight());
          AffineTransform screenCTM = ((SVGMatrixImpl)getScreenCTM()).getAffineTransform();
          Shape transformedShape = screenCTM.createTransformedShape(shape);
          Rectangle2D bounds = transformedShape.getBounds2D();

          // make bounds 10% bigger to make sure don't cut off edges
          double xInc = bounds.getWidth() / 5;
          double yInc = bounds.getHeight() / 5;
          bounds.setRect(bounds.getX() - xInc, bounds.getY() - yInc,
                       bounds.getWidth() + 2*xInc, bounds.getHeight() + 2*yInc);


          int imageWidth = (int)(bounds.getWidth() * currentScale);
          int imageHeight = (int)(bounds.getHeight() * currentScale);

          if (imageWidth > 0 && imageHeight > 0) {

            BufferedImage image = new BufferedImage(imageWidth, imageHeight,
                                                  BufferedImage.TYPE_4BYTE_ABGR);

            Graphics2D offGraphics = (Graphics2D)image.getGraphics();
            RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
					   	                                    RenderingHints.VALUE_ANTIALIAS_ON);
            offGraphics.setRenderingHints(hints);

            // do the zoom and pan transformations here
            if (currentScale != 1) {
              offGraphics.scale(currentScale,currentScale);
            }

            // do translate so that group is drawn at origin
            offGraphics.translate(-bounds.getX(), -bounds.getY());

            // do the transform to the current coord system
            offGraphics.transform(screenCTM);

            // need to transform so fits into image's boundary
            float sx = getWidth().getAnimVal().getValue()/viewport.getWidth();
            float sy = getHeight().getAnimVal().getValue()/viewport.getHeight();
            offGraphics.scale(sx, sy);

            float tx = getX().getAnimVal().getValue()/sx - viewport.getX();
            float ty = getY().getAnimVal().getValue()/sy - viewport.getY();
            offGraphics.translate(tx, ty);

            // draw image to offscreen buffer
            doc.draw(offGraphics);

            // draw buffer image to graphics
            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
            graphics.setComposite(ac);
            AffineTransform imageTransform = AffineTransform.getTranslateInstance(bounds.getX(), bounds.getY());
            imageTransform.scale(1/currentScale, 1/currentScale);
            try {
              imageTransform.preConcatenate(screenCTM.createInverse());
            } catch (NoninvertibleTransformException e) {}
            graphics.drawImage(image, imageTransform , null);
            image.flush();
         }

        } else {     // draw normally

          // need to transform so fits into image's boundary
          float sx = getWidth().getAnimVal().getValue()/viewport.getWidth();
          float sy = getHeight().getAnimVal().getValue()/viewport.getHeight();
          graphics.scale(sx, sy);

          float tx = getX().getAnimVal().getValue()/sx - viewport.getX();
          float ty = getY().getAnimVal().getValue()/sy - viewport.getY();
          graphics.translate(tx, ty);

          doc.draw(graphics);
        }

      } else {  // is a normal image file eg. jpeg or png
        if (image != null) {
          int realWidth = image.getWidth(null);
          int realHeight = image.getHeight(null);

          float drawWidth = getWidth().getAnimVal().getValue();
          float drawHeight = getHeight().getAnimVal().getValue();

          AffineTransform at = new AffineTransform();
          at.translate(getX().getAnimVal().getValue(), getY().getAnimVal().getValue());
          at.scale(drawWidth / realWidth, drawHeight / realHeight);

          if (opacity < 1) {
            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
            graphics.setComposite(ac);
          }

          // this is also wrong .. I want to make sure
          // the conversion doesn't happen all the time since
          // it takes a while
          SVGColorProfileElementImpl colProf = getColorProfile();
          if (colProf != null) {
              if (imageWithProfile == null) {
                  System.out.println("Got a profile -- apply it");
                  imageWithProfile = colProf.applyProfile(image);
                  if (imageWithProfile == null) {
                    imageWithProfile = image;
                    System.out.println("Could not apply profile.");
                    System.out.println("Drawing without profile");
                  } else {
                    System.out.println("Profile Applied");
                    System.out.println("Drawing with profile");
                  }
              }

              graphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
                                     RenderingHints.VALUE_COLOR_RENDER_QUALITY);
              graphics.drawImage(imageWithProfile, at, null);
         } else {
              //System.out.println("Drawing with no profile");
              graphics.drawImage(image, at, null);
         }
        }
      }

      // restore old settings
      graphics.setTransform(oldGraphicsTransform);
      graphics.setClip(oldClip);
      graphics.setComposite(oldComposite);
    }
  }

  /**
   * Returns the tight bounding box in current user space (i.e., after
   * application of the transform attribute) on the geometry of all contained
   * graphics elements, exclusive of stroke-width and filter effects.
   * @return An SVGRect object that defines the bounding box.
   */
  public SVGRect getBBox() {

    AffineTransform ctm = ((SVGMatrixImpl)getCTM()).getAffineTransform();
    AffineTransform inverseTransform;
    try {
      inverseTransform = ctm.createInverse();
    } catch (NoninvertibleTransformException e) {
      inverseTransform = null;
    }
    float x = ((SVGLengthImpl)getX().getAnimVal()).getTransformedLength(inverseTransform);
    float y = ((SVGLengthImpl)getY().getAnimVal()).getTransformedLength(inverseTransform);
    float width = ((SVGLengthImpl)getWidth().getAnimVal()).getTransformedLength(inverseTransform);
    float height = ((SVGLengthImpl)getHeight().getAnimVal()).getTransformedLength(inverseTransform);

    SVGRect rect = new SVGRectImpl(x, y, width, height);
    return rect;
  }

  public boolean contains(double x, double y) {
    SVGRect bounds = getBBox();
    Shape shape = new Rectangle2D.Double(bounds.getX(), bounds.getY(),
                                         bounds.getWidth(), bounds.getHeight());
    AffineTransform screenCTM = ((SVGMatrixImpl)getScreenCTM()).getAffineTransform();
    Shape transformedShape = screenCTM.createTransformedShape(shape);
    return transformedShape.contains(x,y);
  }

  /**
   * Returns the area of the actual bounding box in the document's coordinate
   * system.  i.e its size when drawn on the screen.
   */
  public double boundingArea() {
    SVGRect bounds = getBBox();
    Shape shape = new Rectangle2D.Double(bounds.getX(), bounds.getY(),
                                         bounds.getWidth(), bounds.getHeight());
    AffineTransform screenCTM = ((SVGMatrixImpl)getScreenCTM()).getAffineTransform();
    Shape transformedShape = screenCTM.createTransformedShape(shape);
    Rectangle2D transBounds = transformedShape.getBounds2D();
    return (transBounds.getWidth() * transBounds.getHeight());
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
    } else if (attName.equals("xlink:href")) {
      ((SVGAnimatedValue)getHref()).addAnimation(animation);
    } else {
      super.attachAnimation(animation);
    }
  }


} // SVGImageElementImpl
