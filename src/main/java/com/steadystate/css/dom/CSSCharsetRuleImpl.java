/*
 * CSSCharsetRule.java
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
 * $Id: CSSCharsetRuleImpl.java,v 1.1 2002/02/21 08:19:02 brett Exp $
 */

package com.steadystate.css.dom;

import java.io.*;

import org.w3c.css.sac.*;
import com.steadystate.css.parser.*;
import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSRule;

/**
 *
 * @author  David Schweinsberg
 * @version $Release$
 */
public class CSSCharsetRuleImpl implements org.w3c.dom.css.CSSCharsetRule {

    CSSStyleSheetImpl _parentStyleSheet = null;
    org.w3c.dom.css.CSSRule _parentRule = null;
    String _encoding = null;

    public CSSCharsetRuleImpl(
            CSSStyleSheetImpl parentStyleSheet,
            org.w3c.dom.css.CSSRule parentRule,
            String encoding) {
        _parentStyleSheet = parentStyleSheet;
        _parentRule = parentRule;
        _encoding = encoding;
    }

    public short getType() {
        return CHARSET_RULE;
    }

    public String getCssText() {
        return "@charset \"" + getEncoding() + "\";";
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

            // The rule must be a charset rule
            if (r.getType() == org.w3c.dom.css.CSSRule.CHARSET_RULE) {
                _encoding = ((CSSCharsetRuleImpl)r)._encoding;
            } else {
                throw new DOMExceptionImpl(
                  org.w3c.dom.DOMException.INVALID_MODIFICATION_ERR,
                  DOMExceptionImpl.EXPECTING_CHARSET_RULE);
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

    public String getEncoding() {
        return _encoding;
    }

    public void setEncoding(String encoding) throws DOMException {
        _encoding = encoding;
    }
}
