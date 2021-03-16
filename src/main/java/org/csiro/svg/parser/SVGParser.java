// SVGParser.java
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
// $Id: SVGParser.java,v 1.16 2002/03/11 00:47:20 bella Exp $
//

package org.csiro.svg.parser;

import org.w3c.dom.Document;
import org.xml.sax.*;
import org.apache.xerces.parsers.DOMParser;

import org.csiro.svg.dom.SVGDocumentImpl;
import java.net.*;
import java.io.*;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipEntry;


public class SVGParser extends DOMParser implements ErrorHandler, EntityResolver {

  public SVGParser() {
  }

  public SVGDocumentImpl parseSVG(String url) throws SVGParseException {

    // first see if it's zipped
    boolean zipped = false;

    if (url.startsWith("http:") || url.startsWith("ftp:") || url.startsWith("file:")) {
      try {
        URL svgUrl = new URL(url);
        URLConnection connection = svgUrl.openConnection();
        String contentType = connection.getContentType();
        if (contentType.indexOf("zip") != -1 || url.endsWith(".zip")) {
          zipped = true;
        }
      } catch (MalformedURLException e) {
        System.out.println("bad url: " + url);
      } catch (IOException e) {
        System.out.println("IOException while getting SVG stream");
      }
    } else { // local file
      if (url.endsWith(".zip")) {
        zipped = true;
      }
    }

    try {


      setFeature("http://xml.org/sax/features/validation", true);
      setFeature("http://apache.org/xml/features/dom/create-entity-ref-nodes", false);
      setErrorHandler(this);
      setEntityResolver(this);

      // this allows attributes to contain colons!!! eg. xlink:href
      setFeature("http://xml.org/sax/features/namespaces", false);

      InputStream in = null;
      if (url.startsWith("http:") || url.startsWith("ftp:") || url.startsWith("file:")) {
        URL svgUrl = new URL(url);
        URLConnection connection = svgUrl.openConnection();
        if (zipped) {
          ZipInputStream zis = new ZipInputStream(connection.getInputStream());
          ZipEntry ze = zis.getNextEntry();
          in = zis;
        } else {
          in = connection.getInputStream();
        }
      } else { // is a zipped file
        File file = new File(url);
        if (zipped) {
          ZipInputStream zis = new ZipInputStream(new FileInputStream(file));
          ZipEntry ze = zis.getNextEntry();
          in = zis;
        } else {
          if (file.exists()) {
            in = new FileInputStream(file);
          } else {
            System.out.println("\nFILE IO ERROR: Unable to find file '" + url + "'\n");
            file = null;
            return null;
          }
        }
      }
      if (in != null) {
        parse(new InputSource(in));
        in.close();
      }
    } catch (SAXException e) {
      System.out.println("SAXException in parsing XML: " + e.getMessage());
      e.printStackTrace();
      throw new SVGParseException("Fatal parsing error while trying to parse: " + url);
    } catch (MalformedURLException e) {
      System.out.println("bad url: " + url);
      e.printStackTrace();
      throw new SVGParseException("Cannot parse bad URL: " + url);
    } catch (IOException e) {
      System.out.println("IOException in parsing XML: " + e.getMessage());
      e.printStackTrace();
      throw new SVGParseException("Fatal IO error while trying to parse: " + url);
    } catch (Exception e) {
      System.out.println("IOException in parsing XML: " + e.getMessage());
      e.printStackTrace();
      throw new SVGParseException("Fatal error while trying to parse: " + url);
    }
    System.out.println("xml parsing finished");

    Document doc = getDocument();
    SVGDocumentImpl svgDoc = new SVGDocumentImpl(doc);
    svgDoc.setURL(url);
    return svgDoc;
  }


  /** Warning. */
  public void warning(SAXParseException ex) {
    System.err.println("[Warning] "+
		       getLocationString(ex)+": "+
		       ex.getMessage());
  }

  /** Error. */
  public void error(SAXParseException ex) {
    System.err.println("[Error] "+
		       getLocationString(ex)+": "+
		       ex.getMessage());
  }

  /** Fatal error. */
  public void fatalError(SAXParseException ex) throws SAXException {
    System.err.println("[Fatal Error] "+
		       getLocationString(ex)+": "+
		       ex.getMessage());
    throw ex;
  }

  /** Returns a string of the location. */
  private String getLocationString(SAXParseException ex) {
    StringBuffer str = new StringBuffer();
    String systemId = ex.getSystemId();
    if (systemId != null) {
      int index = systemId.lastIndexOf('/');
      if (index != -1)
        systemId = systemId.substring(index + 1);
      str.append(systemId);
    }
    str.append(':');
    str.append(ex.getLineNumber());
    str.append(':');
    str.append(ex.getColumnNumber());
    return str.toString();
  }

  public InputSource resolveEntity (String publicId, String systemId) {

    // return local copy of the dtd if we have it

    if (publicId.indexOf("SVG 1.0") != -1) {
      return new InputSource(getClass().getResourceAsStream("/dtds/svg10.dtd"));

    } else if (publicId.indexOf("20001102") != -1) {
      return new InputSource(getClass().getResourceAsStream("/dtds/svg-20001102.dtd"));

    } else if (publicId.indexOf("20000802") != -1) {
      return new InputSource(getClass().getResourceAsStream("/dtds/svg-20000802.dtd"));

    } else if (publicId.indexOf("20000629") != -1) {
      return new InputSource(getClass().getResourceAsStream("/dtds/svg-20000629.dtd"));

    } else if (publicId.indexOf("20000303 Stylable") != -1) {
      return new InputSource(getClass().getResourceAsStream("/dtds/svg-20000303-stylable.dtd"));

    } else if (publicId.indexOf("20000303 Shared") != -1) {
      return new InputSource(getClass().getResourceAsStream("/dtds/svg-20000303-shared.dtd"));

    } else {
      // use the default behaviour
      return null;
    }
  }

}
