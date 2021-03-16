// SvgFileFilter.java
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
// $Id: SvgFileFilter.java,v 1.2 2000/09/26 14:10:39 dino Exp $
//

package org.csiro.svg.viewer;

import org.csiro.svg.dom.*;
import org.csiro.svg.parser.*;


import java.io.File;
import javax.swing.filechooser.FileFilter;

public class SvgFileFilter extends FileFilter {

    public SvgFileFilter() {
    }

    /**
     * Return true if this file should be shown in the directory pane,
     * false if it shouldn't.
     *
     * Files that begin with "." are ignored.
     *
     * @see #getExtension
     * @see FileFilter#accept(File)
     */
    public boolean accept(File f) {
        if (f != null) {
            if (f.isDirectory()) {
                return true;
	        }
            String extension = getExtension(f);
            if (extension != null && extension.equalsIgnoreCase("svg")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return the extension portion of the file's name .
     *
     * @see #getExtension
     * @see FileFilter#accept
     */
    public String getExtension(File f) {
        if(f != null) {
            String filename = f.getName();
            int i = filename.lastIndexOf('.');
            if (i > 0 && i < filename.length()-1) {
                return filename.substring(i+1).toLowerCase();
            }
        }
        return null;
    }

    /**
     * Returns the human readable description of this filter.
     */
    public String getDescription() {
	    return "SVG Files (*.svg)";
    }
}
