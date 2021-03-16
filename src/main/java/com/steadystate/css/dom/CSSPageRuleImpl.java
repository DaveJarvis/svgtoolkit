/*
 * CSSPageRuleImpl.java
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
 * $Id: CSSPageRuleImpl.java,v 1.1 2002/02/21 08:19:02 brett Exp $
 */

package com.steadystate.css.dom;

import java.io.*;

import org.w3c.css.sac.*;
import com.steadystate.css.parser.*;
import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSPageRule;

import static com.steadystate.css.dom.DOMExceptionImpl.*;
import static org.w3c.dom.DOMException.*;

/**
 * TO DO:
 * Implement setSelectorText()
 *
 * @author  David Schweinsberg
 * @version $Release$
 */
public class CSSPageRuleImpl implements CSSPageRule {

    private CSSStyleSheetImpl _parentStyleSheet = null;
    private org.w3c.dom.css.CSSRule _parentRule = null;
    private String _ident = null;
    private String _pseudoPage = null;
    private org.w3c.dom.css.CSSStyleDeclaration _style = null;

    public CSSPageRuleImpl(
            CSSStyleSheetImpl parentStyleSheet,
            org.w3c.dom.css.CSSRule parentRule,
            String ident,
            String pseudoPage) {
        _parentStyleSheet = parentStyleSheet;
        _parentRule = parentRule;
        _ident = ident;
        _pseudoPage = pseudoPage;
    }

    public short getType() {
        return PAGE_RULE;
    }

    public String getCssText() {
        String sel = getSelectorText();
        return "@page "
            + sel + ((sel.length() > 0) ? " " : "")
            + getStyle().getCssText();
    }

    public void setCssText(String cssText) throws org.w3c.dom.DOMException {
        if (_parentStyleSheet != null && _parentStyleSheet.isReadOnly()) {
            throw new DOMExceptionImpl(
              NO_MODIFICATION_ALLOWED_ERR,
              READ_ONLY_STYLE_SHEET);
        }

        try {
            InputSource is = new InputSource(new StringReader(cssText));
            CSSOMParser parser = new CSSOMParser();
            org.w3c.dom.css.CSSRule r = parser.parseRule( is);

            // The rule must be a page rule
            if (r.getType() == org.w3c.dom.css.CSSRule.PAGE_RULE) {
                _ident = ((CSSPageRuleImpl)r)._ident;
                _pseudoPage = ((CSSPageRuleImpl)r)._pseudoPage;
                _style = ((CSSPageRuleImpl)r)._style;
            } else {
                throw new DOMExceptionImpl(
                  INVALID_MODIFICATION_ERR,
                  EXPECTING_PAGE_RULE);
            }
        } catch (CSSException e) {
            throw new DOMExceptionImpl(
              SYNTAX_ERR,
              SYNTAX_ERROR,
              e.getMessage());
        } catch (IOException e) {
            throw new DOMExceptionImpl(
              SYNTAX_ERR,
              SYNTAX_ERROR,
              e.getMessage());
        }
    }

    public org.w3c.dom.css.CSSStyleSheet getParentStyleSheet() {
        return _parentStyleSheet;
    }

    public org.w3c.dom.css.CSSRule getParentRule() {
        return _parentRule;
    }

    public String getSelectorText() {
        return ((_ident != null) ? _ident : "")
            + ((_pseudoPage != null) ? ":" + _pseudoPage : "");
    }

    public void setSelectorText(String selectorText) throws DOMException {
    }

    public org.w3c.dom.css.CSSStyleDeclaration getStyle() {
        return _style;
    }

    protected void setIdent(String ident) {
        _ident = ident;
    }

    protected void setPseudoPage(String pseudoPage) {
        _pseudoPage = pseudoPage;
    }

    public void setStyle(CSSStyleDeclarationImpl style) {
        _style = style;
    }
}
