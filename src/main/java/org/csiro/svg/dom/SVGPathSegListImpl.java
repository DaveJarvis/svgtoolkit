// SVGPathSegListImpl.java
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
// $Id: SVGPathSegListImpl.java,v 1.8 2000/11/21 05:57:04 bella Exp $
//

package org.csiro.svg.dom;

import org.w3c.dom.DOMException;
import org.w3c.dom.svg.*;

import java.util.Vector;

/**
 * SVGPathSegListImpl is an SVGList containing SVGPathSeg objects
 */
public class SVGPathSegListImpl extends SVGListImpl implements SVGPathSegList {

  /**
   * Constructs a new empty SVGPathSegListImpl.
   */
  public SVGPathSegListImpl() {
  }

  /**
   * Copy constructor.
   */
  public SVGPathSegListImpl(SVGPathSegListImpl pathSegList) {

    items = new Vector();

    for (int i = 0; i < pathSegList.getNumberOfItems(); i++) {
      SVGPathSeg seg = (SVGPathSeg)pathSegList.getItem( i);

      switch(seg.getPathSegType()) {

        case SVGPathSeg.PATHSEG_CLOSEPATH : {
          SVGPathSeg newSeg = new SVGPathSegClosePathImpl();
          appendItem(newSeg);
          break;
        }

        case SVGPathSeg.PATHSEG_MOVETO_ABS : {
          float x = ((SVGPathSegMovetoAbs)seg).getX();
          float y = ((SVGPathSegMovetoAbs)seg).getY();
          SVGPathSeg newSeg = new SVGPathSegMovetoAbsImpl(x, y);
          appendItem(newSeg);
          break;
        }

        case SVGPathSeg.PATHSEG_MOVETO_REL : {
          float x = ((SVGPathSegMovetoRel)seg).getX();
          float y = ((SVGPathSegMovetoRel)seg).getY();
          SVGPathSeg newSeg = new SVGPathSegMovetoRelImpl(x, y);
          appendItem(newSeg);
          break;
        }

        case SVGPathSeg.PATHSEG_LINETO_ABS : {
          float x = ((SVGPathSegLinetoAbs)seg).getX();
          float y = ((SVGPathSegLinetoAbs)seg).getY();
          SVGPathSeg newSeg = new SVGPathSegLinetoAbsImpl(x, y);
          appendItem(newSeg);
          break;
        }

        case SVGPathSeg.PATHSEG_LINETO_REL : {
          float x = ((SVGPathSegLinetoRel)seg).getX();
          float y = ((SVGPathSegLinetoRel)seg).getY();
          SVGPathSeg newSeg = new SVGPathSegLinetoRelImpl(x, y);
          appendItem(newSeg);
          break;
        }

        case SVGPathSeg.PATHSEG_LINETO_HORIZONTAL_ABS : {
          float x = ((SVGPathSegLinetoHorizontalAbs)seg).getX();
          SVGPathSeg newSeg = new SVGPathSegLinetoHorizontalAbsImpl(x);
          appendItem(newSeg);
          break;
        }

        case SVGPathSeg.PATHSEG_LINETO_HORIZONTAL_REL : {
          float x = ((SVGPathSegLinetoHorizontalRel)seg).getX();
          SVGPathSeg newSeg = new SVGPathSegLinetoHorizontalRelImpl(x);
          appendItem(newSeg);
          break;
        }

        case SVGPathSeg.PATHSEG_LINETO_VERTICAL_ABS : {
          float y = ((SVGPathSegLinetoVerticalAbs)seg).getY();
          SVGPathSeg newSeg = new SVGPathSegLinetoVerticalAbsImpl(y);
          appendItem(newSeg);
          break;
        }

        case SVGPathSeg.PATHSEG_LINETO_VERTICAL_REL : {
          float y = ((SVGPathSegLinetoVerticalRel)seg).getY();
          SVGPathSeg newSeg = new SVGPathSegLinetoVerticalRelImpl(y);
          appendItem(newSeg);
          break;
        }

        case SVGPathSeg.PATHSEG_CURVETO_CUBIC_ABS : {
          float x = ((SVGPathSegCurvetoCubicAbs)seg).getX();
          float y = ((SVGPathSegCurvetoCubicAbs)seg).getY();
          float x1 = ((SVGPathSegCurvetoCubicAbs)seg).getX1();
          float y1 = ((SVGPathSegCurvetoCubicAbs)seg).getY1();
          float x2 = ((SVGPathSegCurvetoCubicAbs)seg).getX2();
          float y2 = ((SVGPathSegCurvetoCubicAbs)seg).getY2();
          SVGPathSeg newSeg = new SVGPathSegCurvetoCubicAbsImpl(x, y, x1, y1, x2, y2);
          appendItem(newSeg);
          break;
        }

        case SVGPathSeg.PATHSEG_CURVETO_CUBIC_REL : {
          float x = ((SVGPathSegCurvetoCubicRel)seg).getX();
          float y = ((SVGPathSegCurvetoCubicRel)seg).getY();
          float x1 = ((SVGPathSegCurvetoCubicRel)seg).getX1();
          float y1 = ((SVGPathSegCurvetoCubicRel)seg).getY1();
          float x2 = ((SVGPathSegCurvetoCubicRel)seg).getX2();
          float y2 = ((SVGPathSegCurvetoCubicRel)seg).getY2();
          SVGPathSeg newSeg = new SVGPathSegCurvetoCubicRelImpl(x, y, x1, y1, x2, y2);
          appendItem(newSeg);
          break;
        }

        case SVGPathSeg.PATHSEG_CURVETO_CUBIC_SMOOTH_ABS : {
          float x = ((SVGPathSegCurvetoCubicSmoothAbs)seg).getX();
          float y = ((SVGPathSegCurvetoCubicSmoothAbs)seg).getY();
          float x2 = ((SVGPathSegCurvetoCubicSmoothAbs)seg).getX2();
          float y2 = ((SVGPathSegCurvetoCubicSmoothAbs)seg).getY2();
          SVGPathSeg newSeg = new SVGPathSegCurvetoCubicSmoothAbsImpl(x, y, x2, y2);
          appendItem(newSeg);
          break;
        }

        case SVGPathSeg.PATHSEG_CURVETO_CUBIC_SMOOTH_REL : {
          float x = ((SVGPathSegCurvetoCubicSmoothRel)seg).getX();
          float y = ((SVGPathSegCurvetoCubicSmoothRel)seg).getY();
          float x2 = ((SVGPathSegCurvetoCubicSmoothRel)seg).getX2();
          float y2 = ((SVGPathSegCurvetoCubicSmoothRel)seg).getY2();
          SVGPathSeg newSeg = new SVGPathSegCurvetoCubicSmoothRelImpl(x, y, x2, y2);
          appendItem(newSeg);
          break;
        }

        case SVGPathSeg.PATHSEG_CURVETO_QUADRATIC_ABS : {
          float x = ((SVGPathSegCurvetoQuadraticAbs)seg).getX();
          float y = ((SVGPathSegCurvetoQuadraticAbs)seg).getY();
          float x1 = ((SVGPathSegCurvetoQuadraticAbs)seg).getX1();
          float y1 = ((SVGPathSegCurvetoQuadraticAbs)seg).getY1();
          SVGPathSeg newSeg = new SVGPathSegCurvetoQuadraticAbsImpl(x, y, x1, y1);
          appendItem(newSeg);
          break;
        }

        case SVGPathSeg.PATHSEG_CURVETO_QUADRATIC_REL : {
          float x = ((SVGPathSegCurvetoQuadraticRel)seg).getX();
          float y = ((SVGPathSegCurvetoQuadraticRel)seg).getY();
          float x1 = ((SVGPathSegCurvetoQuadraticRel)seg).getX1();
          float y1 = ((SVGPathSegCurvetoQuadraticRel)seg).getY1();
          SVGPathSeg newSeg = new SVGPathSegCurvetoQuadraticRelImpl(x, y, x1, y1);
          appendItem(newSeg);
          break;
        }

        case SVGPathSeg.PATHSEG_CURVETO_QUADRATIC_SMOOTH_ABS : {
          float x = ((SVGPathSegCurvetoQuadraticSmoothAbs)seg).getX();
          float y = ((SVGPathSegCurvetoQuadraticSmoothAbs)seg).getY();
          SVGPathSeg newSeg = new SVGPathSegCurvetoQuadraticSmoothAbsImpl(x, y);
          appendItem(newSeg);
          break;
        }

        case SVGPathSeg.PATHSEG_CURVETO_QUADRATIC_SMOOTH_REL : {
          float x = ((SVGPathSegCurvetoQuadraticSmoothRel)seg).getX();
          float y = ((SVGPathSegCurvetoQuadraticSmoothRel)seg).getY();
          SVGPathSeg newSeg = new SVGPathSegCurvetoQuadraticSmoothRelImpl(x, y);
          appendItem(newSeg);
          break;
        }

        case SVGPathSeg.PATHSEG_ARC_ABS : {
          float x = ((SVGPathSegArcAbs)seg).getX();
          float y = ((SVGPathSegArcAbs)seg).getY();
          float r1 = ((SVGPathSegArcAbs)seg).getR1();
          float r2 = ((SVGPathSegArcAbs)seg).getR2();
          float angle = ((SVGPathSegArcAbs)seg).getAngle();
          boolean largeArcFlag = ((SVGPathSegArcAbs)seg).getLargeArcFlag();
          boolean sweepFlag = ((SVGPathSegArcAbs)seg).getSweepFlag();
          SVGPathSeg newSeg = new SVGPathSegArcAbsImpl(x, y, r1, r2, angle, largeArcFlag, sweepFlag);
          appendItem(newSeg);
          break;
        }

        case SVGPathSeg.PATHSEG_ARC_REL : {
          float x = ((SVGPathSegArcRel)seg).getX();
          float y = ((SVGPathSegArcRel)seg).getY();
          float r1 = ((SVGPathSegArcRel)seg).getR1();
          float r2 = ((SVGPathSegArcRel)seg).getR2();
          float angle = ((SVGPathSegArcRel)seg).getAngle();
          boolean largeArcFlag = ((SVGPathSegArcRel)seg).getLargeArcFlag();
          boolean sweepFlag = ((SVGPathSegArcRel)seg).getSweepFlag();
          SVGPathSeg newSeg = new SVGPathSegArcRelImpl(x, y, r1, r2, angle, largeArcFlag, sweepFlag);
          appendItem(newSeg);
          break;
        }
      }
    }
  }

  public SVGPathSeg initialize (SVGPathSeg newItem)
                  throws DOMException, SVGException {
    return (SVGPathSeg)super.initialize(newItem);
  }

  public SVGPathSeg getItem ( int index )
                  throws DOMException {
    return (SVGPathSeg)super.getItemAt(index);
  }

  public SVGPathSeg insertItemBefore ( SVGPathSeg newItem, int index )
                  throws DOMException, SVGException {
    return (SVGPathSeg)super.insertItemBefore(newItem, index);
  }

  public SVGPathSeg replaceItem ( SVGPathSeg newItem, int index )
                  throws DOMException, SVGException {
    return (SVGPathSeg)super.replaceItem(newItem, index);
  }

  public SVGPathSeg removeItem ( int index )
                  throws DOMException {
    return (SVGPathSeg)super.removeItemAt(index);
  }

  public SVGPathSeg appendItem ( SVGPathSeg newItem )
                  throws DOMException, SVGException {
    return (SVGPathSeg)super.appendItem(newItem);
  }


  /**
   * Checks that the item is the correct type for the given list.
   * @param item The item to check.
   * @exception SVGException
   *     Raised if the item is the wrong type of object for the given list.
   */
  protected void checkItemType(Object item) throws SVGException {
    if (!(item instanceof SVGPathSeg)) {
       throw new SVGExceptionImpl(SVGException.SVG_WRONG_TYPE_ERR,
                "Wrong item type for this list. Was expecting SVGPathSeg.");
    }
  }

  /**
   * Returns the list of path segments as a single "path" String.
   * @return The the path segment list as a String.
   */
  public String toString() {
    String path = "";
    long numSegments = getNumberOfItems();
    for (int i = 0; i < numSegments; i++) {
      path += getItem(i).toString();
      if (i != numSegments-1) {
        path += " ";
      }
    }
    return path;
  }

}  // SVGPathSegListImpl
