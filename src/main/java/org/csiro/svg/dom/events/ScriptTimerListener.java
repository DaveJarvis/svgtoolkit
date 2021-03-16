// ScriptTimerListener.java
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
// $Id: ScriptTimerListener.java,v 1.3 2000/09/26 14:10:36 dino Exp $
//

package org.csiro.svg.dom.events;

import org.csiro.svg.dom.SVGDocumentImpl;
import org.mozilla.javascript.JavaScriptException;
import org.w3c.dom.events.Event;


public class ScriptTimerListener implements TimerCallback
{
  SVGDocumentImpl document = null;

  public ScriptTimerListener(SVGDocumentImpl document) {
    this.document=document;
  }

  public void timer(Timer t, Object userdata) {
    //System.out.println("ScriptTimerListener got an event " + userdata.toString());

    //  enter the proper context...
    document.getContext().enter();

    //
    //  Insert the event as an object in the script engine context...
    //

    //Scriptable jsArgs = Context.toObject(evt,document.getScope());
    //document.getScope().put("evt", document.getScope(), jsArgs);

    String commandString = userdata.toString();
    //System.out.println("ScriptTimerListener About to execute command: '" + commandString + "'");
    Object result = null;

    try {
      result = document.getContext().evaluateString(document.getScope(),commandString,"<cmd>",1,null);
      //result = document.getContext().evaluateReader(document.getScope(),fr,commandString,1,null);// String(getScope(),commandString,"<cmd>",1,null);
      //System.out.println("result is: '" + document.getContext().toString(result) + "'");

    } catch (JavaScriptException jseex) {
      System.err.println("Exception caught: " + jseex.getMessage());
    }

    //catch (IOException ioex) {
    //  System.err.println("IOException reading from file " + commandString);
    //  return;
    //}

    catch (Exception except) {
      System.err.println("Exception caught: " + except.getLocalizedMessage());
    }

    document.getScope().delete("evt");

    // For now just raise an event with the dom...
    Event evt = document.createEvent( "UIEvent");
    evt.initEvent("onchange",false,false);
    document.getRootElement().dispatchEvent(evt);

    document.getContext().exit();
  }

} // ScriptTimerListener
