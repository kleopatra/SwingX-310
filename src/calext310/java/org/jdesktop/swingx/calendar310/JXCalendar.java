/*
 * Created on 03.08.2012
 *
 */
package org.jdesktop.swingx.calendar310;

import javax.swing.JComponent;

import org.jdesktop.swingx.calendar310.plaf.CalendarAddon;
import org.jdesktop.swingx.calendar310.plaf.CalendarUI;
import org.jdesktop.swingx.plaf.LookAndFeelAddons;

/**
 * @author Jeanette Winzenburg, Berlin
 */
public class JXCalendar extends JComponent {

    public final static String uiClassID = "XCalendarUI";
    
    static {
        LookAndFeelAddons.contribute(new CalendarAddon());
    }

    
    public JXCalendar() {
        updateUI();
    }
    
//------------------------ UI 
    
    
    /**
     * @return
     */
    public CalendarUI getUI() {
        return (CalendarUI) ui;
    }

    @Override
    public void updateUI() {
        setUI((CalendarUI)LookAndFeelAddons.getUI(this, CalendarUI.class));
    }

    @Override
    public String getUIClassID() {
        return uiClassID;
    }

}
