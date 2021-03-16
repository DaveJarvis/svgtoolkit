/*
 * CSSOMParseTest.java
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
 * $Id: CSSOMParseTest.java,v 1.1 2002/02/21 08:19:03 brett Exp $
 */

package com.steadystate.css.parser;

import java.io.*;
import org.w3c.css.sac.*;
import org.w3c.dom.css.CSSRule;

/**
 *
 * @author  David Schweinsberg
 * @version $Release$
 */
public class CSSOMParseTest extends Object {

    /** Creates new CSSOMParseTest */
    public CSSOMParseTest() {
        try {
            Reader r = new FileReader("d:\\working\\css2parser\\stylesheets\\test.css");
//            Reader r = new StringReader("FOO { color: rgb(1,2,3) }");
            InputSource is = new InputSource(r);
            CSSOMParser parser = new CSSOMParser();
            org.w3c.dom.css.CSSStyleSheet ss = parser.parseStyleSheet( is);
            System.out.println(ss.toString());

            org.w3c.dom.css.CSSRuleList rl = ss.getCssRules();
            for (int i = 0; i < rl.getLength(); i++) {
                org.w3c.dom.css.CSSRule rule = rl.item( i);
                if (rule.getType() == CSSRule.STYLE_RULE) {
                    org.w3c.dom.css.CSSStyleRule sr = (org.w3c.dom.css.CSSStyleRule) rule;
                    org.w3c.dom.css.CSSStyleDeclaration style = sr.getStyle();
                    for (int j = 0; j < style.getLength(); j++) {
                        org.w3c.dom.css.CSSValue value = style.getPropertyCSSValue( style.item( j));
                        if (value.getCssValueType() == org.w3c.dom.css.CSSValue.CSS_PRIMITIVE_VALUE) {
                            org.w3c.dom.css.CSSPrimitiveValue pv = (org.w3c.dom.css.CSSPrimitiveValue) value;
                            System.out.println(">> " + pv.toString());
                            if (pv.getPrimitiveType() == org.w3c.dom.css.CSSPrimitiveValue.CSS_COUNTER) {
                                System.out.println("CSS_COUNTER(" + pv.toString() + ")");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            e.printStackTrace(System.err);
        }
    }

    public static void main(String[] args) {
        new CSSOMParseTest();
    }
}