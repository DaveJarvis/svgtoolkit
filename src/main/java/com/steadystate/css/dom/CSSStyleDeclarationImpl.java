/*
 * CSSStyleDeclarationImpl.java
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
 * $Id: CSSStyleDeclarationImpl.java,v 1.1 2002/02/21 08:19:02 brett Exp $
 */

package com.steadystate.css.dom;

import com.steadystate.css.parser.CSSOMParser;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSRule;

import java.io.StringReader;
import java.util.Vector;

/**
 * @author David Schweinsberg
 * @version $Release$
 */
public class CSSStyleDeclarationImpl implements
  org.w3c.dom.css.CSSStyleDeclaration {

    private org.w3c.dom.css.CSSRule _parentRule;
    private Vector _properties = new Vector();
    
    public CSSStyleDeclarationImpl( org.w3c.dom.css.CSSRule parentRule) {
        _parentRule = parentRule;
    }

    public String getCssText() {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        //if newlines requested in text
        //sb.append("\n");
        for (int i = 0; i < _properties.size(); ++i) {
            Property p = (Property) _properties.elementAt(i);
            if (p != null) {
                sb.append(p.toString());
            }
            if (i < _properties.size() - 1) {
                sb.append("; ");
            }
            //if newlines requested in text
            //sb.append("\n");
        }
        sb.append("}");
        return sb.toString();
    }

    public void setCssText(String cssText) throws org.w3c.dom.DOMException {
        try {
            InputSource is = new InputSource(new StringReader(cssText));
            CSSOMParser parser = new CSSOMParser();
            _properties.clear();
            parser.parseStyleDeclaration(this, is);
        } catch (Exception e) {
            throw new DOMExceptionImpl(
              org.w3c.dom.DOMException.SYNTAX_ERR,
              DOMExceptionImpl.SYNTAX_ERROR,
              e.getMessage());
        }
    }

    public String getPropertyValue(String propertyName) {
        Property p = getPropertyDeclaration(propertyName);
        return (p != null) ? p.getValue().toString() : "";
    }

    public org.w3c.dom.css.CSSValue getPropertyCSSValue( String propertyName) {
        Property p = getPropertyDeclaration(propertyName);
        return (p != null) ? p.getValue() : null;
    }

    public String removeProperty(String propertyName) throws
      org.w3c.dom.DOMException {
        for (int i = 0; i < _properties.size(); i++) {
            Property p = (Property) _properties.elementAt(i);
            if (p.getName().equalsIgnoreCase(propertyName)) {
                _properties.remove(i);
                return p.getValue().toString();
            }
        }
        return "";
    }

    public String getPropertyPriority(String propertyName) {
        Property p = getPropertyDeclaration(propertyName);
        if (p != null) {
            return p.isImportant() ? "important" : "";
        } else {
            return "";
        }
    }

    public void setProperty(
            String propertyName,
            String value,
            String priority ) throws org.w3c.dom.DOMException {
        try {
            InputSource is = new InputSource(new StringReader(value));
            CSSOMParser parser = new CSSOMParser();
            org.w3c.dom.css.CSSValue expr = parser.parsePropertyValue( is);
            Property p = getPropertyDeclaration(propertyName);
            boolean important = (priority != null)
                ? priority.equalsIgnoreCase("important")
                : false;
            if (p == null) {
                p = new Property(propertyName, expr, important);
                addProperty(p);
            } else {
                p.setValue(expr);
                p.setImportant(important);
            }
        } catch (Exception e) {
            throw new DOMExceptionImpl(
              DOMException.SYNTAX_ERR,
              DOMExceptionImpl.SYNTAX_ERROR,
              e.getMessage());
        }
    }
    
    public int getLength() {
        return _properties.size();
    }

    public String item(int index) {
        Property p = (Property) _properties.elementAt(index);
        return (p != null) ? p.getName() : "";
    }

    public CSSRule getParentRule() {
        return _parentRule;
    }

    public void addProperty(Property p) {
        _properties.addElement(p);
    }

    private Property getPropertyDeclaration(String name) {
        for (int i = 0; i < _properties.size(); i++) {
            Property p = (Property) _properties.elementAt(i);
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

    public String toString() {
        return getCssText();
    }
}
