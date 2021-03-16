// SvgListener.java
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
// $Id: SvgListener.java,v 1.2 2000/09/26 14:10:40 dino Exp $
//

package org.csiro.svg.viewer;

import org.csiro.svg.dom.*;
import org.csiro.svg.parser.*;

public interface SvgListener {
    public void newSvgDoc(SVGDocumentImpl svgDoc, String svgUrl);
}
