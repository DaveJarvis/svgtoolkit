// ParseTest.java
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
// $Id: ParseTest.java,v 1.9 2000/09/26 14:10:37 dino Exp $
//

package org.csiro.svg.parser;

import org.csiro.svg.dom.SVGDocumentImpl;
import org.csiro.svg.dom.SVGSVGElementImpl;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ParseTest {

  public ParseTest() {
  }

  public static void main(String[] args) {

    if (args.length != 2) {
      System.out.println("usage: java org.csiro.svg.parser.ParseTest in.svg out.jpg");
      System.exit(1);
    }
    try {
      SVGParser parser = new SVGParser();
      SVGDocumentImpl doc = parser.parseSVG(args[0]);
      System.out.println("parsed svg file: " + args[0] + " successfully");
     // FileOutputStream out = new FileOutputStream(new File(args[1]));
     // XmlWriter writer = new XmlWriter(out);
     // writer.print(doc, "<!DOCTYPE svg SYSTEM \"svg-20000110.dtd\">");
     // out.close();

      SVGSVGElementImpl root = (SVGSVGElementImpl)doc.getRootElement();
      BufferedImage image = new BufferedImage((int)root.getWidth().getBaseVal().getValue(),
            (int)root.getHeight().getBaseVal().getValue(), BufferedImage.TYPE_3BYTE_BGR);
      Graphics2D graphics = (Graphics2D)image.getGraphics();
      graphics.setColor(Color.white);
      graphics.fillRect(0,0,(int)root.getWidth().getBaseVal().getValue(),(int)root.getHeight().getBaseVal().getValue());
      doc.draw(graphics);

      File file = new File(args[1]);
      FileOutputStream out = new FileOutputStream(file);

      ImageWriter imageWriter = ImageIO.getImageWritersBySuffix( "jpeg").next();
      ImageOutputStream ios = ImageIO.createImageOutputStream(out);
      imageWriter.setOutput(ios);
      IIOMetadata imageMetaData = imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(image), null);
      imageWriter.write( imageMetaData, new IIOImage( image, null, null), null);
      out.close();
    } catch (SVGParseException e) {
      System.out.println("SVGParseException: " + e.getMessage());
      System.exit(1);

    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
      System.exit(1);
    }
    System.out.println("finished writing new jpg file: " + args[1]);
    System.exit(0);
  }
}

