/*
 * Created on 01.08.2012
 *
 */
package org.jdesktop.swingx.calendar.jsr310;

import java.util.logging.Logger;

import javax.time.LocalDateTime;
import javax.time.LocalTime;
import javax.time.calendrical.DateTimeAdjusters;
import javax.time.calendrical.LocalDateTimeField;

import org.jdesktop.swingx.InteractiveTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author Jeanette Winzenburg, Berlin
 */
@RunWith(JUnit4.class)
public class DateTimeUtilsTest extends InteractiveTestCase {
    
    @Test
    public void testIsStartOfMonth() {
        LocalDateTime now = LocalDateTime.now();
        LOG.info("startofyear " + now.with(DateTimeAdjusters.firstDayOfYear()));
        
        LocalTime midNight = LocalTime.MIDNIGHT;
        LocalTime copy = midNight.withMinute(10).withMinute(0);
//        LOG.info("" + midNight + " / " + copy);
//        assertNotSame(midNight, copy);
    }

    @Test
    public void testSameDateDayOfMonth() {
        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
        int amount = hour <12 ? 3 : -3;
        LocalDateTime laterToday = now.plusHours(amount);
        assertTrue("expected same day: " + now + " / " + laterToday,
                DateTimeUtils.isSame(now, laterToday, LocalDateTimeField.DAY_OF_MONTH));
    }
    
    @Test
    public void testNotSameDateDayOfMonthOtherDay() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime laterToday = now.plusDays(1);
        assertFalse("expected not same day: " + now + " / " + laterToday,
                DateTimeUtils.isSame(now, laterToday, LocalDateTimeField.DAY_OF_MONTH));
    }
    
    @Test
    public void testNotSameDateDayOfMonthOtherMonth() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime laterToday = now.plusMonths(1);
        assertFalse("expected not same day: " + now + " / " + laterToday,
                DateTimeUtils.isSame(now, laterToday, LocalDateTimeField.DAY_OF_MONTH));
    }
    @Test
    public void testNotSameDateDayOfMonthOtherYear() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime laterToday = now.minusYears(1);
        assertFalse("expected not same day: " + now + " / " + laterToday,
                DateTimeUtils.isSame(now, laterToday, LocalDateTimeField.DAY_OF_MONTH));
    }
    
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(DateTimeUtilsTest.class
            .getName());
}
