/*
 * Created on 22.02.2010
 *
 */
package org.jdesktop.swingx;

import java.util.Locale;

/**
 * 
 */
public interface Localizable {

    /**
     * Sets the Locale and guarantees to update all locale-dependent properties
     * accordingly.
     * 
     * PENDING JW: specify default? SwingX defaults to Locale.getDefault.
     * 
     * @param locale the locale to set, may be null to denote the default Locale
     *   
     */
    public void setLocale(Locale locale);
    
    /**
     * Returns the Locale, guaranteed to be never null.
     * 
     * @return the Locale which drives all locale-dependent properties.
     */
    public Locale getLocale();
}
