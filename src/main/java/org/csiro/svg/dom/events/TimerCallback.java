// TimerCallback.java
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
// $Id: TimerCallback.java,v 1.3 2000/09/26 14:10:37 dino Exp $
//

package org.csiro.svg.dom.events;

/**
 * Interface for timers to call back into a given class. If your
 * class implements this interface, you can construct Timer objects
 * (see Timer.java) that will call your class's timer method after
 * a given number of milliseconds.
 */
public interface TimerCallback {

    public void timer(Timer t, Object userdata);

} // TimerCallback

