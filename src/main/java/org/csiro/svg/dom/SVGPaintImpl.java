// SVGPaintImpl.java
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
// $Id: SVGPaintImpl.java,v 1.4 2000/11/21 05:57:02 bella Exp $
//

package org.csiro.svg.dom;

import org.w3c.dom.svg.SVGException;
import org.w3c.dom.svg.SVGPaint;

/**
 * SVGPaintImpl is the implementation of org.w3c.dom.svg.SVGPaint
 */
public class SVGPaintImpl extends SVGColorImpl implements SVGPaint {

  protected short paintType;
  protected String uri;

  public SVGPaintImpl() {
  }

  public short getPaintType( ) {
    return paintType;
  }

  public String getUri( ) {
    return uri;
  }

  /**
   * Sets the paintType to SVG_PAINTTYPE_URI_NONE and sets uri to the specified value.
   * @param uri The URI for the desired paint server.
   */
  public void setUri( String uri ) {
    this.uri = uri;
  }



  /**
   * Sets the paintType as specified by the parameters. If paintType requires a
   * URI, then uri must be non-null and a valid string; otherwise, uri must be
   * null. If paintType requires an RGBColor, then rgbColor must be a valid
   * RGBColor object; otherwise, rgbColor must be null. If paintType requires
   * an SVGICCColor, then iccColor must be a valid SVGICCColor object; otherwise,
   * iccColor must be null.
   *
   * @param paintType One of the defined constants for paintType.
   * @param uri The URI for the desired paint server, or null.
   * @param rgbColor The specification of an sRGB color, or null.
   * @param iccColor The specification of an ICC color, or null.
   */
  public void setPaint (short paintType, String uri, String rgbColor, String iccColor)
                  throws SVGException {
    this.paintType = paintType;
    this.uri = uri;
    this.setRGBColorICCColor(rgbColor, iccColor);
  }
}
