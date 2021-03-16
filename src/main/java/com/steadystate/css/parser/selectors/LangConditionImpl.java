/*
 * LangConditionImpl.java
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
 * $Id: LangConditionImpl.java,v 1.1 2002/02/22 04:05:19 brett Exp $
 */

package com.steadystate.css.parser.selectors;

import org.w3c.css.sac.*;

/** 
 *
 * @author  David Schweinsberg
 * @version $Release$
 */
public class LangConditionImpl implements LangCondition {

    private String _lang;

    public LangConditionImpl(String lang) {
        _lang = lang;
    }

    public short getConditionType() {
        return Condition.SAC_LANG_CONDITION;
    }

    public String getLang() {
        return _lang;
    }
    
    public String toString() {
        return ":lang(" + getLang() + ")";
    }
}
