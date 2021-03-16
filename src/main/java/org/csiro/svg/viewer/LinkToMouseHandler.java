// LinkToMouseHandler.java
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
// $Id: LinkToMouseHandler.java,v 1.11 2000/12/08 04:50:34 bella Exp $
//

package org.csiro.svg.viewer;

import org.csiro.svg.dom.SVGAElementImpl;
import org.csiro.svg.dom.SVGDocumentImpl;
import org.w3c.dom.NodeList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class LinkToMouseHandler extends MouseHandler {

  /**
   * Constructs a LinkToMouseHandler with the given Canvas and Viewer objects.
   *
   * @param canvas The Canvas object.
   * @param viewer The Viewer object.
   */
  public LinkToMouseHandler(Canvas canvas, Viewer viewer) {
    super(canvas);
    this.viewer = viewer;
  }

  private final Viewer viewer;
  private boolean usingHand = false;

  /**
   * Invoked when the mouse button has been moved in the Canvas canvas,
   * when there are no buttons pressed.
   *
   */
  public void mouseMoved ( MouseEvent e ) {
    SVGDocumentImpl doc = canvas.getSVGDocument();
    if (doc == null) return;
    if (doc.getRootElement() == null) return;

    org.w3c.dom.NodeList aElements = doc.getRootElement().getElementsByTagName( "a");
    int numAElements = aElements.getLength();
    if ( numAElements > 0) {
      Vector aElementsUnder = new Vector();
      for (int i = 0; i < numAElements; i++) {
        SVGAElementImpl aElement = (SVGAElementImpl)aElements.item(i);
        if (aElement.getHref() != null
               && aElement.contains(((WorldMouseEvent)e).getWorldX() , ((WorldMouseEvent)e).getWorldY())) {
          // mouse is over this <a> element
          aElementsUnder.addElement(aElement);
        }
      }
      int numUnder = aElementsUnder.size();
      if (numUnder > 0) {
        SVGAElementImpl selectedAElement;
        if (numUnder == 1) {
          selectedAElement = (SVGAElementImpl)aElementsUnder.elementAt(0);
        } else {
           // need to find the element with the smallest area
           selectedAElement = (SVGAElementImpl)aElementsUnder.elementAt(0);
           double minArea = selectedAElement.boundingArea();
           for (int j = 1; j < numUnder; j++) {
             SVGAElementImpl aElement = (SVGAElementImpl)aElementsUnder.elementAt(j);
             double area = aElement.boundingArea();
             if (area < minArea) {
               selectedAElement = aElement;
               minArea = area;
             }
           }
        }
        if (selectedAElement != null) {
          canvas.setCursor(new Cursor(Cursor.HAND_CURSOR));
          usingHand = true;
          viewer.showStatus(selectedAElement.getHref().getAnimVal());
          return;
        }
      }
    }
    if (usingHand) {
      canvas.setCursor(viewer.currentCursor);
      usingHand = false;
      viewer.showStatus("");
    }
  }


  /**
   * Invoked when the mouse has been clicked in the Canvas canvas.
   */
  public void mouseClicked(MouseEvent e) {
    SVGDocumentImpl doc = canvas.getSVGDocument();
    if (doc == null) return;
    if (doc.getRootElement() == null) return;

    NodeList aElements = doc.getRootElement().getElementsByTagName( "a");
    int numAElements = aElements.getLength();
    if ( numAElements > 0) {
      // create list of <a> elements that contain the mouse click
      Vector aElementsUnder = new Vector();
      for (int i = 0; i < numAElements; i++) {
        SVGAElementImpl aElement = (SVGAElementImpl)aElements.item(i);
        if (aElement.getHref() != null
             && aElement.contains(((WorldMouseEvent)e).getWorldX() , ((WorldMouseEvent)e).getWorldY())) {
          // mouse clicked on this <a> element
          aElementsUnder.addElement(aElement);
        }
      }
      int numUnder = aElementsUnder.size();
      if (numUnder > 0) {
        SVGAElementImpl selectedAElement;
        if (numUnder == 1) {
          selectedAElement = (SVGAElementImpl)aElementsUnder.elementAt(0);
        } else {
          // need to find the element with the smallest area
          selectedAElement = (SVGAElementImpl)aElementsUnder.elementAt(0);
          double minArea = selectedAElement.boundingArea();
          for (int j = 1; j < numUnder; j++) {
            SVGAElementImpl aElement = (SVGAElementImpl)aElementsUnder.elementAt(j);
            double area = aElement.boundingArea();
            if (area < minArea) {
              selectedAElement = aElement;
              minArea = area;
            }
          }
        }
        if (selectedAElement != null) {
          String href = selectedAElement.getHref().getAnimVal();
          if (href.indexOf(':') == -1 && href.indexOf("#") != 0) {
            // is a relative address, need to append current directory onto the front
            int slashIndex = viewer.currentPath.lastIndexOf('/');
            if (slashIndex == -1)
              slashIndex = viewer.currentPath.lastIndexOf('\\');
            href = viewer.currentPath.substring(0,slashIndex+1) + href;
          }
          if ((href.contains( ".svg" )) || (href.indexOf( "#") == 0)) {   // if its an svg doc try and load it
            viewer.loadNewDocument(href);
          } else {  // if an applet within a browser, get browser to display it
            if (viewer.invokedStandalone) {
              JOptionPane.showMessageDialog(viewer, "Cannot load URL: " + href
                                    + "\nwhile running as a stand alone application.",
				      "Link Error", JOptionPane.INFORMATION_MESSAGE);
            }
          }
        }
      }
    }
  }

}
