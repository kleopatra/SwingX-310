/*
 * Created on 03.08.2012
 *
 */
package org.jdesktop.swingx.calendar310;

import java.util.logging.Logger;

import javax.swing.UIManager;

import org.jdesktop.swingx.InteractiveTestCase;
import org.jdesktop.swingx.calendar310.plaf.CalendarUI;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author Jeanette Winzenburg, Berlin
 */
@RunWith(JUnit4.class)
public class CalendarTest extends InteractiveTestCase {

    private JXCalendar calendar;

    @Test
    public void testCalendarHasUI() {
        assertEquals(JXCalendar.uiClassID, calendar.getUIClassID());
        LOG.info("" + UIManager.getUI(calendar));
        assertTrue("expected ui CalendarUI, but was: " + calendar.getUI(), 
                calendar.getUI() instanceof CalendarUI);
    }
    
    @Before
    public void setup() {
        calendar = new JXCalendar();
    }
    
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(CalendarTest.class
            .getName());
}
