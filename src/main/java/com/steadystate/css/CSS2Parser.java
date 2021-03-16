/*
 * CSS2Parser.java
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
 * $Id: CSS2Parser.java,v 1.1 2002/02/21 08:19:02 brett Exp $
 */

package com.steadystate.css;

import java.io.*;

import org.w3c.css.sac.InputSource;
import com.steadystate.css.parser.CSSOMParser;
import org.w3c.dom.Node;
import org.w3c.dom.css.CSSPageRule;

/**
 *
 * @author  David Schweinsberg
 * @version $Release$
 * @deprecated As of 0.9.0, replaced by
 * {@link com.steadystate.css.parser.CSSOMParser}
 */
public class CSS2Parser {

    private CSSOMParser _parser = null;
    private InputSource _is = null;
    
    public CSS2Parser(
            Reader stream,
            org.w3c.dom.Node ownerNode,
            String href,
            String title,
            String media) {
        _parser = new CSSOMParser();
        _is = new InputSource(stream);
    }

    public CSS2Parser(
            InputStream stream,
            Node ownerNode,
            String href,
            String title,
            String media) {
        this(new InputStreamReader(stream), ownerNode, href, title, media);
    }

    public CSS2Parser(Reader stream) {
        this(stream, null, null, null, null);
    }

    public CSS2Parser(InputStream stream) {
        this(stream, null, null, null, null);
    }
/*
    public CSS2Parser(
            InputStream stream,
            StyleSheet parentStyleSheet,
            CSSRule ownerRule,
            String href,
            String title,
            String media) {
        _parentStyleSheet = parentStyleSheet;
        _ownerRule = ownerRule;
        _href = href;
        _title = title;
        _media = media;
    }

    public CSS2Parser(
            Reader stream,
            StyleSheet parentStyleSheet,
            CSSRule ownerRule,
            String href,
            String title,
            String media) {
        _parser = new CSSOMParser();
        _is = new InputSource(stream);
    }
*/    
    public org.w3c.dom.css.CSSStyleSheet styleSheet() {
        try {
            return _parser.parseStyleSheet(_is);
        } catch (IOException e) {
            return null;
        }
    }

    public org.w3c.dom.css.CSSRuleList styleSheetRuleList() throws IOException {
        return null;
    }

    public org.w3c.dom.css.CSSCharsetRule charsetRule() throws IOException {
        return null;
    }

    public org.w3c.dom.css.CSSUnknownRule unknownRule() throws IOException {
        return null;
    }

    public org.w3c.dom.css.CSSImportRule importRule() throws IOException {
        return null;
    }

    public org.w3c.dom.css.CSSMediaRule mediaRule() throws IOException {
        return null;
    }

    public CSSPageRule pageRule() throws IOException {
        return null;
    }

    public org.w3c.dom.css.CSSFontFaceRule fontFaceRule() throws IOException {
        return null;
    }

    public org.w3c.dom.css.CSSStyleRule styleRule() throws IOException {
        return null;
    }
    
    public org.w3c.dom.css.CSSStyleDeclaration styleDeclaration() {
        try {
            return _parser.parseStyleDeclaration(_is);
        } catch (IOException e) {
            return null;
        }
    }

    public org.w3c.dom.css.CSSValue expr() {
        try {
            return _parser.parsePropertyValue(_is);
        } catch (IOException e) {
            return null;
        }
    }
}