/*
 * RectImpl.java
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
 * $Id: RectImpl.java,v 1.1 2002/02/21 08:19:02 brett Exp $
 */
 
package com.steadystate.css.dom;

import org.w3c.css.sac.*;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.Rect;

/** 
 *
 * @author  David Schweinsberg
 * @version $Release$
 */
public class RectImpl implements Rect {
    
    private final CSSPrimitiveValue _left;
    private final CSSPrimitiveValue _top;
    private final CSSPrimitiveValue _right;
    private final CSSPrimitiveValue _bottom;

    /** Creates new RectImpl */
    public RectImpl(LexicalUnit lu) {
        LexicalUnit next = lu;
        _left = new CSSValueImpl(next, true);
        next = next.getNextLexicalUnit();
        next = next.getNextLexicalUnit();
        _top = new CSSValueImpl(next, true);
        next = next.getNextLexicalUnit();
        next = next.getNextLexicalUnit();
        _right = new CSSValueImpl(next, true);
        next = next.getNextLexicalUnit();
        next = next.getNextLexicalUnit();
        _bottom = new CSSValueImpl(next, true);
    }
  
    public CSSPrimitiveValue getTop() {
        return _top;
    }

    public CSSPrimitiveValue getRight() {
        return _right;
    }

    public CSSPrimitiveValue getBottom() {
        return _bottom;
    }

    public CSSPrimitiveValue getLeft() {
        return _left;
    }
    
    public String toString() {
        return "rect(" +
          _left.toString() +
          ", " +
          _top.toString() +
          ", " +
          _right.toString() +
          ", " +
          _bottom.toString() +
          ")";
    }
}