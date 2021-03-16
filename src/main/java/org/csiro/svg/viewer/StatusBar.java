// StatusBar.java
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
// $Id: StatusBar.java,v 1.3 2000/09/26 14:10:39 dino Exp $
//

package org.csiro.svg.viewer;

import org.csiro.svg.dom.*;
import org.csiro.svg.parser.*;


import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.BorderFactory;
import java.awt.FlowLayout;
import javax.swing.border.BevelBorder;

public class StatusBar extends JPanel {

  /**
   * the message bar for status messages
   */
  private JTextField messageBar;
  private String message;

    public StatusBar() {
        messageBar = new JTextField(50);
        messageBar.setEditable(false);
        messageBar.setBorder(BorderFactory.createEmptyBorder());
        messageBar.setText("");
        message = "";
        this.setBorder(BorderFactory.createEtchedBorder());
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 3, 0));
        this.add(messageBar);
    }

    public void showStatus(String status) {
        message = status;
        messageBar.setText(status);
    }

}


