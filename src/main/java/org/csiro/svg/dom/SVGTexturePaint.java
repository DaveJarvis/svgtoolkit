// SVGTexturePaint.java
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
// $Id: SVGTexturePaint.java,v 1.6 2000/09/26 14:10:34 dino Exp $
//

package org.csiro.svg.dom;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

/**
 * SVGTexturePaint is used for gradients and patterns. It is basically a
 * TexturePaint object with a (possible) transformation.
 */
public class SVGTexturePaint extends TexturePaint {

  public static final short SVG_TEXTURETYPE_RADIAL_GRADIENT = 0;
  public static final short SVG_TEXTURETYPE_LINEAR_GRADIENT = 1;
  public static final short SVG_TEXTURETYPE_PATTERN = 2;

  AffineTransform transform;
  short textureType;

  public SVGTexturePaint(BufferedImage txtr, Rectangle2D anchor, AffineTransform transform, short textureType) {
    super(txtr, anchor);
    this.transform = transform;
    if (this.transform == null) {
      this.transform = new AffineTransform();
    }
    this.textureType = textureType;
  }

  public PaintContext createContext(ColorModel cm,
                                  Rectangle deviceBounds,
                                  Rectangle2D userBounds,
                                  AffineTransform xform,
                                  RenderingHints hints) {
    AffineTransform trans = new AffineTransform(xform);
    trans.concatenate(transform);
    return super.createContext(cm, deviceBounds, userBounds, trans, hints);
  }

  public short getTextureType() {
    return textureType;
  }

} 
