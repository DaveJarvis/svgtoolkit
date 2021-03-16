// SVGElementImpl.java
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
// $Id: SVGElementImpl.java,v 1.52 2002/02/21 07:46:09 brett Exp $
//

package org.csiro.svg.dom;

import org.apache.xerces.dom.ElementImpl;
import org.w3c.dom.*;
import org.w3c.dom.svg.SVGElement;
import org.w3c.dom.svg.SVGSVGElement;
import org.w3c.dom.svg.SVGSymbolElement;

/**
 * SVGElementImpl is the implementation of org.w3c.dom.svg.SVGElement
 */

public abstract class SVGElementImpl extends ElementImpl implements SVGElement {

  public SVGElementImpl( SVGDocumentImpl owner, Element elem, String name ) {
    this( owner, name );
    copyElement( elem );
  }

  public SVGElementImpl( SVGDocumentImpl owner, String name ) {
    super( owner, name );
    setOwnerDoc( owner );
  }

  public abstract SVGElementImpl cloneElement();

  public void attachAnimation( SVGAnimationElementImpl animation ) {
    // if not overridden by sub class do nothing
  }


  SVGDocumentImpl ownerDoc = null;

  /**
   * Get the value of ownerDoc.
   *
   * @return Value of ownerDoc.
   */

  protected SVGDocumentImpl getOwnerDoc() {
    return ownerDoc;
  }

  /**
   * Set the value of ownerDoc.
   *
   * @param v Value to assign to ownerDoc.
   */

  protected void setOwnerDoc( SVGDocumentImpl v ) {
    this.ownerDoc = v;
  }

  /**
   * Get the value of "xml:base".
   *
   * @return Value of "xml:base".
   */
  public String getXMLbase() {
    return getAttribute( "xml:base" );
  }

  /**
   * Set the value of "xml:base".
   *
   * @param xmlBase Value to assign to "xml:base".
   */
  public void setXMLbase( String xmlBase ) throws DOMException {
    setAttribute( "xml:base", xmlBase );
  }


  /**
   * Get the value of id.
   *
   * @return Value of id.
   */
  public String getId() {
    return getAttribute( "id" );
  }

  /**
   * Set the value of id.
   *
   * @param id Value to assign to id.
   */
  public void setId( String id ) {
    if( id != null && id.length() > 0 ) {
      setAttribute( "id", id );
    }
    else {
      removeAttribute( "id" );
    }
    getOwnerDoc().setChanged();
  }


  /**
   * Returns the nearest ancestor 'svg' element. Null if this element is the
   * outermost 'svg' element.
   *
   * @return The nearest ancestor 'svg' element.
   */
  public SVGSVGElement getOwnerSVGElement() {
    if( getParentNode() == ownerDoc ) { // this is the root element
      return null;
    }
    Node parent = getParentNode();
    while( parent != null && !(parent instanceof SVGSVGElement) ) {
      parent = parent.getParentNode();
    }
    return (SVGSVGElement) parent;
  }

  /**
   * Returns the element which established the current viewport. Often, the
   * nearest ancestor 'svg' element. Null if this is the outermost 'svg'
   * element.
   *
   * @return The element which established the current viewport.
   */
  public SVGElement getViewportElement() {
    if( getParentNode() == ownerDoc ) {
      return null;
    }
    Node parent = getParentNode();
    while( parent != null
      && !(parent instanceof SVGSVGElement || parent instanceof SVGSymbolElement) ) {
      parent = parent.getParentNode();
    }
    return (SVGElement) parent;
  }

  protected void copyElement( Element elem ) {
    copyAttributes( elem );
    copyCreateChildren( elem );
  }

  protected void copyCreateChildren( Element elem ) {

    // if the children are elements that should be made into
    // SVG elements then do it here - otherwise just copy them

    NodeList childNodes = elem.getChildNodes();
    int numChildren = childNodes.getLength();
    SVGDocumentImpl owner = getOwnerDoc();

    for( int i = 0; i < numChildren; i++ ) {
      Node child = childNodes.item( i );
      if( child.getNodeType() == Node.ELEMENT_NODE ) { // it is an element
        Element childElement = (Element) child;
        String childTag = childElement.getTagName();

        Element newChildElement = createSVGElement( childTag,
                                                    childElement,
                                                    owner );

        // if a child element was created then add it
        if( newChildElement != null ) {
          this.appendChild( newChildElement );
        }
        else {
          // not an element we recognise (or support yet)
          // add it as a normal child element
          if( !childTag.equals( "mpath" ) ) {
            System.out.println( "element: <" + childTag + "> not supported " +
                                  "yet" );
          }
          this.appendChild( cloneNode( childElement, owner ) );
        }
        // Bella, SVGScriptElementImpl relies on the next test to add CDATA
        // nodes as children of the script node.
        //  not sure if this will break other things...
      }
      else if( (child.getNodeType() == Node.TEXT_NODE) || (child.getNodeType() == Node.CDATA_SECTION_NODE) ) {
        this.appendChild( cloneNode( child, owner ) );
      }
    }
  }

  protected SVGElementImpl createSVGElement( String tag, Element element,
                                             SVGDocumentImpl owner ) {
    // for now only create the elements that we have implemented
    return switch( tag ) {
      case "svg" -> new SVGSVGElementImpl( owner, element );
      case "g" -> new SVGGElementImpl( owner, element );
      case "path" -> new SVGPathElementImpl( owner, element );
      case "rect" -> new SVGRectElementImpl( owner, element );
      case "circle" -> new SVGCircleElementImpl( owner, element );
      case "ellipse" -> new SVGEllipseElementImpl( owner, element );
      case "line" -> new SVGLineElementImpl( owner, element );
      case "polyline" -> new SVGPolylineElementImpl( owner, element );
      case "polygon" -> new SVGPolygonElementImpl( owner, element );
      case "text" -> new SVGTextElementImpl( owner, element );
      case "tspan" -> new SVGTSpanElementImpl( owner, element );
      case "image" -> new SVGImageElementImpl( owner, element );
      case "a" -> new SVGAElementImpl( owner, element );
      case "defs" -> new SVGDefsElementImpl( owner, element );
      case "use" -> new SVGUseElementImpl( owner, element );
      case "symbol" -> new SVGSymbolElementImpl( owner, element );
      case "marker" -> new SVGMarkerElementImpl( owner, element );
      case "pattern" -> new SVGPatternElementImpl( owner, element );
      case "stop" -> new SVGStopElementImpl( owner, element );
      case "linearGradient" -> new SVGLinearGradientElementImpl( owner,
                                                                 element );
      case "radialGradient" -> new SVGRadialGradientElementImpl( owner,
                                                                 element );
      case "clipPath" -> new SVGClipPathElementImpl( owner, element );
      case "title" -> new SVGTitleElementImpl( owner, element );
      case "desc" -> new SVGDescElementImpl( owner, element );
      case "script" -> new SVGScriptElementImpl( owner, element );
      case "style" -> new SVGStyleElementImpl( owner, element );
      case "font" -> new SVGFontElementImpl( owner, element );
      case "missing-glyph" -> new SVGMissingGlyphElementImpl( owner, element );
      case "glyph" -> new SVGGlyphElementImpl( owner, element );
      case "font-face" -> new SVGFontFaceElementImpl( owner, element );
      case "font-face-src" -> new SVGFontFaceSrcElementImpl( owner, element );
      case "font-face-uri" -> new SVGFontFaceUriElementImpl( owner, element );
      case "font-face-name" -> new SVGFontFaceNameElementImpl( owner, element );
      case "font-face-format" -> new SVGFontFaceFormatElementImpl( owner,
                                                                   element );
      case "definition-src" -> new SVGDefinitionSrcElementImpl( owner,
                                                                element );
      case "hkern" -> new SVGHKernElementImpl( owner, element );
      case "vkern" -> new SVGVKernElementImpl( owner, element );
      case "animate" -> new SVGAnimateElementImpl( owner, element );
      case "animateTransform" -> new SVGAnimateTransformElementImpl( owner,
                                                                     element );
      case "animateColor" -> new SVGAnimateColorElementImpl( owner, element );
      case "animateMotion" -> new SVGAnimateMotionElementImpl( owner, element );
      case "set" -> new SVGSetElementImpl( owner, element );
      case "cursor" -> new SVGCursorElementImpl( owner, element );
      case "view" -> new SVGViewElementImpl( owner, element );
      case "filter" -> new SVGFilterElementImpl( owner, element );
      case "feBlend" -> new SVGFEBlendElementImpl( owner, element );
      case "feColorMatrix" -> new SVGFEColorMatrixElementImpl( owner, element );
      case "feComposite" -> new SVGFECompositeElementImpl( owner, element );
      case "color-profile" -> new SVGColorProfileElementImpl( owner, element );
      default -> null;
    };
  }

  protected void copyAttributes( Element elem ) {

    NamedNodeMap attributes = elem.getAttributes();
    int numAttributes = attributes.getLength();
    Node att = null;

    for( int i = 0; i < numAttributes; i++ ) {
      att = attributes.item( i );
      // make sure the attribute really is an attribute
      if( att instanceof Attr ) {
        setAttribute( att.getNodeName(), att.getNodeValue() );
        //  System.out.println("copying att: " + att.getNodeName() + " " +
        //  att.getNodeValue());
      }
      else {
        System.out.println(
          "Bad Attribute in copyAttributes - should check node type rather " +
            "than use instanceof" );
      }
    }
  }

  protected Node cloneNode( Node node, Document owner ) {

    Node newNode = null;

    if( node.getNodeType() == Node.ELEMENT_NODE ) {

      newNode = owner.createElement( ((Element) node).getTagName() );
      // copy attributes
      NamedNodeMap attributes = node.getAttributes();
      int numAttributes = attributes.getLength();
      Node att = null;
      for( int i = 0; i < numAttributes; i++ ) {
        att = attributes.item( i );
        // make sure the attribute really is an attribute
        if( att instanceof Attr ) {
          ((Element) newNode).setAttribute( att.getNodeName(),
                                            att.getNodeValue() );
        }
      }
      // copy children
      NodeList childNodes = node.getChildNodes();
      int numChildren = childNodes.getLength();
      for( int i = 0; i < numChildren; i++ ) {
        Node child = childNodes.item( i );
        if( child.getNodeType() == Node.TEXT_NODE ) {
          if( child.getNodeValue().trim().length() > 0 ) {
            newNode.appendChild( cloneNode( child, owner ) );
          }
        }
        else if( child.getNodeType() == Node.ELEMENT_NODE || child.getNodeType() == Node.CDATA_SECTION_NODE ) {
          newNode.appendChild( cloneNode( child, owner ) );
        }
      }

    }
    else if( node.getNodeType() == Node.TEXT_NODE ) {
      newNode = owner.createTextNode( ((Text) node).getNodeValue() );
    }
    else if( node.getNodeType() == Node.CDATA_SECTION_NODE ) {
      newNode =
        owner.createCDATASection( ((CDATASection) node).getNodeValue() );
    }
    return newNode;
  }

  public NamedNodeMap getAttributes() {

    NamedNodeMap atts = super.getAttributes();
    int numAtts = atts.getLength();
    for( int i = 0; i < numAtts; i++ ) {
      Attr att = (Attr) atts.item( i );
      String val = getAttribute( att.getName() );
      att.setValue( val );
    }
    return atts;
  }

  // these methods are here to make sure that the owner doc is notified when an
  // attribute is set
  public void setAttribute( String name, String value ) {
    super.setAttribute( name, value );
    getOwnerDoc().setChanged();
  }

  public Attr setAttributeNode( Attr newAttr ) throws
    DOMException {
    getOwnerDoc().setChanged();
    return super.setAttributeNode( newAttr );
  }

  public void removeAttribute( String name ) {
    super.removeAttribute( name );
    getOwnerDoc().setChanged();
  }

  public Attr removeAttributeNode( Attr oldAttr ) throws
    DOMException {
    getOwnerDoc().setChanged();
    return super.removeAttributeNode( oldAttr );
  }

  public Node appendChild( Node newChild ) {
    getOwnerDoc().setChanged();
    return super.appendChild( newChild );
  }

  public Node insertBefore( Node newChild, Node refChild ) {
    getOwnerDoc().setChanged();
    return super.insertBefore( newChild, refChild );
  }

  public Node removeChild( Node oldChild ) {
    getOwnerDoc().setChanged();
    return super.removeChild( oldChild );
  }

  public Node replaceChild( Node newChild, Node oldChild ) {
    getOwnerDoc().setChanged();
    return super.replaceChild( newChild, oldChild );
  }

} // SVGElementImpl

