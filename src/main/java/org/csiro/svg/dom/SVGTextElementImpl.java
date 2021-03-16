// SVGTextElementImpl.java
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
// $Id: SVGTextElementImpl.java,v 1.46 2002/03/11 02:09:54 bella Exp $
//

package org.csiro.svg.dom;


import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.svg.*;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * SVGTextElementImpl is the implementation of org.w3c.dom.svg.SVGTextElement
 */
public class SVGTextElementImpl extends SVGGraphic
  implements SVGTextElement, Drawable, BasicShape {

  /**
   * Align the text at the start of the line.
   */
  static public final int ALIGN_START = 0;
  /**
   * Align the text at the middle of the line.
   */
  static public final int ALIGN_MIDDLE = 1;
  /**
   * Align the text at the end of the line.
   */
  static public final int ALIGN_END = 2;

  /**
   * Use the spacing between the glyphs to adjust for textLength.
   */
  static public final int ADJUST_SPACING = 1;
  /**
   * Use the entire glyph to adjust for textLength.
   */
  static public final int ADJUST_GLYPHS = 2;


  protected SVGAnimatedLengthList x;
  protected SVGAnimatedLengthList y;
  protected SVGAnimatedLength textLength;
  protected SVGAnimatedEnumeration lengthAdjust;

  private static Vector lengthAdjustStrings;
  private static Vector lengthAdjustValues;

  private static final boolean mHasAffineTransform = getVersion() >= 4;

  private static boolean hasAffineTransform() {
    return mHasAffineTransform;
  }

  private static int getVersion() {
    String version = System.getProperty( "java.version" );
    if( version.startsWith( "1." ) ) {
      version = version.substring( 2, 3 );
    }
    else {
      int dot = version.indexOf( "." );
      if( dot != -1 ) { version = version.substring( 0, dot ); }
    }

    return Integer.parseInt( version );
  }

  /**
   * Simple constructor, x and y attributes are initialized to 0
   */
  public SVGTextElementImpl( SVGDocumentImpl owner ) {
    this( owner, "text" );
  }

  public SVGTextElementImpl( SVGDocumentImpl owner, final String name ) {
    super( owner, name );
    super.setAttribute( "x", "0" );
    super.setAttribute( "y", "0" );
  }

  /**
   * Constructor using an XML Parser
   */
  public SVGTextElementImpl( SVGDocumentImpl owner, Element elem ) {
    this( owner, elem, "text" );
  }

  /**
   * Constructor using an XML Parser
   */
  public SVGTextElementImpl( SVGDocumentImpl owner, Element elem,
                             final String name ) {
    super( owner, elem, name );
  }

  public SVGElementImpl cloneElement() {
    var newText = new SVGTextElementImpl( getOwnerDoc(), this );

    var xAnims = ((SVGAnimatedLengthListImpl) getX()).getAnimations();
    var yAnims = ((SVGAnimatedLengthListImpl) getY()).getAnimations();
    var textLengthAnims =
      ((SVGAnimatedLengthImpl) getTextLength()).getAnimations();
    var lengthAdjustAnims =
      ((SVGAnimatedEnumerationImpl) getLengthAdjust()).getAnimations();
    var transformAnims =
      ((SVGAnimatedTransformListImpl) getTransform()).getAnimations();

    if( xAnims != null ) {
      for( var i = 0; i < xAnims.size(); i++ ) {
        var anim = (SVGAnimationElementImpl) xAnims.elementAt( i );
        newText.attachAnimation( anim );
      }
    }
    if( yAnims != null ) {
      for( var i = 0; i < yAnims.size(); i++ ) {
        var anim = (SVGAnimationElementImpl) yAnims.elementAt( i );
        newText.attachAnimation( anim );
      }
    }
    if( textLengthAnims != null ) {
      for( var i = 0; i < textLengthAnims.size(); i++ ) {
        var anim = (SVGAnimationElementImpl) textLengthAnims.elementAt( i );
        newText.attachAnimation( anim );
      }
    }
    if( lengthAdjustAnims != null ) {
      for( var i = 0; i < lengthAdjustAnims.size(); i++ ) {
        var anim = (SVGAnimationElementImpl) lengthAdjustAnims.elementAt( i );
        newText.attachAnimation( anim );
      }
    }
    if( transformAnims != null ) {
      for( var i = 0; i < transformAnims.size(); i++ ) {
        var anim = (SVGAnimationElementImpl) transformAnims.elementAt( i );
        newText.attachAnimation( anim );
      }
    }
    if( animatedProperties != null ) {
      newText.animatedProperties = animatedProperties;
    }
    return newText;

  }

  private void initLengthAdjustVectors() {
    if( lengthAdjustStrings == null ) {
      lengthAdjustStrings = new Vector();
      lengthAdjustStrings.addElement( "spacingAndGlyphs" );
      lengthAdjustStrings.addElement( "spacing" );
    }
    if( lengthAdjustValues == null ) {
      lengthAdjustValues = new Vector();
      lengthAdjustValues.addElement( LENGTHADJUST_SPACINGANDGLYPHS );
      lengthAdjustValues.addElement( LENGTHADJUST_SPACING );
      lengthAdjustValues.addElement( LENGTHADJUST_UNKNOWN );
    }
  }

  public SVGAnimatedLengthList getX() {
    if( x == null ) {
      var baseList = new SVGLengthListImpl();
      x = new SVGAnimatedLengthListImpl( baseList, this );
      x.getBaseVal()
       .initialize( new SVGLengthImpl( 0, this, SVGLengthImpl.X_DIRECTION ) );
    }
    return x;
  }

  public SVGAnimatedLengthList getY() {
    if( y == null ) {
      var baseList = new SVGLengthListImpl();
      y = new SVGAnimatedLengthListImpl( baseList, this );
      y.getBaseVal()
       .initialize( new SVGLengthImpl( 0, this, SVGLengthImpl.Y_DIRECTION ) );
    }
    return y;
  }


  public SVGAnimatedLengthList getDx() {
    return null;
  }

  public SVGAnimatedLengthList getDy() {
    return null;
  }

  public SVGAnimatedNumberList getRotate() {
    return null;
  }

  public SVGAnimatedLength getTextLength() {
    if( textLength == null ) {
      textLength = new SVGAnimatedLengthImpl( new SVGLengthImpl( 0,
                                                                 this,
                                                                 SVGLengthImpl.X_DIRECTION ),
                                              this );
    }
    return textLength;
  }

  public SVGAnimatedEnumeration getLengthAdjust() {
    if( lengthAdjust == null ) {
      if( lengthAdjustStrings == null ) {
        initLengthAdjustVectors();
      }
      lengthAdjust = new SVGAnimatedEnumerationImpl( LENGTHADJUST_SPACING,
                                                     this,
                                                     lengthAdjustStrings,
                                                     lengthAdjustValues );
    }
    return lengthAdjust;
  }

  public int getNumberOfChars() {
    var text = getText();
    return text.length();
  }

  public float getComputedTextLength() {
    return 0;
  }

  public float getSubStringLength( int charnum, int nchars ) throws
    DOMException {
    return 1;
  }

  public SVGPoint getStartPositionOfChar( int charnum ) throws
    DOMException {
    return null;
  }

  public SVGPoint getEndPositionOfChar( int charnum ) throws
    DOMException {
    return null;
  }

  public SVGRect getExtentOfChar( int charnum ) throws
    DOMException {
    return null;
  }

  public float getRotationOfChar( int charnum )
    throws DOMException {
    return 0;
  }

  public int getCharNumAtPosition( SVGPoint point ) throws SVGException {
    return 0;
  }

  public void selectSubString( int charnum, int nchars ) throws
    DOMException {
  }

  public String getAttribute( String name ) {
    if( name.equalsIgnoreCase( "x" ) ) {
      return getX().getBaseVal().getItem( 0 ).getValueAsString();
    }
    else if( name.equalsIgnoreCase( "y" ) ) {
      return getY().getBaseVal().getItem( 0 ).getValueAsString();
    }
    else if( name.equalsIgnoreCase( "textLength" ) && textLength != null ) {
      return getTextLength().getBaseVal().getValueAsString();
    }
    else if( name.equalsIgnoreCase( "lengthAdjust" ) ) {
      if( getLengthAdjust().getBaseVal() == LENGTHADJUST_SPACINGANDGLYPHS ) {
        return "spacingAndGlyphs";
      }
      else {
        return "spacing";
      }
    }
    else {
      return super.getAttribute( name );
    }
  }

  public Attr getAttributeNode( String name ) {
    var attr = super.getAttributeNode( name );
    if( attr == null ) { return attr; }
    if( name.equalsIgnoreCase( "x" ) ) {
      attr.setValue( getX().getBaseVal().getItem( 0 ).getValueAsString() );
    }
    else if( name.equalsIgnoreCase( "y" ) ) {
      attr.setValue( getY().getBaseVal().getItem( 0 ).getValueAsString() );
    }
    else if( name.equalsIgnoreCase( "textLength" ) && textLength != null ) {
      attr.setValue( getTextLength().getBaseVal().getValueAsString() );
    }
    else if( name.equalsIgnoreCase( "lengthAdjust" ) ) {
      if( getLengthAdjust().getBaseVal() == LENGTHADJUST_SPACINGANDGLYPHS ) {
        attr.setValue( "spacingAndGlyphs" );
      }
      else {
        attr.setValue( "spacing" );
      }
    }
    return attr;
  }

  public void setAttribute( String name, String value ) {
    super.setAttribute( name, value );
    setAttributeValue( name, value );
  }

  public Attr setAttributeNode( Attr newAttr ) throws
    DOMException {
    setAttributeValue( newAttr.getName(), newAttr.getValue() );
    return super.setAttributeNode( newAttr );
  }

  private void setAttributeValue( String name, String value ) {

    if( name.equalsIgnoreCase( "x" ) ) {
      getX().getBaseVal().getItem( 0 ).setValueAsString( value );
    }
    else if( name.equalsIgnoreCase( "y" ) ) {
      getY().getBaseVal().getItem( 0 ).setValueAsString( value );
    }
    else if( name.equalsIgnoreCase( "textLength" ) ) {
      getTextLength().getBaseVal().setValueAsString( value );

    }
    else if( name.equalsIgnoreCase( "lengthAdjust" ) ) {
      if( value.equalsIgnoreCase( "spacingAndGlyphs" ) ) {
        getLengthAdjust().setBaseVal( LENGTHADJUST_SPACINGANDGLYPHS );
      }
      else if( value.equalsIgnoreCase( "spacing" ) ) {
        getLengthAdjust().setBaseVal( LENGTHADJUST_SPACING );
      }
      else {
        System.out.println( "invalid lengthAdjust value: " + value + ". " +
                              "Setting to default 'spacing'." );
        getLengthAdjust().setBaseVal( LENGTHADJUST_SPACING );
      }
    }
  }


  String getText() {
    var text = "";
    var xmlSpace = getXMLspace();
    if( xmlSpace.length() == 0 ) {
      xmlSpace = "default";
    }
    if( hasChildNodes() ) {
      var children = getChildNodes();
      var numChildren = children.getLength();
      for( var i = 0; i < numChildren; i++ ) {
        var child = children.item( i );
        if( child.getNodeType() == Node.TEXT_NODE ) { // it is
          // #PCDATA
          var nodeValue = child.getNodeValue();

          var childText = "";
          if( xmlSpace.equals( "default" ) ) {

            // remove newline chars
            var newLineIndex = nodeValue.indexOf( '\n' );
            while( newLineIndex != -1 ) {
              nodeValue = nodeValue.substring( 0,
                                               newLineIndex ) + nodeValue.substring(
                newLineIndex + 1 );
              newLineIndex = nodeValue.indexOf( '\n' );
            }
            var returnIndex = nodeValue.indexOf( '\r' );
            while( returnIndex != -1 ) {
              nodeValue = nodeValue.substring( 0,
                                               returnIndex ) + nodeValue.substring(
                returnIndex + 1 );
              returnIndex = nodeValue.indexOf( '\r' );
            }

            // repace tabs with space chars
            nodeValue = nodeValue.replace( '\t', ' ' );

            // replace consecutive space chars with a single space
            var st = new StringTokenizer( nodeValue );
            while( st.hasMoreTokens() ) {
              childText += st.nextToken() + " ";
            }

            // trim leading and trailing spaces
            childText = childText.trim();

          }
          else { // xml:space=preserve
            nodeValue = nodeValue.replace( '\n', ' ' );
            nodeValue = nodeValue.replace( '\r', ' ' );
            nodeValue = nodeValue.replace( '\t', ' ' );
            childText = nodeValue;
          }
          text += childText + " ";
        } // ignore other children for the moment
      }
    }
    // remove the last space
    if( text.length() > 0 ) {
      return text.substring( 0, text.length() - 1 );
    }
    else {
      return text;
    }
  }

  private GeneralPath layoutText( String text, Font font,
                                  int align,
                                  float startOffset ) {

    var glyphs = font.createGlyphVector( new FontRenderContext( null,
                                                                true,
                                                                true ),
                                         text );
    var textLength = getTextLength().getBaseVal().getValue();

    int lengthAdjustMode = getLengthAdjust().getBaseVal();

    var newPath = new GeneralPath();
    var glyphsLength = (float) glyphs.getVisualBounds().getWidth();

    // return from the ugly cases
    if( glyphs == null ||
      glyphs.getNumGlyphs() == 0 ||
      glyphsLength == 0f ) {
      return newPath;
    }

    // work out the expansion/contraction per character
    var lengthRatio = 1f;
    if( textLength != 0f ) {
      lengthRatio = textLength / glyphsLength;
    }

    // the current start point of the character on the path
    var currentPosition = startOffset;

    var tackon = 0f;
    if( lengthAdjustMode == ADJUST_SPACING && glyphs.getNumGlyphs() > 1 && textLength > 0f && (textLength > glyphsLength) ) {
      var lastIndex = glyphs.getNumGlyphs() - 1;
      var gm = glyphs.getGlyphMetrics( lastIndex );
      var charAdvance = gm.getAdvance();
      var glyph = glyphs.getGlyphOutline( lastIndex );
      var glyphWidth = (float) glyph.getBounds2D().getWidth();
      var diff = charAdvance - glyphWidth;
      tackon = diff / (float) glyphs.getNumGlyphs();
    }

    // iterate through the GlyphVector placing each glyph

    for( var i = 0; i < glyphs.getNumGlyphs(); i++ ) {

      var gm = glyphs.getGlyphMetrics( i );

      var charAdvance = gm.getAdvance();

      var glyph = glyphs.getGlyphOutline( i );

      if( hasAffineTransform() ) {
        var tr = AffineTransform.getTranslateInstance
          ( -glyphs.getGlyphPosition( i ).getX(),
            -glyphs.getGlyphPosition( i ).getY() );

        tr.translate( 0, 0 );

        glyph = tr.createTransformedShape( glyph );
      }

      // if lengthAdjust was GLYPHS, then scale the glyph
      // by the lengthRatio in the X direction
      // FIXME: for vertical text this will be the Y direction
      if( lengthAdjustMode == ADJUST_GLYPHS ) {
        var scale = AffineTransform.getScaleInstance( lengthRatio, 1f );
        glyph = scale.createTransformedShape( glyph );
        // charAdvance has to scale accordingly
        charAdvance *= lengthRatio;
      }
      else if( lengthAdjustMode == ADJUST_SPACING ) {
        // charAdvance has to scale accordingly
        charAdvance *= lengthRatio;

        // now we just have to tack a little extra on to
        // compensate for the space after the last character

        charAdvance += tackon;

      }


      // Define the transform of the glyph
      var glyphTrans = new AffineTransform();

      // translate to the current glyph location
      //	    glyphTrans.translate(charMidPoint.getX(), charMidPoint.getY());

      glyphTrans.translate( currentPosition, 0 );

      glyph = glyphTrans.createTransformedShape( glyph );
      newPath.append( glyph, false );

      currentPosition += charAdvance;

    }

    return newPath;
  }

  private Shape createShape( AffineTransform transform ) {

    AffineTransform inverseTransform;
    try {
      inverseTransform = transform.createInverse();
    } catch( NoninvertibleTransformException e ) {
      inverseTransform = null;
    }

    var x = ((SVGLengthImpl) getX().getAnimVal()
                                   .getItem( 0 )).getTransformedLength(
      inverseTransform );
    var y = ((SVGLengthImpl) getY().getAnimVal()
                                   .getItem( 0 )).getTransformedLength(
      inverseTransform );

    var path = new GeneralPath();
    path.setWindingRule( GeneralPath.WIND_NON_ZERO );
    var font = getFont( inverseTransform );
    var text = getText();

    if( text.length() > 0 && font != null ) {
      // try to handle text length
      // quick hack mode on (is there any other way for me???)
      // stop using textlayout and start using (uuugghhh..) GlyphVector

      path = layoutText( text, font, ALIGN_START, 0f );

      //TextLayout tl = new TextLayout(text, font, new FontRenderContext
      // (null,true,true));
      //Shape textShape = tl.getOutline(null);
      //path.append(textShape,false);
      path.transform( AffineTransform.getTranslateInstance( x, y ) );
    }

    var textAnchor = getTextAnchor();

    if( textAnchor != null ) {
      // have to apply a slight transform
      if( textAnchor.equals( "middle" ) ) {
        // transform graphics back half the width of the shape
        var swidth = path.getBounds2D().getWidth();
        path.transform( AffineTransform.getTranslateInstance( -swidth / 2.0,
                                                              0 ) );
      }
      else if( textAnchor.equals( "end" ) ) {
        // transform graphics back the complete width of the shape
        var swidth = path.getBounds2D().getWidth();
        path.transform( AffineTransform.getTranslateInstance( -swidth, 0 ) );
      }
    }
    return path;
  }

  boolean visible = true;
  boolean display = true;
  float opacity = 1;
  Shape clipShape = null;
  Shape shape = null;
  BasicStroke stroke = null;
  Paint fillPaint = null;
  Paint linePaint = null;
  AffineTransform thisTransform = null;
  SVGFontElementImpl font = null;
  float fontSize = 10; // in userUnits
  float xPos = 0;
  float yPos = 0;

  /**
   * Draws this text element.
   *
   * @param graphics    Handle to the graphics object that does the drawing.
   * @param refreshData Indicates whether the shapes, line and fill painting
   *                    objects should be recreated. Set this to true if the
   *                    text has changed
   *                    in any way since the last time it was drawn.
   *                    Otherwise set to false for
   *                    speedier rendering.
   */
  public void draw( Graphics2D graphics, boolean refreshData ) {

    if( refreshData || (shape == null && font == null) ) { // regenerate all
      // of the shapes and painting objects

      refreshData();  // tells all of the stylable stuff to refresh any
      // cached data

      // get visibility and opacity
      visible = getVisibility();
      display = getDisplay();
      opacity = getOpacity();

      // get clipping shape
      var clipPath = getClippingPath();
      clipShape = null;
      if( clipPath != null ) {
        clipShape = clipPath.getClippingShape( this );
      }

      // get transform matrix
      thisTransform =
        ((SVGTransformListImpl) getTransform().getAnimVal()).getAffineTransform();

      font = getFontElement();

      if( font != null ) {
        var transform = ((SVGMatrixImpl) getCTM()).getAffineTransform();
        AffineTransform inverseTransform;
        try {
          inverseTransform = transform.createInverse();
        } catch( NoninvertibleTransformException e ) {
          inverseTransform = null;
        }
        fontSize = getFontSize( inverseTransform );
        xPos = ((SVGLengthImpl) getX().getAnimVal()
                                      .getItem( 0 )).getTransformedLength(
          inverseTransform );
        yPos = ((SVGLengthImpl) getY().getAnimVal()
                                      .getItem( 0 )).getTransformedLength(
          inverseTransform );


      }
      else {
        // create text shape
        shape = createShape( ((SVGMatrixImpl) getCTM()).getAffineTransform() );
      }

      // get stroke, fillPaint and linePaint
      stroke = getStroke();
      fillPaint = getFillPaint();
      linePaint = getLinePaint();


    }
    else {

      // if fillPaint or linePaint is a patern, then should regenerate them
      // anyway
      // this is to overcome loss of pattern quality when zooming

      if( fillPaint != null && fillPaint instanceof SVGTexturePaint ) {
        if( ((SVGTexturePaint) fillPaint).getTextureType() == SVGTexturePaint.SVG_TEXTURETYPE_PATTERN ) {
          fillPaint = getFillPaint();
        }
      }
      if( linePaint != null && linePaint instanceof SVGTexturePaint ) {
        if( ((SVGTexturePaint) linePaint).getTextureType() == SVGTexturePaint.SVG_TEXTURETYPE_PATTERN ) {
          linePaint = getLinePaint();
        }
      }
    }

    if( visible && display && opacity > 0 ) {

      // save current settings
      var oldGraphicsTransform = graphics.getTransform();
      var oldClip = graphics.getClip();

      // do transformations
      graphics.transform( thisTransform );

      // do clipping
      if( clipShape != null ) {
        graphics.clip( clipShape );
      }

      if( opacity < 1 ) {

        // need to draw to an offscreen buffer first
        var root = getOwnerDoc().getRootElement();
        var currentScale = root.getCurrentScale();
        var currentTranslate = root.getCurrentTranslate();
        if( currentTranslate == null ) {
          currentTranslate = new SVGPointImpl();
        }

        // create buffer image to draw on
        Rectangle2D bounds;
        var screenCTM = ((SVGMatrixImpl) getScreenCTM()).getAffineTransform();

        if( font != null ) {
          // must be using a fontElement
          var textBox = font.getBounds( getText(), xPos, yPos, fontSize );
          var transformedTextBox = screenCTM.createTransformedShape( textBox );
          bounds = transformedTextBox.getBounds2D();

        }
        else {
          var transformedShape = screenCTM.createTransformedShape( shape );
          bounds = transformedShape.getBounds2D();
        }

        // make bounds 10% bigger to make sure don't cut off edges
        var xInc = bounds.getWidth() / 5;
        var yInc = bounds.getHeight() / 5;
        bounds.setRect( bounds.getX() - xInc,
                        bounds.getY() - yInc,
                        bounds.getWidth() + 2 * xInc,
                        bounds.getHeight() + 2 * yInc );

        var imageWidth = (int) (bounds.getWidth() * currentScale);
        var imageHeight = (int) (bounds.getHeight() * currentScale);

        if( imageWidth > 0 && imageHeight > 0 ) {
          var image = new BufferedImage( imageWidth, imageHeight,
                                         BufferedImage.TYPE_4BYTE_ABGR );

          var offGraphics = (Graphics2D) image.getGraphics();
          var hints = new RenderingHints( RenderingHints.KEY_ANTIALIASING,
                                          RenderingHints.VALUE_ANTIALIAS_ON );
          offGraphics.setRenderingHints( hints );

          // do the zoom and pan transformations here
          if( currentScale != 1 ) {
            offGraphics.scale( currentScale, currentScale );
          }

          // do translate so that group is drawn at origin
          offGraphics.translate( -bounds.getX(), -bounds.getY() );

          // do the transform to the current coord system
          offGraphics.transform( screenCTM );

          // draw text to buffer
          if( font != null ) {
            double xShift = 0;
            var textAnchor = getTextAnchor();
            if( textAnchor != null ) {
              if( textAnchor.equals( "middle" ) ) {
                // transform graphics back half the width of the shape
                var swidth = bounds.getWidth();
                xShift = -swidth / 2.0;
              }
              else if( textAnchor.equals( "end" ) ) {

                var swidth = bounds.getWidth();
                xShift = -swidth;
              }
            }
            font.drawText( offGraphics,
                           this,
                           getText(),
                           (float) (xPos + xShift),
                           yPos,
                           fontSize,
                           refreshData );

          }
          else {

            offGraphics.setStroke( stroke );
            if( fillPaint != null ) {
              offGraphics.setPaint( fillPaint );
              offGraphics.fill( shape );
            }
            if( linePaint != null ) {
              offGraphics.setPaint( linePaint );
              offGraphics.draw( shape );
            }
          }

          // draw highlight
          if( highlighted ) {
            offGraphics.setPaint( Color.yellow );
            var bbox = getBBox();
            offGraphics.draw( new Rectangle2D.Float( bbox.getX(),
                                                     bbox.getY(),
                                                     bbox.getWidth(),
                                                     bbox.getHeight() ) );
          }

          // draw buffer image to graphics
          var oldComposite = graphics.getComposite();
          var ac = AlphaComposite.getInstance( AlphaComposite.SRC_OVER,
                                               opacity );
          graphics.setComposite( ac );
          var imageTransform =
            AffineTransform.getTranslateInstance( bounds.getX(),
                                                  bounds.getY() );
          imageTransform.scale( 1 / currentScale, 1 / currentScale );
          try {
            imageTransform.preConcatenate( screenCTM.createInverse() );
          } catch( NoninvertibleTransformException e ) {}
          graphics.drawImage( image, imageTransform, null );
          graphics.setComposite( oldComposite );
          image.flush();
        }

      }
      else {
        // draw text normally

        if( font != null ) {
          double xShift = 0;
          var textAnchor = getTextAnchor();
          if( textAnchor != null ) {   // may need to adjust xPos
            if( textAnchor.equals( "middle" ) ) {
              // transform graphics back half the width of the shape
              var swidth = font.getBounds( getText(), xPos, yPos, fontSize )
                               .getWidth();
              xShift = -swidth / 2.0;
            }
            else if( textAnchor.equals( "end" ) ) {

              var swidth = font.getBounds( getText(), xPos, yPos, fontSize )
                               .getWidth();
              xShift = -swidth;
            }
          }
          font.drawText( graphics,
                         this,
                         getText(),
                         (float) (xPos + xShift),
                         yPos,
                         fontSize,
                         refreshData );

        }
        else {

          graphics.setStroke( stroke );
          if( fillPaint != null ) {
            graphics.setPaint( fillPaint );
            graphics.fill( shape );
          }
          if( linePaint != null ) {
            graphics.setPaint( linePaint );
            graphics.draw( shape );
          }
        }
        // draw highlight
        if( highlighted ) {
          graphics.setPaint( Color.yellow );
          var bbox = getBBox();
          graphics.draw( new Rectangle2D.Float( bbox.getX(),
                                                bbox.getY(),
                                                bbox.getWidth(),
                                                bbox.getHeight() ) );
        }
      }

      // restore old settings
      graphics.setTransform( oldGraphicsTransform );
      graphics.setClip( oldClip );
    }
  }


  public Shape getShape() {
    if( shape == null ) {
      // create the shape, ctm is used for determining lengths that have
      // specified
      // absolute units, the shape is not transformed
      var ctm = ((SVGMatrixImpl) getCTM()).getAffineTransform();
      return createShape( ctm );
    }
    else {
      return shape;
    }
  }

  private Rectangle2D getBounds() {
    var font = getFontElement();
    if( font != null ) {
      // must be using a fontElement
      var transform = ((SVGMatrixImpl) getCTM()).getAffineTransform();
      AffineTransform inverseTransform;
      try {
        inverseTransform = transform.createInverse();
      } catch( NoninvertibleTransformException e ) {
        inverseTransform = null;
      }
      var fontSize = getFontSize( inverseTransform );

      var xPos = ((SVGLengthImpl) getX().getAnimVal()
                                        .getItem( 0 )).getTransformedLength(
        inverseTransform );
      var yPos = ((SVGLengthImpl) getY().getAnimVal()
                                        .getItem( 0 )).getTransformedLength(
        inverseTransform );

      var textBox = font.getBounds( getText(), xPos, yPos, fontSize );
      var transformedTextBox =
        ((SVGMatrixImpl) getScreenCTM()).getAffineTransform()
                                        .createTransformedShape(
                                          textBox );
      return transformedTextBox.getBounds2D();

    }
    else {
      var shape = getShape();
      var transformedShape =
        ((SVGMatrixImpl) getScreenCTM()).getAffineTransform()
                                        .createTransformedShape(
                                          shape );
      return transformedShape.getBounds2D();
    }
  }


  /**
   * Returns the tight bounding box in current user space (i.e., after
   * application of the transform attribute) on the geometry of all contained
   * graphics elements, exclusive of stroke-width and filter effects.
   *
   * @return An SVGRect object that defines the bounding box.
   */
  public SVGRect getBBox() {

    var font = getFontElement();
    if( font != null ) {
      // must be using a fontElement
      var transform = ((SVGMatrixImpl) getCTM()).getAffineTransform();
      AffineTransform inverseTransform;
      try {
        inverseTransform = transform.createInverse();
      } catch( NoninvertibleTransformException e ) {
        inverseTransform = null;
      }
      var fontSize = getFontSize( inverseTransform );
      var xPos = ((SVGLengthImpl) getX().getAnimVal()
                                        .getItem( 0 )).getTransformedLength(
        inverseTransform );
      var yPos = ((SVGLengthImpl) getY().getAnimVal()
                                        .getItem( 0 )).getTransformedLength(
        inverseTransform );
      var textBox = font.getBounds( getText(), xPos, yPos, fontSize );
      return new SVGRectImpl( textBox );

    }
    else {
      var shape = getShape();
      return new SVGRectImpl( shape.getBounds2D() );
    }
  }

  /**
   * Returns true if the bounding box around the text contains the point.
   */
  public boolean contains( double x, double y ) {
    var bounds = getBounds();
    return bounds.contains( x, y );
  }

  /**
   * Returns the area of the actual bounding box in the document's coordinate
   * system.  i.e its size when drawn on the screen.
   */
  public double boundingArea() {
    var bounds = getBounds();
    return (bounds.getWidth() * bounds.getHeight());
  }

  public void attachAnimation( SVGAnimationElementImpl animation ) {
    var attName = animation.getAttributeName();
    if( attName.equals( "x" ) ) {
      ((SVGAnimatedValue) getX()).addAnimation( animation );
    }
    else if( attName.equals( "y" ) ) {
      ((SVGAnimatedValue) getY()).addAnimation( animation );
    }
    else if( attName.equals( "textLength" ) ) {
      ((SVGAnimatedValue) getTextLength()).addAnimation( animation );
    }
    else if( attName.equals( "lengthAdjust" ) ) {
      ((SVGAnimatedValue) getLengthAdjust()).addAnimation( animation );
    }
    else {
      super.attachAnimation( animation );
    }
  }
}
