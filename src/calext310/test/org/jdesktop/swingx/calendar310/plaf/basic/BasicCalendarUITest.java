/*
 * Created on 03.08.2012
 *
 */
package org.jdesktop.swingx.calendar310.plaf.basic;

import javax.time.calendrical.LocalDateTimeField;

import org.jdesktop.swingx.InteractiveTestCase;
import org.jdesktop.swingx.calendar.jsr310.DateTimeUtils;
import org.jdesktop.swingx.calendar310.JXCalendar;
import org.jdesktop.swingx.calendar310.PageNavigator;
import org.jdesktop.swingx.calendar310.plaf.CalendarUI;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author Jeanette Winzenburg, Berlin
 */
@RunWith(JUnit4.class)
public class BasicCalendarUITest extends InteractiveTestCase {

    private CalendarUI ui;
    private PageNavigator navigator;
    
    @Test
    public void testInstallNavigator() {
        assertEquals(navigator.getStartOfPage(), ui.getStartOfPage());
        assertEquals(navigator.getColumnCount(), ui.getColumnCount());
        assertEquals(navigator.getRowCount(), ui.getRowCount());
        assertEquals(6, ui.getRowCount());
        // not the same to the millisecond ;-)
        assertTrue(DateTimeUtils.isSame(navigator.getLead(), ui.getLead(), LocalDateTimeField.SECOND_OF_DAY));
    }
    
    @Before
    public void setup() {
        JXCalendar calendar = new JXCalendar();
        ui = calendar.getUI();
        navigator = new PageNavigator();
    }
}
