/*
 * RGBColorImpl.java
 *
 * SteadyState CSS2 Parser
 *
 * Copyright (C) 1999, 2000 Steady State Software Ltd.  All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * To contact the authors of the library, write to Steady State Software Ltd.,
 * Unit 5, Manor Farm Courtyard, Rowsham, Buckinghamshire, HP22 4QP, England
 *
 * http://www.steadystate.com/css/
 * mailto:css@steadystate.co.uk
 *
 * $Id: RGBColorImpl.java,v 1.1 2002/02/21 08:19:02 brett Exp $
 */

package com.steadystate.css.dom;

import org.w3c.css.sac.*;
import org.w3c.dom.css.RGBColor;

public class RGBColorImpl implements RGBColor {

    private org.w3c.dom.css.CSSPrimitiveValue _red = null;
    private org.w3c.dom.css.CSSPrimitiveValue _green = null;
    private org.w3c.dom.css.CSSPrimitiveValue _blue = null;

    protected RGBColorImpl(LexicalUnit lu) {
        LexicalUnit next = lu;
        _red = new CSSValueImpl(next, true);
        next = next.getNextLexicalUnit();
        next = next.getNextLexicalUnit();
        _green = new CSSValueImpl(next, true);
        next = next.getNextLexicalUnit();
        next = next.getNextLexicalUnit();
        _blue = new CSSValueImpl(next, true);
    }

    protected RGBColorImpl() {
    }
    
    public org.w3c.dom.css.CSSPrimitiveValue getRed() {
        return _red;
    }

    public void setRed( org.w3c.dom.css.CSSPrimitiveValue red) {
        _red = red;
    }

    public org.w3c.dom.css.CSSPrimitiveValue getGreen() {
        return _green;
    }

    public void setGreen( org.w3c.dom.css.CSSPrimitiveValue green) {
        _green = green;
    }

    public org.w3c.dom.css.CSSPrimitiveValue getBlue() {
        return _blue;
    }

    public void setBlue( org.w3c.dom.css.CSSPrimitiveValue blue) {
        _blue = blue;
    }

    public String toString() {
        return
            "rgb(" +
            _red.toString() + ", " +
            _green.toString() + ", " +
            _blue.toString() + ")";
    }
}
