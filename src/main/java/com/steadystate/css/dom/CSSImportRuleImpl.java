/*
 * CSSImportRuleImpl.java
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
 * $Id: CSSImportRuleImpl.java,v 1.1 2002/02/21 08:19:02 brett Exp $
 */

package com.steadystate.css.dom;

import java.io.*;

import org.w3c.css.sac.*;
import com.steadystate.css.parser.*;
import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.stylesheets.MediaList;

/**
 * TODO:
 * Implement getStyleSheet()
 *
 * @author David Schweinsberg
 * @version $Release$
 */
public class CSSImportRuleImpl implements org.w3c.dom.css.CSSImportRule {

    CSSStyleSheetImpl _parentStyleSheet = null;
    org.w3c.dom.css.CSSRule _parentRule = null;
    String _href = null;
    org.w3c.dom.stylesheets.MediaList _media = null;

    public CSSImportRuleImpl(
            CSSStyleSheetImpl parentStyleSheet,
            org.w3c.dom.css.CSSRule parentRule,
            String href,
            org.w3c.dom.stylesheets.MediaList media) {
        _parentStyleSheet = parentStyleSheet;
        _parentRule = parentRule;
        _href = href;
        _media = media;
    }

    public short getType() {
        return IMPORT_RULE;
    }

    public String getCssText() {
        StringBuffer sb = new StringBuffer();
        sb.append("@import url(")
            .append(getHref())
            .append(")");
        if (getMedia().getLength() > 0) {
            sb.append(" ").append(getMedia().toString());
        }
        sb.append(";");
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

            // The rule must be an import rule
            if (r.getType() == org.w3c.dom.css.CSSRule.IMPORT_RULE) {
                _href = ((CSSImportRuleImpl)r)._href;
                _media = ((CSSImportRuleImpl)r)._media;
            } else {
                throw new DOMExceptionImpl(
                  org.w3c.dom.DOMException.INVALID_MODIFICATION_ERR,
                  DOMExceptionImpl.EXPECTING_IMPORT_RULE);
            }
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

    public org.w3c.dom.css.CSSStyleSheet getParentStyleSheet() {
        return _parentStyleSheet;
    }

    public CSSRule getParentRule() {
        return _parentRule;
    }

    public String getHref() {
        return _href;
    }

    public MediaList getMedia() {
        return _media;
    }

    public org.w3c.dom.css.CSSStyleSheet getStyleSheet() {
        return null;
    }
    
    public String toString() {
        return getCssText();
    }
}
