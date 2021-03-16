/*
 * CSSMediaRuleImpl.java
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
 * $Id: CSSMediaRuleImpl.java,v 1.2 2002/02/22 03:57:06 brett Exp $
 */

package com.steadystate.css.dom;

import com.steadystate.css.parser.CSSOMParser;
import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.stylesheets.MediaList;

import java.io.IOException;
import java.io.StringReader;

/**
 *
 * @author  David Schweinsberg
 * @version $Release$
 */
public class CSSMediaRuleImpl implements org.w3c.dom.css.CSSMediaRule {

    private CSSStyleSheetImpl _parentStyleSheet = null;
    private org.w3c.dom.css.CSSRule _parentRule = null;
    private org.w3c.dom.stylesheets.MediaList _media = null;
    private org.w3c.dom.css.CSSRuleList _rules = null;

    public CSSMediaRuleImpl(
            CSSStyleSheetImpl parentStyleSheet,
            org.w3c.dom.css.CSSRule parentRule,
            org.w3c.dom.stylesheets.MediaList media) {
        _parentStyleSheet = parentStyleSheet;
        _parentRule = parentRule;
        _media = media;
    }

    public short getType() {
        return MEDIA_RULE;
    }

    public String getCssText() {
        StringBuffer sb = new StringBuffer("@media ");
        sb.append(getMedia().toString()).append(" {");
        for (int i = 0; i < getCssRules().getLength(); i++) {
            org.w3c.dom.css.CSSRule rule = getCssRules().item( i);
            sb.append(rule.getCssText()).append(" ");
        }
        sb.append("}");
        return sb.toString();
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

            // The rule must be a media rule
            if (r.getType() == org.w3c.dom.css.CSSRule.MEDIA_RULE) {
                _media = ((CSSMediaRuleImpl)r)._media;
                _rules = ((CSSMediaRuleImpl)r)._rules;
            } else {
                throw new DOMExceptionImpl(
                  org.w3c.dom.DOMException.INVALID_MODIFICATION_ERR,
                  DOMExceptionImpl.EXPECTING_MEDIA_RULE);
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

    public org.w3c.dom.css.CSSRule getParentRule() {
        return _parentRule;
    }

    public MediaList getMedia() {
        return _media;
    }

    public org.w3c.dom.css.CSSRuleList getCssRules() {
        return _rules;
    }

    public int insertRule(String rule, int index) throws
      org.w3c.dom.DOMException {
        if (_parentStyleSheet != null && _parentStyleSheet.isReadOnly()) {
            throw new DOMExceptionImpl(
              org.w3c.dom.DOMException.NO_MODIFICATION_ALLOWED_ERR,
              DOMExceptionImpl.READ_ONLY_STYLE_SHEET);
        }

        try {
            InputSource is = new InputSource(new StringReader(rule));
            CSSOMParser parser = new CSSOMParser();
            parser.setParentStyleSheet(_parentStyleSheet);
            parser.setParentRule(_parentRule);
            CSSRule r = parser.parseRule( is);

            // Insert the rule into the list of rules
            ((CSSRuleListImpl)getCssRules()).insert(r, index);

        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DOMExceptionImpl(
              org.w3c.dom.DOMException.INDEX_SIZE_ERR,
              DOMExceptionImpl.ARRAY_OUT_OF_BOUNDS,
              e.getMessage());
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
        return index;
    }

    public void deleteRule(int index) throws org.w3c.dom.DOMException {
        if (_parentStyleSheet != null && _parentStyleSheet.isReadOnly()) {
            throw new DOMExceptionImpl(
              org.w3c.dom.DOMException.NO_MODIFICATION_ALLOWED_ERR,
              DOMExceptionImpl.READ_ONLY_STYLE_SHEET);
        }
        try {
            ((CSSRuleListImpl)getCssRules()).delete(index);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DOMExceptionImpl(
              DOMException.INDEX_SIZE_ERR,
              DOMExceptionImpl.ARRAY_OUT_OF_BOUNDS,
              e.getMessage());
        }
    }

    public void setRuleList(CSSRuleListImpl rules) {
        _rules = rules;
    }
    
    public String toString() {
        return getCssText();
    }
}
