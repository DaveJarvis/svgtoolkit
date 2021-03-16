// Timer.java
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
// $Id: Timer.java,v 1.4 2000/09/26 14:10:36 dino Exp $
//

package org.csiro.svg.dom.events;

/**
 * A simple Java timer class
 * @author Jonathan Locke
 */
public class Timer extends Thread implements TimerCallback
{
  TimerCallback callback;
  Timer t = null;
  int milliseconds;
  Object userdata;

  public Timer(TimerCallback callback) {
    this.callback = callback;
  }

  public void setInterval(Object userdata, int milliseconds) {
    this.userdata = userdata;
    this.milliseconds = milliseconds;
    clearInterval();
    t = new Timer(this);
    t.userdata = userdata;
    t.milliseconds = milliseconds;
    t.start();
  }

  public void clearInterval() {
    if (t != null) {
      t.callback = null;
      // explicitly clear the old timer...
      t = null;
    }
  }

  public void timer(Timer t, Object userdata) {
    // call my listener using the timer...
    callback.timer(this,userdata);
    // ...and create a new timer using my interval.
    setInterval(userdata,this.milliseconds);
  }

  /**
   * The thread's code body.
   */
  public void run() {
    try {
      Thread.sleep(milliseconds);
    } catch (InterruptedException e) {
      System.out.println(e.getLocalizedMessage());
    }
    if (callback != null) {
      callback.timer(this, userdata);
    }
  }

} // Timer

