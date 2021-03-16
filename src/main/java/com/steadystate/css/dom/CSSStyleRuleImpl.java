/*
 * CSSStyleRuleImpl.java
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
 * $Id: CSSStyleRuleImpl.java,v 1.1 2002/02/21 08:19:02 brett Exp $
 */

package com.steadystate.css.dom;

import com.steadystate.css.parser.CSSOMParser;
import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.InputSource;
import org.w3c.css.sac.SelectorList;
import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSRule;

import java.io.IOException;
import java.io.StringReader;

/** 
 *
 * @author  David Schweinsberg
 * @version $Release$
 */
public class CSSStyleRuleImpl implements org.w3c.dom.css.CSSStyleRule {

    private CSSStyleSheetImpl _parentStyleSheet = null;
    private org.w3c.dom.css.CSSRule _parentRule = null;
    private SelectorList _selectors = null;
    private org.w3c.dom.css.CSSStyleDeclaration _style = null;

    public CSSStyleRuleImpl( CSSStyleSheetImpl parentStyleSheet, org.w3c.dom.css.CSSRule parentRule, SelectorList selectors) {
        _parentStyleSheet = parentStyleSheet;
        _parentRule = parentRule;
        _selectors = selectors;
    }

    public short getType() {
        return STYLE_RULE;
    }

    public String getCssText() {
        return getSelectorText() + " " + getStyle().toString();
    }

    public void setCssText(String cssText) throws org.w3c.dom.DOMException {
        if (_parentStyleSheet != null && _parentStyleSheet.isReadOnly()) {
            throw new DOMExceptionImpl(
              org.w3c.dom.DOMException.NO_MODIFICATION_ALLOWED_ERR,
              DOMExceptionImpl.READ_ONLY_STYLE_SHEET);
        }

        try {
            InputSource is = new InputSource(new StringReader(cssText));
            CSSOMParser parser = new CSSOMParser();
            org.w3c.dom.css.CSSRule r = parser.parseRule( is);

            // The rule must be a style rule
            if (r.getType() == org.w3c.dom.css.CSSRule.STYLE_RULE) {
                _selectors = ((CSSStyleRuleImpl)r)._selectors;
                _style = ((CSSStyleRuleImpl)r)._style;
            } else {
                throw new DOMExceptionImpl(
                  org.w3c.dom.DOMException.INVALID_MODIFICATION_ERR,
                  DOMExceptionImpl.EXPECTING_STYLE_RULE);
            }
        } catch (CSSException e) {
            throw new DOMExceptionImpl(
              org.w3c.dom.DOMException.SYNTAX_ERR,
              DOMExceptionImpl.SYNTAX_ERROR,
              e.getMessage());
        } catch (IOException e) {
            throw new DOMExceptionImpl(
              org.w3c.dom.DOMException.SYNTAX_ERR,
              DOMExceptionImpl.SYNTAX_ERROR,
              e.getMessage());
        }
    }

    public org.w3c.dom.css.CSSStyleSheet getParentStyleSheet() {
        return _parentStyleSheet;
    }

    public CSSRule getParentRule() {
        return _parentRule;
    }

    public String getSelectorText() {
        return _selectors.toString();
    }

    public void setSelectorText(String selectorText) throws
      org.w3c.dom.DOMException {
        if (_parentStyleSheet != null && _parentStyleSheet.isReadOnly()) {
            throw new DOMExceptionImpl(
              org.w3c.dom.DOMException.NO_MODIFICATION_ALLOWED_ERR,
              DOMExceptionImpl.READ_ONLY_STYLE_SHEET );
        }

        try {
            InputSource is = new InputSource(new StringReader(selectorText));
            CSSOMParser parser = new CSSOMParser();
            _selectors = parser.parseSelectors(is);
        } catch (CSSException e) {
            throw new DOMExceptionImpl(
              org.w3c.dom.DOMException.SYNTAX_ERR,
              DOMExceptionImpl.SYNTAX_ERROR,
              e.getMessage());
        } catch (IOException e) {
            throw new DOMExceptionImpl(
              DOMException.SYNTAX_ERR,
              DOMExceptionImpl.SYNTAX_ERROR,
              e.getMessage());
        }
    }

    public org.w3c.dom.css.CSSStyleDeclaration getStyle() {
        return _style;
    }

    public void setStyle(CSSStyleDeclarationImpl style) {
        _style = style;
    }
    
    public String toString() {
        return getCssText();
    }
}
