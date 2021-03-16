/*
 * (c) COPYRIGHT 1999 World Wide Web Consortium
 * (Massachusetts Institute of Technology, Institut National de Recherche
 *  en Informatique et en Automatique, Keio University).
 * All Rights Reserved. http://www.w3.org/Consortium/Legal/
 *
 * $Id: CombinatorSelector.java,v 1.2 2002/02/21 22:43:06 brett Exp $
 */
package org.w3c.css.sac;

/**
 * @version $Revision: 1.2 $
 * @author  Philippe Le Hegaret
 * @see Selector#SAC_DESCENDANT_SELECTOR
 * @see Selector#SAC_CHILD_SELECTOR
 * @see Selector#SAC_DIRECT_ADJACENT_SELECTOR
 * @see Selector#SAC_INDIRECT_ADJACENT_SELECTOR
 */
public interface CombinatorSelector extends Selector {
    
    /**
     * Returns the parent selector.
     */    
    public Selector getParentSelector();

    /*
     * Returns the simple selector.
     */    
    public SimpleSelector getSimpleSelector();
}
