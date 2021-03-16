package org.csiro.svg.dom;

import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGTSpanElement;
import org.w3c.dom.svg.SVGTextElement;

public class SVGTSpanElementImpl extends SVGTextElementImpl
  implements SVGTSpanElement, SVGTextElement, Drawable, BasicShape {
  /**
   * Simple constructor, x and y attributes are initialized to 0
   */
  public SVGTSpanElementImpl( SVGDocumentImpl owner ) {
    super( owner, "tspan" );
  }

  /**
   * Constructor using an XML Parser
   */
  public SVGTSpanElementImpl( SVGDocumentImpl owner, Element elem ) {
    super( owner, elem, "tspan" );
  }
}
